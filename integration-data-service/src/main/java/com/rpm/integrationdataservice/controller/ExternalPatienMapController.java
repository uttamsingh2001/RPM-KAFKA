package com.rpm.integrationdataservice.controller;

import com.rpm.integrationdataservice.model.ExternalPatientMapResponse;
import com.rpm.integrationdataservice.service.ExternalPatientMapService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ExternalPatienMapController {
    private final ExternalPatientMapService externalPatientMapService;
    public ExternalPatienMapController(ExternalPatientMapService externalPatientMapService) {
        this.externalPatientMapService = externalPatientMapService;
    }

    @GetMapping(path = "/externalPatientMaps/{externalPatientId}")
    public ResponseEntity<ExternalPatientMapResponse> getExternalPatientMap(@PathVariable Long externalPatientId) {
        ExternalPatientMapResponse entity = externalPatientMapService.getExternalPatientMap(externalPatientId);
        return new ResponseEntity<>(entity, HttpStatus.OK);
    }


}
