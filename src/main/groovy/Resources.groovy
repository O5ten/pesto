import static spark.Spark.*
class Resources{

	void start(){
        	get '/greeting', { req, res -> '{message}' }
        	get '/page',  { req, res -> Page.createPage(req.queryMap().toMap()) }
	} 	
}
