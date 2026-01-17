package com.store.demo.keycloak.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ResourceServerClient {

	private final RestTemplate rest;
//	private final OAuth2AuthorizedClientManager manager;
	@Value("${resource-server.url}")
	String resourceServerUrl;

	public ResourceServerClient(RestTemplate rest) {
		super();
		this.rest = rest;
//		this.manager = manager;
	}

	public String fetchData() {
		/*
		 * var authRequest =
		 * OAuth2AuthorizeRequest.withClientRegistrationId("keycloak-client").principal(
		 * "machine") .build();
		 * 
		 * var client = manager.authorize(authRequest); String token =
		 * client.getAccessToken().getTokenValue();
		 * 
		 * HttpHeaders headers = new HttpHeaders(); headers.setBearerAuth(token);
		 * 
		 * var resp = rest.exchange(resourceServerUrl + "/data", HttpMethod.GET, new
		 * HttpEntity<>(headers), String.class);
		 * 
		 * return resp.getBody();
		 */

		// Instead of generating a new token with the code above, we can forward the
		// token received from the request
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String incomingToken = null;

		if (auth instanceof JwtAuthenticationToken jwtAuthenticationToken) {
			incomingToken = jwtAuthenticationToken.getToken().getTokenValue();
		}

		HttpHeaders headers = new HttpHeaders();
		headers.setBearerAuth(incomingToken);

		var resp = rest.exchange(resourceServerUrl + "/data", HttpMethod.GET, new HttpEntity<>(headers), String.class);

		return resp.getBody();
	}

}
