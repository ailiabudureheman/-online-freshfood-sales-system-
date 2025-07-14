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
    <title>添加商品</title>

    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">

    <!-- 引入Tailwind CSS -->
    <script src="https://cdn.tailwindcss.com"></script>
    <!-- 引入Font Awesome -->
    <link href="https://cdn.jsdelivr.net/npm/font-awesome@4.7.0/css/font-awesome.min.css" rel="stylesheet">

    <!-- 自定义Tailwind配置 -->
    <script>
        tailwind.config = {
            theme: {
                extend: {
                    colors: {
                        primary: '#3B82F6',
                        secondary: '#64748B',
                        accent: '#10B981',
                        danger: '#EF4444',
                        warning: '#F59E0B',
                        info: '#06B6D4',
                        light: '#F8FAFC',
                        dark: '#1E293B'
                    },
                    fontFamily: {
                        sans: ['Inter', 'system-ui', 'sans-serif'],
                    },
                }
            }
        }
    </script>

    <style type="text/tailwindcss">
        @layer utilities {
            .content-auto {
                content-visibility: auto;
            }
            .form-input-focus {
                @apply ring-2 ring-primary/30 border-primary;
            }
            .form-error {
                @apply text-danger text-sm mt-1 hidden;
            }
            .form-group.has-error .form-error {
                @apply block;
            }
            .form-group.has-error input,
            .form-group.has-error select {
                @apply border-danger;
            }
            .fade-in {
                animation: fadeIn 0.3s ease-out forwards;
            }
            @keyframes fadeIn {
                from { opacity: 0; transform: translateY(10px); }
                to { opacity: 1; transform: translateY(0); }
            }
            .file-drop-active {
                @apply border-primary bg-primary/5;
            }
        }
    </style>
