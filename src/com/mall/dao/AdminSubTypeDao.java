package com.mall.dao;

import java.util.List;

import com.mall.po.SubType;

public interface AdminSubTypeDao {

	public List getSubTypeBySuperTypeId(int superTypeId);
	public boolean addSubType(SubType type);
	public boolean checkSubTypeIsExist(String subTypeName);
}
