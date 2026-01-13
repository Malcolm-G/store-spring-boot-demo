package com.store.demo.kafka.producer;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaTopicConfig {

	@Bean
	NewTopic createMyTopic() {
		return new NewTopic("my-new-topic-2", 3, (short) 1);
	}
}
