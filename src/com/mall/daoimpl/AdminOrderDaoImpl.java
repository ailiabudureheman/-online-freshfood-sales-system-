package com.mall.daoimpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mall.common.DbUtil;
import com.mall.dao.AdminOrderDao;
import com.mall.po.Order;
import com.mall.po.OrderFreezePager;
import com.mall.po.OrderItem;
import com.mall.po.OrderNotSendPager;
import com.mall.po.OrderPager;
import com.mall.po.OrderSendPager;
import com.mall.po.User;

public class AdminOrderDaoImpl implements AdminOrderDao {

	public List getAllOrder() {
		List orderList = new ArrayList();
		DbUtil dao = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			dao = new DbUtil();
			String sql = "select * from tb_order order by orderId";
			ps = dao.getCon().prepareStatement(sql);
			rs = ps.executeQuery();
			while(rs.next()) {
				Order order = new Order();
				User user = new User();
				order.setOrderId(rs.getInt("orderId"));
				user.setName(rs.getString("name"));
				order.setRecvName(rs.getString("recvName"));
				user.setAddress(rs.getString("address"));
				user.setPostcode(rs.getString("postcode"));
				user.setEmail(rs.getString("email"));
				order.setUser(user);
				order.setOrderDate(rs.getString("orderDate"));
				order.setFlag(rs.getInt("flag"));
				orderList.add(order);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				rs.close();
				ps.close();
				dao.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return orderList;
	}

	public Order getOneOrder(int id) {
		Order order = new Order();
		List orderItemList = new ArrayList();
		DbUtil dao = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			dao = new DbUtil();
			String sql = "select * from tb_orderItem where orderId = ? order by orderId";
			ps = dao.getCon().prepareStatement(sql);
			ps.setInt(1, id);
			rs = ps.executeQuery();
			while (rs.next()) {
				OrderItem orderItem = new OrderItem();
				orderItem.setOrderItemId(rs.getInt("orderItemId"));
				orderItem.setGoodsId(rs.getInt("bookId"));
				orderItem.setGoodsName(rs.getString("goodsName"));
				orderItem.setPrice(rs.getFloat("price"));
				orderItem.setGoodsNum(rs.getInt("goodsNum"));
				orderItemList.add(orderItem);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				rs.close();
				ps.close();
				dao.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		order.setOrderItem(orderItemList);
		return order;
	}

	public List getNotSendOrder(int flag) {
		List orderList = new ArrayList();
		DbUtil dao = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			dao = new DbUtil();
			String sql = "select * from tb_order where flag = ? order by orderId";
			ps = dao.getCon().prepareStatement(sql);
			ps.setInt(1, flag);
			rs = ps.executeQuery();
			while(rs.next()) {
				Order order = new Order();
				User user = new User();
				order.setOrderId(rs.getInt("orderId"));
				user.setName(rs.getString("name"));
				order.setRecvName(rs.getString("recvName"));
				user.setAddress(rs.getString("address"));
				user.setPostcode(rs.getString("postcode"));
				user.setEmail(rs.getString("email"));
				order.setUser(user);
				order.setOrderDate(rs.getString("orderDate"));
				order.setFlag(rs.getInt("flag"));
				orderList.add(order);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				rs.close();
				ps.close();
				dao.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return orderList;
	}

	public List getSendOrder(int flag) {
		List orderList = new ArrayList();
		DbUtil dao = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			dao = new DbUtil();
			String sql = "select * from tb_order where flag = ? order by orderId";
			ps = dao.getCon().prepareStatement(sql);
			ps.setInt(1, flag);
			rs = ps.executeQuery();
			while(rs.next()) {
				Order order = new Order();
				User user = new User();
				order.setOrderId(rs.getInt("orderId"));
				user.setName(rs.getString("name"));
				order.setRecvName(rs.getString("RecvName"));
				user.setAddress(rs.getString("address"));
				user.setPostcode(rs.getString("postcode"));
				user.setEmail(rs.getString("email"));
				order.setUser(user);
				order.setOrderDate(rs.getString("orderDate"));
				order.setFlag(rs.getInt("flag"));
				orderList.add(order);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				rs.close();
				ps.close();
				dao.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return orderList;
	}

	public boolean SendOrders(int[] orderids) {
		DbUtil daoUtil = null;
		PreparedStatement ps = null;
		Connection conn = null;
		String sql = "update tb_order set flag=1 where orderId=? order by orderId";
		try {
			daoUtil = new DbUtil();
			conn = daoUtil.getCon();
			conn.setAutoCommit(false);
			ps = conn.prepareStatement(sql);
			for(int j=0;j<orderids.length;j++) {
				ps.setInt(1, orderids[j]);
				ps.addBatch();
			}
			int[] k = ps.executeBatch();
			conn.commit();
			if(k.length == orderids.length) {
				return true;
			}
		} catch (Exception e) {
			try {
				conn.rollback();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		} finally {
			try {
				ps.close();
				daoUtil.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return false;
	}
	public boolean SendOrder(int orderid) {
		DbUtil dbUtil = null;
		PreparedStatement pre = null;
		String sql = "update tb_order set flag=1 where orderId=? order by orderId";
		try {
			dbUtil = new DbUtil();
			pre = dbUtil.getCon().prepareStatement(sql);
			pre.setInt(1, orderid);
			int i = pre.executeUpdate();
			if(i != 0) {// 添加成功
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				pre.close();
				dbUtil.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return false;
	}

	public OrderPager getOrderPager(int index, int pageSize) {
		Map orderMap = new HashMap();
		DbUtil db = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			db = new DbUtil();
			String sql = "select * from tb_order order by orderId limit ?,? ";
			ps = db.getCon().prepareStatement(sql);
			ps.setInt(1, index);
			ps.setInt(2, pageSize);
			rs = ps.executeQuery();
			while(rs.next()) {
				Order order = new Order();
				User user = new User();
				order.setOrderId(rs.getInt("orderId"));
				user.setName(rs.getString("name"));
				order.setRecvName(rs.getString("recvName"));
				user.setAddress(rs.getString("address"));
				user.setPostcode(rs.getString("postcode"));
				user.setEmail(rs.getString("email"));
				order.setUser(user);
				order.setOrderDate(rs.getString("orderDate"));
				order.setFlag(rs.getInt("flag"));
				orderMap.put(order.getOrderId(), order);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				rs.close();
				ps.close();
				db.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		OrderPager op = new OrderPager();
		op.setOrderMap(orderMap);
		op.setPageSize(pageSize);
		op.setTotalNum(getAllOrder().size());
		return op;
	}

	public OrderSendPager getOrderSendPager(int index, int pageSize) {
		Map orderMap = new HashMap();
		DbUtil db = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			db = new DbUtil();
			String sql = "select * from tb_order where flag=1 order by orderId limit ?,?";
			ps = db.getCon().prepareStatement(sql);
			ps.setInt(1, index);
			ps.setInt(2, pageSize);
			rs = ps.executeQuery();
			while(rs.next()) {
				Order order = new Order();
				User user = new User();
				order.setOrderId(rs.getInt("orderId"));
				user.setName(rs.getString("name"));
				order.setRecvName(rs.getString("recvName"));
				user.setAddress(rs.getString("address"));
				user.setPostcode(rs.getString("postcode"));
				user.setEmail(rs.getString("email"));
				order.setUser(user);
				order.setOrderDate(rs.getString("orderDate"));
				order.setFlag(rs.getInt("flag"));
				orderMap.put(order.getOrderId(), order);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				rs.close();
				ps.close();
				db.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		OrderSendPager op = new OrderSendPager();
		op.setOrderMap(orderMap);
		op.setPageSize(pageSize);
		op.setTotalNum(getSendOrder(1).size());
		return op;
	}
	public boolean deleteOrder(int[] ids) {		
		DbUtil daoUtil = null;
		PreparedStatement ps = null;
		PreparedStatement ps2 = null;
		Connection conn = null;
		String sql = "delete from tb_order where orderId=?";
		String sql2 = "delete from tb_orderitem where orderId=?";
		
		try {
			daoUtil = new DbUtil();
			conn = daoUtil.getCon();
			conn.setAutoCommit(false);
			ps = conn.prepareStatement(sql);	
			ps2 = conn.prepareStatement(sql2);
			for(int j=0;j<ids.length;j++) {
				ps.setInt(1, ids[j]);
				ps2.setInt(1, ids[j]);
				ps.addBatch();
				ps2.addBatch();
			}
			int[] m = ps2.executeBatch();
			int[] k = ps.executeBatch();
			conn.commit();			
			if(k.length == ids.length) {
				return true;
			}			
		} catch (Exception e) {			
			try {
				conn.rollback();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		} finally {
			try {
				ps.close();
				daoUtil.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return false;
	}

	public OrderNotSendPager getOrderNotSendPager(int index, int pageSize) {
		Map orderMap = new HashMap();
		DbUtil db = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			db = new DbUtil();
			String sql = "select * from tb_order where flag=0 order by orderId limit ?,?";
			ps = db.getCon().prepareStatement(sql);
			ps.setInt(1, index);
			ps.setInt(2, pageSize);
			rs = ps.executeQuery();
			while(rs.next()) {
				Order order = new Order();
				User user = new User();
				order.setOrderId(rs.getInt("orderId"));
				user.setName(rs.getString("name"));
				order.setRecvName(rs.getString("recvName"));
				user.setAddress(rs.getString("address"));
				user.setPostcode(rs.getString("postcode"));
				user.setEmail(rs.getString("email"));
				order.setUser(user);
				order.setOrderDate(rs.getString("orderDate"));
				order.setFlag(rs.getInt("flag"));
				orderMap.put(order.getOrderId(), order);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				rs.close();
				ps.close();
				db.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		OrderNotSendPager op = new OrderNotSendPager();
		op.setOrderMap(orderMap);
		op.setPageSize(pageSize);
		op.setTotalNum(getSendOrder(0).size());
		return op;
	}
	public OrderFreezePager getOrderFreezePager(int index,int pageSize) {
		Map orderMap = new HashMap();
		DbUtil db = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			db = new DbUtil();
			String sql = "select * from tb_order where flag=2 order by orderId limit ?,?";
			ps = db.getCon().prepareStatement(sql);
			ps.setInt(1, index);
			ps.setInt(2, pageSize);
			rs = ps.executeQuery();
			while(rs.next()) {
				Order order = new Order();
				User user = new User();
				order.setOrderId(rs.getInt("orderId"));
				user.setName(rs.getString("name"));
				order.setRecvName(rs.getString("recvName"));
				user.setAddress(rs.getString("address"));
				user.setPostcode(rs.getString("postcode"));
				user.setEmail(rs.getString("email"));
				order.setUser(user);
				order.setOrderDate(rs.getString("orderDate"));
				order.setFlag(rs.getInt("flag"));
				orderMap.put(order.getOrderId(), order);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				rs.close();
				ps.close();
				db.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		OrderFreezePager op = new OrderFreezePager();
		op.setOrderMap(orderMap);
		op.setPageSize(pageSize);
		op.setTotalNum(getSendOrder(2).size());
		return op;
	}

	public boolean freezeOrder(int[] orderids) {
		DbUtil daoUtil = null;
		PreparedStatement ps = null;
		Connection conn = null;
		String sql = "update tb_order set flag=2 where orderId=? order by orderId";
		try {
			daoUtil = new DbUtil();
			conn = daoUtil.getCon();
			conn.setAutoCommit(false);
			ps = conn.prepareStatement(sql);
			for(int j=0;j<orderids.length;j++) {
				ps.setInt(1, orderids[j]);
				ps.addBatch();
			}
			int[] k = ps.executeBatch();
			conn.commit();
			if(k.length == orderids.length) {
				return true;
			}
		} catch (Exception e) {
			try {
				conn.rollback();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		} finally {
			try {
				ps.close();
				daoUtil.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return false;
	}

	@Override
	public OrderPager getOrderPagerByConditions(int offset, int pageSize, String status, String orderNo, String customerName) {
		Map orderMap = new HashMap();
		DbUtil db = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			db = new DbUtil();
			String sql = "select * from tb_order where 1=1 ";
			List<String> params = new ArrayList<>();
			if (status != null && !status.isEmpty()) {
				// 假设 status 和 flag 有对应关系，比如 "未发货" 对应 flag=0 等，这里根据实际业务调整
				sql += " and flag = ? ";
				params.add(status);
			}
			if (orderNo != null && !orderNo.isEmpty()) {
				sql += " and orderId like ? ";
				params.add("%" + orderNo + "%");
			}
			if (customerName != null && !customerName.isEmpty()) {
				sql += " and name like ? ";
				params.add("%" + customerName + "%");
			}
			sql += " order by orderId limit ?,? ";
			ps = db.getCon().prepareStatement(sql);
			for (int i = 0; i < params.size(); i++) {
				ps.setString(i + 1, params.get(i));
			}
			ps.setInt(params.size() + 1, offset);
			ps.setInt(params.size() + 2, pageSize);
			rs = ps.executeQuery();
			while (rs.next()) {
				Order order = new Order();
				User user = new User();
				order.setOrderId(rs.getInt("orderId"));
				user.setName(rs.getString("name"));
				order.setRecvName(rs.getString("recvName"));
				user.setAddress(rs.getString("address"));
				user.setPostcode(rs.getString("postcode"));
				user.setEmail(rs.getString("email"));
				order.setUser(user);
				order.setOrderDate(rs.getString("orderDate"));
				order.setFlag(rs.getInt("flag"));
				orderMap.put(order.getOrderId(), order);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				rs.close();
				ps.close();
				db.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		OrderPager op = new OrderPager();
		op.setOrderMap(orderMap);
		op.setPageSize(pageSize);
		// 这里获取符合条件的总数量，也需写对应的带条件 count 查询方法，类似上面的逻辑，获取满足 status、orderNo、customerName 条件的总数
		op.setTotalNum(getOrderCountByConditions(status, orderNo, customerName));
		return op;
	}

	private int getOrderCountByConditions(String status, String orderNo, String customerName) {
		int count = 0;
		DbUtil db = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			db = new DbUtil();
			String sql = "select count(*) from tb_order where 1=1 ";
			List<String> params = new ArrayList<>();
			if (status != null && !status.isEmpty()) {
				sql += " and flag = ? ";
				params.add(status);
			}
			if (orderNo != null && !orderNo.isEmpty()) {
				sql += " and orderId like ? ";
				params.add("%" + orderNo + "%");
			}
			if (customerName != null && !customerName.isEmpty()) {
				sql += " and name like ? ";
				params.add("%" + customerName + "%");
			}
			ps = db.getCon().prepareStatement(sql);
			for (int i = 0; i < params.size(); i++) {
				ps.setString(i + 1, params.get(i));
			}
			rs = ps.executeQuery();
			if (rs.next()) {
				count = rs.getInt(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				rs.close();
				ps.close();
				db.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return count;
	}



}



