package com.mall.servlet;

import java.io.IOException;
import java.util.List;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mall.daoimpl.NoRelativeGoodsException;
import com.mall.model.Model;

public class SearchServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String keywords = (String) request.getAttribute("keywords");
		Model model = new Model();
		try{
			List searchList = model.searchGoods(keywords);
			request.setAttribute("searchList", searchList);
			request.getRequestDispatcher("searchOut.jsp").forward(request, response);
		}catch(NoRelativeGoodsException e){
			request.setAttribute("NoRelativeGoodsException", e.getMessage());
			request.getRequestDispatcher("searchOut.jsp").forward(request, response);
		}
		
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doGet(request, response);
	}

}
