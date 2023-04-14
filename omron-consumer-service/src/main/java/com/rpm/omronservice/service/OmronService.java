package com.rpm.omronservice.service;

import com.rpm.model.Omron;
import com.rpm.model.PatientObs;
import lombok.extern.log4j.Log4j2;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;

@Service
@Log4j2
public class OmronService {

    private final KafkaTemplate<String, PatientObs> kafkaTemplate;
    private final RestTemplate restTemplate;
    private final String integrationServiceUrl;
    @Value("${topic.name.producer}")
    private String topicName;

    @Autowired
    public OmronService(KafkaTemplate<String, PatientObs> kafkaTemplate,
                        RestTemplate restTemplate,
                        @Value("${integration.service.url}") String integrationServiceUrl)
                       {
        this.kafkaTemplate = kafkaTemplate;
        this.restTemplate = restTemplate;
        this.integrationServiceUrl = integrationServiceUrl;

    }

    @KafkaListener(topics = "${topic.name.consumer}", groupId = "${spring.kafka.consumer.group-id}")
    public void consume(ConsumerRecord<String, Omron> payload) {
        Omron omron = payload.value();
        log.info("Received following details from Kafka broker {}", omron);

        Long userId = omron.getUserId();
        Long obstermId = omron.getExternalObstermId();
        Double value = omron.getValue();
        String uomCode = omron.getUomCode();
        LocalDateTime effectiveDateTime = omron.getEffectiveDateTime();
        Long patientId = getPatientId(userId);

        PatientObs patientObs = new PatientObs();
        patientObs.setPatientId(patientId);
        patientObs.setObstermId(obstermId);
        patientObs.setValue(value);
        patientObs.setUomCode(uomCode);
        patientObs.setEffectiveDateTime(effectiveDateTime);

        try {
            kafkaTemplate.send(topicName, patientObs);
            log.info("Patient details sent to vital topic: {}", patientObs);
        } catch (Exception e) {
            log.error("Broker is down: {}", e.getMessage());
        }
    }

    private Long getPatientId(Long userId) {
        HttpEntity<Long> entity = new HttpEntity<>(userId);
        PatientObs patientObs = restTemplate.exchange(
                integrationServiceUrl + "/" + userId,
                HttpMethod.GET,
                entity,
                PatientObs.class).getBody();
        if (patientObs != null) {
            return patientObs.getPatientId();
        } else {
            throw new RuntimeException("Failed to retrieve patient ID from integration service");
        }
    }
}
