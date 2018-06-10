package com.plzt.onenet.main.controller.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.plzt.onenet.main.commmon.ErrorCode;
import com.plzt.onenet.main.commmon.ResultMsg;
import com.plzt.onenet.main.service.DeviceService;

@RestController
@RequestMapping("/api/device")
public class DeviceController {
	
	@Autowired
	private DeviceService deviceService;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(DeviceController.class);
	
	@PostMapping("/bind")
	public ResultMsg bindDevice(
			@RequestParam("devid")String devid,
			@RequestParam("objid")String objid
		) {
		try {
			return deviceService.bindDevice(devid, objid);
		} catch (Exception e) {
			LOGGER.error("绑定设备出错", e);
		}
		return new ResultMsg(ErrorCode.操作失败.getCode(), ErrorCode.操作失败.getMsg());
	}
	
	@GetMapping("/status")
	public ResultMsg status(
			@RequestParam("devid")String devid,
			@RequestParam("objid")String objid
		) {
		try {
			return deviceService.deviceStatus(devid, objid);
		} catch (Exception e) {
			LOGGER.error("绑定设备出错", e);
		}
		return new ResultMsg(ErrorCode.操作失败.getCode(), ErrorCode.操作失败.getMsg());
	}
	
}
