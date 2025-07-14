package com.mall.servlet;

import com.mall.model.Model;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class GetInventoryAlertDetailServlet extends HttpServlet {
    private Model model = new Model();

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");

        String alertIdStr = request.getParameter("alertId");
        if (alertIdStr == null || alertIdStr.isEmpty()) {
            response.sendRedirect("getInventoryAlertListServlet");
            return;
        }

        try {
            int alertId = Integer.parseInt(alertIdStr);
            // 获取预警详情
            Object alertDetail = model.getAlertDetail(alertId);

            if (alertDetail != null && !alertDetail.toString().contains("error")) {
                // 将详情存入请求属性
                request.setAttribute("alertDetail", alertDetail);
                // 转发到详情页面
                request.getRequestDispatcher("/Admin/pages/inventoryAlertDetail.jsp").forward(request, response);
            } else {
                response.sendRedirect("getInventoryAlertListServlet?error=notFound");
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("getInventoryAlertListServlet?error=exception");
        }
    }
}