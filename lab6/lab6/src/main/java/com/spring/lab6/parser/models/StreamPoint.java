package com.spring.lab6.parser.models;

import javax.xml.bind.annotation.XmlAttribute;

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

