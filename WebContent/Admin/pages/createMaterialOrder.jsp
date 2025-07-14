<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%--
  Created by IntelliJ IDEA.
  User: 艾力
  Date: 2025/7/3
  Time: 21:03
--%>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="tools.jsp" %>
<%
  String path = request.getContextPath();
  String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
  <base href="<%=basePath%>">
  <title>创建采购订单</title>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">

  <!-- 引入外部资源 -->
  <link rel="stylesheet" type="text/css" href="Admin/css/body.css">
  <link rel="stylesheet" type="text/css" href="Admin/css/td_fontSize.css">
  <link rel="stylesheet" type="text/css" href="Admin/css/tab.css">

  <!-- 自定义样式 -->
  <style type="text/css">
    .form-container {
      max-width: 800px;
      margin: 0 auto;
      padding: 20px;
      background-color: #ffffff;
      border-radius: 8px;
      box-shadow: 0 4px 6px -1px rgba(0, 0, 0, 0.1);
    }
    .form-group {
      margin-bottom: 20px;
    }
    .form-label {
      display: block;
      margin-bottom: 8px;
      font-weight: 600;
    }
    .form-input {
      width: 100%;
      padding: 8px 12px;
      border: 1px solid #e2e8f0;
      border-radius: 4px;
      font-size: 14px;
    }
    .form-input-readonly {
      width: 100%;
      padding: 8px 12px;
      border: 1px solid #e2e8f0;
      border-radius: 4px;
      font-size: 14px;
      background-color: #f7fafc;
      cursor: not-allowed;
    }
    .required::after {
      content: " *";
      color: #ff0000;
    }
    .error-message {
      background-color: #ffebee;
      border-left: 4px solid #ff4444;
      padding: 15px;
      margin-bottom: 20px;
      border-radius: 0 4px 4px 0;
    }
    .warning-message {
      background-color: #fff8e1;
      border-left: 4px solid #ffbb33;
      padding: 15px;
      margin-bottom: 20px;
      border-radius: 0 4px 4px 0;
    }
    .info-box {
      margin-bottom: 30px;
      padding: 20px;
      border: 1px solid #e2e8f0;
      border-radius: 4px;
    }
    .page-title {
      margin-bottom: 20px;
      color: #2d3748;
      font-size: 24px;
      font-weight: 700;
    }
    .btn {
      padding: 8px 16px;
      border-radius: 4px;
      font-weight: 600;
      cursor: pointer;
      transition: all 0.2s ease;
      border: none;
    }
    .btn-primary {
      background-color: #4a6cf7;
      color: #ffffff;
    }
    .btn-primary:hover {
      background-color: #3a59e0;
    }
    .btn-cancel {
      background-color: #f7fafc;
      color: #4a5568;
      margin-left: 10px;
    }
    .btn-cancel:hover {
      background-color: #edf2f7;
    }
  </style>

  <script>
    // 根据选择的供应商更新预计到货日期
    function updateExpectedArrivalDate(supplierId) {
      const selectElement = document.querySelector('select[name="supplierId"]');
      const selectedOption = selectElement.options[selectElement.selectedIndex];
      const leadTime = selectedOption.getAttribute('data-lead-time');

      if (leadTime) {
        // 使用JavaScript计算日期
        const today = new Date();
        today.setDate(today.getDate() + parseInt(leadTime));
        const formattedDate = today.toISOString().split('T')[0];

        document.getElementById('expectedArrivalDateDisplay').textContent = formattedDate;
        document.getElementById('expectedArrivalDate').value = formattedDate;
        document.getElementById('leadTimeInfo').textContent =
                `根据所选供应商的交货周期(${leadTime}天)计算`;
      }
    }

    // 页面加载时初始化
    document.addEventListener('DOMContentLoaded', function() {
      const selectElement = document.querySelector('select[name="supplierId"]');

      // 为每个选项添加data-lead-time属性
      Array.from(selectElement.options).forEach(option => {
        if (option.value) {
          const leadTimeMatch = option.textContent.match(/交货周期: (\d+)天/);
          if (leadTimeMatch && leadTimeMatch[1]) {
            option.setAttribute('data-lead-time', leadTimeMatch[1]);
          }
        }
      });

      // 初始化显示第一个供应商的交货日期
      if (selectElement && selectElement.options.length > 1) {
        updateExpectedArrivalDate(selectElement.options[1].value);
      }

      // 添加变更事件监听器
      selectElement.addEventListener('change', function() {
        if (this.value) {
          updateExpectedArrivalDate(this.value);
        }
      });
    });
  </script>
