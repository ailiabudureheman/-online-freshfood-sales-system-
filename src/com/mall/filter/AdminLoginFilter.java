package com.mall.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mall.po.Admin;

public class AdminLoginFilter implements Filter {

	public void destroy() {

	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		request.setCharacterEncoding("gb2312");
		response.setCharacterEncoding("gb2312");
		HttpServletRequest httpreq = (HttpServletRequest)request;
		HttpServletResponse httpresp = (HttpServletResponse)response;
		Admin admin = (Admin) httpreq.getSession().getAttribute("admin");
		if(admin == null) {
			httpreq.getRequestDispatcher("adminLoginError.jsp").forward(httpreq, httpresp);
		} else {
			chain.doFilter(httpreq, httpresp);
		}
	}

	public void init(FilterConfig arg0) throws ServletException {

	}

}
