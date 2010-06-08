package ${groupId}.controller;

import org.apache.commons.dbcp.BasicDataSource;
import org.junit.Assert;
import org.junit.Test;
import org.mortbay.jetty.Connector;
import org.mortbay.jetty.Server;
import org.mortbay.jetty.plus.naming.EnvEntry;
import org.mortbay.jetty.security.SslSocketConnector;
import org.springframework.web.client.RestTemplate;

import javax.net.ssl.*;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URI;
import java.security.KeyStore;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;

public class RestControllerTest {
    @Test
    public void testGet() throws Exception {
        int port = startJetty();
        RestTemplate restTemplate = new RestTemplate();
        Assert.assertEquals("test", restTemplate.getForObject("http://localhost:"+port+"/rest/resource/1", String.class));
    }

    @Test
    public void testPostAndGet() throws Exception {
        int port = startJetty();
        RestTemplate restTemplate = new RestTemplate();
        Map map = new HashMap();
        map.put("text", "hello");
        URI loc = restTemplate.postForLocation("http://localhost:"+port+"/rest/resource/", null, map);
        //      Assert.assertEquals("hello", restTemplate.getForObject(loc, String.class));
    }

    //@Test // run createTestKeys.sh in test resources before enabling this test
    public void testWithSSLServerAndClient() throws Exception {
        int port = startJettyWithSSL();
        RestTemplate restTemplate = new RestTemplate();
        setSSLContextForClient();
        Assert.assertEquals("test", restTemplate.getForObject("https://localhost:"+port+"/rest/resource/1", String.class));
    }

    private int startJetty() throws Exception {
        BasicDataSource basicDataSource = new BasicDataSource();
        basicDataSource.setDriverClassName("org.hsqldb.jdbcDriver");
        basicDataSource.setUrl("jdbc:hsqldb:mem:rest");
        basicDataSource.setUsername("sa");
        basicDataSource.setPassword("");

        new EnvEntry("jdbc/Ds", basicDataSource);
        Server server = new org.mortbay.jetty.Server(0);
        server.addHandler(
                new org.mortbay.jetty.webapp.WebAppContext("src/main/webapp", "/"));
        server.start();
        return server.getConnectors()[0].getLocalPort();
    }

    public int startJettyWithSSL() throws Exception {
        Server server = new org.mortbay.jetty.Server(0);

        SslSocketConnector sslConnector = new SslSocketConnector();
        sslConnector.setTruststore("src/test/resources/server.truststore.jks");
        sslConnector.setTrustPassword("123456");
        sslConnector.setKeystore("src/test/resources/server.keystore.jks");
        sslConnector.setKeyPassword("123456");
        sslConnector.setPassword("123456");
        sslConnector.setPort(8181);
        sslConnector.setName("SslConnection");
        sslConnector.setNeedClientAuth(true);
        server.setConnectors(new Connector[] { sslConnector });

        server.addHandler(
                new org.mortbay.jetty.webapp.WebAppContext("src/main/webapp", "/"));
        server.start();
        return 8181;
    }

    private  void setSSLContextForClient() throws Exception {
        String keystoreType = "JKS";
        InputStream keystoreLocation = new FileInputStream("src/test/resources/client.keystore.jks");
        char [] keystorePassword = "123456".toCharArray();
        char [] keyPassword = "123456".toCharArray();

        KeyStore keystore = KeyStore.getInstance(keystoreType);
        keystore.load(keystoreLocation, keystorePassword);
        KeyManagerFactory kmfactory = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
        kmfactory.init(keystore, keyPassword);

        InputStream truststoreLocation =  new FileInputStream("src/test/resources/client.truststore.jks");
        char [] truststorePassword = "123456".toCharArray();
        String truststoreType = "JKS";

        KeyStore truststore = KeyStore.getInstance(truststoreType);
        truststore.load(truststoreLocation, truststorePassword);
        TrustManagerFactory tmfactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
        tmfactory.init(truststore);
        KeyManager[] keymanagers = kmfactory.getKeyManagers();

        TrustManager[] trustmanagers =  tmfactory.getTrustManagers();

        SSLContext sslContext = SSLContext.getInstance("TLS");
        sslContext.init(keymanagers, trustmanagers, new SecureRandom());
        SSLContext.setDefault(sslContext);
    }
}
