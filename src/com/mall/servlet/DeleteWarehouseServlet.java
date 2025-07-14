package com.mall.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mall.model.Model;

/**
 * 处理仓库删除请求的 Servlet
 */
public class DeleteWarehouseServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 设置请求和响应的字符编码
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");

        // 获取仓库ID参数
        String warehouseId = request.getParameter("warehouseId");

        // 检查参数是否有效
        if (warehouseId == null || warehouseId.isEmpty()) {
            request.setAttribute("message", "错误：仓库ID不能为空！");
            request.getRequestDispatcher("/Admin/pages/warehouseList.jsp").forward(request, response);
            return;
        }

        try {
            // 创建Model实例并调用删除方法
            Model model = new Model();
            boolean result = model.deleteWarehouse(Integer.parseInt(warehouseId));

            if (result) {
                request.setAttribute("message", "仓库删除成功！");
            } else {
                request.setAttribute("message", "仓库删除失败，请检查该仓库是否有关联数据！");
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("message", "仓库删除过程中发生错误：" + e.getMessage());
        }
        request.getSession().setAttribute("message", "仓库删除成功");

        // 重定向到warehouseManagement.jsp
        response.sendRedirect(request.getContextPath() + "/Admin/pages/warehouseManagement.jsp");
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 对于仓库删除操作，通常使用GET方法
        doGet(request, response);
    }
}