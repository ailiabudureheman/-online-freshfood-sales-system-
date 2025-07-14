package com.mall.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mall.model.Model;
import com.mall.po.User;

public class CheckNameExist extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//处理乱码
		response.setCharacterEncoding("gb2312");
		response.setContentType("text/html;charset=gb2312");
		response.setContentType("text/xml;charset=gb2312");
		PrintWriter out = response.getWriter();
		String name = request.getParameter("name");
		request.getSession().setAttribute("name",name);
		Model model = new Model();
		if(model.checkNameExist(name)){//用户名已存在
			out.print("<Users>");
			out.print("<user>");
			out.print("<name>"+"exist"+"</name>");
			out.print("</user>");
			out.print("</Users>");
		}else{//可以注册
			out.print("<Users>");
			out.print("<user>");
			out.print("<name>"+"notExist"+"</name>");
			out.print("</user>");
			out.print("</Users>");
		}
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doGet(request, response);
	}

}
