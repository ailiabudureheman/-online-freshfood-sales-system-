package com.mall.dao;

import java.util.List;

import com.mall.po.Goods;
import com.mall.po.Page;

public interface GoodsDao {
	/**
	 *显示商品
	 * @param type 显示列别（热卖，新品，降价）
	 * @param flag 表示是否是（热卖，新品，降价）
	 * @return List
	 */
	public List showGoods(int type,int flag);
	
	/**
	 *根据商品的GoodsId号来展示改商品的详细信息
	 * @param GoodsId 商品号
	 * @return Goods
	 */
	public Goods showGoodsById(int GoodsId);
	
	/**
	 * 查询商品购买记录
	 * 
	 * @param GoodsId
	 * @return
	 */
	public List showBuyRecordsById(int GoodsId);
	
	/**
	 * 将商品本信息录入数据库
	 */
	public boolean addGoods(Goods Goods);
	/**
	 * 分页显示商品
	 */
	public Page doPage(int type,int currentPage,int pageSize, int flag);
	/**
	 *跟据用户输入的关键字搜索相关商品
	 * @param keywords 用户输入关键字
	 */
	public boolean updateGoodsNum(int num,int GoodsId);

	public boolean updateGoods(Goods Goods);
	
	public List searchGoods(String keywords);
	/**
	 *分页显示商品
	 * @param currentPage显示出来当前页码
	 * @param pageSize 每页显示数目
	 * @return Page
	 */
	public Page doPage(String keywords,int currentPage,int pageSize);
	/**
	 *根据用户选择的搜索条件搜索相关商品
	 * @param superTypeId 所属大类的ID
	 * @param subTypeId 所属小类的ID
	 * @param searchMethod（可以是按品牌、商品名、编码中的一个）
	 * @return List 
	 */
	public List searchGoodsByConditions(int superTypeId,int subTypeId,String searchMethod);
	/**
	 * 根据用户选择的搜索条件搜索相关商品
	 * @param superTypeId 所属大类的ID
	 * @param subTypeId 所属小类的ID
	 * @param searchMethod（可以是按品牌、商品名、产地、编码中的一个）
	 * @param upLoadTime 商品上架的时间
	 * @param currentPage 显示出来当前页码	
	 * @param pageSize 每页显示数目
     * @return Page
	 */
	public Page doPageByConditons(int superTypeId,int subTypeId,String searchMethod,int currentPage,int pageSize); 
}
