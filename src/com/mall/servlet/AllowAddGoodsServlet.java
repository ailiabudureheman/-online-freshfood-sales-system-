package com.mall.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class AllowAddGoodsServlet extends HttpServlet {
	public void doGet(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException {
	int adminType = (Integer)request.getSession().getAttribute("adminType");
    if(adminType==1 || adminType==4 ){
	request.getRequestDispatcher("Admin/pages/addGoods.jsp").forward(request, response);
    }else{
	request.getRequestDispatcher("Admin/pages/adminLoginError.jsp").forward(request, response);
   }
}

 public void doPost(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException {
 this.doGet(request, response);
 }
}
