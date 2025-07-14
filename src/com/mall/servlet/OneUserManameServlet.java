package com.mall.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


import com.mall.model.Model;
import com.mall.po.User;

public class OneUserManameServlet extends HttpServlet {

	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setCharacterEncoding("gb2312");
		request.setCharacterEncoding("gb2312");
		HttpSession session = request.getSession();
		User user=(User)session.getAttribute("user");
		String name = (String)session .getAttribute("name");
		if(name!=null){
	      response.sendRedirect("onePage.jsp");	    
		}else{//将返回到登录页面先登录
		 request.setAttribute("pleseLoggin","请先登录");
				 request.getRequestDispatcher("index.jsp").forward(request, response);	
		}
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

	this.doGet(request, response);
	}

}
