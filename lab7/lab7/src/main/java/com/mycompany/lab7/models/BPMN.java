/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.lab7.models;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.util.Date;
import java.util.List;

/**
 *
 * @author valer
 */
@XmlRootElement(name = "BPMN-model")
public class BPMN {
    private String name;
    private String author;
    private Date date;
    private List<Track> tracks;
    private List<Stream> streams;

    @XmlElement(name = "model-name")
    public void setName(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }

    @XmlElement( name = "author")
    public void setAuthor(String author) {
        this.author = author;
    }
    public String getAuthor() {
        return author;
    }

    @XmlElement( name = "date")
    @XmlJavaTypeAdapter(DateAdapter.class)
    public void setDate(Date date) {
        this.date = date;
    }
    public Date getDate() {
        return date;
    }

    @XmlElementWrapper(name="tracks")
    @XmlElement(name="track")
    public void setTracks(List<Track> tracks) {
        this.tracks = tracks;
    }
    public List<Track> getTracks() {
        return tracks;
    }

    @XmlElementWrapper(name="streams")
    @XmlElement(name="stream")
    public void setStreams(List<Stream> streams) {
        this.streams = streams;
    }
    public List<Stream> getStreams() {
        return streams;
    }
}
