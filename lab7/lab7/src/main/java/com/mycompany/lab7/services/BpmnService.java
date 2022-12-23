/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.lab7.services;
import com.mycompany.lab7.models.Track;
import jakarta.jws.WebMethod;
import jakarta.jws.WebService;
import jakarta.jws.soap.SOAPBinding;

/**
 *
 * @author valer
 */
@WebService
@SOAPBinding(style = SOAPBinding.Style.RPC)
public interface BpmnService {
    @WebMethod
    public boolean addTrack(Track track);
	
    @WebMethod
    public boolean deleteTrack(String id);
	
    @WebMethod
    public Track getTrack(String id);
	
    @WebMethod
    public Track[] getAllTracks();
}
