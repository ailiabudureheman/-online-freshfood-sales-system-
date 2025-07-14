package com.mall.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.mall.dao.InventoryDao;
import com.mall.daoimpl.InventoryDaoImpl;

public class InStockServlet extends HttpServlet {

    private InventoryDao inventoryDao = new InventoryDaoImpl();

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");

        HttpSession session = request.getSession();
        Integer adminType = (Integer) session.getAttribute("adminType");
        if (adminType == null || adminType != 2) {
            response.sendRedirect("Admin/pages/adminLoginError.jsp");
            return;
        }

        String arrivalIdStr = request.getParameter("arrivalId");
        String warehouseIdStr = request.getParameter("warehouseId");

        if (arrivalIdStr == null || warehouseIdStr == null ||
                arrivalIdStr.isEmpty() || warehouseIdStr.isEmpty()) {
            response.getWriter().write("参数错误");
            return;
        }

        try {
            int arrivalId = Integer.parseInt(arrivalIdStr);
            int warehouseId = Integer.parseInt(warehouseIdStr);

            boolean success = inventoryDao.addInventory(arrivalId, warehouseId);

            if (success) {
                response.getWriter().write("1");  // 入库成功
            } else {
                response.getWriter().write("0");  // 入库失败
            }
        } catch (NumberFormatException e) {
            response.getWriter().write("参数格式错误");
        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().write("系统错误");
        }
    }
}