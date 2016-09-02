package routes

import com.google.gson.Gson
import json.JsonTransformer
import json.TextTransformer
import json.EmptyTransformer
import persistence.MongoWrapper
import response.Id
import response.Paste
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
        get '/api/paste/:id/code', this.&readText, new TextTransformer()
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

    Paste readText(Request req, Response res){
        def id = req.params(':id')
        def paste = db.fetchPasteById(req.params(':id'))
        res.status 200
        res.type 'text/text'
        if(paste){
            return paste.code
        } else {
            halt 404, "404 Paste with id $id not found"
        }
    }

    ArrayList<Paste> readAll(Request _, Response res){
        res.status 200
        res.type "application/json"
        return db.fetchAllPastes()
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
