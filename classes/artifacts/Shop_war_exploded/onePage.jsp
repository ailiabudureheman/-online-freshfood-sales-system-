<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>个人信息管理</title>
<link rel="stylesheet" type="text/css" href="style.css" />
</head>
<body>
<div id="wrap">&nbsp; 
 
	   <!-- header -->
	   <%@include file="header.jsp" %>
       <div class="center_content">
       	<div class="left_content">
            <div class="title"><span class="title_icon"><img src="images/bullet1.gif" alt="" title="" /></span>个人信息管理中心</div>
        
        	<div class="feat_prod_box_details">
			        <div class="form_row">
                    <a href="updateUsere.jsp" class="contact">修改资料</a>
                    <a href="updatePassword.jsp" class="contact">修改密码</a> 
                    <a href="selectOrder" class="contact">查看订单</a>    
                    <a href="index.jsp" class="contact">首页</a> 
                     </div>        
            </p>
            
              	<div class="contact_form">
                <div class="form_subtitle">您的个人资料</div>          
                    <div class="form_row">
                    <label class="contact"><strong>积分:</strong></label>
					<label class="contact"><strong>${user.score}</strong></label>
                    </div>  

                    <div class="form_row">
                    <label class="contact"><strong>用户名:</strong></label>
                    <label class="contact"><strong>${user.name}</strong></label>
                    </div>


                    <div class="form_row">
                    <label class="contact"><strong>E—mail:</strong></label>
                    <label class="contact"><strong>${user.email}</strong></label>
                    </div>
                    
                    <div class="form_row">
                    <label class="contact"><strong>真名:</strong></label>
                    <label class="contact"><strong>${user.trueName}</strong></label>
                    </div>


                    <div class="form_row">
                    <label class="contact"><strong>性别:</strong></label>
                    <label class="contact"><strong>${user.sex}</strong></label>
                    </div>
                    
                    <div class="form_row">
                    <label class="contact"><strong>生日:</strong></label>
                    <label class="contact"><strong>${user.birthday}</strong></label>
                    </div>
                    
                    <div class="form_row">
                    <label class="contact"><strong>地址:</strong></label>
                    <label class="contact"><strong>${user.address}</strong></label>
                    </div>
                    
                    <div class="form_row">
                    <label class="contact"><strong>邮编:</strong></label>
                    <label class="contact"><strong>${user.postcode}</strong></label>
                    </div>
                    
                    <div class="form_row">
                    <label class="contact"><strong>座机:</strong></label>
                    <label class="contact"><strong>${user.phone}</strong></label>
                    </div>

                    <div class="form_row">
                    <label class="contact"><strong>手机:</strong></label>
                    <label class="contact"><strong>${user.mphone}</strong></label>
                    </div>
                    
                    <div class="form_row">
                    <label class="contact"><strong>安全问题:</strong></label>
                    <label class="contact"><strong>${user.question}</strong></label>
                    </div>     
                </div>  
            
          </div>	
     
        <div class="clear"></div>
        </div><!--end of left content-->
        <%@include file="right.jsp" %> 
       <div class="clear"></div>
       </div><!--end of center content-->
       <%@include file="footer.jsp"%>  

</div>

</body>
</html>