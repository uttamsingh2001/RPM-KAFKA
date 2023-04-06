package com.rpm.omronservice.service;

import com.rpm.model.Omron;
import com.rpm.model.Patient;
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
    @Autowired
    private KafkaTemplate<String, Patient> kafkaTemplate;
    @Value("${topic.name.producer}")
    private String vitalTopic;
    @Value("${integration.service.url}")
    private String integrationServiceUrl;
    @Autowired
    private RestTemplate restTemplate = new RestTemplate();

    @KafkaListener(topics = "${topic.name.consumer}", groupId = "${spring.kafka.consumer.group-id}")
    public void consume(ConsumerRecord<String, Omron> payload) {

        Omron omron = payload.value();
        log.info("Received following details from kafka broker {}", omron);

        Long userId = omron.getUserId();
        String obstermId = omron.getExternalObstermId();
        double value= omron.getValue();
        String uomCode= omron.getUomCode();
        LocalDateTime effectiveDateTime=omron.getEffectiveDateTime();
        Patient patientId = getPatientId(userId);

        // Mapping UserId to PatientId

        Patient patient=new Patient();
        patient.setPatientId(patientId.getPatientId());
        patient.setObstermId(obstermId);
        patient.setValue(value);
        patient.setUomCode(uomCode);
        patient.setEffectiveDateTime(effectiveDateTime);

        // Send the patient JSON object to the vital topic
        kafkaTemplate.send(vitalTopic, patient);
        log.info("Patient details sent to vital topic: {}", patient);
    }

         // call integration service to get patient id using By externalPatientId
        public Patient getPatientId(Long userId){
            HttpEntity<Long> entity = new HttpEntity<>(userId);
            Patient patient = restTemplate.exchange(integrationServiceUrl +"/"+ userId, HttpMethod.GET, entity, Patient.class).getBody();
            log.info(patient);

            return patient;
        }
}







