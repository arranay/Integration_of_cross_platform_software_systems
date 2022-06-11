package models;

public class Event {
    public String id;
    public String name;
    public String type;
}

enum EventTypes{
    start,
    intermediate,
    end,
}