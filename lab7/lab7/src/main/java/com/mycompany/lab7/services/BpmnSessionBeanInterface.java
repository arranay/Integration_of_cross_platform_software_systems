/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.lab7.services;

import com.mycompany.lab7.models.Track;
import jakarta.ejb.Local;
import java.util.List;

/**
 *
 * @author valer
 */
@Local
public interface BpmnSessionBeanInterface {
    public Track addTrack(Track track);
    public List<Track> getTracks();
    public Track findTrack(String id);
    public List<Track> deleteTrack(String id);
}
