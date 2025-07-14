package com.mall.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mall.model.Model;

/**
 * 添加仓库的 Servlet
 */

public class AddWarehouseServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * 处理 GET 请求，显示添加仓库表单
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 重定向到添加仓库的表单页面
        response.sendRedirect(request.getContextPath() + "/Admin/pages/addWarehouse.jsp");
    }

    /**
     * 处理 POST 请求，处理添加仓库的表单提交
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 设置请求字符编码
        request.setCharacterEncoding("UTF-8");

        // 获取表单参数
        String warehouseCode = request.getParameter("warehouseCode");
        String warehouseName = request.getParameter("warehouseName");
        String location = request.getParameter("location");
        String capacityStr = request.getParameter("capacity");
        String statusStr = request.getParameter("status");

        // 验证参数
        if (warehouseCode == null || warehouseCode.isEmpty() ||
                warehouseName == null || warehouseName.isEmpty()) {
            request.setAttribute("errorMessage", "仓库编码和名称不能为空");
            request.getRequestDispatcher("/addWarehouseServlet").forward(request, response);
            return;
        }

        int capacity = 0;
        int status = 1; // 默认启用状态

        try {
            if (capacityStr != null && !capacityStr.isEmpty()) {
                capacity = Integer.parseInt(capacityStr);
            }
            if (statusStr != null && !statusStr.isEmpty()) {
                status = Integer.parseInt(statusStr);
            }
        } catch (NumberFormatException e) {
            request.setAttribute("errorMessage", "容量必须是数字");
            request.getRequestDispatcher("/addWarehouseServlet").forward(request, response);
            return;
        }

        // 创建Model实例并调用添加仓库的方法
        Model model = new Model();
        boolean result = model.addWarehouse(warehouseCode, warehouseName, location, capacity, status);

        // 设置操作结果消息
        if (result) {
            request.getSession().setAttribute("successMessage", "仓库添加成功");
        } else {
            request.setAttribute("errorMessage", "仓库添加失败，请检查仓库编码是否已存在");
        }

        // 重定向到仓库管理页面
        response.sendRedirect(request.getContextPath() + "/getWarehouseListServlet");
    }
}