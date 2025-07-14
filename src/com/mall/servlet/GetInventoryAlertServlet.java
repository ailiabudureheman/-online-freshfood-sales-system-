package com.mall.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.mall.model.Model;
import com.mall.po.InventoryAlertPager;

public class GetInventoryAlertServlet extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // 设置字符编码
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");

        // 获取会话对象
        HttpSession session = request.getSession();

        // 检查会话中的adminType，添加空值检查
        Integer adminType = (Integer) session.getAttribute("adminType");

        // 验证管理员类型，避免adminType为空导致NPE
        if (adminType == null || (adminType != 2 && adminType != 4)) {
            request.getRequestDispatcher("Admin/pages/adminLoginError.jsp").forward(request, response);
            return;
        }

        // 从第几条记录开始查询
        String pagerOffset = request.getParameter("pager.offset");
        // 查询多少条数据
        String pageSize_str = request.getParameter("pageSize");
        // 预警类型
        String alertType = request.getParameter("alertType");
        // 仓库ID
        String warehouseId = request.getParameter("warehouseId");
        // 产品名称
        String productName = request.getParameter("productName");

        int offset = 0;
        int pagecurrentPageNo = 1;
        int pageSize = 10;

        // 安全地解析分页参数
        if (pagerOffset != null && !pagerOffset.isEmpty()) {
            try {
                offset = Integer.parseInt(pagerOffset);
            } catch (NumberFormatException e) {
                // 忽略无效的页码参数，使用默认值
            }
        }

        if (pageSize_str != null && !pageSize_str.isEmpty()) {
            try {
                pageSize = Integer.parseInt(pageSize_str);
            } catch (NumberFormatException e) {
                // 忽略无效的页面大小参数，使用默认值
            }
        }

        Model model = new Model();
        InventoryAlertPager iap = model.getInventoryAlertPager(offset, pageSize, alertType, warehouseId, productName);

        // 核心修复：强制初始化alertList（无论iap是否为null）
        if (iap == null) {
            iap = new InventoryAlertPager();
        }
        if (iap.getAlertList() == null) {
            iap.setAlertList(new java.util.ArrayList<>());
        }

        // 安全地处理分页逻辑
        if (iap.getAlertList().size() == 0 && offset != 0) {
            offset -= pageSize;
            if (offset < 0) offset = 0;

            // 重新查询
            iap = model.getInventoryAlertPager(offset, pageSize, alertType, warehouseId, productName);

            // 再次强制初始化alertList
            if (iap == null) {
                iap = new InventoryAlertPager();
            }
            if (iap.getAlertList() == null) {
                iap.setAlertList(new java.util.ArrayList<>());
            }
        }

        // 安全地计算当前页码
        if (offset % pageSize == 0 || offset / pageSize > 0) {
            pagecurrentPageNo = offset / pageSize + 1;
        }

        // 设置分页信息
        iap.setPageOffset(offset);
        iap.setPagecurrentPageNo(pagecurrentPageNo);

        // 确保warehouseList不为空
        if (iap.getWarehouseList() == null) {
            iap.setWarehouseList(new java.util.ArrayList<>());
        }

        // 存储结果到会话
        session.setAttribute("alertPager", iap);
        session.setAttribute("alertList", iap.getAlertList());
        session.setAttribute("warehouseList", iap.getWarehouseList());

        // 转发到JSP页面
        request.getRequestDispatcher("Admin/pages/inventoryAlert.jsp").forward(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        this.doGet(request, response);
    }
}