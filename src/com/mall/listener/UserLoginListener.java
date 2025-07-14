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


public class UserLoginListener implements ServletContextListener,
				HttpSessionListener, HttpSessionAttributeListener {
	private List<String> nameList;

	private ServletContext context;
	public void contextDestroyed(ServletContextEvent arg0) {
		
	}

	public void contextInitialized(ServletContextEvent e) {
		nameList = new ArrayList<String>();
		context = e.getServletContext();
		context.setAttribute("nameList", nameList);
	}

	public void sessionCreated(HttpSessionEvent arg0) {
		
	}

	public void sessionDestroyed(HttpSessionEvent hse) {
		HttpSession session = hse.getSession();
		String name = (String) session.getAttribute("name");
		if(name != null) {
			nameList.remove(name);
			context.setAttribute("nameList", nameList);
		}
		
	}

	public void attributeAdded(HttpSessionBindingEvent hsbe) {
		HttpSession session = hsbe.getSession();
		String name = (String) session.getAttribute("name");
		nameList.add(name);
		context.setAttribute("nameList", nameList);
	}

	public void attributeRemoved(HttpSessionBindingEvent arg0) {
		
		
	}

	public void attributeReplaced(HttpSessionBindingEvent arg0) {
		
		
	}
}
