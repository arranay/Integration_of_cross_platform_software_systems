/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.lab7.client;

import static com.mycompany.lab7.client.Helper.*;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 *
 * @author valer
 */
public class Service {

    public static String getAllTracks() throws MalformedURLException, IOException {
        String xmlInputString = getAllSOAPRequest();
        String outputString = sendSOAPRequest(xmlInputString, "getAllTracks"); 
        return outputString;
    }
    
    public static String getTrackById(String id) throws MalformedURLException, IOException {
        String xmlInputString = getByIdSOAPRequest(id);
        String outputString = sendSOAPRequest(xmlInputString, "getTrack"); 
        return outputString;
    }
    
    public static String deleteTrackById(String id) throws MalformedURLException, IOException {
        String xmlInputString = deleteByIdSOAPRequest(id);
        String outputString = sendSOAPRequest(xmlInputString, "deleteTrack"); 
        return outputString;
    }
    
    public static String cfreateNewTrack(String name) throws MalformedURLException, IOException {
        String xmlInputString = createMethodSOAPRequest(name);
        String outputString = sendSOAPRequest(xmlInputString, "addTrack"); 
        return outputString;
    }
    
    private static String sendSOAPRequest(String xmlInputString, String method) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byteArrayOutputStream.write(xmlInputString.getBytes());
        byte[] bytes = byteArrayOutputStream.toByteArray();
        
        HttpURLConnection httpURLConnection = getConnection(method, bytes);
        try (OutputStream outputStream = httpURLConnection.getOutputStream()) {
            outputStream.write(bytes);
        }
        
        InputStreamReader inputStreamReader = new InputStreamReader(httpURLConnection.getInputStream());
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

        String responseString;
        StringBuilder outputString = new StringBuilder();
        
        while ((responseString = bufferedReader.readLine()) != null) {
            outputString.append(responseString);
        }

        return outputString.toString();
    }
    
    private static HttpURLConnection getConnection(String method, byte[] bytes) throws MalformedURLException, IOException {
        URL url = new URL("http://localhost:8080/tracks/BpmnServiceImplementationService");
        URLConnection connection = url.openConnection();
        
        HttpURLConnection httpURLConnection = (HttpURLConnection)connection;
        httpURLConnection.setRequestProperty("Content-Type", "text/xml; charset=utf-8");   
        httpURLConnection.setRequestMethod("POST");
        httpURLConnection.setRequestProperty("Content-Length", String.valueOf(bytes.length));
        httpURLConnection.setRequestProperty("SOAPAction", method);
        httpURLConnection.setDoOutput(true);
        httpURLConnection.setDoInput(true);
        
        return httpURLConnection;
    }
    
}
