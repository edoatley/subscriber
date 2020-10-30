package com.edoatley.subscriber.kafka;

import com.edoatley.subscriber.model.CPCEvent;
import com.edoatley.subscriber.repository.CPCEventRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class CPCConsumer {

    private final Logger logger = LoggerFactory.getLogger(CPCConsumer.class);

    private final CPCEventRepository eventRepository;

    @KafkaListener(topics = "${kafka.consumer.topic.name}", groupId = "${kafka.consumer.group.name}")
    public void consume(String message) throws IOException {
        logger.info(String.format("#### -> Consumed message -> %s", message));
        ObjectMapper mapper = new ObjectMapper();
        CPCEvent event = mapper.readValue(message, CPCEvent.class);
        CPCEvent savedEvent = eventRepository.save(event);
        logger.info(String.format("#### -> Consumed message -> saved event %s", savedEvent.getEventUUID()));
    }
}
