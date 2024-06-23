package com.eipexample.eip_camel.beans;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

@Component
public class PenPencil {

    public void processPenPencil(Node node) {
        //   var doc = node.getOwnerDocument(); 
        //   NodeList nodes = doc.getElementsByTagName("type");
        //   System.out.println(nodes.item(0).getLocalName() + "=" + nodes.item(0).getTextContent());
        //   nodes = doc.getElementsByTagName("quantity");
        //   System.out.println(nodes.item(0).getLocalName() + "=" + nodes.item(0).getTextContent());
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setNamespaceAware(true);
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document newDocument = builder.newDocument();
            Node importedNode = newDocument.importNode(node, true);
            newDocument.appendChild(importedNode);
            NodeList nodes = newDocument.getElementsByTagName("type");
            System.out.println(nodes.item(0).getLocalName() + "=" + nodes.item(0).getTextContent());
            nodes = newDocument.getElementsByTagName("quantity");
            System.out.println(nodes.item(0).getLocalName() + "=" + nodes.item(0).getTextContent());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
