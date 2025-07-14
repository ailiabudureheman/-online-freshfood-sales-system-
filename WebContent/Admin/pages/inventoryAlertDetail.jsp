<%--
  Created by IntelliJ IDEA.
  User: 艾力
  Date: 2025/7/3
  Time: 20:34
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="tools.jsp" %>
<%
  String path = request.getContextPath();
  String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
  <base href="<%=basePath%>">
  <title>预警详情</title>
  <meta http-equiv="pragma" content="no-cache">
  <meta http-equiv="cache-control" content="no-cache">
  <meta http-equiv="expires" content="0">
  <link rel="stylesheet" type="text/css" href="Admin/css/body.css">
  <link rel="stylesheet" type="text/css" href="Admin/css/td_fontSize.css">
  <link rel="stylesheet" type="text/css" href="Admin/css/tab.css">
  <style type="text/css">
    .detail-container { width: 90%; max-width: 1000px; margin: 30px auto; }
    .page-title { font-size: 24px; font-weight: bold; margin-bottom: 20px; color: #333; }
    .detail-card { background-color: #f9f9f9; border-radius: 5px; box-shadow: 0 2px 5px rgba(0,0,0,0.1); padding: 20px; margin-bottom: 20px; }
    .detail-row { display: flex; margin-bottom: 15px; padding-bottom: 15px; border-bottom: 1px solid #eee; }
    .detail-label { width: 120px; font-weight: bold; color: #555; }
    .detail-value { flex: 1; }
    .alert-level { display: inline-block; padding: 2px 8px; border-radius: 4px; font-weight: bold; }
    .alert-high { background-color: #ffcccc; color: #ff0000; }
    .alert-medium { background-color: #ffebcc; color: #ff6600; }
    .alert-low { background-color: #ffffcc; color: #ffcc00; }
    .action-btn { margin-top: 20px; }
    .back-btn { background-color: #4CAF50; color: white; border: none; padding: 8px 15px; border-radius: 4px; cursor: pointer; text-decoration: none; }
  </style>
</head>
<body>
<div class="detail-container">
  <h1 class="page-title">库存预警详情</h1>

  <c:if test="${not empty alertDetail}">
    <div class="detail-card">
      <div class="detail-row">
        <div class="detail-label">预警ID:</div>
        <div class="detail-value">${alertDetail.alertId}</div>
      </div>
      <div class="detail-row">
        <div class="detail-label">产品名称:</div>
        <div class="detail-value">${alertDetail.productName}</div>
      </div>
      <div class="detail-row">
        <div class="detail-label">仓库:</div>
        <div class="detail-value">${alertDetail.warehouseName}</div>
      </div>
      <div class="detail-row">
        <div class="detail-label">当前库存:</div>
        <div class="detail-value">${alertDetail.currentStock}</div>
      </div>
      <div class="detail-row">
        <div class="detail-label">安全库存:</div>
        <div class="detail-value">${alertDetail.safetyStock}</div>
      </div>
      <div class="detail-row">
        <div class="detail-label">预警类型:</div>
        <div class="detail-value">${alertDetail.alertType}</div>
      </div>
      <div class="detail-row">
        <div class="detail-label">预警级别:</div>
        <div class="detail-value">
          <c:choose>
            <c:when test="${alertDetail.alertLevel == 'high'}">
              <span class="alert-level alert-high">高优先级</span>
            </c:when>
            <c:when test="${alertDetail.alertLevel == 'medium'}">
              <span class="alert-level alert-medium">中优先级</span>
            </c:when>
            <c:otherwise>
              <span class="alert-level alert-low">低优先级</span>
            </c:otherwise>
          </c:choose>
        </div>
      </div>
      <div class="detail-row">
        <div class="detail-label">预警信息:</div>
        <div class="detail-value">${alertDetail.alertMessage}</div>
      </div>
      <div class="detail-row">
        <div class="detail-label">预警时间:</div>
        <div class="detail-value">${alertDetail.alertTime}</div>
      </div>
      <c:if test="${not empty alertDetail.processTime}">
        <div class="detail-row">
          <div class="detail-label">处理时间:</div>
          <div class="detail-value">${alertDetail.processTime}</div>
        </div>
        <div class="detail-row">
          <div class="detail-label">处理备注:</div>
          <div class="detail-value">${alertDetail.processNote}</div>
        </div>
      </c:if>
      <c:if test="${empty alertDetail.processTime}">
        <div class="detail-row">
          <div class="detail-label">处理状态:</div>
          <div class="detail-value"><span style="color: #ff6600;">未处理</span></div>
        </div>
      </c:if>
    </div>

    <div class="action-btn">
      <a href="getInventoryAlertServlet" class="back-btn">返回预警列表</a>
    </div>
  </c:if>

  <c:if test="${empty alertDetail}">
    <div class="detail-card" style="color: #ff5555;">
      <div class="detail-row">
        <div class="detail-value">未找到该预警的详情信息，请确认预警ID是否正确或预警是否已被处理。</div>
      </div>
      <div class="action-btn">
        <a href="getInventoryAlertServlet" class="back-btn">返回预警列表</a>
      </div>
    </div>
  </c:if>
</div>
</body>
</html>
