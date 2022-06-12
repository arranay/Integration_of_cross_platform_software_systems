import models.BPMN;
import org.xml.sax.SAXException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.stream.XMLStreamException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Date;
import java.util.Objects;

public class XMLParser {
    public static BPMN parseFile() throws JAXBException, IOException, XMLStreamException, SAXException {
        System.out.println("LOG: Start of generating HTML file");

        File xmlFile = new File(Objects.requireNonNull(MainJAXBClass.class.getClassLoader().getResource("system.xml")).getFile());
        JAXBContext jaxbContext = JAXBContext.newInstance(BPMN.class);

        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();

        BPMN model = (BPMN) jaxbUnmarshaller.unmarshal(xmlFile);
        System.out.println("LOG: End of generating HTML file");

        model.setName("Changed name");
        model.setAuthor("Changed author");
        model.setDate(new Date());
        System.out.println("LOG: The xml data has been successfully modified");

        XMLGenerator.XMLGeneration(model, jaxbContext);

        return model;
    }
}
