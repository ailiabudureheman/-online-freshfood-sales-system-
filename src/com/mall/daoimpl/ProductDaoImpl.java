
package com.mall.daoimpl;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.mall.dao.ProductDao;
import com.mall.common.DbUtil;
import com.mall.po.Product;
import com.mall.po.ProductPager;

public class ProductDaoImpl implements ProductDao {
    private Connection conn = null;
    private PreparedStatement pstmt = null;
    private ResultSet rs = null;

    @Override
    public ProductPager getProductPager(int offset, int pageSize, String categoryId, String productName) {
        ProductPager pager = new ProductPager();
        List<Product> productList = new ArrayList<>();

        try {
            DbUtil db = new DbUtil();
            conn = db.getCon();

            // 动态构建 SQL 查询语句
            StringBuilder sql = new StringBuilder("SELECT p.*, " +
                    "(SELECT SUM(quantity) FROM tb_product_inventory WHERE productId = p.product_id AND status = 1) AS total_stock " +
                    "FROM product p WHERE 1=1");
            List<Object> params = new ArrayList<>();

            if (categoryId != null && !categoryId.isEmpty()) {
                sql.append(" AND p.superTypeId = ?");
                params.add(Integer.parseInt(categoryId));
            }

            if (productName != null && !productName.isEmpty()) {
                sql.append(" AND p.productName LIKE ?");
                params.add("%" + productName + "%");
            }

            sql.append(" ORDER BY p.product_id LIMIT ?, ?");
            params.add(offset);
            params.add(pageSize);

            pstmt = conn.prepareStatement(sql.toString());

            // 设置查询参数
            for (int i = 0; i < params.size(); i++) {
                pstmt.setObject(i + 1, params.get(i));
            }

            rs = pstmt.executeQuery();

            while (rs.next()) {
                Product product = new Product();
                product.setProductId(rs.getInt("product_id"));
                product.setProductName(rs.getString("productName"));
                product.setCategoryId(rs.getInt("superTypeId"));

                product.setSellingPrice(rs.getBigDecimal("nowPrice").doubleValue());
                product.setPurchasePrice(rs.getBigDecimal("purchase_price").doubleValue());
                product.setCurrentStock(rs.getInt("total_stock"));
                product.setRemark(rs.getString("introduce"));
                product.setCreateTime(rs.getTimestamp("create_time"));
                product.setUpdateTime(rs.getTimestamp("update_time"));

                productList.add(product);
            }



















            pager.setProductList(productList);
            pager.setPageSize(pageSize);
            pager.setPageOffset(offset);

            // 获取总记录数
            int totalCount = getTotalCount(categoryId, productName);
            pager.setTotalCount(totalCount);
            pager.setTotalPages((totalCount + pageSize - 1) / pageSize);

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResources();
        }

        return pager;
    }

    /**
     * 获取符合条件的产品总记录数
     * @param categoryId 类别ID
     * @param productName 产品名称
     * @return 总记录数
     */
    private int getTotalCount(String categoryId, String productName) {
        int totalCount = 0;
        try {
            DbUtil db = new DbUtil();
            conn = db.getCon();

            // 动态构建 COUNT 查询语句
            StringBuilder sql = new StringBuilder("SELECT COUNT(*) FROM product p WHERE 1=1");
            List<Object> params = new ArrayList<>();

            if (categoryId != null && !categoryId.isEmpty()) {
                sql.append(" AND p.superTypeId = ?");
                params.add(Integer.parseInt(categoryId));
            }

            if (productName != null && !productName.isEmpty()) {
                sql.append(" AND p.productName LIKE ?");
                params.add("%" + productName + "%");
            }

            pstmt = conn.prepareStatement(sql.toString());

            // 设置查询参数
            for (int i = 0; i < params.size(); i++) {
                pstmt.setObject(i + 1, params.get(i));
            }

            rs = pstmt.executeQuery();

            if (rs.next()) {
                totalCount = rs.getInt(1);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResources();
        }

        return totalCount;
    }

    /**
     * 关闭数据库资源
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
}

