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

  <title>仓库管理</title>

  <meta http-equiv="pragma" content="no-cache">
  <meta http-equiv="cache-control" content="no-cache">
  <meta http-equiv="expires" content="0">
  <meta http-equiv="keywords" content="仓库,管理,库存">
  <meta http-equiv="description" content="仓库管理页面">

  <link rel="stylesheet" type="text/css" href="Admin/css/body.css">
  <link rel="stylesheet" type="text/css" href="Admin/css/td_fontSize.css">
  <link rel="stylesheet" type="text/css" href="Admin/css/tab.css">
  <style type="text/css">
    table {
      text-align: center;
      margin: 0px;
      padding: 0px;
    }
  </style>
  <script type="text/javascript">
    function searchWarehouse() {
      var warehouseName = document.getElementById("warehouseName").value;
      var warehouseStatus = document.getElementById("warehouseStatus").value;
      var oform = document.getElementsByTagName("form")[0];

      oform.action = "getWarehouseListServlet?warehouseName=" + warehouseName + "&warehouseStatus=" + warehouseStatus;
      oform.submit();
    }

    function addWarehouse() {
      window.location.href = "addWarehouseServlet";
    }

    function editWarehouse(warehouseId) {
      window.location.href = "editWarehouseServlet?warehouseId=" + warehouseId;
    }

    function deleteWarehouse(warehouseId) {
      if(confirm("确定要禁用此仓库吗？")) {
        window.location.href = "deleteWarehouseServlet?warehouseId=" + warehouseId;
      }
    }

    function activateWarehouse(warehouseId) {
      if(confirm("确定要启用此仓库吗？")) {
        window.location.href = "activateWarehouseServlet?warehouseId=" + warehouseId;
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
            按仓库名称查询:
            <input type="text" id="warehouseName" name="warehouseName" value="${warehouseName}">

            按状态查询:
            <select id="warehouseStatus" name="warehouseStatus">
              <option value="">所有状态</option>
              <option value="1" ${warehouseStatus == "1" ? 'selected' : ''}>启用</option>
              <option value="0" ${warehouseStatus == "0" ? 'selected' : ''}>禁用</option>
            </select>

            <input type="button" value="查询" onclick="searchWarehouse()">
            <input type="button" value="添加新仓库" onclick="addWarehouse()">
          </div>

          <table width="99%" border="0" align="center" cellpadding="0" cellspacing="1" bgcolor="#c0de98">
            <tr>
              <th background="Admin/images/tab_14.gif" class="STYLE1">仓库ID</th>
              <th background="Admin/images/tab_14.gif" class="STYLE1">仓库编码</th>
              <th background="Admin/images/tab_14.gif" class="STYLE1">仓库名称</th>
              <th background="Admin/images/tab_14.gif" class="STYLE1">位置</th>
              <th background="Admin/images/tab_14.gif" class="STYLE1">容量</th>
              <th background="Admin/images/tab_14.gif" class="STYLE1">当前库存</th>
              <th background="Admin/images/tab_14.gif" class="STYLE1">状态</th>
              <th background="Admin/images/tab_14.gif" class="STYLE1">操作</th>
            </tr>
            <c:forEach var="warehouse" items="${warehouseList}">
              <tr>
                <td bgcolor="#FFFFFF" class="STYLE2">${warehouse.warehouseId}</td>
                <td bgcolor="#FFFFFF" class="STYLE2">${warehouse.warehouseCode}</td>
                <td bgcolor="#FFFFFF" class="STYLE2">${warehouse.warehouseName}</td>
                <td bgcolor="#FFFFFF" class="STYLE2">${warehouse.location}</td>
                <td bgcolor="#FFFFFF" class="STYLE2">${warehouse.capacity}</td>
                <td bgcolor="#FFFFFF" class="STYLE2">${warehouse.currentStock}</td>
                <td bgcolor="#FFFFFF" class="STYLE2">
                  <c:if test="${warehouse.status == 1}">
                    <span style="color:green">启用</span>
                  </c:if>
                  <c:if test="${warehouse.status == 0}">
                    <span style="color:red">禁用</span>
                  </c:if>
                </td>
                <td bgcolor="#FFFFFF" class="STYLE2">
                  <a href="javascript:editWarehouse(${warehouse.warehouseId})">编辑</a>
                  <c:if test="${warehouse.status == 1}">
                    | <a href="javascript:deleteWarehouse(${warehouse.warehouseId})">禁用</a>
                  </c:if>
                  <c:if test="${warehouse.status == 0}">
                    | <a href="javascript:activateWarehouse(${warehouse.warehouseId})">启用</a>
                  </c:if>
                </td>
              </tr>
            </c:forEach>
            <c:if test="${empty warehouseList}">
              <tr>
                <td colspan="8" bgcolor="#FFFFFF" class="STYLE2">没有找到符合条件的仓库记录</td>
              </tr>
            </c:if>
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