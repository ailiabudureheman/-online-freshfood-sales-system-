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
    <link href="Admin/css/style.css" rel="stylesheet" type="text/css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">

    <!-- 管理页面专用样式 -->
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
            max-width: 1200px;
            margin: 20px auto;
            padding: 0 20px;
        }

        .card {
            background-color: #fff;
            border-radius: 8px;
            box-shadow: 0 4px 6px -1px rgba(0, 0, 0, 0.1), 0 2px 4px -1px rgba(0, 0, 0, 0.06);
            padding: 20px;
            margin-bottom: 20px;
        }

        .card-header {
            display: flex;
            justify-content: space-between;
            align-items: center;
            padding-bottom: 15px;
            margin-bottom: 20px;
            border-bottom: 1px solid #e2e8f0;
        }

        .card-title {
            margin: 0;
            color: #2d3748;
            font-size: 18px;
            font-weight: 600;
        }

        .table-responsive {
            overflow-x: auto;
        }

        .admin-table {
            width: 100%;
            border-collapse: collapse;
            border-radius: 4px;
            overflow: hidden;
        }

        .admin-table th,
        .admin-table td {
            padding: 12px 15px;
            text-align: left;
            border-bottom: 1px solid #edf2f7;
        }

        .admin-table th {
            background-color: #f7fafc;
            color: #4a5568;
            font-weight: 600;
            text-transform: uppercase;
            font-size: 13px;
        }

        .admin-table tr:hover {
            background-color: #f7fafc;
            transition: background-color 0.3s;
        }

        .btn {
            padding: 8px 12px;
            border-radius: 4px;
            font-size: 14px;
            cursor: pointer;
            transition: all 0.3s;
            border: none;
            text-decoration: none;
            display: inline-flex;
            align-items: center;
            justify-content: center;
        }

        .btn-primary {
            background-color: #3182ce;
            color: #fff;
        }

        .btn-primary:hover {
            background-color: #2b6cb0;
        }

        .btn-danger {
            background-color: #e53e3e;
            color: #fff;
        }

        .btn-danger:hover {
            background-color: #c53030;
        }

        .btn-secondary {
            background-color: #edf2f7;
            color: #2d3748;
        }

        .btn-secondary:hover {
            background-color: #e2e8f0;
        }

        .pagination {
            display: flex;
            justify-content: space-between;
            align-items: center;
            margin-top: 20px;
            padding: 10px 0;
            border-top: 1px solid #e2e8f0;
        }

        .pagination-buttons {
            display: flex;
            gap: 5px;
        }

        .pagination-btn {
            width: 32px;
            height: 32px;
            display: flex;
            align-items: center;
            justify-content: center;
            border-radius: 4px;
            text-decoration: none;
            color: #4a5568;
            background-color: #f7fafc;
        }

        .pagination-btn.active {
            background-color: #3182ce;
            color: #fff;
        }

        .pagination-btn:hover:not(.active) {
            background-color: #e2e8f0;
        }

        .select-all-container {
            display: flex;
            align-items: center;
        }

        /* 响应式设计 */
        @media (max-width: 768px) {
            .card-header {
                flex-direction: column;
                align-items: flex-start;
            }

            .pagination {
                flex-direction: column;
                gap: 15px;
            }

            .pagination-info {
                width: 100%;
                text-align: center;
            }

            .admin-table th,
            .admin-table td {
                padding: 8px 10px;
            }

            .btn {
                padding: 6px 10px;
                font-size: 13px;
            }
        }
    </style>
