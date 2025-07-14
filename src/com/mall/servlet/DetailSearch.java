package com.mall.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mall.daoimpl.NoRelativeGoodsException;
import com.mall.model.Model;
import com.mall.po.Page;

public class DetailSearch extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int pageSize = 6; //每页显示数量
		int currentPage = 0;//当前页码
		String str_pageroffset = request.getParameter("pager.offset");
		String superTypeId_str = null;
		String subTypeId_str = null;
		String searchMethod = null;
		String content = null;
		
		if(str_pageroffset != null){
			currentPage = Integer.parseInt(str_pageroffset);
		}
		if(currentPage == 0 && str_pageroffset==null) {
			superTypeId_str = request.getParameter("superType");
			subTypeId_str = request.getParameter("subT");
			searchMethod = request.getParameter("searchMethod");
			content = request.getParameter("content");
			request.getSession().setAttribute("superType", superTypeId_str);
			request.getSession().setAttribute("subT", subTypeId_str);
			request.getSession().setAttribute("searchMethod", searchMethod);
			request.getSession().setAttribute("content", content);			
		}else {
			superTypeId_str = (String)request.getSession().getAttribute("superType");
			subTypeId_str =(String)request.getSession().getAttribute("subT");
			searchMethod = (String)request.getSession().getAttribute("searchMethod");
			content = (String)request.getSession().getAttribute("content");
		}
		//用变量将参数保存
		int superTypeId = Integer.parseInt(superTypeId_str);
		int subTypeId = Integer.parseInt(subTypeId_str);
		Model model = new Model();
		String str = null;
		str =searchMethod+"="+content;
		if(content!=null){
			try{
				Page page = model.doPageByConditons(superTypeId, subTypeId,str,currentPage, pageSize);
				request.setAttribute("page", page);
				request.getRequestDispatcher("detailSearchOut.jsp").forward(request, response);
				}catch(NoRelativeGoodsException e){
					request.setAttribute("NoRelativeGoodsException", e.getMessage());
					request.getRequestDispatcher("detailSearchOut.jsp").forward(request, response);
				}
		}

	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doGet(request, response);
	}

}
