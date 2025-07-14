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

import com.mall.model.Model;
import com.mall.po.Order;
import com.mall.po.OrderItem;
import com.mall.po.User;
import com.mall.vo.Cart;
import com.mall.vo.CartItem;

public class AddOrderServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		User user = (User)request.getSession().getAttribute("user");//得到session中的user
		Order order = new Order();
		//得到用户提交过来的详细的订单信息
		String recvName = request.getParameter("recvName");//收货人姓名
		String email = request.getParameter("email");
		String mphone = request.getParameter("mphone");
		String phone = request.getParameter("phone");
		String address = request.getParameter("address");
		String postcode = request.getParameter("postcode");
		user.setMphone(mphone);
		user.setPhone(phone);
		user.setAddress(address);
		user.setPostcode(postcode);
		user.setEmail(email);
		order.setUser(user);
		order.setRecvName(recvName);
		Collection ci = (Collection)request.getSession().getAttribute("ci");//得到session中的orderitem
		List<OrderItem> item = new ArrayList<OrderItem>();//存放订单项的List
		Iterator it = ci.iterator();
		while(it.hasNext()){
			CartItem cartItem = (CartItem) it.next();
			OrderItem order_Item = new OrderItem();
			order_Item.setGoodsId(cartItem.getGoods().getGoodsId());
			order_Item.setGoodsName(cartItem.getGoods().getGoodsName());
			order_Item.setGoodsNum(cartItem.getCount());
			order_Item.setPrice(cartItem.getItemPrice());
			item.add(order_Item);
		}
		order.setOrderItem(item);
		Model model = new Model();
		int orderId = model.addOrder(order);
		order.setOrderId(orderId);
		request.getSession().setAttribute("order", order);
		Cart cart = new Cart();
		Collection cartitem = cart.getItems();
		request.getSession().setAttribute("ci", cartitem);
		response.sendRedirect("orderSuccess.jsp");
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doGet(request, response);
	}

}
