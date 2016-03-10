import static spark.Spark.*

static void greeting(){
	get('/greeting', { req, res ->
		res.type('application/json')
		new Hello(message: 'You, i like you, hello')
	}, new JsonTransformer())
}

static void systemInfo() {
	get('/system/info', { req, res ->
		res.type('application/json')
		new SystemInfo(properties: System.getProperties())
	}, new JsonTransformer())
}

static void enable(){
		greeting()
		systemInfo()
}
