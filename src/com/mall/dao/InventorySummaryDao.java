package com.mall.dao;

import com.mall.po.*;

import java.util.List;

public interface InventorySummaryDao {
    /**
     * 获取库存概览分页数据
     * @param offset 起始记录索引
     * @param pageSize 每页记录数
     * @param warehouseId 仓库ID
     * @param categoryId 类别ID
     * @param productName 产品名称
     * @return 库存概览分页对象
     */
    InventorySummaryPager getInventorySummaryPager(int offset, int pageSize,
                                                   String warehouseId, String categoryId,
                                                   String productName);

    /**
     * 获取库存记录总数
     * @param warehouseId 仓库ID
     * @param categoryId 类别ID
     * @param productName 产品名称
     * @return 记录总数
     */
    int getInventoryCount(String warehouseId, String categoryId, String productName);

    /**
     * 获取库存列表
     * @param offset 起始记录索引
     * @param pageSize 每页记录数
     * @param warehouseId 仓库ID
     * @param categoryId 类别ID
     * @param productName 产品名称
     * @return 库存列表
     */
    InventorySummaryPager getInventoryList(int offset, int pageSize,
                                           String warehouseId, String categoryId,
                                           String productName);

    /**
     * 获取所有仓库列表
     * @return 仓库列表
     */
    InventorySummaryPager getAllWarehouses();

    /**
     * 获取所有类别列表
     * @return 类别列表
     */
    InventorySummaryPager getAllCategories();

    /**
     * 获取最近入库记录
     * @param limit 限制返回记录数
     * @return 最近入库记录列表
     */
    List<StockInRecord> getRecentStockIns(int limit);

    /**
     * 获取最近出库记录
     * @param limit 限制返回记录数
     * @return 最近出库记录列表
     */
    List<StockOutRecord> getRecentStockOuts(int limit);

    InventoryAlertPager getInventoryAlerts();
}