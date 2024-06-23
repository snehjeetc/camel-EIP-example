package com.eipexample.eip_camel.beans;

import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class ProcessBatchIncomingMessages {
    public void processBatchMsg(List<Object> lst) {
        lst.forEach(obj -> { 
            System.out.println(obj.toString());
        });
    }
}
