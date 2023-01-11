package com.lab9.camel.rest;

import com.lab9.camel.model.Track;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class RestRouter extends RouteBuilder{
    @Override
    public void configure() throws Exception {
        restConfiguration()
                .apiProperty("api.title", "Camel REST API")
                .apiProperty("api.version", "1.0")
                .apiProperty("cors", "true")
                .bindingMode(RestBindingMode.json);

        rest("/track")
                .produces("application/json")
                .consumes("application/json")
                .get("/").route().routeId("get-track-route")
                .outputType(ArrayList.class)
                .bean(GetTrackProcessor.class);

        rest("/track")
                .produces("application/json")
                .consumes("application/json")
                .post("/").route().routeId("post-track-route")
                .inputType(Object.class)
                .outputType(Track.class)
                .bean(AddTrackProcessor.class);
    }
}
