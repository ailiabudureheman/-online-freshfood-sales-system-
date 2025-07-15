online fresh food sales system 是一个客户端和后台功能齐全的在线生鲜销售及管理的项目。
## 商品管理系统 - 项目说明文档

## 项目简介
商品管理系统是一个用于管理商品信息、库存和订单的Web应用系统，旨在提供高效的商品全生命周期管理，包括商品添加、分类管理、库存监控和订单处理等功能。系统采用Java Web技术栈开发，具有良好的可扩展性和易用性。

## 技术栈
- **后端**：Java、Servlet、JDBC
- **前端**：HTML、CSS、JavaScript、Tailwind CSS、Font Awesome
- **数据库**：MySQL 8.0
- **服务器**：Apache Tomcat 8.5+
- **开发工具**：IntelliJ IDEA / Eclipse

## 功能模块
1. **商品管理**
   - 商品添加、编辑、删除
   - 商品分类管理（大类、小类）
   - 商品图片上传
   - 商品状态管理（热卖、新品、特价等）

2. **库存管理**
   - 库存数量实时监控
   - 库存不足预警
   - 入库/出库记录跟踪
   - 库存调整记录

3. **订单处理**
   - 销售订单管理
   - 订单状态跟踪（待处理、处理中、已发货、已完成）
   - 库存扣减自动处理
   - 订单查询与筛选

4. **系统管理**
   - 管理员账户管理
   - 操作日志记录
   - 系统参数设置

## 安装与配置
### 环境要求
- JDK 8+
- MySQL 8.0
- Apache Tomcat 8.5+
- Maven 3.6+（可选）

### 部署步骤
1. **数据库配置**
   - 执行`shop_sql.sql`脚本初始化数据库
   - 修改`db.properties`文件配置数据库连接信息：
     ```properties
     db.url=jdbc:mysql://localhost:3306/shop?useSSL=false&serverTimezone=UTC
     db.username=root
     db.password=your_password
     db.driver=com.mysql.cj.jdbc.Driver
     ```

2. **项目部署**
   - 将项目打包为WAR文件
   - 部署至Tomcat的`webapps`目录
   - 启动Tomcat服务器
   - 访问地址：`http://localhost:8080/shop`

3. **初始账户**
 ID | AdminType | AdminName  | LoginName | LoginPwd |

+----+-----------+------------+-----------+----------+

|  1 |         1 | 商品管理员 | admin1    | admin1 


|  2 |         2 | 订单管理员 | admin2    | admin2   |


|  3 |         3 | 会员管理员 | admin3    | admin3   |


|  4 |         4 | 系统管理员 | admin4    | admin4   |


+----+-----------+------------+-----------+----------+


其中 admin4 的权限最大，建议使用该账号，以获得更好的体验。



## 核心功能说明
### 商品添加流程
1. 选择商品大类和小类（系统自动生成商品编码）
2. 填写商品基本信息（名称、价格、产地、品牌等）
3. 上传商品图片
4. 设置商品属性（热卖、新品等）
5. 提交保存（系统自动验证数据有效性）

### 库存管理机制
- 库存数量从`tb_product_inventory`表实时统计
- 发货时自动扣减库存，遵循"先进先出"原则
- 库存不足时触发预警机制
- 支持多仓库库存管理

### 订单处理流程
1. 订单创建后自动检查库存
2. 库存充足时，从最早入库的库存中扣减
3. 库存不足时提示错误信息
4. 支持订单状态流转（处理中→已发货→已完成）

## 常见问题解决
1. **外键约束错误**
   - 确保添加商品时选择的小类在`tb_subtype`表中存在
   - 检查大类与小类的关联关系是否正确

2. **中文乱码问题**
   - 确认数据库连接URL包含`characterEncoding=utf8`
   - 检查JSP页面编码是否为`UTF-8`
   - 确保过滤器正确设置请求编码

3. **库存计算异常**
   - 检查`tb_product_inventory`表的`status`字段是否正确设置
   - 确认库存统计SQL语句是否正确

## 开发规范
1. **数据库命名**
   - 表名前缀：`tb_`（如`tb_product`）
   - 主键命名：`id`或`表名+Id`（如`product_id`）
   - 外键命名：`关联表名+Id`（如`subTypeId`）

2. **代码规范**
   - 类名采用PascalCase（如`ProductDaoImpl`）
   - 方法名和变量名采用camelCase（如`checkSubTypeIdExists`）
   - 常量全部大写，用下划线分隔（如`MAX_UPLOAD_SIZE`）

3. **安全规范**
   - 所有用户输入必须经过验证
   - 数据库操作使用PreparedStatement防止SQL注入
   - 敏感操作需验证用户权限
     

