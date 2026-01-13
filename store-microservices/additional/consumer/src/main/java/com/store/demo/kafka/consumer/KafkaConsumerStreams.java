package com.store.demo.kafka.consumer;

import java.util.function.Consumer;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaConsumerStreams {

	@Bean
	Consumer<RiderLocation> processRiderLocation() {
		return location -> {
			System.out.println("Received location: " + location.getRiderId() + " @ " + location.getLatitude() + " , "
					+ location.getLongitude());
		};
	}
}
