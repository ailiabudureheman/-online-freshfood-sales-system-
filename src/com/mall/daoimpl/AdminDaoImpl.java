package com.mall.daoimpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.mall.common.DbUtil;
import com.mall.dao.AdminDao;
import com.mall.po.Admin;
import com.mall.po.Page;

public class AdminDaoImpl implements AdminDao{

	//添加管理员
	public boolean addAdmin(Admin admin) {
		boolean flag = false;
		PreparedStatement pstmt = null;
		DbUtil dbUtil = null;
		String sql = "insert into tb_admin(ID,AdminType,AdminName,LoginName,LoginPwd) values(null,?,?,?,?)";
		try {
			dbUtil = new DbUtil();
			pstmt = dbUtil.getCon().prepareStatement(sql);
			pstmt.setInt(1, admin.getAdminType());
			pstmt.setString(2, admin.getAdminName());
			pstmt.setString(3, admin.getLoginName());
			pstmt.setString(4, admin.getLoginPwd());
		
			int i = pstmt.executeUpdate();
			if(i != 0) {
				// 添加成功
				flag = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				pstmt.close();
				dbUtil.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}

		}
		
		return flag;
	}

	//显示管理员列表信息
	public List listAdmin() {
		List adminList = new ArrayList();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		DbUtil du = null;
		String sql = "select * from tb_admin";
		try {
			DbUtil dbUtil = new DbUtil();
			pstmt = dbUtil.getCon().prepareStatement(sql);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				Admin admin = new Admin();
				admin.setAdminName(rs.getString("AdminName"));
				admin.setAdminType(rs.getInt("AdminType"));
				admin.setLoginName(rs.getString("LoginName"));
				admin.setLoginPwd(rs.getString("LoginPwd"));
				admin.setId(rs.getInt("ID"));
				adminList.add(admin);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				rs.close();
				pstmt.close();
				du.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}

		}
		return adminList;
	}
	public Page doPage(int currentPage,int pageSize){
		Page page = new Page();
		int totalNum = listAdmin().size();
		List pageList = new ArrayList();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		DbUtil du = null;
		String sql = "select * from tb_admin limit "+currentPage+","+pageSize;
		try {
			DbUtil dbUtil = new DbUtil();
			pstmt = dbUtil.getCon().prepareStatement(sql);
			rs = pstmt.executeQuery();
			while(rs.next()){
				Admin admin = new Admin();
				admin.setAdminName(rs.getString("AdminName"));
				admin.setAdminType(rs.getInt("AdminType"));
				admin.setLoginName(rs.getString("LoginName"));
				admin.setLoginPwd(rs.getString("LoginPwd"));
				admin.setId(rs.getInt("ID"));
				pageList.add(admin);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				rs.close();
				pstmt.close();
				du.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}

		}
		page.setPageList(pageList);
		page.setTotalNum(totalNum);
		return page;
	}
	
	public boolean deleteAdmin(int[] ids){
		DbUtil daoUtil = null;
		PreparedStatement ps = null;
		Connection conn = null;
		 String sql = "delete from tb_admin where ID=?";;
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
	public boolean changePower(int id, String powerType) {
		return false;
	}

	public String getPower(String name) {
		return null;
	}

	public Admin getAdmin(String name) {
		return null;
	}
	public boolean checkNameExist(String name){
		boolean flag = false;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		DbUtil dbUtil = null;
		String sql ="select * from tb_admin where LoginName=?";	
		try {
			dbUtil = new DbUtil();
			pstmt = dbUtil.getCon().prepareStatement(sql);
			pstmt.setString(1, name);
			rs = pstmt.executeQuery();
			if(rs.next()){
				flag = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}	
		return flag;
	}
	
		public Boolean loggin(String name, String password) {
		Boolean fly = false;
		DbUtil dao = new DbUtil();
		PreparedStatement pre = null;
		ResultSet re = null;
		String sql = "select * from tb_admin where LoginName = ? ";
		try {
			pre = dao.getCon().prepareStatement(sql);
			pre.setString(1, name);
			re = pre.executeQuery();
			if (re.next()) {
				 if (!re.getString("password").equals(password)) {
					 fly = false;
					 }
				 else {
						fly = true;
					}
			} 
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				re.close();
				pre.close();
				dao.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return fly;
	}
	public Admin SelectOneAdmin(int id) {
		Admin admin = new Admin();
		DbUtil dao = new DbUtil();
		
		PreparedStatement pre = null;
		ResultSet re = null;
		String sql = "select * from tb_admin where ID = ? ";
		try {
			pre = dao.getCon().prepareStatement(sql);
			pre.setInt(1, id);
			re = pre.executeQuery();
			if (re.next()) {
                admin.setAdminName(re.getString("AdminName"));
                admin.setAdminType(re.getInt("AdminType"));
                admin.setLoginName(re.getString("LoginName"));
                admin.setLoginPwd(re.getString("LoginPwd"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				re.close();
				pre.close();
				dao.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return admin;
	}
	public Boolean updatePassword(String name, String password) {
		Boolean fly = false;

		DbUtil dao = new DbUtil();
		PreparedStatement pre = null;
		String sql = "update tb_admin set  LoginPwd = ?  where LoginName = ?";
		try {
			pre = dao.getCon().prepareStatement(sql);
			pre.setString(1, password);
			pre.setString(2, name);
			pre.executeUpdate();
			fly = true;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				pre.close();
				dao.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return fly;
	}

	public Boolean updateAdmin(Admin admin) {
		Boolean fly = false;

		DbUtil dao = new DbUtil();
		PreparedStatement pre = null;
		String sql = "update tb_admin set  LoginName = ? , LoginPwd = ? , AdminName = ? , AdminType = ?  where ID = ?";
		try {

			pre = dao.getCon().prepareStatement(sql);			
			pre.setString(1, admin.getLoginName());
			pre.setString(2, admin.getLoginPwd());
			pre.setString(3, admin.getAdminName());
			pre.setInt(4, admin.getAdminType());
			pre.setInt(5, admin.getId());		
			pre.addBatch();
			pre.executeBatch();
			fly = true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				pre.close();
				dao.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return fly;
	}
}