package com.mall.servlet;

import java.io.IOException;
import java.util.List;
import java.io.PrintWriter;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mall.dao.InventoryAlertDao;
import com.mall.daoimpl.InventoryAlertDaoImpl;
import com.mall.exception.NameNotFound;
import com.mall.exception.PasswordError;
import com.mall.model.Model;
import com.mall.po.Admin;

public class AdminLoginServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("gb2312");
		String name = request.getParameter("name");
		String password = request.getParameter("password");
		Admin admin = new Admin();
		admin.setLoginName(name);
		admin.setLoginPwd(password);
		Model model = new Model();

		// 在测试类中添加方法，手动触发预警更新

		InventoryAlertDao dao = new InventoryAlertDaoImpl();
		dao.updateInventoryAlerts();


		try {
			int type= (Integer)model.login(admin);
			if(type!=0) {				
				ServletContext context = this.getServletContext();
				request.getSession().setAttribute("adminType",type);
				List adminList = (List) context.getAttribute("adminList");
				if(!adminList.contains("admin")) {
					request.getSession().setAttribute("admin", admin);
					response.sendRedirect("Admin/pages/adminConter.jsp");
				} else {
					request.setAttribute("message","已登录");
					request.getRequestDispatcher("Admin/login/adminLogin.jsp").forward(request, response);
				}
			}
		} catch(NameNotFound nnf) {
			request.setAttribute("message", nnf.getMessage());
			request.getRequestDispatcher("Admin/login/adminLogin.jsp").forward(request, response);
		} catch(PasswordError pe) {
			request.setAttribute("message", pe.getMessage());
			request.getRequestDispatcher("Admin/login/adminLogin.jsp").forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}	

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		this.doGet(request, response);
	}

}
