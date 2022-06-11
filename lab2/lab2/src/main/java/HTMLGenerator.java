import models.*;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.sax.SAXTransformerFactory;
import javax.xml.transform.sax.TransformerHandler;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class HTMLGenerator {
    public static void HTMLGeneration(BPMN model) throws IOException, TransformerConfigurationException, SAXException {
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

        headerGeneration(tHandler, model);
        bodyGeneration(tHandler, model);

        tHandler.endElement("", "", "html");
        tHandler.endDocument();

        writer.close();
        fos.close();

        System.out.println("LOG: End of generating HTML file");
    }

    private static void headerGeneration(TransformerHandler tHandler, BPMN model) throws SAXException {
        tHandler.startElement("", "", "head", new AttributesImpl());
        tHandler.startElement("", "", "link rel=\"stylesheet\" href=\"mystyle.css\"", new AttributesImpl());
        tHandler.startElement("", "", "title", new AttributesImpl());
        tHandler.characters(model.name.toCharArray(), 0, model.name.length());
        tHandler.endElement("", "", "title");
        tHandler.endElement("", "", "link");
        tHandler.endElement("", "", "head");
    }

    private static void bodyGeneration(TransformerHandler tHandler, BPMN model) throws SAXException {
        tHandler.startElement("", "", "body", new AttributesImpl());

        commonInfoTable(tHandler, model);
        tracksTable(tHandler, model);
        streamsTable(tHandler, model);

        tHandler.endElement("", "", "body");
    }

    private static void commonInfoTable(TransformerHandler tHandler, BPMN model) throws SAXException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);

        tHandler.startElement("", "", "table", new AttributesImpl());

        tHandler.startElement("", "", "tr", new AttributesImpl());
        tHandler.startElement("", "", "td colspan=\"2\" class=\"header\"", new AttributesImpl());
        tHandler.characters("Common info about BPMN model".toCharArray(), 0, "Common info about BPMN model".length());
        tHandler.endElement("", "", "td");
        tHandler.endElement("", "", "tr");

        List<TableInfo> commonInfo = new ArrayList<>();
        commonInfo.add(new TableInfo("Model name:", model.name));
        commonInfo.add(new TableInfo("Model author:", model.author));
        commonInfo.add(new TableInfo("Date:", formatter.format(model.date)));

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

    private static void tracksTable(TransformerHandler tHandler, BPMN model) throws SAXException {
        tHandler.startElement("", "", "table", new AttributesImpl());

        tHandler.startElement("", "", "tr", new AttributesImpl());
        tHandler.startElement("", "", "td colspan=\"2\" class=\"header\"", new AttributesImpl());
        tHandler.characters("Tracks".toCharArray(), 0, "Tracks".length());
        tHandler.endElement("", "", "td");
        tHandler.endElement("", "", "tr");

        model.tracks.forEach(track -> {
            try {
                tHandler.startElement("", "", "tr", new AttributesImpl());

                tHandler.startElement("", "", "td  class=\"track\"", new AttributesImpl());
                tHandler.characters(track.name.toCharArray(), 0, track.name.length());
                tHandler.endElement("", "", "td");

                tHandler.startElement("", "", "td", new AttributesImpl());
                if (track.actions.size() != 0) actionsTable(tHandler, track.actions);
                if (track.events.size() != 0) eventsTable(tHandler, track.events);
                if (track.forks.size() != 0) forksTable(tHandler, track.forks);
                tHandler.endElement("", "", "td");

                tHandler.endElement("", "", "tr");
            } catch (SAXException e) {
                throw new RuntimeException(e);
            }
        });

        String resultTime = getResultTime(model);
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

    private static String getResultTime(BPMN model) {
        Integer resultTime = 0;
        for (Track track : model.tracks) {
            for (Action action: track.actions) {
                resultTime += action.time;
            }
        }

        return resultTime.toString()+" ms";
    }

    private static void actionsTable(TransformerHandler tHandler, List<Action> actions) throws SAXException {
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
                tHandler.startElement("", "", "tr", new AttributesImpl());
                tHandler.startElement("", "", "td", new AttributesImpl());
                tHandler.characters(action.operation.toCharArray(), 0, action.operation.length());
                tHandler.endElement("", "", "td");
                tHandler.startElement("", "", "td", new AttributesImpl());
                tHandler.characters(action.time.toString().toCharArray(), 0, action.time.toString().length());
                tHandler.endElement("", "", "td");
                tHandler.endElement("", "", "tr");
            } catch (SAXException e) {
                throw new RuntimeException(e);
            }
        });

        tHandler.endElement("", "", "table");
    }

    private static void eventsTable(TransformerHandler tHandler, List<Event> events) throws SAXException {
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
            try {
                tHandler.startElement("", "", "tr", new AttributesImpl());
                tHandler.startElement("", "", "td", new AttributesImpl());
                tHandler.characters(event.name.toCharArray(), 0, event.name.length());
                tHandler.endElement("", "", "td");
                tHandler.startElement("", "", "td", new AttributesImpl());
                tHandler.characters(event.type.toString().toCharArray(), 0, event.type.toString().length());
                tHandler.endElement("", "", "td");
                tHandler.endElement("", "", "tr");
            } catch (SAXException e) {
                throw new RuntimeException(e);
            }
        });

        tHandler.endElement("", "", "table");
    }

    private static void forksTable(TransformerHandler tHandler, List<Fork> forks) throws SAXException {
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
            try {
                tHandler.startElement("", "", "tr", new AttributesImpl());
                tHandler.startElement("", "", "td", new AttributesImpl());
                tHandler.characters(fork.condition.toCharArray(), 0, fork.condition.length());
                tHandler.endElement("", "", "td");
                tHandler.endElement("", "", "tr");
            } catch (SAXException e) {
                throw new RuntimeException(e);
            }
        });

        tHandler.endElement("", "", "table");
    }

    private static void streamsTable(TransformerHandler tHandler, BPMN model) throws SAXException {
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

        model.streams.forEach((stream -> {
            try {
                String fromName = getBPMNItem(stream.from, model);
                String toName = getBPMNItem(stream.to, model);
                String streamName = stream.name == null ? "" : stream.name;

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

    private static String getBPMNItem(StreamPoint point, BPMN model) {
        String nameItem = "";
        for (Track track : model.tracks) {
            switch (point.type) {
                case action -> {
                    Action action = track.actions.stream()
                            .filter(currentAction -> point.itemId.equals(currentAction.id))
                            .findAny()
                            .orElse(null);
                    if (action != null) nameItem = action.operation;
                }
                case fork -> {
                    Fork fork = track.forks.stream()
                            .filter(currentFork -> point.itemId.equals(currentFork.id))
                            .findAny()
                            .orElse(null);
                    if (fork != null) nameItem = fork.condition;
                }
                case event -> {
                    Event event = track.events.stream()
                            .filter(currentEvent -> point.itemId.equals(currentEvent.id))
                            .findAny()
                            .orElse(null);
                    if (event != null) nameItem = event.name;
                }
                default -> throw new IllegalStateException("Unexpected value: " + point.type);
            }
        }

        return nameItem;
    }
}
