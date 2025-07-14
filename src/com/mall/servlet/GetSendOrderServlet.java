package com.mall.servlet;

import java.io.IOException;
import java.util.List;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mall.model.Model;

public class GetSendOrderServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String flag_str = request.getParameter("flag");
		System.out.println(flag_str);
		if(flag_str != "") {
			int flag = Integer.parseInt(flag_str);
			Model model = new Model();
			List orderList = model.getNotSendOrder(flag);
			request.setAttribute("orderList", orderList);
			request.getRequestDispatcher("Admin/pages/manageOrder.jsp").forward(request, response);
		}
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		this.doGet(request, response);
	}

}
