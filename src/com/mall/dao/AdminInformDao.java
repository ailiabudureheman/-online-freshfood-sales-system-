package com.mall.dao;

import java.util.List;

import com.mall.po.Inform;
import com.mall.po.InformPager;

public interface AdminInformDao {

	public boolean addInform(Inform inform);
	public List getAllInform();
	public boolean deleteInform(int[] ids);
	public Inform getOneInform(int id);
	public InformPager getInformPager(int index,int pageSize);
}
