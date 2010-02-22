package ${groupId}.controller;

import org.junit.Assert;
import org.junit.Test;
import org.mortbay.jetty.Server;
import org.mortbay.jetty.plus.naming.Resource;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

public class ApplicationTest {
    @Test
    public void testRestExampel() throws Exception {
        RestTemplate restTemplate = new RestTemplate();
        int port = startServerWithHsqlDataSource();
        MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
		map.add("text", "hello");
        
        URI loc = restTemplate.postForLocation("http://localhost:"+port+"/context/resource/", "", map);
        Assert.assertEquals("hello", restTemplate.getForObject(loc.toString(), String.class));
    }
    private int startServerWithHsqlDataSource() throws Exception {
        EmbeddedDatabaseBuilder builder = new EmbeddedDatabaseBuilder();
        EmbeddedDatabase db = builder.setType(EmbeddedDatabaseType.HSQL).addScript("db-provision.sql").build();
        new Resource("jdbc/ds", db);
        Server server = new org.mortbay.jetty.Server(0);
        server.addHandler(
                new org.mortbay.jetty.webapp.WebAppContext("src/main/webapp", "/context"));
        server.start();
        return server.getConnectors()[0].getLocalPort();
    }
}
