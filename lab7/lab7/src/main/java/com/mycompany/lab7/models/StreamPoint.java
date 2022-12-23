/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.lab7.models;
import javax.xml.bind.annotation.XmlAttribute;

/**
 *
 * @author valer
 */
public class StreamPoint {
    private String id;
    private PointTypes type;
    private String itemId;

    @XmlAttribute
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }

    @XmlAttribute(name="type")
    public PointTypes getType() {
        return type;
    }
    public void setType(PointTypes type) {
        this.type = type;
    }

    @XmlAttribute(name="item-id")
    public String getItemId() {
        return itemId;
    }
    public void setItemId(String itemId) {
        this.itemId = itemId;
    }
}
