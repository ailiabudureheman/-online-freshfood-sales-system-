// 菜单切换功能
document.addEventListener('DOMContentLoaded', function() {
    // 菜单展开/收起
    const menuHeaders = document.querySelectorAll('.menu-header');
    menuHeaders.forEach(header => {
        header.addEventListener('click', function(e) {
            e.preventDefault();
            this.classList.toggle('active');

            // 关闭其他打开的菜单
            menuHeaders.forEach(otherHeader => {
                if (otherHeader !== this) {
                    otherHeader.classList.remove('active');
                }
            });
        });
    });

    // 侧边栏折叠功能
    const sidebarToggle = document.getElementById('sidebarToggle');
    const sidebar = document.querySelector('.sidebar');

    sidebarToggle.addEventListener('click', function() {
        sidebar.classList.toggle('collapsed');
    });

    // 库存预警提示
    const inventoryAlert = document.getElementById('inventoryAlert');
    const alertCount = inventoryAlert.querySelector('.alert-count');

    // 这里应该通过AJAX获取实际预警数量
    // 示例代码
    alertCount.textContent = '36';
    inventoryAlert.style.display = 'flex';

    // 5秒后自动关闭提示
    setTimeout(function() {
        inventoryAlert.style.display = 'none';
    }, 10000);

    // 关闭提示按钮
    inventoryAlert.querySelector('.notification-close').addEventListener('click', function() {
        inventoryAlert.style.display = 'none';
    });

    // 点击提示框查看库存预警
    inventoryAlert.addEventListener('click', function(e) {
        if (!e.target.closest('.notification-close')) {
            document.querySelector('a[href="GetInventoryAlertServlet"]').click();
        }
    });

    // 内容iframe显示控制
    const contentIframe = document.querySelector('.content-iframe');
    const contentPlaceholder = document.getElementById('content-placeholder');

    // 监听所有target为contentIframe的链接点击
    document.querySelectorAll('a[target="contentIframe"]').forEach(link => {
        link.addEventListener('click', function(e) {
            e.preventDefault();
            contentIframe.src = this.getAttribute('href');
            contentIframe.style.display = 'block';
            contentPlaceholder.style.display = 'none';
        });
    });

    // 退出登录功能
    const logoutBtn = document.querySelector('.logout-btn');
    logoutBtn.addEventListener('click', function() {
        if (confirm('确定要退出登录吗？')) {
            // 这里应该跳转到登录页面
            window.location.href = 'login.jsp';
        }
    });
});
// 密码修改页面功能
function checkPassword() {
    const password = document.getElementById("password");
    const passwordError = document.getElementById("passwordError");
    const passwordStrength = document.getElementById("passwordStrength");

    if (password.value.trim() === "") {
        passwordError.textContent = "密码不能为空";
        passwordError.style.display = "block";
        passwordStrength.style.display = "none";
        checkPassword_ = false;
        return;
    }

    if (password.value.length < 6) {
        passwordError.textContent = "密码长度不能少于6位";
        passwordError.style.display = "block";
        passwordStrength.style.display = "none";
        checkPassword_ = false;
        return;
    }

    // 密码强度检测
    let strength = 0;
    const hasNumber = /\d/.test(password.value);
    const hasUpperCase = /[A-Z]/.test(password.value);
    const hasLowerCase = /[a-z]/.test(password.value);
    const hasSpecialChar = /[^A-Za-z0-9]/.test(password.value);

    if (hasNumber) strength++;
    if (hasUpperCase) strength++;
    if (hasLowerCase) strength++;
    if (hasSpecialChar) strength++;

    passwordStrength.style.display = "block";
    passwordStrength.className = "password-strength level-" + strength;

    passwordError.style.display = "none";
    checkPassword_ = true;
}

