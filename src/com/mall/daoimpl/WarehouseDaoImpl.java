package com.mall.daoimpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.mall.dao.WarehouseDao;
import com.mall.common.DbUtil;
import com.mall.po.Warehouse;
import com.mall.po.WarehousePager;

public class WarehouseDaoImpl implements WarehouseDao {
    private Connection conn = null;
    private PreparedStatement pstmt = null;
    private ResultSet rs = null;

    @Override
    public WarehousePager getWarehousePager(int offset, int pageSize) {
        WarehousePager pager = new WarehousePager();
        List<Warehouse> warehouseList = new ArrayList<>();

        try {
            DbUtil db = new DbUtil();
            conn = db.getCon();

            // 获取记录总数
            int totalCount = getWarehouseCount();
            pager.setTotalCount(totalCount);

            // 计算总页数
            int totalPages = (totalCount + pageSize - 1) / pageSize;
            pager.setTotalPages(totalPages);

            // 获取仓库列表
            warehouseList = getWarehouseList(offset, pageSize).getWarehouseList();
            pager.setWarehouseList(warehouseList);

            // 设置当前页和偏移量
            pager.setPagecurrentPageNo((offset / pageSize) + 1);
            pager.setPageOffset(offset);
            pager.setPageSize(pageSize);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return pager;
    }

    @Override
    public int getWarehouseCount() {
        int count = 0;
        String sql = "SELECT COUNT(*) FROM tb_warehouse";

        try {
            DbUtil db = new DbUtil();
            conn = db.getCon();
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                count = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return count;
    }

    @Override
    public WarehousePager getWarehouseList(int offset, int pageSize) {
        WarehousePager pager = new WarehousePager();
        List<Warehouse> warehouseList = new ArrayList<>();
        String sql = "SELECT * FROM tb_warehouse ORDER BY warehouseId LIMIT ?, ?";

        try {
            DbUtil db = new DbUtil();
            conn = db.getCon();
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, offset);
            pstmt.setInt(2, pageSize);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                Warehouse warehouse = new Warehouse();
                warehouse.setWarehouseId(rs.getInt("warehouseId"));
                warehouse.setWarehouseCode(rs.getString("warehouseCode"));
                warehouse.setWarehouseName(rs.getString("warehouseName"));
                warehouse.setLocation(rs.getString("location"));
                warehouse.setCapacity(rs.getInt("capacity"));
                warehouse.setCurrentStock(rs.getInt("currentStock"));
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
        } finally {
            try {
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return pager;
    }

    @Override
    public Warehouse getWarehouseById(int warehouseId) {
        Warehouse warehouse = null;
        String sql = "SELECT * FROM tb_warehouse WHERE warehouseId = ?";

        try {
            DbUtil db = new DbUtil();
            conn = db.getCon();
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, warehouseId);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                warehouse = new Warehouse();
                warehouse.setWarehouseId(rs.getInt("warehouseId"));
                warehouse.setWarehouseCode(rs.getString("warehouseCode"));
                warehouse.setWarehouseName(rs.getString("warehouseName"));
                warehouse.setLocation(rs.getString("location"));
                warehouse.setCapacity(rs.getInt("capacity"));
                warehouse.setCurrentStock(rs.getInt("currentStock"));
                warehouse.setLastInspectionDate(rs.getDate("lastInspectionDate"));
                warehouse.setNextInspectionDate(rs.getDate("nextInspectionDate"));
                warehouse.setStatus(rs.getInt("status"));
                warehouse.setCreateTime(rs.getTimestamp("createTime"));
                warehouse.setUpdateTime(rs.getTimestamp("updateTime"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return warehouse;
    }

    @Override
    public boolean addWarehouse(Warehouse warehouse) {
        boolean result = false;
        String sql = "INSERT INTO tb_warehouse (warehouseCode, warehouseName, location, capacity, " +
                "currentStock, lastInspectionDate, nextInspectionDate, status, createTime, updateTime) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, NOW(), NOW())";

        try {
            DbUtil db = new DbUtil();
            conn = db.getCon();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, warehouse.getWarehouseCode());
            pstmt.setString(2, warehouse.getWarehouseName());
            pstmt.setString(3, warehouse.getLocation());
            pstmt.setInt(4, warehouse.getCapacity());
            pstmt.setInt(5, warehouse.getCurrentStock());
            pstmt.setDate(6, new java.sql.Date(warehouse.getLastInspectionDate().getTime()));
            pstmt.setDate(7, new java.sql.Date(warehouse.getNextInspectionDate().getTime()));
            pstmt.setInt(8, warehouse.getStatus());

            int rows = pstmt.executeUpdate();
            result = (rows > 0);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return result;
    }

    @Override
    public boolean updateWarehouse(Warehouse warehouse) {
        boolean result = false;
        String sql = "UPDATE tb_warehouse SET warehouseCode = ?, warehouseName = ?, location = ?, " +
                "capacity = ?, currentStock = ?, lastInspectionDate = ?, nextInspectionDate = ?, " +
                "status = ?, updateTime = NOW() WHERE warehouseId = ?";

        try {
            DbUtil db = new DbUtil();
            conn = db.getCon();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, warehouse.getWarehouseCode());
            pstmt.setString(2, warehouse.getWarehouseName());
            pstmt.setString(3, warehouse.getLocation());
            pstmt.setInt(4, warehouse.getCapacity());
            pstmt.setInt(5, warehouse.getCurrentStock());
            pstmt.setDate(6, new java.sql.Date(warehouse.getLastInspectionDate().getTime()));
            pstmt.setDate(7, new java.sql.Date(warehouse.getNextInspectionDate().getTime()));
            pstmt.setInt(8, warehouse.getStatus());
            pstmt.setInt(9, warehouse.getWarehouseId());

            int rows = pstmt.executeUpdate();
            result = (rows > 0);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return result;
    }

    @Override
    public boolean deleteWarehouse(int warehouseId) {
        boolean result = false;
        String sql = "DELETE FROM tb_warehouse WHERE warehouseId = ?";

        try {
            DbUtil db = new DbUtil();
            conn = db.getCon();
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, warehouseId);

            int rows = pstmt.executeUpdate();
            result = (rows > 0);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return result;
    }
}