package com.store.demo.kafka.producer;

import java.util.function.Supplier;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaProducerStreams {

	@Bean
	Supplier<RiderLocation> sendRiderLocation() {
		return () -> {
			RiderLocation location = new RiderLocation("rider123", 28.60, 77.30);
			System.out.println("Sending: " + location.getRiderId());
			return location;
		};
	}
}
