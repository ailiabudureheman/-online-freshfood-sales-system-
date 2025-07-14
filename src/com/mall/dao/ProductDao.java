package com.mall.dao;

import com.mall.po.ProductPager;

public interface ProductDao {
    /**
     * 获取产品分页数据
     * @param offset 起始记录索引
     * @param pageSize 每页记录数
     * @param categoryId 类别ID
     * @param productName 产品名称
     * @return 产品分页对象
     */
    ProductPager getProductPager(int offset, int pageSize, String categoryId, String productName);
}