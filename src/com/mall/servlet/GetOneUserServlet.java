package com.mall.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mall.po.User;
import com.mall.po.UserPager;

public class GetOneUserServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String id_str = request.getParameter("id");
		int id = 0;
		if(id_str != null) {
			id = Integer.parseInt(id_str);
		}
		UserPager userPager = (UserPager) request.getSession().getAttribute("userPager");
		Map userMap = userPager.getUserMap();
		User user = (User) userMap.get(id);
		request.setAttribute("user", user);
		request.getRequestDispatcher("Admin/pages/manageDetailUser.jsp").forward(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doGet(request, response);
	}

}
