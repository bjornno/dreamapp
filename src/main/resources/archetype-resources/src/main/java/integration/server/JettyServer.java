package ${groupId}.server;

import org.mortbay.jetty.Server;
import org.mortbay.jetty.plus.naming.Resource;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

public class JettyServer {
    public static void main(String[] args) {
        EmbeddedDatabaseBuilder builder = new EmbeddedDatabaseBuilder();
        EmbeddedDatabase db = builder.setType(EmbeddedDatabaseType.HSQL).addScript("db-provision.sql").build();
        try {
            new Resource("jdbc/Ds", db);
            Server server = new org.mortbay.jetty.Server(8080);
            server.addHandler(
                    new org.mortbay.jetty.webapp.WebAppContext("src/main/webapp", "/context"));
            server.start();
        } catch (Exception e) {
            System.exit(-1);
        }
    }
}
