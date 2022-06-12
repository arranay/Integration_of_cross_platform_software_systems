package models;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Stream {
    private String id;
    private StreamPoint from;
    private StreamPoint to;
    private String name;

    @XmlAttribute
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }

    @XmlElement
    public StreamPoint getFrom() {
        return from;
    }
    public void setFrom(StreamPoint from) {
        this.from = from;
    }

    @XmlElement
    public StreamPoint getTo() {
        return to;
    }
    public void setTo(StreamPoint to) {
        this.to = to;
    }

    @XmlElement(name = "stream-name")
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
}

