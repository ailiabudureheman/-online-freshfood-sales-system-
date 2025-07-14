package com.mall.daoimpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.mall.dao.MaterialOrderDao;
import com.mall.common.DbUtil;
import com.mall.po.MaterialOrder;
import com.mall.po.MaterialOrderItem;
import com.mall.po.MaterialOrderPager;
import com.mall.po.OrderStatus;

public class MaterialOrderDaoImpl implements MaterialOrderDao {
    private Connection conn = null;
    private PreparedStatement pstmt = null;
    private ResultSet rs = null;

    @Override
    public MaterialOrderPager getMaterialOrderPager(int offset, int pageSize,
                                                    String status, String orderNo,
                                                    String supplier) {
        MaterialOrderPager pager = new MaterialOrderPager();
        List<MaterialOrder> orderList = new ArrayList<>();
        Connection localConn = null;

        try {
            // 获取数据库连接
            DbUtil db = new DbUtil();
            localConn = db.getCon();

            // 确保连接有效
            if (localConn == null) {
                System.err.println("数据库连接失败");
                pager.setOrderList(new ArrayList<>());
                return pager;
            }

            // 获取记录总数
            int totalCount = getMaterialOrderCount(localConn, status, orderNo, supplier);
            pager.setTotalCount(totalCount);

            // 计算总页数
            int totalPages = (totalCount + pageSize - 1) / pageSize;
            pager.setTotalPages(totalPages);

            // 获取订单列表
            MaterialOrderPager listPager = getMaterialOrderList(localConn, offset, pageSize, status, orderNo, supplier);
            if (listPager != null && listPager.getOrderList() != null) {
                orderList = listPager.getOrderList();
            } else {
                orderList = new ArrayList<>();
            }
            pager.setOrderList(orderList);

            // 设置当前页和偏移量
            pager.setPagecurrentPageNo((offset / pageSize) + 1);
            pager.setPageOffset(offset);
            pager.setPageSize(pageSize);

        } catch (SQLException e) {
            System.err.println("数据库操作异常: " + e.getMessage());
            e.printStackTrace();
            pager.setOrderList(new ArrayList<>());
        } finally {
            // 关闭数据库连接
            closeResources(localConn, pstmt, rs);
        }

        return pager;
    }

    @Override
    public int getMaterialOrderCount(String status, String orderNo, String supplier) {
        Connection localConn = null;
        try {
            DbUtil db = new DbUtil();
            localConn = db.getCon();
            return getMaterialOrderCount(localConn, status, orderNo, supplier);
        } catch (SQLException e) {
            System.err.println("获取订单数量异常: " + e.getMessage());
            e.printStackTrace();
            return 0;
        } finally {
            closeResources(localConn, null, null);
        }
    }

    /**
     * 使用已有的数据库连接获取订单数量
     */
    private int getMaterialOrderCount(Connection conn, String status, String orderNo, String supplier) throws SQLException {
        int count = 0;
        PreparedStatement localPstmt = null;
        ResultSet localRs = null;

        try {
            String sql = "SELECT COUNT(*) FROM tb_material_order WHERE 1=1";
            StringBuilder sb = new StringBuilder(sql);
            List<String> params = new ArrayList<>();

            if (status != null && !status.isEmpty()) {
                sb.append(" AND status = ?");
                params.add(status);
            }

            if (orderNo != null && !orderNo.isEmpty()) {
                sb.append(" AND order_no LIKE ?");
                params.add("%" + orderNo + "%");
            }

            if (supplier != null && !supplier.isEmpty()) {
                sb.append(" AND supplier_name LIKE ?");
                params.add("%" + supplier + "%");
            }

            // 确保连接有效
            if (conn == null || conn.isClosed()) {
                System.err.println("数据库连接无效");
                return 0;
            }

            localPstmt = conn.prepareStatement(sb.toString());

            for (int i = 0; i < params.size(); i++) {
                localPstmt.setString(i + 1, params.get(i));
            }

            localRs = localPstmt.executeQuery();

            if (localRs.next()) {
                count = localRs.getInt(1);
            }
        } finally {
            // 注意：这里不关闭conn，因为是外部传入的连接
            closeResources(null, localPstmt, localRs);
        }

        return count;
    }

