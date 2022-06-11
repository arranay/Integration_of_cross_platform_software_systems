package models;

import java.util.ArrayList;
import java.util.List;

public class Track {
    public String id;
    public String name;
    public List<Event> events = new ArrayList<>();
    public List<Action> actions = new ArrayList<>();
    public List<Fork> forks = new ArrayList<>();
}
