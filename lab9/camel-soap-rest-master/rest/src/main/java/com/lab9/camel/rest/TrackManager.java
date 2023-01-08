package com.lab9.camel.rest;

import com.lab9.camel.model.GetTrackResponse;
import com.lab9.camel.model.Track;
import com.lab9.camel.model.TrackService;
import org.apache.cxf.frontend.ClientProxyFactoryBean;
import org.apache.cxf.interceptor.LoggingInInterceptor;
import org.apache.cxf.interceptor.LoggingOutInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class TrackManager {

    @Value("${user.soap.address}")
    private String address;

    private TrackService trackService;

    @PostConstruct
    private void setUp(){
        ClientProxyFactoryBean factoryBean = new ClientProxyFactoryBean();
        factoryBean.setAddress(address);
        factoryBean.setServiceClass(TrackService.class);
        factoryBean.getInInterceptors().add(new LoggingInInterceptor());
        factoryBean.getOutInterceptors().add(new LoggingOutInterceptor());

        trackService = factoryBean.create(TrackService.class);
    }

    public GetTrackResponse getTrack() {
        return trackService.getTrack();
    }

    public Track addTrack(Track track) {
        return trackService.addTrack(track);
    }
}
