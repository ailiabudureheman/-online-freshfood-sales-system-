package com.mall.servlet;

import java.io.IOException;
import java.util.List;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mall.model.Model;

public class ShowAllGoodsByType extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		Integer type = (Integer)request.getAttribute("type");
//		if(str_type != null){
//			type = Integer.parseInt(str_type);
//		}
		System.out.println("typeinshow="+type);
		Model model = new Model();
		List GoodsList = model.showGoods(type, 1);
		request.setAttribute("type", type);
		request.setAttribute("GoodsList", GoodsList);
		request.getRequestDispatcher("category.jsp").forward(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doGet(request, response);
	}

}
