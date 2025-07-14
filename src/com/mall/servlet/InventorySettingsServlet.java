package com.mall.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.mall.model.Model;
import com.mall.po.InventorySettings;
import com.mall.po.ProductPager;

public class InventorySettingsServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 设置字符编码
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");

		// 获取会话对象
		HttpSession session = request.getSession();

		// 安全获取adminType，使用包装类型避免自动拆箱NPE
		Integer adminType = (Integer) session.getAttribute("adminType");

		// 验证管理员类型，添加空值检查
		if (adminType == null || (adminType != 2 && adminType != 4)) {
			request.getRequestDispatcher("Admin/pages/adminLoginError.jsp").forward(request, response);
			return;
		}

		// 获取分页和查询参数
		String pagerOffset = request.getParameter("pager.offset");
		String pageSize_str = request.getParameter("pageSize");
		String categoryId = request.getParameter("categoryId");
		String productName = request.getParameter("productName");

		int offset = 0;
		int pagecurrentPageNo = 1;
		int pageSize = 10;

		// 安全解析分页参数
		if (pagerOffset != null && !pagerOffset.isEmpty() && pageSize_str != null && !pageSize_str.isEmpty()) {
			try {
				offset = Integer.parseInt(pagerOffset);
				pageSize = Integer.parseInt(pageSize_str);
			} catch (NumberFormatException e) {
				// 忽略无效参数，使用默认值
			}
		}

		Model model = new Model();
		InventorySettings settings = model.getInventorySettings();
		ProductPager pp = model.getProductPager(offset, pageSize, categoryId, productName);

		// 确保ProductPager不为null
		if (pp == null) {
			pp = new ProductPager();
		}

		// 确保productList已初始化
		if (pp.getProductList() == null) {
			pp.setProductList(new java.util.ArrayList<>());
		}

		// 安全处理分页逻辑
		if (pp.getProductList().size() == 0 && offset != 0) {
			offset -= pageSize;
			if (offset < 0) offset = 0;

			pp = model.getProductPager(offset, pageSize, categoryId, productName);
			if (pp == null) {
				pp = new ProductPager();
			}
			if (pp.getProductList() == null) {
				pp.setProductList(new java.util.ArrayList<>());
			}
		}

		// 计算当前页码
		if (offset % pageSize == 0 || offset / pageSize > 0) {
			pagecurrentPageNo = offset / pageSize + 1;
		}

		// 设置分页信息
		pp.setPageOffset(offset);
		pp.setPagecurrentPageNo(pagecurrentPageNo);
		pp.setPageSize(pageSize);

		// 确保其他列表已初始化
		if (pp.getCategoryList() == null) {
			pp.setCategoryList(new java.util.ArrayList<>());
		}
		if (pp.getSupplierList() == null) {
			pp.setSupplierList(new java.util.ArrayList<>());
		}

		// 确保settings不为null
		if (settings == null) {
			settings = new InventorySettings();
		}

		// 存储到会话
		session.setAttribute("inventorySettings", settings);
		session.setAttribute("productPager", pp);
		session.setAttribute("productList", pp.getProductList());
		session.setAttribute("categoryList", pp.getCategoryList());
		session.setAttribute("supplierList", pp.getSupplierList());

		// 转发到JSP页面
		request.getRequestDispatcher("Admin/pages/inventorySettings.jsp").forward(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doGet(request, response);
	}
}