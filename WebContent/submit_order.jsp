<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<jsp:directive.page import="com.mall.vo.Cart"/>
<jsp:directive.page import="com.mall.vo.CartItem"/>
<%@include file="common.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>填写收货人信息资料</title>
<link rel="stylesheet" type="text/css" href="style.css" />
<script type="text/javascript">
	//验证收货人姓名不能为空
	function checkRN(){
		var recvName = document.getElementById("recvName");
		if(recvName.value==""){
			var remindRN = document.getElementById("remindRN");
			remindRN.style.color="red";
			remindRN.innerHTML="收货人姓名不能为空";
			return false;
		}else{
			var remindRN = document.getElementById("remindRN");
			remindRN.style.color="green";
			remindRN.innerHTML="√ 输入正确";
			return true;
		}
	}
	
	//验证收货人地址不能为空
	function checkAddr(){
		var address = document.getElementById("address");
		if(address.value==""){
			var remindAddr = document.getElementById("remindAddr");
			remindAddr.style.color="red";
			remindAddr.innerHTML="收货人姓名不能为空";
			return false;
		}else{
			var remindAddr = document.getElementById("remindAddr");
			remindAddr.style.color="green";
			remindAddr.innerHTML="√ 输入正确";
			return true;
		}
	}
	//校验邮政编码,必须是六位数字，且第一位不能为0
	function isPostalCode()
	{
	var postCode = document.getElementById("postcode");
	var remindPostCode = document.getElementById("remindPostCode");
	var patrn=/^[1-9]{1}(\d){5}$/;
	if (!patrn.exec(postCode.value)){
		remindPostCode.style.color="red";
		remindPostCode.innerHTML="邮政编码输入有误";
		return false;
	}else{
		remindPostCode.style.color="green";
		remindPostCode.innerHTML="√ 输入正确";
		return true
	}
	}
	//验证邮箱格式
	function checkMail(){
		var email = document.getElementById("email");
		var patrn=/((\w)|[-]|[.])+@(((\w)|[-])+[.])+[a-z]{2,4}$/;
		var checkMail = document.getElementById("checkMail");
		if(!patrn.exec(email.value)){
			checkMail.style.color="red";
			checkMail.innerHTML="邮箱格式输入有误!";
			return false;
		}else{
			checkMail.style.color="green";
			checkMail.innerHTML="√ 邮箱输入正确!";
			return true;
		}
	}
	
	//校验固定电话号码
	function checkPhone(){
		var phone = document.getElementById("phone");
		var checkPhone = document.getElementById("checkPhone");
		var patrn=/^[+]{0,1}\d{3,5}-?\d{5,9}$/;
		if (!patrn.exec(phone.value)){
			checkPhone.style.color="red";
			checkPhone.innerHTML="电话格式不正确";
			return false;
		}else{
			checkPhone.style.color="green";
			checkPhone.innerHTML="√ 输入正确";
			return true;
		}
	}
	//校验移动电话号码
	function checkMPhone(){
		var phone = document.getElementById("mphone");
		var checkMphone = document.getElementById("checkMphone");
		var patrn=/^1[356]\d{9}|01[356]\d{9}$/;
		if (!patrn.exec(phone.value)){
			checkMphone.style.color="red";
			checkMphone.innerHTML="手机格式不正确";
			return false;
		}else{
			checkMphone.style.color="green";
			checkMphone.innerHTML="√ 输入正确";
			return true;
		}
	}
	
	//所有信息正确才可以提交
	function checkAll(){
		if(checkRN()&& checkAddr() && isPostalCode() && checkPhone() && checkMPhone()){
			document.submitOrder.submit();		
		}else{
			alert("请正确填写收货人信息!!");
		}
	}
	function submit_order(){
		if(checkAll()){
			
		}
	}
</script>
</head>
<body>

