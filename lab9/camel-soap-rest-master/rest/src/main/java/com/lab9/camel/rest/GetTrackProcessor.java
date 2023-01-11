package com.lab9.camel.rest;

import com.lab9.camel.model.GetTrackResponse;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;

public class GetTrackProcessor implements Processor {

    @Autowired
    private TrackManager trackManager;

    @Override
    public void process(Exchange exchange) throws Exception {
        GetTrackResponse track = trackManager.getTrack();
        exchange.getOut().setBody(Arrays.asList(track.getTracks()));
    }
}
