package com.lab9.camel.rest;

import com.lab9.camel.model.GetTrackResponse;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.http.common.HttpMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpSession;

public class GetTrackProcessor implements Processor {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private TrackManager trackManager;

    @Override
    public void process(Exchange exchange) throws Exception {
        GetTrackResponse track = trackManager.getTrack();
        exchange.getOut().setBody(track);
    }
}
