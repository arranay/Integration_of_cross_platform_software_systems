import models.BPMN;
import org.xml.sax.SAXException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.stream.XMLStreamException;
import java.io.*;
import java.util.Objects;

public class XMLGenerator {
    public static void XMLGeneration(BPMN model, JAXBContext jaxbContext) throws JAXBException, IOException, XMLStreamException, SAXException {
        Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

        jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        jaxbMarshaller.setProperty(Marshaller.JAXB_SCHEMA_LOCATION, "src/main/resources/system.xsd");

        FileOutputStream fos = new FileOutputStream("src/main/resources/new.xml");
        OutputStreamWriter writer = new OutputStreamWriter(fos);
        jaxbMarshaller.marshal(model, writer);
        System.out.println("LOG: New xml file was generated successfully");
    }
}
