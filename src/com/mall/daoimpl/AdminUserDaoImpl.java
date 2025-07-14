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
import com.mall.dao.AdminUserDao;
import com.mall.po.Goods;
import com.mall.po.User;
import com.mall.po.UserPager;

public class AdminUserDaoImpl implements AdminUserDao{

	public boolean deleteUserById(int id) {
		DbUtil daoUtil = null;
		PreparedStatement ps = null;
		String sql = "delete from tb_user where id=?";
		try {
			daoUtil = new DbUtil();
			ps = daoUtil.getCon().prepareStatement(sql);
			ps.setInt(1, id);
			int i = ps.executeUpdate();
			if (i != 0) {
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
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

	public boolean deleteUsers(int[] ids) {
		DbUtil daoUtil = null;
		PreparedStatement ps = null;
		Connection conn = null;
		String sql = "delete from tb_user where id=?";
		try {
			daoUtil = new DbUtil();
			conn = daoUtil.getCon();
			conn.setAutoCommit(false);
			ps = conn.prepareStatement(sql);
			for(int j=0;j<ids.length;j++) {
				ps.setInt(1, ids[j]);
				ps.addBatch();
			}
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

	public Map getAllUsers() {
		Map userMap = new HashMap();
		DbUtil dao = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = "select * from tb_user";
		try {
			dao = new DbUtil();
			ps = dao.getCon().prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next()) {
				User user = new User();
				user.setId(rs.getInt("id"));
				user.setName(rs.getString("name"));
				user.setPassword(rs.getString("password"));
				user.setEmail(rs.getString("email"));
				user.setTrueName(rs.getString("trueName"));
				user.setSex(rs.getString("sex"));
				user.setBirthday(rs.getString("birthday"));
				user.setAddress(rs.getString("address"));
				user.setPostcode(rs.getString("postcode"));
				user.setPhone(rs.getString("phone"));
				user.setMphone(rs.getString("mphone"));
				user.setQuestion(rs.getString("question"));
				user.setAnswer(rs.getString("answer"));
				user.setImg(rs.getString("img"));
				user.setScore(rs.getInt("score"));
				userMap.put(new Integer(user.getId()), user);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				rs.close();
				ps.close();
				dao.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return userMap;
	}

	public UserPager getUserPager(int index, int pageSize) {
		Map userMap = new HashMap();
		DbUtil db = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			db = new DbUtil();
			String sql = "select * from tb_user limit ?,?";
			ps = db.getCon().prepareStatement(sql);
			ps.setInt(1, index);
			ps.setInt(2, pageSize);
			rs = ps.executeQuery();
			while(rs.next()) {
				User user = new User();
				user.setId(rs.getInt("id"));
				user.setName(rs.getString("name"));
				user.setPassword(rs.getString("password"));
				user.setEmail(rs.getString("email"));
				user.setTrueName(rs.getString("trueName"));
				user.setSex(rs.getString("sex"));
				user.setBirthday(rs.getString("birthday"));
				user.setAddress(rs.getString("address"));
				user.setPostcode(rs.getString("postcode"));
				user.setPhone(rs.getString("phone"));
				user.setMphone(rs.getString("mphone"));
				user.setQuestion(rs.getString("question"));
				user.setAnswer(rs.getString("answer"));
				user.setImg(rs.getString("img"));
				user.setScore(rs.getInt("score"));
				userMap.put(user.getId(), user);
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
		UserPager up = new UserPager();
		up.setUserMap(userMap);
		up.setPageSize(pageSize);
		up.setTotalNum(getAllUsers().size());
		return up;
	}
}
