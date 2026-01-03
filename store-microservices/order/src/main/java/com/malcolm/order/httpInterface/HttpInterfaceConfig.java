package com.malcolm.order.httpInterface;

import java.util.Optional;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@Configuration
public class HttpInterfaceConfig {

	@Bean
	@LoadBalanced
	RestClient.Builder loadBalancedRestClientBuilder() {
		return RestClient.builder();
	}

	@Bean
	ProductHttpInterface restClientHttpInterface(RestClient.Builder loadBalancedRestClientBuilder) {
		@SuppressWarnings("unused")
		RestClient restClient = loadBalancedRestClientBuilder.baseUrl("http://product")
				.defaultStatusHandler(HttpStatusCode::is4xxClientError, (request, response) -> Optional.empty())
				.build();
		RestClientAdapter adapter = RestClientAdapter.create(restClient);
		HttpServiceProxyFactory factory = HttpServiceProxyFactory.builderFor(adapter).build();
		ProductHttpInterface service = factory.createClient(ProductHttpInterface.class);
		return service;
	}
}
