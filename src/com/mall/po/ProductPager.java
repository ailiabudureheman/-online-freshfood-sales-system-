package com.mall.po;

import java.util.List;

public class ProductPager {
	private List<Product> productList;
	private List<Category> categoryList;
	private List<Supplier> supplierList;
	private int totalCount;
	private int pageOffset;
	private int pagecurrentPageNo;
	private int pageSize;
	private int totalPages;
	
	// getter和setter方法
	public List<Product> getProductList() {
		return productList;
	}
	public void setProductList(List<Product> productList) {
		this.productList = productList;
	}
	public List<Category> getCategoryList() {
		return categoryList;
	}
	public void setCategoryList(List<Category> categoryList) {
		this.categoryList = categoryList;
	}
	public List<Supplier> getSupplierList() {
		return supplierList;
	}
	public void setSupplierList(List<Supplier> supplierList) {
		this.supplierList = supplierList;
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