<div id="wrap">
		
		<%@include file="header.jsp" %>
       <div class="center_content">
       	<div class="left_content">
        	<form name="submitOrder" action="addOrderServlet" method="post">     
        		<table>
        			<tr>
                    	<td class="label"><strong>用户:</strong></td>
                    	<td><font style="color: red">${user.name}</font></td>
                    	<td class="remind">&nbsp;</td>
                    </tr>
                    <tr>
                    	<td class="label"><strong>收货人姓名:</strong></td>
                    	<td><input type="text" id="recvName" name="recvName" class="contact_input" onblur="checkRN()" value="${user.name}"/></td>
                    	<td class="remind" id="remindRN">&nbsp;</td>
                    </tr>
                    <tr>
                    	<td class="label"><strong>收货地址:</strong></td>
                    	<td><input type="text" id="address" name="address" class="contact_input" onblur="checkAddr()" value="${user.address}"/></td>
                    	<td class="remind" id="remindAddr">&nbsp;</td>
                    </tr>
                    <tr>
                    	<td class="label"><strong>邮政编码:</strong></td>
                    	<td><input type="text" id="postcode" name="postcode" class="contact_input" onblur="isPostalCode()" value="${user.postcode}"/></td>
                    	<td class="remind" id="remindPostCode">&nbsp;</td>
                    </tr>
                      <tr>
                    	<td class="label"><strong>email:</strong></td>
                    	<td><input type="text" id="email" name="email" class="contact_input" onblur="checkMail()" value="${user.email}"/></td>
                    	<td class="remind" id="checkMail">&nbsp;</td>
                    </tr>
                     <tr>
                    	<td class="label"><strong>固定电话:</strong></td>
                    	<td><input type="text" id="phone" name="phone" class="contact_input" onblur="checkPhone()" value="${user.phone}" /></td>
                    	<td class="remind" id="checkPhone">&nbsp;</td>
                    </tr>
                     <tr>
                    	<td class="label"><strong>移动电话:</strong></td>
                    	<td><input type="text" id="mphone" name="mphone" class="contact_input" onblur="checkMPhone()" value="${user.mphone}"/></td>
                    	<td class="remind" id="checkMphone">&nbsp;</td>
                    </tr>
        		</table>	     
			<div class="feat_prod_box_details">
            <table class="cart_table">
               	<tr class="cart_title">
                	<td>商品编码</td>
                	<td>商品名</td>
                    <td>单价</td>
                    <td>数量</td>
                    <td>总价</td>           
                </tr>
				<%
					Collection ci = (Collection)request.getSession().getAttribute("ci");
					
						Iterator<CartItem> it= ci.iterator();
						while(it.hasNext()){
							CartItem cartItem = it.next();
							%>
								<tr>
									<td><%=cartItem.getGoods().getISBN()%></td>
									<td><%=cartItem.getGoods().getGoodsName() %></td>
									<td><%=cartItem.getGoods().getNowPrice()%></td>
									<td><%=cartItem.getCount()%></td>
									<td><%=cartItem.getItemPrice() %></td>
								</tr>
							<%
						}
				 %>  
                <tr>
                <td colspan="4" class="cart_total"><span class="red">总价:</span></td>
                	<%
					Cart c = (Cart)session.getAttribute("cart");
					if(c != null){
    	 	   			%>
    	 	   			  <td><%=c.getTotalPrice()%></td>
    	 	   			<%
    	 	  	   }
					%>              
                </tr>             
            </table>
            <a href="cart.jsp" class="continue">&lt; 返回购物车</a>
            <span class="submit_order"><input type="button" id="subOrder" name="subOrder" class="register" value="下订单" onclick="checkAll()" />&lgt;</span>
            </div>	   
        </form>  
 		</div><!--end of left content-->
        <%@include file="right.jsp" %> 
       <!--end of right content-->
   
       <div class="clear"></div>
       </div><!--end of center content-->
       
       <%@include file="footer.jsp"%>
</div>

</body>
</html>