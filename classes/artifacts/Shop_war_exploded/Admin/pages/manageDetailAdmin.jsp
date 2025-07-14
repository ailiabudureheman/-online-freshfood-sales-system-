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
    <title>管理员详情</title>

    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">

    <!-- 引入现代化样式和图标库 -->
    <link href="Admin/css/style.css" rel="stylesheet" type="text/css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">

    <!-- 表单页面专用样式 -->
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

        .form-group {
            margin-bottom: 15px;
        }

        .form-label {
            display: block;
            margin-bottom: 5px;
            font-weight: 500;
            color: #4a5568;
        }

        .form-control {
            width: 100%;
            padding: 8px 12px;
            border: 1px;
            border-radius: 4px;
            background-color: #f7fafc;
            border: 1px solid #e2e8f0;
            transition: border-color 0.3s;
        }

        .form-control:focus {
            outline: none;
            border-color: #3182ce;
        }

        .form-control[disabled] {
            background-color: #edf2f7;
            cursor: not-allowed;
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

        .btn-secondary {
            background-color: #edf2f7;
            color: #2d3748;
        }

        .btn-secondary:hover {
            background-color: #e2e8f0;
        }

        .form-actions {
            display: flex;
            gap: 10px;
            margin-top: 20px;
        }

        .alert {
            padding: 12px 16px;
            border-radius: 4px;
            margin-bottom: 20px;
            font-size: 14px;
        }

        .alert-success {
            background-color: #f0fff4;
            color: #22543d;
            border: 1px solid #c6f6d5;
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
<body onload="getAdminType()">
<div class="container">
    <!-- 页面标题卡片 -->
    <div class="card">
        <div class="card-header">
            <h2 class="card-title">管理员详情</h2>
        </div>

        <!-- 表单卡片 -->
        <div class="card">
            <!-- 成功消息提示 -->
            <c:if test="${not empty updateMessage}">
                <div class="alert alert-success">
                        ${updateMessage}
                </div>
            </c:if>

            <!-- 管理员表单 -->
            <form method="post" action="updateAdminServlet">
                <div class="form-group">
                    <label for="adminId" class="form-label">管理员ID</label>
                    <input type="text" id="adminId" name="adminId" class="form-control"
                           contentEditable="false" value="${admin.id}" readonly>
                </div>

                <div class="form-group">
                    <label for="adminName" class="form-label">管理员姓名</label>
                    <input type="text" id="adminName" name="adminName" class="form-control"
                           value="${admin.adminName}">
                </div>

                <input type="hidden" id="typeId" name="typeId" value="${admin.adminType}">

                <div class="form-group">
                    <label for="adminTypeId" class="form-label">管理级别</label>
                    <select id="adminTypeId" name="adminTypeId" class="form-control">
                        <c:forEach var="adminType" items="${adminTypes}">
                            <option value="${adminType.adminTypeId}"
                                ${admin.adminType == adminType.adminTypeId ? 'selected' : ''}>
                                    ${adminType.typeName}
                            </option>
                        </c:forEach>
                    </select>
                </div>

                <div class="form-group">
                    <label for="loginName" class="form-label">登录名</label>
                    <input type="text" id="loginName" name="loginName" class="form-control"
                           value="${admin.loginName}">
                </div>

                <div class="form-group">
                    <label for="loginPwd" class="form-label">登录密码</label>
                    <input type="password" id="loginPwd" name="loginPwd" class="form-control"
                           value="${admin.loginPwd}">
                </div>

                <!-- 表单操作按钮 -->
                <div class="form-actions">
                    <a href="getAdminPagerServlet" class="btn btn-secondary">
                        <i class="fas fa-arrow-left mr-2"></i>返回列表
                    </a>
                    <button type="submit" name="update" class="btn btn-primary">
                        <i class="fas fa-save mr-2"></i>保存修改
                    </button>
                </div>
            </form>
        </div>
    </div>
</div>

<script>
    // 获取管理员类型数据
    function getAdminType() {
        var url = "getAdminType";
        sendRequest(url);
    }

    var req;
    function sendRequest(url) {
        if (window.XMLHttpRequest) {
            req = new XMLHttpRequest();
        } else if (window.ActiveXObject) {
            req = new ActiveXObject("Microsoft.XMLHTTP");
        }

        req.onreadystatechange = showAdminType;
        req.open("get", url, true);
        req.send(null);
    }

    function showAdminType() {
        if (req.readyState == 4) {
            if (req.status == 200) {
                var TypeXml = req.responseXML;
                var adminTypes = TypeXml.getElementsByTagName("adminType");
                var adminTypeId = document.getElementById("adminTypeId");

                if (adminTypes.length > 0) {
                    // 清空现有选项
                    while (adminTypeId.firstChild) {
                        adminTypeId.removeChild(adminTypeId.firstChild);
                    }

                    // 添加新选项
                    for (var i = 0; i < adminTypes.length; i++) {
                        var TypeId = adminTypes[i].getElementsByTagName("adminTypeId").item(0).firstChild.data;
                        var adminTypeName = adminTypes[i].getElementsByTagName("adminTypeName").item(0).firstChild.data;

                        var op = document.createElement("option");
                        op.setAttribute("value", TypeId);
                        var txt = document.createTextNode(adminTypeName);
                        op.appendChild(txt);

                        adminTypeId.appendChild(op);
                    }

                    // 设置当前选中项
                    var nowType = document.getElementById("typeId").value;
                    for (var i = 0; i < adminTypeId.options.length; i++) {
                        if (adminTypeId.options[i].value == nowType) {
                            adminTypeId.selectedIndex = i;
                            return;
                        }
                    }
                } else {
                    var typeDiv = document.getElementById("typeDiv");
                    if (typeDiv) {
                        typeDiv.innerHTML = "还未设置管理级别";
                    }
                }
            }
        }
    }
</script>
</body>
</html>
