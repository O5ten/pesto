import static spark.Spark.*
import static Utils.*;
staticFileLocation '/build/public'
port 8080
println "Configuring server on port 8080"
println 'CWD: ' + System.getProperty('user.dir')
Resources.start();
