package com.mall.daoimpl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.mall.common.DbUtil;
import com.mall.dao.AdminSuperTypeDao;
import com.mall.po.SuperType;

public class AdminSuperTypeDaoImpl implements AdminSuperTypeDao{
	public boolean addSuperType(SuperType type) {
		DbUtil dao = null;
		PreparedStatement ps = null;
		String sql = "";
		boolean flag = false;
		try {
			dao = new DbUtil();
			sql = "insert into tb_superType values(null,?)";
			ps = dao.getCon().prepareStatement(sql);
			ps.setString(1, type.getTypeName());
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

	public List getSuperType() {
		List superTypes = new ArrayList();
		DbUtil dao = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = "";
		try {
			dao = new DbUtil();
			sql = "select * from tb_supertype";
			ps = dao.getCon().prepareStatement(sql);
			rs = ps.executeQuery();
			while(rs.next()) {
				SuperType superType = new SuperType();
				superType.setSuperTypeId(rs.getInt("superTypeId"));
				superType.setTypeName(rs.getString("typeName"));
				superTypes.add(superType);
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
		return superTypes;
	}

	public boolean checkSuperTypeIsExist(String superTypeName) {
		DbUtil daoUtil = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = null;
		try {
			daoUtil = new DbUtil();
			sql = "select * from tb_superType where typeName = ?";
			ps = daoUtil.getCon().prepareStatement(sql);
			ps.setString(1, superTypeName);
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
