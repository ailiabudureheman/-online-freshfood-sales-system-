package com.mall.dao;

import java.util.List;

import com.mall.po.Order;
import com.mall.po.OrderFreezePager;
import com.mall.po.OrderNotSendPager;
import com.mall.po.OrderPager;
import com.mall.po.OrderSendPager;

public interface AdminOrderDao {

	public List getAllOrder();
	public Order getOneOrder(int id);
	public List getNotSendOrder(int flag);
	public List getSendOrder(int flag);
	public boolean SendOrders(int[] orderids);
	public boolean SendOrder(int orderid);
	public OrderPager getOrderPager(int index, int pageSize);
	public OrderSendPager getOrderSendPager(int index,int pageSize);
	public boolean deleteOrder(int[] ids);
	public OrderNotSendPager getOrderNotSendPager(int index,int pageSize);
	public OrderFreezePager getOrderFreezePager(int index,int pageSize);
	public boolean freezeOrder(int[] orderids);
	OrderPager getOrderPagerByConditions(int offset, int pageSize, String status, String orderNo, String customerName);
}
