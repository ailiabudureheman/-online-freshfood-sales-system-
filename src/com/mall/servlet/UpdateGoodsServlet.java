package com.mall.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


import com.mall.model.Model;
import com.mall.po.Admin;
import com.mall.po.Goods;

public class UpdateGoodsServlet extends HttpServlet {

	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("gb2312");
		response.setCharacterEncoding("gb2312");	
		HttpSession session = request.getSession();	  
		String str_GoodsId = request.getParameter("GoodsId");
		String str_superTypeId = request.getParameter("superTypeId");
		String str_subTypeId = request.getParameter("subTypeId");
		String GoodsName = request.getParameter("GoodsName");
		String ISBN = request.getParameter("ISBN"); 
		String introduce = request.getParameter("introduce"); 
		String pages_str = request.getParameter("pages"); 
		String publisher = request.getParameter("publisher");
		String author = request.getParameter("author");   
		String str_price = request.getParameter("price");
		String str_nowPrice = request.getParameter("nowPrice");
		String str_GoodsNum = request.getParameter("GoodsNum");        
		String str_newGoods = request.getParameter("newGoods");      
		String str_saleGoods = request.getParameter("saleGoods");
		String str_hostGoods = request.getParameter("hostGoods");
		String str_specialGoods = request.getParameter("specialGoods");
		byte[] GoodsName_buf = GoodsName.getBytes("iso8859-1");
		byte[] ISBN_buf = ISBN.getBytes("iso8859-1");
		byte[] introduce_buf = introduce.getBytes("iso8859-1");
		byte[] publisher_buf = publisher.getBytes("iso8859-1");
		byte[] author_buf = author.getBytes("iso8859-1");
		int superTypeId = Integer.parseInt(str_superTypeId);
		int subTypeId = Integer.parseInt(str_subTypeId);
		float price = Float.parseFloat(str_price);
		float nowPrice = Float.parseFloat(str_nowPrice);
		int GoodsNum = Integer.parseInt(str_GoodsNum);
		int newGoods = 0;
		int saleGoods = 0;
		int hostGoods = 0;
		int specialGoods = 0; 
		int GoodsId = 0;
		if(str_GoodsId!=null){
			GoodsId = Integer.parseInt(str_GoodsId);
		}
		if(str_newGoods != null)
			newGoods = Integer.parseInt(str_newGoods);
		if(str_saleGoods != null)
			saleGoods = Integer.parseInt(str_saleGoods);
		if(str_hostGoods != null)
			hostGoods = Integer.parseInt(str_hostGoods);
		if(str_specialGoods != null)
			specialGoods = Integer.parseInt(str_specialGoods);
		 
		Goods Goods = new Goods();
		Goods.setGoodsId(GoodsId);
		Goods.setSuperTypeId(superTypeId);
		Goods.setSubTypeId(subTypeId);
		Goods.setGoodsName(new String(GoodsName_buf));
		Goods.setISBN(new String(ISBN_buf));
		Goods.setIntroduce(new String(introduce_buf));
		Goods.setProduceDate(pages_str);
		Goods.setPublisher(new String(publisher_buf));
		Goods.setAuthor(new String(author_buf));
		Goods.setPrice(price);
		Goods.setNowPrice(nowPrice);
		Goods.setNewGoods(newGoods);
		Goods.setSaleGoods(saleGoods);
		Goods.setHostGoods(hostGoods);
		Goods.setSpecialGoods(specialGoods);
		Goods.setGoodsNum(GoodsNum);

	   
		Model model= new Model();
	    if(model.updateGoods(Goods)){//修改成功
	    	session.setAttribute("Goods", Goods);
	    	session.setAttribute("updateMessage","修改成功");
	    	response.sendRedirect("Admin/pages/updateGoods.jsp");    	
	    }else{//修改失败
	    	session.setAttribute("updateMessage","修改失败");
	    	request.getRequestDispatcher("Admin/pages/updateGoods.jsp").forward(request, response);
	    	
	    }
	}
	public void doPost(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException {
		this.doGet(request, response);
		}
	}
