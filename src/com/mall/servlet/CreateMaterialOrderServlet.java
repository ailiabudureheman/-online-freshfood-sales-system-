package com.mall.servlet;

import com.mall.model.Model;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CreateMaterialOrderServlet extends HttpServlet {
    private static final Logger logger = Logger.getLogger(CreateMaterialOrderServlet.class.getName());
    private Model model = new Model();

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");

        String alertIdStr = request.getParameter("alertId");
        logger.info("接收到创建订单请求，alertId: " + alertIdStr);

        if (alertIdStr == null || alertIdStr.isEmpty()) {
            request.setAttribute("errorMsg", "缺少必要参数：预警ID");
            request.getRequestDispatcher("/Admin/pages/createMaterialOrder.jsp").forward(request, response);
            return;
        }

        int alertId;
        try {
            alertId = Integer.parseInt(alertIdStr);
        } catch (NumberFormatException e) {
            logger.warning("无效的alertId格式: " + alertIdStr);
            request.setAttribute("errorMsg", "无效的预警ID格式，请提供数字类型的ID");
            request.getRequestDispatcher("/Admin/pages/createMaterialOrder.jsp").forward(request, response);
            return;
        }

        try {
            // 获取预警详情
            Map<String, Object> alertDetail = model.getAlertDetail(alertId);




            if (alertDetail.containsKey("error")) {
                request.setAttribute("errorMsg", alertDetail.get("error").toString());
                request.getRequestDispatcher("/Admin/pages/createMaterialOrder.jsp").forward(request, response);
                return;
            }

            int productId = (int) alertDetail.get("productId");
            String productName = (String) alertDetail.get("productName");

            if (productName == null || productName.trim().isEmpty()) {
                request.setAttribute("errorMsg", "未找到产品名称，请检查产品信息");
                request.getRequestDispatcher("/Admin/pages/createMaterialOrder.jsp").forward(request, response);
                return;
            }

            int currentStock = (int) alertDetail.get("currentStock");
            int safetyStock = (int) alertDetail.get("safetyStock");
            boolean overstock = currentStock > safetyStock;

            // 获取供应商列表
            List<Map<String, Object>> supplierList = model.getSuppliersByProductId(productId);

            if (supplierList.isEmpty()) {
                request.setAttribute("supplierError", "未找到该产品的供应商信息，请先维护供应商关系");
            }

            int suggestedQuantity = overstock ? 0 : Math.max(1, safetyStock - currentStock);

            // 存入请求属性
            request.setAttribute("alertDetail", alertDetail);
            request.setAttribute("productId", productId);
            request.setAttribute("productName", productName);
            request.setAttribute("supplierList", supplierList);
            request.setAttribute("suggestedQuantity", suggestedQuantity);
            request.setAttribute("overstock", overstock);
            request.setAttribute("currentStock", currentStock);
            request.setAttribute("safetyStock", safetyStock);

            logger.info("成功加载创建订单页面数据，产品: " + productName);
            request.getRequestDispatcher("/Admin/pages/createMaterialOrder.jsp").forward(request, response);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "处理创建订单请求时发生异常", e);
            request.setAttribute("errorMsg", "处理请求时发生异常: " + e.getMessage());
            request.getRequestDispatcher("/Admin/pages/createMaterialOrder.jsp").forward(request, response);
        }
    }
}