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

    MongoWrapper() {
        this.username = this.username ?: 'mongodb'
        this.password = this.password ?: 'mongodb'
        this.database = this.database ?: 'pesto'
        this.gson = new Gson()
        this.credentials = MongoCredential.createMongoCRCredential(this.username, this.database, this.password as char[])
        this.client = new GMongoClient(new ServerAddress(), [credentials])
        this.pesto = client.getDB('pesto');
    }

    Paste fetchPasteById(String id) {
        def e = this.pesto.pastes.find(id: id).find()
        if (e) {
            entryAsPaste e
        }
    }

    ArrayList<Paste> fetchAllPastes(int count = 10, int after = 0) {
        return this.pesto.pastes.find().limit(count).skip(after).collect({ e ->
            entryAsPaste e
        })
    }

    ArrayList<Paste> fetchAllPastesByFilter(field, value, int count = 10, int after = 0) {
        def criteria = [:]
        criteria.put(field, value)
        this.pesto.pastes.find(criteria).limit(count).skip(after).collect({ e ->
            entryAsPaste e
        })
    }

    static Paste entryAsPaste(e) {
        return [id       : e.id,
                language : e.language ?: 'Uncategorized',
                code     : e.code ?: '',
                title    : e.title ?: '',
                published: e.published ?: getNow(),
                modified : e.modified ?: getNow(),
                votes    : e.votes ?: 0] as Paste
    }

    Id persistPaste(Paste paste) {
        paste.id = paste.id ?: UUID.randomUUID()
        paste.published = getNow();
        paste.modified = paste.published;
        println "POST: persisting object " + paste.asMap()
        this.pesto.pastes.insert(paste.asMap());
        return new Id(id: paste.id);
    }

    static final String getNow(){
        return new Date().format(asISO8601())
    }

    static final String asISO8601(){
        return 'YYYY-MM-dd HH:mm:ss'
    }

    String updatePasteById(String id, Paste paste) {
        println "PUT: updating object:" + paste.asMap()
        paste.modified = getNow();
        return this.pesto.pastes.update([id: id], paste.asMap())
    }

    String deletePasteById(String id) {
        println "DELETE: removing object with id $id"
        return this.pesto.pastes.remove(id: id)
    }
}
