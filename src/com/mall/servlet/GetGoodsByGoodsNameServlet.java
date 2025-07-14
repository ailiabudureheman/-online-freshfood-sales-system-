package com.mall.servlet;

import java.io.IOException;
import java.util.List;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mall.model.Model;
import com.mall.po.GoodsPager;

public class GetGoodsByGoodsNameServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("gb2312");
		//查询多少条数据
		String pageSize_str = request.getParameter("pageSize");
		int offset = 0;
		int pagecurrentPageNo = 1;
		int pageSize = 5;
		if(pageSize_str != null) {
			pageSize = Integer.parseInt(pageSize_str);
		}
		String GoodsName_str = request.getParameter("GoodsName");
		if(GoodsName_str != null) {
			byte[] buf = GoodsName_str.getBytes("iso8859-1");
			String GoodsName = new String(buf);
			Model model = new Model();
			GoodsPager GoodsPager = model.searchGood(GoodsName);
			GoodsPager.setPageOffset(offset);
			GoodsPager.setPagecurrentPageNo(pagecurrentPageNo);
			GoodsPager.setPageSize(pageSize);
			request.setAttribute("GoodsPager", GoodsPager);
			request.setAttribute("GoodsList", GoodsPager.getGoodsMap().values());
			request.getRequestDispatcher("Admin/pages/manageGoods.jsp").forward(request, response);
		}
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doGet(request, response);
	}

}
