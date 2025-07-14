package com.mall.servlet;

import java.io.IOException;
import java.util.List;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mall.model.ModelOrder;
import com.mall.po.OrderItem;

public class SonOrder extends HttpServlet {

	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
          int orderid = 0 ;
          float totalPrice=0;
          String id = request.getParameter("id");
          if(id!=null){
        	  orderid = Integer.parseInt(id);
          }
          ModelOrder model = new ModelOrder();
          List list =   model.selectOrderIt(orderid);
          for(int i=0;i<list.size();i++){
        	  OrderItem orderItem=(OrderItem) list.get(i);
        	  totalPrice=totalPrice+orderItem.getPrice();
        	  request.getSession().setAttribute("totalPrice",totalPrice);
          }       
	      request.getSession().setAttribute("list", list);
	      response.sendRedirect("sonOrder.jsp");
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
this.doGet(request, response);
	}

}
