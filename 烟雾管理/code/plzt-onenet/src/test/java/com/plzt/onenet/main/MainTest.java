package com.plzt.onenet.main;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import net.sf.json.JSONObject;

@SpringBootTest
@RunWith(SpringRunner.class)
public class MainTest {
	
	@Autowired
	public RestTemplate restTemplate;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(MainTest.class);
	
	@Test
	public void test1() {
		HttpHeaders requestHeaders = new HttpHeaders();
		requestHeaders.add("api-key", "mpXtBeHRkt2Aj0Ye3=48RaoBmK4=");
		HttpEntity<String> requestEntity = new HttpEntity<String>(requestHeaders);
		Map<String, String> params = new HashMap<>();
		params.put("online", "false");
		params.put("page", "2");
		ResponseEntity<String> response = 
				restTemplate.exchange("http://api.heclouds.com/devices", 
						HttpMethod.GET, requestEntity, String.class, params);
		String sttr = response.getBody();
		JSONObject data = JSONObject.fromObject(sttr);
		System.out.println(data.getString("data"));
		//LOGGER.info(data.getString("data"));
	}
	
	@Test
	public void test2() {
		HttpHeaders requestHeaders = new HttpHeaders();
		requestHeaders.add("api-key", "mpXtBeHRkt2Aj0Ye3=48RaoBmK4=");
		HttpEntity<String> requestEntity = new HttpEntity<String>(requestHeaders);
		Map<String, String> params = new HashMap<>();
		params.put("page", "1");
		ResponseEntity<String> response = 
				restTemplate.exchange("http://api.heclouds.com/triggers", 
						HttpMethod.GET, requestEntity, String.class, params);
		String sttr = response.getBody();
		JSONObject data = JSONObject.fromObject(sttr);
		System.out.println(data.getString("data"));
		//LOGGER.info(data.getString("data"));
	}
	
}
