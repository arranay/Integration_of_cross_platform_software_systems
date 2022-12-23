package com.spring.lab6.rest.controllers;

import com.spring.lab6.parser.models.BPMN;
import com.spring.lab6.parser.models.Stream;
import com.spring.lab6.parser.service.XMLParser;
import com.spring.lab6.rest.models.Error;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class BPMNController {

    @GetMapping(path = "/bpmn", produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> getBPMNModel() {
        try {
            BPMN model = XMLParser.parseFile();
            return new ResponseEntity<Object>(model, HttpStatus.OK);
        } catch (Exception e) {
            System.out.println("ERROR: " + e);
            Error error = new Error(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
            return new ResponseEntity<Object>(error, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(path = "/bpmn/streams", produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> getStreams() {
        try {
            BPMN model = XMLParser.parseFile();
            List<Stream> streams = model.getStreams();

            return new ResponseEntity<Object>(streams, HttpStatus.OK);
        } catch (Exception e) {
            System.out.println("ERROR: " + e);
            Error error = new Error(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
            return new ResponseEntity<Object>(error, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(path = "/bpmn/streams/{streamId}", produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> getStreamsById(@PathVariable String streamId) {
        try {
            BPMN model = XMLParser.parseFile();
            List<Stream> streams = model.getStreams();
            Stream stream = new Stream();

            streams.forEach(s -> {
                if (s.getId().equals(streamId)) {
                    stream.setId(s.getId());
                    stream.setFrom(s.getFrom());
                    stream.setTo(s.getTo());
                    stream.setName(s.getName());
                }
            });

            if (stream.getId() == null) {
                Error error = new Error(HttpStatus.NOT_FOUND, "Stream not found");
                return new ResponseEntity<Object>(error, HttpStatus.NOT_FOUND);
            }

            return new ResponseEntity<Object>(stream, HttpStatus.OK);
        } catch (Exception e) {
            System.out.println("ERROR: " + e);
            Error error = new Error(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
            return new ResponseEntity<Object>(error, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
