package com.mall.dao;

import java.util.List;

import com.mall.po.SuperType;

public interface AdminSuperTypeDao {

	public boolean addSuperType(SuperType type);
	public List getSuperType();
	public boolean checkSuperTypeIsExist(String superTypeName);
}
