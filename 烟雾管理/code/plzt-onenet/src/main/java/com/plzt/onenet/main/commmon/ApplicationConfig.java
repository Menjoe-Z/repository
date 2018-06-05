package com.plzt.onenet.main.commmon;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class ApplicationConfig {
	
	@Bean
	public RestTemplate restTemplate() {
		/*HttpComponentsClientHttpRequestFactory  requestFactory = new HttpComponentsClientHttpRequestFactory();
		requestFactory.setReadTimeout(3000);
		requestFactory.setConnectionRequestTimeout(3000);
	    return new RestTemplate(requestFactory);*/
	    return new RestTemplate();
	}
	
}
