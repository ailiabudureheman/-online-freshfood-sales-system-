package com.mall.dao;

import java.util.List;

import com.mall.po.Goods;
import com.mall.po.GoodsPager;

public interface AdminGoodsDao {

	public List getAllGoods();
	public boolean addGoods(Goods Goods);
	public boolean checkGoodsNameIsExist(String GoodsName);
	public boolean checkISBNIsExist(String ISBN);
	public GoodsPager searchGoods(String GoodsName);
	public List getAllGoodsName();
	public GoodsPager getGoodsPager(int index,int pageSize);
	public boolean deleteGoods(int[] GoodsIds);
}
