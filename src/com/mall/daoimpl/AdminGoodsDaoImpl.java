
package com.mall.daoimpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mall.common.DbUtil;
import com.mall.dao.AdminGoodsDao;
import com.mall.po.Goods;
import com.mall.po.GoodsPager;

public class AdminGoodsDaoImpl implements AdminGoodsDao {

    @Override
    public List<Goods> getAllGoods() {
        List<Goods> goodsList = new ArrayList<>();
        DbUtil daoUtil = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql = null;
        try {
            daoUtil = new DbUtil();
            sql = "SELECT * FROM product";
            ps = daoUtil.getCon().prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                Goods goods = new Goods();
                goods.setGoodsId(rs.getInt("product_id"));
                goods.setSuperTypeId(rs.getInt("superTypeId"));
                goods.setSubTypeId(rs.getInt("subTypeId"));
                goods.setGoodsName(rs.getString("productName"));
                goods.setIntroduce(rs.getString("introduce"));
                goods.setPurchasePrice(rs.getFloat("purchase_price"));
                goods.setPrice(rs.getFloat("price"));
                goods.setNowPrice(rs.getFloat("nowPrice"));
                goods.setPicture(rs.getString("picture"));
                goods.setPublisher(rs.getString("publisher"));
                goods.setAuthor(rs.getString("author"));
                goods.setNewGoods(rs.getInt("newgoods"));
                goods.setSaleGoods(rs.getInt("salegoods"));
                goods.setHostGoods(rs.getInt("hostgoods"));
                goods.setSpecialGoods(rs.getInt("specialgoods"));
                goods.setCreateTime(rs.getTimestamp("create_time"));
                goods.setUpdateTime(rs.getTimestamp("update_time"));

                // 从库存表获取库存数量
                int stockQuantity = getStockQuantity(daoUtil.getCon(), rs.getInt("product_id"));
                goods.setStockQuantity(stockQuantity);

                goodsList.add(goods);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
                if (daoUtil != null) daoUtil.close();
            } catch (SQLException s) {
                s.printStackTrace();
            }
        }
        return goodsList;
    }

    @Override
    public boolean addGoods(Goods goods) {
        DbUtil dao = null;
        PreparedStatement ps = null;
        Connection conn = null;
        boolean result = false;




        try {
            dao = new DbUtil();
            conn = dao.getCon();
            conn.setAutoCommit(false); // 开启事务


            // 检查subTypeId是否存在
            String subTypeName = null;
            try {
                subTypeName = getSubTypeName(conn, goods.getSubTypeId());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            if (goods.getSubTypeId() > 0 && subTypeName == null) {
                System.out.println("错误: 子类别ID不存在 - " + goods.getSubTypeId());
                try {
                    conn.rollback();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                return false;
            } else {
                System.out.println("子类别ID验证通过: " + goods.getSubTypeId() + "，类别名称: " + subTypeName);
            }





            System.out.println("开始添加商品: " + goods.getGoodsName());
            System.out.println("主类别ID: " + goods.getSuperTypeId());
            System.out.println("子类别ID: " + goods.getSubTypeId());

            // 检查superTypeId是否存在
            if (goods.getSuperTypeId() > 0 && !checkSuperTypeIdExists(conn, goods.getSuperTypeId())) {
                System.out.println("错误: 主类别ID不存在 - " + goods.getSuperTypeId());
                conn.rollback();
                return false;
            } else {
                System.out.println("主类别ID验证通过: " + goods.getSuperTypeId());
            }

            // 检查subTypeId是否存在
            if (goods.getSubTypeId() > 0 && !checkSubTypeIdExists(conn, goods.getSubTypeId())) {
                System.out.println("错误: 子类别ID不存在 - " + goods.getSubTypeId());
                conn.rollback();
                return false;
            } else {
                System.out.println("子类别ID验证通过: " + goods.getSubTypeId());
            }

            String sql = "INSERT INTO product " +
                    "(superTypeId, subTypeId, productName, ISBN, introduce, " +
                    "purchase_price, price, nowPrice, picture, publisher, " +
                    "author, newgoods, salegoods, hostgoods, specialgoods, " +
                    "create_time, update_time) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, NOW(), NOW())";
            ps = conn.prepareStatement(sql);

            // 设置参数
            ps.setInt(1, goods.getSuperTypeId() > 0 ? goods.getSuperTypeId() : 0);
            ps.setInt(2, goods.getSubTypeId() > 0 ? goods.getSubTypeId() : 0);
            ps.setString(3, goods.getGoodsName() != null ? goods.getGoodsName() : "");
            ps.setString(4, goods.getISBN() != null ? goods.getISBN() : "");
            ps.setString(5, goods.getIntroduce() != null ? goods.getIntroduce() : "");

            Float purchasePrice = goods.getPurchasePrice();
            Float price = goods.getPrice();
            Float nowPrice = goods.getNowPrice();

            ps.setBigDecimal(6, purchasePrice != null ? BigDecimal.valueOf(goods.getPurchasePrice()) : new BigDecimal("0.00"));
            ps.setBigDecimal(7, price != null ? BigDecimal.valueOf(goods.getPrice()) : new BigDecimal("0.00"));
            ps.setBigDecimal(8, nowPrice != null ? BigDecimal.valueOf(goods.getNowPrice()) : new BigDecimal("0.00"));
            ps.setString(9, goods.getPicture() != null ? goods.getPicture() : "");
            ps.setString(10, goods.getPublisher() != null ? goods.getPublisher() : "");
            ps.setString(11, goods.getAuthor() != null ? goods.getAuthor() : "");
            ps.setInt(12, goods.getNewGoods());
            ps.setInt(13, goods.getSaleGoods());
            ps.setInt(14, goods.getHostGoods());
            ps.setInt(15, goods.getSpecialGoods());

            System.out.println("准备执行插入语句，子类别ID: " + goods.getSubTypeId());
            int affectedRows = ps.executeUpdate();
            conn.commit(); // 提交事务
            System.out.println("插入操作完成，影响行数: " + affectedRows);
            result = affectedRows > 0;

        } catch (Exception e) {
            try {
                if (conn != null && !conn.isClosed()) {
                    System.out.println("执行回滚操作");
                    conn.rollback(); // 回滚事务
                }
            } catch (SQLException se) {
                se.printStackTrace();
            }
            e.printStackTrace();
        } finally {
            try {
                if (ps != null) ps.close();
                if (conn != null) {
                    conn.setAutoCommit(true); // 恢复自动提交模式
                    conn.close();
                }
                if (dao != null) dao.close();
            } catch (SQLException s) {
                s.printStackTrace();
            }
        }
        return result;
    }



    /**
     * 检查子类别ID是否存在，并返回子类别名称
     */
    private String getSubTypeName(Connection conn, int subTypeId) throws SQLException {
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            String sql = "SELECT subTypeName FROM tb_subtype WHERE subTypeId = ?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, subTypeId);
            rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getString("subTypeName");
            }
            return null;
        } finally {
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }




    /**
     * 检查主类别ID是否存在
     */
    private boolean checkSuperTypeIdExists(Connection conn, int superTypeId) throws SQLException {
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            String sql = "SELECT COUNT(*) FROM tb_supertype WHERE superTypeId = ?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, superTypeId);
            rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
            return false;
        } finally {
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 检查子类别ID是否存在
     */
    private boolean checkSubTypeIdExists(Connection conn, int subTypeId) throws SQLException {
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            String sql = "SELECT COUNT(*) FROM tb_subtype WHERE subTypeId = ?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, subTypeId);
            rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
            return false;
        } finally {
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public boolean checkGoodsNameIsExist(String goodsName) {
        DbUtil daoUtil = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        boolean exists = false;
        try {
            daoUtil = new DbUtil();
            String sql = "SELECT * FROM product WHERE productName = ?";
            ps = daoUtil.getCon().prepareStatement(sql);
            ps.setString(1, goodsName);
            rs = ps.executeQuery();
            exists = rs.next();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
                if (daoUtil != null) daoUtil.close();
            } catch (SQLException s) {
                s.printStackTrace();
            }
        }
        return exists;
    }

    @Override
    public boolean checkISBNIsExist(String ISBN) {
        DbUtil daoUtil = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        boolean exists = false;
        try {
            daoUtil = new DbUtil();
            String sql = "SELECT * FROM product WHERE ISBN = ?";
            ps = daoUtil.getCon().prepareStatement(sql);
            ps.setString(1, ISBN);
            rs = ps.executeQuery();
            exists = rs.next();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
                if (daoUtil != null) daoUtil.close();
            } catch (SQLException s) {
                s.printStackTrace();
            }
        }
        return exists;
    }

    @Override
    public GoodsPager searchGoods(String goodsName) {
        Map<Integer, Goods> goodsMap = new HashMap<>();
        DbUtil daoUtil = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            daoUtil = new DbUtil();
            String sql = "SELECT * FROM product WHERE productName LIKE ?";
            ps = daoUtil.getCon().prepareStatement(sql);
            ps.setString(1, "%" + goodsName + "%");
            rs = ps.executeQuery();
            while (rs.next()) {
                Goods goods = new Goods();
                goods.setGoodsId(rs.getInt("product_id"));
                goods.setSuperTypeId(rs.getInt("superTypeId"));
                goods.setSubTypeId(rs.getInt("subTypeId"));
                goods.setGoodsName(rs.getString("productName"));
                goods.setIntroduce(rs.getString("introduce"));
                goods.setPurchasePrice(rs.getFloat("purchase_price"));
                goods.setPrice(rs.getFloat("price"));
                goods.setNowPrice(rs.getFloat("nowPrice"));
                goods.setPicture(rs.getString("picture"));
                goods.setPublisher(rs.getString("publisher"));
                goods.setAuthor(rs.getString("author"));
                goods.setNewGoods(rs.getInt("newgoods"));
                goods.setSaleGoods(rs.getInt("salegoods"));
                goods.setHostGoods(rs.getInt("hostgoods"));
                goods.setSpecialGoods(rs.getInt("specialgoods"));
                goods.setCreateTime(rs.getTimestamp("create_time"));
                goods.setUpdateTime(rs.getTimestamp("update_time"));

                // 从库存表获取库存数量
                int stockQuantity = getStockQuantity(daoUtil.getCon(), rs.getInt("product_id"));
                goods.setGoodsNum(stockQuantity);

                goodsMap.put(goods.getGoodsId(), goods);
            }

//            while (rs.next()) {
//                Goods goods = new Goods();
//                goods.setGoodsId(rs.getInt("bookId"));
//                goods.setSuperTypeId(rs.getInt("superTypeId"));
//                goods.setSubTypeId(rs.getInt("subTypeId"));
//                goods.setGoodsName(rs.getString("goodsName"));
//                goods.setIntroduce(rs.getString("introduce"));
//                goods.setPrice(rs.getFloat("price"));
//                goods.setNowPrice(rs.getFloat("nowPrice"));
//                goods.setPicture(rs.getString("picture"));
//                goods.setInTime(rs.getString("inTime"));
//                goods.setNewGoods(rs.getInt("newGoods"));
//                goods.setSaleGoods(rs.getInt("saleGoods"));
//                goods.setHostGoods(rs.getInt("hostGoods"));
//                goods.setGoodsNum(rs.getInt("goodsNum"));
//                goodsList.add(goods);
//            }











        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
                if (daoUtil != null) daoUtil.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        GoodsPager bp = new GoodsPager();
        bp.setGoodsMap(goodsMap);
        bp.setTotalNum(goodsMap.size());
        return bp;
    }

    @Override
    public List<String> getAllGoodsName() {
        List<String> goodsNameList = new ArrayList<>();
        DbUtil daoUtil = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            daoUtil = new DbUtil();
            String sql = "SELECT productName FROM product";
            ps = daoUtil.getCon().prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                goodsNameList.add(rs.getString("productName"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
                if (daoUtil != null) daoUtil.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return goodsNameList;
    }

    @Override
    public GoodsPager getGoodsPager(int index, int pageSize) {
        Map<Integer, Goods> goodsMap = new HashMap<>();
        DbUtil db = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            db = new DbUtil();
            String sql = "SELECT * FROM product LIMIT ?, ?";
            ps = db.getCon().prepareStatement(sql);
            ps.setInt(1, index);
            ps.setInt(2, pageSize);
            rs = ps.executeQuery();
            while (rs.next()) {
                Goods goods = new Goods();
                goods.setGoodsId(rs.getInt("product_id"));
                goods.setSuperTypeId(rs.getInt("superTypeId"));
                goods.setSubTypeId(rs.getInt("subTypeId"));
                goods.setGoodsName(rs.getString("productName"));
                goods.setIntroduce(rs.getString("introduce"));
                goods.setPurchasePrice(rs.getFloat("purchase_price"));
                goods.setPrice(rs.getFloat("price"));
                goods.setNowPrice(rs.getFloat("nowPrice"));
                goods.setPicture(rs.getString("picture"));
                goods.setPublisher(rs.getString("publisher"));
                goods.setAuthor(rs.getString("author"));
                goods.setNewGoods(rs.getInt("newgoods"));
                goods.setSaleGoods(rs.getInt("salegoods"));
                goods.setHostGoods(rs.getInt("hostgoods"));
                goods.setSpecialGoods(rs.getInt("specialgoods"));
                goods.setCreateTime(rs.getTimestamp("create_time"));
                goods.setUpdateTime(rs.getTimestamp("update_time"));

                // 从库存表获取库存数量
                int stockQuantity = getStockQuantity(db.getCon(), rs.getInt("product_id"));
                goods.setStockQuantity(stockQuantity);

                goodsMap.put(goods.getGoodsId(), goods);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
                if (db != null) db.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        GoodsPager bp = new GoodsPager();
        bp.setGoodsMap(goodsMap);
        bp.setPageSize(pageSize);
        bp.setTotalNum(getAllGoods().size());
        return bp;
    }

    @Override
    public boolean deleteGoods(int[] goodsIds) {
        DbUtil daoUtil = null;
        PreparedStatement ps = null;
        Connection conn = null;
        boolean result = false;
        try {
            daoUtil = new DbUtil();
            conn = daoUtil.getCon();
            conn.setAutoCommit(false);
            String sql = "DELETE FROM product WHERE product_id = ?";
            ps = conn.prepareStatement(sql);
            for (int goodsId : goodsIds) {
                ps.setInt(1, goodsId);
                ps.addBatch();
            }
            int[] affectedRows = ps.executeBatch();
            conn.commit();
            result = affectedRows.length == goodsIds.length;
        } catch (Exception e) {
            try {
                if (conn != null) conn.rollback();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
        } finally {
            try {
                if (ps != null) ps.close();
                if (daoUtil != null) daoUtil.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    /**
     * 检查subTypeId是否存在于tb_subtype表中
     */
    private boolean checkSubTypeIdExists(int subTypeId) {
        DbUtil daoUtil = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        boolean exists = false;
        try {
            daoUtil = new DbUtil();
            String sql = "SELECT COUNT(1) FROM tb_subtype WHERE subTypeId = ?";
            ps = daoUtil.getCon().prepareStatement(sql);
            ps.setInt(1, subTypeId);
            rs = ps.executeQuery();
            if (rs.next()) {
                exists = rs.getInt(1) > 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
                if (daoUtil != null) daoUtil.close();
            } catch (SQLException s) {
                s.printStackTrace();
            }
        }
        return exists;
    }

    /**
     * 从库存表获取商品库存数量
     */
    private int getStockQuantity(Connection conn, int productId) throws SQLException {
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            String sql = "SELECT SUM(quantity) FROM tb_product_inventory WHERE productId = ? AND status = 1";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, productId);
            rs = ps.executeQuery();
            if (rs.next()) {
                int sum_num= rs.getInt(1) == 0 ? 0 : rs.getInt(1);
                return sum_num;

            }
        } finally {
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return 0;
    }
}

