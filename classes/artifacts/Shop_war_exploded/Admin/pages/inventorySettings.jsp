<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="tools.jsp" %>
<%
  String path = request.getContextPath();
  String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
  <base href="<%=basePath%>">
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

  <title>库存设置管理</title>

  <meta http-equiv="pragma" content="no-cache">
  <meta http-equiv="cache-control" content="no-cache">
  <meta http-equiv="expires" content="0">
  <meta http-equiv="keywords" content="库存设置,库存预警,自动审批">
  <meta http-equiv="description" content="库存设置管理页面">

  <link rel="stylesheet" type="text/css" href="Admin/css/body.css">
  <link rel="stylesheet" type="text/css" href="Admin/css/td_fontSize.css">
  <link rel="stylesheet" type="text/css" href="Admin/css/tab.css">
  <style type="text/css">
    table {
      text-align: center;
      margin: 0px;
      padding: 0px;
    }
    .setting-group {
      margin-bottom: 20px;
      padding: 10px;
      border: 1px solid #ddd;
      background-color: #f9f9f9;
    }
    .setting-label {
      font-weight: bold;
      display: inline-block;
      width: 150px;
    }
    .setting-input {
      width: 100px;
      margin-right: 10px;
    }
    .alert-message {
      color: red;
      font-weight: bold;
    }
    .success-message {
      color: green;
      font-weight: bold;
    }
  </style>
  <script type="text/javascript">
    function saveSettings() {
      var form = document.getElementById("settingsForm");
      form.submit();
    }

    function resetSettings() {
      if (confirm("确定要重置所有设置为默认值吗？")) {
        var form = document.getElementById("settingsForm");
        form.action = "resetInventorySettingsServlet";
        form.submit();
      }
    }

    function searchProducts() {
      var categoryId = document.getElementById("categoryId").value;
      var productName = document.getElementById("productName").value;

      var oform = document.getElementsByTagName("form")[1];
      oform.action = "inventorySettingsServlet?categoryId=" + categoryId + "&productName=" + productName;
      oform.submit();
    }

    function updateProductStock(productId) {
      var newStock = prompt("请输入新的库存数量:", "");
      if (newStock !== null && newStock !== "") {
        if (!isNaN(newStock) && parseInt(newStock) >= 0) {
          var oform = document.createElement("form");
          oform.action = "updateProductStockServlet";
          oform.method = "post";

          var inputProductId = document.createElement("input");
          inputProductId.type = "hidden";
          inputProductId.name = "productId";
          inputProductId.value = productId;

          var inputNewStock = document.createElement("input");
          inputNewStock.type = "hidden";
          inputNewStock.name = "newStock";
          inputNewStock.value = newStock;

          oform.appendChild(inputProductId);
          oform.appendChild(inputNewStock);
          document.body.appendChild(oform);
          oform.submit();
        } else {
          alert("请输入有效的库存数量！");
        }
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

          <!-- 消息显示区域 -->
          <div id="messageDiv">
            <c:if test="${not empty message}">
              <c:choose>
                <c:when test="${message.contains('成功')}">
                  <div class="success-message">${message}</div>
                </c:when>
                <c:otherwise>
                  <div class="alert-message">${message}</div>
                </c:otherwise>
              </c:choose>
            </c:if>
          </div>

          <!-- 库存设置区域 -->
          <div class="setting-group">
            <h3>库存预警设置</h3>
            <form id="settingsForm" action="inventorySettingsServlet" method="post">
              <div class="setting-row">
                <span class="setting-label">最小库存预警值:</span>
                <input type="text" name="minStockAlert" id="minStockAlert"
                       value="${param.minStockAlert != null ? param.minStockAlert : '10'}" class="setting-input">
                <span class="setting-hint">当库存低于此值时发出预警</span>
              </div>

              <div class="setting-row">
                <span class="setting-label">最大库存预警值:</span>
                <input type="text" name="maxStockAlert" id="maxStockAlert"
                       value="${param.maxStockAlert != null ? param.maxStockAlert : '100'}" class="setting-input">
                <span class="setting-hint">当库存高于此值时发出预警</span>
              </div>

              <div class="setting-row">
                <span class="setting-label">补货点:</span>
                <input type="text" name="reorderPoint" id="reorderPoint"
                       value="${param.reorderPoint != null ? param.reorderPoint : '20'}" class="setting-input">
                <span class="setting-hint">当库存低于此值时触发补货流程</span>
              </div>

              <div class="setting-row">
                <span class="setting-label">自动审批阈值:</span>
                <input type="text" name="autoApproveThreshold" id="autoApproveThreshold"
                       value="${param.autoApproveThreshold != null ? param.autoApproveThreshold : '500'}" class="setting-input">
                <span class="setting-hint">小于此金额的采购单自动审批</span>
              </div>

              <div class="button-group">
                <input type="button" value="保存设置" onclick="saveSettings()">
                <input type="button" value="重置默认值" onclick="resetSettings()">
              </div>
            </form>
          </div>

          <!-- 产品搜索区域 -->
          <div class="setting-group">
            <h3>产品库存管理</h3>
            <div align="left">
              类别:
              <select id="categoryId" name="categoryId">
                <option value="">所有类别</option>
                <c:forEach var="category" items="${categoryList}">
                  <option value="${category.categoryId}" ${param.categoryId == category.categoryId ? 'selected' : ''}>
                      ${category.categoryName}
                  </option>
                </c:forEach>
              </select>

              产品名称:
              <input type="text" id="productName" name="productName" value="${param.productName}">

              <input type="button" value="查询" onclick="searchProducts()">
              <div id="searchDiv" style="display: inline"></div>
            </div>

            <table width="99%" border="0" align="center" cellpadding="0" cellspacing="1" bgcolor="#c0de98">
              <tr>
                <th background="Admin/images/tab_14.gif" class="STYLE1">产品ID</th>
                <th background="Admin/images/tab_14.gif" class="STYLE1">产品名称</th>
                <th background="Admin/images/tab_14.gif" class="STYLE1">类别</th>
                <th background="Admin/images/tab_14.gif" class="STYLE1">供应商</th>
                <th background="Admin/images/tab_14.gif" class="STYLE1">当前库存</th>
                <th background="Admin/images/tab_14.gif" class="STYLE1">操作</th>
              </tr>

              <c:if test="${empty productList}">
                <tr>
                  <td colspan="6" bgcolor="#FFFFFF" class="STYLE2">没有找到符合条件的产品</td>
                </tr>
              </c:if>

              <c:forEach var="product" items="${productList}">
                <tr>
                  <td bgcolor="#FFFFFF" class="STYLE2">${product.productId}</td>
                  <td bgcolor="#FFFFFF" class="STYLE2">${product.productName}</td>
                  <td bgcolor="#FFFFFF" class="STYLE2">${product.categoryName}</td>
                  <td bgcolor="#FFFFFF" class="STYLE2">${product.supplierName}</td>
                  <td bgcolor="#FFFFFF" class="STYLE2">
                    <c:choose>
                      <c:when test="${product.stock < 10}">
                        <span style="color: red; font-weight: bold">${product.stock} (库存不足)</span>
                      </c:when>
                      <c:when test="${product.stock > 100}">
                        <span style="color: orange; font-weight: bold">${product.stock} (库存过多)</span>
                      </c:when>
                      <c:otherwise>
                        ${product.stock}
                      </c:otherwise>
                    </c:choose>
                  </td>
                  <td bgcolor="#FFFFFF" class="STYLE2">
                    <a href="javascript:updateProductStock(${product.productId})">更新库存</a>
                  </td>
                </tr>
              </c:forEach>
            </table>
          </div>
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
            <td>
              <pg:pager items="${productPager.totalCount}" maxPageItems="${productPager.pageSize}" export="currentPageNo = pageNumber" url="inventorySettingsServlet">
                <pg:param name="pageSize" value="${productPager.pageSize}"/>
                <pg:param name="pageNo" value="${currentPageNo}"/>
                <pg:param name="categoryId" value="${param.categoryId}"/>
                <pg:param name="productName" value="${param.productName}"/>
                <pg:first>
                  <a href="${pageUrl}"><img src="Admin/images/first.gif" border="0"></a>
                </pg:first>
                <pg:prev>
                  <a href="${pageUrl}"><img src="Admin/images/back.gif" border="0" /></a>
                </pg:prev>
                <pg:pages>
                  <c:choose>
                    <c:when test="${productPager.pagecurrentPageNo eq pageNumber}">
                      <font color="red">${pageNumber}</font>
                    </c:when>
                    <c:otherwise>
                      <a href="${pageUrl}">${pageNumber}</a>
                    </c:otherwise>
                  </c:choose>
                </pg:pages>
                <pg:next>
                  <a href="${pageUrl}"><img src="Admin/images/next.gif" border="0"/></a>
                </pg:next>
                <pg:last>
                  <a href="${pageUrl}"><img src="Admin/images/last.gif" border="0"/></a>
                </pg:last>
              </pg:pager>
            </td>
            <td colspan="1" align="right">
              共 ${productPager.totalCount} 条记录，当前第 ${productPager.pagecurrentPageNo} 页
            </td>
            <td width="14"><img src="Admin/images/tab_22.gif" width="14" height="29" /></td>
          </tr>
        </table></td>
      </tr>
    </table></td>
  </tr>
</table>
</body>
</html>