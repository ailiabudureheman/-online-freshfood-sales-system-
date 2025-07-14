<%@ page language="java" pageEncoding="UTF-8"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <base href="<%=basePath%>">
    <title>添加信息</title>

    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">

    <!-- 引入现代化样式和图标库 -->
    <link href="Admin/css/style.css" rel="stylesheet" type="text/css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">

    <!-- 添加信息页面专用样式 -->
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

        .form-group {
            margin-bottom: 20px;
        }

        .form-group label {
            display: block;
            margin-bottom: 8px;
            color: #4a5568;
            font-weight: 500;
        }

        .form-control {
            width: 100%;
            padding: 10px 12px;
            border: 1px solid #e2e8f0;
            border-radius: 4px;
            font-size: 14px;
            transition: border-color 0.3s, box-shadow 0.3s;
        }

        .form-control:focus {
            border-color: #3182ce;
            box-shadow: 0 0 0 3px rgba(49, 130, 206, 0.1);
            outline: none;
        }

        .form-textarea {
            width: 100%;
            padding: 10px 12px;
            border: 1px solid #e2e8f0;
            border-radius: 4px;
            font-size: 14px;
            transition: border-color 0.3s, box-shadow 0.3s;
            resize: vertical;
            min-height: 150px;
        }

        .form-textarea:focus {
            border-color: #3182ce;
            box-shadow: 0 0 0 3px rgba(49, 130, 206, 0.1);
            outline: none;
        }

        .form-actions {
            display: flex;
            justify-content: flex-end;
            gap: 10px;
            margin-top: 30px;
        }

        .btn {
            padding: 10px 20px;
            border-radius: 4px;
            font-size: 14px;
            cursor: pointer;
            transition: all 0.3s;
            border: none;
            display: flex;
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

        .btn-secondary {
            background-color: #edf2f7;
            color: #2d3748;
        }

        .btn-secondary:hover {
            background-color: #e2e8f0;
        }

        .alert {
            padding: 12px 16px;
            border-radius: 4px;
            margin-bottom: 20px;
            font-size: 14px;
        }

        .alert-success {
            background-color: #f0fff4;
            color: #276749;
            border: 1px solid #c6f6d5;
        }

        .alert-error {
            background-color: #fff5f5;
            color: #c53030;
            border: 1px solid #fed7d7;
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
            .form-actions {
                flex-direction: column;
            }

            .btn {
                width: 100%;
            }
        }
    </style>
</head>
<body>
<div class="container">
    <!-- 错误消息提示 -->
    <% if (request.getAttribute("message") != null) { %>
    <div class="alert alert-error">
        <i class="fas fa-exclamation-circle mr-2"></i>
        <%= request.getAttribute("message") %>
    </div>
    <% } %>

    <!-- 添加信息表单卡片 -->
    <div class="card">
        <div class="card-header">
            <h2 class="card-title">添加信息</h2>
        </div>

        <form action="addInformServlet" method="post">
            <div class="form-group">
                <label for="informTitle">标题</label>
                <input type="text" id="informTitle" name="informTitle" class="form-control" placeholder="请输入信息标题">
            </div>

            <div class="form-group">
                <label for="informContent">内容</label>
                <textarea id="informContent" name="informContent" class="form-textarea" placeholder="请输入详细内容..."></textarea>
            </div>

            <div class="form-actions">
                <button type="button" class="btn btn-secondary" onclick="resetForm()">
                    <i class="fas fa-refresh mr-2"></i>重置
                </button>
                <button type="submit" class="btn btn-primary">
                    <i class="fas fa-save mr-2"></i>添加
                </button>
            </div>
        </form>
    </div>
</div>

<script>
    // 重置表单
    function resetForm() {
        document.forms[0].reset();
    }
</script>
</body>
</html>
