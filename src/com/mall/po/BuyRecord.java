/**
 * 
 */
package com.mall.po;

import java.io.Serializable;

/**
 * @author 
 *
 */
public class BuyRecord implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8685415367882991411L;
	
	private String name;
	
	private int goodsNum;
	
	private String orderDate;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getGoodsNum() {
		return goodsNum;
	}

	public void setGoodsNum(int goodsNum) {
		this.goodsNum = goodsNum;
	}

	public String getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(String orderDate) {
		this.orderDate = orderDate;
	}

}
