package com.mall.dao;

import java.util.Map;

import com.mall.po.AdminPager;

public interface AdminAdminDao {

	public Map getAllAdmins();   //显示管理员
	public boolean deleteAdminById(int id);   //删除管理员
	public boolean deleteAdmins(int[] ids);
	public AdminPager getAdminPager(int index, int pageSize);
}
