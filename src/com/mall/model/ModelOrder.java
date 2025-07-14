package com.mall.model;

import java.util.List;

import com.mall.dao.OrderDao;
import com.mall.daoimpl.OrderDaoImpl;

public class ModelOrder {
	private OrderDao ordao = new OrderDaoImpl();

	public List selectOr(String name) {
		List list = ordao.selectOrder(name);
		return list;
	}
    public List selectOrderIt(int id ){
    	List list = ordao.selectOrderItem(id);
		return list;
    }
}