

package com.mall.daoimpl;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mall.common.DbUtil;
import com.mall.dao.GoodsDao;
import com.mall.po.BuyRecord;
import com.mall.po.Goods;
import com.mall.po.Page;

public class GoodsDaoImpl implements GoodsDao {
	/**
	 * 显示商品
	 * @param type 显示类别（热卖，新商品，降价,特别推荐）
	 * @param flag 表示是否是（热卖，新商品，降价）
	 */
	public List<Goods> showGoods(int type, int flag) {
		List<Goods> all = new ArrayList<>();
		PreparedStatement pstmt = null;
		DbUtil dbUtil = null;
		ResultSet rs = null;
		String sql = null;

		if (type == 0) {
			// 显示所有商品
			sql = "SELECT * FROM product";
			try {
				dbUtil = new DbUtil();
				pstmt = dbUtil.getCon().prepareStatement(sql);
				rs = pstmt.executeQuery();
				while (rs.next()) {
					Goods goods = new Goods();
					goods.setGoodsId(rs.getInt("product_id"));
					goods.setGoodsName(rs.getString("productName"));
					goods.setISBN(rs.getString("ISBN"));
					goods.setIntroduce(rs.getString("introduce"));
					goods.setPurchasePrice((float) rs.getBigDecimal("purchase_price").doubleValue());
					goods.setPrice((float) rs.getBigDecimal("price").doubleValue());
					goods.setNowPrice((float) rs.getBigDecimal("nowPrice").doubleValue());
					goods.setPicture(rs.getString("picture"));
					goods.setPublisher(rs.getString("publisher"));
					goods.setAuthor(rs.getString("author"));
					goods.setNewGoods(rs.getInt("newgoods"));
					goods.setSaleGoods(rs.getInt("salegoods"));
					goods.setHostGoods(rs.getInt("hostgoods"));
					goods.setSpecialGoods(rs.getInt("specialgoods"));


					// 从库存表获取库存数量
					goods.setGoodsNum(getStockQuantity(dbUtil.getCon(), rs.getInt("product_id")));

					all.add(goods);
				}


				while(rs.next()) {
					Goods Goods = new Goods();
					Goods.setGoodsId(rs.getInt("bookId"));
					Goods.setGoodsName(rs.getString("GoodsName"));
					Goods.setISBN(rs.getString("ISBN"));
					Goods.setProduceDate(rs.getString("produceDate"));
					Goods.setAuthor(rs.getString("author"));
					Goods.setPublisher(rs.getString("publisher"));
					Goods.setIntroduce(rs.getString("introduce"));
					Goods.setPrice(rs.getFloat("price"));
					Goods.setNowPrice(rs.getFloat("nowPrice"));
					Goods.setPicture(rs.getString("picture"));
					all.add(Goods);
				}








			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				try {
					if (rs != null) rs.close();
					if (pstmt != null) pstmt.close();
					if (dbUtil != null) dbUtil.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		} else {
			if (type == 1) {
				// 热卖商品
				sql = "SELECT * FROM product WHERE hostgoods = ?";
			} else if (type == 2) {
				// 新到商品
				sql = "SELECT * FROM product WHERE newgoods = ?";
			} else if (type == 3) {
				// 打折商品
				sql = "SELECT * FROM product WHERE salegoods = ?";
			} else if (type == 4) {
				// 特别推荐
				sql = "SELECT * FROM product WHERE specialgoods = ?";
			} else if (type == 9) {
				// 分类查看
				sql = "SELECT * FROM product WHERE superTypeId = ?";
			}

			try {
				dbUtil = new DbUtil();
				pstmt = dbUtil.getCon().prepareStatement(sql);
				pstmt.setInt(1, flag);
				rs = pstmt.executeQuery();
				while (rs.next()) {
					Goods goods = new Goods();
					goods.setGoodsId(rs.getInt("product_id"));
					goods.setGoodsName(rs.getString("productName"));
					goods.setISBN(rs.getString("ISBN"));
					goods.setIntroduce(rs.getString("introduce"));
					goods.setPurchasePrice((float) rs.getBigDecimal("purchase_price").doubleValue());
					goods.setPrice((float) rs.getBigDecimal("price").doubleValue());
					goods.setNowPrice((float) rs.getBigDecimal("nowPrice").doubleValue());
					goods.setPicture(rs.getString("picture"));
					goods.setPublisher(rs.getString("publisher"));
					goods.setAuthor(rs.getString("author"));
					goods.setNewGoods(rs.getInt("newgoods"));
					goods.setSaleGoods(rs.getInt("salegoods"));
					goods.setHostGoods(rs.getInt("hostgoods"));
					goods.setSpecialGoods(rs.getInt("specialgoods"));

					// 从库存表获取库存数量
					goods.setGoodsNum(getStockQuantity(dbUtil.getCon(), rs.getInt("product_id")));

					all.add(goods);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				try {
					if (rs != null) rs.close();
					if (pstmt != null) pstmt.close();
					if (dbUtil != null) dbUtil.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return all;
	}

	@Override
	public boolean updateGoodsNum(int num, int productId) {
		System.out.println("productId = " + productId);
		System.out.println(productId);
		System.out.println(num);

		if (num <= 0 || productId <= 0) {
			return false;
		}

		DbUtil daoUtil = null;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql;

		try {
			daoUtil = new DbUtil();
			conn = daoUtil.getCon();
			conn.setAutoCommit(false); // 开启事务

			// 1. 查询该产品的所有库存记录，按入库时间升序排列
			sql = "SELECT inventoryId, quantity, stockInTime " +
					"FROM tb_product_inventory " +
					"WHERE productId = ? AND status = 1 " +
					"ORDER BY stockInTime ASC";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, productId);
			rs = ps.executeQuery();

			// 存储需要更新的库存记录
			List<Map<String, Object>> inventoryList = new ArrayList<>();
			while (rs.next()) {
				Map<String, Object> inventory = new HashMap<>();
				inventory.put("inventoryId", rs.getInt("inventoryId"));
				inventory.put("quantity", rs.getInt("quantity"));
				inventory.put("stockInTime", rs.getTimestamp("stockInTime"));
				inventoryList.add(inventory);
			}
			rs.close();
			ps.close();

			// 2. 计算需要从各批次减少的库存
			int remainingNum = num;
			for (Map<String, Object> inventory : inventoryList) {
				if (remainingNum <= 0) {
					break;
				}

				int inventoryId = (int) inventory.get("inventoryId");
				int currentQuantity = (int) inventory.get("quantity");

				if (currentQuantity >= remainingNum) {
					// 该批次库存足够，直接扣减
					updateInventory(conn, inventoryId, currentQuantity - remainingNum, remainingNum == currentQuantity);
					remainingNum = 0;
				} else {
					// 该批次库存不足，扣减全部库存
					updateInventory(conn, inventoryId, 0, true);
					remainingNum -= currentQuantity;
				}
			}

			// 如果仍有剩余数量，说明库存不足
			if (remainingNum > 0) {
				conn.rollback();
				return false;
			}

			conn.commit();
			return true;

		} catch (Exception e) {
			try {
				if (conn != null) {
					conn.rollback();
				}
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
			e.printStackTrace();
			return false;
		} finally {
			try {
				if (rs != null) rs.close();
				if (ps != null) ps.close();
				if (conn != null) {
					conn.setAutoCommit(true); // 恢复自动提交模式
					conn.close();
				}
				if (daoUtil != null) daoUtil.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}

	private void updateInventory(Connection conn, int inventoryId, int newQuantity, boolean isZero) throws SQLException {
		String sql;
		if (isZero) {
			// 库存减为0，更新状态和出库时间
			sql = "UPDATE tb_product_inventory " +
					"SET quantity = ?, status = 0, stockOutTime = NOW() " +
					"WHERE inventoryId = ?";
		} else {
			// 库存未减为0，仅更新数量
			sql = "UPDATE tb_product_inventory " +
					"SET quantity = ? " +
					"WHERE inventoryId = ?";
		}

		try (PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setInt(1, newQuantity);
			ps.setInt(2, inventoryId);
			ps.executeUpdate();
		}
	}

	@Override
	public boolean updateGoods(Goods goods) {
		DbUtil daoUtil = null;
		PreparedStatement ps = null;
		String sql = "UPDATE product SET " +
				"productName = ?, " +
				"goodsNum = ?, " +
				"introduce = ?, " +
				"ISBN = ?, " +
				"author = ?, " +
				"superTypeId = ?, " +
				"subTypeId = ?, " +
				"publisher = ?, " +
				"price = ?, " +
				"nowPrice = ?, " +
				"newgoods = ?, " +
				"salegoods = ?, " +
				"hostgoods = ?, " +
				"specialgoods = ? " +
				"update_time = now() " +
				"WHERE product_id = ?";
		try {
			daoUtil = new DbUtil();
			ps = daoUtil.getCon().prepareStatement(sql);
			ps.setString(1, goods.getGoodsName());
			ps.setInt(2, goods.getGoodsNum());
			ps.setString(3, goods.getIntroduce());
			ps.setString(4, goods.getISBN());
			ps.setString(5, goods.getAuthor());
			ps.setInt(6, goods.getSuperTypeId());
			ps.setInt(7, goods.getSubTypeId());
			ps.setString(8, goods.getPublisher());
			ps.setBigDecimal(9, new BigDecimal(goods.getPrice()));
			ps.setBigDecimal(10, new BigDecimal(goods.getNowPrice()));
			ps.setInt(11, goods.getNewGoods());
			ps.setInt(12, goods.getSaleGoods());
			ps.setInt(13, goods.getHostGoods());
			ps.setInt(14, goods.getSpecialGoods());
			ps.setInt(15, goods.getGoodsId());
			int i = ps.executeUpdate();
			return i != 0;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (ps != null) ps.close();
				if (daoUtil != null) daoUtil.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return false;
	}

	/**
	 * 根据商品的GoodsId号来展示该商品的详细信息
	 * @param goodsId 商品号
	 * @return Goods
	 */
	@Override
	public Goods showGoodsById(int goodsId) {
		Goods goods = new Goods();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		DbUtil dbUtil = null;
		String sql = "SELECT * FROM product WHERE product_id = ?";
		try {
			dbUtil = new DbUtil();
			pstmt = dbUtil.getCon().prepareStatement(sql);
			pstmt.setInt(1, goodsId);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				goods.setGoodsId(rs.getInt("product_id"));
				goods.setGoodsName(rs.getString("productName"));
				goods.setIntroduce(rs.getString("introduce"));
				goods.setISBN(rs.getString("ISBN"));
				goods.setAuthor(rs.getString("author"));
				goods.setSuperTypeId(rs.getInt("superTypeId"));
				goods.setSubTypeId(rs.getInt("subTypeId"));
				goods.setPublisher(rs.getString("publisher"));
				goods.setPrice((float) rs.getBigDecimal("price").doubleValue());
				goods.setNowPrice((float) rs.getBigDecimal("nowPrice").doubleValue());
				goods.setPicture(rs.getString("picture"));

				int goodsNum = getStockQuantity(dbUtil.getCon(), rs.getInt("product_id"));
				goods.setGoodsNum(goodsNum);

//				goods.setGoodsNum(rs.getInt("goodsNum"));
				goods.setHostGoods(rs.getInt("hostgoods"));
				goods.setNewGoods(rs.getInt("newgoods"));
				goods.setSaleGoods(rs.getInt("salegoods"));
				goods.setSpecialGoods(rs.getInt("specialgoods"));
				goods.setCreateTime(rs.getTimestamp("create_time"));
				goods.setUpdateTime(rs.getTimestamp("update_time"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null) rs.close();
				if (pstmt != null) pstmt.close();
				if (dbUtil != null) dbUtil.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return goods;
	}

	@Override
	public List<BuyRecord> showBuyRecordsById(int goodsId) {
		List<BuyRecord> searchList = new ArrayList<>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		DbUtil dbUtil = null;
		String sql = "SELECT B.name, A.goodsNum, B.orderDate " +
				"FROM tb_orderitem A, tb_order B " +
				"WHERE A.orderId = B.orderId AND A.bookId = ?";
		try {
			dbUtil = new DbUtil();
			pstmt = dbUtil.getCon().prepareStatement(sql);
			pstmt.setInt(1, goodsId);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				BuyRecord brc = new BuyRecord();
				brc.setName(rs.getString("name"));
				brc.setGoodsNum(rs.getInt("goodsNum"));
				brc.setOrderDate(rs.getString("orderDate"));
				searchList.add(brc);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return searchList;
	}

	/**
	 * 将商品信息录入数据库
	 */
	@Override
	public boolean addGoods(Goods goods) {
		boolean flag = false;
		PreparedStatement pstmt = null;
		DbUtil dbUtil = null;
		String sql = "INSERT INTO product " +
				"(superTypeId, subTypeId, productName, ISBN, introduce, " +
				"purchase_price, price, nowPrice, picture, publisher, " +
				"author, newgoods, salegoods, hostgoods, goodsNum,create_time,update_time) " +
				"VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,now(),now())";
		try {
			dbUtil = new DbUtil();
			pstmt = dbUtil.getCon().prepareStatement(sql);
			pstmt.setInt(1, goods.getSuperTypeId());
			pstmt.setInt(2, goods.getSubTypeId());
			pstmt.setString(3, goods.getGoodsName());
			pstmt.setString(4, goods.getISBN());
			pstmt.setString(5, goods.getIntroduce());
			pstmt.setBigDecimal(6, new BigDecimal(goods.getPurchasePrice()));
			pstmt.setBigDecimal(7, new BigDecimal(goods.getPrice()));
			pstmt.setBigDecimal(8, new BigDecimal(goods.getNowPrice()));
			pstmt.setString(9, goods.getPicture());
			pstmt.setString(10, goods.getPublisher());
			pstmt.setString(11, goods.getAuthor());
			pstmt.setInt(12, goods.getNewGoods());
			pstmt.setInt(13, goods.getSaleGoods());
			pstmt.setInt(14, goods.getHostGoods());
			pstmt.setInt(15, goods.getGoodsNum());
			int i = pstmt.executeUpdate();
			flag = i != 0;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (pstmt != null) pstmt.close();
				if (dbUtil != null) dbUtil.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return flag;
	}

	/**
	 * 分页显示商品
	 * @param type 按要求显示相应的商品
	 * @param currentPage 显示出来的当前页码
	 * @param pageSize 每页显示数目
	 * @param flag 是否分类显示标识，-1表示不分类，其他值为大分类ID
	 * @return Page
	 */
	@Override
	public Page doPage(int type, int currentPage, int pageSize, int flag) {
		Page page = new Page();
		int totalNum = 0;

		if (flag == -1) {
			totalNum = showGoods(type, 1).size();
		} else {
			totalNum = showGoods(9, flag).size();
		}

		List<Goods> pageList = new ArrayList<>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		DbUtil dbUtil = null;
		String sql = null;

		if (flag == -1) {
			if (type == 0) {
				// 显示所有商品
				sql = "SELECT * FROM product LIMIT ?, ?";
			} else if (type == 1) {
				// 热卖商品
				sql = "SELECT * FROM product WHERE hostgoods = 1 LIMIT ?, ?";
			} else if (type == 2) {
				// 新到商品
				sql = "SELECT * FROM product WHERE newgoods = 1 LIMIT ?, ?";
			} else if (type == 3) {
				// 打折商品
				sql = "SELECT * FROM product WHERE salegoods = 1 LIMIT ?, ?";
			} else if (type == 4) {
				// 特别推荐
				sql = "SELECT * FROM product WHERE specialgoods = 1 LIMIT ?, ?";
			}
		} else {
			// 分类商品
			sql = "SELECT * FROM product WHERE superTypeId = ? LIMIT ?, ?";
		}

		try {
			dbUtil = new DbUtil();
			if (flag != -1) {
				pstmt = dbUtil.getCon().prepareStatement(sql);
				pstmt.setInt(1, flag);
				pstmt.setInt(2, currentPage);
				pstmt.setInt(3, pageSize);
			} else {
				pstmt = dbUtil.getCon().prepareStatement(sql);
				pstmt.setInt(1, currentPage);
				pstmt.setInt(2, pageSize);
			}
			rs = pstmt.executeQuery();
			while (rs.next()) {
				Goods goods = new Goods();
				goods.setGoodsId(rs.getInt("product_id"));
				goods.setGoodsName(rs.getString("productName"));
				goods.setPicture(rs.getString("picture"));
				goods.setPrice((float) rs.getBigDecimal("price").doubleValue());
				goods.setNowPrice((float) rs.getBigDecimal("nowPrice").doubleValue());

				// 从库存表获取库存数量
				goods.setGoodsNum(getStockQuantity(dbUtil.getCon(), rs.getInt("product_id")));

				pageList.add(goods);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null) rs.close();
				if (pstmt != null) pstmt.close();
				if (dbUtil != null) dbUtil.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		page.setPageList(pageList);
		page.setTotalNum(totalNum);
		return page;
	}


//	private int getStockQuantity(Connection conn, int productId) throws SQLException {
//		PreparedStatement ps = null;
//		ResultSet rs = null;
//		try {
//			String sql = "SELECT SUM(quantity) FROM tb_product_inventory WHERE productId = ? AND status = 1";
//			ps = conn.prepareStatement(sql);
//			ps.setInt(1, productId);
//			rs = ps.executeQuery();
//			if (rs.next()) {
//				return rs.getInt(1) == 0 ? 0 : rs.getInt(1);
//			}
//		} finally {
//			try {
//				if (rs != null) rs.close();
//				if (ps != null) ps.close();
//			} catch (SQLException e) {
//				e.printStackTrace();
//			}
//		}
//		return 0;
//	}






	/**
	 * 根据用户输入的关键字搜索相关商品
	 * @param keywords 用户输入的关键字
	 */
	@Override
	public List<Goods> searchGoods(String keywords) {
		List<Goods> searchList = new ArrayList<>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		DbUtil dbUtil = null;
		String sql = "SELECT * FROM product WHERE productName LIKE ?";
		try {
			dbUtil = new DbUtil();
			pstmt = dbUtil.getCon().prepareStatement(sql);
			pstmt.setString(1, "%" + keywords + "%");
			rs = pstmt.executeQuery();
			while (rs.next()) {
				Goods goods = new Goods();
				goods.setGoodsId(rs.getInt("product_id"));
				goods.setGoodsName(rs.getString("productName"));
				goods.setPicture(rs.getString("picture"));
				goods.setPrice((float) rs.getBigDecimal("price").doubleValue());
				goods.setNowPrice((float) rs.getBigDecimal("nowPrice").doubleValue());
				goods.setHostGoods(rs.getInt("hostgoods"));
				goods.setNewGoods(rs.getInt("newgoods"));
				goods.setSaleGoods(rs.getInt("salegoods"));

				// 从库存表获取库存数量
				goods.setGoodsNum(getStockQuantity(dbUtil.getCon(), rs.getInt("product_id")));

				searchList.add(goods);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null) rs.close();
				if (pstmt != null) pstmt.close();
				if (dbUtil != null) dbUtil.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return searchList;
	}

	/**
	 * 分页显示搜索结果
	 * @param keywords 用户输入的关键字
	 * @param currentPage 当前页码
	 * @param pageSize 每页显示数量
	 * @return 分页对象
	 */
	@Override
	public Page doPage(String keywords, int currentPage, int pageSize) {
		Page page = new Page();
		int totalNum = searchGoods(keywords).size();
		List<Goods> pageList = new ArrayList<>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		DbUtil dbUtil = null;
		String sql = "SELECT * FROM product WHERE productName LIKE ? LIMIT ?, ?";

		try {
			dbUtil = new DbUtil();
			pstmt = dbUtil.getCon().prepareStatement(sql);
			pstmt.setString(1, "%" + keywords + "%");
			pstmt.setInt(2, currentPage);
			pstmt.setInt(3, pageSize);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				Goods goods = new Goods();
				goods.setGoodsId(rs.getInt("product_id"));
				goods.setGoodsName(rs.getString("productName"));
				goods.setPicture(rs.getString("picture"));
				goods.setPrice((float) rs.getBigDecimal("price").doubleValue());
				goods.setNowPrice((float) rs.getBigDecimal("nowPrice").doubleValue());

				// 从库存表获取库存数量
				goods.setGoodsNum(getStockQuantity(dbUtil.getCon(), rs.getInt("product_id")));

				pageList.add(goods);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null) rs.close();
				if (pstmt != null) pstmt.close();
				if (dbUtil != null) dbUtil.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		page.setPageList(pageList);
		page.setTotalNum(totalNum);
		return page;
	}

	@Override
	public List<Goods> searchGoodsByConditions(int superTypeId, int subTypeId, String searchMethod) {
		List<Goods> goodsList = new ArrayList<>();
		String[] str = searchMethod.split("=");
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		DbUtil dbUtil = null;
		String sql = "SELECT * FROM product WHERE superTypeId = ? AND subTypeId = ? AND " + str[0] + " = ?";

		try {
			dbUtil = new DbUtil();
			pstmt = dbUtil.getCon().prepareStatement(sql);
			pstmt.setInt(1, superTypeId);
			pstmt.setInt(2, subTypeId);
			pstmt.setString(3, str[1]);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				Goods goods = new Goods();
				goods.setGoodsId(rs.getInt("product_id"));
				goods.setGoodsName(rs.getString("productName"));
				goods.setPicture(rs.getString("picture"));
				goods.setPrice((float) rs.getBigDecimal("price").doubleValue());
				goods.setNowPrice((float) rs.getBigDecimal("nowPrice").doubleValue());

				// 从库存表获取库存数量
				goods.setGoodsNum(getStockQuantity(dbUtil.getCon(), rs.getInt("product_id")));

				goodsList.add(goods);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null) rs.close();
				if (pstmt != null) pstmt.close();
				if (dbUtil != null) dbUtil.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return goodsList;
	}

	/**
	 * 根据条件分页显示商品
	 * @param superTypeId 大类ID
	 * @param subTypeId 小类ID
	 * @param searchMethod 搜索条件
	 * @param currentPage 当前页码
	 * @param pageSize 每页显示数量
	 * @return 分页对象
	 */
	@Override
	public Page doPageByConditons(int superTypeId, int subTypeId, String searchMethod, int currentPage, int pageSize) {
		Page page = new Page();
		String[] str = searchMethod.split("=");
		int totalNum = searchGoodsByConditions(superTypeId, subTypeId, searchMethod).size();
		List<Goods> pageList = new ArrayList<>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		DbUtil dbUtil = null;
		String sql = "SELECT * FROM product WHERE superTypeId = ? AND subTypeId = ? AND " + str[0] + " = ? LIMIT ?, ?";

		try {
			dbUtil = new DbUtil();
			pstmt = dbUtil.getCon().prepareStatement(sql);
			pstmt.setInt(1, superTypeId);
			pstmt.setInt(2, subTypeId);
			pstmt.setString(3, str[1]);
			pstmt.setInt(4, currentPage);
			pstmt.setInt(5, pageSize);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				Goods goods = new Goods();
				goods.setGoodsId(rs.getInt("product_id"));
				goods.setGoodsName(rs.getString("productName"));
				goods.setPicture(rs.getString("picture"));
				goods.setPrice((float) rs.getBigDecimal("price").doubleValue());
				goods.setNowPrice((float) rs.getBigDecimal("nowPrice").doubleValue());
				goods.setAuthor(rs.getString("author"));
				goods.setPublisher(rs.getString("publisher"));
				goods.setISBN(rs.getString("ISBN"));
				goods.setSuperTypeId(rs.getInt("superTypeId"));
				goods.setSubTypeId(rs.getInt("subTypeId"));

				// 从库存表获取库存数量
				goods.setGoodsNum(getStockQuantity(dbUtil.getCon(), rs.getInt("product_id")));

				pageList.add(goods);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null) rs.close();
				if (pstmt != null) pstmt.close();
				if (dbUtil != null) dbUtil.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		page.setPageList(pageList);
		page.setTotalNum(totalNum);
		return page;
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
				return rs.getInt(1);
			}
			return 0;
		} finally {
			try {
				if (rs != null) rs.close();
				if (ps != null) ps.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}