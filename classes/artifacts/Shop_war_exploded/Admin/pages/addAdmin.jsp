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
    <title>企业管理系统 - 添加管理员</title>

    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">

    <!-- 引入现代化样式和图标库 -->
    <link href="Admin/css/style.css" rel="stylesheet" type="text/css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
    <script src="Admin/js/script.js" type="text/javascript"></script>

    <!-- 添加管理员页面专用样式 -->
    <style>
        .admin-container {
            display: flex;
            min-height: 100vh;
            background-color: #f5f7fa;
        }

        .main-content {
            flex: 1;
            padding: 20px;
            overflow-x: auto;
        }

        .page-header {
            display: flex;
            justify-content: space-between;
            align-items: center;
            margin-bottom: 20px;
        }

        .page-title {
            margin: 0;
            color: #2d3748;
        }

        .page-actions {
            display: flex;
            gap: 10px;
        }

        .btn {
            padding: 8px 16px;
            border-radius: 4px;
            font-size: 14px;
            cursor: pointer;
            transition: all 0.3s;
            border: none;
            display: flex;
            align-items: center;
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

        .card {
            background-color: #fff;
            border-radius: 8px;
            box-shadow: 0 4px 6px -1px rgba(0, 0, 0, 0.1), 0 2px 4px -1px rgba(0, 0, 0, 0.06);
            padding: 20px;
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
            font-size: 18px;
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

        .form-group.has-error .form-control {
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
        @media (max-width: 768px) {
            .sidebar {
                display: none;
            }

            .page-header {
                flex-direction: column;
                align-items: flex-start;
                gap: 10px;
            }

            .form-actions {
                flex-direction: column;
            }

            .btn {
                width: 100%;
                justify-content: center;
            }
        }
    </style>
</head>
<body>


    <!-- 主内容区 -->
    <div class="main-content">
        <!-- 页面标题和操作按钮 -->
        <div class="page-header">
            <h1 class="page-title">添加管理员</h1>
            <div class="page-actions">
                <button class="btn btn-secondary" onclick="window.history.back()">
                    <i class="fas fa-arrow-left mr-2"></i>返回
                </button>
            </div>
        </div>

        <!-- 错误消息提示 -->
        <% if (request.getAttribute("addMessage") != null) { %>
        <div class="alert alert-error">
            <i class="fas fa-exclamation-circle mr-2"></i>
            <%= request.getAttribute("addMessage") %>
        </div>
        <% } %>

        <!-- 添加管理员表单卡片 -->
        <div class="card">
            <div class="card-header">
                <h2 class="card-title">管理员信息</h2>
            </div>

            <form name="addAdmin_form" method="post" action="addAdminServlet">
                <div class="form-group">
                    <label for="adminTypeId">管理级别</label>
                    <select name="adminTypeId" id="adminTypeId" class="form-select">
                        <c:forEach var="adminType" items="${adminTypes }">
                            <option value="${adminType.adminTypeId}">${adminType.adminTypeName}</option>
                        </c:forEach>
                    </select>
                    <div class="form-error" id="TypeDiv"></div>
                </div>

                <div class="form-group">
                    <label for="adminName">真实姓名</label>
                    <input type="text" name="adminName" id="adminName" class="form-control"
                           onfocus="clearError('adminName')" onblur="checkAdminName()">
                    <div class="form-error" id="adminNameDiv"></div>
                </div>

                <div class="form-group">
                    <label for="loginName">登录账号</label>
                    <input type="text" name="loginName" id="loginName" class="form-control"
                           onblur="checkLoginName()">
                    <div class="form-error" id="loginNameDiv"></div>
                </div>

                <div class="form-group">
                    <label for="loginPwd1">登录密码</label>
                    <input type="password" name="loginPwd1" id="loginPwd1" class="form-control"
                           onfocus="clearError('loginPwd1')" onblur="checkPwd1()">
                    <div class="form-error" id="pwd1Div"></div>
                </div>

                <div class="form-group">
                    <label for="loginPwd2">确认密码</label>
                    <input type="password" name="loginPwd2" id="loginPwd2" class="form-control"
                           onfocus="clearError('loginPwd2')" onblur="checkPwd2()">
                    <div class="form-error" id="pwd2Div"></div>
                </div>

                <div class="form-actions">
                    <button type="button" class="btn btn-secondary" onclick="resetForm()">
                        <i class="fas fa-refresh mr-2"></i>重置
                    </button>
                    <button type="button" class="btn btn-primary" onclick="checkAll()">
                        <i class="fas fa-save mr-2"></i>添加
                    </button>
                </div>
            </form>
        </div>
    </div>
</div>

<script>
    // 获取管理员类型
    function getAdminType() {
        var url = "getAdminType";
        sendRequest(url);
    }

    var req;
    function sendRequest(url) {
        if(window.XMLHttpRequest) {
            req = new XMLHttpRequest();
        } else if(window.ActiveXObject) {
            req = new ActiveXObject("Microsoft.XMLHTTP");
        }

        req.onreadystatechange = showAdminType;
        req.open("get", url, true);
        req.send(null);
    }

    function showAdminType() {
        if(req.readyState == 4) {
            if(req.status == 200) {
                var TypeXml = req.responseXML;
                var adminTypes = TypeXml.getElementsByTagName("adminType");
                var adminTypeId = document.getElementById("adminTypeId");

                if(adminTypes.length > 0) {
                    for(var i = 0; i < adminTypes.length; i++) {
                        var TypeId = adminTypes[i].getElementsByTagName("adminTypeId").item(0).firstChild.data;
                        var adminTypeName = adminTypes[i].getElementsByTagName("adminTypeName").item(0).firstChild.data;
                        var op = document.createElement("option");
                        op.setAttribute("value", TypeId);
                        var txt = document.createTextNode(adminTypeName);
                        op.appendChild(txt);
                        adminTypeId.appendChild(op);
                        adminTypeId.style.width = "auto";
                    }
                } else {
                    document.getElementById("TypeDiv").innerHTML = "还未设置管理级别";
                    document.getElementById("TypeDiv").style.display = "block";
                }
            }
        }
    }

    // 检查真实姓名
    var checkAdminNameResult = false;
    function checkAdminName() {
        var adminName = document.getElementById("adminName");
        var adminNameDiv = document.getElementById("adminNameDiv");
        var formGroup = adminName.closest('.form-group');

        if(adminName.value == "") {
            adminNameDiv.innerHTML = "真实姓名不能为空";
            formGroup.classList.add('has-error');
            checkAdminNameResult = false;
        } else {
            adminNameDiv.innerHTML = "";
            formGroup.classList.remove('has-error');
            checkAdminNameResult = true;
        }
    }

    // 检查登录名是否存在
    var loginName_IsExist;
    function checkLoginNameIsExist() {
        var loginName = document.getElementById("loginName");
        var url = "checkLoginNameIsExist?loginName=" + loginName.value;
        sendLoginName(url);
    }

    function sendLoginName(url) {
        if(window.XMLHttpRequest) {
            req = new XMLHttpRequest();
        } else if(window.ActiveXObject) {
            req = new ActiveXObject("Microsoft.XMLHTTP");
        }

        req.onreadystatechange = loginNameIsExist;
        req.open("get", url, true);
        req.send(null);
    }

    function loginNameIsExist() {
        if(req.readyState == 4) {
            if(req.status == 200) {
                var returnXml = req.responseXML;
                var loginNameDiv = document.getElementById("loginNameDiv");
                var formGroup = document.getElementById("loginName").closest('.form-group');
                var state = returnXml.getElementsByTagName("state")[0].firstChild.data;
                var content = returnXml.getElementsByTagName("content")[0].firstChild.data;

                if(state == "true") {
                    loginNameDiv.innerHTML = content;
                    formGroup.classList.add('has-error');
                    loginName_IsExist = true;
                } else {
                    loginNameDiv.innerHTML = "";
                    formGroup.classList.remove('has-error');
                    loginName_IsExist = false;
                }
            }
        }
    }

    function checkLoginName() {
        var loginName = document.getElementById("loginName");
        var loginNameDiv = document.getElementById("loginNameDiv");
        var formGroup = loginName.closest('.form-group');

        if(loginName.value == "") {
            loginNameDiv.innerHTML = "登录名不能为空";
            formGroup.classList.add('has-error');
        } else {
            checkLoginNameIsExist();
        }
    }

    // 检查密码
    var checkPwd1Result = false;
    function checkPwd1() {
        var pwd1 = document.getElementById("loginPwd1");
        var pwd1Div = document.getElementById("pwd1Div");
        var formGroup = pwd1.closest('.form-group');

        if(pwd1.value == "") {
            pwd1Div.innerHTML = "密码不能为空";
            formGroup.classList.add('has-error');
            checkPwd1Result = false;
        } else {
            pwd1Div.innerHTML = "";
            formGroup.classList.remove('has-error');
            checkPwd1Result = true;
        }
    }

    var checkPwd2Result = false;
    function checkPwd2() {
        var pwd2 = document.getElementById("loginPwd2").value;
        var pwd1 = document.getElementById("loginPwd1").value;
        var pwd2Div = document.getElementById("pwd2Div");
        var formGroup = document.getElementById("loginPwd2").closest('.form-group');

        if(pwd2 == "") {
            pwd2Div.innerHTML = "密码不能为空";
            formGroup.classList.add('has-error');
            checkPwd2Result = false;
        } else {
            if(pwd2 != pwd1) {
                pwd2Div.innerHTML = "两次密码不一致";
                formGroup.classList.add('has-error');
                checkPwd2Result = false;
            } else {
                pwd2Div.innerHTML = "";
                formGroup.classList.remove('has-error');
                checkPwd2Result = true;
            }
        }
    }

    // 清除错误提示
    function clearError(fieldId) {
        var field = document.getElementById(fieldId);
        var errorDiv = document.getElementById(fieldId + 'Div');
        var formGroup = field.closest('.form-group');

        errorDiv.innerHTML = "";
        formGroup.classList.remove('has-error');
    }

    // 重置表单
    function resetForm() {
        document.addAdmin_form.reset();
        document.querySelectorAll('.form-group').forEach(group => {
            group.classList.remove('has-error');
        });
        document.querySelectorAll('.form-error').forEach(error => {
            error.innerHTML = "";
        });
    }

    // 检查所有表单数据并提交
    function checkAll() {
        // 重新验证所有字段
        checkAdminName();
        checkLoginName();
        checkPwd1();
        checkPwd2();

        // 检查是否有管理级别选项
        var adminTypeId = document.getElementById("adminTypeId");
        if(adminTypeId.options.length === 0) {
            document.getElementById("TypeDiv").innerHTML = "请先设置管理级别";
            document.getElementById("TypeDiv").style.display = "block";
            return;
        } else {
            document.getElementById("TypeDiv").style.display = "none";
        }

        // 检查所有验证结果
        if(checkAdminNameResult && !loginName_IsExist && checkPwd1Result && checkPwd2Result) {
            document.addAdmin_form.submit();
        } else {
            // 显示错误消息
            alert("请检查并修正表单中的错误");
        }
    }

    // 页面加载时获取管理级别
    window.onload = function() {
        getAdminType();
    };
</script>
</body>
</html>
