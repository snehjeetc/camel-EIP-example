package com.eipexample.eip_camel.camelroutes;


import org.apache.camel.builder.RouteBuilder;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CamelRouteConfiguration extends RouteBuilder {

    @Override
    public void configure() throws Exception {

        //creating a route from file to kafka brokers
           //from orders -> using context based EIP to route it using choice when 
       /**
        * i.) context based route starts
        */
        System.out.println("Starting routes"); 
        try{
        from("file://D:/ApacheCamelStarterProjects/ApacheCamelRepoVSCode/eip-camel/orders")
        .routeId("fileInId")
        .to("kafka://orders?brokers=localhost:9092");

     
        from("kafka://orders?brokers=localhost:9092")
        .log("${body}")
        .split(xpath("/orders/order"))
        .choice()
            .when(xpath("//order/type/text() = 'vegetable'"))
                .to("activemq:queue:vegetable")
            .when(xpath("//order/type/text() = 'fruits'"))
                .to("activemq:queue:fruits")
            .otherwise()
                .to("kafka://product?brokers=localhost:9092"); 
        }catch(Exception ex){ 
            System.out.println("ex in making route" + ex.getMessage()); 
        }
        /**
         * i.) Context based route ends
         */

         /**
          * ii.) Filtering EIP starts
          */

          from("file://D:/ApacheCamelStarterProjects/ApacheCamelRepoVSCode/eip-camel/orders/order2.xml")
            .log("${body}")
            .split(xpath("/orders/order"))
            .filter().xpath("//order/type[contains(text(),'pen')] | //order/type[contains(text(),'pencil')]")
          .to("bean:penPencil?method=processPenPencil");
          /**
           * ii.) Filtering EIP ends
           */

          System.out.println("Ending routes");         

    }

}
