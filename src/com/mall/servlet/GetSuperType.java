package com.mall.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mall.model.Model;
import com.mall.po.SuperType;

public class GetSuperType extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/xml;charset=utf-8");
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter out = response.getWriter();
		Model model = new Model();
		List superTypes = model.getSuperType();
		out.println("<supers>");
		for(int i=0;i<superTypes.size();i++) {
			SuperType superType = (SuperType) superTypes.get(i);
			out.println("<super>");
			out.println("<superId>"+ superType.getSuperTypeId()+"</superId>");
			out.println("<superName>"+ superType.getTypeName()+"</superName>");
			out.println("</super>");
		}
		out.println("</supers>");
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doGet(request, response);
	}

}
