<%@ page language="java" pageEncoding="utf-8"%>
<%@ page contentType="text/html;charset=utf-8" %>
<%@ include file="tools.jsp" %>
<%
  String path = request.getContextPath();
  String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
  <base href="<%=basePath%>">

  <title>原材料订单</title>

  <meta http-equiv="pragma" content="no-cache">
  <meta http-equiv="cache-control" content="no-cache">
  <meta http-equiv="expires" content="0">
  <meta http-equiv="keywords" content="原材料订单,订单管理,采购订单">
  <meta http-equiv="description" content="原材料订单管理页面">

  <link rel="stylesheet" type="text/css" href="Admin/css/body.css">
  <link rel="stylesheet" type="text/css" href="Admin/css/td_fontSize.css">
  <link rel="stylesheet" type="text/css" href="Admin/css/tab.css">
  <style type="text/css">
    table {
      text-align: center;
      margin: 0px;
      padding: 0px;
    }
    .status-pending {
      background-color: #ffffcc;
      color: #ff9900;
      font-weight: bold;
    }
    .status-processing {
      background-color: #ccffcc;
      color: #006600;
      font-weight: bold;
    }
    .status-shipped {
      background-color: #ccccff;
      color: #0000cc;
      font-weight: bold;
    }
    .status-completed {
      background-color: #ccffcc;
      color: #006600;
      font-weight: bold;
    }
    .status-cancelled {
      background-color: #ffcccc;
      color: #cc0000;
      font-weight: bold;
    }
  </style>
  <script type="text/javascript">
    function searchOrders() {
      var orderNo = document.getElementById("orderNo").value;
      var status = document.getElementById("status").value;
      var supplier = document.getElementById("supplier").value;
      var startDate = document.getElementById("startDate").value;
      var endDate = document.getElementById("endDate").value;

      var oform = document.getElementsByTagName("form")[0];
      oform.action = "getMaterialOrderServlet?orderNo=" + orderNo +
              "&status=" + status +
              "&supplier=" + supplier +
              "&startDate=" + startDate +
              "&endDate=" + endDate;
      oform.submit();
    }

    function viewOrder(orderId) {
      window.location.href = "viewMaterialOrderServlet?orderId=" + orderId;
    }

    function cancelOrder(orderId) {
      if(confirm("确定要取消此订单吗？")) {
        window.location.href = "cancelMaterialOrderServlet?orderId=" + orderId;
      }
    }
  </script>
</head>

<body>
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
            订单号:
            <input type="text" id="orderNo" name="orderNo" value="${orderNo}">

            订单状态:
            <select id="status" name="status">
              <option value="">所有状态</option>
              <option value="pending" ${status == "pending" ? 'selected' : ''}>待处理</option>
              <option value="processing" ${status == "processing" ? 'selected' : ''}>处理中</option>
              <option value="shipped" ${status == "shipped" ? 'selected' : ''}>已发货</option>
              <option value="completed" ${status == "completed" ? 'selected' : ''}>已完成</option>
              <option value="cancelled" ${status == "cancelled" ? 'selected' : ''}>已取消</option>
            </select>

            供应商:
            <input type="text" id="supplier" name="supplier" value="${supplier}">

            日期范围:
            <input type="text" id="startDate" name="startDate" value="${startDate}" placeholder="开始日期">
            -
            <input type="text" id="endDate" name="endDate" value="${endDate}" placeholder="结束日期">

            <input type="button" value="查询" onclick="searchOrders()">
            <div id="searchDiv" style="display: inline"></div>
          </div>

          <table width="99%" border="0" align="center" cellpadding="0" cellspacing="1" bgcolor="#c0de98">
            <tr>
              <th background="Admin/images/tab_14.gif" class="STYLE1">订单ID</th>
              <th background="Admin/images/tab_14.gif" class="STYLE1">订单号</th>
              <th background="Admin/images/tab_14.gif" class="STYLE1">供应商</th>
              <th background="Admin/images/tab_14.gif" class="STYLE1">下单日期</th>
              <th background="Admin/images/tab_14.gif" class="STYLE1">金额</th>
              <th background="Admin/images/tab_14.gif" class="STYLE1">商品数量</th>
              <th background="Admin/images/tab_14.gif" class="STYLE1">订单状态</th>
              <th background="Admin/images/tab_14.gif" class="STYLE1">操作</th>
            </tr>

            <c:if test="${empty materialOrderList}">
              <tr>
                <td colspan="8" bgcolor="#FFFFFF" class="STYLE2">没有找到符合条件的原材料订单记录</td>
              </tr>
            </c:if>

            <c:forEach var="order" items="${materialOrderList}">
              <tr>
                <td bgcolor="#FFFFFF" class="STYLE2">${order.orderId}</td>
                <td bgcolor="#FFFFFF" class="STYLE2">${order.orderNo}</td>
                <td bgcolor="#FFFFFF" class="STYLE2">${order.supplierName}</td>
                <td bgcolor="#FFFFFF" class="STYLE2">${order.createTime}</td>
                <td bgcolor="#FFFFFF" class="STYLE2">¥ ${order.totalAmount}</td>
                <td bgcolor="#FFFFFF" class="STYLE2">${order.materialCount}</td>
                <td bgcolor="#FFFFFF" class="STYLE2">
                  <c:choose>
                    <c:when test="${order.status.name() == 'PENDING'}">
                      <span class="status-pending">待处理</span>
                    </c:when>
                    <c:when test="${order.status.name() == 'PROCESSING'}">
                      <span class="status-processing">处理中</span>
                    </c:when>
                    <c:when test="${order.status.name() == 'SHIPPED'}">
                      <span class="status-shipped">已发货</span>
                    </c:when>
                    <c:when test="${order.status.name() == 'COMPLETED'}">
                      <span class="status-completed">已完成</span>
                    </c:when>

                    <c:when test="${order.status.name() == 'CANCELLED'}">
                      <span class="status-completed">已取消</span>
                    </c:when>
                    <c:otherwise>
                      <span class="status-cancelled">##</span>
                    </c:otherwise>
                  </c:choose>
                </td>
                <td bgcolor="#FFFFFF" class="STYLE2">
                  <a href="javascript:viewOrder(${order.orderId})">查看</a>
                  <c:if test="${order.status.name() == 'pending' || order.status.name() == 'processing'}">
                    | <a href="javascript:cancelOrder(${order.orderId})">取消</a>
                  </c:if>
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
</body>
</html>