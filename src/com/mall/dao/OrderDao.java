package com.mall.dao;

import java.util.List;

import com.mall.po.Order;

/**
 *对订单进行操作的接口
 */
public interface OrderDao {
	
	//添加订单，并返回一个订单号
	public int addOrder(Order order);
	//根据当前用户的用户名查订单
	public List selectOrder(String name);
	//查看订单项
	public List selectOrderItem(int id);
}
