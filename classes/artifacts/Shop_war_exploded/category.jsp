<%@ page language="java" import="java.util.*" pageEncoding="GB18030"%>
<%@include file="common.jsp"%>
<%@taglib uri="http://jsptags.com/tags/navigation/pager" prefix="pg"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>商品库</title>
<link rel="stylesheet" type="text/css" href="style.css" />

</head>
<body>
<div id="wrap">&nbsp; 
 
      <%@include file="header.jsp" %>
       <div class="center_content">
       	<div class="left_content">
            <div class="title">
            	<span class="title_icon"><img src="images/bullet1.gif" alt="" title="" /></span>
            	<c:if test="${type == 0}">所有商品</c:if>
            	<c:if test="${type == 1}">热卖商品</c:if>
            	<c:if test="${type == 2}">最新商品</c:if>
            	<c:if test="${type == 3}">特价商品</c:if>
            	<c:if test="${type == 4}">推荐商品</c:if>
            	<c:if test="${type == 9}">分类查看</c:if>
            </div>
        <c:choose>
    		<c:when test="${empty page.pageList}">
    			<h1><font color="red">暂没有商品</font></h1>
    		</c:when>
    		<c:otherwise>
           	 <div class="new_products">
           		<c:forEach var="Goods" items="${page.pageList}">
                    <div class="new_prod_box">
                        <div class="new_prod_bg">
                        <a href="showGoodsByIdServlet?GoodsId=${Goods.goodsId}">
                        	<img src="${Goods.picture}" alt="" class="thumb" border="0" />
                        </a>
                        <a href="showGoodsByIdServlet?GoodsId=${Goods.goodsId}">${Goods.goodsName }</a>
                        </div>
                    </div>
                </c:forEach>
              </div>
			</c:otherwise>
		</c:choose>
		 <div class="pagination">
            <span class="disabled">

     <pg:pager items="${page.totalNum}" url="page?type=${type}" maxPageItems="6" maxIndexPages="5">
    	<pg:first>
    		<a href="${pageUrl}&sid=${sid}">首页</a>
    	</pg:first>
    	<pg:prev>
    		<a href="${pageUrl}&sid=${sid}">前页</a>
    	</pg:prev>
    	<pg:pages>
    		<a href="${pageUrl}&sid=${sid}">${pageNumber}</a>
    	</pg:pages>
    	<pg:next>
    		<a href="${pageUrl}&sid=${sid}">后页</a>
    	</pg:next>
    	<pg:last>
    		<a href="${pageUrl}&sid=${sid}">尾页</a>
    	</pg:last>
    </pg:pager> 
    </span>
       </div>     
     </div> 
          
            
        <div class="clear"></div>
        </div><!--end of left content-->

        <!--end of right content-->
        
        
       
       
       <div class="clear"></div>
       </div><!--end of center content-->
        <%@include file="footer.jsp" %> 
                
</body>
</html>