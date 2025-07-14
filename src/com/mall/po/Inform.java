package com.mall.po;

public class Inform implements java.io.Serializable {

	private int informId;

	private String informTitle;

	private String informContent;

	private String informTime;

	public String getInformContent() {
		return informContent;
	}

	public void setInformContent(String informContent) {
		this.informContent = informContent;
	}

	public int getInformId() {
		return informId;
	}

	public void setInformId(int informId) {
		this.informId = informId;
	}

	public String getInformTime() {
		return informTime;
	}

	public void setInformTime(String informTime) {
		this.informTime = informTime;
	}

	public String getInformTitle() {
		return informTitle;
	}

	public void setInformTitle(String informTitle) {
		this.informTitle = informTitle;
	}
}
