package com.mall.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mall.model.Model;
import com.mall.po.Inform;

public class GetOneInformServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String id_str = request.getParameter("informId");
		if(id_str != "") {
			int id = Integer.parseInt(id_str);
			Model model = new Model();
			Inform inform = model.getOneInform(id);
			request.setAttribute("inform", inform);
			request.getRequestDispatcher("").forward(request, response);
		}
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		this.doGet(request, response);
	}

}
