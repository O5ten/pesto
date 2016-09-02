package persistence

import com.gmongo.GMongoClient
import com.google.gson.Gson
import com.mongodb.DB
import com.mongodb.MongoCredential
import com.mongodb.ServerAddress
import response.Paste
import response.Id

class MongoWrapper {
    def MongoCredential credentials
    def GMongoClient client
    def DB pesto;
    String username
    String password
    String database
    Gson gson

    MongoWrapper(){
        this.username = this.username ?: 'mongodb'
        this.password = this.password ?: 'mongodb'
        this.database = this.database ?: 'pesto'
        this.gson = new Gson()
        this.credentials = MongoCredential.createMongoCRCredential(this.username, this.database, this.password as char[])
        this.client = new GMongoClient(new ServerAddress(), [credentials])
        this.pesto = client.getDB('pesto');
    }

    Paste fetchPasteById(String id){
        def v = this.pesto.pastes.find(id: id).find()
        if(v){
            new Paste(id: v.id, language: v.language, code: v.code, title: v.title, author: (v.author ?: 'Anonymous'))
        }
    }

    ArrayList<Paste> fetchAllPastes(){
        return this.pesto.pastes.find().collect({ v ->
            new Paste(id: v.id, language: v.language, code: v.code, title: v.title);
        })
    }

    ArrayList<Paste> fetchAllPastesByFilter(field, value){
        def criteria = [:]
        criteria.put(field, value)
        return this.pesto.pastes.find(criteria).collect({ v ->
            new Paste(id: v.id, language: v.language, code: v.code, title: v.title);
        })
    }

    Id persistPaste(Paste paste){
        paste.id = paste.id ?: UUID.randomUUID()
        println "persisting object:" + paste.asMap()
        this.pesto.pastes.insert(paste.asMap());
        return new Id(id: paste.id);
    }

    String updatePasteById(String id, Paste paste){
        println "updating object:" + paste.asMap()
        return this.pesto.pastes.update([id: id], paste.asMap())
    }

    String deletePasteById(String id){
        println "removing object with id $id"
        this.pesto.pastes.remove(id: id)
    }
}
