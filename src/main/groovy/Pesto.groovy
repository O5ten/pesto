import static spark.Spark.*
import static Utils.*;
import java.io.File;

String serverHome = System.getProperty('user.dir') + File.separator + System.getProperty('serverHome');

println 'Configuring server on port 8080'
port 8080

println 'Serving static files from:' + serverHome;
externalStaticFileLocation serverHome

Resources.enable();
