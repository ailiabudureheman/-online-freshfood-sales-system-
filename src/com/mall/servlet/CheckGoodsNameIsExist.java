package com.mall.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mall.model.Model;

public class CheckGoodsNameIsExist extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/xml;charset=utf-8");
		response.setHeader("Cache-Control", "no-cache");
		request.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		
		String GoodsName = request.getParameter("GoodsName");
		byte[] buf = GoodsName.getBytes("iso8859-1");
		Model model = new Model();
		if(model.checkGoodsNameIsExist(new String(buf))) {
			out.println("<message>");
			out.println("<state>true</state>");
			out.println("<content>商品名重复</content>");
			out.println("</message>");
		} else {
			out.println("<message>");
			out.println("<state>false</state>");
			out.println("<content>名称可用</content>");
			out.println("</message>");
		}
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		this.doGet(request, response);
	}

}
