package com.mall.daoimpl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.mall.common.DbUtil;
import com.mall.dao.AdminSubTypeDao;
import com.mall.po.SubType;

public class AdminSubTypeDaoImpl implements AdminSubTypeDao{

	public List getSubTypeBySuperTypeId(int superTypeId) {
		List subTypes = new ArrayList();
		DbUtil dao = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = ""	;
		try {
			dao = new DbUtil();
			sql = "select * from tb_subType where superTypeId = ?";
			ps = dao.getCon().prepareStatement(sql);
			ps.setInt(1, superTypeId);
			rs = ps.executeQuery();
			while(rs.next()) {
				SubType subType = new SubType();
				subType.setSubTypeId(rs.getInt("subTypeId"));
				subType.setSuperTypeId(superTypeId);
				subType.setTypeName(rs.getString("subTypeName"));
				subTypes.add(subType);
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
		return subTypes;
	}

	public boolean addSubType(SubType type) {
		DbUtil dao = null;
		PreparedStatement ps = null;
		String sql = "";
		try {
			dao = new DbUtil();
			sql = "insert into tb_subType values(null,?,?)";
			ps = dao.getCon().prepareStatement(sql);
			ps.setInt(1, type.getSuperTypeId());
			ps.setString(2, type.getTypeName());
			int i = ps.executeUpdate();
			if(i != 0) {
				return true;
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
		return false;
	}

	public boolean checkSubTypeIsExist(String subTypeName) {
		DbUtil daoUtil = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = null;
		try {
			daoUtil = new DbUtil();
			sql = "select * from tb_subType where subTypeName = ?";
			ps = daoUtil.getCon().prepareStatement(sql);
			ps.setString(1, subTypeName);
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
