package com.mall.servlet;

import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mall.model.Model;
import com.mall.po.OrderPager;
import com.mall.po.SalesOrderPager;

public class GetSalesOrderRequestServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int adminType = (Integer) request.getSession().getAttribute("adminType");
		// 在doGet/doPost方法开头添加
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");

		if (adminType == 2 || adminType == 4) {
			String pagerOffset = request.getParameter("pager.offset");
			String pageSize_str = request.getParameter("pageSize");
			String status = request.getParameter("status");
			String orderNo = request.getParameter("orderNo");
			String customerName = request.getParameter("customerName");

			int offset = 0;
			int pagecurrentPageNo = 1;
			int pageSize = 10;

			if (pagerOffset != null && pageSize_str != null) {
				offset = Integer.parseInt(pagerOffset);
				pageSize = Integer.parseInt(pageSize_str);
			}

			Model model = new Model();
			// 调用 Model 中适配后的方法，获取 OrderPager
			OrderPager sop = model.getSalesOrderPager(offset, pageSize, status, orderNo, customerName);

			if (sop.getOrderMap().size() == 0 && offset != 0) {
				offset -= pageSize;
				sop = model.getSalesOrderPager(offset, pageSize, status, orderNo, customerName);
			}

			if (offset % pageSize == 0 || offset / pageSize > 0) {
				pagecurrentPageNo = offset / pageSize + 1;
			}

			sop.setPageOffset(offset);
			sop.setPagecurrentPageNo(pagecurrentPageNo);
			request.getSession().setAttribute("salesOrderPager", sop);
			request.getSession().setAttribute("salesOrderList", new ArrayList<>(sop.getOrderMap().values()));
			request.getRequestDispatcher("Admin/pages/salesOrderProcessing.jsp").forward(request, response);
		} else {
			request.getRequestDispatcher("Admin/pages/adminLoginError.jsp").forward(request, response);
		}
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doGet(request, response);
	}
}
