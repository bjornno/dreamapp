package ${groupId}.integration;

import org.junit.Test;

public class RouteConfigTest {
    @Test
    public void testRoute() throws Exception {
        /*
        ApplicationContext context = new ClassPathXmlApplicationContext("META-INF/spring/applicationContext.xml");
        CamelContext camelContext = (CamelContext) context.getBean("camelContext");
        ProducerTemplate template = camelContext.createProducerTemplate();
        MockEndpoint endpoint = (MockEndpoint) camelContext.getEndpoint("mock:result");
        endpoint.expectedMessageCount(1);
        int i = endpoint.getReceivedCounter();
        Assert.assertEquals(0,i);

        template.sendBody("direct:simple", "1");
        endpoint.assertIsSatisfied();
        */
    }
}
