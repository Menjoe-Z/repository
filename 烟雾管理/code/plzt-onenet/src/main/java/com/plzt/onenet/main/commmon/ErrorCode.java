package com.plzt.onenet.main.commmon;

public enum ErrorCode {
	
	认证失败(-2, "auth fail"),
	已绑定(100, "already exists"),
	设备不存在(101, "device not exists"),
	设备不在线(102, "device not online"),
	操作成功(0, "ok"),
	操作失败(-1, "action fail");
	
	private String msg;
	
	private int code;

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	private ErrorCode(int code, String msg) {
		this.msg = msg;
		this.code = code;
	}
	
}
