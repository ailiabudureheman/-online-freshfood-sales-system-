package com.mall.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mall.model.Model;
import com.mall.po.SubType;

public class AddSubTypeServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=utf-8");
		String superTypeId_str = request.getParameter("superTypeId");
		String subTypeName = request.getParameter("subTypeName");
		
		if(superTypeId_str != null && subTypeName != null) {
			
			Model model = new Model();
			SubType subType = new SubType();
			subType.setSuperTypeId(Integer.parseInt(superTypeId_str));
			subType.setTypeName(subTypeName);
			if(model.addSubType(subType)) {
				request.setAttribute("message","添加成功");
			} else {
				request.setAttribute("message","添加失败");
			}
			request.getRequestDispatcher("Admin/pages/addSubType.jsp").forward(request, response);
		}
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doGet(request, response);
	}

}
