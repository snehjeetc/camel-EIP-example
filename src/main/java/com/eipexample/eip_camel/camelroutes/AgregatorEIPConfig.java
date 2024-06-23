package com.eipexample.eip_camel.camelroutes;

import java.util.ArrayList;

import org.apache.camel.AggregationStrategy;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AgregatorEIPConfig extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        from("activemq:queue:receivedorders")
                .aggregate(new AggregationStrategy() {
                    @Override
                    public Exchange aggregate(Exchange oldExchange, Exchange newExchange) {

                        Object newBody = newExchange.getIn().getBody();
                        ArrayList<Object> list = null;
                        if (oldExchange == null) {
                            list = new ArrayList<Object>();
                            list.add(newBody);
                            newExchange.getIn().setBody(list);
                            return newExchange;
                        } else {
                            list = oldExchange.getIn().getBody(ArrayList.class);
                            list.add(newBody);
                            return oldExchange;
                        }

                    }

                }).constant(true).completionTimeout(100L).to("bean:processBatchIncomingMessages?method=processBatchMsg"); 

    }
}
