package com.store.demo.keycloak.client;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProxyController {

	private final ResourceServerClient client;

	public ProxyController(ResourceServerClient client) {
		super();
		this.client = client;
	}

	@GetMapping("/proxy")
	public String proxy() {
		return client.fetchData();
	}

}
