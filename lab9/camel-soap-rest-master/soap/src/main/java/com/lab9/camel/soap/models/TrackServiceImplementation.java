package com.lab9.camel.soap.models;

import com.lab9.camel.model.Track;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TrackServiceImplementation {

    private final TrackSessionBeanInterface bpmnSessionBean;

    public TrackServiceImplementation(TrackSessionBeanInterface bpmnSessionBean) {
        this.bpmnSessionBean = bpmnSessionBean;
    }

    public Track[] getTrack() {
        List<Track> trackList = this.bpmnSessionBean.getTracks();
        Track[] tracks = new Track[trackList.size()];
        Track[] array = trackList.toArray(tracks);
        return array;
    }

    public Track addTrack(Track track) {
        Track result = bpmnSessionBean.addTrack(track);
        return result;
    }
}
