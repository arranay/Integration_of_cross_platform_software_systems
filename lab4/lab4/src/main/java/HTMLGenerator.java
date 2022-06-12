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
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static models.EventTypes.end;
import static models.EventTypes.start;

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
        tHandler.characters(model.getName().toCharArray(), 0, model.getName().length());
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
        commonInfo.add(new TableInfo("Model name:", model.getName()));
        commonInfo.add(new TableInfo("Model author:", model.getAuthor()));
        commonInfo.add(new TableInfo("Date:", formatter.format(model.getDate())));

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

        model.getTracks().forEach(track -> {
            try {
                tHandler.startElement("", "", "tr", new AttributesImpl());

                tHandler.startElement("", "", "td  class=\"track\"", new AttributesImpl());
                tHandler.characters(track.getName().toCharArray(), 0, track.getName().length());
                tHandler.endElement("", "", "td");

                tHandler.startElement("", "", "td", new AttributesImpl());
                if (track.getActions().size() != 0) actionsTable(tHandler, track.getActions());
                if (track.getEvents().size() != 0) eventsTable(tHandler, track.getEvents());
                if (track.getForks().size() != 0) forksTable(tHandler, track.getForks());
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
        for (Track track : model.getTracks()) {
            for (Action action: track.getActions()) {
                resultTime += action.getTime();
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
                tHandler.characters(action.getOperation().toCharArray(), 0, action.getOperation().length());
                tHandler.endElement("", "", "td");
                tHandler.startElement("", "", "td", new AttributesImpl());
                tHandler.characters(action.getTime().toString().toCharArray(), 0, action.getTime().toString().length());
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
            String type = event.getType() == start ? "start" : event.getType() == end ? "end" : "intermediate";
            try {
                tHandler.startElement("", "", "tr", new AttributesImpl());
                tHandler.startElement("", "", "td", new AttributesImpl());
                tHandler.characters(event.getName().toCharArray(), 0, event.getName().length());
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
                tHandler.characters(fork.getCondition().toCharArray(), 0, fork.getCondition().length());
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

        model.getStreams().forEach((stream -> {
            try {
                String fromName = getBPMNItem(stream.getFrom(), model);
                String toName = getBPMNItem(stream.getTo(), model);
                String streamName = stream.getName() == null ? "" : stream.getName();

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
        for (Track track : model.getTracks()) {
            switch (point.getType()) {
                case action -> {
                    Action action = track.getActions().stream()
                            .filter(currentAction -> point.getItemId().equals(currentAction.getId()))
                            .findAny()
                            .orElse(null);
                    if (action != null) nameItem = action.getOperation();
                }
                case fork -> {
                    Fork fork = track.getForks().stream()
                            .filter(currentFork -> point.getItemId().equals(currentFork.getId()))
                            .findAny()
                            .orElse(null);
                    if (fork != null) nameItem = fork.getCondition();
                }
                case event -> {
                    Event event = track.getEvents().stream()
                            .filter(currentEvent -> point.getItemId().equals(currentEvent.getId()))
                            .findAny()
                            .orElse(null);
                    if (event != null) nameItem = event.getName();
                }
                default -> throw new IllegalStateException("Unexpected value: " + point.getType());
            }
        }

        return nameItem;
    }
}
