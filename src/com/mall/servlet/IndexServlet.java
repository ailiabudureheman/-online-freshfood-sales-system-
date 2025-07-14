package com.mall.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mall.model.Model;
import com.mall.po.Goods;

public class IndexServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Model model = new Model();
		List hostList0 = model.showGoods(1, 1);
		List newList0 = model.showGoods(2, 1);
		List saleList0 = model.showGoods(3, 1);
		List specialList0=model.showGoods(4, 1);
//		商品分类
		List supertypes = model.showAllSuperType();
		
		Goods Goods11 = (Goods) hostList0.get(0);
		Goods Goods12 = (Goods) hostList0.get(1);	
		Goods Goods2 = (Goods) newList0.get(0);
		Goods Goods3 = (Goods) specialList0.get(0);
		
		List<Goods> hostList = new ArrayList<Goods>();
		hostList.add(Goods11);
		hostList.add(Goods12);
		List<Goods> newList = new ArrayList<Goods>();
		newList.add(Goods2);
		List<Goods> specialList = new ArrayList<Goods>();
		specialList.add(Goods3);
		List<Goods> saleList = new ArrayList<Goods>();
		for(int i=0;i<3;i++){
			Goods Goods = (Goods) saleList0.get(i);
			saleList.add(Goods);
		}
		
		List informList = model.getAllInform();
		request.getSession().setAttribute("informList", informList);
		request.getSession().setAttribute("hostList", hostList);
		request.getSession().setAttribute("newList", newList);
		request.getSession().setAttribute("saleList", saleList);
		request.getSession().setAttribute("specialList", specialList);
		//分类
		request.getSession().setAttribute("clsList", supertypes);
		response.sendRedirect("index.jsp");
		//request.getRequestDispatcher("index.jsp");
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doGet(request, response);
	}

}
