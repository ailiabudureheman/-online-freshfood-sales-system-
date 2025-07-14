package com.mall.vo;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

import com.mall.po.Goods;

public class Cart {
	private HashMap<Integer, CartItem> items = null;

	private int itemsCount = 0;//购物项的数量
	public Cart() {
		items = new HashMap<Integer, CartItem>();
	}

	/**
	 * 在购物车中添加一个购物项，如果该购物项已存在购物车中，则什么都不放
	 * @param GoodsId 商品的ID
	 * @param Goods
	 */
	public synchronized void addItem(Integer GoodsId,Goods Goods) {
		if(!items.containsKey(GoodsId)) {//购物车中没有该商品，可以添加该商品到购物车中
			CartItem item = new CartItem(Goods);
			items.put(GoodsId, item);
			itemsCount++;
		}
	}
	/**
	 * 修改已经购买好的商品数量
	 *  @param GoodsId 商品的Id号
	 * @param count 修改后的数量
	 */
	public synchronized void updateGoodsCount(Integer GoodsId,int count){
		if(count >= 1){
			if(items.containsKey(GoodsId)){
				CartItem item = items.get(GoodsId);//根据Id号，来得到商品对象
				item.setCount(count);//修改数量
				}
		}
	}
	/**
	 * 从购物车中删除一个购物项
	 * @param GoodsId 商品的Id号
	 */
	public synchronized void deleteItem(Integer GoodsId){
		if(items.containsKey(GoodsId)){
			items.remove(GoodsId);
			itemsCount--;
		}
	}
	/**
	 *删除购物车中的所有购物项
	 */
	public synchronized void clear(){
		items.clear();
		itemsCount = 0;
	}
	/**
	 *删除购物车中的所有购物项
	 */
	public synchronized int getItemCount(){
		return itemsCount;
	}
	/**
	 *得到购物车中所有购物项的数目
	 */
	public synchronized float getTotalPrice(){
		float amount = 0.0f;
		//得到迭代器
		Iterator<CartItem> it = items.values().iterator();
		while(it.hasNext()){
			CartItem item = it.next();
			amount+=item.getItemPrice();
		}
		return amount;
	}
	/**
	 *得到购物车中的所有商品
	 */
	 public synchronized Collection<CartItem> getItems(){
		 return items.values();
	 }
	 /**
	  *判断商品是否在购物车中
	  */
	 public synchronized boolean isExist(Integer GoodsId){
		 if(items.containsKey(GoodsId)){
			 return true;
		 }
		 return false;
	 }
}
