<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>My JSP 'answerFail.jsp' starting page</title>

	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">

	<link rel="stylesheet" type="text/css" href="style.css">

  </head>
  
  <body>
      	  	<div id="wrap">
		<%@include file="header.jsp" %>
       <div class="center_content">
       	<div class="left_content">
       		你输入的答案不正确！3 秒钟后自动跳转到主页面！
       		<%
       			response.setHeader("refresh","3;url=index.jsp");
       		 %>
        <div class="clear"></div>
        </div><!--end of left content-->
        <%@include file="right.jsp" %>
        <!--end of right content-->
  
       <div class="clear"></div>
       </div><!--end of center content-->
        <%@include file="footer.jsp" %>  
</div>
  </body>
</html>
