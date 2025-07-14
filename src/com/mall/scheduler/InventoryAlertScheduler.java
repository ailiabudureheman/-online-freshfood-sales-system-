package com.mall.scheduler;

import com.mall.dao.InventoryAlertDao;
import com.mall.daoimpl.InventoryAlertDaoImpl;
import com.mall.po.InventoryAlert;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.sql.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * 库存预警定时更新任务（完整实现）
 * 功能：定期检查库存状态，更新预警信息，基于tb_product_inventory表计算实际库存
 */
public class InventoryAlertScheduler implements Job {

    // 数据库连接参数（实际应用中应从配置文件获取）
    private static final String DB_URL = "jdbc:mysql://localhost:3306/shop?serverTimezone=UTC&useSSL=false";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "123456";

    private InventoryAlertDao inventoryAlertDao = new InventoryAlertDaoImpl();

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        System.out.println("开始执行库存预警更新任务: " + new Date());

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            // 1. 获取数据库连接
            conn = getConnection();
            conn.setAutoCommit(false); // 开启事务

            // 2. 查询所有需要监控的产品库存信息（从tb_product_inventory统计）
            String sql = "SELECT " +
                    "pws.productId, " +
                    "pws.warehouseId, " +
                    "pws.safetyStock, " +
                    "COALESCE(SUM(pi.quantity), 0) AS currentStock " +
                    "FROM tb_product_warehouse_safety_stock pws " +
                    "LEFT JOIN tb_product_inventory pi " +
                    "ON pws.productId = pi.productId AND pws.warehouseId = pi.warehouseId " +
                    "GROUP BY pws.productId, pws.warehouseId";

            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();

            // 3. 用于存储需要保留的预警键（产品ID_仓库ID）
            Set<String> activeAlertKeys = new HashSet<>();

            // 4. 处理每条记录，更新预警状态
            while (rs.next()) {
                int productId = rs.getInt("productId");
                int warehouseId = rs.getInt("warehouseId");
                int safetyStock = rs.getInt("safetyStock");
                int currentStock = rs.getInt("currentStock");
                String alertKey = productId + "_" + warehouseId;

                // 检查是否需要预警（库存 < 安全库存）
                boolean needAlert = currentStock < safetyStock;

                // 获取当前预警记录
                InventoryAlert existingAlert = getExistingAlert(conn, productId, warehouseId);

                if (needAlert) {
                    // 需要预警的情况
                    if (existingAlert == null) {
                        // 无预警记录，创建新预警
                        createNewAlert(conn, productId, warehouseId, currentStock, safetyStock);
                    } else if (existingAlert.getAlertStatus() == 1) {
                        // 预警已解决，重新激活
                        reactivateAlert(conn, existingAlert.getAlertId());
                    }
                    // 添加到活跃预警列表
                    activeAlertKeys.add(alertKey);
                } else if (existingAlert != null && existingAlert.getAlertStatus() == 0) {
                    // 不需要预警但预警未解决，标记为已解决
                    resolveAlert(conn, existingAlert.getAlertId());
                }
            }

            // 5. 清理长时间已解决的预警记录（保留30天内的记录）
            cleanupOldResolvedAlerts(conn, activeAlertKeys);

            conn.commit(); // 提交事务
            System.out.println("库存预警更新任务完成");

        } catch (SQLException e) {
            // 事务回滚
            try {
                if (conn != null) {
                    conn.rollback();
                    System.err.println("库存预警更新任务回滚: " + e.getMessage());
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
        } catch (Exception e) {
            // 未知异常处理
            try {
                if (conn != null) {
                    conn.rollback();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            System.err.println("库存预警更新任务异常: " + e.getMessage());
            e.printStackTrace();
        } finally {
            // 6. 关闭数据库资源
            closeResources(rs, pstmt, conn);
        }
    }

    /**
     * 获取数据库连接
     */
    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
    }

    /**
     * 获取产品在仓库的现有预警记录
     */
    private InventoryAlert getExistingAlert(Connection conn, int productId, int warehouseId) throws SQLException {
        String sql = "SELECT * FROM tb_inventory_alert " +
                "WHERE productId = ? AND warehouseId = ? " +
                "ORDER BY alertTime DESC LIMIT 1";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, productId);
            pstmt.setInt(2, warehouseId);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    InventoryAlert alert = new InventoryAlert();
                    alert.setAlertId(rs.getInt("alertId"));
                    alert.setProductId(productId);
                    alert.setWarehouseId(warehouseId);
                    alert.setAlertType(rs.getString("alertType"));
                    alert.setAlertStatus(rs.getInt("alertStatus"));
                    alert.setAlertTime(rs.getTimestamp("alertTime"));
                    return alert;
                }
            }
        }
        return null;
    }

    /**
     * 创建新的库存预警记录
     */
    private void createNewAlert(Connection conn, int productId, int warehouseId, int currentStock, int safetyStock) throws SQLException {
        String sql = "INSERT INTO tb_inventory_alert " +
                "(productId, warehouseId, alertType, alertMessage, alertStatus, alertTime) " +
                "VALUES (?, ?, ?, ?, 0, NOW())";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, productId);
            pstmt.setInt(2, warehouseId);
            pstmt.setString(3, "LOW_STOCK"); // 低库存预警类型
            pstmt.setString(4, "库存不足: 当前库存 " + currentStock + ", 安全库存 " + safetyStock);
            pstmt.executeUpdate();
        }
    }

    /**
     * 标记预警为已解决
     */
    private void resolveAlert(Connection conn, int alertId) throws SQLException {
        String sql = "UPDATE tb_inventory_alert " +
                "SET alertStatus = 1, processTime = NOW(), processNote = '库存已恢复正常' " +
                "WHERE alertId = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, alertId);
            pstmt.executeUpdate();
        }
    }

    /**
     * 重新激活已解决的预警
     */
    private void reactivateAlert(Connection conn, int alertId) throws SQLException {
        String sql = "UPDATE tb_inventory_alert " +
                "SET alertStatus = 0, alertTime = NOW() " +
                "WHERE alertId = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, alertId);
            pstmt.executeUpdate();
        }
    }

    /**
     * 清理长时间已解决的预警记录（保留30天内的记录）
     */
    private void cleanupOldResolvedAlerts(Connection conn, Set<String> activeAlertKeys) throws SQLException {
        // 构建活跃预警条件
        StringBuilder activeAlertCondition = new StringBuilder();
        if (!activeAlertKeys.isEmpty()) {
            activeAlertCondition.append(" AND CONCAT(productId, '_', warehouseId) NOT IN (");
            int count = 0;
            for (String key : activeAlertKeys) {
                if (count > 0) activeAlertCondition.append(", ");
                activeAlertCondition.append("'").append(key).append("'");
                count++;
            }
            activeAlertCondition.append(")");
        }

        // 清理30天前已解决且不在活跃列表中的预警
        String sql = "DELETE FROM tb_inventory_alert " +
                "WHERE alertStatus = 1 " +
                "AND processTime < DATE_SUB(NOW(), INTERVAL 30 DAY)" +
                activeAlertCondition.toString();

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.executeUpdate();
        }
    }

    /**
     * 关闭数据库资源
     */
    private void closeResources(ResultSet rs, Statement stmt, Connection conn) {
        try {
            if (rs != null) rs.close();
            if (stmt != null) stmt.close();
            if (conn != null) conn.close();
        } catch (SQLException e) {
            System.err.println("关闭数据库资源异常: " + e.getMessage());
            e.printStackTrace();
        }
    }
}