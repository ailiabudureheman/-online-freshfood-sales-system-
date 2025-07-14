package com.mall.servlet;

import com.mall.common.DbUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/processSalesOrderServlet")
public class ProcessSalesOrderServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public ProcessSalesOrderServlet() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");

        String action = request.getParameter("action");
        String orderIdStr = request.getParameter("orderId");

        if ("ship".equals(action) && orderIdStr != null) {
            int orderId = Integer.parseInt(orderIdStr);
            processShipment(orderId, request, response);
        } else {
            // 处理其他操作如取消、完成等
            response.sendRedirect("getSalesOrderRequestServlet");
        }
    }

    private void processShipment(int orderId, HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Connection conn = null;
        PreparedStatement pstmtOrder = null;
        PreparedStatement pstmtOrderItem = null;
        PreparedStatement pstmtInventory = null;
        ResultSet rsOrderItem = null;
        ResultSet rsInventory = null;

        boolean success = false;
        String message = "发货处理失败，请重试";

        try {
            DbUtil dbUtil = new DbUtil();
            conn = dbUtil.getCon();
            conn.setAutoCommit(false); // 开启事务

            // 1. 查询订单商品项
            String orderItemSql = "SELECT * FROM tb_orderitem WHERE orderId = ?";
            pstmtOrderItem = conn.prepareStatement(orderItemSql);
            pstmtOrderItem.setInt(1, orderId);
            rsOrderItem = pstmtOrderItem.executeQuery();

            boolean enoughStock = true;
            List<Integer> inventoryIdsToUpdate = new ArrayList<>();

            // 2. 检查每个商品的库存
            while (rsOrderItem.next() && enoughStock) {
                int productId = rsOrderItem.getInt("bookId");
                int quantity = rsOrderItem.getInt("goodsNum");

                // 查询该商品入库日期最旧的库存记录
                String inventorySql = "SELECT * FROM tb_product_inventory " +
                        "WHERE productId = ? AND status = 1 " +
                        "ORDER BY stockInTime ASC " +
                        "LIMIT 1";
                pstmtInventory = conn.prepareStatement(inventorySql);
                pstmtInventory.setInt(1, productId);
                rsInventory = pstmtInventory.executeQuery();

                if (rsInventory.next()) {
                    int inventoryId = rsInventory.getInt("inventoryId");
                    int currentStock = rsInventory.getInt("quantity");

                    if (currentStock >= quantity) {
                        // 库存足够，扣减库存
                        int newStock = currentStock - quantity;
                        String updateSql = "UPDATE tb_product_inventory " +
                                "SET quantity = ?, status = ? " +
                                "WHERE inventoryId = ?";
                        pstmtInventory = conn.prepareStatement(updateSql);
                        pstmtInventory.setInt(1, newStock);
                        pstmtInventory.setInt(2, newStock > 0 ? 1 : 0); // 库存>0则为1，否则为0
                        pstmtInventory.setInt(3, inventoryId);
                        pstmtInventory.executeUpdate();

                        inventoryIdsToUpdate.add(inventoryId);
                    } else {
                        // 库存不足
                        enoughStock = false;
                        message = "库存不足，商品ID: " + productId + "，需要数量: " + quantity + "，现有库存: " + currentStock;
                    }
                } else {
                    // 没有库存
                    enoughStock = false;
                    message = "商品ID: " + productId + " 没有可用库存";
                }
            }

            if (enoughStock) {
                // 3. 更新订单状态为已发货
                String updateOrderSql = "UPDATE tb_order SET status = 'shipped' WHERE orderId = ?";
                pstmtOrder = conn.prepareStatement(updateOrderSql);
                pstmtOrder.setInt(1, orderId);
                pstmtOrder.executeUpdate();

                conn.commit();
                success = true;
                message = "发货成功";
            }
        } catch (SQLException e) {
            try {
                if (conn != null) conn.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
            message = "发货处理出错: " + e.getMessage();
        } finally {
            // 关闭资源
            try {
                conn.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

        // 重定向回订单列表并传递消息
        response.sendRedirect("getSalesOrderRequestServlet?message=" + (success ? "success:" : "error:") + message);
    }
}