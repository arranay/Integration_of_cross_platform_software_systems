package com.lab9.camel.soap;

import com.lab9.camel.model.Track;
import com.lab9.camel.soap.models.TrackServiceImplementation;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class AddTrackProcessor implements Processor {

    @Autowired
    TrackServiceImplementation trackService;

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Override
    public void process(Exchange exchange) throws Exception {
        Track track = exchange.getIn().getBody(Track.class);
        trackService.addTrack(track);

        exchange.getOut().setBody(track);
    }
}
