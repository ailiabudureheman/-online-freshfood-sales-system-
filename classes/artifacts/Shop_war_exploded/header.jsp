<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
    <script type="text/javascript">
    	function b1(){		
		var timeId = document.getElementById("timeId");
		var x  = new Array("星期日", "星期一", "星期二","星期三","星期四", "星期五","星期六");
		var e = new Date();
		var day = e.getUTCDay();
		timeId.innerHTML = "现在是："+e.getYear()+"年"+(e.getMonth()+1)+"月"+e.getDate()+"日   " +x[day];
	}
	</script>
    <body onload="b1()">
     <div class="header">
       	<div class="logo"></div>            
        <div id="menu">
            <ul>                                                                       
            <li class="selected"><a href="getGoodsByTypeServlet">首页</a></li>
            <li><a href="page?type=0">所有商品</a></li>
            <li><a href="page?type=2">最新商品</a></li>
            <li><a href="page?type=4">推荐商品</a></li>
            <li><a href="page?type=1">热卖商品</a></li>
            <li><a href="page?type=3">特价商品</a></li>
            <li><a href="okLoggin">个人中心</a></li>
            <li><a href="pageNoteServlet">用户留言</a></li>
            <li><a href="cart.jsp">购物车</a></li>
            <li><a href="userLoginOut">注销</a></li>
            </ul>
        </div>                        
       </div> 
       <div id="serachDiv">
       	<div style="padding-left:50px">
       	 <form action="pageSearchServlet" method="post" id="searchForm">
       	 	<table>
       	 		<tr>
       				<td><input type="text" name="keywords" id="keywords" /></td>
       				<td><input type="image" src="images/search.gif" name="submit" /></td>
       				<td><img src="images/gjsearch.png" name="gjsearch" /></td>    
       				<td><a href="showSuperTypeServlet">高级搜索</a></td>
       				<td id="timeId" style="padding-left: 250px;"></td>   				
       			</tr>
       		</table>
       	 </form>
       	</div> 		
       </div>
       <!--end of leftTop content-->
      </body> 