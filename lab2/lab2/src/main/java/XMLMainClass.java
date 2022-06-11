import models.*;
import org.xml.sax.SAXException;

import javax.xml.stream.XMLStreamException;
import javax.xml.transform.TransformerConfigurationException;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.Objects;

public class XMLMainClass {
    public static void main(String[] args) throws XMLStreamException, IOException, ParseException, TransformerConfigurationException, SAXException {
        File xmlFile = new File(Objects.requireNonNull(XMLMainClass.class.getClassLoader().getResource("system.xml")).getFile());
        File xsdFile = new File(Objects.requireNonNull(XMLMainClass.class.getClassLoader().getResource("system.xsd")).getFile());

        boolean isFileValid = XMLValidator.schemaValidator(xmlFile, xsdFile);
        if (isFileValid) {
            BPMN model = XMLParser.parseFile(xmlFile);
            HTMLGenerator.HTMLGeneration(model);
        }
    }
}
