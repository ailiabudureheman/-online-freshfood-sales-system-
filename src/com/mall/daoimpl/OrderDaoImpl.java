package com.mall.daoimpl;

import com.mall.common.DbUtil;
import com.mall.dao.OrderDao;
import com.mall.po.Order;
import com.mall.po.OrderItem;
import com.mall.po.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderDaoImpl implements OrderDao {

	/**
	 * 添加订单，并返回订一个单号
	 * @param order 一个订单
	 * @return int  返回一个整型的订单号
	 */
	public int addOrder(Order order) {
		int orderId = 0;
		PreparedStatement pstmt_order = null; //用于对订单进行操作的预定义语句
		PreparedStatement pstmt_item = null; //用于对订单项进行操作的预定义语句
		ResultSet rs = null;
		DbUtil dbUtil = null;
		String sql_order = "insert into tb_order (name, recvName, address, postcode, email, flag) " +
				"values (?,?,?,?,?,?)";
		try {
			dbUtil = new DbUtil();
			dbUtil.getCon().setAutoCommit(false);

			pstmt_order = dbUtil.getCon().prepareStatement(sql_order, Statement.RETURN_GENERATED_KEYS);
			pstmt_order.setString(1, order.getUser().getName());
			pstmt_order.setString(2, order.getRecvName());  // 注意：这里调整了参数顺序
			pstmt_order.setString(3, order.getUser().getAddress());
			pstmt_order.setString(4, order.getUser().getPostcode());
			pstmt_order.setString(5, order.getUser().getEmail());
			pstmt_order.setInt(6, order.getFlag());
			pstmt_order.executeUpdate();

			rs = pstmt_order.getGeneratedKeys();
			if (rs.next()) {
				orderId = rs.getInt(1);
			}
			//将订单项中的数据录入数据库
			String sql_item = "insert into tb_orderItem values(null,?,?,?,?,?)";
			pstmt_item = dbUtil.getCon().prepareStatement(sql_item);
			List orderItem = order.getOrderItem();//得到所有订单项
			for(int i=0;i<orderItem.size();i++) {
				OrderItem orderItems = (OrderItem)orderItem.get(i);
				pstmt_item.setInt(1, orderId);
				pstmt_item.setInt(2, orderItems.getGoodsId());
				pstmt_item.setString(3, orderItems.getGoodsName());
				pstmt_item.setFloat(4, orderItems.getPrice());
				pstmt_item.setInt(5, orderItems.getGoodsNum());
				pstmt_item.addBatch();
			}
			pstmt_item.executeBatch();
			dbUtil.getCon().commit();
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				dbUtil.getCon().rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}finally {
			try {
				if (rs != null){
					rs.close();}
				else{
					System.out.println("rs is null");
				}
				if (pstmt_order != null) {pstmt_order.close();}else {System.out.println("pstmt_order is null");}
				if (pstmt_item != null) {pstmt_item.close();}else {System.out.println("pstmt_item is null");}
				if (dbUtil != null) {dbUtil.close();}else {System.out.println("dbUtil is null");}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		}
		return orderId;
	}
	//根据当前用户的用户名查订单
	public List selectOrder(String name) {
		List list = new ArrayList();
		Order or = null ;
		DbUtil dao = new DbUtil();
		PreparedStatement pre = null;
		ResultSet re = null;
		String sql = "select * from tb_order where name=?";	
		 try {
			pre = dao.getCon().prepareStatement(sql);
			pre.setString(1, name);
			  re =pre.executeQuery();
			  while(re.next()){
				  or = new Order ();
				  User user = new User();
				  or.setOrderId(re.getInt("orderId"));
				  user.setName(re.getString("name"));
				  or.setRecvName(re.getString("recvName"));
				  user.setAddress(re.getString("address"));
				  user.setPostcode(re.getString("postcode"));
				  user.setEmail(re.getString("email"));
				  or.setUser(user);
				  or.setOrderDate(re.getString("orderDate"));
				  or.setFlag(re.getInt("flag"));
				  if(re.getInt("Flag")==0){
					  or.setFlagName("等待发货");
				  }else if(re.getInt("Flag")==1){
					  or.setFlagName("已发货");
				  }else{
					  or.setFlagName("已确认收货");
				  }
				  list.add(or);
			  }
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	// 根据订单号查订单项
	public List selectOrderItem(int id ) {
		List list = new ArrayList();
		OrderItem ordetrItem = null ;
		DbUtil dao = new DbUtil();
		PreparedStatement pre = null;
		ResultSet re = null;
		String sql = "select * from tb_orderItem where orderId = ? ";
		try{
		pre = dao.getCon().prepareStatement(sql);
		pre.setInt(1, id);
		  re =pre.executeQuery();
		  while(re.next()){
			  ordetrItem = new OrderItem();
			  ordetrItem.setGoodsId(re.getInt("bookId"));
			  ordetrItem.setGoodsName(re.getString("goodsName"));
			  ordetrItem.setGoodsNum(re.getInt("goodsNum"));
			  ordetrItem.setOrderId(re.getInt("orderId"));
			  ordetrItem.setOrderItemId(re.getInt("orderItemId"));
			  ordetrItem.setPrice(re.getFloat("price"));
			  list.add(ordetrItem);
		  }
		}catch (Exception e) {
		}
		return list;
	}
}
