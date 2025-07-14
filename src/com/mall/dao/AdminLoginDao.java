package com.mall.dao;

import com.mall.po.Admin;

public interface AdminLoginDao {

	public int login(Admin admin);
	public boolean updatePassword(Admin admin);
}
