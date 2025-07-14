package com.mall.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.mall.model.Model;
import com.mall.po.DailyArrivalPager;

public class GetDailyArrivalServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 设置字符编码
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");

		// 获取会话对象
		HttpSession session = request.getSession();

		// 检查adminType是否存在且为有效类型
		Integer adminType = (Integer) session.getAttribute("adminType");
		if (adminType == null || (adminType != 2 && adminType != 4)) {
			request.getRequestDispatcher("Admin/pages/adminLoginError.jsp").forward(request, response);
			return;
		}

		// 获取分页参数，添加空值检查
		String pagerOffset = request.getParameter("pager.offset");
		String pageSize_str = request.getParameter("pageSize");
		String date = request.getParameter("date");
		String supplier = request.getParameter("supplier");

		int offset = 0;
		int pageSize = 10;
		int currentPageNo = 1;


		String currentPage = request.getParameter("currentPage");

// 处理页码参数
		if (currentPage != null && !currentPage.isEmpty()) {
			try {
				currentPageNo = Integer.parseInt(currentPage);
				if (currentPageNo <= 0) currentPageNo = 1;
			} catch (NumberFormatException e) {
				currentPageNo = 1;
			}
		}

// 计算偏移量
		offset = (currentPageNo - 1) * pageSize;

		// 执行查询
		Model model = new Model();
		DailyArrivalPager dap = model.getDailyArrivalPager(offset, pageSize, date, supplier);




		// 检查查询结果是否为空
		if (dap == null) {
			// 创建一个空的结果对象，避免NPE
			dap = new DailyArrivalPager();
		}

		// 安全地处理分页逻辑
		if (dap.getArrivalList() != null && dap.getArrivalList().isEmpty() && offset != 0) {
			offset -= pageSize;
			if (offset < 0) offset = 0;

			// 重新查询
			dap = model.getDailyArrivalPager(offset, pageSize, date, supplier);

			// 再次检查结果
			if (dap == null) {
				dap = new DailyArrivalPager();
			}
		}

		// 安全地计算当前页码
		int pagecurrentPageNo;
		if (offset % pageSize == 0 || offset / pageSize > 0) {
			pagecurrentPageNo = offset / pageSize + 1;
		}
		else {
			pagecurrentPageNo = 1;
		}

		// 设置分页信息
		dap.setPageOffset(offset);
		dap.setPagecurrentPageNo(pagecurrentPageNo);
		dap.setPageSize(pageSize);

		// 存储结果到会话
		session.setAttribute("dailyArrivalPager", dap);
		session.setAttribute("dailyArrivalList", dap.getArrivalList());


		System.out.println(dap.getArrivalList());
		System.out.println(dap);



		// 转发到JSP页面
		request.getRequestDispatcher("Admin/pages/dailyArrival.jsp").forward(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doGet(request, response);
	}
}