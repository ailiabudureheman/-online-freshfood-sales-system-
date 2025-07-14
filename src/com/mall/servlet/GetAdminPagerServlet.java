package com.mall.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

import com.mall.model.Model;
import com.mall.po.AdminPager;

public class GetAdminPagerServlet extends HttpServlet{

	public void doGet(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
	    int adminType = (Integer)request.getSession().getAttribute("adminType");
	    if(adminType==4 ){
			//从第几条记录开始查询
			String pagerOffset = request.getParameter("pager.offset");
			//查询多少条数据
			String pageSize_str = request.getParameter("pageSize");
			int offset = 0;
			int pagecurrentPageNo = 1;
			int pageSize = 5;
			if(pagerOffset != null && pageSize_str != null) {
				offset = Integer.parseInt(pagerOffset);
				pageSize = Integer.parseInt(pageSize_str);
				}
			Model model = new Model();
			AdminPager up = model.getAdminPager(offset, pageSize);
			if(up.getAdminMap().size() == 0 && offset != 0) {
				offset -=pageSize;
				up = model.getAdminPager(offset, pageSize);
				}
			if(offset%pageSize == 0 || offset/pageSize >0) {
				pagecurrentPageNo = offset/pageSize + 1;
				}		
			up.setPageOffset(offset);
			up.setPagecurrentPageNo(pagecurrentPageNo);
			request.getSession().setAttribute("adminList", up.getAdminMap().values());
			request.getSession().setAttribute("adminPager", up);
			request.getRequestDispatcher("Admin/pages/manageAdmins.jsp").forward(request, response);
	    }else{
		request.getRequestDispatcher("Admin/pages/adminLoginError.jsp").forward(request, response);
	   }

		}
	public void doPost(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		this.doGet(request, response);
		}
	}
