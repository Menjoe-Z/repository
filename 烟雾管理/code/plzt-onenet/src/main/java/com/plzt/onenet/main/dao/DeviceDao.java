package com.plzt.onenet.main.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.plzt.onenet.main.commmon.HttpHandler;

import net.sf.json.JSONObject;

@Component
public class DeviceDao {
	
	@Autowired
	private HttpHandler httpHandler;
	
	public String getDevice(String devid) {
		
		String resultStr = httpHandler.doGet("/devices/" + devid);
		JSONObject result = JSONObject.fromObject(resultStr);
		JSONObject data = result.getJSONObject("data");
		return data.toString();
	}
	
}
