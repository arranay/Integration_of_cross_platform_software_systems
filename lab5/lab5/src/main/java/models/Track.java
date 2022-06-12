package models;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name = "track")
public class Track {
    private String id;
    private String name;
    private List<Event> events = new ArrayList<>();
    private List<Action> actions = new ArrayList<>();
    private List<Fork> forks = new ArrayList<>();

    @XmlAttribute
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }

    @XmlElement(name = "track-name")
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    @XmlElementWrapper(name="actions")
    @XmlElement(name="action")
    public void setActions(List<Action> actions) {
        this.actions = actions;
    }
    public List<Action> getActions() {
        return actions;
    }

    @XmlElementWrapper(name="events")
    @XmlElement(name="event", required=false)
    public void setEvents(List<Event> events) {
        this.events = events;
    }
    public List<Event> getEvents() {
        return events;
    }

    @XmlElementWrapper(name="forks")
    @XmlElement(name="fork")
    public void setForks(List<Fork> forks) {
        this.forks = forks;
    }
    public List<Fork> getForks() {
        return forks;
    }
}
