package com.plzt.onenet.main.commmon;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

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
	
	@Bean
	public WebMvcConfigurerAdapter addAdpater() {
		return new WebMvcConfigurerAdapter() {
			@Override
		    public void addInterceptors(InterceptorRegistry registry) {
		        registry.addInterceptor(new AdminHandlerInterceptor())
		        	.addPathPatterns("/main/**");
		        registry.addInterceptor(new ApiHandlerInterceptor())
		        	.addPathPatterns("/api/**");
		    }
		};
	}
	
}
