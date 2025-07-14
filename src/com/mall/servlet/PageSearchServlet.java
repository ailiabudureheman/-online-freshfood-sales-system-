package com.mall.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mall.daoimpl.NoRelativeGoodsException;
import com.mall.model.Model;
import com.mall.po.Page;

public class PageSearchServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int pageSize = 6; //每页显示的数
		int pageNo = 0;//当前页码
		String str_pageroffset = request.getParameter("pager.offset");
		String keywords = null;
		if(str_pageroffset != null){
			pageNo = Integer.parseInt(str_pageroffset);
		}
		if(pageNo == 0 && str_pageroffset==null) {
			keywords = request.getParameter("keywords");	
			request.getSession().setAttribute("keywords", keywords);	
		}else {
			keywords = (String)request.getSession().getAttribute("keywords");
		}
		Model model = new Model();
		try{
		Page page = model.doPage(keywords,pageNo, pageSize);
		request.setAttribute("page", page);
		request.getRequestDispatcher("searchOut.jsp").forward(request, response);
		}catch(NoRelativeGoodsException e){
			request.setAttribute("NoRelativeGoodsException", e.getMessage());
			request.getRequestDispatcher("searchOut.jsp").forward(request, response);
		}
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doGet(request, response);
	}

}
