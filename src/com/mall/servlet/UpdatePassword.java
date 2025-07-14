package com.mall.servlet;

import java.io.IOException;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


import com.mall.model.Model;


public class UpdatePassword extends HttpServlet {

	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String name = request.getParameter("name");
		String password = request.getParameter("password");
		String newPassword1 = request.getParameter("newPassword1");
		String newPassword2= request.getParameter("newPassword2");
		System.out.println(password+"********"+newPassword1+"******");
		   HttpSession session =   request.getSession();
	       ServletContext context = session.getServletContext(); 
	       Model model = new Model();//和登录差不多，主要判断旧密码是否正确
	       Boolean fly = model.loggin(name, password);
		
		    	if(fly){//旧密码正确
		    		if(!newPassword1.equals(newPassword2)){
		    			request.setAttribute("passwordenrol", "两次密码输入不一致");
						request.getRequestDispatcher("updatePassword.jsp").forward(request, response);
		    		}else{
		    			Boolean bo = model.updatePassword(name, newPassword1);
		    			if(bo){
		    				request.setAttribute("passwordOK","密码修改成功");
		    						System.out.println(request.getAttribute("passwordOK"));
							request.getRequestDispatcher("updatePassword.jsp").forward(request, response);
		    			}
		    	   }
			    }else{
			    	request.setAttribute("passwordexception","旧密码出错了");
			    	request.getRequestDispatcher("updatePassword.jsp").forward(request, response);
			    }
	}

	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	        this.doGet(request, response);
	}

}
