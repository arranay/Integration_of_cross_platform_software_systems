package com.lab9.camel.model;

import java.util.ArrayList;
import java.util.List;

public class Track {
    private String id;
    private String name;
    private List<Event> events = new ArrayList<>();
    private List<Action> actions = new ArrayList<>();
    private List<Fork> forks = new ArrayList<>();

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

    public void setActions(List<Action> actions) {
        this.actions = actions;
    }
    public List<Action> getActions() {
        return actions;
    }

    public void setEvents(List<Event> events) {
        this.events = events;
    }
    public List<Event> getEvents() {
        return events;
    }

    public void setForks(List<Fork> forks) {
        this.forks = forks;
    }
    public List<Fork> getForks() {
        return forks;
    }
}