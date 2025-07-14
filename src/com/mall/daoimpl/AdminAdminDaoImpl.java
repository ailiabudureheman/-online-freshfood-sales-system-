package com.mall.daoimpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mall.common.DbUtil;
import com.mall.dao.AdminAdminDao;
import com.mall.po.Admin;
import com.mall.po.AdminPager;

public class AdminAdminDaoImpl implements AdminAdminDao{

	public boolean deleteAdminById(int id) {
		DbUtil daoUtil = null;
		PreparedStatement ps = null;
		String sql = "delete from tb_admin where ID=?";
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

	public boolean deleteAdmins(int[] ids) {
		DbUtil daoUtil = null;
		PreparedStatement ps = null;
		Connection conn = null;
		String sql = "delete from tb_admin where ID=?";
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

	public Map getAllAdmins() {
		Map adminMap = new HashMap();
		DbUtil dao = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = "select * from tb_admin";
		try {
			dao = new DbUtil();
			ps = dao.getCon().prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next()) {
				Admin admin = new Admin();
				admin.setId(rs.getInt("ID"));
				admin.setAdminType(rs.getInt("AdminType"));
				admin.setLoginName(rs.getString("LoginName"));
				admin.setLoginPwd(rs.getString("LoginPwd"));
				admin.setAdminName(rs.getString("AdminName"));
				adminMap.put(new Integer(admin.getId()), admin);
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
		return adminMap;
	}

	public AdminPager getAdminPager(int index, int pageSize) {
		Map adminMap = new HashMap();
		DbUtil db = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			db = new DbUtil();
			String sql = "select * from tb_admin limit ?,?";
			ps = db.getCon().prepareStatement(sql);
			ps.setInt(1, index);
			ps.setInt(2, pageSize);
			rs = ps.executeQuery();
			while(rs.next()) {
				Admin admin = new Admin();
				admin.setId(rs.getInt("ID"));
				admin.setAdminType(rs.getInt("AdminType"));
				admin.setLoginName(rs.getString("LoginName"));
				admin.setLoginPwd(rs.getString("LoginPwd"));
				admin.setAdminName(rs.getString("AdminName"));
				adminMap.put(admin.getId(), admin);
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
		AdminPager up = new AdminPager();
		up.setAdminMap(adminMap);
		up.setPageSize(pageSize);
		up.setTotalNum(getAllAdmins().size());
		return up;
		
	}
}