import org.mortbay.jetty.Connector;
import org.mortbay.jetty.Handler;
import org.mortbay.jetty.Server;
import org.mortbay.jetty.bio.SocketConnector;
import org.mortbay.jetty.webapp.WebAppContext;

import java.net.URL;
import java.security.ProtectionDomain;


/**
 * TODO evaluate:
 * http://simplericity.org/svn/simplericity/trunk/jetty-console/jetty-console-core/src/main/java/JettyConsoleBootstrapMainClass.java
 *
 * (will enable us to put jetty deps in separate directory)
 *
 */
public class Start {

    private static final String WEBAPPLICATION_CONTEXT_NAME = "/";
    
    public static void main(String[] args) throws Exception {
        Server server = new Server();
        Connector defaultConnector = new SocketConnector();
        defaultConnector.setPort(8080);
        server.setConnectors(new Connector[] { defaultConnector });
        server.addHandler(createWebappContextHandler());
        try {
            server.start();
        } catch (Exception e) {
            System.exit(-1);
        }
    }


    private static Handler createWebappContextHandler() {
        WebAppContext context = new WebAppContext();
        context.setContextPath(WEBAPPLICATION_CONTEXT_NAME);
        ProtectionDomain protectionDomain = Start.class.getProtectionDomain();
        setWebappFilesLocation(context, protectionDomain);
        return context;
    }

    private static void setWebappFilesLocation(WebAppContext context, ProtectionDomain protectionDomain) {
        URL location = protectionDomain.getCodeSource().getLocation();
        context.setWar(location.toExternalForm());
    }

}
/*
public class Start {

    public static void main(String[] args) {
        Server server = new Server();
            Connector defaultConnector = new SocketConnector();
            defaultConnector.setPort(8080);
            server.setConnectors(new Connector[] { defaultConnector });
        server.addHandler(
                new org.mortbay.jetty.webapp.WebAppContext("src/main/webapp", "/"));
        try {
            server.start();
        } catch (Exception e) {
            System.exit(-1);
        }

    }

}
*/