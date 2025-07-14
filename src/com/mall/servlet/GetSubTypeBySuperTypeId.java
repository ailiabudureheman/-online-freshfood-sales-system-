package com.mall.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mall.model.Model;
import com.mall.po.SubType;

public class GetSubTypeBySuperTypeId extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/xml;charset=utf-8");
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter out = response.getWriter();
		String superTypeId_str = request.getParameter("superTypeId");
		int superTypeId = 0;
		if(superTypeId_str != "") {
			superTypeId = Integer.parseInt(superTypeId_str);
		}
		Model model = new Model();
		List subTypes = model.getSubTypeBySuperTypeId(superTypeId);
		out.println("<subTypes>");
		for(int i = 0;i<subTypes.size();i++) {
			SubType subType = (SubType)subTypes.get(i);
			out.println("<subType>");
			out.println("<subTypeId>"+subType.getSubTypeId()+"</subTypeId>");
			out.println("<subTypeName>"+subType.getTypeName()+"</subTypeName>");
			out.println("</subType>");
		}
		out.println("</subTypes>");
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		this.doGet(request, response);
	}

}
