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

  <title>库存预警</title>

  <meta http-equiv="pragma" content="no-cache">
  <meta http-equiv="cache-control" content="no-cache">
  <meta http-equiv="expires" content="0">
  <meta http-equiv="keywords" content="库存,预警,管理">
  <meta http-equiv="description" content="库存预警页面">

  <link rel="stylesheet" type="text/css" href="Admin/css/body.css">
  <link rel="stylesheet" type="text/css" href="Admin/css/td_fontSize.css">
  <link rel="stylesheet" type="text/css" href="Admin/css/tab.css">
  <style type="text/css">
    table {
      text-align: center;
      margin: 0px;
      padding: 0px;
    }
    .alert-high {
      color: #FF0000;
      font-weight: bold;
    }
    .alert-medium {
      color: #FF6600;
      font-weight: bold;
    }
    .alert-low {
      color: #FFCC00;
      font-weight: bold;
    }
  </style>
  <script type="text/javascript">
    function searchAlerts() {
      var alertLevel = document.getElementById("alertLevel").value;
      var warehouseId = document.getElementById("warehouseId").value;
      var productName = document.getElementById("productName").value;

      var oform = document.forms[0]; // 使用document.forms[0]获取第一个表单
      oform.action = "getInventoryAlertServlet?alertLevel=" + alertLevel +
              "&warehouseId=" + warehouseId +
              "&productName=" + productName;
      oform.submit();
    }

    // 修改后的JavaScript函数，处理所有操作场景
    function handleInventory(btn, alertId, action) {
      // 显示加载提示
      var loadingMsg = "正在处理...";
      if (action === "view") {
        loadingMsg = "正在加载详情...";
      } else if (action === "order") {
        loadingMsg = "正在创建订单...";
      } else if (action === "ignore") {
        loadingMsg = "正在忽略预警...";
      }

      // 显示加载状态
      var originalHtml = btn.innerHTML;
      btn.innerHTML = loadingMsg;
      btn.disabled = true;

      // 发送 AJAX 请求
      var xhr = new XMLHttpRequest();
      xhr.open("POST", "handleInventoryAlertServlet", true);
      xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
      xhr.onreadystatechange = function() {
        if (xhr.readyState === 4) {
          if (xhr.status === 200) {
            try {
              var response = JSON.parse(xhr.responseText);

              if (response.status === "success") {
                if (action === "view") {
                  // 查看详情处理
                  if (response.data.redirectUrl) {
                    window.location.href = response.data.redirectUrl;
                  }
                } else if (action === "order") {
                  // 直接跳转到采购订单创建页面
                  window.location.href = "createMaterialOrderServlet?alertId=" + alertId;
                } else if (action === "ignore") {
                  alert("预警已忽略！");
                  var tr = btn.closest("tr");
                  if (tr) tr.style.display = "none";
                }
              } else {
                alert("操作失败: " + response.message);
              }
            } catch (jsonError) {
              alert("数据解析失败: " + xhr.responseText);
            }
          } else {
            alert("服务器错误: 状态码 " + xhr.status);
          }
        }
        // 恢复按钮状态
        btn.innerHTML = originalHtml;
        btn.disabled = false;
      };
      xhr.send("action=" + action + "&alertId=" + alertId);
    }


    function buildDetailHtml(detail) {
      if (detail.error) {
        return "<div class='error-message' style='color: red; padding: 10px; border: 1px solid #ffcccc; border-radius: 5px;'>" +
                detail.error + "</div>";
      }

      return "<h2>预警详情</h2>" +
              "<p><strong>预警ID:</strong> " + detail.alertId + "</p>" +
              "<p><strong>产品名称:</strong> " + detail.productName + "</p>" +
              "<p><strong>仓库:</strong> " + detail.warehouseName + "</p>" +
              "<p><strong>当前库存:</strong> " + detail.currentStock + "</p>" +
              "<p><strong>安全库存:</strong> " + detail.safetyStock + "</p>" +
              "<p><strong>预警类型:</strong> " + detail.alertType + "</p>" +
              "<p><strong>预警级别:</strong> " + getAlertLevelText(detail.alertLevel) + "</p>" +
              "<p><strong>预警信息:</strong> " + detail.alertMessage + "</p>" +
              "<p><strong>预警时间:</strong> " + formatDateTime(detail.alertTime) + "</p>" +
              (detail.processTime ?
                      "<p><strong>处理时间:</strong> " + formatDateTime(detail.processTime) + "</p>" +
                      "<p><strong>处理备注:</strong> " + (detail.processNote || "未处理") + "</p>" :
                      "<p><strong>状态:</strong> 未处理</p>");
    }

    // 日期时间格式化
    function getAlertLevelText(level) {
      switch(level) {
        case "high": return "<span class='alert-high'>高优先级</span>";
        case "medium": return "<span class='alert-medium'>中优先级</span>";
        default: return "<span class='alert-low'>低优先级</span>";
      }
    }

  </script>
</head>

