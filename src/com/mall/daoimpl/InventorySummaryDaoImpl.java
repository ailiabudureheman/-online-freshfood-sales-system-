package com.mall.daoimpl;

import com.mall.common.DbUtil;
import com.mall.dao.InventorySummaryDao;
import com.mall.po.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class InventorySummaryDaoImpl implements InventorySummaryDao {

    @Override
    public InventorySummaryPager getInventorySummaryPager(int offset, int pageSize, String warehouseId, String categoryId, String productName) {
        InventorySummaryPager pager = new InventorySummaryPager();
        pager.setPageOffset(offset);
        pager.setPageSize(pageSize);

        int totalCount = getInventoryCount(warehouseId, categoryId, productName);
        pager.setTotalCount(totalCount);

        int totalPages = (int) Math.ceil((double) totalCount / pageSize);
        pager.setTotalPages(totalPages);

        InventorySummaryPager inventoryListPager = getInventoryList(offset, pageSize, warehouseId, categoryId, productName);
        pager.setInventoryList(inventoryListPager.getInventoryList());

        InventorySummaryPager warehousePager = getAllWarehouses();
        pager.setWarehouseList(warehousePager.getWarehouseList());

        InventorySummaryPager categoryPager = getAllCategories();
        pager.setCategoryList(categoryPager.getCategoryList());

        return pager;
    }

    @Override
    public int getInventoryCount(String warehouseId, String categoryId, String productName) {
        int count = 0;
        DbUtil dbUtil = new DbUtil();
        Connection conn = dbUtil.getCon();
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            StringBuilder query = new StringBuilder("SELECT COUNT(*) FROM tb_product_inventory pi " +
                    "JOIN tb_warehouse w ON pi.warehouseId = w.warehouseId " +
                    "JOIN product st ON pi.productId = st.product_id " +
                    "WHERE 1 = 1");

            List<Object> params = new ArrayList<>();
            if (warehouseId != null && !warehouseId.isEmpty()) {
                query.append(" AND w.warehouseId = ?");
                params.add(Integer.parseInt(warehouseId));
            }
            if (categoryId != null && !categoryId.isEmpty()) {
                query.append(" AND st.superTypeId = ?");
                params.add(Integer.parseInt(categoryId));
            }
            if (productName != null && !productName.isEmpty()) {
                query.append(" AND st.productName LIKE ?");
                params.add("%" + productName + "%");
            }

            stmt = conn.prepareStatement(query.toString());
            for (int i = 0; i < params.size(); i++) {
                stmt.setObject(i + 1, params.get(i));
            }
            rs = stmt.executeQuery();
            if (rs.next()) {
                count = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (rs != null) try { rs.close(); } catch (SQLException e) { e.printStackTrace(); }
            if (stmt != null) try { stmt.close(); } catch (SQLException e) { e.printStackTrace(); }
            dbUtil.close();
        }
        return count;
    }

    @Override
    public InventorySummaryPager getInventoryList(int offset, int pageSize, String warehouseId, String categoryId, String productName) {
        InventorySummaryPager pager = new InventorySummaryPager();
        List<Inventory> inventoryList = new ArrayList<>();
        DbUtil dbUtil = new DbUtil();
        Connection conn = dbUtil.getCon();
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
           StringBuilder query = new StringBuilder("SELECT  \n" +
                   "distinct\n" +
                   "pi.warehouseId,\n" +
                   "    pi.productId,\n" +
                   "    SUM(pi.quantity) AS sumquantity,  -- 使用普通聚合函数\n" +
                   "    MAX(w.warehouseName) AS warehouseName,  -- 任意选取一个仓库名（因为相同分组下值相同）\n" +
                   "    MAX(st.productName) AS productName,    -- 任意选取一个产品名\n" +
                   "    MAX(sup.typeName) AS categoryName      -- 任意选取一个类别名\n" +
                   "FROM tb_product_inventory pi \n" +
                   "JOIN tb_warehouse w ON pi.warehouseId = w.warehouseId \n" +
                   "JOIN product st ON pi.productId = st.product_id \n" +
                   "JOIN tb_supertype sup ON st.superTypeId = sup.superTypeId \n" +
                   "WHERE pi.status = 1\n"



           );



            List<Object> params = new ArrayList<>();



//            System.out.println(pageSize);
//            System.out.println(offset);
//
            if (warehouseId != null && !warehouseId.isEmpty()) {
                query.append(" AND w.warehouseId = ?");
                params.add(Integer.parseInt(warehouseId));

            }
            if (categoryId != null && !categoryId.isEmpty()) {
                query.append(" AND sup.superTypeId = ?");
                params.add(Integer.parseInt(categoryId));
            }
            if (productName != null && !productName.isEmpty()) {
                query.append(" AND st.productName LIKE ?");
                params.add("%" + productName + "%");

            }

            query.append("GROUP BY pi.warehouseId, pi.productId  -- 按仓库ID和产品ID分组");
//
            query.append(" ORDER BY pi.warehouseId, st.productName ");


//            query.append(" LIMIT ? OFFSET ?");
//            params.add(pageSize);  // 添加分页参数
//            params.add(offset);    // 添加分页参数


            stmt = conn.prepareStatement(query.toString());
            for (int i = 0; i < params.size(); i++) {
                stmt.setObject(i + 1, params.get(i));
            }
            rs = stmt.executeQuery();


            while (rs.next()) {
                Inventory inventory = new Inventory();
                //inventory.setInventoryId(rs.getInt("inventoryId"));
                inventory.setProductId(rs.getInt("productId"));
                inventory.setWarehouseId(rs.getInt("warehouseId"));
                inventory.setQuantity(rs.getInt("sumquantity"));
                inventory.setWarehouseName(rs.getString("warehouseName"));
                inventory.setProductName(rs.getString("productName"));
                inventory.setCategoryName(rs.getString("categoryName"));  // 修复：使用categoryName属性



//                System.out.println(rs.getString("productName"));
//                System.out.println(rs.getString("productId"));
//                System.out.println(rs.getString("warehouseId"));
//                System.out.println(rs.getString("quantity"));
//                System.out.println(rs.getString("warehouseName"));
//                System.out.println(rs.getString("productName"));
//                System.out.println(rs.getString("categoryName"));


//                inventory.setInventoryId(0);
//                inventory.setProductId(0);
//                inventory.setWarehouseId(0);
//                inventory.setQuantity(0);
//                inventory.setWarehouseName("0");
//                inventory.setProductName("0");
//                inventory.setCategoryName("0");


                inventoryList.add(inventory);








            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (rs != null) try { rs.close(); } catch (SQLException e) { e.printStackTrace(); }
            if (stmt != null) try { stmt.close(); } catch (SQLException e) { e.printStackTrace(); }
            dbUtil.close();
        }
        pager.setInventoryList(inventoryList);
        return pager;
    }

    @Override
    public InventorySummaryPager getAllWarehouses() {
        InventorySummaryPager pager = new InventorySummaryPager();
        List<Warehouse> warehouseList = new ArrayList<>();
        DbUtil dbUtil = new DbUtil();
        Connection conn = dbUtil.getCon();
        Statement stmt = null;
        ResultSet rs = null;

        try {
            // 使用窗口函数计算每个仓库的总库存
            String query = "SELECT " +
                    "w.warehouseId, " +
                    "w.warehouseCode, " +
                    "w.warehouseName, " +
                    "w.location, " +
                    "w.capacity, " +
                    "SUM(IFNULL(i.quantity, 0)) AS currentStock, " + // 使用IFNULL处理无库存的仓库
                    "w.lastInspectionDate, " +
                    "w.nextInspectionDate, " +
                    "w.status, " +
                    "w.createTime, " +
                    "w.updateTime " +
                    "FROM tb_warehouse w " +
                    "LEFT JOIN tb_product_inventory i " +
                    "  ON w.warehouseId = i.warehouseId " +
                    "  AND i.status != 0 " + // 移动过滤条件到ON子句，确保保留无库存的仓库
                    "GROUP BY " +
                    "w.warehouseId, " +
                    "w.warehouseCode, " +
                    "w.warehouseName, " +
                    "w.location, " +
                    "w.capacity, " +
                    "w.lastInspectionDate, " +
                    "w.nextInspectionDate, " +
                    "w.status, " +
                    "w.createTime, " +
                    "w.updateTime " +
                    "ORDER BY w.warehouseId";

            stmt = conn.createStatement();
            rs = stmt.executeQuery(query);

            while (rs.next()) {
                Warehouse warehouse = new Warehouse();
                warehouse.setWarehouseId(rs.getInt("warehouseId"));
                warehouse.setWarehouseCode(rs.getString("warehouseCode"));
                warehouse.setWarehouseName(rs.getString("warehouseName"));
                warehouse.setLocation(rs.getString("location"));
                warehouse.setCapacity(rs.getInt("capacity"));
                //System.out.println(rs.getInt("capacity"));
                warehouse.setCurrentStock(rs.getInt("currentStock"));  // 使用计算后的库存总量
                warehouseList.add(warehouse);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (rs != null) try { rs.close(); } catch (SQLException e) { e.printStackTrace(); }
            if (stmt != null) try { stmt.close(); } catch (SQLException e) { e.printStackTrace(); }
            dbUtil.close();
        }

        pager.setWarehouseList(warehouseList);
        return pager;
    }

    @Override
    public InventorySummaryPager getAllCategories() {
        InventorySummaryPager pager = new InventorySummaryPager();
        List<Category> categoryList = new ArrayList<>();
        DbUtil dbUtil = new DbUtil();
        Connection conn = dbUtil.getCon();
        Statement stmt = null;
        ResultSet rs = null;

        try {
            // 修复：使用typeName代替superTypeName
            String query = "SELECT superTypeId AS categoryId, typeName AS categoryName FROM tb_supertype ORDER BY superTypeId";
            stmt = conn.createStatement();
            rs = stmt.executeQuery(query);
            while (rs.next()) {
                Category category = new Category();
                category.setCategoryId(rs.getInt("categoryId"));
                category.setCategoryName(rs.getString("categoryName"));
                categoryList.add(category);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (rs != null) try { rs.close(); } catch (SQLException e) { e.printStackTrace(); }
            if (stmt != null) try { stmt.close(); } catch (SQLException e) { e.printStackTrace(); }
            dbUtil.close();
        }
        pager.setCategoryList(categoryList);
        return pager;
    }

    @Override
    public List<StockInRecord> getRecentStockIns(int limit) {
        List<StockInRecord> result = new ArrayList<>();
        String sql = "SELECT " +
                "pi.inventoryId, " +
                "pi.productId, " +
                "st.productName AS productName, " +
                "pi.warehouseId, " +
                "w.warehouseName, " +
                "pi.quantity, " +
                "pi.stockInTime, " +
                "pi.batchNumber " +
                "FROM tb_product_inventory pi " +
                "JOIN tb_warehouse w ON pi.warehouseId = w.warehouseId " +
                "JOIN product st ON pi.productId = st.product_id " +
                "WHERE pi.status = 1 " +  // 只查询正常在库的产品
                "  AND pi.stockInTime IS NOT NULL " +
                "ORDER BY pi.stockInTime DESC " +
                "LIMIT ?";





        DbUtil dbUtil = new DbUtil();
        try (Connection conn = dbUtil.getCon();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, limit);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    StockInRecord record = new StockInRecord();
                    record.setInventoryId(rs.getInt("inventoryId"));
                    record.setProductId(rs.getInt("productId"));
                    record.setProductName(rs.getString("productName"));
                    record.setWarehouseId(rs.getInt("warehouseId"));
                    record.setWarehouseName(rs.getString("warehouseName"));
                    record.setQuantity(rs.getInt("quantity"));
                    record.setStockInTime(rs.getTimestamp("stockInTime"));
                    record.setBatchNumber(rs.getString("batchNumber"));
                    // 假设操作人信息存储在另一个表中，这里简化处理
                    record.setOperator("系统管理员");
                    result.add(record);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public List<StockOutRecord> getRecentStockOuts(int limit) {
        List<StockOutRecord> result = new ArrayList<>();
        String sql = "SELECT " +
                "pi.inventoryId, " +
                "pi.productId, " +
                "st.productName AS productName, " +
                "pi.warehouseId, " +
                "w.warehouseName, " +
                "pi.quantity, " +
                "pi.stockOutTime " +
                "FROM tb_product_inventory pi " +
                "JOIN tb_warehouse w ON pi.warehouseId = w.warehouseId " +
                "JOIN product st ON pi.productId = st.product_id " +
                "WHERE pi.status = 0 " +  // 只查询已出库的产品
                "  AND pi.stockOutTime IS NOT NULL " +
                "ORDER BY pi.stockOutTime DESC " +
                "LIMIT ?";



        DbUtil dbUtil = new DbUtil();
        try (Connection conn = dbUtil.getCon();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, limit);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    StockOutRecord record = new StockOutRecord();
                    record.setInventoryId(rs.getInt("inventoryId"));
                    record.setProductId(rs.getInt("productId"));
                    record.setProductName(rs.getString("productName"));
                    record.setWarehouseId(rs.getInt("warehouseId"));
                    record.setWarehouseName(rs.getString("warehouseName"));
                    record.setQuantity(rs.getInt("quantity"));
                    record.setStockOutTime(rs.getTimestamp("stockOutTime"));
                    // 假设操作人信息存储在另一个表中，这里简化处理
                    record.setOperator("系统管理员");
                    result.add(record);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public InventoryAlertPager getInventoryAlerts() {
        InventoryAlertPager pager = new InventoryAlertPager();
        List<InventoryAlert> alertList = new ArrayList<>();
        DbUtil dbUtil = new DbUtil();
        Connection conn = dbUtil.getCon();
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            String query = "SELECT " +
                    "ta.alertId, " +
                    "ta.productId, " +
                    "ta.warehouseId, " +
                    "ta.alertType, " +
                    "ta.alertMessage, " +
                    "ta.alertStatus, " +
                    "ta.alertTime, " +
                    "ta.processTime, " +
                    "ta.processNote, " +
                    "st.productName AS productName, " +
                    "w.warehouseName " +
                    "FROM tb_inventory_alert ta " +
                    "JOIN product st ON ta.productId = st.product_id " +
                    "JOIN tb_warehouse w ON ta.warehouseId = w.warehouseId " +
                    "WHERE ta.alertStatus = 0 " + // 只查询未解决的预警
                    "ORDER BY ta.alertTime DESC";

            stmt = conn.prepareStatement(query);
            rs = stmt.executeQuery();
            while (rs.next()) {
                InventoryAlert alert = new InventoryAlert();
                alert.setAlertId(rs.getInt("alertId"));
                alert.setProductId(rs.getInt("productId"));
                alert.setWarehouseId(rs.getInt("warehouseId"));
                alert.setAlertType(rs.getString("alertType"));
                alert.setAlertMessage(rs.getString("alertMessage"));
                alert.setAlertStatus(rs.getInt("alertStatus"));
                alert.setAlertTime(rs.getTimestamp("alertTime"));
                alert.setProcessTime(rs.getTimestamp("processTime"));
                alert.setProcessNote(rs.getString("processNote"));
                alert.setProductName(rs.getString("productName"));
                alert.setWarehouseName(rs.getString("warehouseName"));
                alertList.add(alert);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (rs != null) try { rs.close(); } catch (SQLException e) { e.printStackTrace(); }
            if (stmt != null) try { stmt.close(); } catch (SQLException e) { e.printStackTrace(); }
            dbUtil.close();
        }
        pager.setAlertList(alertList);
        return pager;
    }
}