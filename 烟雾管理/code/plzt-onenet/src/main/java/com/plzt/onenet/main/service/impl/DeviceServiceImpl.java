package com.plzt.onenet.main.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import com.plzt.onenet.main.commmon.ErrorCode;
import com.plzt.onenet.main.commmon.ResultEntity;
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
	
	private static final Logger LOGGER = LoggerFactory.getLogger(DeviceServiceImpl.class);
	
	@Override
	public ResultMsg bindDevice(String devid, String objid) {
		
		ResultMsg status = deviceStatus(devid);
		if (status.getErrno() != 0) {
			return status;
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
		
		return new ResultMsg(ErrorCode.OK.getCode(), ErrorCode.OK.getMsg());
	}

	@Override
	public ResultMsg deviceStatus(String devid, String objid) {
		ResultMsg status = deviceStatus(devid);
		if (status.getErrno() != 0) {
			return status;
		}
		Query query = Query.query(Criteria.where("devid").is(devid).and("objid").is(objid));
		int record = (int) mongoTemplate.count(query, DeviceRelation.class);
		if (0 < record) {
			return new ResultMsg(ErrorCode.已绑定.getCode(), ErrorCode.已绑定.getMsg());
		}
		return new ResultMsg(ErrorCode.OK.getCode(), ErrorCode.OK.getMsg());
	}

	@Override
	public ResultMsg smoke(String devid, String objid) {

		ResultMsg status = deviceStatus(devid);
		if (status.getErrno() != 0) {
			return status;
		}
		Query query = Query.query(Criteria.where("devid").is(devid).and("objid").is(objid));
		int record = (int) mongoTemplate.count(query, DeviceRelation.class);
		if (0 == record) {
			return new ResultMsg(ErrorCode.未绑定.getCode(), ErrorCode.未绑定.getMsg());
		}
		
		String result = deviceDao.smoke(devid);
		JSONObject obj = JSONObject.fromObject(result);
		
		String errno = obj.getString("errno");
		
		if (StringUtils.isEmpty(errno)) {
			return new ResultMsg(ErrorCode.超时.getCode(), ErrorCode.超时.getMsg());
		}
		
		if ("0".equals(errno)) {
			return new ResultMsg(ErrorCode.OK.getCode(), ErrorCode.OK.getMsg());
		} else if ("16".equals(errno)) {
			return new ResultMsg(ErrorCode.超时.getCode(), ErrorCode.超时.getMsg());
		} else if ("14".equals(errno)) {
			return new ResultMsg(ErrorCode.设备不在线.getCode(), ErrorCode.设备不在线.getMsg());
		} else {
			return new ResultMsg(ErrorCode.状态未知.getCode(), ErrorCode.状态未知.getMsg());
		}
	}

	@Override
	public ResultMsg deviceStatus(String devid) {
		String result = deviceDao.getDevice(devid);
		if (StringUtils.isEmpty(result)) {
			return new ResultMsg(ErrorCode.设备不存在.getCode(), ErrorCode.设备不存在.getMsg());
		}
		JSONObject data = JSONObject.fromObject(result);
		String online = data.getString("online");
		if ("false".equals(online.toLowerCase())) {
			return new ResultMsg(ErrorCode.设备不在线.getCode(), ErrorCode.设备不在线.getMsg());
		}
		return new ResultMsg(ErrorCode.OK.getCode(), ErrorCode.OK.getMsg());
	}

	@Override
	public ResultEntity bindList(Integer pageNumber) {
		int pageSize = 30;
		Query query = new Query();
		int total = (int) mongoTemplate.count(query, DeviceRelation.class);
		query.skip((pageNumber - 1) * pageSize).limit(pageSize);
		List<DeviceRelation> rows = mongoTemplate.find(query, DeviceRelation.class);
		ResultEntity entity = new ResultEntity();
		entity.setTotal(total);
		entity.setPageNo(pageNumber);
		entity.setRows(rows);
		return entity;
	}

	@Override
	public ResultEntity deviceList(Integer pageNumber, String online, String priv, String key_word) {
		Map<String, Object> params = new HashMap<>();
		params.put("page", pageNumber);
		params.put("per_page", 30);
		if (!StringUtils.isEmpty(online)) {
			params.put("online", online);
		}
		if (!StringUtils.isEmpty(key_word)) {
			params.put("key_word", key_word);
		}
		if (!StringUtils.isEmpty(priv)) {
			params.put("private", priv);
		}
		String listStr = deviceDao.deviceList(params);
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
		entity.setRows(data.getJSONArray("devices"));
		return entity;
	}

	@Override
	public ResultMsg removeBind(String devid, String objid) {
		ResultMsg status = deviceStatus(devid);
		if (status.getErrno() != 0) {
			return status;
		}
		Query query = Query.query(Criteria.where("devid").is(devid).and("objid").is(objid));
		DeviceRelation record = mongoTemplate.findOne(query, DeviceRelation.class);
		if (null == record) {
			return new ResultMsg(ErrorCode.未绑定.getCode(), ErrorCode.未绑定.getMsg());
		}
		mongoTemplate.remove(record);
		return new ResultMsg(ErrorCode.OK.getCode(), ErrorCode.OK.getMsg());
	}

	@Override
	public List<DeviceRelation> bindListAll() {
		List<DeviceRelation> rows = mongoTemplate.findAll(DeviceRelation.class);
		return rows;
	}

}
