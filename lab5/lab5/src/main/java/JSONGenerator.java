import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import models.BPMN;
import org.everit.json.schema.loader.SchemaLoader;
import org.json.JSONObject;
import org.json.JSONTokener;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class JSONGenerator {
    public static JSONObject JSONGeneration(BPMN model) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        mapper.setDateFormat(df);
        ObjectWriter writer = mapper.writer(new DefaultPrettyPrinter());

        FileOutputStream jsonFile = new FileOutputStream("src/main/resources/json.json");

        writer.writeValue(jsonFile, model);

        JSONObject jsonSchema = new JSONObject(
                new JSONTokener(Files.newInputStream(Paths.get("src/main/resources/schema.json"))));
        JSONObject jsonObj = new JSONObject(
                new JSONTokener(Files.newInputStream(Paths.get("src/main/resources/json.json"))));

        System.out.println("LOG: JSON object was generated successfully: " + jsonObj);

        try {
            org.everit.json.schema.Schema schema = SchemaLoader.load(jsonSchema);
            schema.validate(jsonObj);
            System.out.println("LOG: JSON is valid");
        } catch (Exception e) {
            System.out.println("LOG: JSON is not valid, the reason: " + e);
        }

        return jsonObj;
    }
}