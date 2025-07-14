package com.mall.daoimpl;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

import com.mall.dao.InventoryAlertDao;
import com.mall.common.DbUtil;
import com.mall.po.InventoryAlert;
import com.mall.po.InventoryAlertPager;
import com.mall.po.Warehouse;
import jdk.nashorn.internal.objects.NativeMath;

public class InventoryAlertDaoImpl implements InventoryAlertDao {
    private Connection conn = null;
    private PreparedStatement pstmt = null;
    private ResultSet rs = null;

    @Override
    public InventoryAlertPager getInventoryAlertPager(int offset, int pageSize, String alertType,
                                                      String warehouseId, String productName) {
        InventoryAlertPager pager = new InventoryAlertPager();
        List<InventoryAlert> alertList = new ArrayList<>();

        try {
            // 获取数据库连接
            DbUtil db = new DbUtil();
            conn = db.getCon();

            // 1. 获取总记录数
            int totalCount = getInventoryAlertCount(alertType, warehouseId, productName);
            pager.setTotalCount(totalCount);

            // 2. 计算总页数
            int totalPages = (totalCount + pageSize - 1) / pageSize;
            pager.setTotalPages(totalPages);

            // 3. 获取预警数据列表（带多表关联）
            pager = getInventoryAlertList(offset, pageSize, alertType, warehouseId, productName);

            // 4. 获取仓库列表（用于筛选条件）
            InventoryAlertPager warehousePager = getAllWarehouses();
            pager.setWarehouseList(warehousePager.getWarehouseList());

            // 5. 获取各类预警统计数量
            InventoryAlertPager countPager = getAlertTypeCounts(alertType, warehouseId, productName);
            pager.setLowStockCount(countPager.getLowStockCount());
            pager.setExpiringCount(countPager.getExpiringCount());
            pager.setExpiredCount(countPager.getExpiredCount());

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeResources(); // 统一关闭资源
        }

        return pager;
    }

