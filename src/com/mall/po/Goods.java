package com.mall.po;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

/**
 *javabean
 */
public class Goods implements Serializable {
	private int goodsId;

	private int superTypeId;

	private int subTypeId;

	private String GoodsName;

	private String introduce;

	private float price;

	private float nowPrice;

	private String picture;
	
	private String produceDate;
	
	private String publisher;
	
	private String author;
	
	private String ISBN;

	private String inTime;

	private int newGoods;

	private int saleGoods;

	private int hostGoods;
	
	private int specialGoods;

	private int GoodsNum;

	public int getGoodsId() {
		return goodsId;
	}

	public void setGoodsId(int GoodsId) {
		this.goodsId = GoodsId;
	}

	public String getGoodsName() {
		return GoodsName;
	}

	public void setGoodsName(String GoodsName) {
		this.GoodsName = GoodsName;
	}

	public int getGoodsNum() {
		return GoodsNum;
	}

	public void setGoodsNum(int GoodsNum) {
		this.GoodsNum = GoodsNum;
	}

	public int getHostGoods() {
		return hostGoods;
	}

	public void setHostGoods(int hostGoods) {
		this.hostGoods = hostGoods;
	}

	public String getInTime() {
		return inTime;
	}

	public void setInTime(String inTime) {
		this.inTime = inTime;
	}

	public String getIntroduce() {
		return introduce;
	}

	public void setIntroduce(String introduce) {
		this.introduce = introduce;
	}

	public int getNewGoods() {
		return newGoods;
	}

	public void setNewGoods(int newGoods) {
		this.newGoods = newGoods;
	}

	public float getNowPrice() {
		return nowPrice;
	}

	public void setNowPrice(float nowPrice) {
		this.nowPrice = nowPrice;
	}

	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public int getSaleGoods() {
		return saleGoods;
	}

	public void setSaleGoods(int saleGoods) {
		this.saleGoods = saleGoods;
	}

	public int getSubTypeId() {
		return subTypeId;
	}

	public void setSubTypeId(int subTypeId) {
		this.subTypeId = subTypeId;
	}

	public int getSuperTypeId() {
		return superTypeId;
	}

	public void setSuperTypeId(int superTypeId) {
		this.superTypeId = superTypeId;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getISBN() {
		return ISBN;
	}

	public void setISBN(String isbn) {
		ISBN = isbn;
	}

	public String getProduceDate() {
		return produceDate;
	}

	public void setProduceDate(String date) {
		this.produceDate = date;
	}

	public String getPublisher() {
		return publisher;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

	public int getSpecialGoods() {
		return specialGoods;
	}

	public void setSpecialGoods(int specialGoods) {
		this.specialGoods = specialGoods;
	}

	private float purchasePrice;
	public float getPurchasePrice() {
		return purchasePrice;
	}
	public void setPurchasePrice(float purchasePrice) {
		this.purchasePrice = purchasePrice;
	}

	private Timestamp createTime;
	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	private Timestamp updateTime;
	public Timestamp getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}

	private int stockQuantity;
	public int getStockQuantity() {
		return stockQuantity;
	}
	public void setStockQuantity(int stockQuantity) {
		this.stockQuantity = stockQuantity;
	}
}
