<%@ page language="java" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
	<base href="<%=basePath%>">
	<title>企业管理系统 - 管理员登录</title>

	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">

	<!-- 引入现代化样式和图标库 -->
	<link href="Admin/css/style.css" rel="stylesheet" type="text/css">
	<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
	<script src="Admin/js/script.js" type="text/javascript"></script>

	<!-- 登录页面专用样式 -->
	<style>
		.login-container {
			display: flex;
			justify-content: center;
			align-items: center;
			min-height: 100vh;
			background-color: #f5f7fa;
			background-image: linear-gradient(135deg, #f5f7fa 0%, #e4ebf5 100%);
			padding: 20px;
		}

		.login-card {
			background-color: #fff;
			border-radius: 12px;
			box-shadow: 0 10px 25px rgba(0, 0, 0, 0.1);
			width: 100%;
			max-width: 400px;
			overflow: hidden;
			transition: transform 0.3s, box-shadow 0.3s;
		}

		.login-card:hover {
			transform: translateY(-5px);
			box-shadow: 0 15px 30px rgba(0, 0, 0, 0.15);
		}

		.login-header {
			background-color: #1a56db;
			color: #fff;
			padding: 30px 20px;
			text-align: center;
			position: relative;
		}

		.login-header::before {
			content: "";
			position: absolute;
			bottom: -10px;
			left: 50%;
			transform: translateX(-50%);
			width: 20px;
			height: 20px;
			background-color: #fff;
			transform: translateX(-50%) rotate(45deg);
		}

		.login-header h1 {
			font-size: 24px;
			margin-bottom: 5px;
		}

		.login-header p {
			font-size: 14px;
			opacity: 0.8;
		}

		.login-body {
			padding: 30px 20px;
		}

		.form-group {
			margin-bottom: 20px;
			position: relative;
		}

		.form-group label {
			display: block;
			font-size: 14px;
			color: #4a5568;
			margin-bottom: 8px;
			font-weight: 500;
		}

		.form-control {
			width: 100%;
			padding: 12px 15px;
			border: 1px solid #e2e8f0;
			border-radius: 6px;
			font-size: 14px;
			color: #1a202c;
			transition: border-color 0.3s, box-shadow 0.3s;
		}

		.form-control:focus {
			border-color: #1a56db;
			box-shadow: 0 0 0 3px rgba(26, 86, 219, 0.1);
			outline: none;
		}

		.form-group i {
			position: absolute;
			right: 15px;
			top: 40px;
			color: #cbd5e0;
		}

		.form-control:focus + i {
			color: #1a56db;
		}

		.form-actions {
			display: flex;
			justify-content: space-between;
			align-items: center;
			margin-top: 30px;
		}

		.btn-login {
			background-color: #1a56db;
			color: #fff;
			padding: 12px 25px;
			border-radius: 6px;
			font-size: 14px;
			font-weight: 500;
			transition: all 0.3s;
			cursor: pointer;
			border: none;
			box-shadow: 0 4px 6px rgba(26, 86, 219, 0.1);
		}

		.btn-login:hover {
			background-color: #1e429f;
			transform: translateY(-2px);
			box-shadow: 0 6px 10px rgba(26, 86, 219, 0.15);
		}

		.btn-reset {
			background-color: #e2e8f0;
			color: #4a5568;
			padding: 12px 25px;
			border-radius: 6px;
			font-size: 14px;
			font-weight: 500;
			transition: all 0.3s;
			cursor: pointer;
			border: none;
		}

		.btn-reset:hover {
			background-color: #cbd5e0;
		}

		.error-message {
			color: #e53e3e;
			font-size: 14px;
			margin-top: 15px;
			text-align: center;
			padding: 10px;
			background-color: #fff5f5;
			border-radius: 6px;
		}

		.login-footer {
			background-color: #f7fafc;
			padding: 15px 20px;
			text-align: center;
			border-top: 1px solid #e2e8f0;
			font-size: 12px;
			color: #718096;
		}

		/* 动画效果 */
		@keyframes fadeIn {
			from { opacity: 0; transform: translateY(10px); }
			to { opacity: 1; transform: translateY(0); }
		}

		.login-card {
			animation: fadeIn 0.5s ease-out forwards;
		}

		/* 响应式设计 */
		@media (max-width: 480px) {
			.login-card {
				max-width: 100%;
			}

			.form-actions {
				flex-direction: column;
			}

			.btn-login, .btn-reset {
				width: 100%;
				margin-bottom: 10px;
			}
		}
	</style>
</head>
<body>
<div class="login-container">
	<div class="login-card">
		<div class="login-header">
			<h1>企业管理系统</h1>
			<p>管理员登录</p>
		</div>

		<div class="login-body">
			<form action="adminLoginServlet" method="post" name="form" id="form">
				<div class="form-group">
					<label for="name">用户名</label>
					<div class="input-group">
						<input type="text" id="name" name="name" class="form-control" placeholder="请输入用户名">
						<i class="fas fa-user"></i>
					</div>
				</div>

				<div class="form-group">
					<label for="password">密码</label>
					<div class="input-group">
						<input type="password" id="password" name="password" class="form-control" placeholder="请输入密码">
						<i class="fas fa-lock"></i>
					</div>
				</div>

				<div class="form-actions">
					<button type="submit" class="btn-login">
						<i class="fas fa-sign-in-alt mr-2"></i> 登录
					</button>
					<button type="button" class="btn-reset" onclick="resetForm()">
						<i class="fas fa-refresh mr-2"></i> 重置
					</button>
				</div>

				<% if (request.getAttribute("message") != null) { %>
				<div class="error-message">
					<i class="fas fa-exclamation-circle mr-2"></i>
					<%= request.getAttribute("message") %>
				</div>
				<% } %>
			</form>
		</div>

		<div class="login-footer">
			<p>农产品销售网站 &copy; 2025 版权所有</p>
		</div>
	</div>
</div>

<script>
	// 重置表单函数
	function resetForm() {
		document.getElementById('name').value = '';
		document.getElementById('password').value = '';
	}

	// 添加表单验证
	document.getElementById('form').addEventListener('submit', function(e) {
		const name = document.getElementById('name').value;
		const password = document.getElementById('password').value;

		if (name.trim() === '' || password.trim() === '') {
			alert('请输入用户名和密码');
			e.preventDefault();
			return false;
		}
	});
</script>
</body>
</html>
