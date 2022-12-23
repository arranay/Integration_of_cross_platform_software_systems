/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.lab7.services;

import com.mycompany.lab7.models.Action;
import com.mycompany.lab7.models.Event;
import com.mycompany.lab7.models.EventTypes;
import com.mycompany.lab7.models.Fork;
import com.mycompany.lab7.models.Track;
import jakarta.ejb.Stateless;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author valer
 */
@Stateless
public class BpmnSessionBean implements BpmnSessionBeanInterface {

    List<Track> tracks;
    
    @Override
    public Track addTrack(Track track) {
        if (tracks == null)
            tracks = new ArrayList<>();
                    
        this.tracks.add(track);
        return findTrack(track.getId());
    }

    @Override
    public List<Track> getTracks() {
        if (tracks == null) {
            tracks = new ArrayList<>();
            Track track = createFirstTrack();
            tracks.add(track);
        }
        
        return this.tracks;
    }

    @Override
    public Track findTrack(String id) {
        for(Track track : tracks) {
            if(track.getId().equals(id)) {
                return track;
            }
        }
        
        return null;
    }

    @Override
    public List<Track> deleteTrack(String id) {
        Track track = findTrack(id);
        if (track != null){
            tracks.remove(track);
        }
        
        return getTracks();
    }
    
    private Track createFirstTrack() {
        Track newTrack = new Track();
        newTrack.setId("123");
        newTrack.setName("name");
            
        List<Event> events = new ArrayList<>();
        Event event = new Event();
        event.setId("1");
        event.setName("name");
        event.setType(EventTypes.start);
        events.add(event);
            
        List<Action> actions = new ArrayList<>();
        Action action = new Action();
        action.setId("2");
        action.setOperation("start");
        action.setTime(12);
        actions.add(action);
            
        List<Fork> forks = new ArrayList<>();
        Fork fork = new Fork();
        fork.setCondition("condition");
        fork.setId("3");
        forks.add(fork);
            
        newTrack.setEvents(events);
        newTrack.setActions(actions);
        newTrack.setForks(forks);
    
        return newTrack;  
    }
}
