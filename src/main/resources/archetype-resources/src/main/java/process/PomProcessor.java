package ${groupId}.process;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

public class PomProcessor implements Processor {
    public void process(Exchange exchange) throws Exception {
        System.out.println("received new pom: " + exchange.getExchangeId());
    }
}