<body>
<!-- 包裹在一个表单中，便于搜索功能使用 -->
<form>
  <table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
    <tr>
      <td height="30"><table width="100%" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td width="15" height="30"><img src="Admin/images/tab_03.gif" width="15" height="30" /></td>
          <td width="1101" background="Admin/images/tab_05.gif">&nbsp;</td>
          <td width="281" background="Admin/images/tab_05.gif"><table border="0" align="right" cellpadding="0" cellspacing="0">
            <tr>
              <td width="60"><table width="87%" border="0" cellpadding="0" cellspacing="0">
                <tr>
                  <td class="STYLE1"><div align="center"></div></td>
                  <td class="STYLE1"><div align="center"></div></td>
                </tr>
              </table></td>
              <td width="60"><table width="90%" border="0" cellpadding="0" cellspacing="0">
                <tr>
                  <td class="STYLE1"><div align="center"><br></div></td>
                  <td class="STYLE1"></td>
                </tr>
              </table></td>
              <td width="60"><table width="90%" border="0" cellpadding="0" cellspacing="0">
                <tr>
                  <td class="STYLE1"><div align="center"><br></div></td>
                  <td class="STYLE1"><div align="center"><br></div></td>
                </tr>
              </table></td>
              <td width="52"><table width="88%" border="0" cellpadding="0" cellspacing="0">
                <tr>
                  <td class="STYLE1"><div align="center"><br></div></td>
                  <td class="STYLE1"><div align="center"><br></div></td>
                </tr>
              </table></td>
            </tr>
          </table></td>
          <td width="14"><img src="Admin/images/tab_07.gif" width="14" height="30" /></td>
        </tr>
      </table></td>
    </tr>
    <tr>
      <td><table width="100%" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td width="9" background="Admin/images/tab_12.gif">&nbsp;</td>
          <td bgcolor="#f3ffe3">

            <div align="left">
              按预警级别查询:
              <select id="alertLevel" name="alertLevel">
                <option value="">所有级别</option>
                <option value="high" ${alertLevel == 'high' ? 'selected' : ''}>高优先级</option>
                <option value="medium" ${alertLevel == 'medium' ? 'selected' : ''}>中优先级</option>
                <option value="low" ${alertLevel == 'low' ? 'selected' : ''}>低优先级</option>
              </select>

              按仓库查询:
              <select id="warehouseId" name="warehouseId">
                <option value="">所有仓库</option>
                <c:forEach items="${warehouseList}" var="warehouse">
                  <option value="${warehouse.warehouseId}" ${warehouseId == warehouse.warehouseId ? 'selected' : ''}>${warehouse.warehouseName}</option>
                </c:forEach>
              </select>

              按产品名称查询:
              <input type="text" id="productName" name="productName" value="${productName}">

              <input type="button" value="查询" onclick="searchAlerts()">
              <div id="searchDiv" style="display: inline"></div>
            </div>

            <table width="99%" border="0" align="center" cellpadding="0" cellspacing="1" bgcolor="#c0de98">
              <tr>
                <th background="Admin/images/tab_14.gif" class="STYLE1">预警ID</th>
                <th background="Admin/images/tab_14.gif" class="STYLE1">产品ID</th>
                <th background="Admin/images/tab_14.gif" class="STYLE1">产品名称</th>
                <th background="Admin/images/tab_14.gif" class="STYLE1">仓库</th>
                <th background="Admin/images/tab_14.gif" class="STYLE1">当前库存</th>
                <th background="Admin/images/tab_14.gif" class="STYLE1">安全库存</th>
                <th background="Admin/images/tab_14.gif" class="STYLE1">预警级别</th>
                <th background="Admin/images/tab_14.gif" class="STYLE1">预警时间</th>
                <th background="Admin/images/tab_14.gif" class="STYLE1">操作</th>
              </tr>

              <c:if test="${empty alertList}">
                <tr>
                  <td colspan="9" bgcolor="#FFFFFF" class="STYLE2">没有找到符合条件的库存预警记录</td>
                </tr>
              </c:if>

              <c:forEach var="alert" items="${alertList}">
                <tr>
                  <td bgcolor="#FFFFFF" class="STYLE2">${alert.alertId}</td>
                  <td bgcolor="#FFFFFF" class="STYLE2">${alert.productId}</td>
                  <td bgcolor="#FFFFFF" class="STYLE2">${alert.productName}</td>
                  <td bgcolor="#FFFFFF" class="STYLE2">${alert.warehouseName}</td>
                  <td bgcolor="#FFFFFF" class="STYLE2">${alert.currentStock}</td>
                  <td bgcolor="#FFFFFF" class="STYLE2">${alert.safetyStock}</td>
                  <td bgcolor="#FFFFFF" class="STYLE2">
                    <c:choose>
                      <c:when test="${alert.alertLevel eq 'high'}">
                        <span class="alert-high">高优先级</span>
                      </c:when>
                      <c:when test="${alert.alertLevel eq 'medium'}">
                        <span class="alert-medium">中优先级</span>
                      </c:when>
                      <c:otherwise>
                        <span class="alert-low">低优先级</span>
                      </c:otherwise>
                    </c:choose>
                  </td>
                  <td bgcolor="#FFFFFF" class="STYLE2">${alert.alertTime}</td>
                  <td bgcolor="#FFFFFF" class="STYLE2">
                    <a href="javascript:void(0);" onclick="handleInventory(this, ${alert.alertId}, 'view')">查看详情</a>
                    |
                    <a href="javascript:void(0);" onclick="handleInventory(this, ${alert.alertId}, 'order')">
                      创建订单
                    </a>
                    | <a href="javascript:void(0);" onclick="handleInventory(this, ${alert.alertId}, 'ignore')">忽略预警</a>
                  </td>
                </tr>
              </c:forEach>
            </table>
          </td>
          <td width="9" background="Admin/images/tab_16.gif">&nbsp;</td>
        </tr>
      </table></td>
    </tr>
    <tr>
      <td height="29"><table width="100%" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td width="15" height="29"><img src="Admin/images/tab_20.gif" width="15" height="29" /></td>
          <td background="Admin/images/tab_21.gif"><table width="100%" border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td width="25%" height="29" nowrap="nowrap">&nbsp;</td>
              <td width="75%" valign="top" class="STYLE1">&nbsp;</td>
            </tr>
          </table></td>
          <td width="14"><img src="Admin/images/tab_22.gif" width="14" height="29" /></td>
        </tr>
      </table></td>
    </tr>
  </table>
</form>
</body>
</html>