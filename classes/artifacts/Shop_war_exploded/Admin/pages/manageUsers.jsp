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
	<title>普通用户管理</title>

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
			height: 60px;
			display: flex;
			align-items: center;
			justify-content: space-between;
		}
		.header-left { display: flex; align-items: center; }
		.logo { display: flex; align-items: center; font-size: 18px; font-weight: bold; }
		.logo i { margin-right: 10px; font-size: 24px; }
		.header-right { display: flex; align-items: center; }
		.user-info { display: flex; align-items: center; cursor: pointer; margin-left: 20px; }
		.avatar { width: 36px; height: 36px; border-radius: 50%; margin-right: 10px; object-fit: cover; }

		/* 主内容区 */
		.main-content { padding: 20px; }
		.content-header { margin-bottom: 20px; padding-bottom: 10px; border-bottom: 1px solid #e2e8f0; }
		.page-title { margin-bottom: 5px; }
		.page-title h1 { font-size: 24px; font-weight: 600; color: #1a202c; }
		.breadcrumbs { display: flex; align-items: center; color: #718096; font-size: 14px; }
		.breadcrumbs a { color: #4a5568; transition: color 0.3s; }
		.breadcrumbs a:hover { color: #1a56db; }
		.breadcrumbs span { margin: 0 8px; }

		/* 卡片样式 */
		.card { background-color: #fff; border-radius: 8px; box-shadow: 0 4px 6px rgba(0, 0, 0, 0.05); overflow: hidden; margin-bottom: 20px; }
		.card-header { display: flex; justify-content: space-between; align-items: center; padding: 15px 20px; background-color: #f7fafc; border-bottom: 1px solid #e2e8f0; }
		.card-title { display: flex; align-items: center; font-size: 16px; font-weight: 600; color: #1a202c; }
		.card-title i { margin-right: 10px; color: #1a56db; }
		.card-actions { display: flex; gap: 10px; }
		.btn { display: inline-flex; align-items: center; justify-content: center; padding: 6px 12px; border-radius: 4px; font-size: 14px; font-weight: 500; transition: all 0.3s; cursor: pointer; border: none; }
		.btn-danger { background-color: #ef4444; color: #fff; }
		.btn-danger:hover { background-color: #dc2626; }
		.btn-danger i { margin-right: 5px; }

		/* 表格样式 */
		.table-container { overflow-x: auto; }
		.table { width: 100%; border-collapse: collapse; }
		.table th, .table td { padding: 12px 15px; text-align: left; border-bottom: 1px solid #e2e8f0; }
		.table th { background-color: #f7fafc; font-weight: 600; color: #1a202c; text-align: center; }
		.table-striped tbody tr:nth-of-type(odd) { background-color: #f8fafc; }
		.table-hover tbody tr:hover { background-color: #ebf8ff; transition: background-color 0.3s; }
		.table td { font-size: 14px; }
		.table .action-btn { display: inline-flex; align-items: center; padding: 4px 10px; font-size: 12px; border-radius: 4px; color: #fff; background-color: #3b82f6; }
		.table .action-btn:hover { background-color: #2563eb; }

		/* 分页样式 */
		.pagination { display: flex; justify-content: center; margin-top: 20px; font-size: 0; }
		.page-link { display: inline-block; width: 36px; height: 36px; line-height: 36px; text-align: center; margin: 0 5px; border-radius: 4px; font-size: 14px; color: #4a5568; background-color: #fff; border: 1px solid #e2e8f0; transition: all 0.3s; }
		.page-link:hover { background-color: #edf2f7; border-color: #cbd5e0; color: #1a56db; }
		.page-link.active { background-color: #1a56db; color: #fff; border-color: #1a56db; }
		.page-link i { font-size: 14px; }

		/* 响应式设计 */
		@media (max-width: 768px) {
			.header { height: 50px; padding: 0 15px; }
			.logo { font-size: 16px; }
			.logo i { font-size: 20px; }
			.avatar { width: 30px; height: 30px; }
			.page-title h1 { font-size: 20px; }
			.card-header { padding: 10px 15px; }
			.card-title { font-size: 14px; }
			.table th, .table td { padding: 8px 10px; font-size: 13px; }
			.page-link { width: 30px; height: 30px; line-height: 30px; font-size: 12px; }
		}
	</style>
</head>

<body>
<!-- 简化的顶部导航 -->
<header class="header">
	<div class="header-left">
		<div class="logo">
			<i class="fas fa-cubes"></i>
			<span>企业管理系统</span>
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
			<h1>普通用户管理</h1>
			<p>查看和管理系统普通用户</p>
		</div>
		<div class="breadcrumbs">
			<a href="#"><i class="fas fa-home"></i> 首页</a>
			<span>></span>
			<span>用户管理</span>
		</div>
	</div>

	<div class="card">
		<div class="card-header">
			<div class="card-title">
				<i class="fas fa-users"></i>
				<span>用户列表</span>
			</div>
			<div class="card-actions">
				<button type="button" class="btn btn-danger" onclick="removeUser(${userPager.pageOffset}, ${userPager.pageSize})">
					<i class="fas fa-trash"></i> 删除选中
				</button>
			</div>
		</div>

		<div class="card-body">
			<div class="table-container">
				<form method="post" name="deleteForm">
					<table class="table table-striped table-hover">
						<thead>
						<tr>
							<th>用户ID</th>
							<th>用户名</th>
							<th>性别</th>
							<th>手机</th>
							<th>地址</th>
							<th>积分</th>
							<th>
								<input type="checkbox" id="selectAll" onclick="selectAll()">
								<label for="selectAll">全选</label>
							</th>
							<th>操作</th>
						</tr>
						</thead>
						<tbody>
						<c:forEach var="user" items="${userList}">
							<tr align="center" class="hover-bg">
								<td>${user.id }</td>
								<td>${user.name }</td>
								<td>${user.sex }</td>
								<td>${user.mphone }</td>
								<td>${user.address }</td>
								<td>${user.score }</td>
								<td><input type="checkbox" name="delete" value="${user.id }"></td>
								<td>
									<a href="getOneUserServlet?id=${user.id}" class="action-btn">
										<i class="fas fa-eye"></i> 详情
									</a>
								</td>
							</tr>
						</c:forEach>
						</tbody>
					</table>
				</form>
			</div>

			<!-- 分页导航 -->
			<div class="pagination">
				<pg:pager items="${userPager.totalNum }" maxPageItems="${userPager.pageSize}" export="currentPageNo = pageNumber" url="getUserPagerServlet">
					<pg:param name="pageSize" value="${userPager.pageSize }"/>
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
							<c:when test="${userPager.pagecurrentPageNo eq pageNumber}">
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

	// 删除用户功能
	function removeUser(pageOffset, pageSize) {
		var deletes = document.getElementsByName("delete");
		var count = 0;
		var users = new Array();
		for (var i = 0; i < deletes.length; i++) {
			if (deletes[i].checked) {
				count++;
				users.push(deletes[i].value);
			}
		}
		if (count == 0) {
			alert("请选择要删除的用户");
			return false;
		}
		if (!confirm("确定要删除选中的" + count + "个用户吗？此操作不可恢复")) {
			return false;
		}
		var oform = document.getElementsByTagName("form")[0];
		oform.action = "deleteUser?userIds=" + users + "&pageOffset=" + pageOffset + "&pageSize=" + pageSize;
		oform.submit();
	}
</script>
</body>
</html>