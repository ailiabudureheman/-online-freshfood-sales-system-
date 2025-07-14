package com.mall.dao;

import java.util.List;

public interface SubTypeDao {
	//根据大类的ID获取所有的小类
	public List showAllSubTypeBySuperId(int superId);
}
