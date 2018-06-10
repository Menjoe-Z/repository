package com.plzt.onenet.main.commmon;

public class ResultMsg {
	
	private int errno;
	
	private String resinfo;
	
	private String error;

	public int getErrno() {
		return errno;
	}

	public void setErrno(int errno) {
		this.errno = errno;
	}

	public String getResinfo() {
		return resinfo;
	}

	public void setResinfo(String resinfo) {
		this.resinfo = resinfo;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public ResultMsg(int errno, String error,  String resinfo) {
		super();
		this.errno = errno;
		this.resinfo = resinfo;
		this.error = error;
	}
	
	public ResultMsg(int errno, String error) {
		super();
		this.errno = errno;
		this.error = error;
	}
	
	public ResultMsg() {
		super();
	}
	
}
