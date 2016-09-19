package routes

import com.google.gson.Gson
import json.JsonTransformer
import json.TextTransformer
import json.EmptyTransformer
import persistence.MongoWrapper
import response.Id
import response.Paste
import spark.QueryParamsMap
import spark.Request
import spark.Response

import javax.xml.ws.http.HTTPException

import static spark.Spark.*;

class PersistenceRoute {
    private MongoWrapper db
    private Gson gson

    PersistenceRoute(){
        this.db = new MongoWrapper()
        this.gson = new Gson()
    }

    void enableCrud(){
        get '/api/paste', this.&readAll, new JsonTransformer()
        get '/api/paste/by/:field/as/:value', this.&readByFilter, new JsonTransformer()
        get '/api/paste/:id/:field', this.&readField, new TextTransformer()
        get '/api/paste/:id', this.&read, new JsonTransformer()
        post '/api/paste', this.&create, new JsonTransformer()
        put '/api/paste/:id', this.&update, new EmptyTransformer()
        delete '/api/paste/:id', this.&delete, new EmptyTransformer()
    }

    Id create(Request req, Response res){
        Paste paste = gson.fromJson(req.body(), Paste.class)
        println paste.asMap()
        Id id = this.db.persistPaste(paste)
        res.status 201
        res.type "application/json"
        return id;
    }

    Paste read(Request req, Response res){
        def id = req.params(':id')
        def paste = db.fetchPasteById(req.params(':id'))
        res.status 200
        res.type paste ? "application/json" : 'text/html'
        if(paste){
            return paste
        } else {
            halt 404, "404 Paste with id $id not found"
        }
    }

    String readField(Request req, Response res){
        def field = req.params(':field')
        def id = req.params(':id')
        def paste = db.fetchPasteById(id)
        res.status 200
        res.type 'text/text'
        if(paste && paste.hasProperty('field')){
            return paste[field]
        } else {
            halt 404, "404 Paste with id $id and field $field not found"
        }
    }

    ArrayList<Paste> readByFilter(Request req, Response res){
        def field = req.params(':field')
        def value = req.params(':value')
        def count = req.queryParams('limit') ?: 100
        def after = req.queryParams('after') ?: 0
        ArrayList<Paste> pastes = db.fetchAllPastesByFilter(field, value, count as int, after as int)
        res.status 200
        res.type 'application/json'
        println "GET: Filtered pastes by key $field with $value"
        return pastes
    }

    ArrayList<Paste> readAll(Request req, Response res){
        def count = req.queryParams('limit') ?: 100
        def after = req.queryParams('after') ?: 0
        res.status 200
        res.type "application/json"
        println "GET: Fetched all available pastes"
        return db.fetchAllPastes(count as int, after as int)
    }

    Id update(Request req, Response res){
        Paste paste = gson.fromJson(req.body(), Paste.class)
        db.updatePasteById req.params(':id'), paste
        res.status 200
        res.type "application/json"
    }

    Id delete(Request req, Response res){
        db.deletePasteById req.params(':id')
        res.status 204
        res.type "application/json"
    }

    PersistenceRoute enable(){
        this.enableCrud();
        return this;
    }
}
