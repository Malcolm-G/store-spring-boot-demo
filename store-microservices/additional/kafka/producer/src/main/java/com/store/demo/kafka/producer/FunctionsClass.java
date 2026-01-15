package com.store.demo.kafka.producer;

import java.util.function.Function;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FunctionsClass {

	@Bean
	Function<String, String> uppercase() {
		return value -> value.toUpperCase();
	}
}