</head>
<body>
<div class="container">
    <!-- 页面标题卡片 -->
    <div class="card">
        <div class="card-header">
            <h2 class="card-title">普通用户管理</h2>
            <div class="btn-group">
                <a href="addAdmin.jsp" class="btn btn-primary">
                    <i class="fas fa-plus mr-2"></i>添加用户
                </a>
            </div>
        </div>

        <!-- 用户列表 -->
        <div class="table-responsive">
            <table class="admin-table">
                <thead>
                <tr>
                    <th>管理员ID</th>
                    <th>真实姓名</th>
                    <th>管理员级别</th>
                    <th>登录名</th>
                    <th>登录密码</th>
                    <th class="text-center">
                        <div class="select-all-container">
                            <input type="checkbox" id="selectAll" onclick="selectAll()">
                            <label for="selectAll" class="ml-2">全/反选</label>
                        </div>
                    </th>
                    <th>操作</th>
                </tr>
                </thead>
                <tbody>
                <form method="post" name="deleteForm">
                    <c:forEach var="admin" items="${adminList}">
                        <tr align="center">
                            <td>${admin.id }</td>
                            <td>${admin.adminName}</td>
                            <td>${admin.adminType }</td>
                            <td>${admin.loginName }</td>
                            <td>${admin.loginPwd }</td>
                            <td><input type="checkbox" name="delete" value="${admin.id }"></td>
                            <td>
                                <a href="getOneAdminServlet?id=${admin.id}" class="btn btn-secondary">
                                    <i class="fas fa-eye mr-2"></i>详情
                                </a>
                            </td>
                        </tr>
                    </c:forEach>
                </form>
                </tbody>
            </table>
        </div>

        <!-- 分页和批量操作 -->
        <div class="pagination">
            <div class="pagination-info">
                共 ${adminPager.totalNum} 条记录，当前第 ${adminPager.pagecurrentPageNo} 页
            </div>

            <div class="pagination-actions">
                <div class="pagination-buttons">
                    <pg:pager items="${adminPager.totalNum}" maxPageItems="${adminPager.pageSize}" export="currentPageNo = pageNumber" url="getAdminPagerServlet">
                        <pg:param name="pageSize" value="${adminPager.pageSize }"/>
                        <pg:param name="pageNo" value="${currentPageNo}"/>
                        <pg:first>
                            <a href="${pageUrl}" class="pagination-btn">
                                <i class="fas fa-angle-double-left"></i>
                            </a>
                        </pg:first>
                        <pg:prev>
                            <a href="${pageUrl}" class="pagination-btn">
                                <i class="fas fa-angle-left"></i>
                            </a>
                        </pg:prev>
                        <pg:pages>
                            <c:choose>
                                <c:when test="${adminPager.pagecurrentPageNo eq pageNumber}">
                                    <a href="${pageUrl}" class="pagination-btn active">${pageNumber}</a>
                                </c:when>
                                <c:otherwise>
                                    <a href="${pageUrl}" class="pagination-btn">${pageNumber}</a>
                                </c:otherwise>
                            </c:choose>
                        </pg:pages>
                        <pg:next>
                            <a href="${pageUrl}" class="pagination-btn">
                                <i class="fas fa-angle-right"></i>
                            </a>
                        </pg:next>
                        <pg:last>
                            <a href="${pageUrl}" class="pagination-btn">
                                <i class="fas fa-angle-double-right"></i>
                            </a>
                        </pg:last>
                    </pg:pager>
                </div>

                <button type="button" class="btn btn-danger ml-2" onclick="removeAdmin(${adminPager.pageOffset},${adminPager.pageSize})">
                    <i class="fas fa-trash mr-2"></i>删除选中
                </button>
            </div>
        </div>
    </div>
</div>

<script>
    // 全选/取消全选功能
    function selectAll() {
        var deletes = document.getElementsByName("delete");
        var selectAll = document.getElementById("selectAll");

        for (var i = 0; i < deletes.length; i++) {
            deletes[i].checked = selectAll.checked;
        }
    }

    // 删除选中的管理员
    function removeAdmin(pageOffset, pageSize) {
        var deletes = document.getElementsByName("delete");
        var count = 0;
        var admins = [];

        for (var i = 0; i < deletes.length; i++) {
            if (deletes[i].checked) {
                count++;
                admins.push(deletes[i].value);
            }
        }

        if (count == 0) {
            alert("还没有选中删除项");
            return false;
        }

        if (confirm("确定要删除选中的" + count + "个管理员吗？")) {
            var oform = document.getElementsByTagName("form")[0];
            oform.action = "deleteAdmin?adminIds=" + admins + "&pageOffset=" + pageOffset + "&pageSize=" + pageSize;
            oform.submit();
        }
    }
</script>
</body>
</html>
