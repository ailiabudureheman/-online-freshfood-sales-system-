package com.mall.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mall.model.Model;
import com.mall.po.Goods;
import com.mall.po.Order;
import com.mall.po.OrderItem;

public class AdminSendOrderServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
       //获得当前页号
		String pageOffset_str = request.getParameter("pageOffset");
		//获得页面大小
		String pageSize_str = request.getParameter("pageSize");
		//获得要处理的订单号
		String orderId_str = request.getParameter("orderId");
		int pageOffset = 0;
		int pageSize = 5;
		int orderId = 0;
		if(pageOffset_str != null && pageSize_str != null && orderId_str!=null) {
			pageOffset = Integer.parseInt(pageOffset_str);
			pageSize = Integer.parseInt(pageSize_str);
			orderId = Integer.parseInt(orderId_str);
		}
		Model model = new Model();
		//根据订单号获得订单信息
		Order order=model.getOneOrder(orderId);
		//获得订单号对应的订单项List
		List orderItemList = (List) order.getOrderItem();
		//逐条读取每条订单项
		for(int i=0;i<orderItemList.size();i++){
			OrderItem orderItem=(OrderItem) orderItemList.get(i);
			Goods Goods=model.showGoodsById(orderItem.getGoodsId());

			System.out.println("ordernum"+orderItem.getGoodsNum());
			System.out.println("goodsnum"+Goods.getGoodsNum());

			if(orderItem.getGoodsNum()< Goods.getGoodsNum()){
				int newNum = orderItem.getGoodsNum();
				model.updateGoodsNum(newNum, orderItem.getGoodsId());
				model.SendOrder(orderId);
			}else {
			   request.setAttribute("sendMessage", "库存不足");
			}
			request.getRequestDispatcher("getOrderNotSendPagerServlet?pager.offset="+pageOffset+"&pageSize="+pageSize).forward(request, response);
			return;
		}
				
	}
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doGet(request, response);
	}
}
