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

import com.plzt.onenet.main.commmon.HttpHandler;
import com.plzt.onenet.main.dao.DeviceDao;

import net.sf.json.JSONObject;

//@SpringBootTest
//@RunWith(SpringRunner.class)
public class MainTest {
	
	@Autowired
	public RestTemplate restTemplate;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(MainTest.class);
	
	//@Test
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
	
	//@Test
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
	@Autowired
	private DeviceDao deviceDao; 
	//@Test
	public void test3() {
		System.out.println(deviceDao.smoke("33362340"));
	}
	@Autowired
	private HttpHandler httpHandler;
	//@Test
	public void test4() {
		String action = "/nbiot?imei=869664030013299&obj_id=3342&obj_inst_id=0&mode=2";
		//String action = "/nbiot/execute";
		String params = "{\"data\":[{\"res_id\":5750,\"val\":\"FORG\"}]}";
		String sss = httpHandler.doPost(action, params);
		System.out.println(sss);
	}
	
	//@Test
	public void test5() {
		//String action = "/nbiot?imei=869664030013299&res_id=869664030013299obj_id=29458732&obj_inst_id=869664030013299";
		String action = "/devices/33362340";
		String sss = httpHandler.doGet(action);
		System.out.println(sss);
	}
	
}
