package com.plzt.onenet.main.service.impl;

import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;
import com.plzt.onenet.main.commmon.ErrorCode;
import com.plzt.onenet.main.commmon.ResultMsg;
import com.plzt.onenet.main.dao.DeviceDao;
import com.plzt.onenet.main.entity.DeviceRelation;
import com.plzt.onenet.main.service.DeviceService;

import net.sf.json.JSONObject;

@Service
public class DeviceServiceImpl implements DeviceService {

	
	
	@Autowired
	private DeviceDao deviceDao;
	
	@Autowired
	private MongoTemplate mongoTemplate;
	
	@Override
	public ResultMsg bindDevice(String devid, String objid) {
		
		String result = deviceDao.getDevice(devid);
		if (StringUtils.isEmpty(result)) {
			return new ResultMsg(ErrorCode.设备不存在.getCode(), "", ErrorCode.设备不存在.getMsg());
		}
		JSONObject data = JSONObject.fromObject(result);
		String online = data.getString("online");
		if ("false".equals(online.toLowerCase())) {
			return new ResultMsg(ErrorCode.设备不在线.getCode(), "", ErrorCode.设备不在线.getMsg());
		}
		
		Query query = Query.query(Criteria.where("devid").is(devid).and("objid").is(objid));
		int record = (int) mongoTemplate.count(query, DeviceRelation.class);
		if (0 < record) {
			return new ResultMsg(ErrorCode.已绑定.getCode(), ErrorCode.已绑定.getMsg());
		}
		
		DeviceRelation deviceRelation = new DeviceRelation();
		deviceRelation.setCreatetime(new Date());
		deviceRelation.setDevid(devid);
		deviceRelation.setObjid(objid);
		mongoTemplate.save(deviceRelation);
		
		return new ResultMsg(ErrorCode.操作成功.getCode(), ErrorCode.操作成功.getMsg());
	}

	@Override
	public ResultMsg deviceStatus(String devid, String objid) {
		String result = deviceDao.getDevice(devid);
		if (StringUtils.isEmpty(result)) {
			return new ResultMsg(ErrorCode.设备不存在.getCode(), "", ErrorCode.设备不存在.getMsg());
		}
		JSONObject data = JSONObject.fromObject(result);
		String online = data.getString("online");
		if ("false".equals(online.toLowerCase())) {
			return new ResultMsg(ErrorCode.设备不在线.getCode(), "", ErrorCode.设备不在线.getMsg());
		}
		
		Query query = Query.query(Criteria.where("devid").is(devid).and("objid").is(objid));
		int record = (int) mongoTemplate.count(query, DeviceRelation.class);
		if (0 < record) {
			return new ResultMsg(ErrorCode.已绑定.getCode(), ErrorCode.已绑定.getMsg());
		}
		return new ResultMsg(ErrorCode.操作成功.getCode(), ErrorCode.操作成功.getMsg());
	}

}
