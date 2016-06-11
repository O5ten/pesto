package routes

import com.google.gson.Gson
import json.JsonTransformer
import persistence.MongoWrapper
import response.Id
import response.Paste
import spark.Request
import spark.Response

import static spark.Spark.*;

class PersistenceRoute {
    private MongoWrapper db
    private Gson gson

    PersistenceRoute(){
        this.db = new MongoWrapper()
        this.gson = new Gson()
    }

    void enableCrud(){
        get '/paste', this.&readAll, new JsonTransformer()
        get '/paste/:id', this.&read, new JsonTransformer()
        post '/paste', this.&create, new JsonTransformer()
        put '/paste/:id', this.&update
        delete '/paste/:id', this.&delete
    }

    Id create(Request req, Response res){
        Paste paste = gson.fromJson(req.body(), Paste.class)
        def id = this.db.persistPaste(paste);
        res.status 201
        res.type "application/json"
        return new Id(id: id);
    }

    Paste read(Request req, Response res){
        db.fetchPasteById req.params(':id')
        res.status 200
        res.type "application/json"
        return PasteResponse()

    }

    ArrayList<Paste> readAll(Request req, Response res){
        res.status 200
        res.type "application/json"
        return db.fetchAllPastes()
    }

    void update(Request req, Response res){
        db.updatePasteById req.params(':id')
        res.status 200
        res.type "application/json"
    }

    void delete(Request req, Response res){
        db.deletePasteById req.params(':id')
        res.status 204
        res.type "application/json"
    }

    PersistenceRoute enable(){
        this.enableCrud();
        return this;
    }
}
