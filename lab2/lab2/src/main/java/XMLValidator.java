import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.transform.stax.StAXSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class XMLValidator {
    public static boolean schemaValidator(File xmlFile, File xsdFile) throws SAXException, IOException, XMLStreamException {
        try {
            SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Schema schema = factory.newSchema(xsdFile);
            Validator validator = schema.newValidator();

            XMLInputFactory xmlInputFactory = XMLInputFactory.newInstance();
            XMLEventReader reader = xmlInputFactory.createXMLEventReader(new FileInputStream(xmlFile));

            validator.validate(new StAXSource(reader));
            System.out.println("LOG: XML is valid");
            return true;
        } catch (Exception e) {
            System.out.println("LOG: XML is not valid, the reason: " + e);
            return false;
        }
    }
}
