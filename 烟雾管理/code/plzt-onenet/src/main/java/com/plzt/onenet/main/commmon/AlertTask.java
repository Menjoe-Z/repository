package com.plzt.onenet.main.commmon;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;


import com.plzt.onenet.main.dao.DeviceDao;

@Component
public class AlertTask {
	
	@Autowired
	private DeviceDao deviceDao;
	
	@Autowired
	private RestTemplate restTemplate;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(AlertTask.class);
	
	@Scheduled(fixedDelay = 1 * 60 * 1000) //首先设置为1分钟
	public void run() {
		try {
			String alert = System.getProperty("alert");
			if ("true".equals(alert)) {
				Map<String, Object> params = new HashMap<>();
				params.put("online", "false");
				String result = deviceDao.deviceList(params);
				if (!StringUtils.isEmpty(result)) {
					HttpEntity<String> entity = new HttpEntity<String>(result, new HttpHeaders());
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
