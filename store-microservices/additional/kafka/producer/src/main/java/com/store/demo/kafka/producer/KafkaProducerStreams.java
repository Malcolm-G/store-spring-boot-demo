package com.store.demo.kafka.producer;

import java.util.Random;
import java.util.function.Supplier;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.util.MimeTypeUtils;

@Configuration
public class KafkaProducerStreams {

	@Bean
	Supplier<RiderLocation> sendRiderLocation() {
		Random random = new Random();
		return () -> {
			String riderId = "rider" + random.nextInt(20);
			RiderLocation location = new RiderLocation(riderId, 28.60, 77.30);
			System.out.println("Sending: " + location.getRiderId());
			return location;
		};
	}

	/*
	 * We set a key in the header and so each message may go to a different
	 * partititon
	 */
	@Bean
	Supplier<Message<String>> sendRiderStatus() {
		Random random = new Random();
		return () -> {
			String riderId = "rider" + random.nextInt(20);
			String status = random.nextBoolean() ? "ride started" : "ride completed";
			System.out.println("Sending: " + status);
			return MessageBuilder.withPayload(riderId + ":" + status).setHeader(KafkaHeaders.KEY, riderId.getBytes())
					.setHeader(MessageHeaders.CONTENT_TYPE, MimeTypeUtils.TEXT_PLAIN).build();
		};
	}
}
