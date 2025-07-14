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
    <title>订单详情 - 企业管理系统</title>

    <meta charset="UTF-8">
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
        .card { background-color: #fff; border-radius: 8px; box-shadow: 0 4px 6px rgba(0, 0, 0, 0.05); overflow: hidden; margin-bottom: 20px; }
        .card-header { border-bottom: 1px solid #e2e8f0; padding: 15px 20px; display: flex; justify-content: space-between; align-items: center; }
        .card-title { display: flex; align-items: center; font-size: 16px; font-weight: 600; color: #1a202c; }
        .card-title i { margin-right: 8px; color: #1a56db; }
        .card-actions { display: flex; gap: 10px; }

        /* 表格样式 */
        .table-container { overflow-x: auto; padding: 20px; }
        .table { width: 100%; border-collapse: collapse; }
        .table th, .table td { padding: 12px 15px; text-align: left; border-bottom: 1px solid #e2e8f0; }
        .table th { background-color: #f7fafc; font-weight: 600; color: #1a202c; text-align: center; font-size: 14px; }
        .table-striped tbody tr:nth-of-type(odd) { background-color: #f8fafc; }
        .table-hover tbody tr:hover { background-color: #ebf8ff; transition: background-color 0.3s; }
        .table td { font-size: 14px; }

        /* 按钮样式 */
        .btn { display: inline-flex; align-items: center; justify-content: center; padding: 8px 16px; border-radius: 4px; font-size: 14px; font-weight: 500; transition: all 0.3s; cursor: pointer; border: none; }
        .btn-secondary { background-color: #e2e8f0; color: #4a5568; }
        .btn-secondary:hover { background-color: #cbd5e0; }
        .btn i { margin-right: 6px; }

        /* 响应式设计 */
        @media (max-width: 768px) {
            .header { height: 45px; padding: 0 10px; }
            .logo { font-size: 14px; }
            .page-title h1 { font-size: 18px; }
            .card-header { padding: 10px 15px; }
            .card-title { font-size: 15px; }
            .table th, .table td { padding: 8px 10px; font-size: 12px; }
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
            <h1>订单详情</h1>
            <p>查看订单中的商品项信息</p>
        </div>
        <div class="breadcrumbs">
            <a href="#"><i class="fas fa-home"></i> 首页</a>
            <span>></span>
            <span>订单管理</span>
            <span>></span>
            <span>订单详情</span>
        </div>
    </div>

    <div class="card">
        <div class="card-header">
            <div class="card-title">
                <i class="fas fa-receipt"></i>
                <span>订单 #${order.orderId} 商品明细</span>
            </div>
            <div class="card-actions">
                <a href="getOrderPagerServlet" class="btn btn-secondary">
                    <i class="fas fa-arrow-left"></i> 返回订单列表
                </a>
            </div>
        </div>

        <div class="table-container">
            <table class="table table-striped table-hover">
                <thead>
                <tr>
                    <th>订单ID</th>
                    <th>订单项ID</th>
                    <th>商品编号</th>
                    <th>商品名称</th>
                    <th>单价</th>
                    <th>数量</th>
                    <th>小计</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="orderItem" items="${order.orderItem}">
                    <tr>
                        <td>${order.orderId}</td>
                        <td>${orderItem.orderItemId}</td>
                        <td>${orderItem.goodsId}</td>
                        <td>${orderItem.goodsName}</td>
                        <td>? ${orderItem.price}</td>
                        <td>${orderItem.goodsNum}</td>
                        <td>? ${orderItem.price * orderItem.goodsNum}</td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</div>
</body>
</html>