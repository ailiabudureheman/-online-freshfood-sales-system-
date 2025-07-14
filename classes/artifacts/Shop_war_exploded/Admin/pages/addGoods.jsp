<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<meta charset="UTF-8">
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

	<meta charset="GBK">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">

	<!-- 引入现代化样式和图标库 -->
	<link href="Admin/css/style.css" rel="stylesheet" type="text/css">
	<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
	<script src="Admin/js/script.js" type="text/javascript"></script>

	<!-- 添加商品页面专用样式 -->
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

		.form-check {
			display: flex;
			align-items: center;
			margin-right: 20px;
		}

		.form-check-input {
			margin-right: 8px;
		}

		.file-upload {
			border: 1px dashed #e2e8f0;
			border-radius: 4px;
			padding: 20px;
			text-align: center;
			cursor: pointer;
			transition: border-color 0.3s;
		}

		.file-upload:hover {
			border-color: #3182ce;
		}

		.file-upload i {
			font-size: 24px;
			color: #718096;
			margin-bottom: 10px;
		}

		.file-upload p {
			margin: 0;
			color: #4a5568;
		}

		.file-upload input {
			display: none;
		}

		.file-name {
			margin-top: 10px;
			text-align: left;
			font-size: 14px;
			color: #3182ce;
			display: none;
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

			.form-check {
				margin-bottom: 10px;
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

	<!-- 添加商品表单卡片 -->
	<div class="card">
		<div class="card-header">
			<h2 class="card-title">添加商品</h2>
		</div>

		<form action="addGoodsServlet" method="post">
			<div class="form-group">
				<label>选择类别</label>
				<div class="form-row">
					<div class="form-col">
						<label class="form-label">大类</label>
						<select id="superTypeId" name="superTypeId" class="form-select" onchange="getSubType()">
							<option value="0">--选择大类--</option>
							<c:forEach var="superType" items="${superTypes }">
								<option value="${superType.superTypeId }">${superType.typeName }</option>
							</c:forEach>
						</select>
					</div>
					<div class="form-col ml-2">
						<label class="form-label">小类</label>
						<select id="subTypeId" name="subTypeId" class="form-select">
							<option value="0">--选择小类--</option>
						</select>
					</div>
				</div>
				<div class="form-error" id="typeDiv"></div>
			</div>

			<div class="form-group">
				<label for="GoodsName">商品名</label>
				<input type="text" id="GoodsName" name="GoodsName" class="form-control" onblur="checkGoodsName()">
				<div class="form-error" id="GoodsNameDiv"></div>
			</div>

			<div class="form-group">
				<label for="ISBN">ISBN</label>
				<input type="text" id="ISBN" name="ISBN" class="form-control" onblur="checkISBN()">
				<div class="form-error" id="ISBNDiv"></div>
			</div>

			<div class="form-group">
				<label for="introduce">相关介绍</label>
				<textarea id="introduce" name="introduce" class="form-control" rows="3"></textarea>
			</div>

			<div class="form-group">
				<label for="pages">生产日期</label>
				<input type="text" id="pages" name="pages" class="form-control" onblur="checkPages()">
				<div class="form-error" id="pagesDiv"></div>
			</div>

			<div class="form-group">
				<label for="publisher">产地</label>
				<input type="text" id="publisher" name="publisher" class="form-control" onblur="checkPublisher()">
				<div class="form-error" id="publisherDiv"></div>
			</div>

			<div class="form-group">
				<label for="author">品牌</label>
				<input type="text" id="author" name="author" class="form-control" onblur="checkAuthor()">
				<div class="form-error" id="authorDiv"></div>
			</div>

			<div class="form-group">
				<label for="price">原价</label>
				<input type="text" id="price" name="price" class="form-control" onblur="checkPrice()">
				<div class="form-error" id="priceDiv"></div>
			</div>

			<div class="form-group">
				<label for="nowPrice">现价</label>
				<input type="text" id="nowPrice" name="nowPrice" class="form-control" onblur="checkNowPrice()">
				<div class="form-error" id="nowPriceDiv"></div>
			</div>

<%--			<div class="form-group">--%>
<%--				<label for="picture">图片</label>--%>
<%--				<div class="file-upload" id="fileUpload">--%>
<%--					<i class="fas fa-cloud-upload-alt"></i>--%>
<%--					<p>点击或拖拽文件到此处上传</p>--%>
<%--					<input type="file" id="picture" name="picture" accept="image/*">--%>
<%--				</div>--%>
<%--				<div class="file-name" id="fileName"></div>--%>
<%--			</div>--%>


			<div class="form-group">
				<label>图片</label>
				<div class="file-upload" id="fileUpload">
					<i class="fas fa-cloud-upload-alt"></i>
					<p>点击或拖拽文件到此处上传</p>
					<input type="file" id="picture" name="picture" accept="image/*" onchange="updatePicturePath(this)">
				</div>
				<input type="hidden" id="picturePath" name="picturePath"> <!-- 存储图片路径 -->
				<div id="fileName" class="mt-2 text-sm text-gray-500"></div>
			</div>

			<script>
				// 更新图片路径（示例：根据文件名生成路径）
				function updatePicturePath(input) {
					if (input.files && input.files[0]) {
						const fileName = input.files[0].name;
						document.getElementById('fileName').textContent = '已选择: ' + fileName;

						// 生成图片路径（根据您的服务器目录结构调整）
						const picturePath = 'images/' + fileName;
						document.getElementById('picturePath').value = picturePath;
					}
				}
			</script>





<%--			<div class="form-group">--%>
<%--				<label for="GoodsNum">数量</label>--%>
<%--				<input type="text" id="GoodsNum" name="GoodsNum" class="form-control" onblur="checkGoodsNum()">--%>
<%--				<div class="form-error" id="GoodsNumDiv"></div>--%>
<%--			</div>--%>

			<div class="form-group">
				<label>类型</label>
				<div class="form-checks">
					<div class="form-check">
						<input type="checkbox" class="form-check-input" name="hotGoods" value="1" id="hotGoods">
						<label class="form-check-label" for="hotGoods">热卖</label>
					</div>
					<div class="form-check">
						<input type="checkbox" class="form-check-input" name="newGoods" value="1" id="newGoods">
						<label class="form-check-label" for="newGoods">新品</label>
					</div>
					<div class="form-check">
						<input type="checkbox" class="form-check-input" name="saleGoods" value="1" id="saleGoods">
						<label class="form-check-label" for="saleGoods">特价</label>
					</div>
					<div class="form-check">
						<input type="checkbox" class="form-check-input" name="specialGoods" value="1" id="specialGoods">
						<label class="form-check-label" for="specialGoods">特别推荐</label>
					</div>
				</div>
			</div>

			<div class="form-actions">
				<button type="button" class="btn btn-secondary" onclick="resetForm()">
					<i class="fas fa-refresh mr-2"></i>重置
				</button>
				<button type="button" class="btn btn-primary" onclick="add()">
					<i class="fas fa-save mr-2"></i>添加
				</button>
			</div>
		</form>
	</div>
</div>

<script>
	// 页面加载时获取大类
	window.onload = function() {
		getSuperType();
	};

	// 获取大类
	var req;
	function getSuperType() {
		var url = "getSuperType";
		sendSuperTypeRequest(url);
	}

	function sendSuperTypeRequest(url) {
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

	// 获取小类
	function getSubType() {
		var id;
		var superType = document.getElementById("superTypeId");
		for (var i = 0; i < superType.options.length; i++) {
			if (superType.options[i].selected) {
				id = superType.options[i].value;
			}
		}
		var url = "getSubTypeBySuperTypeId?superTypeId=" + id;
		sendRequest(url);
	}

	function sendRequest(url) {
		if (window.XMLHttpRequest) {
			req = new XMLHttpRequest();
		} else if (window.ActiveXObject) {
			req = new ActiveXObject("Microsoft.XMLHTTP");
		}

		req.onreadystatechange = showSub;
		req.open("get", url, true);
		req.send(null);
	}

	function clearSubType() {
		var subType = document.getElementById("subTypeId");
		for (var i = subType.options.length - 1; i >= 0; i--) {
			subType.options[i].parentNode.removeChild(subType.options[i]);
		}
		subType.style.width = "";
	}

	function showSub() {
		if (req.readyState == 4) {
			if (req.status == 200) {
				var subTypeXml = req.responseXML;
				clearSubType();
				var subTypes = subTypeXml.getElementsByTagName("subType");
				var subType = document.getElementById("subTypeId");
				var typeDiv = document.getElementById("typeDiv");

				if (subTypes.length > 0) {
					for (var i = 0; i < subTypes.length; i++) {
						var subTypeId = subTypes[i].getElementsByTagName("subTypeId").item(0).firstChild.data;
						var subTypeName = subTypes[i].getElementsByTagName("subTypeName").item(0).firstChild.data;
						var op = document.createElement("option");
						op.setAttribute("value", subTypeId);
						var txt = document.createTextNode(subTypeName);
						op.appendChild(txt);
						subType.appendChild(op);
						subType.style.width = "auto";
						typeDiv.innerHTML = "";
					}
				} else {
					typeDiv.innerHTML = "请选择大类";
				}
			}
		}
	}

	// 检查商品名是否存在
	var GoodsName_IsExist;
	function checkGoodsNameIsExist() {
		var GoodsName = document.getElementById("GoodsName");
		var url = "checkGoodsNameIsExist?GoodsName=" + encodeURIComponent(GoodsName.value);
		sendGoodsName(url);
	}

	function sendGoodsName(url) {
		if (window.XMLHttpRequest) {
			req = new XMLHttpRequest();
		} else if (window.ActiveXObject) {
			req = new ActiveXObject("Microsoft.XMLHTTP");
		}

		req.onreadystatechange = GoodsNameIsExist;
		req.open("get", url, true);
		req.send(null);
	}

	function GoodsNameIsExist() {
		if (req.readyState == 4) {
			if (req.status == 200) {
				var returnXml = req.responseXML;
				var GoodsNameDiv = document.getElementById("GoodsNameDiv");
				var formGroup = document.getElementById("GoodsName").closest('.form-group');
				var state = returnXml.getElementsByTagName("state")[0].firstChild.data;
				var content = returnXml.getElementsByTagName("content")[0].firstChild.data;

				if (state == "true") {
					GoodsNameDiv.innerHTML = content;
					formGroup.classList.add('has-error');
					GoodsName_IsExist = true;
				} else {
					GoodsNameDiv.innerHTML = "";
					formGroup.classList.remove('has-error');
					GoodsName_IsExist = false;
				}
			}
		}
	}

	function checkGoodsName() {
		var GoodsName = document.getElementById("GoodsName");
		var GoodsNameDiv = document.getElementById("GoodsNameDiv");
		var formGroup = GoodsName.closest('.form-group');

		if (GoodsName.value == "") {
			GoodsNameDiv.innerHTML = "商品名不能为空";
			formGroup.classList.add('has-error');
		} else {
			checkGoodsNameIsExist();
		}
	}

	// 检查ISBN是否存在
	var ISBN_IsExist;
	function checkISBNIsExist() {
		var isbn = document.getElementById("ISBN");
		var url = "checkISBNIsExist?ISBN=" + isbn.value;
		sendISBN(url);
	}

	function sendISBN(url) {
		if (window.XMLHttpRequest) {
			req = new XMLHttpRequest();
		} else if (window.ActiveXObject) {
			req = new ActiveXObject("Microsoft.XMLHTTP");
		}

		req.onreadystatechange = ISBNIsExist;
		req.open("get", url, true);
		req.send(null);
	}

	function ISBNIsExist() {
		if (req.readyState == 4) {
			if (req.status == 200) {
				var returnXml = req.responseXML;
				var ISBNDiv = document.getElementById("ISBNDiv");
				var formGroup = document.getElementById("ISBN").closest('.form-group');
				var state = returnXml.getElementsByTagName("state")[0].firstChild.data;
				var content = returnXml.getElementsByTagName("content")[0].firstChild.data;

				if (state == "true") {
					ISBNDiv.innerHTML = content;
					formGroup.classList.add('has-error');
					ISBN_IsExist = true;
				} else {
					ISBNDiv.innerHTML = "";
					formGroup.classList.remove('has-error');
					ISBN_IsExist = false;
				}
			}
		}
	}

	function checkISBN() {
		var isbn = document.getElementById("ISBN");
		var isbnDiv = document.getElementById("ISBNDiv");
		var formGroup = isbn.closest('.form-group');

		if (isbn.value == "") {
			isbnDiv.innerHTML = "商品编号不能为空";
			formGroup.classList.add('has-error');
		} else {
			checkISBNIsExist();
		}
	}

	// 检查生产日期
	var checkPages_;
	function checkPages() {
		var pages = document.getElementById("pages");
		var pagesDiv = document.getElementById("pagesDiv");
		var formGroup = pages.closest('.form-group');
		var pattern = /(?:19|20\d{2})\/(?:0[1-9]|1[0-2])\/(?:0[1-9]|[12][0-9]|3[01])/;

		if (pages.value == "") {
			pagesDiv.innerHTML = "生产日期不能为空";
			formGroup.classList.add('has-error');
			checkPages_ = false;
		} else if (pattern.test(pages.value)) {
			pagesDiv.innerHTML = "";
			formGroup.classList.remove('has-error');
			checkPages_ = true;
		} else {
			pagesDiv.innerHTML = "格式应为 YYYY/MM/DD";
			formGroup.classList.add('has-error');
			checkPages_ = false;
		}
	}

	// 检查产地
	var checkPublisher_;
	function checkPublisher() {
		var publisher = document.getElementById("publisher");
		var publisherDiv = document.getElementById("publisherDiv");
		var formGroup = publisher.closest('.form-group');

		if (publisher.value == "") {
			publisherDiv.innerHTML = "产地不能为空";
			formGroup.classList.add('has-error');
			checkPublisher_ = false;
		} else {
			publisherDiv.innerHTML = "";
			formGroup.classList.remove('has-error');
			checkPublisher_ = true;
		}
	}

	// 检查品牌
	var checkAuthor_;
	function checkAuthor() {
		var author = document.getElementById("author");
		var authorDiv = document.getElementById("authorDiv");
		var formGroup = author.closest('.form-group');

		if (author.value == "") {
			authorDiv.innerHTML = "品牌不能为空";
			formGroup.classList.add('has-error');
			checkAuthor_ = false;
		} else {
			authorDiv.innerHTML = "";
			formGroup.classList.remove('has-error');
			checkAuthor_ = true;
		}
	}

	// 检查原价
	var checkPrice_;
	function checkPrice() {
		var price = document.getElementById("price");
		var priceDiv = document.getElementById("priceDiv");
		var formGroup = price.closest('.form-group');
		var pattern = /^[1-9]\d*([.]\d+||\d*)$/;

		if (price.value == "") {
			priceDiv.innerHTML = "原价不能为空";
			formGroup.classList.add('has-error');
			checkPrice_ = false;
		} else if (pattern.test(price.value)) {
			priceDiv.innerHTML = "";
			formGroup.classList.remove('has-error');
			checkPrice_ = true;
		} else {
			priceDiv.innerHTML = "请输入有效的数字";
			formGroup.classList.add('has-error');
			checkPrice_ = false;
		}
	}

	// 检查现价
	var checkNowPrice_;
	function checkNowPrice() {
		var nowPrice = document.getElementById("nowPrice");
		var nowPriceDiv = document.getElementById("nowPriceDiv");
		var formGroup = nowPrice.closest('.form-group');
		var pattern = /^[1-9]\d*([.]\d+||\d*)$/;

		if (nowPrice.value == "") {
			nowPriceDiv.innerHTML = "现价不能为空";
			formGroup.classList.add('has-error');
			checkNowPrice_ = false;
		} else if (pattern.test(nowPrice.value)) {
			nowPriceDiv.innerHTML = "";
			formGroup.classList.remove('has-error');
			checkNowPrice_ = true;
		} else {
			nowPriceDiv.innerHTML = "请输入有效的数字";
			formGroup.classList.add('has-error');
			checkNowPrice_ = false;
		}
	}

	// // 检查数量
	// var checkGoodsNum_;
	// function checkGoodsNum() {
	// 	var GoodsNum = document.getElementById("GoodsNum");
	// 	var GoodsNumDiv = document.getElementById("GoodsNumDiv");
	// 	var formGroup = GoodsNum.closest('.form-group');
	// 	var pattern = /^[1-9]\d*$/;
	//
	// 	if (GoodsNum.value == "") {
	// 		GoodsNumDiv.innerHTML = "数量不能为空";
	// 		formGroup.classList.add('has-error');
	// 		checkGoodsNum_ = false;
	// 	} else if (pattern.test(GoodsNum.value)) {
	// 		GoodsNumDiv.innerHTML = "";
	// 		formGroup.classList.remove('has-error');
	// 		checkGoodsNum_ = true;
	// 	} else {
	// 		GoodsNumDiv.innerHTML = "请输入正整数";
	// 		formGroup.classList.add('has-error');
	// 		checkGoodsNum_ = false;
	// 	}
	// }

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

	// 处理文件上传
	document.getElementById('fileUpload').addEventListener('click', function() {
		document.getElementById('picture').click();
	});

	document.getElementById('picture').addEventListener('change', function() {
		var fileName = this.files[0] ? this.files[0].name : '';
		var fileNameDiv = document.getElementById('fileName');

		if (fileName) {
			fileNameDiv.textContent = '已选择文件: ' + fileName;
			fileNameDiv.style.display = 'block';
		} else {
			fileNameDiv.style.display = 'none';
		}
	});

	// 添加商品
	function add() {
		var s1 = document.getElementById("superTypeId");
		var s2 = document.getElementById("subTypeId");
		var typeDiv = document.getElementById("typeDiv");

		if (s1.value == "0" || s2.value == "0" || s2.value == "") {
			typeDiv.innerHTML = "请匹配大类和小类";
			typeDiv.style.display = 'block';
		} else if (!GoodsName_IsExist && !ISBN_IsExist && checkPages_ && checkPublisher_ && checkAuthor_ && checkPrice_ && checkNowPrice_) {
			var oForm = document.getElementsByTagName("form")[0];
			oForm.submit();
		} else {
			alert("请检查并修正表单中的错误");
		}
	}
</script>
</body>
</html>
