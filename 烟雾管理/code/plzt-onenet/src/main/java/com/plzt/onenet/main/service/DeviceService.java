package com.plzt.onenet.main.service;

import com.plzt.onenet.main.commmon.ResultMsg;

public interface DeviceService {

	ResultMsg bindDevice(String devid, String objid);

	ResultMsg deviceStatus(String devid, String objid);
	
}
