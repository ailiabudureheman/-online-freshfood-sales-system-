package com.mall.listener;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import com.mall.po.Admin;


public class AdminLoginListener implements ServletContextListener,
								HttpSessionListener, HttpSessionAttributeListener {

	private List<Admin> adminList;

	private ServletContext context;
	public void contextDestroyed(ServletContextEvent arg0) {
		
	}

	public void contextInitialized(ServletContextEvent e) {
		adminList = new ArrayList<Admin>();
		context = e.getServletContext();
		context.setAttribute("adminList", adminList);
	}

	public void sessionCreated(HttpSessionEvent arg0) {
		
	}

	public void sessionDestroyed(HttpSessionEvent hse) {
		HttpSession session = hse.getSession();
		Admin admin = (Admin) session.getAttribute("admin");
		if(admin != null) {
			adminList.remove(admin);
			context.setAttribute("adminList", adminList);
		}
		
	}

	public void attributeAdded(HttpSessionBindingEvent hsbe) {
		HttpSession session = hsbe.getSession();
		Admin admin = (Admin) session.getAttribute("admin");
		if(adminList.size() == 0) {
			adminList.add(admin);
			context.setAttribute("adminList", adminList);
		}
	}

	public void attributeRemoved(HttpSessionBindingEvent arg0) {
		
		
	}

	public void attributeReplaced(HttpSessionBindingEvent arg0) {
		
		
	}

}
