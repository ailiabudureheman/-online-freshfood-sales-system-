package com.mall.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mall.dao.AdminTypeDao;
import com.mall.model.Model;
import com.mall.po.AdminType;

public class GetAdminType extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/xml;charset=utf-8");
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter out = response.getWriter();
		Model model = new Model();
		List adminTypes = model.getAdminType();
		out.println("<adminTypes>");
		for(int i=0;i<adminTypes.size();i++) {
			AdminType adminType = (AdminType) adminTypes.get(i);
			request.setAttribute("adminTypes", adminTypes);
			out.println("<adminType>");
			out.println("<adminTypeId>"+ adminType.getAdminTypeId()+"</adminTypeId>");
			out.println("<adminTypeName>"+ adminType.getAdminTypeName()+"</adminTypeName>");
			out.println("</adminType>");
		}
		out.println("</adminTypes>");
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doGet(request, response);
	}

}
