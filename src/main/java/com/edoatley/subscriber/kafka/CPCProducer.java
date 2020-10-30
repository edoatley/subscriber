package com.edoatley.subscriber.kafka;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Getter
public class CPCProducer {
    private static final Logger logger = LoggerFactory.getLogger(CPCProducer.class);

    @Value("${kafka.producer.topic.name}")
    private String producerTopic;

    private final KafkaTemplate<String, String> kafkaTemplate;

    public void sendMessage(String message) {
        logger.info(String.format("#### -> Producing message -> %s", message));
        this.kafkaTemplate.send(producerTopic, message);
    }
}
