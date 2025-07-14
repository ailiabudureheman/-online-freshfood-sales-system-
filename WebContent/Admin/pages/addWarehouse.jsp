<%--
  Created by IntelliJ IDEA.
  User: 艾力
  Date: 2025/6/12
  Time: 16:22
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

  <title>添加仓库</title>

  <meta http-equiv="pragma" content="no-cache">
  <meta http-equiv="cache-control" content="no-cache">
  <meta http-equiv="expires" content="0">
  <meta http-equiv="keywords" content="添加仓库,仓库管理">
  <meta http-equiv="description" content="添加仓库页面">

  <link rel="stylesheet" type="text/css" href="Admin/css/body.css">
  <link rel="stylesheet" type="text/css" href="Admin/css/td_fontSize.css">
  <link rel="stylesheet" type="text/css" href="Admin/css/tab.css">
  <style type="text/css">
    table {
      text-align: center;
      margin: 0px auto;
      padding: 0px;
    }
    .form-group {
      margin-bottom: 15px;
    }
    .form-label {
      display: inline-block;
      width: 120px;
      text-align: right;
      margin-right: 10px;
    }
    .error-message {
      color: red;
      font-weight: bold;
      margin-bottom: 15px;
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
          <div align="center">
            <h3>添加仓库</h3>

            <!-- 错误消息显示 -->
            <c:if test="${not empty errorMessage}">
              <div class="error-message">${errorMessage}</div>
            </c:if>
            <c:if test="${not empty successMessage}">
              <div class="success-message">${successMessage}</div>
            </c:if>

            <form action="addWarehouseServlet" method="post">
              <table width="400" border="0" align="center" cellpadding="0" cellspacing="1" bgcolor="#c0de98">
                <tr>
                  <td colspan="2" background="Admin/images/tab_14.gif" class="STYLE1">仓库信息</td>
                </tr>
                <tr>
                  <td width="130" bgcolor="#FFFFFF" class="STYLE2">
                    <div align="right">仓库编码:</div>
                  </td>
                  <td width="270" bgcolor="#FFFFFF" class="STYLE2">
                    <input type="text" name="warehouseCode" maxlength="20" required>
                  </td>
                </tr>
                <tr>
                  <td bgcolor="#FFFFFF" class="STYLE2">
                    <div align="right">仓库名称:</div>
                  </td>
                  <td bgcolor="#FFFFFF" class="STYLE2">
                    <input type="text" name="warehouseName" maxlength="50" required>
                  </td>
                </tr>
                <tr>
                  <td bgcolor="#FFFFFF" class="STYLE2">
                    <div align="right">位置:</div>
                  </td>
                  <td bgcolor="#FFFFFF" class="STYLE2">
                    <input type="text" name="location" maxlength="100">
                  </td>
                </tr>
                <tr>
                  <td bgcolor="#FFFFFF" class="STYLE2">
                    <div align="right">容量:</div>
                  </td>
                  <td bgcolor="#FFFFFF" class="STYLE2">
                    <input type="number" name="capacity" min="0">
                  </td>
                </tr>
                <tr>
                  <td bgcolor="#FFFFFF" class="STYLE2">
                    <div align="right">状态:</div>
                  </td>
                  <td bgcolor="#FFFFFF" class="STYLE2">
                    <input type="radio" name="status" value="1" checked> 启用
                    <input type="radio" name="status" value="0"> 禁用
                  </td>
                </tr>
                <tr>
                  <td colspan="2" bgcolor="#FFFFFF" class="STYLE2" align="center">
                    <input type="submit" value="提交">
                    <input type="reset" value="重置">
                    <input type="button" value="返回" onclick="window.location.href='getWarehouseListServlet'">
                  </td>
                </tr>
              </table>
            </form>
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