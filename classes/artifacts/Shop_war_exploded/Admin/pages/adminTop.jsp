<%@ page language="java" pageEncoding="UTF-8"%>
<%
  String path = request.getContextPath();
  String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html>
<html lang="zh-CN">
<head>
  <base href="<%=basePath%>">
  <title>管理员控制面板</title>

  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">

  <!-- 引入现代化样式和图标库 -->
  <link href="Admin/css/style.css" rel="stylesheet" type="text/css">
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">

  <!-- 顶部导航专用样式 -->
  <style>
    body {
      font-family: "Microsoft YaHei", Arial, sans-serif;
      background-color: #f5f7fa;
      color: #333;
      line-height: 1.6;
      padding: 0;
      margin: 0;
    }

    /* 顶部导航栏 */
    .admin-header {
      background-color: #3498db;
      color: #fff;
      display: flex;
      justify-content: space-between;
      align-items: center;
      padding: 0 20px;
      height: 60px;
      box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
    }

    /* 品牌标志 */
    .admin-brand {
      display: flex;
      align-items: center;
    }

    .admin-logo {
      width: 40px;
      height: 40px;
      margin-right: 10px;
    }

    .admin-title {
      font-size: 18px;
      font-weight: 600;
    }

    /* 导航菜单 */
    .admin-nav {
      display: flex;
      align-items: center;
    }

    .admin-nav-item {
      color: #fff;
      text-decoration: none;
      padding: 0 15px;
      height: 60px;
      display: flex;
      align-items: center;
      transition: background-color 0.3s;
      position: relative;
    }

    .admin-nav-item:hover {
      background-color: #2980b9;
    }

    .admin-nav-icon {
      margin-right: 5px;
      font-size: 16px;
    }

    /* 用户信息区域 */
    .admin-user {
      display: flex;
      align-items: center;
      margin-left: 20px;
    }

    .admin-avatar {
      width: 36px;
      height: 36px;
      border-radius: 50%;
      background-color: #fff;
      display: flex;
      align-items: center;
      justify-content: center;
      margin-right: 10px;
      overflow: hidden;
    }

    .admin-avatar img {
      width: 100%;
      height: 100%;
      object-fit: cover;
    }

    .admin-username {
      font-size: 14px;
      margin-right: 5px;
    }

    /* 日期时间显示 */
    .admin-date {
      font-size: 13px;
      color: #e0e0e0;
      margin-left: 20px;
    }

    /* 下拉菜单 */
    .dropdown {
      position: relative;
    }

    .dropdown-menu {
      position: absolute;
      top: 100%;
      right: 0;
      background-color: #fff;
      border-radius: 4px;
      box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
      min-width: 160px;
      z-Index: 1000;
      display: none;
    }

    .dropdown:hover .dropdown-menu {
      display: block;
    }

    .dropdown-item {
      display: block;
      padding: 10px 15px;
      color: #333;
      text-decoration: none;
      transition: background-color 0.3s;
    }

    .dropdown-item:hover {
      background-color: #f5f5f5;
    }

    /* 移动端菜单按钮 */
    .menu-toggle {
      display: none;
      cursor: pointer;
      padding: 10px;
    }

    .menu-toggle i {
      font-size: 20px;
    }

    /* 响应式设计 */
    @media (max-width: 768px) {
      .admin-nav {
        display: none;
        position: absolute;
        top: 60px;
        left: 0;
        width: 100%;
        background-color: #3498db;
        flex-direction: column;
        z-Index: 100;
      }

      .admin-nav-item {
        width: 100%;
        padding: 0 20px;
        border-bottom: 1px solid rgba(255, 255, 255, 0.1);
      }

      .menu-toggle {
        display: block;
      }

      .admin-date {
        display: none;
      }

      .admin-user {
        margin-left: auto;
      }
    }

    /* 日期格式化样式 */
    .date-container {
      font-size: 14px;
      color: #f5f5f5;
    }
  </style>
</head>
<body onload="showTime()">
<!-- 顶部导航栏 -->
<div class="admin-header">
  <!-- 品牌标志 -->
  <div class="admin-brand">
    <div class="admin-logo">
      <i class="fas fa-crown text-white"></i>
    </div>
    <div class="admin-title">管理中心</div>
  </div>

  <!-- 移动端菜单按钮 -->
  <div class="menu-toggle" onclick="toggleMenu()">
    <i class="fas fa-bars"></i>
  </div>

  <!-- 导航菜单 -->
  <div class="admin-nav" id="adminNav">
    <a href="Admin/pages/adminConter.jsp" class="admin-nav-item">
      <i class="admin-nav-icon fas fa-tachometer-alt"></i>控制台
    </a>
    <a href="#" class="admin-nav-item" onclick="goBack()">
      <i class="admin-nav-icon fas fa-arrow-left"></i>后退
    </a>
    <a href="#" class="admin-nav-item" onclick="goForWord()">
      <i class="admin-nav-icon fas fa-arrow-right"></i>前进
    </a>

    <!-- 用户信息区域 -->
    <div class="admin-user">
      <div class="admin-avatar">
        <i class="fas fa-user"></i>
      </div>
      <span class="admin-username">${admin.adminName }</span>

      <!-- 下拉菜单 -->
      <div class="dropdown">
        <a href="#" class="admin-nav-item" style="padding-right: 30px;">
          <i class="admin-nav-icon fas fa-cog"></i>设置
          <i class="fas fa-angle-down ml-2"></i>
        </a>
        <div class="dropdown-menu">
          <a href="Admin/pages/updatePassword.jsp" target="contentIframe" class="dropdown-item">
            <i class="fas fa-key mr-2"></i>修改密码
          </a>
          <a href="adminLogout" class="dropdown-item">
            <i class="fas fa-sign-out-alt mr-2"></i>退出登录
          </a>
        </div>
      </div>
    </div>

    <!-- 日期显示 -->
    <div class="admin-date">
      <div id="timeId" class="date-container"></div>
    </div>
  </div>
</div>

<script>
  // 显示时间
  function showTime() {
    var timeId = document.getElementById("timeId");
    var x = ["星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"];
    var e = new Date();
    var day = e.getDay();
    var year = e.getFullYear();
    var month = e.getMonth() + 1;
    var date = e.getDate();

    // 确保月份和日期是两位数
    month = month < 10 ? '0' + month : month;
    date = date < 10 ? '0' + date : date;

    timeId.innerHTML = "日期：" + year + "年" + month + "月" + date + "日 " + x[day];
  }

  // 后退
  function goBack() {
    window.history.go(-1);
  }

  // 前进
  function goForWord() {
    window.history.go(1);
  }

  // 移动端菜单切换
  function toggleMenu() {
    var nav = document.getElementById("adminNav");
    nav.style.display = nav.style.display === "none" ? "flex" : "none";
  }

  // 监听窗口大小变化，适应响应式设计
  window.addEventListener('resize', function() {
    var nav = document.getElementById("adminNav");
    if (window.innerWidth > 768) {
      nav.style.display = "flex";
    } else {
      nav.style.display = "none";
    }
  });
</script>
</body>
</html>
