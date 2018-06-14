package com.plzt.onenet.main.commmon;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class HttpHandler {
	
	@Autowired
	private RestTemplate restTemplate;
	
	private final static HttpEntity<String> getHeader() {
		HttpHeaders requestHeaders = new HttpHeaders();
		requestHeaders.add("api-key", "mpXtBeHRkt2Aj0Ye3=48RaoBmK4=");
		return new HttpEntity<String>(requestHeaders);
	}
	
	private final static HttpEntity<String> getHeader(String params) {
		HttpHeaders requestHeaders = new HttpHeaders();
		requestHeaders.add("api-key", "mpXtBeHRkt2Aj0Ye3=48RaoBmK4=");
		return new HttpEntity<String>(params, requestHeaders);
	}
	
	public String doGet(String action, Map<String, Object> params) {
		StringBuilder requrl = new StringBuilder(Constant.BASEURL).append(action).append("?");
		if (params != null && params.size() > 0) {
			for (String key : params.keySet()) {
				requrl.append(key).append("={").append(key).append("}&");
			}
		}
		ResponseEntity<String> response = 
				restTemplate.exchange(
						requrl.toString(),
						HttpMethod.GET,
						getHeader(),
						String.class,
						params);
		return response.getBody();
	}
	
	public String doGet(String action) {
		StringBuilder requrl = new StringBuilder(Constant.BASEURL).append(action).append("?");
		ResponseEntity<String> response = 
				restTemplate.exchange(
						requrl.toString(),
						HttpMethod.GET,
						getHeader(),
						String.class);
		return response.getBody();
	}
	
	public String doPost(String action, Map<String, Object> params) {
		String requrl = Constant.BASEURL + action;
		ResponseEntity<String> response = 
				restTemplate.exchange(
						requrl,
						HttpMethod.POST,
						getHeader(),
						String.class,
						params);
		return response.getBody();
	}
	
	public String doPost(String action, String params) {
		String requrl = Constant.BASEURL + action;
		String result = restTemplate.postForObject(requrl, getHeader(params), String.class);
		return result;
	}
	
}
