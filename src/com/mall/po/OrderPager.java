package com.mall.po;

import java.util.Map;

public class OrderPager {

	private Map orderMap;

	private int totalNum;

	private int pagecurrentPageNo;

	private int pageSize;

	private int pageNum;

	private int pageOffset;

	public Map getOrderMap() {
		return orderMap;
	}

	public void setOrderMap(Map orderMap) {
		this.orderMap = orderMap;
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
}