function checkRpassword() {
    const rpassword = document.getElementById("rpassword");
    const password = document.getElementById("password");
    const rpasswordError = document.getElementById("rpasswordError");

    if (rpassword.value.trim() === "") {
        rpasswordError.textContent = "确认密码不能为空";
        rpasswordError.style.display = "block";
        checkRpassword_ = false;
        return;
    }

    if (password.value !== rpassword.value) {
        rpasswordError.textContent = "两次输入的密码不一致";
        rpasswordError.style.display = "block";
        checkRpassword_ = false;
        return;
    }

    rpasswordError.style.display = "none";
    checkRpassword_ = true;
}

function updatePassword() {
    const messageDiv = document.getElementById("messageDiv");
    if (checkRpassword_ && checkPassword_) {
        const form = document.getElementById("passwordForm");
        form.submit();
    } else {
        messageDiv.className = "form-message error";
        messageDiv.textContent = "请输入正确的密码信息";
        // 平滑滚动到错误信息
        messageDiv.scrollIntoView({ behavior: 'smooth', block: 'center' });
    }
}

// 公告更新页面功能
function validateTitle() {
    const title = document.getElementById("informTitle");
    const titleError = document.getElementById("titleError");

    if (title.value.trim() === "") {
        titleError.textContent = "公告标题不能为空";
        titleError.style.display = "block";
        return false;
    }

    if (title.value.length > 100) {
        titleError.textContent = "公告标题不能超过100字";
        titleError.style.display = "block";
        return false;
    }

    titleError.style.display = "none";
    return true;
}

function validateContent() {
    const content = document.getElementById("informContent");
    const contentError = document.getElementById("contentError");

    if (content.value.trim() === "") {
        contentError.textContent = "公告内容不能为空";
        contentError.style.display = "block";
        return false;
    }

    contentError.style.display = "none";
    return true;
}

function updateInform() {
    const messageDiv = document.getElementById("messageDiv");
    if (validateTitle() && validateContent()) {
        const form = document.getElementById("updateInformForm");
        form.submit();
    } else {
        messageDiv.className = "form-message error";
        messageDiv.textContent = "请修正表单中的错误";
        // 平滑滚动到错误信息
        messageDiv.scrollIntoView({ behavior: 'smooth', block: 'center' });
    }
}

// 输入框实时验证
document.getElementById("informTitle").addEventListener("blur", validateTitle);
document.getElementById("informContent").addEventListener("blur", validateContent);
// 商品修改页面功能
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
            var superTypeId = document.getElementById("superTypeId");
            var superTypeHidden = document.getElementById("superTypeHidden");
            var subTypeHidden = document.getElementById("subTypeHidden");

            // 清空现有选项
            while (superTypeId.firstChild) {
                superTypeId.removeChild(superTypeId.firstChild);
            }

            var subTypeId = document.getElementById("subTypeId");
            clearSubType();

            var subTypeXml = req.responseXML;
            var superTypes = subTypeXml.getElementsByTagName("super");
            if (superTypes.length > 0) {
                var defaultOption = document.createElement("option");
                defaultOption.setAttribute("value", "0");
                defaultOption.appendChild(document.createTextNode("--选择大类--"));
                superTypeId.appendChild(defaultOption);

                for (var i = 0; i < superTypes.length; i++) {
                    var superId = superTypes[i].getElementsByTagName("superId").item(0).firstChild.data;
                    var superName = superTypes[i].getElementsByTagName("superName").item(0).firstChild.data;
                    var op = document.createElement("option");
                    op.setAttribute("value", superId);
                    var txt = document.createTextNode(superName);
                    op.appendChild(txt);
                    superTypeId.appendChild(op);

                    // 自动选择当前大类
                    if (superId == superTypeHidden.value) {
                        superTypeId.value = superId;
                        getSubType();
                    }
                }
                superTypeId.style.width = "auto";
            } else {
                document.getElementById("typeError").textContent = "还没有大类";
            }
        }
    }
}

