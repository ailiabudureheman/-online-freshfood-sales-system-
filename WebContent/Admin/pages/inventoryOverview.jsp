<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="tools.jsp" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%
  String path = request.getContextPath();
  String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
  <base href="<%=basePath%>">
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

  <title>库存总览</title>

  <meta http-equiv="pragma" content="no-cache">
  <meta http-equiv="cache-control" content="no-cache">
  <meta http-equiv="expires" content="0">
  <meta http-equiv="keywords" content="库存总览,库存预警,库存统计">
  <meta http-equiv="description" content="库存总览页面">

  <link rel="stylesheet" type="text/css" href="Admin/css/body.css">
  <link rel="stylesheet" type="text/css" href="Admin/css/td_fontSize.css">
  <link rel="stylesheet" type="text/css" href="Admin/css/tab.css">
  <style type="text/css">
    table {
      text-align: center;
      margin: 0px;
      padding: 0px;
    }
    .alert-message {
      color: red;
      font-weight: bold;
    }
    .warning-message {
      color: orange;
      font-weight: bold;
    }
    .info-message {
      color: blue;
      font-weight: bold;
    }
    .stat-box {
      width: 200px;
      height: 100px;
      margin: 10px;
      padding: 10px;
      border: 1px solid #ddd;
      border-radius: 5px;
      display: inline-block;
      text-align: center;
    }
    .stat-value {
      font-size: 24px;
      font-weight: bold;
      margin-top: 10px;
    }
    .stat-title {
      font-size: 14px;
      color: #666;
    }
    .inventory-table {
      width: 99%;
      border: 0;
      align: center;
      cellpadding: 0;
      cellspacing: 1;
      bgcolor: #c0de98;
    }
  </style>
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

          <!-- 库存统计信息 -->
          <div align="center">
            <h3>库存统计概览</h3>
            <c:if test="${empty inventoryList}">
              <div class="info-message">暂无库存数据</div>
            </c:if>
            <c:if test="${not empty inventoryList}">
              <div class="stat-box">
                <div class="stat-title">产品总数</div>
                <div class="stat-value">${fn:length(inventoryList)}</div>
              </div>
              <div class="stat-box">
                <div class="stat-title">库存总量</div>
                <div class="stat-value">
                    <%-- 使用JSTL循环计算库存总量 --%>
                  <c:set var="totalStock" value="0" />
                  <c:forEach var="item" items="${inventoryList}">
                    <c:set var="totalStock" value="${totalStock + item.quantity}" />
                  </c:forEach>
                    ${totalStock}
                </div>
              </div>
              <div class="stat-box">
                <div class="stat-title">仓库数量</div>
                <div class="stat-value">${fn:length(warehouseList)}</div>
              </div>
              <div class="stat-box">
                <div class="stat-title">平均库存</div>
                <div class="stat-value">
                  <c:choose>
                    <c:when test="${fn:length(inventoryList) > 0}">
                      <fmt:formatNumber value="${totalStock / fn:length(inventoryList)}" pattern="0.00" />
                    </c:when>
                    <c:otherwise>0.00</c:otherwise>
                  </c:choose>
                </div>
              </div>
            </c:if>
          </div>

          <!-- 库存明细列表 -->
          <div align="center">
            <h3>库存明细</h3>
            <c:if test="${empty inventoryList}">
              <div class="info-message">暂无库存记录</div>
            </c:if>
            <c:if test="${not empty inventoryList}">
              <table class="inventory-table">
                <tr>
                  <th background="Admin/images/tab_14.gif" class="STYLE1">产品ID</th>
                  <th background="Admin/images/tab_14.gif" class="STYLE1">产品名称</th>
                  <th background="Admin/images/tab_14.gif" class="STYLE1">库存数量</th>
                  <th background="Admin/images/tab_14.gif" class="STYLE1">仓库</th>
                  <th background="Admin/images/tab_14.gif" class="STYLE1">操作</th>
                </tr>
                <c:forEach var="item" items="${inventoryList}">
                  <tr>
                    <td bgcolor="#FFFFFF" class="STYLE2">${item.productId}</td>
                    <td bgcolor="#FFFFFF" class="STYLE2">${item.productName}</td>
                    <td bgcolor="#FFFFFF" class="STYLE2">${item.quantity}</td>
                    <td bgcolor="#FFFFFF" class="STYLE2">
                      <c:forEach var="warehouse" items="${warehouseList}" varStatus="status">
                        <c:if test="${warehouse.warehouseId == item.warehouseId}">
                          ${warehouse.warehouseName}
                        </c:if>
                      </c:forEach>
                    </td>
                    <td bgcolor="#FFFFFF" class="STYLE2">
                      <a href="viewProductInventoryServlet?productId=${item.productId}">查看详情</a>
                    </td>
                  </tr>
                </c:forEach>
              </table>
            </c:if>
          </div>

          <!-- 库存预警信息 -->
          <div align="center">
            <h3>库存预警</h3>
            <c:if test="${empty alertList}">
              <div class="info-message">暂无库存预警信息</div>
            </c:if>
            <c:if test="${not empty alertList}">
              <table class="inventory-table">
                <tr>
                  <th background="Admin/images/tab_14.gif" class="STYLE1">预警类型</th>
                  <th background="Admin/images/tab_14.gif" class="STYLE1">数量</th>
                  <th background="Admin/images/tab_14.gif" class="STYLE1">详情</th>
                </tr>
                <!-- 库存不足预警 -->
                <tr>
                  <td bgcolor="#FFFFFF" class="STYLE2">库存不足</td>
                  <td bgcolor="#FFFFFF" class="STYLE2">
                    <c:set var="lowStockCount" value="0" />
                    <c:forEach var="alert" items="${alertList}">
                      <c:if test="${alert.alertType == 'LOW_STOCK'}">
                        <c:set var="lowStockCount" value="${lowStockCount + 1}" />
                      </c:if>
                    </c:forEach>
                    <c:choose>
                      <c:when test="${lowStockCount > 0}">
                        <span class="alert-message">${lowStockCount}</span>
                      </c:when>
                      <c:otherwise>
                        ${lowStockCount}
                      </c:otherwise>
                    </c:choose>
                  </td>
                  <td bgcolor="#FFFFFF" class="STYLE2">
                    <c:if test="${lowStockCount > 0}">
                      <a href="getInventoryAlertServlet">查看详情</a>
                    </c:if>
                  </td>
                </tr>
                <!-- 库存过多预警 -->
                <tr>
                  <td bgcolor="#FFFFFF" class="STYLE2">库存过多</td>
                  <td bgcolor="#FFFFFF" class="STYLE2">
                    <c:set var="highStockCount" value="0" />
                    <c:forEach var="alert" items="${alertList}">
                      <c:if test="${alert.alertType == 'HIGH_STOCK'}">
                        <c:set var="highStockCount" value="${highStockCount + 1}" />
                      </c:if>
                    </c:forEach>
                    <c:choose>
                      <c:when test="${highStockCount > 0}">
                        <span class="warning-message">${highStockCount}</span>
                      </c:when>
                      <c:otherwise>
                        ${highStockCount}
                      </c:otherwise>
                    </c:choose>
                  </td>
                  <td bgcolor="#FFFFFF" class="STYLE2">
                    <c:if test="${highStockCount > 0}">
                      <a href="getInventoryAlertServlet">查看详情</a>
                    </c:if>
                  </td>
                </tr>
                <!-- 即将过期预警 -->
                <tr>
                  <td bgcolor="#FFFFFF" class="STYLE2">即将过期</td>
                  <td bgcolor="#FFFFFF" class="STYLE2">
                    <c:set var="expiringCount" value="0" />
                    <c:forEach var="alert" items="${alertList}">
                      <c:if test="${alert.alertType == 'EXPIRING_SOON'}">
                        <c:set var="expiringCount" value="${expiringCount + 1}" />
                      </c:if>
                    </c:forEach>
                    <c:choose>
                      <c:when test="${expiringCount > 0}">
                        <span class="warning-message">${expiringCount}</span>
                      </c:when>
                      <c:otherwise>
                        ${expiringCount}
                      </c:otherwise>
                    </c:choose>
                  </td>
                  <td bgcolor="#FFFFFF" class="STYLE2">
                    <c:if test="${expiringCount > 0}">
                      <a href="getInventoryAlertServlet">查看详情</a>
                    </c:if>
                  </td>
                </tr>
                <!-- 已过期预警 -->
                <tr>
                  <td bgcolor="#FFFFFF" class="STYLE2">已过期</td>
                  <td bgcolor="#FFFFFF" class="STYLE2">
                    <c:set var="expiredCount" value="0" />
                    <c:forEach var="alert" items="${alertList}">
                      <c:if test="${alert.alertType == 'EXPIRED'}">
                        <c:set var="expiredCount" value="${expiredCount + 1}" />
                      </c:if>
                    </c:forEach>
                    <c:choose>
                      <c:when test="${expiredCount > 0}">
                        <span class="alert-message">${expiredCount}</span>
                      </c:when>
                      <c:otherwise>
                        ${expiredCount}
                      </c:otherwise>
                    </c:choose>
                  </td>
                  <td bgcolor="#FFFFFF" class="STYLE2">
                    <c:if test="${expiredCount > 0}">
                      <a href="getInventoryAlertServlet">查看详情</a>
                    </c:if>
                  </td>
                </tr>
              </table>
            </c:if>
          </div>

          <!-- 仓库列表 -->
          <div align="center">
            <h3>仓库列表</h3>
            <c:if test="${empty warehouseList}">
              <div class="info-message">暂无仓库信息</div>
            </c:if>
            <c:if test="${not empty warehouseList}">
              <table class="inventory-table">
                <tr>
                  <th background="Admin/images/tab_14.gif" class="STYLE1">仓库ID</th>
                  <th background="Admin/images/tab_14.gif" class="STYLE1">仓库编码</th>
                  <th background="Admin/images/tab_14.gif" class="STYLE1">仓库名称</th>
                  <th background="Admin/images/tab_14.gif" class="STYLE1">位置</th>
                  <th background="Admin/images/tab_14.gif" class="STYLE1">容量</th>
                  <th background="Admin/images/tab_14.gif" class="STYLE1">当前库存</th>
                </tr>
                <c:forEach var="warehouse" items="${warehouseList}">
                  <tr>
                    <td bgcolor="#FFFFFF" class="STYLE2">${warehouse.warehouseId}</td>
                    <td bgcolor="#FFFFFF" class="STYLE2">${warehouse.warehouseCode}</td>
                    <td bgcolor="#FFFFFF" class="STYLE2">${warehouse.warehouseName}</td>
                    <td bgcolor="#FFFFFF" class="STYLE2">${warehouse.location}</td>
                    <td bgcolor="#FFFFFF" class="STYLE2">${warehouse.capacity}</td>
                    <td bgcolor="#FFFFFF" class="STYLE2">${warehouse.currentStock}</td>
                  </tr>
                </c:forEach>
              </table>
            </c:if>
          </div>

          <!-- 最近入库产品 -->
          <div align="center">
            <h3>最近入库产品</h3>
            <table class="inventory-table">
              <tr>
                <th background="Admin/images/tab_14.gif" class="STYLE1">产品ID</th>
                <th background="Admin/images/tab_14.gif" class="STYLE1">产品名称</th>
                <th background="Admin/images/tab_14.gif" class="STYLE1">入库数量</th>
                <th background="Admin/images/tab_14.gif" class="STYLE1">入库时间</th>
                <th background="Admin/images/tab_14.gif" class="STYLE1">操作人</th>
              </tr>

              <c:if test="${empty recentStockIns}">
                <tr>
                  <td colspan="5" bgcolor="#FFFFFF" class="STYLE2">没有找到最近入库记录</td>
                </tr>
              </c:if>

              <c:forEach var="stockIn" items="${recentStockIns}">
                <tr>
                  <td bgcolor="#FFFFFF" class="STYLE2">${stockIn.productId}</td>
                  <td bgcolor="#FFFFFF" class="STYLE2">${stockIn.productName}</td>
                  <td bgcolor="#FFFFFF" class="STYLE2">${stockIn.quantity}</td>
                  <td bgcolor="#FFFFFF" class="STYLE2">
                    <fmt:formatDate value="${stockIn.stockInTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
                  </td>
                  <td bgcolor="#FFFFFF" class="STYLE2">${stockIn.operator}</td>
                </tr>
              </c:forEach>
            </table>
          </div>

          <!-- 最近出库产品 -->
          <div align="center">
            <h3>最近出库产品</h3>
            <table class="inventory-table">
              <tr>
                <th background="Admin/images/tab_14.gif" class="STYLE1">产品ID</th>
                <th background="Admin/images/tab_14.gif" class="STYLE1">产品名称</th>
                <th background="Admin/images/tab_14.gif" class="STYLE1">出库数量</th>
                <th background="Admin/images/tab_14.gif" class="STYLE1">出库时间</th>
                <th background="Admin/images/tab_14.gif" class="STYLE1">操作人</th>
              </tr>

              <c:if test="${empty recentStockOuts}">
                <tr>
                  <td colspan="5" bgcolor="#FFFFFF" class="STYLE2">没有找到最近出库记录</td>
                </tr>
              </c:if>

              <c:forEach var="stockOut" items="${recentStockOuts}">
                <tr>
                  <td bgcolor="#FFFFFF" class="STYLE2">${stockOut.productId}</td>
                  <td bgcolor="#FFFFFF" class="STYLE2">${stockOut.productName}</td>
                  <td bgcolor="#FFFFFF" class="STYLE2">${stockOut.quantity}</td>
                  <td bgcolor="#FFFFFF" class="STYLE2">
                    <fmt:formatDate value="${stockOut.stockOutTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
                  </td>
                  <td bgcolor="#FFFFFF" class="STYLE2">${stockOut.operator}</td>
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
              <pg:pager items="${inventoryPager.totalCount}" maxPageItems="${inventoryPager.pageSize}" export="currentPageNo = pageNumber" url="getInventorySummaryServlet">
                <pg:param name="pageSize" value="${inventoryPager.pageSize}"/>
                <pg:param name="pageNo" value="${currentPageNo}"/>
                <pg:first>
                  <a href="${pageUrl}"><img src="Admin/images/first.gif" border="0"></a>
                </pg:first>
                <pg:prev>
                  <a href="${pageUrl}"><img src="Admin/images/back.gif" border="0" /></a>
                </pg:prev>
                <pg:pages>
                  <c:choose>
                    <c:when test="${inventoryPager.pagecurrentPageNo eq pageNumber}">
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
              库存设置:
              <a href="inventorySettingsServlet">安全库存阈值: ${inventorySettings.minStockAlert}, 库存上限: ${inventorySettings.maxStockAlert}</a>
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