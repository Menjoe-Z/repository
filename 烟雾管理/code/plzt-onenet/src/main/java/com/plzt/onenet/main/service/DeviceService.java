package com.plzt.onenet.main.service;

import java.util.List;

import com.plzt.onenet.main.commmon.ResultEntity;
import com.plzt.onenet.main.commmon.ResultMsg;
import com.plzt.onenet.main.entity.DeviceRelation;

public interface DeviceService {

	ResultMsg bindDevice(String devid, String objid);

	ResultMsg deviceStatus(String devid, String objid);

	ResultMsg smoke(String devid, String objid);
	
	ResultMsg deviceStatus(String devid);
	
	ResultEntity bindList(Integer pageNumber);
	
	List<DeviceRelation> bindListAll();
	
	ResultEntity deviceList(Integer pageNumber, String online, String priv, String key_word);

	ResultMsg removeBind(String devid, String objid);
}
