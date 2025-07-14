package com.mall.daoimpl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.mall.common.DbUtil;
import com.mall.dao.AdminTypeDao;
import com.mall.po.AdminType;

public class AdminTypeDaoImpl implements AdminTypeDao{
	public boolean addAdminType(AdminType type) {
		DbUtil dao = null;
		PreparedStatement ps = null;
		String sql = "";
		boolean flag = false;
		try {
			dao = new DbUtil();
			sql = "insert into tb_adminType values(null,?)";
			ps = dao.getCon().prepareStatement(sql);
			ps.setString(1, type.getAdminTypeName());
			int i = ps.executeUpdate();
			if(i != 0) {
				flag = true;
			}
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			try {
				ps.close();
				dao.close();
			} catch(SQLException s) {
				s.printStackTrace();
			}
		}
		return flag;
	}

	public List getAdminType() {
		List adminTypes = new ArrayList();
		DbUtil dao = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = "";
		try {
			dao = new DbUtil();
			sql = "select * from tb_adminType";
			ps = dao.getCon().prepareStatement(sql);
			rs = ps.executeQuery();
			while(rs.next()) {
				AdminType adminType = new AdminType();
				adminType.setAdminTypeId(rs.getInt("adminTypeId"));
				adminType.setAdminTypeName(rs.getString("adminTypeName"));
				adminTypes.add(adminType);
			}
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			try {
				rs.close();
				ps.close();
				dao.close();
			} catch(SQLException s) {
				s.printStackTrace();
			}
		}
		return adminTypes;
	}

	public boolean checkAdminTypeIsExist(String adminTypeName) {
		DbUtil daoUtil = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = null;
		try {
			daoUtil = new DbUtil();
			sql = "select * from tb_adminType where adminTypeName = ?";
			ps = daoUtil.getCon().prepareStatement(sql);
			ps.setString(1, adminTypeName);
			rs = ps.executeQuery();
			if (rs.next()) {
				return true;
			}
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			try {
				rs.close();
				ps.close();
				daoUtil.close();
			} catch(SQLException s) {
				s.printStackTrace();
			}
		}
		return false;
	}
}
