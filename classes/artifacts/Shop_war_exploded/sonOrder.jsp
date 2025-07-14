<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<jsp:directive.page import="com.mall.vo.Cart"/>
<%@include file="common.jsp" %>
<jsp:directive.page import="com.mall.vo.CartItem"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>订单详情</title>
<link rel="stylesheet" type="text/css" href="style.css" />
</head>
<body>
<div id="wrap">

        <%@include file="header.jsp" %>
       <div class="center_content">
       	<div class="left_content">
            <div class="title"><span class="title_icon"><img src="images/bullet1.gif" alt="" title="" /></span>My order</div>
            <a href="updateUsere.jsp" class="register">个人资料</a>
            <a href="updatePassword.jsp" class="register"> 修改密码</a>
			<a href="selectOrder" class="register">返回订单</a>
			<a href="index.jsp" class="register">返回首页</a>   
        	<div class="feat_prod_box_details">
            <table class="cart_table">
               	<tr class="cart_title">
                	<td>订单项</td>
                	<td>订单Id</td>
                	<td>商品Id</td>
                    <td>商品名</td>
                    <td>价格</td>  
                    <td>数量</td>             
                </tr>
                <c:forEach var="sonOr" items="${list}">
				<tr>
					<td>${sonOr.orderItemId}</td>
					<td>${sonOr.orderId}</td>
					<td>${sonOr.goodsId}</td>
					<td>${sonOr.goodsName}</td>
					<td>${sonOr.price}</td>
					<td>${sonOr.goodsNum}</td>
				</tr>
				</c:forEach>
                <tr>
                <td colspan="4" class="cart_total"><span class="red">总价:</span></td>
                <td>${totalPrice}</td>              
                </tr>             
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