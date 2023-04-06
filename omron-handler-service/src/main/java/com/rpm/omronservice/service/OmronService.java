package com.rpm.omronservice.service;

import com.rpm.model.Omron;
import com.rpm.omronservice.config.KafkaTopicConfig;
import lombok.extern.log4j.Log4j2;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class OmronService {

    private final KafkaTemplate<String, Omron> kafkaTemplate;
    private final KafkaTopicConfig kafkaTopicConfig;
    public OmronService(KafkaTemplate<String, Omron> kafkaTemplate, KafkaTopicConfig kafkaTopicConfig) {
        this.kafkaTemplate = kafkaTemplate;
        this.kafkaTopicConfig = kafkaTopicConfig;
    }

    public void sendVital(Omron omron) {
        Message<Omron> message = MessageBuilder
                .withPayload(omron)
                .setHeader(KafkaHeaders.TOPIC,kafkaTopicConfig.topicName)
                .build();

        kafkaTemplate.send(message);
        log.info("External details sent to omron topic: {}" + message);

    }

}






