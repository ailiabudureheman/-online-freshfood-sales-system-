package com.mall.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mall.vo.Cart;

public class UpdateGoodsCount extends HttpServlet {


	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int GoodsId = 0;
		int count = 0;
		String str_GoodsId = request.getParameter("GoodsId");
		String str_count = request.getParameter("count");
		if(str_GoodsId != null&&str_count!=null){
			GoodsId = Integer.parseInt(str_GoodsId);
			count = Integer.parseInt(str_count);
		}
		Cart cart = (Cart)request.getSession().getAttribute("cart");
		cart.updateGoodsCount(GoodsId, count);
		response.sendRedirect("buyGoodsServlet");
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doGet(request, response);
	}

}
