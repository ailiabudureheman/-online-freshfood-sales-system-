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
	<title>商品管理 - 企业管理系统</title>

	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">

	<!-- 引入现代化样式和图标库 -->
	<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
	<style>
		/* 基础样式重置 */
		* { margin: 0; padding: 0; box-sizing: border-box; }
		body { font-family: "Microsoft YaHei", Arial, sans-serif; color: #333; line-height: 1.6; }
		a { text-decoration: none; color: inherit; }
		ul { list-style: none; }

		/* 顶部导航 */
		.header {
			background-color: #1a56db;
			color: #fff;
			box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
			padding: 0 20px;
			height: 50px;
			display: flex;
			align-items: center;
			justify-content: space-between;
		}
		.header-left { display: flex; align-items: center; }
		.logo { display: flex; align-items: center; font-size: 16px; font-weight: bold; }
		.logo i { margin-right: 10px; font-size: 20px; }
		.header-right { display: flex; align-items: center; }
		.user-info { display: flex; align-items: center; cursor: pointer; margin-left: 20px; }
		.avatar { width: 30px; height: 30px; border-radius: 50%; margin-right: 10px; object-fit: cover; }

		/* 主内容区 */
		.main-content { padding: 20px; }
		.content-header { margin-bottom: 20px; padding-bottom: 10px; border-bottom: 1px solid #e2e8f0; }
		.page-title { margin-bottom: 5px; }
		.page-title h1 { font-size: 20px; font-weight: 600; color: #1a202c; }
		.breadcrumbs { display: flex; align-items: center; color: #718096; font-size: 12px; }
		.breadcrumbs a { color: #4a5568; transition: color 0.3s; }
		.breadcrumbs a:hover { color: #1a56db; }
		.breadcrumbs span { margin: 0 5px; }

		/* 卡片样式 */
		.card { background-color: #fff; border-radius: 8px; box-shadow: 0 4px 6px rgba(0, 0, 0, 0.05); overflow: hidden; margin-bottom: 20px; }
		.card-header { display: flex; justify-content: space-between; align-items: center; padding: 10px 15px; background-color: #f7fafc; border-bottom: 1px solid #e2e8f0; }
		.card-title { display: flex; align-items: center; font-size: 14px; font-weight: 600; color: #1a202c; }
		.card-title i { margin-right: 8px; color: #1a56db; }
		.card-actions { display: flex; gap: 8px; }
		.btn { display: inline-flex; align-items: center; justify-content: center; padding: 4px 10px; border-radius: 4px; font-size: 12px; font-weight: 500; transition: all 0.3s; cursor: pointer; border: none; }
		.btn-danger { background-color: #ef4444; color: #fff; }
		.btn-danger:hover { background-color: #dc2626; }
		.btn-primary { background-color: #3b82f6; color: #fff; }
		.btn-primary:hover { background-color: #2563eb; }
		.btn i { margin-right: 4px; }

		/* 搜索区域 */
		.search-area { display: flex; align-items: center; margin-bottom: 15px; gap: 10px; flex-wrap: wrap; }
		.search-area input[type="text"] { padding: 6px 12px; border: 1px solid #e2e8f0; border-radius: 4px; font-size: 14px; flex: 1; min-width: 200px; }
		.search-area button { padding: 6px 16px; background-color: #3b82f6; color: #fff; border: none; border-radius: 4px; font-size: 14px; cursor: pointer; transition: background-color 0.3s; }
		.search-area button:hover { background-color: #2563eb; }
		.search-error { color: #ef4444; font-size: 14px; margin-left: 10px;}

		/* 表格样式 */
		.table-container { overflow-x: auto; }
		.table { width: 100%; border-collapse: collapse; }
		.table th, .table td { padding: 8px 10px; text-align: left; border-bottom: 1px solid #e2e8f0; }
		.table th { background-color: #f7fafc; font-weight: 600; color: #1a202c; text-align: center; font-size: 13px; }
		.table-striped tbody tr:nth-of-type(odd) { background-color: #f8fafc; }
		.table-hover tbody tr:hover { background-color: #ebf8ff; transition: background-color 0.3s; }
		.table td { font-size: 12px; }

		/* 商品介绍预览 */
		.preview { max-height: 100px; overflow-y: auto; padding: 8px; border: 1px solid #e2e8f0; border-radius: 4px; text-align: left; background-color: #f8fafc; font-size: 12px; }

		/* 商品状态标签 */
		.status-tag { display: inline-block; padding: 2px 6px; border-radius: 4px; font-size: 10px; font-weight: 500; margin-right: 3px; }
		.status-new { background-color: #bbf7d0; color: #166534; }
		.status-sale { background-color: #fef3c7; color: #92400e; }
		.status-hot { background-color: #fecaca; color: #b91c1c; }

		/* 分页样式 */
		.pagination { display: flex; justify-content: center; margin-top: 15px; font-size: 0; }
		.page-link { display: inline-block; width: 30px; height: 30px; line-height: 30px; text-align: center; margin: 0 3px; border-radius: 4px; font-size: 12px; color: #4a5568; background-color: #fff; border: 1px solid #e2e8f0; transition: all 0.3s; }
		.page-link:hover { background-color: #edf2f7; border-color: #cbd5e0; color: #1a56db; }
		.page-link.active { background-color: #1a56db; color: #fff; border-color: #1a56db; }
		.page-link i { font-size: 12px; }

		/* 响应式设计 */
		@media (max-width: 768px) {
			.header { height: 45px; padding: 0 10px; }
			.logo { font-size: 14px; }
			.page-title h1 { font-size: 18px; }
			.card-header { padding: 8px 12px; }
			.card-title { font-size: 13px; }
			.search-area input[type="text"] { min-width: 150px; }
			.table th, .table td { padding: 6px 8px; font-size: 11px; }
			.page-link { width: 26px; height: 26px; line-height: 26px; font-size: 10px; }
			.preview { max-height: 80px; }
		}
	</style>
</head>
<body>
<!-- 简化的顶部导航 -->
<header class="header">
	<div class="header-left">
		<div class="logo">
			<i class="fas fa-box-open"></i>
			<span>商品管理</span>
		</div>
	</div>
	<div class="header-right">
		<div class="user-info">
			<img src="Admin/images/avatar.png" alt="管理员头像" class="avatar">
			<div class="user-detail">
				<div class="user-name">管理员</div>
			</div>
		</div>
	</div>
</header>

<div class="main-content">
	<div class="content-header">
		<div class="page-title">
			<h1>商品管理</h1>
			<p>查看和管理系统商品</p>
		</div>
		<div class="breadcrumbs">
			<a href="#"><i class="fas fa-home"></i> 首页</a>
			<span>></span>
			<span>商品管理</span>
		</div>
	</div>

	<div class="card">
		<div class="card-header">
			<div class="card-title">
				<i class="fas fa-archive"></i>
				<span>商品列表</span>
			</div>
			<div class="card-actions">
				<button type="button" class="btn btn-danger" onclick="removeGoods(${GoodsPager.pageOffset}, ${GoodsPager.pageSize})">
					<i class="fas fa-trash"></i> 删除选中
				</button>
			</div>
		</div>

		<!-- 搜索区域 -->
		<div class="card-body">
			<div class="search-area">
				<span>按商品名查询:</span>
				<input type="text" id="GoodsName" name="GoodsName" placeholder="输入商品名称">
				<button type="button" onclick="searchGoods(${GoodsPager.pageOffset}, ${GoodsPager.pageSize})">
					<i class="fas fa-search"></i> 查询
				</button>
				<div id="searchDiv" class="search-error"></div>
			</div>

			<div class="table-container">
				<form method="post" name="deleteForm">
					<table class="table table-striped table-hover">
						<thead>
						<tr>
							<th>商品ID</th>
							<th>商品名</th>
							<th>商品介绍</th>
							<th>原价</th>
							<th>现价</th>
							<th>商品类型</th>
							<th>数量</th>
							<th>
								<input type="checkbox" id="selectAll" onclick="selectAll()">
								<label for="selectAll">全选</label>
							</th>
							<th>操作</th>
						</tr>
						</thead>
						<tbody>
						<c:forEach var="Goods" items="${GoodsList}">
							<tr align="center" class="hover-bg">
								<td>${Goods.goodsId}</td>
								<td>${Goods.goodsName}</td>
								<td>
									<div class="preview">${Goods.introduce}</div>
								</td>
								<td>${Goods.price}</td>
								<td>${Goods.nowPrice}</td>
								<td>
									<c:if test="${Goods.newGoods == 1}">
										<span class="status-tag status-new">新品</span>
									</c:if>
									<c:if test="${Goods.saleGoods == 1}">
										<span class="status-tag status-sale">特价</span>
									</c:if>
									<c:if test="${Goods.hostGoods == 1}">
										<span class="status-tag status-hot">热卖</span>
									</c:if>
								</td>
								<td>${Goods.goodsNum}</td>
								<td><input type="checkbox" name="delete" value="${Goods.goodsId}"></td>
								<td>
									<a href="updateGoodServlet?GoodsId=${Goods.goodsId}" class="btn btn-primary btn-sm">
										<i class="fas fa-edit"></i> 详情
									</a>
								</td>
							</tr>
						</c:forEach>
						</tbody>
					</table>
				</form>
			</div>

			<!-- 分页导航 -->
			<div class="pagination mt-20">
				<pg:pager items="${GoodsPager.totalNum }" maxPageItems="${GoodsPager.pageSize}" export="currentPageNo = pageNumber" url="getGoodsPagerServlet">
					<pg:param name="pageSize" value="${GoodsPager.pageSize }"/>
					<pg:param name="pageNo" value="${currentPageNo}"/>
					<pg:first>
						<a href="${pageUrl}" class="page-link">
							<i class="fas fa-fast-backward"></i>
						</a>
					</pg:first>
					<pg:prev>
						<a href="${pageUrl}" class="page-link">
							<i class="fas fa-backward"></i>
						</a>
					</pg:prev>
					<pg:pages>
						<c:choose>
							<c:when test="${GoodsPager.pagecurrentPageNo eq pageNumber}">
								<span class="page-link active">${pageNumber}</span>
							</c:when>
							<c:otherwise>
								<a href="${pageUrl}" class="page-link">${pageNumber}</a>
							</c:otherwise>
						</c:choose>
					</pg:pages>
					<pg:next>
						<a href="${pageUrl}" class="page-link">
							<i class="fas fa-forward"></i>
						</a>
					</pg:next>
					<pg:last>
						<a href="${pageUrl}" class="page-link">
							<i class="fas fa-fast-forward"></i>
						</a>
					</pg:last>
				</pg:pager>
			</div>
		</div>
	</div>
</div>

<script>
	// 全选/反选功能
	function selectAll() {
		var deletes = document.getElementsByName("delete");
		var selectAll = document.getElementById("selectAll");
		for (var i = 0; i < deletes.length; i++) {
			deletes[i].checked = selectAll.checked;
		}
	}

	// 删除商品功能
	function removeGoods(pageOffset, pageSize) {
		var deletes = document.getElementsByName("delete");
		var count = 0;
		var goods = new Array();
		for (var i = 0; i < deletes.length; i++) {
			if (deletes[i].checked) {
				count++;
				goods.push(deletes[i].value);
			}
		}
		if (count == 0) {
			alert("请选择要删除的商品");
			return false;
		}
		if (!confirm("确定要删除选中的" + count + "个商品吗？此操作不可恢复")) {
			return false;
		}
		var oform = document.getElementsByTagName("form")[0];
		oform.action = "deleteGoodsServlet?GoodsIds=" + goods + "&pageOffset=" + pageOffset + "&pageSize=" + pageSize;
		oform.submit();
	}

	// 搜索商品功能
	function searchGoods(pageOffset, pageSize) {
		var goodsName = document.getElementById("GoodsName");
		var searchDiv = document.getElementById("searchDiv");
		var oform = document.getElementsByTagName("form")[0];

		if (goodsName.value.trim() !== "") {
			oform.action = "getGoodsByGoodsNameServlet?GoodsName=" + encodeURIComponent(goodsName.value.trim());
			oform.submit();
		} else {
			searchDiv.textContent = "请输入关键字";
			// 3秒后清除错误提示
			setTimeout(function() {
				searchDiv.textContent = "";
			}, 3000);
		}
	}
</script>
</body>
</html>