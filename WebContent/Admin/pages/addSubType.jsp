<%@ page language="java" pageEncoding="GBK"%>
<%@ include file="tools.jsp" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <base href="<%=basePath%>">
    <title>添加小类</title>

    <meta charset="GBK">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">

    <!-- 引入现代化样式和图标库 -->
    <link href="Admin/css/style.css" rel="stylesheet" type="text/css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">

    <!-- 添加小类页面专用样式 -->
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

        .form-select {
            width: 100%;
            padding: 10px 12px;
            border: 1px solid #e2e8f0;
            border-radius: 4px;
            font-size: 14px;
            background-color: #fff;
            transition: border-color 0.3s, box-shadow 0.3s;
        }

        .form-select:focus {
            border-color: #3182ce;
            box-shadow: 0 0 0 3px rgba(49, 130, 206, 0.1);
            outline: none;
        }

        .form-error {
            color: #e53e3e;
            font-size: 13px;
            margin-top: 5px;
            display: none;
        }

        .form-group.has-error .form-control,
        .form-group.has-error .form-select {
            border-color: #e53e3e;
        }

        .form-group.has-error .form-error {
            display: block;
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
<body onload="getSuperType()">
<div class="container">
    <!-- 错误消息提示 -->
    <% if (request.getAttribute("message") != null) { %>
    <div class="alert alert-error">
        <i class="fas fa-exclamation-circle mr-2"></i>
        <%= request.getAttribute("message") %>
    </div>
    <% } %>

    <!-- 添加小类表单卡片 -->
    <div class="card">
        <div class="card-header">
            <h2 class="card-title">添加小类</h2>
        </div>

        <form action="addSubTypeServlet" method="post">
            <div class="form-group">
                <label for="subTypeName">小类名称</label>
                <input type="text" id="subTypeName" name="subTypeName" class="form-control" onblur="checkSubTypeName()" placeholder="请输入小类名称">
                <div class="form-error" id="subTypeNameDiv"></div>
            </div>

            <div class="form-group">
                <label for="superTypeId">所属大类</label>
                <select id="superTypeId" name="superTypeId" class="form-select">
                    <option value="0">--请选择大类--</option>
                    <c:forEach var="superType" items="${superTypes }">
                        <option value="${superType.superTypeId}">${superType.typeName}</option>
                    </c:forEach>
                </select>
            </div>

            <div class="form-actions">
                <button type="button" class="btn btn-secondary" onclick="resetForm()">
                    <i class="fas fa-refresh mr-2"></i>重置
                </button>
                <button type="button" class="btn btn-primary" onclick="addSubType()">
                    <i class="fas fa-save mr-2"></i>添加
                </button>
            </div>
        </form>
    </div>
</div>

<script>
    // 获取大类
    var req;
    function getSuperType() {
        var url = "getSuperType";
        sendRequest(url);
    }

    function sendRequest(url) {
        if (window.XMLHttpRequest) {
            req = new XMLHttpRequest();
        } else if (window.ActiveXObject) {
            req = new ActiveXObject("Microsoft.XMLHTTP");
        }

        req.onreadystatechange = showSuper;
        req.open("get", url, true);
        req.send(null);
    }

    function showSuper() {
        if (req.readyState == 4) {
            if (req.status == 200) {
                var subTypeXml = req.responseXML;
                var superTypes = subTypeXml.getElementsByTagName("super");
                var superTypeId = document.getElementById("superTypeId");

                if (superTypes.length > 0) {
                    for (var i = 0; i < superTypes.length; i++) {
                        var superId = superTypes[i].getElementsByTagName("superId").item(0).firstChild.data;
                        var superName = superTypes[i].getElementsByTagName("superName").item(0).firstChild.data;
                        var op = document.createElement("option");
                        op.setAttribute("value", superId);
                        var txt = document.createTextNode(superName);
                        op.appendChild(txt);
                        superTypeId.appendChild(op);
                        superTypeId.style.width = "auto";
                    }
                } else {
                    document.getElementById("typeDiv").innerHTML = "还没有大类";
                }
            }
        }
    }

    // 检查小类名
    function checkSubTypeName() {
        var subTypeName = document.getElementById("subTypeName");
        var subTypeNameDiv = document.getElementById("subTypeNameDiv");
        var formGroup = subTypeName.closest('.form-group');

        if (subTypeName.value == "") {
            subTypeNameDiv.innerHTML = "小类名不能为空";
            formGroup.classList.add('has-error');
        } else {
            checkSubTypeNameIsExist();
        }
    }

    function checkSubTypeNameIsExist() {
        var subTypeName = document.getElementById("subTypeName");
        var url = "checkSubTypeIsExist?subTypeName=" + encodeURIComponent(subTypeName.value);
        sendSubTypeName(url);
    }

    function sendSubTypeName(url) {
        if (window.XMLHttpRequest) {
            req = new XMLHttpRequest();
        } else if (window.ActiveXObject) {
            req = new ActiveXObject("Microsoft.XMLHTTP");
        }

        req.onreadystatechange = subTypeNameIsExist;
        req.open("get", url, true);
        req.send(null);
    }

    var subTypeName_IsExist;
    function subTypeNameIsExist() {
        if (req.readyState == 4) {
            if (req.status == 200) {
                var returnXml = req.responseXML;
                var subTypeNameDiv = document.getElementById("subTypeNameDiv");
                var formGroup = document.getElementById("subTypeName").closest('.form-group');
                var state = returnXml.getElementsByTagName("state")[0].firstChild.data;
                var content = returnXml.getElementsByTagName("content")[0].firstChild.data;

                if (state == "true") {
                    subTypeNameDiv.innerHTML = content;
                    formGroup.classList.add('has-error');
                    subTypeName_IsExist = true;
                } else {
                    subTypeNameDiv.innerHTML = "";
                    formGroup.classList.remove('has-error');
                    subTypeName_IsExist = false;
                }
            }
        }
    }

    // 添加小类
    function addSubType() {
        var oForm = document.getElementsByTagName("form")[0];
        var superTypeId = document.getElementById("superTypeId");

        if (superTypeId.value == "0") {
            alert("请选择所属大类");
            return;
        }

        if (!subTypeName_IsExist) {
            oForm.submit();
        }
    }

    // 重置表单
    function resetForm() {
        document.forms[0].reset();
        document.querySelectorAll('.form-group.has-error').forEach(group => {
            group.classList.remove('has-error');
        });
        document.querySelectorAll('.form-error').forEach(error => {
            error.innerHTML = "";
        });
    }
</script>
</body>
</html>