function getSubType() {
    var id;
    var superType = document.getElementById("superTypeId");
    for (var i = 0; i < superType.options.length; i++) {
        if (superType.options[i].selected) {
            id = superType.options[i].value;
            break;
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
    while (subType.firstChild) {
        subType.removeChild(subType.firstChild);
    }
    subType.style.width = "";
}

function showSub() {
    if (req.readyState == 4) {
        if (req.status == 200) {
            clearSubType();
            var subType = document.getElementById("subTypeId");
            var typeError = document.getElementById("typeError");

            var subTypeXml = req.responseXML;
            var subTypes = subTypeXml.getElementsByTagName("subType");
            if (subTypes.length > 0) {
                var defaultOption = document.createElement("option");
                defaultOption.setAttribute("value", "0");
                defaultOption.appendChild(document.createTextNode("--选择小类--"));
                subType.appendChild(defaultOption);

                for (var i = 0; i < subTypes.length; i++) {
                    var subTypeId = subTypes[i].getElementsByTagName("subTypeId").item(0).firstChild.data;
                    var subTypeName = subTypes[i].getElementsByTagName("subTypeName").item(0).firstChild.data;
                    var op = document.createElement("option");
                    op.setAttribute("value", subTypeId);
                    var txt = document.createTextNode(subTypeName);
                    op.appendChild(txt);
                    subType.appendChild(op);

                    // 自动选择当前小类
                    var subTypeHidden = document.getElementById("subTypeHidden");
                    if (subTypeId == subTypeHidden.value) {
                        subType.value = subTypeId;
                    }
                }
                subType.style.width = "auto";
                typeError.style.display = "none";
            } else {
                typeError.textContent = "请先选择大类";
                typeError.style.display = "block";
            }
        }
    }
}

var checkGoodsName_ = false;
function checkGoodsName() {
    const goodsName = document.getElementById("GoodsName");
    const goodsNameDiv = document.getElementById("GoodsNameDiv");

    if (goodsName.value.trim() === "") {
        goodsNameDiv.textContent = "商品名不能为空";
        goodsNameDiv.style.display = "block";
        checkGoodsName_ = false;
    } else {
        goodsNameDiv.textContent = "";
        goodsNameDiv.style.display = "none";
        checkGoodsName_ = true;
    }
}

var checkISBN_ = false;
function checkISBN() {
    const isbn = document.getElementById("ISBN");
    const isbnDiv = document.getElementById("ISBNDiv");

    if (isbn.value.trim() === "") {
        isbnDiv.textContent = "商品编码不能为空";
        isbnDiv.style.display = "block";
        checkISBN_ = false;
    } else {
        isbnDiv.textContent = "";
        isbnDiv.style.display = "none";
        checkISBN_ = true;
    }
}

var checkPages_ = false;
function checkPages() {
    const pages = document.getElementById("pages");
    const pagesDiv = document.getElementById("pagesDiv");
    const pattern = /(?:19|20\d{2})\/(?:0[1-9]|1[0-2])\/(?:0[1-9]|[12][0-9]|3[01])/;

    if (pages.value.trim() === "") {
        pagesDiv.textContent = "商品的生产日期不能为空";
        pagesDiv.style.display = "block";
        checkPages_ = false;
    } else if (pattern.test(pages.value)) {
        pagesDiv.textContent = "";
        pagesDiv.style.display = "none";
        checkPages_ = true;
    } else {
        pagesDiv.textContent = "日期格式应为YYYY/MM/DD";
        pagesDiv.style.display = "block";
        checkPages_ = false;
    }
}

var checkPublisher_ = false;
function checkPublisher() {
    const publisher = document.getElementById("publisher");
    const publisherDiv = document.getElementById("publisherDiv");

    if (publisher.value.trim() === "") {
        publisherDiv.textContent = "产地不能为空";
        publisherDiv.style.display = "block";
        checkPublisher_ = false;
    } else {
        publisherDiv.textContent = "";
        publisherDiv.style.display = "none";
        checkPublisher_ = true;
    }
}

var checkAuthor_ = false;
function checkAuthor() {
    const author = document.getElementById("author");
    const authorDiv = document.getElementById("authorDiv");

    if (author.value.trim() === "") {
        authorDiv.textContent = "品牌不能为空";
        authorDiv.style.display = "block";
        checkAuthor_ = false;
    } else {
        authorDiv.textContent = "";
        authorDiv.style.display = "none";
        checkAuthor_ = true;
    }
}

var checkPrice_ = false;
function checkPrice() {
    const price = document.getElementById("price");
    const priceDiv = document.getElementById("priceDiv");
    const pattern = /^[1-9]\d*([.]\d+||\d*)$/;

    if (price.value.trim() === "") {
        priceDiv.textContent = "原价不能为空";
        priceDiv.style.display = "block";
        checkPrice_ = false;
    } else if (pattern.test(price.value)) {
        priceDiv.textContent = "";
        priceDiv.style.display = "none";
        checkPrice_ = true;
    } else {
        priceDiv.textContent = "请输入有效的价格格式";
        priceDiv.style.display = "block";
        checkPrice_ = false;
    }
}

var checkNowPrice_ = false;
function checkNowPrice() {
    const nowPrice = document.getElementById("nowPrice");
    const nowPriceDiv = document.getElementById("nowPriceDiv");
    const pattern = /^[1-9]\d*([.]\d+||\d*)$/;

    if (nowPrice.value.trim() === "") {
        nowPriceDiv.textContent = "现价不能为空";
        nowPriceDiv.style.display = "block";
        checkNowPrice_ = false;
    } else if (pattern.test(nowPrice.value)) {
        nowPriceDiv.textContent = "";
        nowPriceDiv.style.display = "none";
        checkNowPrice_ = true;
    } else {
        nowPriceDiv.textContent = "请输入有效的价格格式";
        nowPriceDiv.style.display = "block";
        checkNowPrice_ = false;
    }
}

var checkGoodsNum_ = false;
function checkGoodsNum() {
    const goodsNum = document.getElementById("GoodsNum");
    const goodsNumDiv = document.getElementById("GoodsNumDiv");
    const pattern = /^[1-9]\d*$/;

    if (goodsNum.value.trim() === "") {
        goodsNumDiv.textContent = "数量不能为空";
        goodsNumDiv.style.display = "block";
        checkGoodsNum_ = false;
    } else if (pattern.test(goodsNum.value)) {
        goodsNumDiv.textContent = "";
        goodsNumDiv.style.display = "none";
        checkGoodsNum_ = true;
    } else {
        goodsNumDiv.textContent = "请输入正整数";
        goodsNumDiv.style.display = "block";
        checkGoodsNum_ = false;
    }
}

function updateGoods() {
    checkGoodsName();
    checkISBN();
    checkPages();
    checkPublisher();
    checkAuthor();
    checkPrice();
    checkNowPrice();
    checkGoodsNum();

    const superTypeId = document.getElementById("superTypeId");
    const subTypeId = document.getElementById("subTypeId");
    const typeError = document.getElementById("typeError");

    if (superTypeId.value === "0" || subTypeId.value === "0") {
        typeError.textContent = "请选择完整的商品类别";
        typeError.style.display = "block";
        return;
    }

    typeError.style.display = "none";

    if (checkGoodsName_ && checkISBN_ && checkPages_ && checkPublisher_ && checkAuthor_ && checkPrice_ && checkNowPrice_ && checkGoodsNum_) {
        const form = document.getElementById("updateGoodsForm");
        form.submit();
    } else {
        const messageDiv = document.getElementById("messageDiv");
        messageDiv.className = "form-message error";
        messageDiv.textContent = "请修正表单中的错误";
        messageDiv.scrollIntoView({ behavior: 'smooth', block: 'center' });
    }
}