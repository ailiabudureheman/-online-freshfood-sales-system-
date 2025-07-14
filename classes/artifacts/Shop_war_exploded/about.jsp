<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>网站简介</title>
<link rel="stylesheet" type="text/css" href="style.css" />
</head>
<body>
<div id="wrap">

      <%@include file="header.jsp" %>
       
       <div class="center_content">
       	<div class="left_content">
       	<div class="crumb_nav">
            <a href="index.jsp">首页</a> &gt;&gt; 关于我们
            </div>
            <div class="title"><span class="title_icon"><img src="images/bullet1.gif" alt="" title="" /></span>关于我们</div>
        
        	<div class="feat_prod_box_details">
            <p class="details">
            <img src="images/house.jpg" alt="" title="" class="right" /> 
            	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;本站出售各种农产品，是您真正的菜篮子米袋子....应有尽有，只要你知道的或是听说过的，或是没听过的，我们这里都有。快点来吧，还等什么？
            	机不可失，时不再来。多买多送，少买少送，不买也送，只要你来就不会让你空手而归。
			</p>        
            </div>
            
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