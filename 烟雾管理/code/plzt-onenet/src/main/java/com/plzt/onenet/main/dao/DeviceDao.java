package com.plzt.onenet.main.dao;


import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.plzt.onenet.main.commmon.HttpHandler;
import com.plzt.onenet.main.commmon.ResultEntity;

import net.sf.json.JSONObject;

@Component
public class DeviceDao {
	
	@Autowired
	private HttpHandler httpHandler;
	
	public String getDevice(String devid) {
		
		String resultStr = httpHandler.doGet("/devices/" + devid);
		JSONObject result = JSONObject.fromObject(resultStr);
		JSONObject data = result.getJSONObject("data");
		if (data.isEmpty()) {
			return null;
		}
		return data.toString();
	}

	public String smoke(String devid) {
		String device = getDevice(devid);
		if (StringUtils.isEmpty(device)) {
			return null;
		}
		JSONObject deviceObj = JSONObject.fromObject(device);
		String imsi = deviceObj.getString("imsi");
		String action = "/nbiot?imei="+ imsi +"&obj_id=3342&obj_inst_id=0&mode=2";
		String params = "{\"data\":[{\"res_id\":5750,\"val\":\"FORG\"}]}";
		String resultStr = httpHandler.doPost(action, params.toString());
		return resultStr;
	}

	public String deviceList(Map<String, Object> params) {
		String resultStr = httpHandler.doGet("/devices", params);
		return resultStr;
	}
	
}
