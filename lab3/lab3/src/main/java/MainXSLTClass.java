import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.XMLStreamException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.*;
import java.util.Objects;

public class MainXSLTClass {
    public static void main(String[] args) throws XMLStreamException, IOException, SAXException, TransformerException, ParserConfigurationException {
        File xmlFile = new File(Objects.requireNonNull(MainXSLTClass.class.getClassLoader().getResource("system.xml")).getFile());
        File xslFile = new File(Objects.requireNonNull(MainXSLTClass.class.getClassLoader().getResource("system.xsl")).getFile());

        convertXMLToHTML(xmlFile, xslFile);
    }

    public static void convertXMLToHTML(File xmlFile, File xslFile) throws IOException, TransformerException, SAXException, ParserConfigurationException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(xmlFile);
        DOMSource source = new DOMSource(document);

        FileOutputStream fos = new FileOutputStream("src/main/resources/result.html");
        StreamResult result = new StreamResult(fos);

        TransformerFactory tFactory = TransformerFactory.newInstance();
        StreamSource xsl = new StreamSource(xslFile);
        Transformer transformer = tFactory.newTransformer(xsl);
        transformer.transform(source, result);

        fos.close();
    }
}
