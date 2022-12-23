/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.lab7.models;
import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author valer
 */
public class DateAdapter extends XmlAdapter<String, Date> {
    private static final String CUSTOM_FORMAT_STRING = "yyyy-MM-dd";

    @Override
    public String marshal(Date v) {
        return new SimpleDateFormat(CUSTOM_FORMAT_STRING).format(v);
    }

    @Override
    public Date unmarshal(String v) throws ParseException {
        return new SimpleDateFormat(CUSTOM_FORMAT_STRING).parse(v);
    }
}