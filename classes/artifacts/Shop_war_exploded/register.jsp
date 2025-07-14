<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>用户注册</title>
<link rel="stylesheet" type="text/css" href="style.css" />
<script type="text/javascript">
	//必须同意协议后才可注册
	function enable(){
		var terms = document.getElementById("terms");
		var register = document.getElementById("register");
		if(terms.checked){
			register.disabled=false;
		}else{
			register.disabled=true;
		}
	}
	//检查用户名是否合法,只能由英文字母、数字和下划线组成，且开头不能为数字
	function checkName(){
		var name = document.getElementById("name");
		//var pic = document.getElementById("pic");
		var checkName = document.getElementById("checkName");
		var patrn=/^[a-zA-Z_]{1}\w{5,16}$/; 
		if (!patrn.exec(name.value)){
			 checkName.style.color="red";
			 checkName.innerHTML="用户名输入有误!";
			 return false;
		}else{	
			 checkName.style.color="green";
			 checkName.innerHTML="√ 输入正确";
			 return true;
		}
	}
	//检查用户名是否存在
	var req;
	function checkNE(){
		var name = document.getElementById("name");
		if(checkName()){
			if(window.XMLHttpRequest){
				req = new XMLHttpRequest();
			}else if(window.ActiveXObject){
				req = new ActiveXObject("Microsoft.XMLHTTP");
			}
			//设置回调函数
			req.onreadystatechange = callbackForName;
			var url = "checkNameExist?name="+name.value;
			req.open("get",url,true);
			req.send(null);
			return true;
		}else{
			return false;
		}
		
	}
	function callbackForName(){
		var state = req.readyState;
		if(state==4){
			var doms = req.responseXML;
			var user = doms.getElementsByTagName("user");
			if(user[0].getElementsByTagName("name")[0].firstChild.data=="exist"){//用户名已经存在
				var name = document.getElementById("name");
				name.focus();
				name.value="";
				window.open("exist.jsp","fail","width=180,height=120,left=200,top=180");
			}else{
				window.open("notExist.jsp","success","width=180,height=120,left=200,top=180");
			}
		}
	}
	//检查密码,只能由6到15位的英文字母和数字组成
	function checkPassword(){
		var password1 = document.getElementById("password1");
		var pwdRemind = document.getElementById("pwdRemind");
		var patrn=/^[a-z,A-Z,0-9]{6,15}$/;
		if (!patrn.exec(password1.value)){
			pwdRemind.style.color="red";
			pwdRemind.innerHTML="密码格式输入有误!";
			return false;
		}else{
			pwdRemind.style.color="green";
			pwdRemind.innerHTML="√ 输入正确";
			return true;
		}
	}
	//确认密码
	function checkPwd(){
		var password1 = document.getElementById("password1");
		var password2 = document.getElementById("password2");
		var checkPwd = document.getElementById("checkPwd");
		if(password1.value!=password2.value){
			checkPwd.style.color="red";
			checkPwd.innerHTML="两次密码输入不一致!";
			return false;
		}else{
			checkPwd.style.color="green";
			checkPwd.innerHTML="两次密码输入一致!";
			return true;
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
	//校验邮政编码,必须是六位数字，且第一位不能为0
	function isPostalCode()
	{
	var postcode = document.getElementById("postcode");
	var remindPostCode = document.getElementById("remindPostCode");
	var patrn=/^[1-9]{1}(\d){5}$/;
	if (!patrn.exec(postcode.value)){
		remindPostCode.style.color="red";
		remindPostCode.innerHTML="邮政编码输入有误";
		return false;
	}else{
		remindPostCode.style.color="green";
		remindPostCode.innerHTML="√ 输入正确";
		return true
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
	//检查验证码是否正确
	function checkCode(){
		var code = document.getElementById("code");
		var codeR = document.getElementById("codeR");
		if(code.value != null){
			if(window.XMLHttpRequest){
				req = new XMLHttpRequest();
			}else if(window.ActiveXObject){
				req = new ActiveXObject("Microsoft.XMLHTTP");
			}
			//设置回调函数
			req.onreadystatechange = callbackForCode;
			var url = "checkRandomCode?code="+code.value;
			req.open("get",url,true);
			req.send(null);
			return true;
		}else{
			codeR.style.color="red";
			codeR.innerHTML="请输入验证码";
			return false;
		}
		
	}
	function callbackForCode(){
		var state = req.readyState;
		var codeR = document.getElementById("codeR");
		if(state==4){
			var doms = req.responseXML;
			var code = doms.getElementsByTagName("code");
			var message = code[0].getElementsByTagName("message")[0].firstChild.data;
			if(code[0].getElementsByTagName("message")[0].firstChild.data=="correct"){//验证码输入正确
				codeR.style.color="green";
				codeR.innerHTML="√ 输入正确";
			}else{
				codeR.style.color="red";
				codeR.innerHTML="输入错误";
			}
		}
	}
	//必须所有信息填写正确才能成功提交信息
	function checkAll(){
		document.register_form.submit();
	}
	//清楚用户名错误提示
	function clearRN(){
		var checkName = document.getElementById("checkName");
		checkName.style.color="black";
		checkName.innerHTML="英文字母或下划线(6-15)字符组成";
	}
	//清楚密码验证错误提示
	function clearPwd(){
		var checkPwd = document.getElementById("checkPwd");
		var pwdRemind = document.getElementById("pwdRemind");
		checkPwd.style.color="black";
		checkPwd.innerHTML="由英文字母和数字(6-15)字符组成";
		pwdRemind.innerHTML="";
	}
	//清除邮箱错误提示
	function clearMail(){
		var checkMail = document.getElementById("checkMail");
		checkMail.style.color="black";
		checkMail.innerHTML="请输入正确的邮箱地址";
	}
	//清除邮政编码错误提示
	function clearPostR(){
		var remindPostCode = document.getElementById("remindPostCode");
		remindPostCode.innerHTML="";
	}
	//清除固定电话错误提示
	function clearPhoneR(){
		var checkPhone = document.getElementById("checkPhone");
		checkPhone.innerHTML="";
	}
	function clearMphoneR(){
		var checkMphone = document.getElementById("checkMphone");
		checkMphone.innerHTML="";
	}
	//刷新验证码
	function refreshCode(){
		var safeCode = document.getElementById("safeCode");
		safeCode.src = "code";
	}
</script>
</head>
<body>
<div id="wrap">		
		<%@include file="header.jsp" %>    
       <div class="center_content">
       	<div class="left_content">
       		<div class="crumb_nav">
           		 <a href="index.jsp">首页</a> &gt;&gt; 注册
            </div>
            <div class="title"><span class="title_icon"><img src="images/bullet1.gif" alt="" title="" /></span>注册账号</div>
        
        	<div class="feat_prod_box_details">
            <p class="details">
            </p>
              	<div class="contact_form">
                <div class="form_subtitle">创建新账号</div>
                 <form name="register_form" action="register" method="post">          
                    <table>
                    	<tr>
                    		<td class="label">用户名:</td>
                    		<td><input type="text" id="name" name="name" onblur="checkName()" onfocus="clearRN()"/></td>
                    		<td id="checkName" class="remind"><span style="color: red">*</span><span style="color:black">由英文字母、数字或下划线(6-16)字符组成,且不能以数字开头</span></td>
                    	</tr>
                    	<tr>
                    		<td>&nbsp;</td>
                    		<td><input type="button" value="检查用户名是否存在" id="checkNameExist" onclick="checkNE()" /></td>
                    		<td>&nbsp;</td>
                    	</tr>
                    	<tr>
                    		<td class="label">密码:</td>
                    		<td><input type="password" id="password1" name="password" class="contact_input" onfocus="clearPwd()" onblur="checkPassword()" /></td>
                    		<td class="remind" id="pwdRemind"><span style="color: red">*</span><span style="color:black">由英文字母和数字(6-16)字符组成</span></td>
                    	</tr>
                    	<tr>
                    		<td class="label">确认密码:</td>
                    		<td> <input type="password" id="password2" name="password" class="contact_input" onblur="checkPwd()" /></td>
                    		<td id="checkPwd" class="remind">&nbsp;</td>
                    	</tr>
                    	<tr>
                    		<td class="label">电子邮件:</td>
                    		<td><input type="text" id="email" name="email" class="contact_input" onblur="checkMail()" onfocus="clearMail()" /></td>
                    		<td id="checkMail" class="remind"><span style="color: red">*</span><span style="color:black">请输入正确的邮箱地址，如:<span style="color: aqua">hi@qq.com</span></td>
                    	</tr>
                    	<tr>
                    		<td class="label">真实姓名:</td>
                    		<td><input type="text" id="trueName" name="trueName" class="contact_input" /></td>
                    		<td class="remind">&nbsp;</td>
                    	</tr>
                    	<tr>
                    		<td class="label">性别:</td>
                    		<td>
                    			 <input type="radio" name="sex" value="男" checked="checked"/>男
                    			 <input type="radio" name="sex"  value="女" />女
                    		</td>
                    		<td class="remind">&nbsp;</td>
                    	</tr>
                    	<tr>
                    		<td class="label">出生日期:</td>
                    		<td><input type="text" id="birthday" name="birthday" class="contact_input" /></td>
                    		<td class="remind" style="color: black">格式如：1996-10-12</td>
                    	</tr>
                    	<tr>
                    		<td class="label">地址:</td>
                    		<td><input type="text" id="address" name="address" class="contact_input" /></td>
                    		<td class="remind">&nbsp;</td>
                    	</tr>
                    	<tr>
                    		<td class="label">邮政编码:</td>
                    		<td><input type="text" id="postcode" name="postcode" class="contact_input" onblur="isPostalCode()" onfocus="clearPostR()" /></td>
                    		<td class="remind" id="remindPostCode">&nbsp;</td>
                    	</tr>
                    	<tr>
                    		<td class="label">固定电话:</td>
                    		<td><input type="text" id="phone" name="phone" class="contact_input" onblur="checkPhone()" onfocus="clearPhoneR()" /></td>
                    		<td id="checkPhone" class="remind">&nbsp;</td>
                    	</tr>
                    	<tr>
                    		<td class="label">移动电话:</td>
                    		<td><input type="text" id="mphone" name="mphone" class="contact_input" onblur="checkMPhone()" onfocus="clearMphoneR()" /></td>
                    		<td id="checkMphone" class="remind">&nbsp;</td>
                    	</tr>
                    	<tr>
                    		<td class="label">密码问题提示:</td>
                    		<td>
                    			<select id="question" name="question" class="contact_input" style="width: 155px;">
                    				<option value="empty">--请选择提示问题--</option>
                    				<option value="你最爱的人的名字叫什么">你最爱的人的名字叫什么</option>
                    				<option value="你喜欢的业余爱好是什么">你喜欢的业余爱好是什么</option>
                    				<option value="你妈妈的生日是什么时候">你妈妈的生日是什么时候</option>
                    				<option value="其他">其他</option>
                    			</select>
                    		<td class="remind">&nbsp;</td>
                    	</tr>
                    	<tr>
                    		<td class="label">问题答案:</td>
                    		<td><input type="text" id="answer" name="answer" class="contact_input" /></td>
                    		<td class="remind">&nbsp;</td>
                    	</tr>
                    	<tr>
                    		<td>验证码:</td>
                    		<td><input type="text" id="code" name="code"  style="width: 50px;" onblur="checkCode()" />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<img src="code" id="safeCode" onclick="refreshCode()" /></td>
                    		<td> <span id="codeR"></span></td>
                    	</tr>
                    	<tr>
                    		<td class="label">&nbsp;</td>
                    		<td class="terms"><input type="checkbox" id="terms" name="terms" onclick="enable()" />
                        我同意你的所有 <a href="">条款</a> </td>
                    		<td class="remind">&nbsp;</td>
                    	</tr>
                    	<tr>
                    		<td class="label">&nbsp;</td>
                    		<td>&nbsp;</td>
                    		<td class="remind">&nbsp;</td>
                    	</tr>
                    		<tr>
                    		<td class="label">&nbsp;</td>
                    		<td>&nbsp;</td>
                    		<td class="remind">&nbsp;</td>
                    	</tr>
                    	<tr>
                    		<td class="label">&nbsp;</td>
                    		<td><input type="button" id="register" name="register" class="register" value="注册" disabled="disabled" onclick="checkAll()" /></td>
                    		<td class="remind">&nbsp;</td>
                    	</tr>
                    </table> 
                  </form>     
                </div>           
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