package com.mall.servlet;

import java.io.IOException;
import java.util.List;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mall.model.Model;

public class GetGoodsByTypeServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Model model = new Model();
		List hostList = model.showGoods(1,1);
		
		if(hostList.size() > 6) {
			//最多显示6个商品，热卖
			while(hostList.size()>6) {
				hostList.remove(hostList.size()-1);
			}
		}
		request.getSession().setAttribute("hostList",hostList);
		
		List newList = model.showGoods(2, 1);
		//最多显示6个商品，新品
		while(newList.size()>6) {
			newList.remove(newList.size()-1);
		}
		request.getSession().setAttribute("newList", newList);
		
		List specialList = model.showGoods(4, 1);
		//最多显示6个商品，推荐
		while(specialList.size()>6) {
			specialList.remove(specialList.size()-1);
		}
		request.getSession().setAttribute("specialList", specialList);
		
		//特价
		List saleList=model.showGoods(3, 1);
		//最多显示8个商品，特价
		while(saleList.size()>8) {
			saleList.remove(saleList.size()-1);
		}
		request.getSession().setAttribute("saleList", saleList);
		
		List informList = model.getAllInform();
		request.getSession().setAttribute("informList", informList);
		
		//商品分类
		List supertypes = model.showAllSuperType();
		//分类
		request.getSession().setAttribute("clsList", supertypes);
		
		request.getRequestDispatcher("index.jsp").forward(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doGet(request, response);
	}

}
