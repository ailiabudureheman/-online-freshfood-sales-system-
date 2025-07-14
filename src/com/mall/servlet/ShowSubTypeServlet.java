package com.mall.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mall.model.Model;
import com.mall.po.SubType;

public class ShowSubTypeServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//缓存处理
		response.setDateHeader("Expires", 0);
		response.setHeader("Pragma", "no-cache");
		response.setHeader("Cache-Control", "no-cache");
		
		String str_superId = request.getParameter("superTypeId");
		int superId=0;
		if(str_superId != null){
			superId = Integer.parseInt(str_superId);
		}
		Model model = new Model();
		List subList = model.showAllSubTypeBySuperId(superId);
		response.setCharacterEncoding("gb2312");
		response.setContentType("text/html;charset=gb2312");
		response.setContentType("text/xml;charset=utf-8");
		PrintWriter out = response.getWriter();
		out.println("<type>");
		for(int i=0;i<subList.size();i++){
			SubType st = (SubType) subList.get(i);
			out.print("<subType>");
			out.print("<subTypeId>"+st.getSubTypeId()+"</subTypeId>");
			out.print("<subTypeName>"+st.getTypeName()+"</subTypeName>");
			out.print("</subType>");
		}
		out.println("</type>");
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doGet(request, response);
	}

}
