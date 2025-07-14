package com.mall.po;

import java.util.ArrayList;
import java.util.List;

public class InventoryAlertPager {

	private List<InventoryAlert> alertList = new ArrayList<>();
	private List<Warehouse> warehouseList = new ArrayList<>();

//	private List<InventoryAlert> alertList;
//	private List<Warehouse> warehouseList;
	private int totalCount;
	private int pageOffset;
	private int pagecurrentPageNo;
	private int pageSize;
	private int totalPages;
	private int lowStockCount;
	private int expiringCount;
	private int expiredCount;
	
	// getter和setter方法
	public List<InventoryAlert> getAlertList() {
		return alertList;
	}
	public void setAlertList(List<InventoryAlert> alertList) {
		this.alertList = alertList;
	}
	public List<Warehouse> getWarehouseList() {
		return warehouseList;
	}
	public void setWarehouseList(List<Warehouse> warehouseList) {
		this.warehouseList = warehouseList;
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
	public int getLowStockCount() {
		return lowStockCount;
	}
	public void setLowStockCount(int lowStockCount) {
		this.lowStockCount = lowStockCount;
	}
	public int getExpiringCount() {
		return expiringCount;
	}
	public void setExpiringCount(int expiringCount) {
		this.expiringCount = expiringCount;
	}
	public int getExpiredCount() {
		return expiredCount;
	}
	public void setExpiredCount(int expiredCount) {
		this.expiredCount = expiredCount;
	}
}
