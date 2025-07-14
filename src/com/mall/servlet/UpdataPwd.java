package com.mall.servlet;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mall.common.DbUtil;
import com.mall.po.User;

public class UpdataPwd extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	 	   User user= (User) request.getSession().getAttribute("user");
		   String name=(String)user.getName();
	       String password=request.getParameter("password");
	       PreparedStatement stat=null;
	       DbUtil db=new DbUtil();
	        String sql="update tb_user set password=? where name=?";
	        try {
				stat=db.getCon().prepareStatement(sql);
				stat.setString(1, password);
				stat.setString(2, name);
				stat.executeUpdate();
				response.sendRedirect("index.jsp");
			} catch (SQLException e) {
				
				e.printStackTrace();
			}
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		this.doGet(request, response);
	}
}
