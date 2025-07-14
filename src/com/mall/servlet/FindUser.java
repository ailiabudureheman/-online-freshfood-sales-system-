package com.mall.servlet;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mall.common.DbUtil;
import com.mall.po.User;

public class FindUser extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	       
	       
	       String name=request.getParameter("name");
           PreparedStatement stat=null;
           DbUtil db=new DbUtil();
           ResultSet set=null;
           String sql="select * from tb_user where name=?";
           try {
				stat=db.getCon().prepareStatement(sql);
				stat.setString(1, name);
				set=stat.executeQuery();
				while(set.next())
				{
				  User user=new User();
				  user.setName(name);
				  user.setQuestion(set.getString("question"));
				  user.setAnswer(set.getString("answer"));
				  request.getSession().setAttribute("user", user);
				  request.getRequestDispatcher("findAnswer.jsp").forward(request, response);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
	}
}
