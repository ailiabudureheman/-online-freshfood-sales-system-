package com.mall.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mall.po.User;

public class FindAnswer extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		 
		 User user= (User) request.getSession().getAttribute("user");
		   String name=(String)user.getAnswer();
	       String answer=request.getParameter("answer");
	       if(name.equals(answer))
	       {
	    	   response.sendRedirect("updataPwd.jsp");
	       }
	       else
	       {
	    	 response.sendRedirect("answerFail.jsp"); 
	       }
	}

}
