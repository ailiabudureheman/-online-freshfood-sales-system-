package com.mall.servlet;

import java.io.IOException;
import java.util.List;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mall.daoimpl.NoMessageException;
import com.mall.model.Model;

public class ShowNoteServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Model model = new Model();
		try{
			List noteList = model.showNote();
			request.setAttribute("noteList", noteList);
			request.getRequestDispatcher("note.jsp").forward(request, response);
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
