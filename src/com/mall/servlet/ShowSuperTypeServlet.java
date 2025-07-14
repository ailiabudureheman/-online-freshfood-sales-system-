package com.mall.servlet;

import java.io.IOException;
import java.util.List;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mall.model.Model;


public class ShowSuperTypeServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//缓存处理
		response.setDateHeader("Expires", 0);
		response.setHeader("Pragma", "no-cache");
		response.setHeader("Cache-Control", "no-cache");
		Model model = new Model();
		List superList = model.showAllSuperType();
		request.setAttribute("superList", superList);
		request.getRequestDispatcher("detailSearch.jsp").forward(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doGet(request, response);
	}

}
