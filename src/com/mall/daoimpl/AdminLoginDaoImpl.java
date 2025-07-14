package com.mall.daoimpl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.mall.common.DbUtil;
import com.mall.dao.AdminLoginDao;
import com.mall.exception.NameNotFound;
import com.mall.exception.PasswordError;
import com.mall.po.Admin;

public class AdminLoginDaoImpl implements AdminLoginDao {

	public int login(Admin admin) {
		DbUtil db = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			db = new DbUtil();
			String sql = "select * from tb_admin where LoginName=?";
			ps = db.getCon().prepareStatement(sql);
			ps.setString(1, admin.getLoginName());
			rs = ps.executeQuery();
			if(rs.next()) {
				if(rs.getString("LoginPwd").equals(admin.getLoginPwd())) {
					int adminType = rs.getInt("AdminType");
					return adminType;
				} else {
					throw new PasswordError("密码错误");
				}
			} else {
				throw new NameNotFound("账号错误");
			}
		} catch(NameNotFound nnf) {
			throw nnf;
		} catch (PasswordError pe) {
			throw pe;
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
		return 0;
	}

	public boolean updatePassword(Admin admin) {
		DbUtil daoUtil = null;
		PreparedStatement ps = null;
		String sql = "update tb_admin set LoginPwd=? where LoginName=?";
		try {
			daoUtil = new DbUtil();
			ps = daoUtil.getCon().prepareStatement(sql);
			ps.setString(1, admin.getLoginPwd());
			ps.setString(2, admin.getLoginName());
			int i = ps.executeUpdate();
			if(i != 0) {
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
}
