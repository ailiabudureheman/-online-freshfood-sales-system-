<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="tools.jsp" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
	<base href="<%=basePath%>">
	<title>修改商品 - 企业管理系统</title>

	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">

	<!-- 引入现代化样式和图标库 -->
	<link href="Admin/css/style.css" rel="stylesheet" type="text/css">
	<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
	<script src="Admin/js/script.js" type="text/javascript"></script>
</head>

<body onload="getSuperType()">
<!-- 顶部导航栏 -->
<header class="header">
	<div class="header-content">
		<div class="logo">
			<i class="fas fa-cubes"></i>
			<span>企业管理系统</span>
		</div>
		<nav class="header-nav">
			<ul>
				<li><a href="#"><i class="fas fa-tachometer-alt"></i> 控制台</a></li>
				<li><a href="#"><i class="fas fa-bell"></i> 通知</a></li>
			</ul>
		</nav>
		<div class="user-info">
			<img src="Admin/images/avatar.png" alt="管理员头像" class="avatar">
			<div class="user-detail">
				<div class="user-name">管理员</div>
				<div class="user-role">系统管理员</div>
			</div>
			<i class="fas fa-sign-out-alt logout-btn"></i>
		</div>
	</div>
</header>

<div class="main-container">
	<!-- 侧边栏导航 -->


	<!-- 主内容区域 -->
	<main class="main-content">
		<div class="content-header">
			<div class="page-title">
				<h1>修改商品</h1>
				<p>编辑和更新商品信息</p>
			</div>
			<div class="breadcrumbs">
				<a href="#"><i class="fas fa-home"></i> 首页</a>
				<span>></span>
				<span>商品管理</span>
				<span>></span>
				<span>修改商品</span>
			</div>
		</div>

		<div class="content-container">
			<div class="card">
				<div class="card-header">
					<div class="card-title">
						<i class="fas fa-box-open"></i>
						<span>商品信息</span>
					</div>
				</div>
				<div class="card-body">
					<form id="updateGoodsForm" action="updateGoodsServlet" method="get">
						<input type="hidden" id="GoodsId" name="GoodsId" value="${Goods.goodsId}" readonly>
						<input type="hidden" id="superTypeHidden" name="superTypeId" value="${Goods.superTypeId}">
						<input type="hidden" id="subTypeHidden" name="subTypeId" value="${Goods.subTypeId}">

						<div class="form-group">
							<label for="superTypeId">
								<i class="fas fa-sitemap"></i> 选择类别
								<span class="required">*</span>
							</label>
							<div class="input-group">
								<span class="input-group-addon">大类</span>
								<select id="superTypeId" name="superTypeId" class="form-control" onchange="getSubType()">
									<option value="0">--选择大类--</option>
									<c:forEach var="superType" items="${superTypes }">
										<option value="${superType.superTypeId }">${superType.typeName }</option>
									</c:forEach>
								</select>
							</div>
							<div class="input-group mt-10">
								<span class="input-group-addon">小类</span>
								<select id="subTypeId" name="subTypeId" class="form-control">
									<option value="0">--选择小类--</option>

								</select>
							</div>
							<div class="form-error" id="typeError"></div>
						</div>

						<div class="form-group">
							<label for="GoodsName">
								<i class="fas fa-tag"></i> 商品名
								<span class="required">*</span>
							</label>
							<input type="text" id="GoodsName" name="GoodsName" class="form-control"
								   value="${Goods.goodsName}" onblur="checkGoodsName()">
							<div class="form-error" id="GoodsNameDiv"></div>
						</div>

						<div class="form-group">
							<label for="ISBN">
								<i class="fas fa-barcode"></i> 商品编码
								<span class="required">*</span>
							</label>
							<input type="text" id="ISBN" name="ISBN" class="form-control"
								   value="${Goods.ISBN}" onblur="checkISBN()">
							<div class="form-error" id="ISBNDiv"></div>
						</div>

						<div class="form-group">
							<label for="introduce">
								<i class="fas fa-file-alt"></i> 相关介绍
							</label>
							<textarea id="introduce" name="introduce" class="form-control" rows="3">${Goods.introduce}</textarea>
						</div>

						<div class="form-group">
							<label for="pages">
								<i class="fas fa-calendar-alt"></i> 生产时间
								<span class="required">*</span>
							</label>
							<input type="text" id="pages" name="pages" class="form-control"
								   value="${Goods.produceDate}" onblur="checkPages()">
							<div class="form-error" id="pagesDiv"></div>
						</div>

						<div class="form-group">
							<label for="publisher">
								<i class="fas fa-map-marker-alt"></i> 产地
								<span class="required">*</span>
							</label>
							<input type="text" id="publisher" name="publisher" class="form-control"
								   value="${Goods.publisher}" onblur="checkPublisher()">
							<div class="form-error" id="publisherDiv"></div>
						</div>

						<div class="form-group">
							<label for="author">
								<i class="fas fa-copyright"></i> 品牌
								<span class="required">*</span>
							</label>
							<input type="text" id="author" name="author" class="form-control"
								   value="${Goods.author}" onblur="checkAuthor()">
							<div class="form-error" id="authorDiv"></div>
						</div>

						<div class="form-row">
							<div class="form-group col-md-6">
								<label for="price">
									<i class="fas fa-dollar-sign"></i> 原价
									<span class="required">*</span>
								</label>
								<input type="text" id="price" name="price" class="form-control"
									   value="${Goods.price}" onblur="checkPrice()">
								<div class="form-error" id="priceDiv"></div>
							</div>
							<div class="form-group col-md-6">
								<label for="nowPrice">
									<i class="fas fa-tag"></i> 现价
									<span class="required">*</span>
								</label>
								<input type="text" id="nowPrice" name="nowPrice" class="form-control"
									   value="${Goods.nowPrice}" onblur="checkNowPrice()">
								<div class="form-error" id="nowPriceDiv"></div>
							</div>
						</div>

						<div class="form-group">
							<label for="GoodsNum">
								<i class="fas fa-boxes"></i> 数量
								<span class="required">*</span>
							</label>
							<input type="text" id="GoodsNum" name="GoodsNum" class="form-control"
								   value="${Goods.goodsNum}" onblur="checkGoodsNum()">
							<div class="form-error" id="GoodsNumDiv"></div>
						</div>

						<div class="form-group">
							<label>
								<i class="fas fa-star"></i> 类型
								<span class="required">*</span>
							</label>
							<div class="form-check-group">
								<label class="form-check">
									<input type="checkbox" name="hostGoods" value="1"
										   <c:if test="${Goods.hostGoods == 1 }">checked="checked"</c:if>>
									<span>热卖</span>
								</label>
								<label class="form-check">
									<input type="checkbox" name="newGoods" value="1"
										   <c:if test="${Goods.newGoods == 1 }">checked="checked"</c:if>>
									<span>新品</span>
								</label>
								<label class="form-check">
									<input type="checkbox" name="saleGoods" value="1"
										   <c:if test="${Goods.saleGoods == 1 }">checked="checked"</c:if>>
									<span>特价</span>
								</label>
								<label class="form-check">
									<input type="checkbox" name="specialGoods" value="1"
										   <c:if test="${Goods.specialGoods == 1 }">checked="checked"</c:if>>
									<span>特别推荐</span>
								</label>
							</div>
						</div>

						<div class="form-actions">
							<button type="button" class="btn btn-primary" onclick="updateGoods()">
								<i class="fas fa-save"></i> 保存修改
							</button>
							<button type="reset" class="btn btn-secondary">
								<i class="fas fa-undo"></i> 重置
							</button>
							<a href="getGoodsPagerServlet" target="contentIframe" class="btn btn-secondary">
								<i class="fas fa-times"></i> 取消
							</a>
						</div>

						<div class="form-message" id="messageDiv">
							${updateMessage != null ? updateMessage : ''}
						</div>
					</form>
				</div>
			</div>
		</div>
	</main>
</div>

<!-- 底部信息 -->
<footer class="footer">
	<div class="footer-content">
		<div class="copyright">
			<span class="STYLE1">copyright &copy; 2025 企业管理系统</span>
		</div>
		<div class="footer-links">
			<a href="#">使用帮助</a>
			<span>|</span>
			<a href="#">联系我们</a>
			<span>|</span>
			<a href="#">隐私政策</a>
		</div>
	</div>
</footer>
</body>
</html>