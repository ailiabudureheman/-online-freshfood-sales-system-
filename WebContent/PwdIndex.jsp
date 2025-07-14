<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="common.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    <title>My JSP 'PwdIndex.jsp' starting page</title>

    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<link rel="stylesheet" type="text/css" href="style.css">
	<script type="text/javascript">
	//检查用户名是否合法,只能由英文字母、数字和下划线组成，且开头不能为数字
	function checkName(){
		var name = document.getElementById("name");
		var checkName = document.getElementById("checkName");
		var patrn=/^[a-zA-Z_]{1}\w{5,16}$/; 
		if (!patrn.exec(name.value)){
			 checkName.style.color="red";
			 checkName.innerHTML="用户名输入有误!";
			 return false;
		}else{	
			 checkName.style.color="green";
			 checkName.innerHTML="√ 输入正确";
			 document.f1.submit();
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
				window.open("findAnswer.jsp","fail","width=180,height=120,left=200,top=180");
			}else{
				window.open("notExist.jsp","success","width=180,height=120,left=200,top=180");
			}
		}
	}
	</script>
  </head>
  
  <body>
  	<div id="wrap">&nbsp; 
	   <%@include file="header.jsp" %>
       <div class="center_content">
       	<div class="left_content">
       		
       		<form action="findUser" method="post" name="f1">
       			<table>
       				<tr>
       					<td class="form_row"><strong> 请输入你的帐号：<br /></strong></td>
       					<td class="form_row"><input type="text" id="name" name="name" class="contact_input" onblur="checkName()" /></td>
       				</tr>
       				<tr>
       					<td>&nbsp;</td>
       					<td id="checkName">&nbsp;</td>
       				</tr>
       				<tr>
       					<td>&nbsp;</td>
       					<td class="form_row"><input type="button" class="register" value="提交" onclick="checkNE()" /></td> 
       				</tr>
       			</table>  
		    </form>
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
