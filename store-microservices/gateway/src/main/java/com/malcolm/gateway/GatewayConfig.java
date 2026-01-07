package com.malcolm.gateway;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfig {

	@Bean
	public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {

		return builder.routes().route("product", r -> r.path("/api/products/**").uri("lb://product"))
				.route("user", r -> r.path("/api/users/**").uri("lb://user"))
				.route("order", r -> r.path("/api/orders/**", "/api/cart/**").uri("lb://order"))
				.route("eureka-server",
						r -> r.path("/eureka/main").filters(f -> f.rewritePath("/eureka/main", "/"))
								.uri("http://localhost:8761"))
				.route("eureka-server", r -> r.path("/eureka/**").filters(f -> f.rewritePath("/eureka/main", "/"))
						.uri("http://localhost:8761"))
				.build();
	}
}
