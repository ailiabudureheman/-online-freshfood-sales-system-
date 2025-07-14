package com.mall.scheduler;

import com.mall.common.DbUtil;
import com.mall.po.InventoryAlert;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.sql.*;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 仓库容量预警定时任务
 * 功能：每5分钟检查一次仓库容量使用情况，生成相应预警
 */
public class WarehouseCapacityAlertJob implements Job {

    // 数据库连接参数
//    private static final String DB_URL = "jdbc:mysql://localhost:3306/shop?serverTimezone=UTC&useSSL=false";
//    private static final String DB_USER = "root";
//    private static final String DB_PASSWORD = "123456";

    // 预警阈值配置（百分比）
    private static final double WARNING_THRESHOLD = 70.0;  // 容量使用率达到70%时发出警告
    private static final double ALERT_THRESHOLD = 85.0;    // 容量使用率达到85%时发出警报
    private static final double CRITICAL_THRESHOLD = 95.0; // 容量使用率达到95%时发出严重警报

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        System.out.println("开始执行仓库容量预警检查: " + new Date());

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            DbUtil dbUtil = new DbUtil();
            conn= dbUtil.getCon();
            conn.setAutoCommit(false);

            // 1. 查询各仓库容量信息
            Map<Integer, Integer> warehouseCapacities = getWarehouseCapacities(conn);

            // 2. 查询各仓库实际使用情况（排除已出库的库存）
            Map<Integer, Integer> warehouseUsage = getWarehouseUsage(conn);

            // 3. 检查各仓库容量使用情况并生成预警
            for (Map.Entry<Integer, Integer> entry : warehouseCapacities.entrySet()) {
                int warehouseId = entry.getKey();
                int capacity = entry.getValue();
                int used = warehouseUsage.getOrDefault(warehouseId, 0);

                // 计算使用率百分比
                double usagePercent = (double) used / capacity * 100;

                // 检查是否需要预警
                checkAndCreateAlert(conn, warehouseId, capacity, used, usagePercent);
            }

            conn.commit();
            System.out.println("仓库容量预警检查完成");

        } catch (SQLException e) {
            try {
                if (conn != null) conn.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * 获取各仓库容量信息
     */
    private Map<Integer, Integer> getWarehouseCapacities(Connection conn) throws SQLException {
        Map<Integer, Integer> capacities = new HashMap<>();
        String sql = "SELECT warehouseId, capacity FROM tb_warehouse";

        try (PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                int warehouseId = rs.getInt("warehouseId");
                int capacity = rs.getInt("capacity");
                capacities.put(warehouseId, capacity);
            }
        }

        return capacities;
    }

    /**
     * 获取各仓库实际使用情况（排除已出库的库存）
     */
    private Map<Integer, Integer> getWarehouseUsage(Connection conn) throws SQLException {
        Map<Integer, Integer> usage = new HashMap<>();
        String sql = "SELECT " +
                "warehouseId, " +
                "SUM(quantity) AS used " +
                "FROM tb_product_inventory " +
                "WHERE status != 0  /* 排除已出库的库存 */ " +
                "GROUP BY warehouseId";

        try (PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                int warehouseId = rs.getInt("warehouseId");
                int used = rs.getInt("used");
                usage.put(warehouseId, used);
            }
        }

        return usage;
    }

    /**
     * 检查仓库容量使用情况并创建预警
     */
    private void checkAndCreateAlert(Connection conn, int warehouseId, int capacity,
                                     int used, double usagePercent) throws SQLException {
        String alertType = null;
        String alertMessage = null;

        // 根据使用率确定预警等级
        if (usagePercent >= CRITICAL_THRESHOLD) {
            alertType = "CRITICAL_WAREHOUSE_CAPACITY";
            alertMessage = "仓库容量严重不足: 仓库ID=" + warehouseId +
                    ", 容量=" + capacity + ", 已使用=" + used +
                    ", 使用率=" + String.format("%.2f", usagePercent) + "%";
        } else if (usagePercent >= ALERT_THRESHOLD) {
            alertType = "ALERT_WAREHOUSE_CAPACITY";
            alertMessage = "仓库容量即将不足: 仓库ID=" + warehouseId +
                    ", 容量=" + capacity + ", 已使用=" + used +
                    ", 使用率=" + String.format("%.2f", usagePercent) + "%";
        } else if (usagePercent >= WARNING_THRESHOLD) {
            alertType = "WARNING_WAREHOUSE_CAPACITY";
            alertMessage = "仓库容量开始紧张: 仓库ID=" + warehouseId +
                    ", 容量=" + capacity + ", 已使用=" + used +
                    ", 使用率=" + String.format("%.2f", usagePercent) + "%";
        }

        // 如果需要预警，则创建预警记录
        if (alertType != null) {
            createWarehouseCapacityAlert(conn, warehouseId, alertType, alertMessage);
        }
    }

    /**
     * 创建仓库容量预警记录
     */
    private void createWarehouseCapacityAlert(Connection conn, int warehouseId,
                                              String alertType, String alertMessage) throws SQLException {
        // 先检查是否已有相同类型的未解决预警
        if (hasExistingAlert(conn, warehouseId, alertType)) {
            return; // 已有相同类型的未解决预警，不再创建新的
        }

        String sql = "INSERT INTO tb_inventory_alert " +
                "(warehouseId, alertType, alertMessage, alertStatus, alertTime) " +
                "VALUES (?, ?, ?, 0, NOW())";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, warehouseId);
            pstmt.setString(2, alertType);
            pstmt.setString(3, alertMessage);
            pstmt.executeUpdate();
        }
    }

    /**
     * 检查是否已有相同类型的未解决预警
     */
    private boolean hasExistingAlert(Connection conn, int warehouseId, String alertType) throws SQLException {
        String sql = "SELECT COUNT(*) FROM tb_inventory_alert " +
                "WHERE warehouseId = ? " +
                "AND alertType = ? " +
                "AND alertStatus = 0";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, warehouseId);
            pstmt.setString(2, alertType);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        }

        return false;
    }



}