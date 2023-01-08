package com.lab9.camel.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lab9.camel.model.Track;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.http.common.HttpMessage;
import org.springframework.beans.factory.annotation.Autowired;

public class AddTrackProcessor implements Processor {

    @Autowired
    private TrackManager trackManager;

    @Override
    public void process(Exchange exchange) throws Exception {
        Object body = exchange.getIn(HttpMessage.class).getBody();

        ObjectMapper mapper = new ObjectMapper();
        Track track = mapper.convertValue(body, Track.class);
        Track created_track = trackManager.addTrack(track);

        exchange.getOut().setBody(created_track);
    }
}
