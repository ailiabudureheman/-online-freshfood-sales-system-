package com.mall.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.mall.model.Model;
import com.mall.po.Goods;
import com.mall.po.User;
import com.mall.vo.Cart;
import com.mall.vo.CartItem;

public class BuyGoodsServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int GoodsId = 0;
		String str_GoodsId = request.getParameter("GoodsId");
		if(str_GoodsId != null) {
			GoodsId = Integer.parseInt(str_GoodsId);
		}
		Model model = new Model();
		Goods Goods = model.showGoodsById(GoodsId);
		Cart cart = (Cart)request.getSession().getAttribute("cart");
		if(cart == null){
			cart = new Cart();
			request.getSession().setAttribute("cart", cart);
		}
		if(GoodsId!=0){
			cart.addItem(GoodsId, Goods);
		}
		HttpSession session = request.getSession();
		User user=(User)session.getAttribute("user");
		String name = (String)session .getAttribute("name");
		if(name!=null){
			Collection ci = cart.getItems();	
			request.getSession().setAttribute("ci", ci);
			request.getRequestDispatcher("cart.jsp").forward(request, response);  
		}else{//将返回到登录页面先登录
		    request.setAttribute("pleseLoggin","请先登录");
		   request.getRequestDispatcher("index.jsp").forward(request, response);	
		}

	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doGet(request, response);
	}

}
