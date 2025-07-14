package com.mall.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mall.model.Model;
import com.mall.po.Goods;

public class AddGoodsServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		Goods goods = new Goods();

		// 获取表单参数
		String superTypeIdStr = request.getParameter("superTypeId");
		String subTypeIdStr = request.getParameter("subTypeId");
		String goodsName = request.getParameter("GoodsName");
		String isbn = request.getParameter("isbn");
		String introduce = request.getParameter("introduce");
		String origin = request.getParameter("publisher");
		String brand = request.getParameter("author");
		String priceStr = request.getParameter("price");
		String nowPriceStr = request.getParameter("nowPrice");
		String imgUrl = request.getParameter("picturePath");





		goods.setPicture(imgUrl);


		// 设置商品类型
		if (superTypeIdStr != null && !superTypeIdStr.isEmpty()) {
			goods.setSuperTypeId(Integer.parseInt(superTypeIdStr));
		}
		if (subTypeIdStr != null && !subTypeIdStr.isEmpty()) {
			goods.setSubTypeId(Integer.parseInt(subTypeIdStr));
		}

		// 设置基本信息
		goods.setGoodsName(goodsName);
		goods.setISBN(isbn);
		goods.setIntroduce(introduce);
		goods.setPublisher(origin);
		goods.setAuthor(brand);

		// 设置价格
		if (priceStr != null && !priceStr.isEmpty()) {
			goods.setPrice(Float.parseFloat(priceStr));
		}
		if (nowPriceStr != null && !nowPriceStr.isEmpty()) {
			goods.setNowPrice(Float.parseFloat(nowPriceStr));
		}

		// 设置商品类型标识
		goods.setNewGoods(request.getParameter("newGoods") != null ? 1 : 0);
		goods.setSaleGoods(request.getParameter("saleGoods") != null ? 1 : 0);
		goods.setHostGoods(request.getParameter("hotGoods") != null ? 1 : 0);
		goods.setSpecialGoods(request.getParameter("specialGoods") != null ? 1 : 0);






		// 调用Model添加商品
		Model model = new Model();
		if (model.addGoods(goods)) {
			request.setAttribute("message", "添加成功");
		} else {
			request.setAttribute("message", "添加失败，请检查选择的商品分类是否存在");
		}

		request.getRequestDispatcher("Admin/pages/addGoods.jsp").forward(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doGet(request, response);
	}
}