</head>
<body class="bg-gray-50 font-sans text-gray-800 min-h-screen">
<div class="container mx-auto px-4 py-8 max-w-4xl">
    <!-- 顶部导航 -->
    <div class="mb-8">
        <div class="flex items-center justify-between">
            <div class="text-2xl font-bold text-gray-800">
                <i class="fa fa-shopping-bag mr-2 text-primary"></i>商品管理系统
            </div>
            <div class="flex space-x-4">
                <a href="#" class="text-gray-600 hover:text-primary transition-colors duration-200">
                    <i class="fa fa-home mr-1"></i>首页
                </a>
                <a href="#" class="text-primary font-medium">
                    <i class="fa fa-plus-circle mr-1"></i>添加商品
                </a>
            </div>
        </div>
    </div>

    <!-- 错误消息提示 -->
    <% if (request.getAttribute("message") != null) { %>
    <div class="alert alert-error bg-red-50 border border-red-200 text-red-700 px-4 py-3 rounded-lg mb-6 fade-in">
        <div class="flex items-center">
            <i class="fa fa-exclamation-circle mr-2"></i>
            <span><%= request.getAttribute("message") %></span>
        </div>
    </div>
    <% } %>

    <!-- 添加商品表单卡片 -->
    <div class="bg-white rounded-xl shadow-lg p-6 mb-8 fade-in">
        <div class="border-b border-gray-200 pb-4 mb-6">
            <h2 class="text-xl font-semibold text-gray-800">
                <i class="fa fa-plus-circle text-primary mr-2"></i>添加商品
            </h2>
        </div>

        <form action="addGoodsServlet" method="post" enctype="multipart/form-data" class="space-y-6">
            <!-- 商品分类部分 -->
            <div class="grid grid-cols-1 md:grid-cols-2 gap-6">
                <div class="form-group">
                    <label for="superTypeId" class="block text-sm font-medium text-gray-700 mb-1">
                        商品大类 <span class="text-danger">*</span>
                    </label>
                    <div class="relative">
                        <select id="superTypeId" name="superTypeId" class="form-control w-full px-4 py-2 border border-gray-300 rounded-lg focus:outline-none focus:form-input-focus transition-all duration-200 appearance-none">
                            <option value="0">--请选择大类--</option>
                            <c:forEach var="superType" items="${superTypes }">
                                <option value="${superType.superTypeId }">${superType.typeName }</option>
                            </c:forEach>
                        </select>
                        <div class="absolute inset-y-0 right-0 flex items-center px-2 pointer-events-none text-gray-500">
                            <i class="fa fa-chevron-down"></i>
                        </div>
                    </div>
                    <div class="form-error" id="superTypeError"></div>
                </div>

                <div class="form-group">
                    <label for="subTypeId" class="block text-sm font-medium text-gray-700 mb-1">
                        商品小类 <span class="text-danger">*</span>
                    </label>
                    <div class="relative">
                        <select id="subTypeId" name="subTypeId" class="form-control w-full px-4 py-2 border border-gray-300 rounded-lg focus:outline-none focus:form-input-focus transition-all duration-200 appearance-none">
                            <option value="0">--请先选择大类--</option>
                        </select>
                        <div class="absolute inset-y-0 right-0 flex items-center px-2 pointer-events-none text-gray-500">
                            <i class="fa fa-chevron-down"></i>
                        </div>
                    </div>
                    <div class="form-error" id="subTypeError"></div>
                </div>
            </div>

            <!-- 商品基本信息 -->
            <div class="space-y-6">
                <div class="form-group">
                    <label for="goodsName" class="block text-sm font-medium text-gray-700 mb-1">
                        商品名称 <span class="text-danger">*</span>
                    </label>
                    <input type="text" id="goodsName" name="goodsName" class="form-control w-full px-4 py-2 border border-gray-300 rounded-lg focus:outline-none focus:form-input-focus transition-all duration-200" placeholder="请输入商品名称" required>
                    <div class="form-error" id="goodsNameError"></div>
                </div>

                <div class="form-group">
                    <label for="introduce" class="block text-sm font-medium text-gray-700 mb-1">
                        商品介绍
                    </label>
                    <textarea id="introduce" name="introduce" rows="3" class="form-control w-full px-4 py-2 border border-gray-300 rounded-lg focus:outline-none focus:form-input-focus transition-all duration-200 resize-none" placeholder="请输入商品介绍信息"></textarea>
                </div>

                <div class="grid grid-cols-1 md:grid-cols-2 gap-6">
                    <div class="form-group">
                        <label for="publisher" class="block text-sm font-medium text-gray-700 mb-1">
                            产地 <span class="text-danger">*</span>
                        </label>
                        <input type="text" id="publisher" name="publisher" class="form-control w-full px-4 py-2 border border-gray-300 rounded-lg focus:outline-none focus:form-input-focus transition-all duration-200" placeholder="请输入商品产地" required>
                        <div class="form-error" id="publisherError"></div>
                    </div>

                    <div class="form-group">
                        <label for="author" class="block text-sm font-medium text-gray-700 mb-1">
                            品牌 <span class="text-danger">*</span>
                        </label>
                        <input type="text" id="author" name="author" class="form-control w-full px-4 py-2 border border-gray-300 rounded-lg focus:outline-none focus:form-input-focus transition-all duration-200" placeholder="请输入商品品牌" required>
                        <div class="form-error" id="authorError"></div>
                    </div>
                </div>

                <div class="grid grid-cols-1 md:grid-cols-2 gap-6">
                    <div class="form-group">
                        <label for="price" class="block text-sm font-medium text-gray-700 mb-1">
                            原价 <span class="text-danger">*</span>
                        </label>
                        <div class="relative">
                            <span class="absolute inset-y-0 left-0 flex items-center pl-3 text-gray-500">¥</span>
                            <input type="text" id="price" name="price" class="form-control w-full pl-8 pr-4 py-2 border border-gray-300 rounded-lg focus:outline-none focus:form-input-focus transition-all duration-200" placeholder="请输入商品原价" required>
                        </div>
                        <div class="form-error" id="priceError"></div>
                    </div>

                    <div class="form-group">
                        <label for="nowPrice" class="block text-sm font-medium text-gray-700 mb-1">
                            现价 <span class="text-danger">*</span>
                        </label>
                        <div class="relative">
                            <span class="absolute inset-y-0 left-0 flex items-center pl-3 text-gray-500">¥</span>
                            <input type="text" id="nowPrice" name="nowPrice" class="form-control w-full pl-8 pr-4 py-2 border border-gray-300 rounded-lg focus:outline-none focus:form-input-focus transition-all duration-200" placeholder="请输入商品现价" required>
                        </div>
                        <div class="form-error" id="nowPriceError"></div>
                    </div>
                </div>
            </div>

            <!-- 商品图片上传 -->
            <div class="form-group">
                <label class="block text-sm font-medium text-gray-700 mb-1">
                    商品图片
                </label>
                <div id="fileUpload" class="border-2 border-dashed border-gray-300 rounded-lg p-8 text-center hover:border-primary transition-colors duration-200 cursor-pointer file-drop-active">
                    <i class="fa fa-cloud-upload text-3xl text-gray-400 mb-2"></i>
                    <p class="text-gray-600">点击或拖拽文件到此处上传</p>
                    <p class="text-xs text-gray-500 mt-1">支持JPG、PNG、GIF格式，最大5MB</p>
                    <input type="file" id="picture" name="picture" accept="image/*" class="hidden">
                </div>
                <div id="fileName" class="mt-2 hidden">
                    <div class="flex items-center text-sm text-primary">
                        <i class="fa fa-check-circle mr-2"></i>
                        <span id="selectedFileName">已选择文件: filename.jpg</span>
                    </div>
                </div>
            </div>

            <!-- 商品类型 -->
            <div class="form-group">
                <label class="block text-sm font-medium text-gray-700 mb-3">
                    商品类型
                </label>
                <div class="grid grid-cols-2 md:grid-cols-4 gap-4">
                    <div class="flex items-center">
                        <input type="checkbox" id="hotGoods" name="hotGoods" value="1" class="h-4 w-4 text-primary focus:ring-primary border-gray-300 rounded">
                        <label for="hotGoods" class="ml-2 block text-sm text-gray-700">热卖</label>
                    </div>
                    <div class="flex items-center">
                        <input type="checkbox" id="newGoods" name="newGoods" value="1" class="h-4 w-4 text-primary focus:ring-primary border-gray-300 rounded">
                        <label for="newGoods" class="ml-2 block text-sm text-gray-700">新品</label>
                    </div>
                    <div class="flex items-center">
                        <input type="checkbox" id="saleGoods" name="saleGoods" value="1" class="h-4 w-4 text-primary focus:ring-primary border-gray-300 rounded">
                        <label for="saleGoods" class="ml-2 block text-sm text-gray-700">特价</label>
                    </div>
                    <div class="flex items-center">
                        <input type="checkbox" id="specialGoods" name="specialGoods" value="1" class="h-4 w-4 text-primary focus:ring-primary border-gray-300 rounded">
                        <label for="specialGoods" class="ml-2 block text-sm text-gray-700">特别推荐</label>
                    </div>
                </div>
            </div>

            <!-- 隐藏的ISBN字段 -->
            <input type="hidden" id="isbn" name="isbn">

            <!-- 操作按钮 -->
            <div class="flex justify-end space-x-3 pt-4 border-t border-gray-200">
                <button type="button" id="resetBtn" class="px-6 py-2 border border-gray-300 rounded-lg text-gray-700 bg-white hover:bg-gray-50 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-primary transition-all duration-200 flex items-center">
                    <i class="fa fa-refresh mr-2"></i>重置
                </button>
                <button type="button" id="submitBtn" class="px-6 py-2 bg-primary border border-transparent rounded-lg text-white hover:bg-primary/90 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-primary transition-all duration-200 flex items-center">
                    <i class="fa fa-save mr-2"></i>添加商品
                </button>
            </div>
        </form>
    </div>

    <!-- 页脚 -->
    <footer class="text-center text-gray-500 text-sm py-4">
        <p>© 2025 商品管理系统 | 版权所有</p>
    </footer>
