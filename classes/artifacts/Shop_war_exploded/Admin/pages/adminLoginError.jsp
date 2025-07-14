<%@ page language="java" pageEncoding="UTF-8"%>
<%
  String path = request.getContextPath();
  String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html>
<html lang="zh-CN">
<head>
  <base href="<%=basePath%>">
  <title>访问受限</title>

  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">

  <!-- 引入现代化样式和图标库 -->
  <link href="Admin/css/style.css" rel="stylesheet" type="text/css">
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">

  <!-- 登录错误页面专用样式 -->
  <style>
    body {
      font-family: "Microsoft YaHei", Arial, sans-serif;
      background-color: #f5f7fa;
      color: #333;
      line-height: 1.6;
      padding: 0;
      margin: 0;
    }

    .container {
      max-width: 800px;
      margin: 20px auto;
      padding: 0 20px;
    }

    .card {
      background-color: #fff;
      border-radius: 8px;
      box-shadow: 0 4px 6px -1px rgba(0, 0, 0, 0.1), 0 2px 4px -1px rgba(0, 0, 0, 0.06);
      padding: 30px;
      margin-bottom: 20px;
    }

    .card-header {
      border-bottom: 1px solid #e2e8f0;
      padding-bottom: 15px;
      margin-bottom: 20px;
    }

    .card-title {
      margin: 0;
      color: #2d3748;
      font-size: 20px;
      font-weight: 600;
    }

    .alert {
      padding: 16px 20px;
      border-radius: 4px;
      margin-bottom: 20px;
      font-size: 14px;
      display: flex;
      align-items: flex-start;
    }

    .alert-danger {
      background-color: #fff5f5;
      color: #c53030;
      border: 1px solid #fed7d7;
    }

    .alert-icon {
      font-size: 24px;
      margin-right: 15px;
      margin-top: -2px;
    }

    .alert-content {
      flex: 1;
    }

    .alert-title {
      font-size: 18px;
      font-weight: 600;
      margin-bottom: 8px;
    }

    .alert-message {
      margin-bottom: 15px;
    }

    .btn {
      padding: 10px 20px;
      border-radius: 4px;
      font-size: 14px;
      cursor: pointer;
      transition: all 0.3s;
      border: none;
      display: inline-flex;
      align-items: center;
      justify-content: center;
      text-decoration: none;
    }

    .btn-primary {
      background-color: #3182ce;
      color: #fff;
    }

    .btn-primary:hover {
      background-color: #2b6cb0;
    }

    .btn-secondary {
      background-color: #edf2f7;
      color: #2d3748;
    }

    .btn-secondary:hover {
      background-color: #e2e8f0;
    }

    /* 动画效果 */
    @keyframes fadeIn {
      from { opacity: 0; transform: translateY(10px); }
      to { opacity: 1; transform: translateY(0); }
    }

    .card {
      animation: fadeIn 0.5s ease-out forwards;
    }

    /* 响应式设计 */
    @media (max-width: 576px) {
      .btn {
        width: 100%;
        margin-bottom: 10px;
      }

      .alert {
        flex-direction: column;
      }

      .alert-icon {
        margin-bottom: 10px;
      }
    }
  </style>
</head>
<body>
<div class="container">
  <!-- 错误提示卡片 -->
  <div class="card">
    <div class="card-header">
      <h2 class="card-title">访问受限</h2>
    </div>

    <div class="alert alert-danger">
      <div class="alert-icon">
        <i class="fas fa-exclamation-triangle"></i>
      </div>
      <div class="alert-content">
        <div class="alert-title">权限不足</div>
        <div class="alert-message">
          你无权访问该页面！请确保你已登录且拥有足够的权限。
        </div>
        <div class="alert-actions">
          <a href="Admin/index.jsp" class="btn btn-primary">
            <i class="fas fa-home mr-2"></i>返回首页
          </a>
          <a href="Admin/login.jsp" class="btn btn-secondary">
            <i class="fas fa-sign-in-alt mr-2"></i>登录
          </a>
        </div>
      </div>
    </div>
  </div>
</div>
</body>
</html>