</head>
<body>
<div class="form-container">
  <h1 class="page-title">创建采购订单</h1>

  <!-- 错误消息显示 -->
  <c:if test="${not empty errorMsg}">
    <div class="error-message">
      <p><strong>错误:</strong> ${errorMsg}</p>
    </div>
  </c:if>
  <c:if test="${not empty supplierError}">
    <div class="warning-message">
      <p><strong>警告:</strong> ${supplierError}</p>
    </div>
  </c:if>

  <!-- 产品信息显示 -->
  <c:if test="${not empty productName}">
    <div class="info-box">
      <p><strong>产品名称:</strong> ${productName}</p>
      <p><strong>当前库存:</strong> ${alertDetail.currentStock}</p>
      <p><strong>安全库存:</strong> ${alertDetail.safetyStock}</p>
      <p><strong>建议采购数量:</strong> ${suggestedQuantity}</p>

      <c:if test="${overstock}">
        <div class="warning-message">
          <p><strong>警告:</strong> 当前库存 ${currentStock} 已超过安全库存 ${safetyStock}，继续创建采购订单可能导致库存积压！</p>
        </div>
      </c:if>
    </div>

    <form action="saveMaterialOrderServlet" method="post">
      <input type="hidden" name="alertId" value="${alertDetail.alertId}">
      <input type="hidden" name="productId" value="${productId}">

      <div class="form-group">
        <label class="form-label required">供应商:</label>
        <select class="form-input" name="supplierId" required
                onchange="updateExpectedArrivalDate(this.value)">
          <option value="">请选择供应商</option>
          <c:forEach items="${supplierList}" var="supplier">
            <option value="${supplier.supplierId}"
                    data-lead-time="${supplier.leadTime}">
                ${supplier.supplierName} - ${supplier.contactPerson} (${supplier.contactPhone})
              - 交货周期: ${supplier.leadTime}天
            </option>
          </c:forEach>
        </select>
      </div>

      <div class="form-group">
        <label class="form-label required">采购数量:</label>
        <input type="number" class="form-input" name="quantity"
               min="0" value="${overstock ? 0 : suggestedQuantity}" required>
        <c:if test="${overstock}">
          <span style="font-size: 12px; color: #ff6600;">
            库存超量，建议采购数量已自动设为0
          </span>
        </c:if>
      </div>

      <div class="form-group">
        <label class="form-label">预计到货日期:</label>
        <input type="text" class="form-input form-input-readonly" id="expectedArrivalDateDisplay" readonly>
        <input type="hidden" name="expectedArrivalDate" id="expectedArrivalDate">
        <span id="leadTimeInfo" style="font-size: 12px; color: #718096;">
          <c:if test="${not empty supplierList}">
            默认根据首个供应商的交货周期(${supplierList[0].leadTime}天)计算
          </c:if>
        </span>
      </div>

      <div class="form-group">
        <label class="form-label">备注:</label>
        <textarea class="form-input" name="remark" rows="3"></textarea>
      </div>

      <div class="form-group">
        <button type="submit" class="btn btn-primary">
          提交订单
        </button>
        <button type="button" class="btn btn-cancel" onclick="history.back()">取消</button>
      </div>
    </form>
  </c:if>

  <!-- 无产品信息时的错误提示 -->
  <c:if test="${empty productName}">
    <div class="error-message">
      <p><strong>错误:</strong> 未找到产品信息，请返回库存预警列表重新选择</p>
      <a href="getInventoryAlertServlet">返回库存预警列表</a>
    </div>
  </c:if>
</div>
</body>
</html>