package com.rpm.omronservice.controller;

import com.rpm.model.Omron;
import com.rpm.omronservice.service.OmronService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OmronController {

    @Autowired
    private final OmronService omronService;

    public OmronController(OmronService omronService) {
        this.omronService = omronService;
    }

    @PostMapping(path = "/v1/omron", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> sendVital(@RequestBody Omron omron){
        omronService.sendVital(omron);
        return ResponseEntity.status(HttpStatus.CREATED).body("Message published successfully");
    }
}
