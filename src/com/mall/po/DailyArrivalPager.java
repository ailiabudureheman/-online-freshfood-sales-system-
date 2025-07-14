package com.mall.po;

import java.util.List;

public class DailyArrivalPager {
	private List<DailyArrival> arrivalList;
	private int totalCount;
	private int pageOffset;
	private int pagecurrentPageNo;
	private int pageSize;
	private int totalPages;
	
	// getter和setter方法
	public List<DailyArrival> getArrivalList() {
		return arrivalList;
	}
	public void setArrivalList(List<DailyArrival> arrivalList) {
		this.arrivalList = arrivalList;
	}
	public int getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}
	public int getPageOffset() {
		return pageOffset;
	}
	public void setPageOffset(int pageOffset) {
		this.pageOffset = pageOffset;
	}
	public int getPagecurrentPageNo() {
		return pagecurrentPageNo;
	}
	public void setPagecurrentPageNo(int pagecurrentPageNo) {
		this.pagecurrentPageNo = pagecurrentPageNo;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public int getTotalPages() {
		return totalPages;
	}
	public void setTotalPages(int totalPages) {
		this.totalPages = totalPages;
	}
}
