package com.mall.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mall.dao.AdminDao;
import com.mall.daoimpl.AdminDaoImpl;
import com.mall.model.Model;


public class AllowAddAdminServlet extends HttpServlet {
	public void doGet(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException {		
	int adminType = (Integer)request.getSession().getAttribute("adminType");
    if(adminType==4){
    	Model model = new Model();
    	
    	request.setAttribute("adminTypes", model.getAdminType());
	request.getRequestDispatcher("Admin/pages/addAdmin.jsp").forward(request, response);
    }else{
	request.getRequestDispatcher("Admin/pages/adminLoginError.jsp").forward(request, response);
    }
}

 public void doPost(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException {
 this.doGet(request, response);
 }
}
