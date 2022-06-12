import models.BPMN;
import org.xml.sax.SAXException;

import javax.xml.bind.JAXBException;
import javax.xml.stream.XMLStreamException;
import javax.xml.transform.TransformerConfigurationException;
import java.io.IOException;

public class MainJAXBClass {
    public static void main(String[] args) throws JAXBException, TransformerConfigurationException, IOException, SAXException, XMLStreamException {
        BPMN model = XMLParser.parseFile();
        HTMLGenerator.HTMLGeneration(model);
    }
}
