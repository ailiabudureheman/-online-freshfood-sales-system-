package com.mall.po;

import java.util.Map;

public class AdminPager {

	private Map adminMap;  //管理员集合

	private int totalNum;  //分页的总条数

	private int pageSize;  //每页显示的条数

	private int pagecurrentPageNo;  //当前第几页
	
	private int pageNum;   //总页数

	private int pageOffset; // 数据库limit语句中的索引值
	
	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getTotalNum() {
		return totalNum;
	}

	public void setTotalNum(int totalNum) {
		this.totalNum = totalNum;
	}

	public Map getAdminMap() {
		return adminMap;
	}

	public void setAdminMap(Map adminMap) {
		this.adminMap = adminMap;
	}

	public int getPagecurrentPageNo() {
		return pagecurrentPageNo;
	}

	public void setPagecurrentPageNo(int pagecurrentPageNo) {
		this.pagecurrentPageNo = pagecurrentPageNo;
	}

	public int getPageNum() {
		return pageNum;
	}

	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}

	public int getPageOffset() {
		return pageOffset;
	}

	public void setPageOffset(int pageOffset) {
		this.pageOffset = pageOffset;
	}
}
