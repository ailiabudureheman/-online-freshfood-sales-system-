package com.mall.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mall.model.Model;
import com.mall.po.Admin;

public class AdminUpdatePassword extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String password = request.getParameter("password");
		String rpassword = request.getParameter("rpassword");
		String name = request.getParameter("name");
		if(password != null && rpassword != null && name != null) {
			if(!password.equals(rpassword)) {
				request.setAttribute("message", "not the same password");
				request.getRequestDispatcher("Admin/pages/updatePassword.jsp").forward(request, response);
			} else {
				Admin admin = new Admin();
				admin.setLoginName(name);
				admin.setLoginPwd(password);
				Model model = new Model();
				if(model.updatePassword(admin)) {
					request.setAttribute("message", "修改成功！");
					request.getRequestDispatcher("Admin/pages/updatePassword.jsp").forward(request, response);
				}
			}
		} else {
			request.getRequestDispatcher("Admin/pages/updatePassword.jsp").forward(request, response);
		}
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doGet(request, response);
	}

}
