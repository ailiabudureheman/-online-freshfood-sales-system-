package com.mall.dao;

import com.mall.po.InventorySettings;

public interface InventorySettingsDao {
    /**
     * 获取库存设置
     * @return 库存设置对象
     */
    InventorySettings getInventorySettings();

    /**
     * 更新库存设置
     * @param settings 库存设置对象
     * @return 是否更新成功
     */
    boolean updateInventorySettings(InventorySettings settings);
}