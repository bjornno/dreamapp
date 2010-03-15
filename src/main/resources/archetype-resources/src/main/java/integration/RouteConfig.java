package ${groupId}.integration;

import ${groupId}.process.PomProcessor;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.spring.SpringRouteBuilder;
import org.apache.camel.spring.spi.SpringTransactionPolicy;

import java.util.UUID;

/**
 */
public class RouteConfig extends SpringRouteBuilder {

    @Override
    public void configure() throws Exception {
        SpringTransactionPolicy required = lookup("PROPAGATION_REQUIRED", SpringTransactionPolicy.class);
        SpringTransactionPolicy requirenew = lookup("PROPAGATION_REQUIRES_NEW", SpringTransactionPolicy.class);


        from("file:target/data/in?delay=5000&preMove=inprogress&move=done")
                .convertBodyTo(String.class)
                .policy(requirenew)
                .to("sql:insert into resources(id, text) values ('"+UUID.randomUUID().toString()+"', #)")
                .choice().when(header("CamelFileName").contains("pom.xml"))
                .to("jms:poms");  

        from("jms:poms").process(new PomProcessor());
    }

}
