package com.mall.dao;

import java.util.List;

import com.mall.po.AdminType;

public interface AdminTypeDao {

	public boolean addAdminType(AdminType type);
	public List getAdminType();
	public boolean checkAdminTypeIsExist(String adminTypeName);
}
