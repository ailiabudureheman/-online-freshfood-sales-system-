package com.mall.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CheckRandomCode extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
         //处理乱码
		response.setCharacterEncoding("gb2312");
		response.setContentType("text/html;charset=gb2312");
		response.setContentType("text/xml;charset=utf-8");
		
		PrintWriter out = response.getWriter();
		String code = request.getParameter("code");
		String rcode = (String)request.getSession().getAttribute("randomCode");
		if(code.equals(rcode)){//验证码输入正确
			out.print("<Codes>");
			out.print("<code>");
			out.print("<message>"+"correct"+"</message>");
			out.print("</code>");
			out.print("</Codes>");
		}else{//验证码输入错误
			out.print("<Codes>");
			out.print("<code>");
			out.print("<message>"+"error"+"</message>");
			out.print("</code>");
			out.print("</Codes>");
		}
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doGet(request, response);
	}

}
