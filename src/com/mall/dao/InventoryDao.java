package com.mall.dao;

import com.mall.po.SubType;

import java.util.List;

public abstract class InventoryDao {
    public abstract boolean addInventory(int arrivalId, int warehouseId);

    public abstract List<SubType> getProductList();

    public abstract List<Integer> getWarehouseList();
}
