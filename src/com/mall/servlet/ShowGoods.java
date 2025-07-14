package com.mall.servlet;

import java.io.IOException;
import java.util.List;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mall.model.Model;

public class ShowGoods extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Model model = new Model();
		List hostList = model.showGoods(1, 1);
		List newList = model.showGoods(2, 1);
		List saleList = model.showGoods(3, 1);
		List specialList=model.showGoods(4, 1);
		request.setAttribute("hostList", hostList);
		request.setAttribute("newList", newList);
		request.setAttribute("saleList", saleList);
		request.setAttribute("specialList", specialList);
		request.getRequestDispatcher("category.jsp").forward(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doGet(request, response);
	}

}
