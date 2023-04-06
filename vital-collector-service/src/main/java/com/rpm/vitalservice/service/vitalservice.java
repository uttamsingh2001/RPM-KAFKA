package com.rpm.vitalservice.service;

import com.rpm.model.Patient;
import lombok.extern.log4j.Log4j2;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class vitalservice {
    @KafkaListener(topics = "${topic.name.consumer}", groupId = "${spring.kafka.consumer.group-id}")
    public void consume(ConsumerRecord<String, Patient> payload) {

        Patient patient = payload.value();
        log.info("Received following details from kafka broker {}", patient);
    }

}
