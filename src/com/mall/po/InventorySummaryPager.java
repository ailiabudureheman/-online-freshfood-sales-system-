package com.mall.po;

import java.util.List;
import java.util.Map;

public class InventorySummaryPager {
	private List<Inventory> inventoryList;
	private List<Warehouse> warehouseList;
	private List<Category> categoryList;
	private int totalCount;
	private int pageOffset;
	private int pagecurrentPageNo;
	private int pageSize;
	private int totalPages;
	
	// getter和setter方法
	public List<Inventory> getInventoryList() {
		return inventoryList;
	}
	public void setInventoryList(List<Inventory> inventoryList) {
		this.inventoryList = inventoryList;
	}

	public List<Warehouse> getWarehouseList() {
		return warehouseList;
	}
	public void setWarehouseList(List<Warehouse> warehouseList) {
		this.warehouseList = warehouseList;
	}
	public List<Category> getCategoryList() {
		return categoryList;
	}
	public void setCategoryList(List<Category> categoryList) {
		this.categoryList = categoryList;
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
