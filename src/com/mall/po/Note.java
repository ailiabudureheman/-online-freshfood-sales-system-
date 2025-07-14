package com.mall.po;

public class Note {
	private int id;

	private String title;

	private String author;

	private String content;

	private String imgs;
	
	private String ly_time;

	public String getLy_time() {
		return ly_time;
	}

	public void setLy_time(String ly_time) {
		this.ly_time = ly_time;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getImgs() {
		return imgs;
	}

	public void setImgs(String imgs) {
		this.imgs = imgs;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
}
