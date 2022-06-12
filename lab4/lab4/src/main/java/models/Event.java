package models;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Event {
    private String id;
    private String name;
    private EventTypes type;

    @XmlAttribute
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }

    @XmlAttribute
    public EventTypes getType() {
        return type;
    }
    public void setType(EventTypes type) {
        this.type = type;
    }

    @XmlElement(name = "event-name")
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
}

