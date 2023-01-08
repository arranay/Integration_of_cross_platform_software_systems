package com.lab9.camel.soap;

import com.lab9.camel.model.TrackService;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class SoapRouter extends RouteBuilder {

    private String url = "cxf:track?serviceClass=" + TrackService.class.getName();

    @Override
    public void configure() throws Exception {
        from(url).routeId("track-soap")
                .to("log:input")
                .recipientList(simple("direct:${header.operationName}"));

       from("direct:getTrack")
                .bean(GetTrackProcessor.class)
                .to("log:output");

       from("direct:addTrack")
               .bean(AddTrackProcessor.class)
               .to("log:output");
    }
}
