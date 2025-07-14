package com.mall.daoimpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import com.mall.common.DbUtil;
import com.mall.dao.InventoryDao;
import com.mall.po.Inventory;
import com.mall.po.SubType;

public class InventoryDaoImpl extends InventoryDao {

    private Connection conn = null;
    private PreparedStatement pstmt = null;
    private ResultSet rs = null;
    private DbUtil db = new DbUtil();

    @Override
    public boolean addInventory(int arrivalId, int warehouseId) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        boolean result = false;

        try {
            conn = db.getCon();
            conn.setAutoCommit(false);  // 开启事务

            // 1. 查询到货记录获取产品信息
            String arrivalSql = "SELECT da.orderId, da.productName, da.quantity, " +
                    "da.supplierName, st.product_id " +
                    "FROM tb_daily_arrival da " +
                    "JOIN tb_material_order mo ON da.orderId = mo.orderId " +
                    "JOIN tb_material_order_item moi ON mo.orderId = moi.orderId " +
                    "JOIN product st ON moi.productId = st.product_id " +
                    "WHERE da.arrivalId = ?";
            pstmt = conn.prepareStatement(arrivalSql);
            pstmt.setInt(1, arrivalId);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                int orderId = rs.getInt("orderId");
                int productId = rs.getInt("subTypeId");
                int quantity = rs.getInt("quantity");

                // 2. 更新库存表
                String inventorySql = "INSERT INTO tb_product_inventory " +
                        "(productId, warehouseId, quantity, " +
                        "reorderPoint, status, stockInTime) " +
                        "VALUES (?, ?, ?, (SELECT safetyStock FROM tb_product_warehouse_safety_stock " +
                        "WHERE productId = ? AND warehouseId = ?), 1, NOW()) " +
                        "ON DUPLICATE KEY UPDATE " +
                        "quantity = quantity + VALUES(quantity), " +
                        "stockInTime = VALUES(stockInTime), " +
                        "status = 1";
                pstmt = conn.prepareStatement(inventorySql, Statement.RETURN_GENERATED_KEYS);
                pstmt.setInt(1, productId);
                pstmt.setInt(2, warehouseId);
                pstmt.setInt(3, quantity);
                pstmt.setInt(4, productId);
                pstmt.setInt(5, warehouseId);
                int affectedRows = pstmt.executeUpdate();

                if (affectedRows > 0) {
                    // 3. 更新到货记录状态为已入库
                    String updateStatusSql = "UPDATE tb_daily_arrival SET status = 'IN_STOCK' WHERE arrivalId = ?";
                    pstmt = conn.prepareStatement(updateStatusSql);
                    pstmt.setInt(1, arrivalId);
                    pstmt.executeUpdate();

                    // 4. 更新订单状态（如果所有到货都已入库）
                    updateOrderStatusIfComplete(conn, orderId);

                    conn.commit();
                    result = true;
                }
            }

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
            }
            catch (SQLException ex) {
                ex.printStackTrace();
            }

        }

        return result;
    }

    private void updateOrderStatusIfComplete(Connection conn, int orderId) throws SQLException {
        String sql = "SELECT COUNT(*) FROM tb_daily_arrival " +
                "WHERE orderId = ? AND status != 'IN_STOCK'";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, orderId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next() && rs.getInt(1) == 0) {
                    String updateSql = "UPDATE tb_material_order SET status = 'COMPLETED' WHERE orderId = ?";
                    try (PreparedStatement updateStmt = conn.prepareStatement(updateSql)) {
                        updateStmt.setInt(1, orderId);
                        updateStmt.executeUpdate();
                    }
                }
            }
        }
    }

    @Override
    public List<SubType> getProductList() {
        // 实现获取产品列表逻辑
        return null;
    }

    @Override
    public List<Integer> getWarehouseList() {
        // 实现获取仓库列表逻辑
        return null;
    }
}