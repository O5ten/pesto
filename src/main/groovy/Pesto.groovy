import static spark.Spark.*

def portNumber = 8080
println "Configuring server on port $portNumber"
port(portNumber)
staticFileLocation 'public'
Resources.start();
