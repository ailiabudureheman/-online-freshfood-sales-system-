package com.mall.servlet;

import java.io.IOException;
import java.util.List;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mall.model.ModelOrder;
import com.mall.po.Order;

public class SelectOrder extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String name = (String) request.getSession().getAttribute("name");
		ModelOrder model = new ModelOrder();
		List list = model.selectOr(name);
		request.getSession().setAttribute("list", list);
		response.sendRedirect("selfOrder.jsp");
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		this.doGet(request, response);
	}

}
