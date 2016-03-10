import static spark.Spark.*
import static Utils.*;
import java.io.File;

String cwd = System.getProperty('user.dir')
String serverHome = cwd + File.separator + 'build' + File.separator + 'public';

println 'Configuring server on port 8080'
port 8080

println 'Serving static files from:' + serverHome;
externalStaticFileLocation serverHome

Resources.enable();
