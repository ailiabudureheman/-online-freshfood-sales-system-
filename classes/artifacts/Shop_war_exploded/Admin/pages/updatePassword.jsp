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
    <title>修改密码 - 企业管理系统</title>

    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">

    <!-- 引入现代化样式和图标库 -->
    <link href="Admin/css/style.css" rel="stylesheet" type="text/css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
    <script src="Admin/js/script.js" type="text/javascript"></script>
</head>

<body>
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
    <aside class="sidebar">
        <div class="sidebar-toggle" id="sidebarToggle">
            <i class="fas fa-bars"></i>
        </div>

        <nav class="sidebar-menu">
            <ul>
                <li class="menu-item">
                    <a href="#" class="menu-header" data-target="manageUser">
                        <i class="fas fa-users"></i>
                        <span>用户管理</span>
                        <i class="fas fa-chevron-down"></i>
                    </a>
                    <ul class="menu-sub" id="manageUser">
                        <li><a href="getUserPagerServlet" target="contentIframe"><i class="fas fa-user"></i> 普通用户</a></li>
                    </ul>
                </li>

                <li class="menu-item">
                    <a href="#" class="menu-header" data-target="manageOrder">
                        <i class="fas fa-shopping-cart"></i>
                        <span>订单管理</span>
                        <i class="fas fa-chevron-down"></i>
                    </a>
                    <ul class="menu-sub" id="manageOrder">
                        <li><a href="getOrderPagerServlet" target="contentIframe"><i class="fas fa-list"></i> 查看所有订单</a></li>
                        <li><a href="getOrderNotSendPagerServlet" target="contentIframe"><i class="fas fa-clock"></i> 未发货订单</a></li>
                        <li><a href="getOrderSendPagerServlet" target="contentIframe"><i class="fas fa-truck"></i> 已发货订单</a></li>
                        <li><a href="getOrderFreezePagerServlet" target="contentIframe"><i class="fas fa-lock"></i> 已冻结订单</a></li>
                    </ul>
                </li>

                <li class="menu-item">
                    <a href="#" class="menu-header" data-target="manageInform">
                        <i class="fas fa-bullhorn"></i>
                        <span>公告管理</span>
                        <i class="fas fa-chevron-down"></i>
                    </a>
                    <ul class="menu-sub" id="manageInform">
                        <li><a href="Admin/pages/addInform.jsp" target="contentIframe"><i class="fas fa-plus"></i> 添加公告</a></li>
                        <li><a href="getInformPagerServlet" target="contentIframe"><i class="fas fa-list-alt"></i> 查看公告</a></li>
                    </ul>
                </li>

                <li class="menu-item">
                    <a href="#" class="menu-header" data-target="manageLiuyan">
                        <i class="fas fa-comments"></i>
                        <span>留言管理</span>
                        <i class="fas fa-chevron-down"></i>
                    </a>
                    <ul class="menu-sub" id="manageLiuyan">
                        <li><a href="getNotePagerServlet" target="contentIframe"><i class="fas fa-comment-dots"></i> 查看留言</a></li>
                    </ul>
                </li>

                <li class="menu-item">
                    <a href="#" class="menu-header" data-target="manageProduct">
                        <i class="fas fa-box-open"></i>
                        <span>商品管理</span>
                        <i class="fas fa-chevron-down"></i>
                    </a>
                    <ul class="menu-sub" id="manageProduct">
                        <li><a href="allowAddSuperServlet" target="contentIframe"><i class="fas fa-sitemap"></i> 添加大类</a></li>
                        <li><a href="allowAddSubServlet" target="contentIframe"><i class="fas fa-folder-plus"></i> 添加小类</a></li>
                        <li><a href="allowAddGoodsServlet" target="contentIframe"><i class="fas fa-tag"></i> 添加商品</a></li>
                        <li><a href="getGoodsPagerServlet" target="contentIframe"><i class="fas fa-archive"></i> 查看商品</a></li>
                    </ul>
                </li>

                <li class="menu-item">
                    <a href="#" class="menu-header" data-target="manageInventory">
                        <i class="fas fa-warehouse"></i>
                        <span>库存管理</span>
                        <i class="fas fa-chevron-down"></i>
                    </a>
                    <ul class="menu-sub" id="manageInventory">
                        <li><a href="getInventoryListServlet" target="contentIframe"><i class="fas fa-list"></i> 库存列表</a></li>
                        <li><a href="getLowInventoryListServlet" target="contentIframe"><i class="fas fa-exclamation-triangle text-warning"></i> 库存预警</a></li>
                        <li><a href="Admin/pages/adjustInventory.jsp" target="contentIframe"><i class="fas fa-sliders-h"></i> 库存调整</a></li>
                        <li><a href="getInventoryHistoryServlet" target="contentIframe"><i class="fas fa-history"></i> 库存历史</a></li>
                    </ul>
                </li>

                <li class="menu-item">
                    <a href="#" class="menu-header" data-target="manageAdmin">
                        <i class="fas fa-user-shield"></i>
                        <span>管理员管理</span>
                        <i class="fas fa-chevron-down"></i>
                    </a>
                    <ul class="menu-sub" id="manageAdmin">
                        <li><a href="getAdminPagerServlet" target="contentIframe"><i class="fas fa-user-cog"></i> 管理员</a></li>
                        <li><a href="allowAddAdminServlet" target="contentIframe"><i class="fas fa-user-plus"></i> 添加管理员</a></li>
                    </ul>
                </li>
            </ul>
        </nav>
    </aside>

    <!-- 主内容区域 -->
    <main class="main-content">
        <div class="content-header">
            <div class="page-title">
                <h1>修改密码</h1>
                <p>更新您的账户密码</p>
            </div>
            <div class="breadcrumbs">
                <a href="#"><i class="fas fa-home"></i> 首页</a>
                <span>></span>
                <span>系统设置</span>
                <span>></span>
                <span>修改密码</span>
            </div>
        </div>

        <div class="content-container">
            <div class="card">
                <div class="card-header">
                    <div class="card-title">
                        <i class="fas fa-key"></i>
                        <span>密码修改</span>
                    </div>
                </div>
                <div class="card-body">
                    <form id="passwordForm" action="adminUpdatePassword" method="post">
                        <input type="hidden" id="name" name="name" value="${admin.loginName}" readonly>

                        <div class="form-group">
                            <label for="password">
                                <i class="fas fa-lock"></i> 新密码
                                <span class="required">*</span>
                            </label>
                            <div class="input-group">
                                <input type="password" id="password" name="password"
                                       class="form-control" onblur="checkPassword()"
                                       placeholder="请输入新密码（至少6位）">
                                <div class="password-strength" id="passwordStrength"></div>
                            </div>
                            <div class="form-error" id="passwordError"></div>
                        </div>

                        <div class="form-group">
                            <label for="rpassword">
                                <i class="fas fa-lock-open"></i> 确认新密码
                                <span class="required">*</span>
                            </label>
                            <input type="password" id="rpassword" name="rpassword"
                                   class="form-control" onblur="checkRpassword()"
                                   placeholder="请再次输入新密码">
                            <div class="form-error" id="rpasswordError"></div>
                        </div>

                        <div class="form-actions">
                            <button type="button" class="btn btn-primary" onclick="updatePassword()">
                                <i class="fas fa-save"></i> 保存修改
                            </button>
                            <button type="reset" class="btn btn-secondary">
                                <i class="fas fa-undo"></i> 重置
                            </button>
                        </div>

                        <div class="form-message" id="messageDiv">
                            ${message != null ? message : ''}
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