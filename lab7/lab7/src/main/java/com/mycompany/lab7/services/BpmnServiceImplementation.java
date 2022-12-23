/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.lab7.services;
import com.mycompany.lab7.models.Track;
import jakarta.ejb.EJB;
import jakarta.jws.WebService;
import java.util.List;

/**
 *
 * @author valer
 */
@WebService(endpointInterface = "com.mycompany.lab7.services.BpmnService")
public class BpmnServiceImplementation implements BpmnService {

    @EJB
    private BpmnSessionBeanInterface bpmnSessionBean;
    
    @Override
    public boolean addTrack(Track track) {
       Track result = bpmnSessionBean.addTrack(track);
       return result != null;
    }

    @Override
    public boolean deleteTrack(String id) {
        int number = bpmnSessionBean.getTracks().size();
        List<Track> list = bpmnSessionBean.deleteTrack(id);
        return (number - list.size()) == 1;
    }

    @Override
    public Track getTrack(String id) {
        return this.bpmnSessionBean.findTrack(id);
    }

    @Override
    public Track[] getAllTracks() {
        List<Track> cityList = this.bpmnSessionBean.getTracks();
        Track[] cities = new Track[cityList.size()];
        Track[] array = cityList.toArray(cities);
        return array;
    }
    
}
