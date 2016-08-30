package persistence

import com.gmongo.GMongoClient
import com.google.gson.Gson
import com.mongodb.DB
import com.mongodb.MongoCredential
import com.mongodb.ServerAddress
import response.Paste


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
            new Paste(id: v.id, language: v.language, code: v.code, title: v.title)
        }
    }

    ArrayList<Paste> fetchAllPastes(){
        this.pesto.pastes.find().collect({ v ->
            new Paste(id: v.id, language: v.language, code: v.code, title: v.title);
        })
    }

    String persistPaste(Paste paste){
        paste.id = paste.id ?: UUID.randomUUID()
        this.pesto.pastes.insert(paste.asMap());
        return id;
    }

    String updatePasteById(String id){
        return this.pesto.pastes.find(id: id)
    }

    String deletePasteById(String id){
        this.pesto.pastes.remove(id: id)
    }
}
