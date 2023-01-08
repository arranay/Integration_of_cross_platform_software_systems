package com.lab9.camel.soap.models;

import com.lab9.camel.model.Track;

import java.util.List;

public interface TrackSessionBeanInterface {
    public Track addTrack(Track track);
    public List<Track> getTracks();
    public Track findTrack(String id);
    public List<Track> deleteTrack(String id);
}
