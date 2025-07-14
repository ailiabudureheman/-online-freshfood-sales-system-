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

  <title>每日到货计划</title>

  <meta http-equiv="pragma" content="no-cache">
  <meta http-equiv="cache-control" content="no-cache">
  <meta http-equiv="expires" content="0">
  <meta http-equiv="keywords" content="每日到货,库存管理,供应商">
  <meta http-equiv="description" content="每日到货计划管理页面">

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
    .storage-btn {
      color: #0066cc;
      cursor: pointer;
    }
  </style>
  <script type="text/javascript">
    function searchArrivals() {
      var date = document.getElementById("date").value;
      var supplier = document.getElementById("supplier").value;

      var oform = document.getElementsByTagName("form")[0];
      oform.action = "getDailyArrivalServlet?date=" + date + "&supplier=" + supplier;
      oform.submit();
    }

    function viewArrival(arrivalId) {
      window.location.href = "viewDailyArrivalServlet?arrivalId=" + arrivalId;
    }

    function processStorage(arrivalId) {
      if (!confirm("确定要将此订单入库吗？")) {
        return false;
      }
      window.location.href = "processStorageServlet?arrivalId=" + arrivalId;
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
            日期:
            <input type="text" id="date" name="date" value="${param.date}">

            供应商:
            <select id="supplier" name="supplier">
              <option value="">所有供应商</option>
              <c:forEach var="supplier" items="${supplierList}">
                <option value="${supplier.supplierId}" ${param.supplier == supplier.supplierId ? 'selected' : ''}>
                    ${supplier.supplierName}
                </option>
              </c:forEach>
            </select>

            <input type="button" value="查询" onclick="searchArrivals()">
            <div id="searchDiv" style="display: inline"></div>
          </div>

          <table width="99%" border="0" align="center" cellpadding="0" cellspacing="1" bgcolor="#c0de98">
            <tr>
              <th background="Admin/images/tab_14.gif" class="STYLE1">到货ID</th>
              <th background="Admin/images/tab_14.gif" class="STYLE1">到货日期</th>
              <th background="Admin/images/tab_14.gif" class="STYLE1">供应商</th>
              <th background="Admin/images/tab_14.gif" class="STYLE1">商品名称</th>
              <th background="Admin/images/tab_14.gif" class="STYLE1">数量</th>
              <th background="Admin/images/tab_14.gif" class="STYLE1">状态</th>
              <th background="Admin/images/tab_14.gif" class="STYLE1">操作</th>
            </tr>

            <c:if test="${empty dailyArrivalList}">
              <tr>
                <td colspan="7" bgcolor="#FFFFFF" class="STYLE2">没有找到符合条件的到货记录</td>
              </tr>
            </c:if>

            <c:forEach var="arrival" items="${dailyArrivalList}">
              <tr>
                <td bgcolor="#FFFFFF" class="STYLE2">${arrival.arrivalId}</td>
                <td bgcolor="#FFFFFF" class="STYLE2">${arrival.arrivalDate}</td>
                <td bgcolor="#FFFFFF" class="STYLE2">${arrival.supplierName}</td>
                <td bgcolor="#FFFFFF" class="STYLE2">${arrival.productName}</td>
                <td bgcolor="#FFFFFF" class="STYLE2">${arrival.quantity}</td>
                <td bgcolor="#FFFFFF" class="STYLE2">
                  <c:choose>
                    <c:when test="${arrival.status == '已发货'}">
                      <span class="status-pending">已发货</span>
                    </c:when>
                    <c:when test="${arrival.status == '已完成'}">
                      <span class="status-completed">已完成</span>
                    </c:when>
                    <c:otherwise>
                      <span class="status-cancelled">${arrival.status}</span>
                    </c:otherwise>
                  </c:choose>
                </td>
                <td bgcolor="#FFFFFF" class="STYLE2">
                  <a href="javascript:viewArrival(${arrival.arrivalId})">查看</a>
                  <c:if test="${arrival.storageStatus == '待入库'}">
                    | <span class="storage-btn" onclick="processStorage(${arrival.arrivalId})">入库</span>
                  </c:if>
                  <c:if test="${arrival.storageStatus == '已入库'}">
                    | <span style="color: green">已入库</span>
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
            <td>
              <pg:pager items="${dailyArrivalPager.totalCount}" maxPageItems="${dailyArrivalPager.pageSize}" export="currentPageNo = pageNumber" url="getDailyArrivalServlet">
                <pg:param name="pageSize" value="${dailyArrivalPager.pageSize}"/>
                <pg:param name="pageNo" value="${currentPageNo}"/>
                <pg:param name="date" value="${param.date}"/>
                <pg:param name="supplier" value="${param.supplier}"/>
                <pg:first>
                  <a href="${pageUrl}"><img src="Admin/images/first.gif" border="0"></a>
                </pg:first>
                <pg:prev>
                  <a href="${pageUrl}"><img src="Admin/images/back.gif" border="0" /></a>
                </pg:prev>
                <pg:pages>
                  <c:choose>
                    <c:when test="${dailyArrivalPager.pagecurrentPageNo eq pageNumber}">
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
              共 ${dailyArrivalPager.totalCount} 条记录，当前第 ${dailyArrivalPager.pagecurrentPageNo} 页
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