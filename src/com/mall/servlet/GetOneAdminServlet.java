package com.mall.servlet;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mall.po.Admin;
import com.mall.po.AdminPager;

public class GetOneAdminServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String id_str = request.getParameter("id");
		int id = 0;
		if(id_str != null) {
			id = Integer.parseInt(id_str);
		}
		AdminPager adminPager = (AdminPager) request.getSession().getAttribute("adminPager");
		Map adminMap = adminPager.getAdminMap();
		Admin admin = (Admin) adminMap.get(id);
		request.setAttribute("admin", admin);
		request.getRequestDispatcher("Admin/pages/manageDetailAdmin.jsp").forward(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doGet(request, response);
	}

}
