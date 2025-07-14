package com.mall.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mall.daoimpl.NoMessageException;
import com.mall.model.Model;
import com.mall.po.Page;

public class PageNoteServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int pageSize = 4; //每页显示留言条数
		int pageNo = 0;//当前页码
		String str_pageroffset = request.getParameter("pager.offset");
		if(str_pageroffset != null){
			pageNo = Integer.parseInt(str_pageroffset);
		}
		Model model = new Model();
		try{
		Page page = model.doNotePage(pageNo, pageSize);
		request.setAttribute("page", page);
		request.getRequestDispatcher("showNoteServlet").forward(request, response);
		}catch(NoMessageException e){
			request.setAttribute("NoMessageException",e.getMessage());
			request.getRequestDispatcher("note.jsp").forward(request, response);
		}
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doGet(request, response);
	}

}