    @Override
    public int getInventoryAlertCount(String alertType, String warehouseId, String productName) {
        int count = 0;
        String sql = "SELECT COUNT(*) FROM tb_inventory_alert a " +
                "JOIN product st ON a.productId = st.product_id " +
                "JOIN tb_warehouse w ON a.warehouseId = w.warehouseId " +
                "WHERE 1=1";
        StringBuilder sb = new StringBuilder(sql);
        List<String> params = new ArrayList<>();

        // 拼接查询条件
        if (alertType != null && !alertType.isEmpty()) {
            sb.append(" AND a.alertType = ?");
            params.add(alertType);
        }

        if (warehouseId != null && !warehouseId.isEmpty()) {
            sb.append(" AND a.warehouseId = ?");
            params.add(warehouseId);
        }

        if (productName != null && !productName.isEmpty()) {
            sb.append(" AND st.productName LIKE ?");
            params.add("%" + productName + "%");
        }

        try {
            DbUtil db = new DbUtil();
            conn = db.getCon();
            pstmt = conn.prepareStatement(sb.toString());
            setParams(pstmt, params); // 统一设置参数
            rs = pstmt.executeQuery();

            if (rs.next()) {
                count = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeResources();
        }

        return count;
    }

    @Override
    public InventoryAlertPager getInventoryAlertList(int offset, int pageSize, String alertType,
                                                     String warehouseId, String productName) {
        InventoryAlertPager pager = new InventoryAlertPager();
        List<InventoryAlert> alertList = new ArrayList<>();

        // 多表关联查询，从tb_product_inventory统计库存数量
        String sql = "SELECT " +
                "a.alertId, a.productId, a.warehouseId, " +
                "st.productName AS productName, " +
                "w.warehouseName, " +
                "SUM(pi.quantity) AS currentStock, " + // 统计库存数量
                "COALESCE(pwss.safetyStock, 0) AS safetyStock, " + // 安全库存
                "MIN(pi.expiryDate) AS expiryDate, " + // 最早过期日期
                "a.alertType, a.alertTime " +
                "FROM tb_inventory_alert a " +
                "JOIN product st ON a.productId = st.product_id " +
                "JOIN tb_warehouse w ON a.warehouseId = w.warehouseId " +
                "JOIN tb_product_inventory pi ON a.productId = pi.productId AND a.warehouseId = pi.warehouseId " +
                "LEFT JOIN tb_product_warehouse_safety_stock pwss ON a.productId = pwss.productId AND a.warehouseId = pwss.warehouseId " +
                "WHERE 1=1";
        StringBuilder sb = new StringBuilder(sql);
        List<String> params = new ArrayList<>();

        // 拼接查询条件
        if (alertType != null && !alertType.isEmpty()) {
            sb.append(" AND a.alertType = ?");
            params.add(alertType);
        }

        if (warehouseId != null && !warehouseId.isEmpty()) {
            sb.append(" AND a.warehouseId = ?");
            params.add(warehouseId);
        }

        if (productName != null && !productName.isEmpty()) {
            sb.append(" AND st.productName LIKE ?");
            params.add("%" + productName + "%");
        }

        // 分组和排序
        sb.append(" GROUP BY a.alertId, a.productId, a.warehouseId, st.productName, w.warehouseName, pwss.safetyStock, a.alertType, a.alertTime ");
        sb.append(" ORDER BY a.alertTime DESC LIMIT ?, ?");

        try {
            DbUtil db = new DbUtil();
            conn = db.getCon();
            pstmt = conn.prepareStatement(sb.toString());

            // 设置查询条件参数
            setParams(pstmt, params);
            // 设置分页参数
            pstmt.setInt(params.size() + 1, offset);
            pstmt.setInt(params.size() + 2, pageSize);

            rs = pstmt.executeQuery();

            while (rs.next()) {
                InventoryAlert alert = new InventoryAlert();
                // 基础属性设置
                alert.setAlertId(rs.getInt("alertId"));
                alert.setProductId(rs.getInt("productId"));
                alert.setWarehouseId(rs.getInt("warehouseId"));
                alert.setProductName(rs.getString("productName"));
                alert.setWarehouseName(rs.getString("warehouseName"));
                alert.setCurrentStock(rs.getInt("currentStock")); // 从统计结果获取库存
                alert.setQuantity(rs.getInt("currentStock")); // 库存数量
                alert.setSafetyStock(rs.getInt("safetyStock"));

                // 处理日期类型
                alert.setExpiryDate(rs.getDate("expiryDate"));
                alert.setAlertType(rs.getString("alertType"));
                alert.setAlertTime(rs.getTimestamp("alertTime"));

                // 计算预警级别
                setAlertLevel(alert);

                alertList.add(alert);
            }

            pager.setAlertList(alertList);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResources();
        }

        return pager;
    }

    @Override
    public InventoryAlertPager getAllWarehouses() {
        InventoryAlertPager pager = new InventoryAlertPager();
        List<Warehouse> warehouseList = new ArrayList<>();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            DbUtil db = new DbUtil();
            conn = db.getCon();
            String sql = "SELECT * FROM tb_warehouse ORDER BY warehouseId";
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                Warehouse warehouse = new Warehouse();
                warehouse.setWarehouseId(rs.getInt("warehouseId"));
                warehouse.setWarehouseCode(rs.getString("warehouseCode"));
                warehouse.setWarehouseName(rs.getString("warehouseName"));
                warehouse.setLocation(rs.getString("location"));
                warehouse.setCapacity(rs.getInt("capacity"));

                // 修复：在ResultSet关闭前获取所有需要的数据
                warehouse.setCurrentStock(getWarehouseCurrentStock(rs.getInt("warehouseId")));
                warehouse.setLastInspectionDate(rs.getDate("lastInspectionDate"));
                warehouse.setNextInspectionDate(rs.getDate("nextInspectionDate"));
                warehouse.setStatus(rs.getInt("status"));
                warehouse.setCreateTime(rs.getTimestamp("createTime"));
                warehouse.setUpdateTime(rs.getTimestamp("updateTime"));

                warehouseList.add(warehouse);
            }

            pager.setWarehouseList(warehouseList);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 确保资源在finally块中关闭
            try {
                if (rs != null) rs.close();
                if (pstmt != null) pstmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return pager;
    }

    /**
     * 获取仓库当前库存（从tb_product_inventory统计）
     */
    private int getWarehouseCurrentStock(int warehouseId) {
        int currentStock = 0;
        String sql = "SELECT SUM(quantity) FROM tb_product_inventory WHERE warehouseId = ?";
        DbUtil db = new DbUtil();
        try (Connection conn = db.getCon();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, warehouseId);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                currentStock = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return currentStock;
    }

    @Override
    public InventoryAlertPager getAlertTypeCounts(String alertType, String warehouseId, String productName) {
        InventoryAlertPager pager = new InventoryAlertPager();
        String sql = "SELECT " +
                "SUM(CASE WHEN a.alertType = 'LOW_STOCK' THEN 1 ELSE 0 END) as lowStockCount, " +
                "SUM(CASE WHEN a.alertType = 'EXPIRING_SOON' THEN 1 ELSE 0 END) as expiringCount, " +
                "SUM(CASE WHEN a.alertType = 'EXPIRED' THEN 1 ELSE 0 END) as expiredCount " +
                "FROM tb_inventory_alert a " +
                "JOIN product st ON a.productId = st.product_id " +
                "JOIN tb_warehouse w ON a.warehouseId = w.warehouseId " +
                "WHERE 1=1";
        StringBuilder sb = new StringBuilder(sql);
        List<String> params = new ArrayList<>();

        // 拼接查询条件
        if (alertType != null && !alertType.isEmpty()) {
            sb.append(" AND a.alertType = ?");
            params.add(alertType);
        }

        if (warehouseId != null && !warehouseId.isEmpty()) {
            sb.append(" AND a.warehouseId = ?");
            params.add(warehouseId);
        }

        if (productName != null && !productName.isEmpty()) {
            sb.append(" AND st.productName LIKE ?");
            params.add("%" + productName + "%");
        }

        try {
            DbUtil db = new DbUtil();
            conn = db.getCon();
            pstmt = conn.prepareStatement(sb.toString());
            setParams(pstmt, params);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                pager.setLowStockCount(rs.getInt("lowStockCount"));
                pager.setExpiringCount(rs.getInt("expiringCount"));
                pager.setExpiredCount(rs.getInt("expiredCount"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResources();
        }

        return pager;
    }

    // ====================== 工具方法 ======================
    /**
     * 统一设置PreparedStatement参数
     */
    private void setParams(PreparedStatement pstmt, List<String> params) throws SQLException {
        for (int i = 0; i < params.size(); i++) {
            pstmt.setString(i + 1, params.get(i));
        }
    }

//    /**
//     * 计算预警级别（基于库存数量、安全库存和过期日期）
//     */
//    private void setAlertLevel(InventoryAlert alert) {
//        int quantity = alert.getQuantity();
//        int safetyStock = alert.getSafetyStock();
//        java.util.Date expiryDate = alert.getExpiryDate();
//        java.util.Date today = new java.util.Date();
//
//        // 已过期产品设为高优先级
//        if (expiryDate != null && expiryDate.before(today)) {
//            alert.setAlertLevel("high");
//        }
//        // 7天内过期设为中优先级
//        else if (expiryDate != null && expiryDate.getTime() - today.getTime() < 7 * 24 * 60 * 60 * 1000) {
//            alert.setAlertLevel("medium");
//        }
//        // 库存低于安全库存
//        else if (quantity < safetyStock) {
//            if (quantity < safetyStock * 0.5) {
//                alert.setAlertLevel("high"); // 低于50%安全库存设为高优先级
//            } else {
//                alert.setAlertLevel("medium"); // 低于安全库存设为中优先级
//            }
//        }
//        // 正常情况设为低优先级
//        else {
//            alert.setAlertLevel("low");
//        }
//    }

    /**
     * 统一关闭数据库资源
     */
    private void closeResources() {
        try {
            if (rs != null) rs.close();
            if (pstmt != null) pstmt.close();
            if (conn != null) conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


//    @Override
//    public void updateInventoryAlerts() {
//        Connection conn = null;
//        PreparedStatement pstmt = null;
//        ResultSet rs = null;
//
//        try {
//            DbUtil db = new DbUtil();
//            conn = db.getCon();
//            conn.setAutoCommit(false); // 开启事务
//
//            // 1. 查询所有产品和仓库的库存与安全库存
//            String sql = "SELECT " +
//                    "pi.productId, pi.warehouseId, " +
//                    "SUM(pi.quantity) AS currentStock, " +
//                    "pwss.safetyStock " +
//                    "FROM tb_product_inventory pi " +
//                    "JOIN tb_product_warehouse_safety_stock pwss " +
//                    "ON pi.productId = pwss.productId AND pi.warehouseId = pwss.warehouseId " +
//                    "GROUP BY pi.productId, pi.warehouseId";
//
//            pstmt = conn.prepareStatement(sql);
//            rs = pstmt.executeQuery();
//
//            // 2. 处理每个产品和仓库的预警状态
//            while (rs.next()) {
//                int productId = rs.getInt("productId");
//                int warehouseId = rs.getInt("warehouseId");
//                int currentStock = rs.getInt("currentStock");
//                int safetyStock = rs.getInt("safetyStock");
//
//                // 计算是否需要预警
//                boolean needAlert = currentStock < safetyStock;
//
//                // 3. 查询该产品和仓库是否已有预警
//                InventoryAlert existingAlert = getAlertByProductAndWarehouse(conn, productId, warehouseId);
//
//                if (needAlert) {
//                    // 需要预警，若不存在则添加，若存在则更新
//                    if (existingAlert == null) {
//                        addNewAlert(conn, productId, warehouseId, currentStock, safetyStock);
//                    } else {
//                        updateExistingAlert(conn, existingAlert, currentStock);
//                    }
//                } else {
//                    // 不需要预警，若存在预警则标记为已解决
//                    if (existingAlert != null) {
//                        resolveAlert(conn, existingAlert.getAlertId());
//                    }
//                }
//            }
//
//            conn.commit(); // 提交事务
//        } catch (SQLException e) {
//            try {
//                if (conn != null) conn.rollback(); // 事务回滚
//            } catch (SQLException ex) {
//                ex.printStackTrace();
//            }
//            e.printStackTrace();
//        } catch (Exception e) {
//            try {
//                if (conn != null) conn.rollback();
//            } catch (SQLException ex) {
//                ex.printStackTrace();
//            }
//            e.printStackTrace();
//        } finally {
//            try {
//                if (rs != null) rs.close();
//                if (pstmt != null) pstmt.close();
//                if (conn != null) conn.close();
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//        }
//    }

    /**
     * 根据产品和仓库查询现有预警
     */
    private InventoryAlert getAlertByProductAndWarehouse(Connection conn, int productId, int warehouseId)
            throws SQLException {
        String sql = "SELECT * FROM tb_inventory_alert " +
                "WHERE productId = ? AND warehouseId = ? AND alertStatus = 0"; // 未处理的预警
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setInt(1, productId);
        pstmt.setInt(2, warehouseId);
        ResultSet rs = pstmt.executeQuery();

        if (rs.next()) {
            InventoryAlert alert = new InventoryAlert();
            alert.setAlertId(rs.getInt("alertId"));
            alert.setProductId(rs.getInt("productId"));
            alert.setWarehouseId(rs.getInt("warehouseId"));
            alert.setAlertType(rs.getString("alertType"));
            alert.setAlertMessage(rs.getString("alertMessage"));
            alert.setAlertStatus(rs.getInt("alertStatus"));
            alert.setAlertTime(rs.getTimestamp("alertTime"));
            return alert;
        }
        return null;
    }

    /**
     * 添加新预警
     */
    private void addNewAlert(Connection conn, int productId, int warehouseId, int currentStock, int safetyStock)
            throws SQLException {
        String alertType = "LOW_STOCK";
        String alertMessage = "库存不足: 当前库存 " + currentStock + ", 安全库存 " + safetyStock;

        String sql = "INSERT INTO tb_inventory_alert " +
                "(productId, warehouseId, alertType, alertMessage, alertStatus, alertTime) " +
                "VALUES (?, ?, ?, ?, 0, NOW())";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setInt(1, productId);
        pstmt.setInt(2, warehouseId);
        pstmt.setString(3, alertType);
        pstmt.setString(4, alertMessage);
        pstmt.executeUpdate();
    }

    /**
     * 更新现有预警
     */
    private void updateExistingAlert(Connection conn, InventoryAlert alert, int currentStock)
            throws SQLException {
        String alertMessage = "库存不足: 当前库存 " + currentStock + ", 安全库存 " + alert.getSafetyStock();

        String sql = "UPDATE tb_inventory_alert " +
                "SET alertMessage = ?, alertTime = NOW() " +
                "WHERE alertId = ?";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, alertMessage);
        pstmt.setInt(2, alert.getAlertId());
        pstmt.executeUpdate();
    }

    /**
     * 标记预警为已解决
     */
    private void resolveAlert(Connection conn, int alertId) throws SQLException {
        String sql = "UPDATE tb_inventory_alert " +
                "SET alertStatus = 1, processTime = NOW(), processNote = '库存已恢复正常' " +
                "WHERE alertId = ?";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setInt(1, alertId);
        pstmt.executeUpdate();
    }

    // 在InventoryAlertDaoImpl中
    @Override
    public void triggerImmediateUpdate() {
        updateInventoryAlerts();
    }


    private static final ConcurrentHashMap<String, Integer> stockCache = new ConcurrentHashMap<>();
    private static final long CACHE_TIMEOUT = 5; // 缓存过期时间5分钟

    @Override
    public void updateInventoryAlerts() {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            DbUtil db = new DbUtil();
            conn = db.getCon();
            conn.setAutoCommit(false);

            // 1. 先查询所有需要监控的产品和仓库组合
            String sql = "SELECT productId, warehouseId FROM tb_product_warehouse_safety_stock";
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();

            List<String> productWarehousePairs = new ArrayList<>();
            while (rs.next()) {
                int productId = rs.getInt("productId");
                int warehouseId = rs.getInt("warehouseId");
                productWarehousePairs.add(productId + "_" + warehouseId);
            }

            // 2. 批量处理每个产品和仓库组合
            for (String pair : productWarehousePairs) {
                String[] parts = pair.split("_");
                int productId = Integer.parseInt(parts[0]);
                int warehouseId = Integer.parseInt(parts[1]);

                // 3. 从缓存获取库存，减少数据库查询
                int currentStock = getStockFromCache(productId, warehouseId);

                // 4. 获取安全库存
                int safetyStock = getSafetyStock(conn, productId, warehouseId);

                // 5. 计算预警状态（与原逻辑相同）
                boolean needAlert = currentStock < safetyStock;
                InventoryAlert existingAlert = getAlertByProductAndWarehouse(conn, productId, warehouseId);

                if (needAlert) {
                    if (existingAlert == null) {
                        addNewAlert(conn, productId, warehouseId, currentStock, safetyStock);
                    } else {
                        updateExistingAlert(conn, existingAlert, currentStock);
                    }
                } else {
                    if (existingAlert != null) {
                        resolveAlert(conn, existingAlert.getAlertId());
                    }
                }
            }

            conn.commit();
        } catch (SQLException e) {
            // 事务回滚
            rollback(conn);
            e.printStackTrace();
        } catch (Exception e) {
            rollback(conn);
            e.printStackTrace();
        } finally {
            closeResources(conn, pstmt, rs);
        }
    }

    /**
     * 从缓存获取库存，减少数据库查询
     */
    private int getStockFromCache(int productId, int warehouseId) {
        String key = productId + "_" + warehouseId;
        Long currentTime = System.currentTimeMillis();

        // 检查缓存是否存在且未过期
        if (stockCache.containsKey(key)) {
            int value = stockCache.get(key);
            // 缓存结构：高32位为时间戳，低32位为库存值
            long cacheTime = (long)value >>> 32;
            if (currentTime - cacheTime < TimeUnit.MINUTES.toMillis(CACHE_TIMEOUT)) {
                return value & 0xFFFFFFFF; // 获取库存值
            }
        }

        // 缓存不存在或已过期，查询数据库
        int stock = queryStockFromDB(productId, warehouseId);
        // 存储缓存：高32位为时间戳，低32位为库存值
        stockCache.put(key, (int)(currentTime << 32 | (stock & 0xFFFFFFFF)));
        return stock;
    }

    /**
     * 查询数据库获取库存
     */
    private int queryStockFromDB(int productId, int warehouseId) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        int stock = 0;

        try {
            DbUtil db = new DbUtil();
            conn = db.getCon();
            String sql = "SELECT SUM(quantity) FROM tb_product_inventory " +
                    "WHERE productId = ? AND warehouseId = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, productId);
            pstmt.setInt(2, warehouseId);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                stock = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResources(conn, pstmt, rs);
        }

        return stock;
    }

    /**
     * 获取安全库存
     */
    private int getSafetyStock(Connection conn, int productId, int warehouseId) throws SQLException {
        String sql = "SELECT safetyStock FROM tb_product_warehouse_safety_stock " +
                "WHERE productId = ? AND warehouseId = ?";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setInt(1, productId);
        pstmt.setInt(2, warehouseId);
        ResultSet rs = pstmt.executeQuery();

        if (rs.next()) {
            return rs.getInt("safetyStock");
        }
        return 0;
    }

    /**
     * 事务回滚
     */
    private void rollback(Connection conn) {
        if (conn != null) {
            try {
                conn.rollback();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 关闭资源
     */
    private void closeResources(Connection conn, PreparedStatement pstmt, ResultSet rs) {
        try {
            if (rs != null) rs.close();
            if (pstmt != null) pstmt.close();
            if (conn != null) conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



    @Override
    public boolean ignoreAlert(int alertId) {
        Connection conn = null;
        PreparedStatement stmt = null;
        boolean success = false;
        boolean originalAutoCommit = true;

        try {
            // 获取数据库连接
            DbUtil db = new DbUtil();
            conn = db.getCon();

            // 保存原始自动提交状态并关闭自动提交
            originalAutoCommit = conn.getAutoCommit();
            conn.setAutoCommit(false);

            // 构建更新SQL语句
            String sql = "UPDATE tb_inventory_alert " +
                    "SET alertStatus = 2, processTime = NOW(), processNote = '已忽略' " +
                    "WHERE alertId = ?";

            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, alertId);

            // 执行更新操作
            int affectedRows = stmt.executeUpdate();

            if (affectedRows > 0) {
                // 操作成功则提交事务
                conn.commit();
                success = true;
            } else {
                // 未影响任何行则回滚
                conn.rollback();
            }
        } catch (SQLException e) {
            // 发生异常时回滚事务
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            e.printStackTrace();
        } finally {
            // 恢复自动提交状态并关闭资源
            try {
                if (conn != null) {
                    conn.setAutoCommit(originalAutoCommit);
                    if (stmt != null) stmt.close();
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return success;
    }

    @Override
    public Map<String, Object> getAlertDetail(int alertId) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Map<String, Object> detail = new HashMap<>();

        try {
            DbUtil db = new DbUtil();
            conn = db.getCon();
            String sql = "SELECT " +
                    "ta.alertId, ta.productId, ta.warehouseId, ta.alertType, " +
                    "ta.alertMessage, ta.alertStatus, ta.alertTime, " +
                    "ta.processTime, ta.processNote, " +
                    "COALESCE(st.productName, '未知产品') AS productName, " +
                    "COALESCE(w.warehouseName, '未知仓库') AS warehouseName, " +
                    "COALESCE(SUM(pi.quantity), 0) AS currentStock, " +
                    "COALESCE(pwss.safetyStock, 0) AS safetyStock, " +
                    "MIN(pi.expiryDate) AS expiryDate " +
                    "FROM tb_inventory_alert ta " +
                    "LEFT JOIN product st ON ta.productId = st.product_id " +
                    "LEFT JOIN tb_warehouse w ON ta.warehouseId = w.warehouseId " +
                    "LEFT JOIN tb_product_inventory pi ON ta.productId = pi.productId AND ta.warehouseId = pi.warehouseId " +
                    "LEFT JOIN tb_product_warehouse_safety_stock pwss ON ta.productId = pwss.productId AND ta.warehouseId = pwss.warehouseId " +
                    "WHERE ta.alertId = ? " +
                    "GROUP BY ta.alertId, ta.productId, ta.warehouseId, ta.alertType, " +
                    "ta.alertMessage, ta.alertStatus, ta.alertTime, ta.processTime, ta.processNote, " +
                    "st.productName, w.warehouseName, pwss.safetyStock";

            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, alertId);
            rs = stmt.executeQuery();

            if (rs.next()) {
                detail.put("alertId", rs.getInt("alertId"));
                detail.put("productId", rs.getInt("productId"));
                detail.put("warehouseId", rs.getInt("warehouseId"));
                detail.put("alertType", rs.getString("alertType"));
                detail.put("alertMessage", rs.getString("alertMessage"));
                detail.put("alertStatus", rs.getInt("alertStatus"));
                detail.put("alertTime", rs.getTimestamp("alertTime"));
                detail.put("processTime", rs.getTimestamp("processTime"));
                detail.put("processNote", rs.getString("processNote"));
                detail.put("productName", rs.getString("productName"));
                detail.put("warehouseName", rs.getString("warehouseName"));
                detail.put("currentStock", rs.getInt("currentStock"));
                detail.put("safetyStock", rs.getInt("safetyStock"));

                // 处理可能为NULL的过期日期
                Date expiryDate = rs.getDate("expiryDate");
                detail.put("expiryDate", expiryDate != null ? new java.util.Date(expiryDate.getTime()) : null);

                // 计算预警级别
                setAlertLevelForMap(detail);

                // 修正URL参数拼写
                detail.put("redirectUrl", "getInventoryAlertDetailServlet?alertId=" + alertId);
            } else {
                detail.put("error", "未找到ID为 " + alertId + " 的预警记录");
            }
        } catch (SQLException e) {
            detail.put("error", "查询异常: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return detail;
    }




    private void setAlertLevelForMap(Map<String, Object> detail) {
        int currentStock = (int) detail.getOrDefault("currentStock", 0);
        int safetyStock = (int) detail.getOrDefault("safetyStock", 0);
        java.util.Date expiryDate = (java.util.Date) detail.get("expiryDate");
        java.util.Date today = new java.util.Date();

        if (expiryDate != null && expiryDate.before(today)) {
            detail.put("alertLevel", "high");
        } else if (expiryDate != null && expiryDate.getTime() - today.getTime() < 7 * 24 * 60 * 60 * 1000) {
            detail.put("alertLevel", "medium");
        } else if (currentStock < safetyStock) {
            if (currentStock < safetyStock * 0.5) {
                detail.put("alertLevel", "high");
            } else {
                detail.put("alertLevel", "medium");
            }
        } else {
            detail.put("alertLevel", "low");
        }
    }

    /**
     * 计算预警级别（保留InventoryAlert版本，用于其他场景）
     */
    private void setAlertLevel(InventoryAlert alert) {
        int quantity = alert.getQuantity();
        int safetyStock = alert.getSafetyStock();
        java.util.Date expiryDate = alert.getExpiryDate();
        java.util.Date today = new java.util.Date();

        if (expiryDate != null && expiryDate.before(today)) {
            alert.setAlertLevel("high");
        } else if (expiryDate != null && expiryDate.getTime() - today.getTime() < 7 * 24 * 60 * 60 * 1000) {
            alert.setAlertLevel("medium");
        } else if (quantity < safetyStock) {
            if (quantity < safetyStock * 0.5) {
                alert.setAlertLevel("high");
            } else {
                alert.setAlertLevel("medium");
            }
        } else {
            alert.setAlertLevel("low");
        }
    }


    @Override
    public List<Map<String, Object>> getSuppliersByProductId(int productId) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Map<String, Object>> supplierList = new ArrayList<>();

        try {
            DbUtil db = new DbUtil();
            conn = db.getCon();
            String sql = "SELECT s.*, ps.unitPrice, ps.leadTime, ps.minOrderQuantity " +
                    "FROM tb_product_supplier ps " +
                    "JOIN tb_supplier s ON ps.supplierId = s.supplierId " +
                    "WHERE ps.productId = ? " +
                    "ORDER BY ps.supplierId ASC";

            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, productId);
            rs = stmt.executeQuery();

            while (rs.next()) {
                Map<String, Object> supplier = new HashMap<>();
                supplier.put("supplierId", rs.getInt("supplierId"));
                supplier.put("supplierName", rs.getString("supplierName"));
                supplier.put("contactPerson", rs.getString("contactPerson"));
                supplier.put("contactPhone", rs.getString("contactPhone"));
                supplier.put("unitPrice", rs.getBigDecimal("unitPrice"));
                supplier.put("leadTime", rs.getInt("leadTime"));
                supplier.put("minOrderQuantity", rs.getInt("minOrderQuantity"));
                supplierList.add(supplier);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                conn.close();
            }
            catch (SQLException e) {
                e.printStackTrace();
            }

        }

        return supplierList;
    }



    // 在InventoryAlertDaoImpl中实现


    public boolean createMaterialOrder(
            String orderNo, int productId, String productName,
            int quantity, int supplierId, Date expectedArrivalDate) throws SQLException {
        DbUtil db = new DbUtil();
        boolean success = false;

        try {
            db.beginTransaction(); // 开启事务

            // 1. 查询供应商信息
            String supplierSql = "SELECT s.supplierName, s.contactPerson, s.contactPhone, ps.unitPrice " +
                    "FROM tb_product_supplier ps " +
                    "JOIN tb_supplier s ON ps.supplierId = s.supplierId " +
                    "WHERE ps.productId = ? AND ps.supplierId = ? AND ps.status = 1";
            ResultSet rs = db.executeQuery(supplierSql, productId, supplierId);

            if (!rs.next()) {
                throw new SQLException("供应商不可用");
            }

            String supplierName = rs.getString("supplierName");
            String contactPerson = rs.getString("contactPerson");
            String contactPhone = rs.getString("contactPhone");
            BigDecimal unitPrice = rs.getBigDecimal("unitPrice");
            BigDecimal subtotal = unitPrice.multiply(new BigDecimal(quantity));

            // 2. 创建订单主记录
            String orderSql = "INSERT INTO tb_material_order " +
                    "(orderNo, supplierId, supplierName, contactPerson, contactPhone, " +
                    "orderTime, expectedArrivalDate, totalAmount, status, remark) " +
                    "VALUES (?, ?, ?, ?, ?, NOW(), ?, ?, 'PENDING', '由库存预警创建')";
            db.executeUpdate(orderSql, orderNo, supplierId, supplierName, contactPerson,
                    contactPhone, expectedArrivalDate, subtotal);

            // 3. 获取订单ID
            int orderId = db.getLastInsertId();

            // 4. 创建订单项记录
            String itemSql = "INSERT INTO tb_material_order_item " +
                    "(orderId, productId, productName, unitPrice, quantity, subtotal) " +
                    "VALUES (?, ?, ?, ?, ?, ?)";
            db.executeUpdate(itemSql, orderId, productId, productName, unitPrice, quantity, subtotal);

            db.commit(); // 提交事务
            success = true;
        } catch (SQLException e) {
            db.rollback(); // 回滚事务
            e.printStackTrace();
        } finally {
            db.close(); // 关闭资源
        }

        return success;
    }
}