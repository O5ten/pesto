package routes

import json.JsonTransformer
import response.Hello
import response.SystemInfo

import static spark.Spark.get

class SystemRoute {

    void greeting(){
        get '/greeting', { req, res ->
            res.type('application/json')
            new Hello(message: 'You, i like you, hello')
        }, new JsonTransformer()
    }

    void systemInfo() {
        get '/system/info', { req, res ->
            res.type('application/json')
            new SystemInfo(properties: System.getProperties())
        }, new JsonTransformer()
    }

    SystemRoute enable(){
        systemInfo()
        greeting()
        return this;
    }
}