</div>

<script>
    // 页面加载时获取大类
    document.addEventListener('DOMContentLoaded', function() {
        getSuperType();

        // 文件上传区域交互
        const fileUpload = document.getElementById('fileUpload');
        const fileInput = document.getElementById('picture');
        const fileName = document.getElementById('fileName');
        const selectedFileName = document.getElementById('selectedFileName');

        fileUpload.addEventListener('click', () => fileInput.click());
        fileUpload.addEventListener('dragover', (e) => {
            e.preventDefault();
            fileUpload.classList.add('file-drop-active');
        });
        fileUpload.addEventListener('dragleave', () => {
            fileUpload.classList.remove('file-drop-active');
        });
        fileUpload.addEventListener('drop', (e) => {
            e.preventDefault();
            fileUpload.classList.remove('file-drop-active');
            if (e.dataTransfer.files.length) {
                fileInput.files = e.dataTransfer.files;
                updateFileName();
            }
        });

        fileInput.addEventListener('change', updateFileName);

        function updateFileName() {
            if (fileInput.files.length) {
                selectedFileName.textContent = `已选择文件: ${fileInput.files[0].name}`;
                fileName.classList.remove('hidden');
            } else {
                fileName.classList.add('hidden');
            }
        }

        // 重置按钮动画
        document.getElementById('resetBtn').addEventListener('click', function() {
            this.classList.add('animate-pulse');
            setTimeout(() => this.classList.remove('animate-pulse'), 500);
            resetForm();
        });

        // 提交按钮动画
        document.getElementById('submitBtn').addEventListener('click', function() {
            this.classList.add('animate-pulse');
            setTimeout(() => this.classList.remove('animate-pulse'), 500);
            add();
        });
    });

    // 获取大类
    var req;
    function getSuperType() {
        var url = "getSuperType";
        sendSuperTypeRequest(url);
    }

    function sendSuperTypeRequest(url) {
        req = new XMLHttpRequest();
        req.onreadystatechange = showSuper;
        req.open("GET", url, true);
        req.send(null);
    }

    function showSuper() {
        if (req.readyState === 4 && req.status === 200) {
            var subTypeXml = req.responseXML;
            var superTypes = subTypeXml.getElementsByTagName("super");
            var superTypeId = document.getElementById("superTypeId");

            if (superTypes.length > 0) {
                // 清空原有选项
                while (superTypeId.options.length > 1) {
                    superTypeId.remove(1);
                }

                // 添加新选项
                for (var i = 0; i < superTypes.length; i++) {
                    var superId = superTypes[i].getElementsByTagName("superId").item(0).firstChild.data;
                    var superName = superTypes[i].getElementsByTagName("superName").item(0).firstChild.data;
                    var option = document.createElement("option");
                    option.value = superId;
                    option.textContent = superName;
                    superTypeId.appendChild(option);
                }
            } else {
                document.getElementById("superTypeError").textContent = "还没有可用的大类";
                document.getElementById("superTypeError").classList.remove('hidden');
            }
        }
    }

    // 获取小类并生成ISBN
    function getSubType() {
        var superTypeId = document.getElementById("superTypeId");
        var subTypeId = document.getElementById("subTypeId");
        var selectedSuperId = superTypeId.value;

        // 清空小类选择框
        while (subTypeId.options.length > 1) {
            subTypeId.remove(1);
        }

        if (selectedSuperId === "0") {
            subTypeId.options[0].textContent = "--请先选择大类--";
            document.getElementById("subTypeError").textContent = "请先选择商品大类";
            document.getElementById("subTypeError").classList.remove('hidden');
            return;
        }

        subTypeId.options[0].textContent = "--加载中小类--";

        var url = "getSubTypeBySuperTypeId?superTypeId=" + selectedSuperId;
        sendRequest(url);
    }

    function sendRequest(url) {
        req = new XMLHttpRequest();
        req.onreadystatechange = showSub;
        req.open("GET", url, true);
        req.send(null);
    }

    function showSub() {
        if (req.readyState === 4 && req.status === 200) {
            var subTypeXml = req.responseXML;
            var subTypes = subTypeXml.getElementsByTagName("subType");
            var subTypeId = document.getElementById("subTypeId");
            var subTypeError = document.getElementById("subTypeError");

            // 清空原有选项
            while (subTypeId.options.length > 1) {
                subTypeId.remove(1);
            }

            if (subTypes.length > 0) {
                subTypeId.options[0].textContent = "--请选择小类--";

                // 添加新选项
                for (var i = 0; i < subTypes.length; i++) {
                    var subId = subTypes[i].getElementsByTagName("subTypeId").item(0).firstChild.data;
                    var subName = subTypes[i].getElementsByTagName("subTypeName").item(0).firstChild.data;
                    var option = document.createElement("option");
                    option.value = subId;
                    option.textContent = subName;
                    subTypeId.appendChild(option);
                }

                subTypeError.classList.add('hidden');
            } else {
                subTypeId.options[0].textContent = "--该大类下没有小类--";
                subTypeError.textContent = "该大类下没有可用的小类";
                subTypeError.classList.remove('hidden');
            }
        }
    }

    // 生成ISBN
    function generateISBN() {
        var subTypeId = document.getElementById("subTypeId");
        var isbnInput = document.getElementById("isbn");

        if (subTypeId.value > 0) {
            // 示例：ISBN格式为 ST-STID-SUBID-YYYYMMDD，可根据实际需求调整
            var today = new Date();
            var year = today.getFullYear();
            var month = String(today.getMonth() + 1).padStart(2, '0');
            var day = String(today.getDate()).padStart(2, '0');

            // 使用大类和小类ID生成ISBN
            var superTypeId = document.getElementById("superTypeId").value;
            var isbn = "ST-" + superTypeId + "-" + subTypeId.value + "-" + year + month + day;
            isbnInput.value = isbn;
        } else {
            isbnInput.value = "";
        }
    }

    // 检查商品名
    var goodsName_IsExist = false;
    function checkGoodsName() {
        var goodsName = document.getElementById("goodsName");
        var errorElement = document.getElementById("goodsNameError");
        var formGroup = goodsName.closest('.form-group');

        if (!goodsName.value.trim()) {
            errorElement.textContent = "商品名不能为空";
            formGroup.classList.add('has-error');
            goodsName_IsExist = true;
        } else {
            checkGoodsNameIsExist();
        }
    }

    function checkGoodsNameIsExist() {
        var goodsName = document.getElementById("goodsName");
        var url = "checkGoodsNameIsExist?goodsName=" + encodeURIComponent(goodsName.value);

        req = new XMLHttpRequest();
        req.onreadystatechange = function() {
            if (req.readyState === 4 && req.status === 200) {
                var returnXml = req.responseXML;
                var errorElement = document.getElementById("goodsNameError");
                var formGroup = goodsName.closest('.form-group');
                var state = returnXml.getElementsByTagName("state")[0].firstChild.data;
                var content = returnXml.getElementsByTagName("content")[0].firstChild.data;

                if (state === "true") {
                    errorElement.textContent = content;
                    formGroup.classList.add('has-error');
                    goodsName_IsExist = true;
                } else {
                    errorElement.textContent = "";
                    formGroup.classList.remove('has-error');
                    goodsName_IsExist = false;
                }
            }
        };

        req.open("GET", url, true);
        req.send(null);
    }

    // 检查产地
    function checkPublisher() {
        var publisher = document.getElementById("publisher");
        var errorElement = document.getElementById("publisherError");
        var formGroup = publisher.closest('.form-group');

        if (!publisher.value.trim()) {
            errorElement.textContent = "产地不能为空";
            formGroup.classList.add('has-error');
            return false;
        } else {
            errorElement.textContent = "";
            formGroup.classList.remove('has-error');
            return true;
        }
    }

    // 检查品牌
    function checkAuthor() {
        var author = document.getElementById("author");
        var errorElement = document.getElementById("authorError");
        var formGroup = author.closest('.form-group');

        if (!author.value.trim()) {
            errorElement.textContent = "品牌不能为空";
            formGroup.classList.add('has-error');
            return false;
        } else {
            errorElement.textContent = "";
            formGroup.classList.remove('has-error');
            return true;
        }
    }

    // 检查原价
    function checkPrice() {
        var price = document.getElementById("price");
        var errorElement = document.getElementById("priceError");
        var formGroup = price.closest('.form-group');
        var pattern = /^[1-9]\d*(\.\d+)?$/;

        if (!price.value.trim()) {
            errorElement.textContent = "原价不能为空";
            formGroup.classList.add('has-error');
            return false;
        } else if (!pattern.test(price.value)) {
            errorElement.textContent = "请输入有效的价格（正数）";
            formGroup.classList.add('has-error');
            return false;
        } else {
            errorElement.textContent = "";
            formGroup.classList.remove('has-error');
            return true;
        }
    }

    // 检查现价
    function checkNowPrice() {
        var nowPrice = document.getElementById("nowPrice");
        var errorElement = document.getElementById("nowPriceError");
        var formGroup = nowPrice.closest('.form-group');
        var pattern = /^[1-9]\d*(\.\d+)?$/;
        var price = parseFloat(document.getElementById("price").value);
        var nowPriceValue = parseFloat(nowPrice.value);

        if (!nowPrice.value.trim()) {
            errorElement.textContent = "现价不能为空";
            formGroup.classList.add('has-error');
            return false;
        } else if (!pattern.test(nowPrice.value)) {
            errorElement.textContent = "请输入有效的价格（正数）";
            formGroup.classList.add('has-error');
            return false;
        } else if (price && nowPriceValue >= price) {
            errorElement.textContent = "现价必须小于原价";
            formGroup.classList.add('has-error');
            return false;
        } else {
            errorElement.textContent = "";
            formGroup.classList.remove('has-error');
            return true;
        }
    }

    // 重置表单
    function resetForm() {
        var form = document.querySelector('form');
        form.reset();

        // 清除所有错误提示
        document.querySelectorAll('.form-group.has-error').forEach(group => {
            group.classList.remove('has-error');
        });

        document.querySelectorAll('.form-error').forEach(error => {
            error.textContent = "";
            error.classList.add('hidden');
        });

        // 重置文件上传状态
        document.getElementById('fileName').classList.add('hidden');

        // 重置小类选择框
        var subTypeId = document.getElementById("subTypeId");
        while (subTypeId.options.length > 1) {
            subTypeId.remove(1);
        }
        subTypeId.options[0].textContent = "--请先选择大类--";
    }

    // 添加商品
    function add() {
        // 验证表单
        var isValid = true;

        // 验证大类和小类
        var superTypeId = document.getElementById("superTypeId");
        var subTypeId = document.getElementById("subTypeId");
        var superTypeError = document.getElementById("superTypeError");
        var subTypeError = document.getElementById("subTypeError");

        if (superTypeId.value === "0") {
            superTypeError.textContent = "请选择商品大类";
            superTypeError.classList.remove('hidden');
            isValid = false;
        } else {
            superTypeError.classList.add('hidden');
        }

        if (subTypeId.value === "0") {
            subTypeError.textContent = "请选择商品小类";
            subTypeError.classList.remove('hidden');
            isValid = false;
        } else {
            subTypeError.classList.add('hidden');
        }

        // 验证其他字段
        if (goodsName_IsExist || !checkPublisher() || !checkAuthor() || !checkPrice() || !checkNowPrice()) {
            isValid = false;
        }

        // 如果验证通过，提交表单
        if (isValid) {
            var form = document.querySelector('form');
            form.submit();
        } else {
            // 滚动到第一个错误字段
            var firstError = document.querySelector('.form-group.has-error');
            if (firstError) {
                firstError.scrollIntoView({ behavior: 'smooth', block: 'center' });
            }

            // 显示提示
            alert("请检查并修正表单中的错误");
        }
    }
</script>
</body>
</html>