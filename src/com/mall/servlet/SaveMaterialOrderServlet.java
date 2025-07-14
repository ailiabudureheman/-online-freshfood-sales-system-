package com.mall.servlet;

import com.mall.model.Model;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class SaveMaterialOrderServlet extends HttpServlet {
    private Model model = new Model();

    // 同时处理GET和POST请求
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);

    }



    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");

        String alertIdStr = request.getParameter("alertId");
        String productIdStr = request.getParameter("productId");
        String supplierIdStr = request.getParameter("supplierId");
        String quantityStr = request.getParameter("quantity");
        String expectedArrivalDateStr = request.getParameter("expectedArrivalDate");

        if (alertIdStr == null || productIdStr == null || supplierIdStr == null || quantityStr == null) {
            response.sendRedirect("createMaterialOrderServlet?alertId=" + alertIdStr + "&error=params");
            return;
        }

        try {
            int alertId = Integer.parseInt(alertIdStr);
            int productId = Integer.parseInt(productIdStr);
            int supplierId = Integer.parseInt(supplierIdStr);
            int quantity = Integer.parseInt(quantityStr);

            // 获取产品名称
            String productName = model.getProductNameById(productId);

            // 检查库存超量且采购数量为0的情况
            if (quantity == 0) {
                // 更新预警状态为已处理，备注说明库存超量
                model.updateAlertStatus(alertId, 1, "库存超量，未创建采购订单");
                response.sendRedirect("getInventoryAlertServlet?message=overstockNoOrder");
                return;
            }

            // 生成订单号
            String orderNo = generateOrderNo();

            // 解析预计到货日期
            java.sql.Date expectedArrivalDate = null;
            if (expectedArrivalDateStr != null && !expectedArrivalDateStr.isEmpty()) {
                expectedArrivalDate = java.sql.Date.valueOf(expectedArrivalDateStr);
            } else {
                // 默认7天后到货
                expectedArrivalDate = new java.sql.Date(System.currentTimeMillis() + 7 * 24 * 60 * 60 * 1000);
            }

            // 创建采购订单
            boolean success = model.createMaterialOrder(
                    orderNo, productId, productName, quantity, supplierId, expectedArrivalDate, Integer.parseInt(alertIdStr));

            if (success) {
                // 更新预警状态为已处理
                model.updateAlertStatus(alertId, 1, "已创建采购订单，订单号: " + orderNo);
                response.sendRedirect("getInventoryAlertServlet?success=orderCreated");
            } else {
                response.sendRedirect("createMaterialOrderServlet?alertId=" + alertId + "&error=createFailed");
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("createMaterialOrderServlet?alertId=" + alertIdStr + "&error=exception");
        }
    }

    // 生成唯一订单号
    private String generateOrderNo() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        Random random = new Random();
        return "ORD-" + sdf.format(new Date()) + "-" + String.format("%04d", random.nextInt(10000));
    }
}