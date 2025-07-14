package com.mall.dao;

import com.mall.po.MaterialOrder;
import com.mall.po.MaterialOrderPager;

public interface MaterialOrderDao {
    /**
     * 获取材料订单分页数据
     * @param offset 起始记录索引
     * @param pageSize 每页记录数
     * @param status 订单状态
     * @param orderNo 订单编号
     * @param supplier 供应商
     * @return 材料订单分页对象
     */
    MaterialOrderPager getMaterialOrderPager(int offset, int pageSize,
                                             String status, String orderNo,
                                             String supplier);

    /**
     * 获取材料订单记录总数
     * @param status 订单状态
     * @param orderNo 订单编号
     * @param supplier 供应商
     * @return 记录总数
     */
    int getMaterialOrderCount(String status, String orderNo, String supplier);

    /**
     * 获取材料订单列表
     * @param offset 起始记录索引
     * @param pageSize 每页记录数
     * @param status 订单状态
     * @param orderNo 订单编号
     * @param supplier 供应商
     * @return 材料订单列表
     */
    MaterialOrderPager getMaterialOrderList(int offset, int pageSize,
                                            String status, String orderNo,
                                            String supplier);

    boolean createOrderForAlert(int alertId);
}