    @Override
    public MaterialOrderPager getMaterialOrderList(int offset, int pageSize,
                                                   String status, String orderNo,
                                                   String supplier) {
        Connection localConn = null;
        try {
            DbUtil db = new DbUtil();
            localConn = db.getCon();
            return getMaterialOrderList(localConn, offset, pageSize, status, orderNo, supplier);
        } catch (SQLException e) {
            System.err.println("获取订单列表异常: " + e.getMessage());
            e.printStackTrace();
            MaterialOrderPager pager = new MaterialOrderPager();
            pager.setOrderList(new ArrayList<>());
            return pager;
        } finally {
            closeResources(localConn, null, null);
        }
    }

    /**
     * 使用已有的数据库连接获取订单列表
     */
    /**
     * 使用已有的数据库连接获取订单列表
     */
    /**
     * 使用已有的数据库连接获取订单列表
     */
    /**
     * 使用已有的数据库连接获取订单列表
     */
    private MaterialOrderPager getMaterialOrderList(Connection conn, int offset, int pageSize,
                                                    String status, String orderNo,
                                                    String supplier) throws SQLException {
        MaterialOrderPager pager = new MaterialOrderPager();
        List<MaterialOrder> orderList = new ArrayList<>();
        PreparedStatement localPstmt = null;
        ResultSet localRs = null;

        try {
            String sql = "SELECT * FROM tb_material_order WHERE 1=1";
            StringBuilder sb = new StringBuilder(sql);
            List<String> params = new ArrayList<>();

            if (status != null && !status.isEmpty()) {
                sb.append(" AND status = ?");
                params.add(status);
            }

            if (orderNo != null && !orderNo.isEmpty()) {
                sb.append(" AND orderNo LIKE ?");
                params.add("%" + orderNo + "%");
            }

            if (supplier != null && !supplier.isEmpty()) {
                sb.append(" AND supplierName LIKE ?");
                params.add("%" + supplier + "%");
            }

            // 修改为实际存在的列名，根据数据库表结构调整
            // 可能的列名：update_time, createTime, orderDate等
            sb.append(" ORDER BY updateTime DESC LIMIT ?, ?");
            params.add(String.valueOf(offset));
            params.add(String.valueOf(pageSize));

            // 确保连接有效
            if (conn == null || conn.isClosed()) {
                System.err.println("数据库连接无效");
                pager.setOrderList(new ArrayList<>());
                return pager;
            }

            localPstmt = conn.prepareStatement(sb.toString());

            // 设置参数
            for (int i = 0; i < params.size(); i++) {
                if (i >= params.size() - 2) { // 最后两个参数是LIMIT的offset和pageSize
                    localPstmt.setInt(i + 1, Integer.parseInt(params.get(i)));
                } else {
                    localPstmt.setString(i + 1, params.get(i));
                }
            }

            localRs = localPstmt.executeQuery();

            while (localRs.next()) {

                MaterialOrder order = new MaterialOrder();
                order.setOrderId(localRs.getInt("orderId"));
                order.setOrderNo(localRs.getString("orderNo"));
                order.setSupplierId(localRs.getInt("supplierId"));
                order.setSupplierName(localRs.getString("supplierName"));
                order.setContactPerson(localRs.getString("contactPerson"));
                order.setContactPhone(localRs.getString("contactPhone"));
                order.setOrderTime(localRs.getTimestamp("orderTime"));
                order.setExpectedArrivalDate(localRs.getDate("expectedArrivalDate"));
                order.setTotalAmount(localRs.getDouble("totalAmount"));
//                order.setStatus(OrderStatus.valueOf(localRs.getString("status")));
                order.setRemark(localRs.getString("remark"));
                order.setCreateTime(localRs.getTimestamp("createTime"));
                order.setUpdateTime(localRs.getTimestamp("updateTime"));
                int materialCount = getMaterialItemCount(conn, order.getOrderId());
                order.setMaterialCount(materialCount);

                String statusStr = localRs.getString("status");
                order.setStatus(OrderStatus.valueOf(statusStr)); // 使用枚举值




                orderList.add(order);
            }

            pager.setOrderList(orderList);
        } finally {
            closeResources(null, localPstmt, localRs);
        }

        return pager;
    }

    /**
     * 获取订单项列表（示例方法，需根据实际情况实现）
     */
    private List<MaterialOrderItem> getOrderItems(int orderId) {
        // 实际应用中需要从数据库获取订单项
        return new ArrayList<>();
    }

