package com.mall.po;

public class Admin {
	
	private Integer id;

	private Integer adminType;

	private String adminName;

	private String loginName;

	private String loginPwd;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getAdminType() {
		return adminType;
	}

	public void setAdminType(Integer adminType) {
		this.adminType = adminType;
	}

	public String getAdminName() {
		return adminName;
	}

	public void setAdminName(String adminName) {
		this.adminName = adminName;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getLoginPwd() {
		return loginPwd;
	}

	public void setLoginPwd(String loginPwd) {
		this.loginPwd = loginPwd;
	}
	
	
}
