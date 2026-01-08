package com.malcolm.gateway;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;

@Configuration
public class GatewayConfig {

	@Bean
	public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
		// Retries are for when a service throws an error. Circuit breakers are for when
		// the service is down
		return builder.routes()
				.route("product", r -> r.path("/api/products/**")
						.filters(f -> f.retry(retryConfig -> retryConfig.setRetries(10).setMethods(HttpMethod.GET))
								.circuitBreaker(config -> config.setName("storeBreaker")
										.setFallbackUri("forward:/fallback/products")))
						.uri("lb://product"))
				.route("user", r -> r.path("/api/users/**").uri("lb://user"))
				.route("order", r -> r.path("/api/orders/**", "/api/cart/**").uri("lb://order"))
				.route("eureka-server",
						r -> r.path("/eureka/main").filters(f -> f.rewritePath("/eureka/main", "/"))
								.uri("http://localhost:8761"))
				.route("eureka-server", r -> r.path("/eureka/**").filters(f -> f.rewritePath("/eureka/main", "/"))
						.uri("http://localhost:8761"))
				.build();

		// Below paths are rewritten so that incoming requests don't have "/api" in
		// their path but still route to the right endpoint
		/*
		 * return builder.routes() .route("product", r -> r.path("/products/**")
		 * .filters(f -> f.rewritePath("/products(?<segment>/?.*)",
		 * "/api/products${segment}")) .uri("lb://product")) .route("user", r ->
		 * r.path("/users/**") .filters(f -> f.rewritePath("/users(?<segment>/?.*)",
		 * "/api/users${segment}")).uri("lb://user")) .route("order", r ->
		 * r.path("/orders/**", "/cart/**").filters(f -> {
		 * f.rewritePath("/orders(?<segment>/?.*)", "/api/orders${segment}");
		 * f.rewritePath("/cart(?<segment>/?.*)", "/api/cart${segment}"); return f;
		 * }).uri("lb://order")) .route("eureka-server", r ->
		 * r.path("/eureka/main").filters(f -> f.rewritePath("/eureka/main", "/"))
		 * .uri("http://localhost:8761")) .route("eureka-server", r ->
		 * r.path("/eureka/**").filters(f -> f.rewritePath("/eureka/main", "/"))
		 * .uri("http://localhost:8761")) .build();
		 */
	}
}
