package com.mall.dao;

import com.mall.po.InventoryAlert;
import com.mall.po.InventoryAlertPager;

import java.util.List;
import java.util.Map;

public interface InventoryAlertDao {
    /**
     * 获取库存预警分页数据
     * @param offset 起始记录索引
     * @param pageSize 每页记录数
     * @param alertType 预警类型筛选
     * @param warehouseId 仓库ID筛选
     * @param productName 产品名称筛选
     * @return 库存预警分页对象
     */
    InventoryAlertPager getInventoryAlertPager(int offset, int pageSize, String alertType,
                                               String warehouseId, String productName);

    /**
     * 获取库存预警总数
     * @param alertType 预警类型筛选
     * @param warehouseId 仓库ID筛选
     * @param productName 产品名称筛选
     * @return 记录总数
     */
    int getInventoryAlertCount(String alertType, String warehouseId, String productName);

    /**
     * 获取库存预警列表
     * @param offset 起始记录索引
     * @param pageSize 每页记录数
     * @param alertType 预警类型筛选
     * @param warehouseId 仓库ID筛选
     * @param productName 产品名称筛选
     * @return 库存预警列表
     */
    InventoryAlertPager getInventoryAlertList(int offset, int pageSize, String alertType,
                                              String warehouseId, String productName);

    /**
     * 获取所有仓库列表
     * @return 仓库列表
     */
    InventoryAlertPager getAllWarehouses();

    /**
     * 获取各类预警的统计数量
     * @return 包含各类预警数量的对象
     */
    InventoryAlertPager getAlertTypeCounts(String alertType, String warehouseId, String productName);


    /**
     * 更新库存预警信息
     */
    void updateInventoryAlerts();

    // 在InventoryAlertDaoImpl中
    void triggerImmediateUpdate();

    boolean ignoreAlert(int alertId);

    Map<String, Object> getAlertDetail(int alertId);

    List<Map<String, Object>> getSuppliersByProductId(int productId);
}