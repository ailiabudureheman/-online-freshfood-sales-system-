package com.mall.scheduler;

import com.mall.common.DbUtil;
import com.mall.po.InventoryAlert;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.sql.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * 库存过期检查定时任务
 * 功能：定期检查库存过期状态，生成相应预警
 */
public class InventoryExpiryChecker implements Job {

//    // 数据库连接参数
//    private static final String DB_URL = "jdbc:mysql://localhost:3306/shop?serverTimezone=UTC&useSSL=false";
//    private static final String DB_USER = "root";
//    private static final String DB_PASSWORD = "123456";

    // 预警阈值配置
    private static final int EXPIRY_ALERT_DAYS = 30; // 提前30天发出过期预警
    private static final int URGENT_ALERT_DAYS = 7;  // 提前7天发出紧急预警

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        System.out.println("开始执行库存过期检查任务: " + new Date());

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            // 1. 获取数据库连接
//            conn = getConnection();
//            conn.setAutoCommit(false); // 开启事务
            DbUtil db = new DbUtil();
            conn= db.getCon();
            // 2. 查询所有库存记录
            String sql = "SELECT " +
                    "pi.inventoryId, " +
                    "pi.productId, " +
                    "pi.warehouseId, " +
                    "pi.expiryDate, " +
                    "pi.status, " +
                    "pi.quantity " +
                    "FROM tb_product_inventory pi " +
                    "WHERE pi.expiryDate IS NOT NULL";

            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();

            // 3. 存储已处理的库存ID，避免重复处理
            Set<Integer> processedInventoryIds = new HashSet<>();

            // 4. 处理每条库存记录
            while (rs.next()) {
                int inventoryId = rs.getInt("inventoryId");
                int productId = rs.getInt("productId");
                int warehouseId = rs.getInt("warehouseId");
                Date expiryDate = rs.getDate("expiryDate");
                int status = rs.getInt("status");
                int quantity = rs.getInt("quantity");

                // 跳过已处理的记录
                if (processedInventoryIds.contains(inventoryId)) {
                    continue;
                }
                processedInventoryIds.add(inventoryId);

                // 计算距离过期的天数
                int daysToExpiry = calculateDaysToExpiry(expiryDate);

                // 根据过期天数处理不同预警
                if (daysToExpiry <= 0) {
                    // 已过期
                    handleExpiredInventory(conn, inventoryId, productId, warehouseId, quantity);
                } else if (daysToExpiry <= URGENT_ALERT_DAYS) {
                    // 即将过期（紧急）
                    handleUrgentExpiryAlert(conn, inventoryId, productId, warehouseId, daysToExpiry, quantity);
                } else if (daysToExpiry <= EXPIRY_ALERT_DAYS) {
                    // 即将过期
                    handleExpiryAlert(conn, inventoryId, productId, warehouseId, daysToExpiry, quantity);
                }
            }

            conn.commit(); // 提交事务
            System.out.println("库存过期检查任务完成");

        } catch (SQLException e) {
            // 事务回滚
            try {
                if (conn != null) {
                    conn.rollback();
                    System.err.println("库存过期检查任务回滚: " + e.getMessage());
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
            System.err.println("库存过期检查任务异常: " + e.getMessage());
            e.printStackTrace();
        } finally {
            // 5. 关闭数据库资源
            try {
                conn.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * 计算距离过期的天数
     */
    private int calculateDaysToExpiry(Date expiryDate) {
        if (expiryDate == null) {
            return -1; // 无过期日期
        }

        long currentTime = new Date().getTime();
        long expiryTime = expiryDate.getTime();
        long diffMillis = expiryTime - currentTime;

        // 转换为天数
        return (int) (diffMillis / (24 * 60 * 60 * 1000));
    }

    /**
     * 处理已过期库存
     */
    private void handleExpiredInventory(Connection conn, int inventoryId, int productId,
                                        int warehouseId, int quantity) throws SQLException {
        // 1. 更新库存状态为已过期
        updateInventoryStatus(conn, inventoryId, -1); // -1表示已过期

        // 2. 生成已过期预警
        createExpiryAlert(conn, productId, warehouseId, quantity, 0, "已过期");
    }

    /**
     * 处理紧急过期预警（7天内过期）
     */
    private void handleUrgentExpiryAlert(Connection conn, int inventoryId, int productId,
                                         int warehouseId, int daysToExpiry, int quantity) throws SQLException {
        // 1. 生成紧急过期预警
        createExpiryAlert(conn, productId, warehouseId, quantity, daysToExpiry, "即将过期(紧急)");
    }

    /**
     * 处理普通过期预警（30天内过期）
     */
    private void handleExpiryAlert(Connection conn, int inventoryId, int productId,
                                   int warehouseId, int daysToExpiry, int quantity) throws SQLException {
        // 1. 生成普通过期预警
        createExpiryAlert(conn, productId, warehouseId, quantity, daysToExpiry, "即将过期");
    }

    /**
     * 更新库存状态
     */
    private void updateInventoryStatus(Connection conn, int inventoryId, int status) throws SQLException {
        String sql = "UPDATE tb_product_inventory " +
                "SET status = ? " +
                "WHERE inventoryId = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, status);
            pstmt.setInt(2, inventoryId);
            pstmt.executeUpdate();
        }
    }

    /**
     * 创建过期预警
     */
    private void createExpiryAlert(Connection conn, int productId, int warehouseId,
                                   int quantity, int daysToExpiry, String alertType) throws SQLException {
        String alertMessage;
        if (daysToExpiry <= 0) {
            alertMessage = "库存已过期，数量: " + quantity;
        } else if (daysToExpiry <= URGENT_ALERT_DAYS) {
            alertMessage = "库存即将在" + daysToExpiry + "天内过期，数量: " + quantity;
        } else {
            alertMessage = "库存将在" + daysToExpiry + "天内过期，数量: " + quantity;
        }

        String sql = "INSERT INTO tb_inventory_alert " +
                "(productId, warehouseId, alertType, alertMessage, alertStatus, alertTime) " +
                "VALUES (?, ?, ?, ?, 0, NOW())";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, productId);
            pstmt.setInt(2, warehouseId);
            pstmt.setString(3, alertType);
            pstmt.setString(4, alertMessage);
            pstmt.executeUpdate();
        }
    }


}