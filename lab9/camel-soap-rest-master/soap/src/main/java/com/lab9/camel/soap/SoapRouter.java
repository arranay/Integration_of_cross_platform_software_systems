package com.lab9.camel.soap;

import com.lab9.camel.model.TrackService;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class SoapRouter extends RouteBuilder {

    private String url = "cxf:track?serviceClass=" + TrackService.class.getName();

    @Override
    public void configure() throws Exception {
        from(url).routeId("track-soap")
                .recipientList(simple("direct:${header.operationName}"));

       from("direct:getTrack")
                .bean(GetTrackProcessor.class);

       from("direct:addTrack")
               .bean(AddTrackProcessor.class);
    }
}
