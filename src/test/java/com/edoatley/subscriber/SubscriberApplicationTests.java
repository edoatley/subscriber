package com.edoatley.subscriber;

import com.edoatley.subscriber.kafka.CPCProducer;
import com.edoatley.subscriber.model.CPCEvent;
import com.edoatley.subscriber.repository.CPCEventRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.test.context.EmbeddedKafka;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.awaitility.Awaitility.await;

@SpringBootTest(properties = "spring.kafka.bootstrap-servers=${spring.embedded.kafka.brokers}")
@EnableKafka
@EmbeddedKafka(
		partitions = 1,
		controlledShutdown = false,
		brokerProperties = {
				"listeners=PLAINTEXT://localhost:3333",
				"port=3333"
		})
@Import(TestMongoConfiguration.class)
class SubscriberApplicationTests {
	private static final Logger LOGGER = LoggerFactory.getLogger(SubscriberApplicationTests.class);

	@Autowired
	CPCProducer producer;
    @Autowired
	CPCEventRepository eventRepository;

	@Test
	void publishedMessageReadByConsumer() throws JsonProcessingException {
        // given
		CPCEvent event = CPCEvent.builder()
				.eventUUID(UUID.randomUUID().toString())
				.eventType("build")
				.eventPayload("Successful build event")
				.build();
        String eventString = new ObjectMapper().writeValueAsString(event);

        // when
        producer.sendMessage(eventString);
        producer.getKafkaTemplate().flush();

        // then
		await().atMost(5, TimeUnit.SECONDS)
			   .untilAsserted(()-> assertThat(eventRepository.findAll()).hasSize(1));
	}

}
