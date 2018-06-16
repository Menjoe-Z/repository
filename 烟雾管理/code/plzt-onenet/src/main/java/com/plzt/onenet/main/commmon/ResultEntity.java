package com.plzt.onenet.main.commmon;

public class ResultEntity {
	
	private Integer total;
	
	private Integer pageNo;
	
	private Object rows;
	
	public ResultEntity() {
		super();
	}
	
	public ResultEntity(Integer total, Integer pageNo, String rows) {
		super();
		this.total = total;
		this.pageNo = pageNo;
		this.rows = rows;
	}
	
	public ResultEntity(Integer total, Integer pageNo) {
		super();
		this.total = total;
		this.pageNo = pageNo;
	}
	
	public Integer getTotal() {
		return total;
	}
	
	public void setTotal(Integer total) {
		this.total = total;
	}
	
	public Integer getPageNo() {
		return pageNo;
	}
	
	public void setPageNo(Integer pageNo) {
		this.pageNo = pageNo;
	}

	public Object getRows() {
		return rows;
	}

	public void setRows(Object rows) {
		this.rows = rows;
	}
}
