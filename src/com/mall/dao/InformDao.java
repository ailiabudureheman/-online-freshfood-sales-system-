package com.mall.dao;

import java.util.List;

import com.mall.po.Inform;

public interface InformDao {
	//根据时间显示相应公告
	public List showInformByInTime();
	//根据公告的ID得到该公告信息
	public Inform getInformById(int infromId);
}
