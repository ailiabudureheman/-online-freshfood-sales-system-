<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="common.jsp"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    <base href="<%=basePath%>">
    
    <title>商品高级搜索</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link rel="stylesheet" type="text/css" href="style.css" />
  <script type="text/javascript">
  	var req;
		function selectedSuper(){
			if(window.XMLHttpRequest){//Mozillia
				req = new XMLHttpRequest();
			}else if(window.ActiveXObject){//IE
				req = new ActiveXObject("Microsoft.XMLHTTP");
			}
			//设置回调函数
			req.onreadystatechange = callback;
			var superTypeId = document.getElementById("superType").value;
			var url = "showSubTypeServlet?superTypeId="+superTypeId;
			req.open("get",url,true);
  			var content = document.getElementById("content");
  			content.style.visibility = "hidden";
			req.send(null);
		}
		function clearSubType(){
			var subT = document.getElementById("subT");
			subT.length=0;
		}
		function callback(){
			var state = req.readyState;
			var subT = document.getElementById("subT");
			if(state==4 && req.status==200){
			clearSubType();
			var dom = req.responseXML;
			var subType = dom.getElementsByTagName("subType");
			for(i=0;i<subType.length;i++){
				var subTypeId = subType[i].getElementsByTagName("subTypeId")[0];
				var subTypeName = subType[i].getElementsByTagName("subTypeName")[0];
				var subValue = subTypeId.firstChild.data;
				var subText = subTypeName.firstChild.data;
				subT[i] = new Option(subText,subValue);
			}
			}
		}
  	function getSM(){
  		var searchMethod = document.getElementById("searchMethod").value;
  		if(searchMethod=="noChoice"){
  			alert("请选择查询方式");
  		}else {
  			var content = document.getElementById("content");
  			content.style.visibility = "visible";
  		}
  	}

	function checkDetails(){
		var superType = document.getElementById("superType");
		var subT = document.getElementById("subT");
		var searchMethod = document.getElementById("searchMethod");
		var scontent= document.getElementById("content").value;
		if(superType.value != "noChoice" && subT.value != "noChoice" && searchMethod.value != "noChoice" && scontent!=null && scontent!=""){
		    var content = document.getElementById("content");
			if(content.value != "noChoice"){
				document.detailSearch.submit();
			}
		}else {
			alert("请选择好你的查询条件或查询输入框不能为空！");
		}
	}
  </script>
  </head>
  
  <body>
    		<div id="wrap"> 
		 
			<!-- the begin of header -->
			<%@include file="header.jsp"%>
			<!-- the end of header -->
			
			<!-- the begin of content -->
			<div class="center_content">
				<!-- the begin of left -->
					<div>
						<div>高级搜索</div>
						<div>
						<form action="detailSearch" method="post" name="detailSearch">
                        <div class="feat_prod_box_details">
                         <p class="details"></p>
              	         <div class="contact_form">
                         <div class="form_subtitle">高级搜索</div>       
                          <table>
								<tr>
									<td>所属大类：</td>
									<td>
										<select id="superType" name="superType" onchange="selectedSuper()">
											<option value="noChoice">--请选择大类--</option>
											<c:forEach var="flag" items="${superList}">
						  					<option value="${flag.superTypeId}">${flag.typeName}</option>
						  					</c:forEach>
										</select>
									</td>
								</tr>
								<tr>
									<td>所属小类：</td>
									<td>
										<select id="subT" name="subT">
											<option value="noChoice">--请选择小类--</option>
										</select>
									</td>
								</tr>
								<tr>
									<td>查询方式：</td>
									<td>
										<select name="searchMethod" id="searchMethod" onchange="getSM()">
											<option value="noChoice">--请选择查询方式--</option>
											<option value="GoodsName">按商品名查询</option>
											<option value="author">按品牌查询</option>
											<option value="publisher">按产地查询</option>
											<option value="ISBN">按编码查询</option>
										</select>
									</td>
									<td><input id="content" type="text" name="content"  style="visibility: hidden;"/></td>
								</tr>
								
								<tr>
								<td>&nbsp;</td>	
									<td><input type="button" value="提交" onclick="checkDetails()" /></td>
								</tr>
							</table>
                  </form>     
                </div>           
          </div>	       
        <div class="clear"></div>
						</form>
						</div>
					</div>
				<div class="clear"></div>
			</div>
			
			
			<!--end of center content-->
			<%@include file="footer.jsp"%>
		</div>
  </body>
</html>
