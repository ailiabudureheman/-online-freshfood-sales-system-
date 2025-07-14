package com.mall.dao;

import java.util.List;


import com.mall.po.Admin;
import com.mall.po.Page;

public interface AdminDao {
	public boolean addAdmin(Admin admin);
	
	public boolean checkNameExist(String name);
	
	public List listAdmin();
	
	public Page doPage(int currentPage,int pageSize);
	
	public boolean deleteAdmin(int[] ids);
	
	public boolean changePower(int id,String powerType);
	
	public String getPower(String name);
	
	public Admin getAdmin(String name);
	
	public Admin SelectOneAdmin(int id);
	
	public Boolean updatePassword(String name, String password);
	
	public Boolean updateAdmin(Admin admin);
	
	public Boolean loggin(String name, String password);
	
	
}
