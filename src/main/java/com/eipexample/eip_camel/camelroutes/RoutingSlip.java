package com.eipexample.eip_camel.camelroutes;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RoutingSlip extends RouteBuilder{

    @Override
    public void configure() throws Exception {
        /** 
        RoutingSlip pattern is defined as : 
        Suppose we expect two types of messages in the same queue - 
        i) one from mobile 
        ii) one from desktop 
        Now for mobile we need to perform different set of validations 
        lets say A, C validations are required for incoming mobile requests 
        And for desktop version we need to perform different set of validations 
        lets say A, D, E 
            But these are hard to know at the compile time and hence we need to 
            configure these rules to run at runtime 
            and here comes the Routing Slip to rescue - 
            it basically checks the request, based on a certain parameter will set 
            the stages in the header. 
        */
        from("jetty:http://localhost:9090/routingslip")
        .setBody(constant(""))
        .process(exchange -> { 
            String genType = exchange.getIn().getHeader("genType", String.class);
            if(genType.equals("mobile"))
                exchange.getIn().setHeader("routes", "direct:routeA,direct:routeB");
            else 
                exchange.getIn().setHeader("routes", "direct:routeA,direct:routeC");    
        })
        .routingSlip(header("routes").tokenize(","))
        .log("${body}"); 

        from("direct:routeA")
            .setBody(body().append("routeA")); 
        from("direct:routeB")
            .setBody(body().append("routeB")); 
        from("direct:routeC")
            .setBody(body().append("routeC")); 
        
    }
    
}
