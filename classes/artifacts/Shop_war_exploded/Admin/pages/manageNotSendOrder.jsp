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
	<title>未发货订单 - 企业管理系统</title>

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

		/* 表格样式 */
		.table-container { overflow-x: auto; }
		.table { width: 100%; border-collapse: collapse; }
		.table th, .table td { padding: 8px 10px; text-align: left; border-bottom: 1px solid #e2e8f0; }
		.table th { background-color: #f7fafc; font-weight: 600; color: #1a202c; text-align: center; font-size: 13px; }
		.table-striped tbody tr:nth-of-type(odd) { background-color: #f8fafc; }
		.table-hover tbody tr:hover { background-color: #ebf8ff; transition: background-color 0.3s; }
		.table td { font-size: 12px; }
		.action-btn { display: inline-flex; align-items: center; padding: 2px 8px; font-size: 11px; border-radius: 4px; color: #fff; transition: all 0.3s; }
		.action-btn-primary { background-color: #3b82f6; }
		.action-btn-primary:hover { background-color: #2563eb; }
		.action-btn-info { background-color: #06b6d4; }
		.action-btn-info:hover { background-color: #0891b2; }

		/* 状态标签 */
		.status-pill { display: inline-block; padding: 2px 8px; border-radius: 12px; font-size: 11px; font-weight: 500; }
		.status-pending { background-color: #fef3c7; color: #92400e; }

		/* 分页样式 */
		.pagination { display: flex; justify-content: center; margin-top: 15px; font-size: 0; }
		.page-link { display: inline-block; width: 30px; height: 30px; line-height: 30px; text-align: center; margin: 0 3px; border-radius: 4px; font-size: 12px; color: #4a5568; background-color: #fff; border: 1px solid #e2e8f0; transition: all 0.3s; }
		.page-link:hover { background-color: #edf2f7; border-color: #cbd5e0; color: #1a56db; }
		.page-link.active { background-color: #1a56db; color: #fff; border-color: #1a56db; }
		.page-link i { font-size: 12px; }

		/* 响应式设计 */
		@media (max-width: 576px) {
			.header { height: 45px; padding: 0 10px; }
			.logo { font-size: 14px; }
			.page-title h1 { font-size: 18px; }
			.card-header { padding: 8px 12px; }
			.card-title { font-size: 13px; }
			.table th, .table td { padding: 6px 8px; font-size: 11px; }
			.page-link { width: 26px; height: 26px; line-height: 26px; font-size: 10px; }
		}
	</style>
</head>

<body>
<!-- 简化的顶部导航 -->
<header class="header">
	<div class="header-left">
		<div class="logo">
			<i class="fas fa-shopping-cart"></i>
			<span>订单管理</span>
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
			<h1>未发货订单</h1>
			<p>查看和处理未发货的订单</p>
		</div>
		<div class="breadcrumbs">
			<a href="#"><i class="fas fa-home"></i> 首页</a>
			<span>></span>
			<span>订单管理</span>
			<span>></span>
			<span>未发货订单</span>
		</div>
	</div>

	<div class="card">
		<div class="card-header">
			<div class="card-title">
				<i class="fas fa-clock"></i>
				<span>待发货订单列表</span>
			</div>
		</div>

		<div class="card-body">
			<div class="table-container">
				<form method="post" name="sendForm">
					<table class="table table-striped table-hover">
						<thead>
						<tr>
							<th>订单ID</th>
							<th>用户名</th>
							<th>收货人姓名</th>
							<th>地址</th>
							<th>邮编</th>
							<th>Email</th>
							<th>下单时间</th>
							<th>订单状态</th>
							<th>操作</th>
						</tr>
						</thead>
						<tbody>
						<c:forEach var="order" items="${orderList}">
							<tr align="center" class="hover-bg">
								<td>${order.orderId}</td>
								<td>${order.user.name}</td>
								<td>${order.recvName}</td>
								<td>${order.user.address}</td>
								<td>${order.user.postcode}</td>
								<td>${order.user.email}</td>
								<td>${order.orderDate}</td>
								<td>
                        <span class="status-pill status-pending">
								${order.flag}
						</span>
								</td>
								<td>
									<a href="adminSendOrderServlet?orderId=${order.orderId}&pageOffset=${orderPager.pageOffset}&pageSize=${orderPager.pageSize}" class="action-btn action-btn-primary">
										<i class="fas fa-truck"></i> 发货
									</a>
									<a href="getOneOrderServlet?orderId=${order.orderId}" class="action-btn action-btn-info ml-5">
										<i class="fas fa-eye"></i> 详情
									</a>
								</td>
							</tr>
						</c:forEach>
						</tbody>
					</table>
				</form>
			</div>

			<!-- 发货状态提示 -->
			<div class="send-message mt-15">
				<font size="6" color="red">${sendMessage}</font>
			</div>

			<!-- 分页导航 -->
			<div class="pagination mt-20">
				<pg:pager items="${orderPager.totalNum }" maxPageItems="${orderPager.pageSize}" export="currentPageNo = pageNumber" url="getOrderNotSendPagerServlet">
					<pg:param name="pageSize" value="${orderPager.pageSize }"/>
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
							<c:when test="${orderPager.pagecurrentPageNo eq pageNumber}">
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
	// 发货操作可以在这里添加额外的确认逻辑
	document.querySelectorAll('.action-btn-primary').forEach(button => {
		button.addEventListener('click', function(e) {
			if (!confirm("确定要标记此订单为已发货吗？")) {
				e.preventDefault();
			}
		});
	});
</script>
</body>
</html>