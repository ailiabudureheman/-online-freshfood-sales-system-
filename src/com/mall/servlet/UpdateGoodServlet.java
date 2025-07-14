package com.mall.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

import com.mall.model.Model;
import com.mall.po.Goods;

public class UpdateGoodServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int GoodsId = 0;
		String str_GoodsId = request.getParameter("GoodsId");
		System.out.println(str_GoodsId);
		if(str_GoodsId != null){
			GoodsId = Integer.parseInt(str_GoodsId);
		}
		Model model = new Model();
		Goods Goods = model.showGoodsById(GoodsId);
		request.setAttribute("Goods", Goods);
		request.getRequestDispatcher("Admin/pages/updateGoods.jsp").forward(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doGet(request, response);
	}

}
