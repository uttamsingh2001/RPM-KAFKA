package com.rpm.vitalservice.service;

import com.rpm.model.PatientObs;
import lombok.extern.log4j.Log4j2;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;


@Service
@Log4j2
public class VitalService {

    @Value("${rpm.data.service.url}")
    private String rpmDataServiceUrl;

    private final RestTemplate restTemplate;

    @Autowired
    public VitalService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @KafkaListener(topics = "${topic.name.consumer}", groupId = "${spring.kafka.consumer.group-id}")
    public void consume(ConsumerRecord<String, PatientObs> payload) {

        PatientObs patientObs = payload.value();
        log.info("Received patient observation details from Kafka broker: {}", patientObs);
        sendPatientData(patientObs);

    }

    private void sendPatientData(PatientObs patientObs) {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<PatientObs> entity = new HttpEntity<>(patientObs, headers);

        ResponseEntity<Void> response = restTemplate.exchange(rpmDataServiceUrl + "/v1/patientObs", HttpMethod.POST, entity, Void.class);

        if (response.getStatusCode().is2xxSuccessful()) {
            log.info("Patient observation data sent successfully to RPM data service");
        } else {
            log.error("Failed to send patient observation data to RPM data service. Response code: {}", response.getStatusCodeValue());
        }
    }
}
