import models.*;

import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class XMLParser {
    public static BPMN parseFile(File path) throws FileNotFoundException, XMLStreamException, ParseException {
        System.out.println("LOG: Start of parsing");

        XMLInputFactory xmlInputFactory = XMLInputFactory.newInstance();
        XMLEventReader reader = xmlInputFactory.createXMLEventReader(new FileInputStream(path));

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);

        BPMN model = new BPMN();

        while (reader.hasNext()) {
            XMLEvent nextEvent = reader.nextEvent();
            if (nextEvent.isStartElement()) {
                StartElement startElement = nextEvent.asStartElement();
                switch (startElement.getName().getLocalPart()) {
                    case "model-name" -> {
                        nextEvent = reader.nextEvent();
                        model.name = nextEvent.asCharacters().getData();
                    }
                    case "author" -> {
                        nextEvent = reader.nextEvent();
                        model.author = nextEvent.asCharacters().getData();
                    }
                    case "date" -> {
                        nextEvent = reader.nextEvent();
                        model.date = formatter.parse(nextEvent.asCharacters().getData());
                    }
                    case "tracks" -> model.tracks = parsTracks(reader);
                    case "streams" -> model.streams = parsStreams(reader);
                }
            }

            if (nextEvent.isEndElement()) {
                EndElement endElement = nextEvent.asEndElement();
                if (endElement.getName().getLocalPart().equals("BPMN-model")) {
                    System.out.println("LOG: End of parsing");
                }
            }
        }

        return model;
    }

    private static List<Track> parsTracks(XMLEventReader reader) throws XMLStreamException {
        List<Track> trackList = new ArrayList<>();
        Track track = new Track();

        while (reader.hasNext()) {
            XMLEvent nextEvent = reader.nextEvent();

            if (nextEvent.isStartElement()) {
                StartElement startElement = nextEvent.asStartElement();
                switch (startElement.getName().getLocalPart()) {
                    case "track" -> {
                        Attribute type = startElement.getAttributeByName(QName.valueOf("id"));
                        track.id = type.getValue();
                    }
                    case "track-name" -> {
                        nextEvent = reader.nextEvent();
                        track.name = nextEvent.asCharacters().getData();
                    }
                    case "events" -> track.events = parsEvents(reader);
                    case "actions" -> track.actions = parsActions(reader);
                    case "forks" -> track.forks = parsForks(reader);
                }
            }

            if (nextEvent.isEndElement()) {
                EndElement endElement = nextEvent.asEndElement();
                if (endElement.getName().getLocalPart().equals("tracks")) {
                    return trackList;

                }
                if (endElement.getName().getLocalPart().equals("track")) {
                    trackList.add(track);
                    track = new Track();
                }
            }
        }

        return trackList;
    }

    private static List<Event> parsEvents(XMLEventReader reader) throws XMLStreamException {
        List<Event> eventList = new ArrayList<>();
        Event event = new Event();

        while (reader.hasNext()) {
            XMLEvent nextEvent = reader.nextEvent();

            if (nextEvent.isStartElement()) {
                StartElement startElement = nextEvent.asStartElement();
                switch (startElement.getName().getLocalPart()) {
                    case "event" -> {
                        Attribute type = startElement.getAttributeByName(QName.valueOf("type"));
                        event.type = type.getValue();
                        Attribute id = startElement.getAttributeByName(QName.valueOf("id"));
                        event.id = id.getValue();
                    }
                    case "event-name" -> {
                        nextEvent = reader.nextEvent();
                        event.name = nextEvent.asCharacters().getData();
                    }
                }
            }

            if (nextEvent.isEndElement()) {
                EndElement endElement = nextEvent.asEndElement();
                if (endElement.getName().getLocalPart().equals("events")) {
                    return eventList;

                }
                if (endElement.getName().getLocalPart().equals("event")) {
                    eventList.add(event);
                    event = new Event();
                }
            }
        }

        return eventList;
    }

    private static List<Action> parsActions(XMLEventReader reader) throws XMLStreamException {
        List<Action> actionList = new ArrayList<>();
        Action action = new Action();

        while (reader.hasNext()) {
            XMLEvent nextEvent = reader.nextEvent();

            if (nextEvent.isStartElement()) {
                StartElement startElement = nextEvent.asStartElement();
                switch (startElement.getName().getLocalPart()) {
                    case "action" -> {
                        Attribute id = startElement.getAttributeByName(QName.valueOf("id"));
                        action.id = id.getValue();
                    }
                    case "operation" -> {
                        nextEvent = reader.nextEvent();
                        action.operation = nextEvent.asCharacters().getData();
                    }
                    case "time" -> {
                        nextEvent = reader.nextEvent();
                        action.time = Integer.parseInt(nextEvent.asCharacters().getData());
                    }
                }
            }

            if (nextEvent.isEndElement()) {
                EndElement endElement = nextEvent.asEndElement();
                if (endElement.getName().getLocalPart().equals("actions")) {
                    return actionList;

                }
                if (endElement.getName().getLocalPart().equals("action")) {
                    actionList.add(action);
                    action = new Action();
                }
            }
        }

        return actionList;
    }

    private static List<Fork> parsForks(XMLEventReader reader) throws XMLStreamException {
        List<Fork> forkList = new ArrayList<>();
        Fork fork = new Fork();

        while (reader.hasNext()) {
            XMLEvent nextEvent = reader.nextEvent();

            if (nextEvent.isStartElement()) {
                StartElement startElement = nextEvent.asStartElement();
                switch (startElement.getName().getLocalPart()) {
                    case "fork":
                        Attribute id = startElement.getAttributeByName(QName.valueOf("id"));
                        fork.id = id.getValue();
                        break;
                    case "condition":
                        nextEvent = reader.nextEvent();
                        fork.condition = nextEvent.asCharacters().getData();
                        break;
                }
            }

            if (nextEvent.isEndElement()) {
                EndElement endElement = nextEvent.asEndElement();
                if (endElement.getName().getLocalPart().equals("forks")) {
                    return forkList;

                }
                if (endElement.getName().getLocalPart().equals("fork")) {
                    forkList.add(fork);
                    fork = new Fork();
                }
            }
        }

        return forkList;
    }

    private static List<Stream> parsStreams(XMLEventReader reader) throws XMLStreamException {
        List<Stream> streamList = new ArrayList<>();
        Stream stream = new Stream();

        while (reader.hasNext()) {
            XMLEvent nextEvent = reader.nextEvent();

            if (nextEvent.isStartElement()) {
                StartElement startElement = nextEvent.asStartElement();
                switch (startElement.getName().getLocalPart()) {
                    case "stream":
                        Attribute id = startElement.getAttributeByName(QName.valueOf("id"));
                        stream.id = id.getValue();
                        break;
                    case "from":
                        stream.from = getStreamItem(startElement);
                        break;
                    case "to":
                        stream.to = getStreamItem(startElement);
                        break;
                    case "stream-name":
                        nextEvent = reader.nextEvent();
                        stream.name = nextEvent.asCharacters().getData();
                        break;
                }
            }

            if (nextEvent.isEndElement()) {
                EndElement endElement = nextEvent.asEndElement();
                if (endElement.getName().getLocalPart().equals("streams")) {
                    return streamList;

                }
                if (endElement.getName().getLocalPart().equals("stream")) {
                    streamList.add(stream);
                    stream = new Stream();
                }
            }
        }

        return streamList;
    }

    private static StreamPoint getStreamItem(StartElement startElement) {
        Attribute id = startElement.getAttributeByName(QName.valueOf("id"));
        Attribute type = startElement.getAttributeByName(QName.valueOf("type"));
        Attribute itemId = startElement.getAttributeByName(QName.valueOf("item-id"));

        StreamPoint point = new StreamPoint();
        point.id = id.getValue();
        point.type = PointTypes.valueOf(type.getValue());
        point.itemId = itemId.getValue();
        return point;
    }
}
