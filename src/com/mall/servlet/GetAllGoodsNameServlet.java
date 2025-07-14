package com.mall.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mall.model.Model;

public class GetAllGoodsNameServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/xml;charset=utf-8");
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter out = response.getWriter();
		Model model = new Model();
		List GoodsNameList = model.getAllGoodsName();
		out.println("<Goods>");
		for(int i=0;i<GoodsNameList.size();i++) {
			String GoodsName = (String) GoodsNameList.get(i);
			out.println("<Goods>");
			out.println("<GoodsName>"+GoodsName+"</GoodsName>");
			out.println("</Goods>");
		}
		out.println("</Goods>");
		System.out.println(GoodsNameList.size());
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doGet(request, response);
	}

}
