package com.mall.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mall.model.Model;
import com.mall.po.SuperType;

public class AddSuperTypeServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=utf-8");
		String superTypeName = request.getParameter("superTypeName");
		
		if(superTypeName != null) {
			Model model = new Model();
			SuperType superType = new SuperType();
			superType.setTypeName(superTypeName);
			if(model.addSuperType(superType)) {
				request.setAttribute("message","添加成功");
			} else {
				request.setAttribute("message","添加失败");
			}
			request.getRequestDispatcher("Admin/pages/addSuperType.jsp").forward(request, response);
		}
		
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doGet(request, response);
	}

}
