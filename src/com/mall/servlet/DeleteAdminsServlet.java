package com.mall.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mall.model.Model;

public class DeleteAdminsServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("gb2312");
		String adminIds = request.getParameter("adminIds");
		String pageOffset_str = request.getParameter("pageOffset");
		String pageSize_str = request.getParameter("pageSize");
		int pageOffset = 0;
		int pageSize = 5;
		if(pageOffset_str != null && pageSize_str != null) {
			pageOffset = Integer.parseInt(pageOffset_str);
			pageSize = Integer.parseInt(pageSize_str);
		}
		if(adminIds != "") {
			String[] ids_str = adminIds.split(",");
			int[] ids = new int[ids_str.length];
			for(int i=0;i<ids_str.length;i++) {
				ids[i] = Integer.parseInt(ids_str[i]);
			}
			Model model = new Model();
			if(model.deleteAdmin(ids)) {
				request.getRequestDispatcher("getAdminPagerServlet?pager.offset="+pageOffset+"&pageSize="+pageSize).forward(request, response);
			}
		}
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doGet(request, response);
	}

}
