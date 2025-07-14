package com.mall.dao;

import com.mall.po.Warehouse;
import com.mall.po.WarehousePager;

public interface WarehouseDao {
    /**
     * 获取仓库分页数据
     * @param offset 起始记录索引
     * @param pageSize 每页记录数
     * @return 仓库分页对象
     */
    WarehousePager getWarehousePager(int offset, int pageSize);

    /**
     * 获取仓库记录总数
     * @return 记录总数
     */
    int getWarehouseCount();

    /**
     * 获取仓库列表
     * @param offset 起始记录索引
     * @param pageSize 每页记录数
     * @return 仓库列表
     */
    WarehousePager getWarehouseList(int offset, int pageSize);

    /**
     * 根据ID获取仓库
     * @param warehouseId 仓库ID
     * @return 仓库对象
     */
    Warehouse getWarehouseById(int warehouseId);

    /**
     * 添加仓库
     * @param warehouse 仓库对象
     * @return 是否添加成功
     */
    boolean addWarehouse(Warehouse warehouse);

    /**
     * 更新仓库
     * @param warehouse 仓库对象
     * @return 是否更新成功
     */
    boolean updateWarehouse(Warehouse warehouse);

    /**
     * 删除仓库
     * @param warehouseId 仓库ID
     * @return 是否删除成功
     */
    boolean deleteWarehouse(int warehouseId);
}