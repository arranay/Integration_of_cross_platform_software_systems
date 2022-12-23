/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.lab7.client;

import java.util.Random;

/**
 *
 * @author valer
 */
public class Helper {
    
    public static String getAllSOAPRequest() {
        String xmlInputString = 
                """
                <?xml version="1.0" encoding="UTF-8"?><S:Envelope xmlns:S="http://schemas.xmlsoap.org/soap/envelope/" xmlns:SOAP-ENV="http://schemas.xmlsoap.org/soap/envelope/">
                    <SOAP-ENV:Header/>
                    <S:Body>
                        <ns2:getAllTracks xmlns:ns2="http://services.lab7.mycompany.com/"/>
                    </S:Body>
                </S:Envelope>""";
        
        return xmlInputString;
    }
    
    public static String getByIdSOAPRequest(String id) {
        String xmlInputString = """
                                <?xml version="1.0" encoding="UTF-8"?><S:Envelope xmlns:S="http://schemas.xmlsoap.org/soap/envelope/" xmlns:SOAP-ENV="http://schemas.xmlsoap.org/soap/envelope/">
                                    <SOAP-ENV:Header/>
                                    <S:Body>
                                        <ns2:getTrack xmlns:ns2="http://services.lab7.mycompany.com/">
                                            <arg0>"""+id+"</arg0>\n" +
                                "       </ns2:getTrack>\n" +
                                "   </S:Body>\n" +
                                "</S:Envelope>";
        
        return xmlInputString;
    }
    
    
    public static String deleteByIdSOAPRequest(String id) {
        String xmlInputString = """
                                <?xml version="1.0" encoding="UTF-8"?><S:Envelope xmlns:S="http://schemas.xmlsoap.org/soap/envelope/" xmlns:SOAP-ENV="http://schemas.xmlsoap.org/soap/envelope/">
                                    <SOAP-ENV:Header/>
                                    <S:Body>
                                        <ns2:deleteTrack xmlns:ns2="http://services.lab7.mycompany.com/">
                                            <arg0>"""+id+"</arg0>\n" +
                            "       </ns2:deleteTrack>\n" +
                            "   </S:Body>\n" +
                            "</S:Envelope>";
        
        return xmlInputString;
    }
    
    public static String createMethodSOAPRequest(String name) {
        Random random = new Random();
        String xmlInputString = 
                "<?xml version=\"1.0\" encoding=\"UTF-8\"?><S:Envelope xmlns:S=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:SOAP-ENV=\"http://schemas.xmlsoap.org/soap/envelope/\">\n" +
            "    <SOAP-ENV:Header/>\n" +
            "    <S:Body>\n" +
            "        <ns2:addTrack xmlns:ns2=\"http://services.lab7.mycompany.com/\">\n" +
            "            <arg0 id=\""+random.nextInt(100)+"\">\n" +
            "                <actions>\n" +
            "                    <action>\n" +
            "                        <id>1</id>\n" +
            "                        <operation>start</operation>\n" +
            "                        <time>12</time>\n" +
            "                    </action>\n" +
            "                </actions>\n" +
            "                <events>\n" +
            "                    <event>\n" +
            "                        <id>2</id>\n" +
            "                        <name>name</name>\n" +
            "                        <type>start</type>\n" +
            "                    </event>\n" +
            "                </events>\n" +
            "                <forks>\n" +
            "                    <fork>\n" +
            "                        <condition>condition</condition>\n" +
            "                        <id>3</id>\n" +
            "                    </fork>\n" +
            "                </forks>\n" +
            "                <track-name>"+name+"</track-name>\n" +
            "            </arg0>\n" +
            "        </ns2:addTrack>\n" +
            "    </S:Body>\n" +
            "</S:Envelope>";
        
        return xmlInputString;
    }
}
