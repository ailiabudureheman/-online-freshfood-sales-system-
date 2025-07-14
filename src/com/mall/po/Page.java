package com.mall.po;

import java.util.List;

public class Page {

	private int totalNum;

	private List pageList;
	
	private int currentPage;

	public List getPageList() {
		return pageList;
	}

	public void setPageList(List pageList) {
		this.pageList = pageList;
	}

	public int getTotalNum() {
		return totalNum;
	}

	public void setTotalNum(int totalNum) {
		this.totalNum = totalNum;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}
	
}
