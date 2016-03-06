import static spark.Spark.*

static void start(){
	get '/greeting', { req, res -> '{message}' }
	get '/page',  { req, res -> Page.createPage(req.queryMap().toMap()) }
}
