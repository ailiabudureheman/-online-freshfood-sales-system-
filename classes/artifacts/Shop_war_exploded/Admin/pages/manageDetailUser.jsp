<%@ page language="java" pageEncoding="GB18030"%>
<%@ include file="tools.jsp" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html>
<html lang="zh-CN">
<head>
	<base href="<%=basePath%>">
	<title>用户详情 - 企业管理系统</title>

	<meta charset="GB18030">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">

	<!-- 引入现代化样式和图标库 -->
	<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
	<style>
		/* 基础样式重置 */
		* { margin: 0; padding: 0; box-sizing: border-box; }
		body { font-family: "Microsoft YaHei", Arial, sans-serif; color: #333; line-height: 1.6; background-color: #f8fafc; }
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
		.main-content { padding: 20px; max-width: 1200px; margin: 0 auto; }
		.content-header { margin-bottom: 20px; padding-bottom: 10px; border-bottom: 1px solid #e2e8f0; }
		.page-title { margin-bottom: 5px; }
		.page-title h1 { font-size: 20px; font-weight: 600; color: #1a202c; }
		.breadcrumbs { display: flex; align-items: center; color: #718096; font-size: 12px; }
		.breadcrumbs a { color: #4a5568; transition: color 0.3s; }
		.breadcrumbs a:hover { color: #1a56db; }
		.breadcrumbs span { margin: 0 5px; }

		/* 卡片样式 */
		.card { background-color: #fff; border-radius: 8px; box-shadow: 0 4px 6px rgba(0, 0, 0, 0.05); overflow: hidden; margin-bottom: 20px; padding: 20px; }
		.card-header { border-bottom: 1px solid #e2e8f0; padding-bottom: 15px; margin-bottom: 15px; display: flex; justify-content: space-between; align-items: center; }
		.card-title { display: flex; align-items: center; font-size: 16px; font-weight: 600; color: #1a202c; }
		.card-title i { margin-right: 8px; color: #1a56db; }
		.card-actions { display: flex; gap: 10px; }

		/* 表单样式 */
		.form-group { display: flex; margin-bottom: 20px; flex-wrap: wrap; gap: 20px; }
		.form-row { flex: 1; min-width: 300px; display: flex; margin-bottom: 15px; }
		.form-label { width: 120px; font-weight: 500; color: #4a5568; display: flex; align-items: center; }
		.form-control { flex: 1; padding: 8px 12px; border: 1px solid #e2e8f0; border-radius: 4px; font-size: 14px; }
		.form-control[contentEditable=false] { background-color: #f8fafc; cursor: not-allowed; }
		.form-avatar { display: flex; align-items: center; }
		.user-avatar { width: 100px; height: 100px; border-radius: 50%; object-fit: cover; border: 2px solid #e2e8f0; }

		/* 按钮样式 */
		.btn { display: inline-flex; align-items: center; justify-content: center; padding: 8px 16px; border-radius: 4px; font-size: 14px; font-weight: 500; transition: all 0.3s; cursor: pointer; border: none; }
		.btn-primary { background-color: #3b82f6; color: #fff; }
		.btn-primary:hover { background-color: #2563eb; }
		.btn-secondary { background-color: #e2e8f0; color: #4a5568; }
		.btn-secondary:hover { background-color: #cbd5e0; }
		.btn i { margin-right: 6px; }

		/* 响应式设计 */
		@media (max-width: 768px) {
			.header { height: 45px; padding: 0 10px; }
			.logo { font-size: 14px; }
			.page-title h1 { font-size: 18px; }
			.card { padding: 15px; }
			.card-title { font-size: 15px; }
			.form-group { flex-direction: column; }
			.form-row { min-width: 100%; }
		}
	</style>
</head>

<body>
<!-- 简化的顶部导航 -->
<header class="header">
	<div class="header-left">
		<div class="logo">
			<i class="fas fa-user"></i>
			<span>用户管理</span>
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
			<h1>用户详情</h1>
			<p>查看和编辑用户信息</p>
		</div>
		<div class="breadcrumbs">
			<a href="#"><i class="fas fa-home"></i> 首页</a>
			<span>></span>
			<span>用户管理</span>
			<span>></span>
			<span>用户详情</span>
		</div>
	</div>

	<div class="card">
		<div class="card-header">
			<div class="card-title">
				<i class="fas fa-user-circle"></i>
				<span>用户信息</span>
			</div>
			<div class="card-actions">
				<a href="getUserPagerServlet" class="btn btn-secondary">
					<i class="fas fa-arrow-left"></i> 返回列表
				</a>
			</div>
		</div>

		<div class="form-group">
			<div class="form-row">
				<div class="form-label">
					<i class="fas fa-id-card"></i> 用户ID
				</div>
				<input type="text" class="form-control" name="id" contentEditable="false" value="${user.id}">
			</div>

			<div class="form-row">
				<div class="form-label">
					<i class="fas fa-user"></i> 用户名
				</div>
				<input type="text" class="form-control" name="name" value="${user.name}">
			</div>

			<div class="form-row">
				<div class="form-label">
					<i class="fas fa-lock"></i> 密码
				</div>
				<input type="text" class="form-control" name="password" value="${user.password}">
			</div>

			<div class="form-row">
				<div class="form-label">
					<i class="fas fa-envelope"></i> Email
				</div>
				<input type="text" class="form-control" name="email" value="${user.email}">
			</div>

			<div class="form-row">
				<div class="form-label">
					<i class="fas fa-signature"></i> 真实姓名
				</div>
				<input type="text" class="form-control" name="trueName" value="${user.trueName}">
			</div>

			<div class="form-row">
				<div class="form-label">
					<i class="fas fa-venus-mars"></i> 性别
				</div>
				<input type="text" class="form-control" name="sex" value="${user.sex}">
			</div>

			<div class="form-row">
				<div class="form-label">
					<i class="fas fa-birthday-cake"></i> 生日
				</div>
				<input type="text" class="form-control" name="birthday" value="${user.birthday}">
			</div>

			<div class="form-row">
				<div class="form-label">
					<i class="fas fa-home"></i> 家庭住址
				</div>
				<input type="text" class="form-control" name="address" value="${user.address}">
			</div>

			<div class="form-row">
				<div class="form-label">
					<i class="fas fa-postal-service"></i> 邮编
				</div>
				<input type="text" class="form-control" name="postcode" value="${user.postcode}">
			</div>

			<div class="form-row">
				<div class="form-label">
					<i class="fas fa-phone"></i> 固话
				</div>
				<input type="text" class="form-control" name="phone" value="${user.phone}">
			</div>

			<div class="form-row">
				<div class="form-label">
					<i class="fas fa-mobile-alt"></i> 手机
				</div>
				<input type="text" class="form-control" name="mphone" value="${user.mphone}">
			</div>

			<div class="form-row">
				<div class="form-label">
					<i class="fas fa-question-circle"></i> 安全问题
				</div>
				<input type="text" class="form-control" name="question" value="${user.question}">
			</div>

			<div class="form-row">
				<div class="form-label">
					<i class="fas fa-key"></i> 安全答案
				</div>
				<input type="text" class="form-control" name="answer" value="${user.answer}">
			</div>

			<div class="form-avatar">
				<div class="form-label">
					<i class="fas fa-image"></i> 用户头像
				</div>
				<img src="${user.img}" alt="用户头像" class="user-avatar">
			</div>

			<div class="form-row">
				<div class="form-label">
					<i class="fas fa-trophy"></i> 积分
				</div>
				<input type="text" class="form-control" name="score" value="${user.score}">
			</div>
		</div>
	</div>
</div>
</body>
</html>