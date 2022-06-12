import models.*;
import org.json.JSONArray;
import org.json.JSONObject;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.sax.SAXTransformerFactory;
import javax.xml.transform.sax.TransformerHandler;
import javax.xml.transform.stream.StreamResult;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class HTMLGenerator {
    public static void HTMLGeneration(JSONObject jsonObj) throws IOException, TransformerConfigurationException, SAXException {
        System.out.println("LOG: Start of generating HTML file");

        String encoding = "UTF-8";
        FileOutputStream fos = new FileOutputStream("src/main/resources/result.html");
        OutputStreamWriter writer = new OutputStreamWriter(fos, encoding);
        StreamResult streamResult = new StreamResult(writer);

        SAXTransformerFactory saxFactory =
                (SAXTransformerFactory) TransformerFactory.newInstance();
        TransformerHandler tHandler = saxFactory.newTransformerHandler();
        tHandler.setResult(streamResult);

        Transformer transformer = tHandler.getTransformer();
        transformer.setOutputProperty(OutputKeys.METHOD, "html");
        transformer.setOutputProperty(OutputKeys.ENCODING, encoding);
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");

        writer.write("<!DOCTYPE html>\n");
        writer.flush();

        tHandler.startDocument();
        tHandler.startElement("", "", "html", new AttributesImpl());

        headerGeneration(tHandler, jsonObj);
        bodyGeneration(tHandler, jsonObj);

        tHandler.endElement("", "", "html");
        tHandler.endDocument();

        writer.close();
        fos.close();

        System.out.println("LOG: End of generating HTML file");
    }

    private static void headerGeneration(TransformerHandler tHandler, JSONObject jsonObj) throws SAXException {
        tHandler.startElement("", "", "head", new AttributesImpl());
        tHandler.startElement("", "", "link rel=\"stylesheet\" href=\"mystyle.css\"", new AttributesImpl());
        tHandler.startElement("", "", "title", new AttributesImpl());
        tHandler.characters(jsonObj.getString("name").toCharArray(), 0,jsonObj.getString("name").length());
        tHandler.endElement("", "", "title");
        tHandler.endElement("", "", "link");
        tHandler.endElement("", "", "head");
    }

    private static void bodyGeneration(TransformerHandler tHandler, JSONObject jsonObj) throws SAXException {
        tHandler.startElement("", "", "body", new AttributesImpl());

        commonInfoTable(tHandler, jsonObj);
        tracksTable(tHandler, jsonObj);
        streamsTable(tHandler, jsonObj);

        tHandler.endElement("", "", "body");
    }

    private static void commonInfoTable(TransformerHandler tHandler, JSONObject jsonObj) throws SAXException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);

        tHandler.startElement("", "", "table", new AttributesImpl());

        tHandler.startElement("", "", "tr", new AttributesImpl());
        tHandler.startElement("", "", "td colspan=\"2\" class=\"header\"", new AttributesImpl());
        tHandler.characters("Common info about BPMN model".toCharArray(), 0, "Common info about BPMN model".length());
        tHandler.endElement("", "", "td");
        tHandler.endElement("", "", "tr");

        List<TableInfo> commonInfo = new ArrayList<>();
        commonInfo.add(new TableInfo("Model name:", jsonObj.getString("name")));
        commonInfo.add(new TableInfo("Model author:", jsonObj.getString("author")));
        commonInfo.add(new TableInfo("Date:", jsonObj.getString("date")));

        commonInfo.forEach(info -> {
            try {
                tHandler.startElement("", "", "tr", new AttributesImpl());
                tHandler.startElement("", "", "td", new AttributesImpl());
                tHandler.characters(info.name.toCharArray(), 0, info.name.length());
                tHandler.endElement("", "", "td");
                tHandler.startElement("", "", "td", new AttributesImpl());
                tHandler.characters(info.value.toCharArray(), 0, info.value.length());
                tHandler.endElement("", "", "td");
                tHandler.endElement("", "", "tr");
            } catch (SAXException e) {
                throw new RuntimeException(e);
            }
        });

        tHandler.endElement("", "", "table");
    }

    private static void tracksTable(TransformerHandler tHandler, JSONObject jsonObj) throws SAXException {
        tHandler.startElement("", "", "table", new AttributesImpl());

        tHandler.startElement("", "", "tr", new AttributesImpl());
        tHandler.startElement("", "", "td colspan=\"2\" class=\"header\"", new AttributesImpl());
        tHandler.characters("Tracks".toCharArray(), 0, "Tracks".length());
        tHandler.endElement("", "", "td");
        tHandler.endElement("", "", "tr");

        jsonObj.getJSONArray("tracks").forEach(track -> {
            try {
                JSONObject jsonTrackItem = (JSONObject) track;

                tHandler.startElement("", "", "tr", new AttributesImpl());

                tHandler.startElement("", "", "td  class=\"track\"", new AttributesImpl());
                tHandler.characters(jsonTrackItem.getString("name").toCharArray(), 0, jsonTrackItem.getString("name").length());
                tHandler.endElement("", "", "td");

                tHandler.startElement("", "", "td", new AttributesImpl());
                if (jsonTrackItem.getJSONArray("actions").length() != 0) actionsTable(tHandler, jsonTrackItem.getJSONArray("actions"));
                if (jsonTrackItem.getJSONArray("events").length() != 0) eventsTable(tHandler, jsonTrackItem.getJSONArray("events"));
                if (jsonTrackItem.getJSONArray("forks").length() != 0) forksTable(tHandler, jsonTrackItem.getJSONArray("forks"));
                tHandler.endElement("", "", "td");

                tHandler.endElement("", "", "tr");
            } catch (SAXException e) {
                throw new RuntimeException(e);
            }
        });

         String resultTime = getResultTime(jsonObj);
        tHandler.startElement("", "", "tr class=\"result\"", new AttributesImpl());
        tHandler.startElement("", "", "td", new AttributesImpl());
        tHandler.characters("Result time".toCharArray(), 0, "Result time".length());
        tHandler.endElement("", "", "td");
        tHandler.startElement("", "", "td", new AttributesImpl());
        tHandler.characters(resultTime.toCharArray(), 0, resultTime.length());
        tHandler.endElement("", "", "td");
        tHandler.endElement("", "", "tr");

        tHandler.endElement("", "", "table");
    }

    private static String getResultTime(JSONObject jsonObj) {
        int resultTime = 0;

        for (Object track : jsonObj.getJSONArray("tracks")) {
            JSONObject jsonTrack = (JSONObject) track;
            for (Object action: jsonTrack.getJSONArray("actions")) {
                JSONObject jsonAction = (JSONObject) action;
                resultTime += jsonAction.getInt("time");
            }
        }

        return resultTime + " ms";
    }

    private static void actionsTable(TransformerHandler tHandler, JSONArray actions) throws SAXException {
        tHandler.startElement("", "", "table", new AttributesImpl());

        tHandler.startElement("", "", "tr", new AttributesImpl());
        tHandler.startElement("", "", "td colspan=\"2\" class=\"items\"", new AttributesImpl());
        tHandler.characters("Actions".toCharArray(), 0, "Actions".length());
        tHandler.endElement("", "", "td");
        tHandler.endElement("", "", "tr");

        tHandler.startElement("", "", "tr class=\"item-header\"", new AttributesImpl());
        tHandler.startElement("", "", "td", new AttributesImpl());
        tHandler.characters("Operation".toCharArray(), 0, "Operation".length());
        tHandler.endElement("", "", "td");
        tHandler.startElement("", "", "td", new AttributesImpl());
        tHandler.characters("Time".toCharArray(), 0, "Time".length());
        tHandler.endElement("", "", "td");
        tHandler.endElement("", "", "tr");

        actions.forEach(action -> {
            try {
                JSONObject jsonActionItem = (JSONObject) action;
                int time = jsonActionItem.getInt("time");

                tHandler.startElement("", "", "tr", new AttributesImpl());
                tHandler.startElement("", "", "td", new AttributesImpl());
                tHandler.characters(jsonActionItem.getString("operation").toCharArray(), 0, jsonActionItem.getString("operation").length());
                tHandler.endElement("", "", "td");
                tHandler.startElement("", "", "td", new AttributesImpl());
                tHandler.characters(Integer.toString(time).toCharArray(), 0, Integer.toString(time).length());
                tHandler.endElement("", "", "td");
                tHandler.endElement("", "", "tr");
            } catch (SAXException e) {
                throw new RuntimeException(e);
            }
        });

        tHandler.endElement("", "", "table");
    }

    private static void eventsTable(TransformerHandler tHandler, JSONArray events) throws SAXException {
        tHandler.startElement("", "", "table", new AttributesImpl());

        tHandler.startElement("", "", "tr", new AttributesImpl());
        tHandler.startElement("", "", "td colspan=\"2\" class=\"items\"", new AttributesImpl());
        tHandler.characters("Events".toCharArray(), 0, "Events".length());
        tHandler.endElement("", "", "td");
        tHandler.endElement("", "", "tr");

        tHandler.startElement("", "", "tr class=\"item-header\"", new AttributesImpl());
        tHandler.startElement("", "", "td", new AttributesImpl());
        tHandler.characters("Name".toCharArray(), 0, "Name".length());
        tHandler.endElement("", "", "td");
        tHandler.startElement("", "", "td", new AttributesImpl());
        tHandler.characters("Type".toCharArray(), 0, "Type".length());
        tHandler.endElement("", "", "td");
        tHandler.endElement("", "", "tr");

        events.forEach(event -> {
            JSONObject jsonEventItem = (JSONObject) event;
            String type =
                    Objects.equals(jsonEventItem.getString("type"), "start") ? "start" :
                            Objects.equals(jsonEventItem.getString("type"), "end") ? "end" : "intermediate";
            try {
                tHandler.startElement("", "", "tr", new AttributesImpl());
                tHandler.startElement("", "", "td", new AttributesImpl());
                tHandler.characters(jsonEventItem.getString("name").toCharArray(), 0, jsonEventItem.getString("name").length());
                tHandler.endElement("", "", "td");
                tHandler.startElement("", "", "td", new AttributesImpl());
                tHandler.characters(type.toCharArray(), 0, type.toCharArray().length);
                tHandler.endElement("", "", "td");
                tHandler.endElement("", "", "tr");
            } catch (SAXException e) {
                throw new RuntimeException(e);
            }
        });

        tHandler.endElement("", "", "table");
    }

    private static void forksTable(TransformerHandler tHandler, JSONArray forks) throws SAXException {
        tHandler.startElement("", "", "table", new AttributesImpl());

        tHandler.startElement("", "", "tr", new AttributesImpl());
        tHandler.startElement("", "", "td colspan=\"2\" class=\"items\"", new AttributesImpl());
        tHandler.characters("Forks".toCharArray(), 0, "Forks".length());
        tHandler.endElement("", "", "td");
        tHandler.endElement("", "", "tr");

        tHandler.startElement("", "", "tr class=\"item-header\"", new AttributesImpl());
        tHandler.startElement("", "", "td", new AttributesImpl());
        tHandler.characters("Condition".toCharArray(), 0, "Condition".length());
        tHandler.endElement("", "", "td");
        tHandler.endElement("", "", "tr");

        forks.forEach(fork -> {
            JSONObject jsonForkItem = (JSONObject) fork;

            try {
                tHandler.startElement("", "", "tr", new AttributesImpl());
                tHandler.startElement("", "", "td", new AttributesImpl());
                tHandler.characters(jsonForkItem.getString("condition").toCharArray(), 0, jsonForkItem.getString("condition").length());
                tHandler.endElement("", "", "td");
                tHandler.endElement("", "", "tr");
            } catch (SAXException e) {
                throw new RuntimeException(e);
            }
        });

        tHandler.endElement("", "", "table");
    }

    private static void streamsTable(TransformerHandler tHandler, JSONObject jsonObj) throws SAXException {
        tHandler.startElement("", "", "table", new AttributesImpl());

        tHandler.startElement("", "", "tr", new AttributesImpl());
        tHandler.startElement("", "", "td colspan=\"3\" class=\"header\"", new AttributesImpl());
        tHandler.characters("Streams".toCharArray(), 0, "Streams".length());
        tHandler.endElement("", "", "td");
        tHandler.endElement("", "", "tr");

        tHandler.startElement("", "", "tr class=\"item-header\"", new AttributesImpl());
        tHandler.startElement("", "", "td", new AttributesImpl());
        tHandler.characters("From".toCharArray(), 0, "From".length());
        tHandler.endElement("", "", "td");
        tHandler.startElement("", "", "td", new AttributesImpl());
        tHandler.characters("To".toCharArray(), 0, "To".length());
        tHandler.endElement("", "", "td");
        tHandler.startElement("", "", "td", new AttributesImpl());
        tHandler.characters("Name".toCharArray(), 0, "Name".length());
        tHandler.endElement("", "", "td");
        tHandler.endElement("", "", "tr");

        jsonObj.getJSONArray("streams").forEach((stream -> {
            try {
                JSONObject jsonStreamItem = (JSONObject) stream;

                String fromName = getBPMNItem(jsonStreamItem.getJSONObject("from"), jsonObj);
                String toName = getBPMNItem(jsonStreamItem.getJSONObject("to"), jsonObj);
                String streamName = jsonStreamItem.isNull("name") ? "" : jsonStreamItem.getString("name");

                tHandler.startElement("", "", "tr", new AttributesImpl());
                tHandler.startElement("", "", "td", new AttributesImpl());
                tHandler.characters(fromName.toCharArray(), 0, fromName.length());
                tHandler.endElement("", "", "td");
                tHandler.startElement("", "", "td", new AttributesImpl());
                tHandler.characters(toName.toCharArray(), 0, toName.length());
                tHandler.endElement("", "", "td");
                tHandler.startElement("", "", "td", new AttributesImpl());
                tHandler.characters(streamName.toCharArray(), 0, streamName.length());
                tHandler.endElement("", "", "td");
                tHandler.endElement("", "", "tr");
            } catch (SAXException e) {
                throw new RuntimeException(e);
            }
        }));

        tHandler.endElement("", "", "table");
    }

    private static String getBPMNItem(JSONObject point, JSONObject model) {
        String nameItem = "";

        for (Object track : model.getJSONArray("tracks")) {
            JSONObject jsonTrack = (JSONObject) track;
            switch (point.getString("type")) {
                case "action" -> {
                    for (Object action : jsonTrack.getJSONArray("actions")) {
                        JSONObject jsonAction = (JSONObject) action;
                        if (Objects.equals(jsonAction.getString("id"), point.getString("itemId")))
                            nameItem = jsonAction.getString("operation");
                    }
                }
                case "fork" -> {
                    for (Object fork : jsonTrack.getJSONArray("forks")) {
                        JSONObject jsonFork = (JSONObject) fork;
                        if (Objects.equals(jsonFork.getString("id"), point.getString("itemId")))
                            nameItem = jsonFork.getString("condition");
                    }
                }
                case "event" -> {
                    for (Object event : jsonTrack.getJSONArray("events")) {
                        JSONObject jsonEvent = (JSONObject) event;
                        if (Objects.equals(jsonEvent.getString("id"), point.getString("itemId")))
                            nameItem = jsonEvent.getString("name");
                    }
                }
                default -> throw new IllegalStateException("Unexpected value: " + point.getString("type"));
            }
        }

        return nameItem;
    }
}
