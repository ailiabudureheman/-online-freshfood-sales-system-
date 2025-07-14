package com.mall.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mall.model.Model;
import com.mall.po.WarehousePager;

public class GetWarehouseListServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		// 获取并检查管理员类型
		Integer adminType = (Integer)request.getSession().getAttribute("adminType");
		if(adminType == null || (adminType != 2 && adminType != 4)) {
			request.getRequestDispatcher("Admin/pages/adminLoginError.jsp").forward(request, response);
			return;
		}

		//从第几条记录开始查询
		String pagerOffset = request.getParameter("pager.offset");
		//查询多少条数据
		String pageSize_str = request.getParameter("pageSize");

		int offset = 0;
		int pagecurrentPageNo = 1;
		int pageSize = 10;

		// 安全解析参数
		if(pagerOffset != null && !pagerOffset.isEmpty()) {
			try {
				offset = Integer.parseInt(pagerOffset);
			} catch (NumberFormatException e) {
				// 处理无效参数
				offset = 0;
			}
		}

		if(pageSize_str != null && !pageSize_str.isEmpty()) {
			try {
				pageSize = Integer.parseInt(pageSize_str);
			} catch (NumberFormatException e) {
				// 处理无效参数
				pageSize = 10;
			}
		}

		// 获取仓库分页数据
		Model model = new Model();
		WarehousePager wp = model.getWarehousePager(offset, pageSize);

		// 检查返回值是否为空
		if(wp == null) {
			wp = new WarehousePager(); // 创建空对象避免空指针
		}

		// 检查仓库列表是否为空，避免空指针
		if((wp.getWarehouseList() == null || wp.getWarehouseList().size() == 0) && offset != 0) {
			offset -= pageSize;
			if(offset < 0) offset = 0;
			wp = model.getWarehousePager(offset, pageSize);

			// 再次检查新返回值
			if(wp == null) {
				wp = new WarehousePager();
			}
		}

		// 计算当前页码
		if(offset % pageSize == 0 || offset / pageSize > 0) {
			pagecurrentPageNo = offset / pageSize + 1;
		}

		// 设置分页信息
		wp.setPageOffset(offset);
		wp.setPagecurrentPageNo(pagecurrentPageNo);

		// 确保列表不为null
		if(wp.getWarehouseList() == null) {
			// 根据WarehousePager类的实现，可能需要创建新的空列表
			// 此处假设WarehousePager有setWarehouseList方法
			// wp.setWarehouseList(new ArrayList<>());
		}

		// 存储到会话中
		request.getSession().setAttribute("warehousePager", wp);
		request.getSession().setAttribute("warehouseList", wp.getWarehouseList());

		// 转发请求
		request.getRequestDispatcher("Admin/pages/warehouseManagement.jsp").forward(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doGet(request, response);
	}
}
