package com.mall.daoimpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.mall.dao.InventorySettingsDao;
import com.mall.common.DbUtil;
import com.mall.po.InventorySettings;

public class InventorySettingsDaoImpl implements InventorySettingsDao {
    private Connection conn = null;
    private PreparedStatement pstmt = null;
    private ResultSet rs = null;

    @Override
    public InventorySettings getInventorySettings() {
        InventorySettings settings = new InventorySettings();
        String sql = "SELECT * FROM tb_inventory_settings LIMIT 1";

        try {
            DbUtil db = new DbUtil();
            conn = db.getCon();
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                // 按照表结构的字段名，依次获取并设置到实体类中
                settings.setSettingsId(rs.getInt("settingsId"));
                settings.setDefaultSafetyStockFactor(rs.getBigDecimal("defaultSafetyStockFactor"));
                settings.setDefaultReorderPointFactor(rs.getBigDecimal("defaultReorderPointFactor"));
                settings.setExpiryAlertDays(rs.getInt("expiryAlertDays"));
                settings.setAlertNotificationMethod(rs.getString("alertNotificationMethod"));
                settings.setWarehouseInspectionCycle(rs.getInt("warehouseInspectionCycle"));
                settings.setWarehouseInspectionAlertDays(rs.getInt("warehouseInspectionAlertDays"));
                settings.setDefaultSupplierId(rs.getInt("defaultSupplierId"));
                settings.setDefaultLeadTime(rs.getInt("defaultLeadTime"));
                settings.setMaterialOrderNotificationMethod(rs.getString("materialOrderNotificationMethod"));
                settings.setCreateTime(rs.getTimestamp("createTime"));
                settings.setUpdateTime(rs.getTimestamp("updateTime"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return settings;
    }

    @Override
    public boolean updateInventorySettings(InventorySettings settings) {
        boolean result = false;
        // 根据表结构的字段，编写更新的 SQL 语句
        String sql = "UPDATE tb_inventory_settings SET " +
                "defaultSafetyStockFactor = ?, " +
                "defaultReorderPointFactor = ?, " +
                "expiryAlertDays = ?, " +
                "alertNotificationMethod = ?, " +
                "warehouseInspectionCycle = ?, " +
                "warehouseInspectionAlertDays = ?, " +
                "defaultSupplierId = ?, " +
                "defaultLeadTime = ?, " +
                "materialOrderNotificationMethod = ?, " +
                "updateTime = NOW() " +
                "WHERE settingsId = ?";

        try {
            DbUtil db = new DbUtil();
            conn = db.getCon();
            pstmt = conn.prepareStatement(sql);
            // 按照 SQL 语句中占位符的顺序，设置对应的参数值
            pstmt.setBigDecimal(1, settings.getDefaultSafetyStockFactor());
            pstmt.setBigDecimal(2, settings.getDefaultReorderPointFactor());
            pstmt.setInt(3, settings.getExpiryAlertDays());
            pstmt.setString(4, settings.getAlertNotificationMethod());
            pstmt.setInt(5, settings.getWarehouseInspectionCycle());
            pstmt.setInt(6, settings.getWarehouseInspectionAlertDays());
            pstmt.setInt(7, settings.getDefaultSupplierId());
            pstmt.setInt(8, settings.getDefaultLeadTime());
            pstmt.setString(9, settings.getMaterialOrderNotificationMethod());
            pstmt.setInt(10, settings.getSettingsId());

            int rows = pstmt.executeUpdate();
            result = (rows > 0);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return result;
    }
}