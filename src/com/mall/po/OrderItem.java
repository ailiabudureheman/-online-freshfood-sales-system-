package com.mall.po;

public class OrderItem {

	private int orderItemId;

	private int orderId;

	private int GoodsId;

	private String GoodsName;

	private float price;

	private int GoodsNum;

	public int getGoodsId() {
		return GoodsId;
	}

	public void setGoodsId(int GoodsId) {
		this.GoodsId = GoodsId;
	}

	public String getGoodsName() {
		return GoodsName;
	}

	public void setGoodsName(String GoodsName) {
		this.GoodsName = GoodsName;
	}

	public int getGoodsNum() {
		return GoodsNum;
	}

	public void setGoodsNum(int GoodsNum) {
		this.GoodsNum = GoodsNum;
	}

	public int getOrderId() {
		return orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

	public int getOrderItemId() {
		return orderItemId;
	}

	public void setOrderItemId(int orderItemId) {
		this.orderItemId = orderItemId;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}
	
	
}
