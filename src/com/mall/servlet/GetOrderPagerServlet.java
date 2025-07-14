package com.mall.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mall.model.Model;
import com.mall.po.OrderPager;

public class GetOrderPagerServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int adminType = (Integer)request.getSession().getAttribute("adminType");
		if(adminType==2 || adminType==4 ){
		String pagerOffset = request.getParameter("pager.offset");
		String pageSize_str = request.getParameter("pageSize");
		int offset = 0;
		int pageSize = 5;
		if(pagerOffset != null && pageSize_str != null) {
			offset = Integer.parseInt(pagerOffset);
			pageSize = Integer.parseInt(pageSize_str);
		}
		Model model = new Model();
		OrderPager op = model.getOrderPager(offset,pageSize);
		op.setPageOffset(offset);
		op.setPageSize(pageSize);
		request.getSession().setAttribute("orderPager", op);
		request.getSession().setAttribute("orderList", op.getOrderMap().values());
		request.getRequestDispatcher("Admin/pages/manageOrder.jsp").forward(request, response);
		}else{
		  request.getRequestDispatcher("Admin/pages/adminLoginError.jsp").forward(request, response);
		}
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doGet(request, response);
	}

}
