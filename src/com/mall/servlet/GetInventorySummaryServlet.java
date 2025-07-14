package com.mall.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.mall.model.Model;
import com.mall.po.InventoryAlertPager;
import com.mall.po.InventorySummaryPager;
import com.mall.po.StockInRecord;
import com.mall.po.StockOutRecord;

public class GetInventorySummaryServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 设置请求和响应编码
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");

		// 获取会话对象
		HttpSession session = request.getSession();

		// 安全获取管理员类型（添加null检查）
		Integer adminType = (Integer) session.getAttribute("adminType");
		if (adminType == null || (adminType != 2 && adminType != 4)) {
			request.getRequestDispatcher("Admin/pages/adminLoginError.jsp").forward(request, response);
			return;
		}

		// 从第几条记录开始查询 - 带空值和异常处理
		String pagerOffset = request.getParameter("pager.offset");
		int offset = 0;
		if (pagerOffset != null && !pagerOffset.isEmpty()) {
			try {
				offset = Integer.parseInt(pagerOffset);
				// 防止负偏移
				if (offset < 0) offset = 0;
			} catch (NumberFormatException e) {
				offset = 0; // 无效参数设为默认值
			}
		}

		// 查询多少条数据 - 带空值和异常处理
		String pageSizeStr = request.getParameter("pageSize");
		int pageSize = 10;
		if (pageSizeStr != null && !pageSizeStr.isEmpty()) {
			try {
				pageSize = Integer.parseInt(pageSizeStr);
				// 限制每页数据范围
				if (pageSize < 1 || pageSize > 100) {
					pageSize = 10;
				}
			} catch (NumberFormatException e) {
				pageSize = 10; // 无效参数设为默认值
			}
		}

		// 仓库ID - 空值处理
		String warehouseId = request.getParameter("warehouseId");
		if (warehouseId == null) {
			warehouseId = "";
		}

		// 类别ID - 空值处理
		String categoryId = request.getParameter("categoryId");
		if (categoryId == null) {
			categoryId = "";
		}

		// 产品名称 - 空值处理
		String productName = request.getParameter("productName");
		if (productName == null) {
			productName = "";
		}

		// 计算当前页码
		int pageCurrentPageNo = 1;
		if (offset > 0) {
			pageCurrentPageNo = offset / pageSize + 1;
		}





		// 调用模型层获取数据
		Model model = new Model();


		List<StockInRecord> recentStockIns = model.getRecentStockIns(5);
		List<StockOutRecord> recentStockOuts = model.getRecentStockOuts(5);


		InventorySummaryPager isp = model.getInventorySummaryPager(offset, pageSize, warehouseId, categoryId, productName);

//		System.out.println("=========================================================");
//		System.out.println("InventorySummaryPager 是否为 null: " + (isp == null));
//		if (isp != null) {
//			System.out.println("库存列表大小: " + isp.getInventoryList().size());
//			System.out.println("仓库列表大小: " + isp.getWarehouseList().size());
//		}
//		System.out.println("=========================================================");

		// 空指针保护 - 初始化分页对象
		if (isp == null) {
			isp = new InventorySummaryPager();
		}

		// 空指针保护 - 初始化库存列表
		if (isp.getInventoryList() == null) {
			isp.setInventoryList(new java.util.ArrayList<>());
		}

		// 处理空数据时的分页回退

		System.out.println("inventory");
		System.out.println("InventorySummaryPager <UNK> null: " + (isp == null));
		System.out.println(isp.getInventoryList());


		if (isp.getInventoryList().isEmpty() && offset > 0) {
			offset -= pageSize;
			if (offset < 0) offset = 0;

			isp = model.getInventorySummaryPager(offset, pageSize, warehouseId, categoryId, productName);




			if (isp == null) {
				isp = new InventorySummaryPager();
			}
			if (isp.getInventoryList() == null) {
				isp.setInventoryList(new java.util.ArrayList<>());
			}




			// 重新计算页码
			pageCurrentPageNo = offset / pageSize + 1;
		}

		// 获取预警信息
		InventoryAlertPager alertPager = model.getInventoryAlerts();

		// 空指针保护
		if (alertPager == null) {
			alertPager = new InventoryAlertPager();
		}
		if (alertPager.getAlertList() == null) {
			alertPager.setAlertList(new ArrayList<>());
		}



		// 存储到会话
		session.setAttribute("inventoryPager", isp);
		session.setAttribute("inventoryList", isp.getInventoryList());
		session.setAttribute("alertList", alertPager.getAlertList()); // 新增预警列表



		// 设置分页信息
		isp.setPageOffset(offset);
		isp.setPagecurrentPageNo(pageCurrentPageNo);
		isp.setPageSize(pageSize);

		// 存储到会话
		session.setAttribute("inventoryPager", isp);
		session.setAttribute("inventoryList", isp.getInventoryList());

		// 空指针保护 - 初始化仓库和类别列表
		if (isp.getWarehouseList() == null) {
			isp.setWarehouseList(new java.util.ArrayList<>());
		}
		if (isp.getCategoryList() == null) {
			isp.setCategoryList(new java.util.ArrayList<>());
		}

		session.setAttribute("warehouseList", isp.getWarehouseList());
		session.setAttribute("categoryList", isp.getCategoryList());


		session.setAttribute("recentStockIns", recentStockIns);
		session.setAttribute("recentStockOuts", recentStockOuts);


		// 转发到JSP页面
		request.getRequestDispatcher("Admin/pages/inventoryOverview.jsp").forward(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doGet(request, response);
	}
}