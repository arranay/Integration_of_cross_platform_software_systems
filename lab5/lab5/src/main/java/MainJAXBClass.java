import models.BPMN;
import org.json.JSONObject;
import org.xml.sax.SAXException;

import javax.xml.bind.JAXBException;
import javax.xml.stream.XMLStreamException;
import javax.xml.transform.TransformerConfigurationException;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainJAXBClass {
    public static void main(String[] args) throws JAXBException, TransformerConfigurationException, IOException, SAXException, XMLStreamException {
        BPMN model = XMLParser.parseFile();
        JSONObject jsonObj = JSONGenerator.JSONGeneration(model);

        jsonObj.put("name", "Changed name");
        jsonObj.put("author", "Changed author");
        Date date = new Date();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String newDate = dateFormat.format(date);
        jsonObj.put("date", newDate);

        HTMLGenerator.HTMLGeneration(jsonObj);
    }
}
