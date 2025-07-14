package com.mall.daoimpl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.mall.common.DbUtil;
import com.mall.dao.SubTypeDao;
import com.mall.po.SubType;

public class SubTypeDaoImpl implements SubTypeDao{

	public List showAllSubTypeBySuperId(int superId) {
		List subList = new ArrayList();
		PreparedStatement pstmt = null;	
		ResultSet rs = null;
		DbUtil dbUtil = null;
		try {
			dbUtil = new DbUtil();
			String sql = "select * from tb_subType where superTypeId=?";
			pstmt = dbUtil.getCon().prepareStatement(sql);
			pstmt.setInt(1, superId);
			rs = pstmt.executeQuery();
			while(rs.next()){
				SubType st = new SubType();
				st.setSubTypeId(rs.getInt(1));
				st.setSuperTypeId(rs.getInt(2));
				st.setTypeName(rs.getString(3));
				subList.add(st);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			try {
				rs.close();
				pstmt.close();
				dbUtil.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return subList;
	}
	
	
}
