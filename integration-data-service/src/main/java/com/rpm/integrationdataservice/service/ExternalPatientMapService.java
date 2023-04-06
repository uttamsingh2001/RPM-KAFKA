package com.rpm.integrationdataservice.service;

import com.rpm.integrationdataservice.model.ExternalPatientMapResponse;
import com.rpm.integrationdataservice.repository.ExternalPatienMapRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class ExternalPatientMapService {

    private final ExternalPatienMapRepository externalPatienMapRepository;

    public ExternalPatientMapService(ExternalPatienMapRepository externalPatienMapRepository) {
        this.externalPatienMapRepository = externalPatienMapRepository;
    }
    public ExternalPatientMapResponse getExternalPatientMap(Long externalPatientId) {
        Long patientId = externalPatienMapRepository.findPatientIdByExternalPatientId(externalPatientId);
        log.info("Patient ID: " + patientId);

        ExternalPatientMapResponse externalPatientMapResponse = new ExternalPatientMapResponse();
        externalPatientMapResponse.setPatientId(patientId);

        return externalPatientMapResponse;
    }

}
