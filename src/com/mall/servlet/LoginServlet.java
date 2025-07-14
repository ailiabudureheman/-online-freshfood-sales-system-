package com.mall.servlet;

import java.io.IOException;
import java.util.List;
import java.io.PrintWriter;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mall.daoimpl.ErrPwdException;
import com.mall.daoimpl.NameNotFoundException;
import com.mall.model.Model;
import com.mall.po.User;

public class LoginServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//获取客户端提交过来的信息
		request.setCharacterEncoding("utf-8");
		String name = request.getParameter("name");
		String password=request.getParameter("password");
		Model model = new Model();
		try {
			User user = model.userLogin(name, password);
			user.setName(name);
			ServletContext context = this.getServletContext();
			List nameList = (List) context.getAttribute("nameList");
			if(!nameList.contains(name)) {
				request.getSession().setAttribute("name", name);
				request.getSession().setAttribute("user", user);
				request.setAttribute("loginMessage", "");
				response.sendRedirect("index.jsp");
			} else {
				request.getSession().setAttribute("name", name);
				request.getSession().setAttribute("user", user);
				request.setAttribute("loginMessage","该账号已经登录");
				request.getRequestDispatcher("index.jsp").forward(request, response);
			}

		}catch (NameNotFoundException e) {
			request.setAttribute("loginMessage", "账号错误");
			request.getRequestDispatcher("index.jsp").forward(request, response);
		}catch(ErrPwdException e){
			request.setAttribute("loginMessage", "密码错误");
			request.getRequestDispatcher("index.jsp").forward(request, response);
		}
		
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doGet(request, response);
	}

}
