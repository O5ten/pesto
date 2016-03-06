import static spark.Spark.*

static void start(){
	get '/greeting', { req, res -> 'Hello!' }
}
