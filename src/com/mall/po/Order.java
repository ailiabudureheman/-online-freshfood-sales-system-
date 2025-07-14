package com.mall.po;

import java.util.List;

/**
 *一个订单JavaBean
 */
public class Order {
	private int orderId;

	private String recvName;//收货人
	
	private User user;
	
	private String address; 

	private String postcode; 

	private String email; 

	private String orderDate;

	private int flag;//0表示货没发出，1表示货已发出，2表示货冻结
	
	private String flagName;
	
	private List orderItem; //一个用户装订单项的List
	
	public List getOrderItem() {
		return orderItem; 
	}

	public void setOrderItem(List orderItem) {
		this.orderItem = orderItem;
	}

	public int getFlag() {
		return flag;
	}

	public void setFlag(int flag) {
		this.flag = flag;
	}

	public String getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(String orderDate) {
		this.orderDate = orderDate;
	}

	public int getOrderId() {
		return orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPostcode() {
		return postcode;
	}

	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}

	public String getRecvName() {
		return recvName;
	}

	public void setRecvName(String recvName) {
		this.recvName = recvName;
	}
	
	public String getFlagName() {
		return flagName;
	}

	public void setFlagName(String flagName) {
		this.flagName = flagName;
	}
	
}