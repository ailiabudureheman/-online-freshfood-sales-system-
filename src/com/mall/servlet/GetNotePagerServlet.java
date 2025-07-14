package com.mall.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mall.model.Model;
import com.mall.po.InformPager;
import com.mall.po.NotePager;

public class GetNotePagerServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
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
		NotePager notePager = model.getNotePager(pageOffset, pageSize);
		if(notePager.getNoteMap().size() == 0 && pageOffset != 0) {
			pageOffset -= pageSize;
			notePager = model.getNotePager(pageOffset, pageSize);
		}
		if(pageOffset%pageSize == 0 || pageOffset/pageSize >0) {
			pagecurrentPageNo = pageOffset/pageSize + 1;
		}
		notePager.setPageOffset(pageOffset);
		notePager.setPageSize(pageSize);
		notePager.setPagecurrentPageNo(pagecurrentPageNo);
		request.setAttribute("notePager", notePager);
		request.setAttribute("noteList", notePager.getNoteMap().values());
		request.getRequestDispatcher("Admin/pages/manageNotes.jsp").forward(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doGet(request, response);
	}

}
