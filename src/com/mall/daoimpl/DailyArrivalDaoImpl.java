package com.mall.daoimpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.mall.dao.DailyArrivalDao;
import com.mall.common.DbUtil;
import com.mall.po.DailyArrival;
import com.mall.po.DailyArrivalPager;
import com.mall.po.OrderStatus;
import com.mall.po.StorageStatus;

public class DailyArrivalDaoImpl implements DailyArrivalDao {
    private Connection conn = null;
    private PreparedStatement pstmt = null;
    private ResultSet rs = null;


    @Override
    public DailyArrivalPager getDailyArrivalPager(int offset, int pageSize, String date, String supplier) {
        DailyArrivalPager pager = new DailyArrivalPager();
        Connection localConn = null;

        try {
            DbUtil db = new DbUtil();
            localConn = db.getCon();

            // 获取总数
            int totalCount = getDailyArrivalCount(localConn, date, supplier);
            pager.setTotalCount(totalCount);

            // 计算总页数
            int totalPages = (totalCount + pageSize - 1) / pageSize;
            pager.setTotalPages(totalPages);

            // 获取数据列表
            List<DailyArrival> arrivalList = getDailyArrivalList(localConn, offset, pageSize, date, supplier);
            pager.setArrivalList(arrivalList);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeResources(localConn);
        }

        return pager;
    }
    @Override
    public int getDailyArrivalCount(Connection conn, String date, String supplier) throws SQLException {
        StringBuilder sql = new StringBuilder("SELECT COUNT(*) FROM tb_material_order WHERE 1=1");
        List<Object> params = new ArrayList<>();

        // 筛选条件：已发货或已完成的订单
        sql.append(" AND status IN ('SHIPPED', 'COMPLETED')");

        if (date != null && !date.isEmpty()) {
            sql.append(" AND expected_arrival_date = ?");
            params.add(date);
        }

        if (supplier != null && !supplier.isEmpty()) {
            sql.append(" AND supplier_name LIKE ?");
            params.add("%" + supplier + "%");
        }

        int count = 0;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            pstmt = conn.prepareStatement(sql.toString());

            for (int i = 0; i < params.size(); i++) {
                pstmt.setObject(i + 1, params.get(i));
            }

            rs = pstmt.executeQuery();
            if (rs.next()) {
                count = rs.getInt(1);
            }
        } finally {
            closeResources(null, pstmt, rs);
        }

