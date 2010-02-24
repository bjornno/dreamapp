package {groupId}.controller;

import org.junit.Assert;
import org.junit.Test;
import org.mortbay.jetty.Server;
import org.springframework.web.client.RestTemplate;
import java.util.HashMap;

import java.net.URI;

public class ApplicationTest {
     @Test
    public void testGet() throws Exception {
        int port = startJetty();
        RestTemplate restTemplate = new RestTemplate();
        Assert.assertEquals("test", restTemplate.getForObject("http://localhost:"+port+"/resource/1", String.class));
    }

    @Test
    public void testPostAndGet() throws Exception {
        int port = startJetty();
        RestTemplate restTemplate = new RestTemplate();
        Map map = new HashMap();
        map.put("text", "hello");
        URI loc = restTemplate.postForLocation("http://localhost:"+port+"/resource/", null, map);
  //      Assert.assertEquals("hello", restTemplate.getForObject(loc, String.class));
    }
    private int startJetty() throws Exception {
        Server server = new org.mortbay.jetty.Server(0);
        server.addHandler(
                new org.mortbay.jetty.webapp.WebAppContext("src/main/webapp", "/"));
        server.start();
        return server.getConnectors()[0].getLocalPort();
    }
}
