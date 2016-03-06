@Grab(group = 'com.sparkjava', module = 'spark-core', version = '2.1')
import static spark.Spark.*

println "Configuring server..."

port 8080
staticFileLocation "public"

new Resources().start();
