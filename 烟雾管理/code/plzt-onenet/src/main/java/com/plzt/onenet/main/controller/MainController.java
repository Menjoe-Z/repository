package com.plzt.onenet.main.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;
import com.plzt.onenet.main.commmon.Constant;
import com.plzt.onenet.main.commmon.ResultEntity;

import net.sf.json.JSONObject;

@Controller
@RequestMapping("/main")
public class MainController {
	
	@Autowired
	private RestTemplate restTemplate;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(MainController.class);
	
	@RequestMapping("/list")
	@ResponseBody
	public ResultEntity list(
			@RequestParam(value = "pageNumber", defaultValue = "1")Integer pageNumber,
			@RequestParam(value = "online", required = false, defaultValue = "")String online,
			@RequestParam(value = "private", required = false, defaultValue = "")String priv,
			@RequestParam(value = "key_word", required = false)String key_word) {
		HttpHeaders requestHeaders = new HttpHeaders();
		requestHeaders.add("api-key", "mpXtBeHRkt2Aj0Ye3=48RaoBmK4=");
		Map<String, Object> params = new HashMap<>();
		params.put("page", pageNumber);
		params.put("per_page", 30);
		params.put("online", online);
		params.put("key_word", key_word);
		params.put("private", priv);
		ResponseEntity<String> response = 
				restTemplate.exchange(
						//, defaultValue = "1" Constant.BASEURL + "/devices?key_word={keyword}", &online={online}&private={private}
						Constant.BASEURL + "/devices?page={page}&pre_page={per_page}&key_word={key_word}",
						HttpMethod.GET,
						new HttpEntity<String>(requestHeaders),
						String.class,
						params);
		String listStr = response.getBody();
		ResultEntity entity = new ResultEntity();
		if (StringUtils.isEmpty(listStr)) {
			LOGGER.info("获取列表为空");
			return entity;
		}
		JSONObject result = JSONObject.fromObject(listStr);
		JSONObject data = result.getJSONObject("data");
		LOGGER.info("获取到数据:\t" + result.getString("errno") + ",\t" +result.getString("error"));
		entity.setTotal(data.getInt("total_count"));
		entity.setPageNo(data.getInt("page"));
		entity.setRows(data.getString("devices"));
		return entity;
	}
	
	@RequestMapping("/index")
	public String index() {
		return "main/listDevice";
	}
	
}
