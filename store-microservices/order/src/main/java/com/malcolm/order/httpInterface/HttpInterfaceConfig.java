package com.malcolm.order.httpInterface;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

import io.micrometer.observation.ObservationRegistry;
import io.micrometer.tracing.Tracer;
import io.micrometer.tracing.propagation.Propagator;

@Configuration
public class HttpInterfaceConfig {

	@Autowired(required = false)
	private ObservationRegistry observationRegistry;
	@Autowired(required = false)
	private Tracer tracer;
	@Autowired(required = false)
	private Propagator propagator;

	@Bean
	@LoadBalanced
	RestClient.Builder loadBalancedRestClientBuilder() {
		RestClient.Builder builder = RestClient.builder();

		if (observationRegistry != null) {
			builder.requestInterceptor(createTracingInterceptor());
		}
		return builder;
	}

	private ClientHttpRequestInterceptor createTracingInterceptor() {
		return (request, body, execution) -> {
			if (tracer != null && propagator != null && tracer.currentSpan() != null) {
				propagator.inject(tracer.currentTraceContext().context(), request.getHeaders(),
						(carrier, key, value) -> carrier.add(key, value));
			}
			return execution.execute(request, body);
		};
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

	@Bean
	UserHttpInterface restClientUserHttpInterface(RestClient.Builder loadBalancedRestClientBuilder) {
		@SuppressWarnings("unused")
		RestClient restClient = loadBalancedRestClientBuilder.baseUrl("http://user")
				.defaultStatusHandler(HttpStatusCode::is4xxClientError, (request, response) -> Optional.empty())
				.build();
		RestClientAdapter adapter = RestClientAdapter.create(restClient);
		HttpServiceProxyFactory factory = HttpServiceProxyFactory.builderFor(adapter).build();
		UserHttpInterface service = factory.createClient(UserHttpInterface.class);
		return service;
	}
}
