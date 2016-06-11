import routes.PersistenceRoute

import static spark.Spark.*

String serverHome = System.getProperty('user.dir') + File.separator + System.getProperty('serverHome');
int serverPort = System.getProperty('serverPort') as Integer;

println "Configuring server on port $serverPort"
port serverPort

println "Serving static files from $serverHome at http://localhost:$serverPort"
externalStaticFileLocation serverHome

def persistenceRoute = new PersistenceRoute().enable()