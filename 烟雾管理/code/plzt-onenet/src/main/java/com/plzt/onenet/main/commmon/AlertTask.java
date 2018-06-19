package com.plzt.onenet.main.commmon;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import com.plzt.onenet.main.entity.DeviceRelation;
import com.plzt.onenet.main.service.DeviceService;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Component
public class AlertTask {
	
	@Autowired
	private DeviceService deviceService;
	
	@Autowired
	private RestTemplate restTemplate;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(AlertTask.class);
	
	@Scheduled(fixedDelay = 1 * 60 * 1000) //首先设置为1分钟
	public void run() {
		try {
			String alert = System.getProperty("alert");
			if ("true".equals(alert)) {
				List<String> deviceIds = new ArrayList<>();
				int index = 1;
				while (true) {
					ResultEntity entity = deviceService.deviceList(index, "false", null, null);
					if (StringUtils.isEmpty(entity.getRows())) {
						break;
					}
					JSONArray arr = JSONArray.fromObject(entity.getRows());
					Iterator<?> it = arr.iterator();
					while (it.hasNext()) {
						JSONObject obj = JSONObject.fromObject(it.next());
						deviceIds.add(obj.getString("id"));
					}
					index++;
				}
				if (deviceIds.size() > 0) {
					List<DeviceRelation> list = deviceService.bindListAll();
					if (list == null || list.isEmpty()) {
						return;
					}
					List<String> bindDeviceIds = new ArrayList<>();
					for (DeviceRelation relation : list) {
						if (!bindDeviceIds.contains(relation.getDevid())) {
							bindDeviceIds.add(relation.getDevid());
						}
					}
					for (String id : deviceIds) {
						if (!bindDeviceIds.contains(id)) {
							deviceIds.remove(id);
						}
					}
					HttpEntity<String> entity = new HttpEntity<String>(JSONArray.fromObject(deviceIds).toString());
					restTemplate.postForObject(Constant.ALERT_CENTER, entity, String.class);
					LOGGER.info("有设备掉线, 上报一次");
				}
			} else {
				LOGGER.info("未开启上报");
			}
		} catch (Exception e) {
			LOGGER.error("上报出错", e);
		}
	}
	
}
