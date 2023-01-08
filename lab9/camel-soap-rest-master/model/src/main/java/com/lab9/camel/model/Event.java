package com.lab9.camel.model;

public class Event {
    private String id;
    private String name;
    private EventTypes type;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public EventTypes getType() {
        return type;
    }

    public void setType(EventTypes type) {
        this.type = type;
    }
}