        return count;
    }



    // 优化DailyArrivalDaoImpl中的查询逻辑
    @Override
    public List<DailyArrival> getDailyArrivalList(Connection conn, int offset, int pageSize, String date, String supplier) throws SQLException {
        List<DailyArrival> arrivalList = new ArrayList<>();
        StringBuilder sql = new StringBuilder("SELECT * FROM tb_material_order WHERE 1=1");
        List<Object> params = new ArrayList<>();

        // 筛选条件：已发货或已完成的订单
        sql.append(" AND status IN ('SHIPPED', 'COMPLETED')");

        if (date != null && !date.isEmpty()) {
            sql.append(" AND expectedArrivalDate = ?");
            params.add(date);
        }

        if (supplier != null && !supplier.isEmpty()) {
            sql.append(" AND supplierName LIKE ?");
            params.add("%" + supplier + "%");
        }

        sql.append(" ORDER BY expectedArrivalDate, orderTime LIMIT ?, ?");
        params.add(offset);
        params.add(pageSize);

        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            pstmt = conn.prepareStatement(sql.toString());

            for (int i = 0; i < params.size(); i++) {
                pstmt.setObject(i + 1, params.get(i));
            }









            rs = pstmt.executeQuery();
            while (rs.next()) {
                DailyArrival arrival = new DailyArrival();
                // 从订单表获取数据
                arrival.setArrivalId(rs.getInt("orderId"));
                arrival.setOrderNo(rs.getString("orderNo"));
                String productName = getOrderProductName(conn, rs.getInt("orderId"));
                arrival.setProductName(productName);


                String supplierName = getOrderSupplierName(conn, rs.getInt("orderId"));
                arrival.setSupplierName(supplierName);

                arrival.setQuantity(getOrderItemQuantity(conn, rs.getInt("orderId")));
                arrival.setArrivalDate(rs.getDate("expectedArrivalDate"));

                // 设置订单状态
                String status = rs.getString("status");
                if (status.equals(OrderStatus.SHIPPED.name())) {
                    arrival.setStatus("已发货");
                } else if (status.equals(OrderStatus.COMPLETED.name())) {
                    arrival.setStatus("已完成");
                } else {
                    arrival.setStatus("未知");
                }

                // 设置入库状态
                String storageStatus = rs.getString("storage_status");
                if (storageStatus != null && storageStatus.equals(StorageStatus.COMPLETED.name())) {
                    arrival.setStorageStatus("已入库");
                    arrival.setStorageTime(rs.getTimestamp("storage_time"));
                    arrival.setStorageOperator(rs.getString("storage_operator"));
                } else {
                    arrival.setStorageStatus("待入库");
                }

                arrivalList.add(arrival);
            }
        } finally {
            closeResources(null, pstmt, rs);
        }

        return arrivalList;
    }


    private String getOrderProductName(Connection conn, int orderId) throws SQLException {
        StringBuilder productNames = new StringBuilder();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            String sql = "SELECT DISTINCT productName FROM tb_material_order_item WHERE orderId = ? LIMIT 3";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, orderId);
            rs = pstmt.executeQuery();

            boolean first = true;
            while (rs.next() && productNames.length() < 50) { // 限制长度避免过长
                if (!first) productNames.append(", ");
                productNames.append(rs.getString("productName"));
                first = false;
            }

            if (productNames.length() == 0) {
                return "无产品信息";
            } else if (productNames.length() > 50) {
                return productNames.substring(0, 47) + "..."; // 超出长度显示省略号
            }
            return productNames.toString();
        } finally {
            closeResources(null, pstmt, rs);
        }
    }



    private String getOrderSupplierName(Connection conn, int orderId) throws SQLException {
        StringBuilder supplierName = new StringBuilder();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            String sql = "SELECT DISTINCT supplierName FROM tb_material_order WHERE orderId = ? LIMIT 3";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, orderId);
            rs = pstmt.executeQuery();

            boolean first = true;
            while (rs.next() && supplierName.length() < 50) { // 限制长度避免过长
                if (!first) supplierName.append(", ");
                supplierName.append(rs.getString("supplierName"));
                first = false;
            }

            if (supplierName.length() == 0) {
                return "无商家信息";
            } else if (supplierName.length() > 50) {
                return supplierName.substring(0, 47) + "..."; // 超出长度显示省略号
            }
            return supplierName.toString();
        } finally {
            closeResources(null, pstmt, rs);
        }
    }






    // 获取订单总数量
    private int getOrderItemQuantity(Connection conn, int orderId) throws SQLException {
        int quantity = 0;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            String sql = "SELECT SUM(quantity) FROM tb_material_order_item WHERE orderId = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, orderId);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                quantity = rs.getInt(1);
            }
        } finally {
            closeResources(null, pstmt, rs);
        }
        return quantity;
    }







    public boolean processStorage(int orderId, String operator) {
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            DbUtil db = new DbUtil();
            conn = db.getCon();
            conn.setAutoCommit(false); // 开启事务

            // 1. 更新订单入库状态
            String sql = "UPDATE tb_material_order " +
                    "SET storage_status = 'COMPLETED', " +
                    "    storage_time = NOW(), " +
                    "    storage_operator = ? " +
                    "WHERE order_id = ? AND storage_status = 'PENDING'";

            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, operator);
            pstmt.setInt(2, orderId);

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected == 0) {
                conn.rollback();
                return false;
            }

            // 2. 更新库存
            updateInventory(conn, orderId);

            conn.commit(); // 提交事务
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            try {
                if (conn != null) conn.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            return false;
        } finally {
            closeResources(conn, pstmt, null);
        }
    }

    // 更新库存
    private void updateInventory(Connection conn, int orderId) throws SQLException {
        String sql = "UPDATE tb_product_inventory pi " +
                "JOIN tb_material_order_item moi ON pi.productId = moi.productId " +
                "SET pi.quantity = pi.quantity + moi.quantity, " +
                "    pi.stockInTime = NOW() " +
                "WHERE moi.orderId = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, orderId);
            pstmt.executeUpdate();
        }
    }

    // 关闭资源
    private void closeResources(Connection conn) {
        try {
            if (rs != null) rs.close();
            if (pstmt != null) pstmt.close();
            if (conn != null) conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void closeResources(Connection conn, PreparedStatement pstmt, ResultSet rs) {
        try {
            if (rs != null) rs.close();
            if (pstmt != null) pstmt.close();
            if (conn != null) conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }












    // 统一关闭资源的方法
    private void closeResources() {
        try {
            if (rs != null) rs.close();
            if (pstmt != null) pstmt.close();
            if (conn != null) conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}