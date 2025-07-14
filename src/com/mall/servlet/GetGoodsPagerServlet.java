package com.mall.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mall.model.Model;
import com.mall.po.GoodsPager;
import com.mall.po.InformPager;

public class GetGoodsPagerServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int adminType = (Integer)request.getSession().getAttribute("adminType");
		if(adminType==1 || adminType==4 ){
		String pageOffset_str = request.getParameter("pager.offset");
		String pageSize_str = request.getParameter("pageSize");
		int pageOffset = 0;
		int pageSize = 5;
		int pagecurrentPageNo = 0;
		if(pageOffset_str != null && pageSize_str != null) {
			pageOffset = Integer.parseInt(pageOffset_str);
			pageSize = Integer.parseInt(pageSize_str);
		}
		Model model = new Model();
		GoodsPager GoodsPager = model.getGoodsPager(pageOffset, pageSize);
		if(GoodsPager.getGoodsMap().size() == 0 && pageOffset != 0) {
			pageOffset -= pageSize;
			GoodsPager = model.getGoodsPager(pageOffset, pageSize);
		}
		if(pageOffset%pageSize == 0 || pageOffset/pageSize >0) {
			pagecurrentPageNo = pageOffset/pageSize + 1;
		}
		GoodsPager.setPageOffset(pageOffset);
		GoodsPager.setPageSize(pageSize);
		GoodsPager.setPagecurrentPageNo(pagecurrentPageNo);
		request.setAttribute("GoodsPager", GoodsPager);
		request.setAttribute("GoodsList", GoodsPager.getGoodsMap().values());
		request.getRequestDispatcher("Admin/pages/manageGoods.jsp").forward(request, response);
		}else{
			request.getRequestDispatcher("Admin/pages/adminLoginError.jsp").forward(request, response);
		}
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		this.doGet(request, response);
	}

}
