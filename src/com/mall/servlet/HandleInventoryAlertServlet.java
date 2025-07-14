package com.mall.servlet;

import com.mall.model.Model;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class HandleInventoryAlertServlet extends HttpServlet {
    private static final Logger logger = Logger.getLogger(HandleInventoryAlertServlet.class.getName());
    private Model model = new Model();

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setContentType("application/json;charset=utf-8");
        PrintWriter out = response.getWriter();

        // 记录请求开始
        logger.info("处理预警操作请求: action=" + request.getParameter("action") +
                ", alertId=" + request.getParameter("alertId"));

        String action = request.getParameter("action");
        String alertIdStr = request.getParameter("alertId");

        if (action == null || alertIdStr == null) {
            sendErrorResponse(out, "参数缺失: action和alertId是必需参数");
            return;
        }

        int alertId = -1;
        try {
            alertId = Integer.parseInt(alertIdStr);
        } catch (NumberFormatException e) {
            sendErrorResponse(out, "参数格式错误: alertId必须是数字");
            logger.log(Level.WARNING, "alertId格式错误: " + alertIdStr, e);
            return;
        }

        boolean success = false;
        String message = "操作失败";
        Map<String, Object> data = new HashMap<>();

        try {
            switch (action) {
                case "view":
                    // 获取预警详情数据
                    Map<String, Object> alertDetail = model.getAlertDetail(alertId);

                    System.out.println(alertDetail);

                    if (alertDetail != null && !alertDetail.containsKey("error")) {
                        success = true;
                        message = "获取详情成功";
                        data.put("detail", alertDetail);
                        data.put("redirectUrl", "getInventoryAlertDetailServlet?alertId=" + alertId);



                    } else {
                        message = alertDetail != null ?
                                (String) alertDetail.get("error") :
                                "未找到ID为 " + alertId + " 的预警记录";
                    }
                    break;

                case "order":
                    success = model.createOrderForAlert(alertId);
                    message = success ? "订单创建成功" : "订单创建失败";
                    break;

                case "ignore":
                    success = model.ignoreAlert(alertId);
                    message = success ? "预警已忽略" : "忽略失败";
                    break;

                default:
                    sendErrorResponse(out, "未知操作: " + action);
                    return;
            }
        } catch (Exception e) {
            // 记录详细异常信息
            logger.log(Level.SEVERE, "处理预警操作时发生异常: action=" + action + ", alertId=" + alertId, e);
            message = "操作异常: " + e.getMessage();
        }

        // 构建并发送响应
        sendJsonResponse(out, success, message, data);
    }

    // 发送成功响应
    // 发送成功响应
    private void sendJsonResponse(PrintWriter out, boolean success, String message, Map<String, Object> data) {
        Map<String, Object> response = new HashMap<>();
        response.put("status", success ? "success" : "error");
        response.put("message", message);
        response.put("data", data);

        try {
            // 使用更健壮的JSON生成方式
            StringBuilder sb = new StringBuilder();
            sb.append("{");

            // 添加status字段
            sb.append("\"status\":\"").append(success ? "success" : "error").append("\",");

            // 添加message字段
            sb.append("\"message\":\"").append(escapeJson(message)).append("\",");

            // 添加data字段
            sb.append("\"data\":{");

            // 处理data中的每个字段
            boolean firstDataEntry = true;
            for (Map.Entry<String, Object> dataEntry : data.entrySet()) {
                if (!firstDataEntry) sb.append(",");

                String key = dataEntry.getKey();
                Object value = dataEntry.getValue();

                sb.append("\"").append(escapeJson(key)).append("\":");

                if (value == null) {
                    sb.append("null");
                }
                // 处理嵌套的Map对象
                else if (value instanceof Map) {
                    sb.append("{");
                    Map<?, ?> nestedMap = (Map<?, ?>) value;
                    boolean firstNestedEntry = true;
                    for (Map.Entry<?, ?> nestedEntry : nestedMap.entrySet()) {
                        if (!firstNestedEntry) sb.append(",");

                        String nestedKey = nestedEntry.getKey().toString();
                        Object nestedValue = nestedEntry.getValue();

                        sb.append("\"").append(escapeJson(nestedKey)).append("\":");

                        if (nestedValue == null) {
                            sb.append("null");
                        } else if (nestedValue instanceof String) {
                            sb.append("\"").append(escapeJson(nestedValue.toString())).append("\"");
                        } else if (nestedValue instanceof Number) {
                            sb.append(nestedValue.toString());
                        } else if (nestedValue instanceof Boolean) {
                            sb.append(nestedValue.toString().toLowerCase());
                        } else {
                            // 其他类型使用toString，可能需要根据实际情况调整
                            sb.append("\"").append(escapeJson(nestedValue.toString())).append("\"");
                        }

                        firstNestedEntry = false;
                    }
                    sb.append("}");
                }
                else if (value instanceof String) {
                    sb.append("\"").append(escapeJson(value.toString())).append("\"");
                } else if (value instanceof Number) {
                    sb.append(value.toString());
                } else if (value instanceof Boolean) {
                    sb.append(value.toString().toLowerCase());
                } else {
                    // 其他类型使用toString，可能需要根据实际情况调整
                    sb.append("\"").append(escapeJson(value.toString())).append("\"");
                }

                firstDataEntry = false;
            }

            sb.append("}"); // 结束data对象
            sb.append("}"); // 结束整个响应对象

            out.write(sb.toString());
            out.flush();
        } catch (Exception e) {
            logger.log(Level.SEVERE, "生成JSON响应时出错", e);
            // 发生错误时发送简单的错误响应
            out.write("{\"status\":\"error\",\"message\":\"生成响应失败: " + e.getMessage() + "\"}");
            out.flush();
        }
    }

    // 发送错误响应
    private void sendErrorResponse(PrintWriter out, String errorMessage) {
        logger.warning("发送错误响应: " + errorMessage);
        Map<String, Object> response = new HashMap<>();
        response.put("status", "error");
        response.put("message", errorMessage);

        StringBuilder sb = new StringBuilder("{\"status\":\"error\",\"message\":\"");
        sb.append(escapeJson(errorMessage)).append("\"}");
        out.write(sb.toString());
        out.flush();
    }

    // 转义JSON中的特殊字符
    private String escapeJson(String str) {
        if (str == null) return "";
        return str
                .replace("\\", "\\\\")
                .replace("\"", "\\\"")
                .replace("\n", "\\n")
                .replace("\r", "\\r")
                .replace("\t", "\\t");
    }
}