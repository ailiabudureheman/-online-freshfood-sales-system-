package com.mall.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.mall.common.DbUtil;
import com.mall.model.Model;

public class ResetInventorySettingsServlet extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // 设置字符编码
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");

        // 获取会话对象
        HttpSession session = request.getSession();

        // 检查会话中的adminType
        Integer adminType = (Integer) session.getAttribute("adminType");
        if (adminType == null || (adminType != 2 && adminType != 4)) {
            request.getRequestDispatcher("Admin/pages/adminLoginError.jsp").forward(request, response);
            return;
        }

        // 重置库存设置为默认值
        Model model = new Model();
        boolean success = model.resetInventorySettingsToDefault();

        // 设置结果消息
        if (success) {
            session.setAttribute("message", "库存设置已成功重置为默认值");
        } else {
            session.setAttribute("message", "重置库存设置失败，请重试");
        }

        // 重定向到库存设置页面
        response.sendRedirect("inventorySettingsServlet");
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        this.doGet(request, response);
    }



    /**
     * 重置库存设置为默认值
     * @return 重置是否成功
     */
    public boolean resetInventorySettingsToDefault() {
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            DbUtil dbUtil = new DbUtil();
            conn = dbUtil.getCon();

            // 检查是否存在库存设置记录
            boolean settingsExist = checkInventorySettingsExist(conn);

            if (settingsExist) {
                // 更新现有记录为默认值
                String sql = "UPDATE inventory_settings SET min_stock_alert = ?, max_stock_alert = ?, " +
                        "reorder_point = ?, auto_approve_threshold = ?";
                pstmt = conn.prepareStatement(sql);
                pstmt.setInt(1, 10);  // 默认最小库存预警值
                pstmt.setInt(2, 100); // 默认最大库存预警值
                pstmt.setInt(3, 20);  // 默认补货点
                pstmt.setDouble(4, 500.0); // 默认自动审批阈值
            } else {
                // 插入新的默认设置记录
                String sql = "INSERT INTO inventory_settings (min_stock_alert, max_stock_alert, " +
                        "reorder_point, auto_approve_threshold) VALUES (?, ?, ?, ?)";
                pstmt = conn.prepareStatement(sql);
                pstmt.setInt(1, 10);
                pstmt.setInt(2, 100);
                pstmt.setInt(3, 20);
                pstmt.setDouble(4, 500.0);
            }

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }



    private boolean checkInventorySettingsExist(Connection conn) throws SQLException {
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            String sql = "SELECT COUNT(*) FROM inventory_settings";
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                return rs.getInt(1) > 0;
            }

            return false;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }





}