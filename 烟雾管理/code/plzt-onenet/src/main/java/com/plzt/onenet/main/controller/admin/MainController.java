package com.plzt.onenet.main.controller.admin;

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
import com.plzt.onenet.main.service.DeviceService;

import net.sf.json.JSONObject;

@Controller
@RequestMapping("/main")
public class MainController {
	
	@Autowired
	private DeviceService deviceService;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(MainController.class);
	
	@RequestMapping("/list")
	@ResponseBody
	public ResultEntity list(
			@RequestParam(value = "pageNumber", defaultValue = "1")Integer pageNumber,
			@RequestParam(value = "online", required = false, defaultValue = "")String online,
			@RequestParam(value = "private", required = false, defaultValue = "")String priv,
			@RequestParam(value = "key_word", required = false)String key_word) {
		ResultEntity entity = null; 
		try {
			entity = deviceService.deviceList(pageNumber, online, priv, key_word);
		} catch (Exception e) {
			LOGGER.error("获取设备列表失败:\t" + e.getMessage());
			entity = new ResultEntity();
		}
		return entity;
	}
	
	@RequestMapping("/index")
	public String index() {
		return "main/listDevice";
	}
	
	@RequestMapping("/bind")
	public String bind() {
		return "main/listBind";
	}
	
	@RequestMapping("/bind/list")
	@ResponseBody
	public ResultEntity bindList(
			@RequestParam(value = "pageNumber", defaultValue = "1")Integer pageNumber) {
		ResultEntity entity = null; 
		try {
			entity = deviceService.bindList(pageNumber);
		} catch (Exception e) {
			LOGGER.error("获取绑定列表失败:\t" + e.getMessage());
			entity = new ResultEntity();
		}
		return entity;
	}
	
}
