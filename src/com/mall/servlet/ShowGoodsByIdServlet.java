package com.mall.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mall.model.Model;
import com.mall.po.Goods;

public class ShowGoodsByIdServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int GoodsId = 0;
		String str_GoodsId = request.getParameter("GoodsId");
		if(str_GoodsId != null){
			GoodsId = Integer.parseInt(str_GoodsId);
		}
		Model model = new Model();
		Goods Goods = model.showGoodsById(GoodsId);
		request.setAttribute("Goods", Goods);
		List records = model.showBuyRecordsById(GoodsId);
		request.setAttribute("records", records);
		
		request.getRequestDispatcher("details.jsp").forward(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doGet(request, response);
	}

}
