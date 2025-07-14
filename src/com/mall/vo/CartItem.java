package com.mall.vo;

import com.mall.po.Goods;

/**
 *购物车中的购物项，它包括商品和商品数量
 */
public class CartItem {
	
	private Goods Goods;
	
	private int count;//数量
	public CartItem(Goods Goods){		
		this.Goods=Goods;
		count++;
	}
	
	public Goods getGoods() {
		return Goods;
	}
	
	public void setGoods(Goods Goods) {
		this.Goods = Goods;
	}
	
	public int getCount() {
		return count;
	}
	
	public void setCount(int count) {
		this.count = count;
	}
	//计算某一购物项的总价
	public float getItemPrice(){
		
		float price = Goods.getNowPrice()*count;
		return price;
	}
}
