package com.spring.lab6.parser.service;

import com.spring.lab6.parser.models.BPMN;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.util.Objects;

public class XMLParser {
    public static BPMN parseFile() throws JAXBException {
        System.out.println("LOG: Start of parsing XML file");

        File xmlFile = new File(Objects.requireNonNull(XMLParser.class.getClassLoader().getResource("system.xml")).getFile());

        JAXBContext jaxbContext = JAXBContext.newInstance(BPMN.class);
        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();

        BPMN model = (BPMN) jaxbUnmarshaller.unmarshal(xmlFile);

        System.out.println("LOG: End of parsing XML file");

        return model;
    }
}