    /**
     * 关闭数据库资源
     */
    private void closeResources(Connection conn, PreparedStatement pstmt, ResultSet rs) {
        try {
            if (rs != null) rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            if (pstmt != null) pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            if (conn != null) conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @Override
    public boolean createOrderForAlert(int alertId) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        boolean result = false;
        int orderId = -1;

        try {
            DbUtil db = new DbUtil();
            conn = db.getCon();
            conn.setAutoCommit(false);

            // 1. 查询预警信息
            String alertSql = "SELECT " +
                    "ta.productId, ta.warehouseId, ta.alertMessage, " +
                    "st.subTypeName AS productName, w.warehouseName " +
                    "FROM tb_inventory_alert ta " +
                    "JOIN tb_subtype st ON ta.productId = st.subTypeId " +
                    "JOIN tb_warehouse w ON ta.warehouseId = w.warehouseId " +
                    "WHERE ta.alertId = ?";

            stmt = conn.prepareStatement(alertSql);
            stmt.setInt(1, alertId);
            rs = stmt.executeQuery();

            if (!rs.next()) {
                conn.rollback();
                return false;
            }

            int productId = rs.getInt("productId");
            int warehouseId = rs.getInt("warehouseId");
            String productName = rs.getString("productName");
            String warehouseName = rs.getString("warehouseName");

            // 2. 创建采购订单主记录
            String orderSql = "INSERT INTO tb_material_order " +
                    "(orderNo, supplierId, supplierName, contactPerson, " +
                    "contactPhone, orderTime, expectedArrivalDate, totalAmount, " +
                    "status, remark) " +
                    "VALUES (?, 1, '默认供应商', '系统管理员', '13800138000', " +
                    "NOW(), DATE_ADD(NOW(), INTERVAL 7 DAY), 0.00, 'PENDING', ?)";

            String orderNo = "ORD-" + UUID.randomUUID().toString().substring(0, 8);
            String remark = "自动创建: " + warehouseName + "的" + productName + "库存预警";

            stmt = conn.prepareStatement(orderSql, PreparedStatement.RETURN_GENERATED_KEYS);
            stmt.setString(1, orderNo);
            stmt.setString(2, remark);
            int affectedRows = stmt.executeUpdate();

            if (affectedRows <= 0) {
                conn.rollback();
                return false;
            }

            // 获取订单ID
            rs.close();
            rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                orderId = rs.getInt(1);
            } else {
                conn.rollback();
                return false;
            }

            // 3. 创建订单明细
            String orderItemSql = "INSERT INTO tb_material_order_item " +
                    "(orderId, productId, productName, " +
                    "unitPrice, quantity, subtotal) " +
                    "VALUES (?, ?, ?, 0.00, 100, 0.00)"; // 默认为100个单位

            stmt = conn.prepareStatement(orderItemSql);
            stmt.setInt(1, orderId);
            stmt.setInt(2, productId);
            stmt.setString(3, productName);
            affectedRows = stmt.executeUpdate();

            if (affectedRows <= 0) {
                conn.rollback();
                return false;
            }

            // 4. 更新预警状态
            String updateAlertSql = "UPDATE tb_inventory_alert " +
                    "SET alertStatus = 2, processTime = NOW(), " +
                    "processNote = '已创建采购订单，订单号: " + orderNo + "' " +
                    "WHERE alertId = ?";

            stmt = conn.prepareStatement(updateAlertSql);
            stmt.setInt(1, alertId);
            affectedRows = stmt.executeUpdate();

            if (affectedRows <= 0) {
                conn.rollback();
                return false;
            }

            conn.commit();
            result = true;

        } catch (SQLException e) {
            e.printStackTrace();
            try {
                if (conn != null) conn.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        } finally {
            try {
                conn.close();
            }
            catch (SQLException e) {
                e.printStackTrace();
            }

        }

        return result;
    }




    /**
     * 获取订单对应的物料项数量
     */
    private int getMaterialItemCount(Connection conn, int orderId) throws SQLException {
        int count = 0;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            // 查询指定订单的物料项数量
            String sql = "SELECT sum(quantity) FROM tb_material_order_item WHERE orderId = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, orderId);
            rs = stmt.executeQuery();

            if (rs.next()) {
                count = rs.getInt(1);
            }
        } finally {
            // 关闭资源
            try {
                if (rs != null) rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                if (stmt != null) stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return count;
    }





}
