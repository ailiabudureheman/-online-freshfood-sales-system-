package com.mall.servlet;

import com.mall.common.DbUtil;
import com.mall.po.DailyArrival;
import com.mall.common.DbUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;

@WebServlet("/processStorageServlet")
public class ProcessStorageServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // 设置请求和响应的字符编码
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");

        // 获取当前登录用户（实际项目中应从Session获取）



        HttpSession session = request.getSession(false); // false表示如果不存在Session则返回null


        String operator = "系统管理员"; // 示例，实际应从Session获取
        if (session != null) {
            // 从Session中获取已存储的用户信息（登录时存入）
            String username = (String) session.getAttribute("username");
            if (username != null) {
                // 用户已登录，使用获取到的用户名
                operator = username;
            } else {
                ;
            }
        }

        // 获取到货ID参数
        String arrivalIdStr = request.getParameter("arrivalId");
        if (arrivalIdStr == null || arrivalIdStr.isEmpty()) {
            request.setAttribute("message", "错误：缺少到货ID参数！");
            request.getRequestDispatcher("/error.jsp").forward(request, response);
            return;
        }

        int arrivalId = Integer.parseInt(arrivalIdStr);
        Connection conn = null;
        PreparedStatement pstmtOrder = null;
        PreparedStatement pstmtItems = null;
        PreparedStatement pstmtInventory = null;
        ResultSet rs = null;

        try {
            DbUtil dbUtil = new DbUtil();
            conn = dbUtil.getCon();
            conn.setAutoCommit(false); // 开启事务

            // 1. 查询订单信息
            String orderSql = "SELECT * FROM tb_material_order WHERE orderId = ?";
            pstmtOrder = conn.prepareStatement(orderSql);
            pstmtOrder.setInt(1, arrivalId);
            rs = pstmtOrder.executeQuery();

            if (!rs.next()) {
                request.setAttribute("message", "错误：未找到对应的订单信息！");
                request.getRequestDispatcher("/error.jsp").forward(request, response);
                return;
            }

            // 获取订单基本信息
            int orderId = rs.getInt("orderId");
            int supplierId = rs.getInt("supplierId");
            String expectedArrivalDate = rs.getString("expectedArrivalDate");

            // 2. 查询订单中的商品项
            String itemsSql = "SELECT * FROM tb_material_order_item WHERE orderId = ?";
            pstmtItems = conn.prepareStatement(itemsSql);
            pstmtItems.setInt(1, orderId);
            rs = pstmtItems.executeQuery();

            // 3. 处理每个商品项，添加入库记录
            while (rs.next()) {
                int productId = rs.getInt("productId");
                int quantity = rs.getInt("quantity");

                int warehouseId = rs.getInt("warehouseId");

                // 添加入库记录到库存表
                String inventorySql = "INSERT INTO tb_product_inventory " +
                        "(productId, warehouseId, quantity, stockInTime, status) " +
                        "VALUES (?, ?, ?, ?, 1)";

                pstmtInventory = conn.prepareStatement(inventorySql);
                pstmtInventory.setInt(1, productId);
                pstmtInventory.setInt(3, quantity);
                pstmtInventory.setInt(4,1);
                pstmtInventory.setTimestamp(4, new Timestamp(new Date().getTime()));
                pstmtInventory.executeUpdate();
                pstmtInventory.close();
            }

            // 4. 更新订单状态为已入库
            String updateOrderSql = "UPDATE tb_material_order " +
                    "SET storage_status = 'COMPLETED', " +
                    "storage_time = ?, " +
                    "storage_operator = ? " +
                    "WHERE orderId = ?";

            pstmtInventory = conn.prepareStatement(updateOrderSql);
            pstmtInventory.setTimestamp(1, new Timestamp(new Date().getTime()));
            pstmtInventory.setString(2, operator);
            pstmtInventory.setInt(3, orderId);
            pstmtInventory.executeUpdate();

            // 提交事务
            conn.commit();

            // 返回成功消息
            response.sendRedirect(request.getContextPath() + "/getDailyArrivalServlet?message=入库成功");
        } catch (Exception e) {
            // 回滚事务
            try {
                if (conn != null) {
                    conn.rollback();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }

            e.printStackTrace();
            request.setAttribute("message", "入库失败：" + e.getMessage());
            request.getRequestDispatcher("/error.jsp").forward(request, response);
        } finally {
            // 关闭资源
            try {
                conn.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
}