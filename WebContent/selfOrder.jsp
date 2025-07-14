<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<jsp:directive.page import="com.mall.vo.Cart"/>
<%@include file="common.jsp" %>
<jsp:directive.page import="com.mall.vo.CartItem"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>个人订单查询</title>
<link rel="stylesheet" type="text/css" href="style.css" />
</head>
<body>
<div id="wrap">

        <%@include file="header.jsp" %>
       <div class="center_content">
       	<div class="left_content">
            <div class="title"><span class="title_icon"><img src="images/bullet1.gif" alt="" title="" /></span>My cart</div>   
                        <a href="updateUsere.jsp" class="register">个人资料</a>
            <a href="updatePassword.jsp" class="register"> 修改密码</a>
			<a href="selectOrder" class="register">查看订单</a>
			<a href="index.jsp" class="register">返回首页</a>   
        	<div class="feat_prod_box_details">
            <table class="cart_table">

               	<tr class="cart_title">
                	<td>订单ID</td>
                	<td>真实姓名</td>
                    <td>详细地址</td>
                    <td>邮编</td>  
                    <td>发货状态</td>
					<td>查看子订单</>
                </tr>

								<c:forEach var="order" items="${list}">
								<tr>
			                       <td>${order.orderId}</td>
		                           <td>${order.recvName}</td>
		                           <td>${order.user.address}</td>
		                           <td>${order.user.postcode}</td> 
		                           <td>${order.flagName}</td>
		                           <td><a href="son?id=${order.orderId}">查看</a></td>
								</tr>
								</c:forEach>
           
            </table>

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