package com.mall.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mall.model.Model;
import com.mall.po.Page;

public class PageServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int pageSize = 6;
		int pageNo = 0;
		int type = 0;
		int index = 0;//问号位置
		String str_type = null;
		String str_pageNo = null;
		String str = request.getParameter("type");
		
		String str2 = request.getParameter("sid");//大分类ID
		//如果没有
		int superid = -1;
		if(str2!=null && !"".equals(str2)) {
			superid = Integer.parseInt(str2);
		}
		
		if((index=str.indexOf("?")) != -1){//包含问号
			str_type = str.substring(0,index);
			String strPageNo = str.substring(index);
		    str_pageNo = strPageNo.substring(strPageNo.indexOf("=")+1);
		}else {
			str_type = str;
		}
		if(str_type != null){
			type = Integer.parseInt(str_type);
		}
		if(str_pageNo != null){
			pageNo = Integer.parseInt(str_pageNo);
		}
		Model model = new Model();
		
		Page page = model.doPage(type,pageNo, pageSize,superid);
		//设置大分类ID，供翻页使用。
		if(superid != -1) 
		{
			request.setAttribute("sid", superid);
		}
		//END
		request.setAttribute("type", type);
		request.setAttribute("page", page);
		request.getRequestDispatcher("category.jsp").forward(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doGet(request, response);
	}

}
