<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="common.jsp" %>
<%@include file="common.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>交易成功</title>
<link rel="stylesheet" type="text/css" href="style.css" />

</head>
<body>
<div id="wrap">
		
		<%@include file="header.jsp" %>
       <div class="center_content">
       	<div class="left_content">
       	<pre>
        	交易成功
       	     订单流水号为：<font color="red">${order.orderId}</font>	
       	     收货人：<font color="red">${order.recvName}</font>
              收货地址：<font color="red">${user.address }</font>
             邮政编码： <font color="red">${user.postcode }</font>
             email:<font color="red">${user.email}</font>
       	     联系电话：<font color="red">${user.phone }</font>
       	     手机：<font color="red">${user.mphone }</font>
       	 <%
       	 	out.print("5秒后页面将自动跳转到首页....");
       	 	response.setHeader("refresh","5;url='index.jsp'");
       	  %>
       	 </pre>
 		</div><!--end of left content-->
        <%@include file="right.jsp" %> 
       <!--end of right content-->
   
       <div class="clear"></div>
       </div><!--end of center content-->
       
       <%@include file="footer.jsp"%>
</div>

</body>
</html>