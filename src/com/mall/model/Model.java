package com.mall.model;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import java.util.Map;

import com.mall.common.DbUtil;
import com.mall.dao.*;
import com.mall.daoimpl.*;
import com.mall.po.*;

public class Model {
	private UserDao ud = new UserDaoImpl();
	private GoodsDao bd = new GoodsDaoImpl();
	private OrderDao od = new OrderDaoImpl();
	private SuperTypeDao superDao = new SuperTypeDaoImpl();



	private InventoryAlertDao inventoryAlertDao = new InventoryAlertDaoImpl();



	private MaterialOrderDao materialOrderDao = new MaterialOrderDaoImpl();


	private InventorySummaryDao inventorySummaryDao = new InventorySummaryDaoImpl();



	private WarehouseDao warehouseDao = new WarehouseDaoImpl();


	private InventorySettingsDao inventorySettingsDao = new InventorySettingsDaoImpl();


	private ProductDao productDao = new ProductDaoImpl();







	// 新增每日到货计划DAO
	private DailyArrivalDao dailyArrivalDao = new DailyArrivalDaoImpl();

	private SubTypeDao subDao = new SubTypeDaoImpl();
    //获取大类
	public List showAllSuperType() {
		return superDao.showAllSuperType();
	}
    //根据大类Id获取对应所有的小类
	public List showAllSubTypeBySuperId(int superId) {
		return subDao.showAllSubTypeBySuperId(superId);
	}
	//用户注册
	public boolean addUser(User user) {
		return ud.addUser(user);
	}
	//用户登录
	public User userLogin(String name,String password) {
		return ud.userLogin(name,password);
	}
	//检查用户名是否存在
	public boolean checkNameExist(String name){
		return ud.checkNameExist(name);
	}
	//用户管理页面
	public List listUser() {
		return ud.listUser();
	}
	//根据ID删除用户
	public boolean delete(int id,String powerType){
		return ud.delete(id,powerType);
	}
	//根据ID更改用户权限
	public boolean changePower(int id,String powerType){
		return ud.changePower(id,powerType);
	}
	//根据用户名，获取其权限
	public String getPower(String name){
		return ud.getPower(name);
	}
	//根据用户名，找到该用户
	public User getUser(String name){
		return ud.getUser(name);
	}
	//显示商品
	public List showGoods(int type,int flag) {
		return bd.showGoods(type, flag);
	}
	//根据商品的GoodsId来显示商品的详细信息
	public Goods showGoodsById(int GoodsId){
		return bd.showGoodsById(GoodsId);
	}
	
	//根据商品的GoodsId来显示商品的购买记录
	public List showBuyRecordsById(int GoodsId){
		return bd.showBuyRecordsById(GoodsId);
	}
	
	//添加订单，并返回订单号
	public int addOrder(Order order){
		return od.addOrder(order);
	}	
	//分页显示商品
	public Page doPage(int type,int currentPage,int pageSize, int flag){
		return bd.doPage(type,currentPage, pageSize,flag);
	}
	//根据用户输入的关键字搜索相关商品
	public List searchGoods(String keywords){
		return bd.searchGoods(keywords);
	}
	//根据用户输入的关键字搜索相关商品
	public boolean updateGoodsNum(int num,int GoodsId){
		return bd.updateGoodsNum(num,GoodsId);
	}
	public boolean updateGoods(Goods Goods){
		return bd.updateGoods(Goods);
	}
	
	/**
	 * 分页显示所有用户留言
	 * @param currentPage 显示出来的当前页
	 * @param pageSize 每页显示数目
     * @return Page
	 */
	public Page doPage(String keywords,int currentPage,int pageSize){
		return bd.doPage(keywords, currentPage, pageSize);
	}
	//显示所有用户留言
    public List showNote(){
		return ud.showNote();
	}
	/**
	 * 显示所有用户留言
	 * @param currentPage 显示出来的当前页码
	 * @param pageSize 每页显示数目
	 * @return Page
	 */
	public Page doNotePage(int currentPage,int pageSize){
		return ud.doNotePage(currentPage, pageSize);
	}
	//用户添加留言
	public boolean addNote(Note note){
		return ud.addNote(note);
	}
	/**
	 * 根据用户选择的搜索条件搜索相关商品
	 * @param superTypeId 所属大类的ID
	 * @param subTypeId 所属小类的ID
	 * @param searchMethod （可以是按品牌、商品名、产地、编码的一个） 
	 * @return List
	 */
	public List searchGoodsByConditions(int superTypeId,int subTypeId,String searchMethod){
		return bd.searchGoodsByConditions(superTypeId, subTypeId, searchMethod);//djm
	}
	/**
	 *根据用户选择的搜索条件搜索相关商品
	 * @param superTypeId 所属大类的ID
	 * @param subTypeId 所属小类的ID
	 * @param searchMethod 可以是按品牌、商品名、产地、编码的一个
	 */
	public Page doPageByConditons(int superTypeId,int subTypeId,String searchMethod,int currentPage,int pageSize){
		return bd.doPageByConditons(superTypeId, subTypeId, searchMethod, currentPage, pageSize);
	}
	/**
	 * 后台*/
	private AdminUserDao userDao = new AdminUserDaoImpl();
	private AdminAdminDao adminDao = new AdminAdminDaoImpl();
	private AdminDao adminDao1=new AdminDaoImpl();
	private AdminSubTypeDao subTypeDao = new AdminSubTypeDaoImpl();
	private AdminSuperTypeDao superTypeDao = new AdminSuperTypeDaoImpl();
	private AdminTypeDao adminTypeDao = new AdminTypeDaoImpl();
	private AdminGoodsDao GoodsDao = new AdminGoodsDaoImpl();
	private AdminNoteDao noteDao = new AdminNoteDaoImpl();
	private AdminLoginDao adminLoginDao = new AdminLoginDaoImpl();
	private AdminInformDao adminInformDao = new AdminInformDaoImpl();
	private AdminOrderDao adminOrderDao = new AdminOrderDaoImpl();
	
	public Map getAllUsers() {
		return userDao.getAllUsers();
	}
	
	public boolean deleteUserById(int id) {
		return userDao.deleteUserById(id);
	}
	
	public boolean deleteUsers(int[] ids) {
		return userDao.deleteUsers(ids);
	}
	
	public UserPager getUserPager(int index, int pageSize) {
		return userDao.getUserPager(index, pageSize);
	}
	
	public AdminPager getAdminPager(int index, int pageSize) {
		return adminDao.getAdminPager(index, pageSize);
	}
	
	public List getSubTypeBySuperTypeId(int superTypeId) {
		return subTypeDao.getSubTypeBySuperTypeId(superTypeId);
	}
	
	public boolean addSubType(SubType type) {
		return subTypeDao.addSubType(type);
	}
	
	public boolean checkSubTypeIsExist(String subTypeName) {
		return subTypeDao.checkSubTypeIsExist(subTypeName);
	}
	
	public boolean checkLoginNameIsExist(String loginName) {
		return adminDao1.checkNameExist(loginName);
	}
	
	public boolean addAdmin(Admin admin){
		return adminDao1.addAdmin(admin);
	}
	
	public boolean updateAdmin(Admin admin){
		return adminDao1.updateAdmin(admin);
	}
	
    public boolean deleteAdmin(int[] ids){
		return adminDao1.deleteAdmin(ids);
	}
    
	public List getSuperType() {
		return superTypeDao.getSuperType();
	}
	
	public List getAdminType() {
		return adminTypeDao.getAdminType();
	}
	
	public boolean addSuperType(SuperType type) {
		return superTypeDao.addSuperType(type);
	}
	
	public boolean checkSuperTypeIsExist(String superTypeName) {
		return superTypeDao.checkSuperTypeIsExist(superTypeName);
	}
	
	public List getAllGoods() {
		return GoodsDao.getAllGoods();
	}
	
	public boolean addGoods(Goods Goods) {
		return GoodsDao.addGoods(Goods);
	}
	
	public boolean checkGoodsNameIsExist(String GoodsName) {
		return GoodsDao.checkGoodsNameIsExist(GoodsName);
	}

	public boolean checkISBNIsExist(String ISBN) {
		return GoodsDao.checkISBNIsExist(ISBN);
	}
	
	public GoodsPager searchGood(String GoodsName) {
		return GoodsDao.searchGoods(GoodsName);
	}

	public List getAllGoodsName() {
		return GoodsDao.getAllGoodsName();
	}
	
	public GoodsPager getGoodsPager(int index,int pageSize) {
		return GoodsDao.getGoodsPager(index, pageSize);
	}
	
	public boolean deleteGoods(int[] GoodsIds) {
		return GoodsDao.deleteGoods(GoodsIds);
	}
	
	public List getAllNotes() {
		return noteDao.getAllNotes();
	}
	
	public boolean deleteNote(int[] ids) {
		return noteDao.deleteNote(ids);
	}
	
	public NotePager getNotePager(int index, int pageSize) {
		return noteDao.getNotePager(index, pageSize);
	}
	
	public int login(Admin admin) {
		return adminLoginDao.login(admin);
	}
	
	public boolean updatePassword(Admin admin) {
		return adminLoginDao.updatePassword(admin);
	}
	
	public boolean addInform(Inform inform) {
		return adminInformDao.addInform(inform);
	}
	
	public List getAllInform() {
		return adminInformDao.getAllInform();
	}
	
	public boolean deleteInform(int[] ids) {
		return adminInformDao.deleteInform(ids);
	}
	
	public Inform getOneInform(int id) {
		return adminInformDao.getOneInform(id);
	}
	
	public InformPager getInformPager(int index, int pageSize) {
		return adminInformDao.getInformPager(index, pageSize);
	}
	
	public List getAllOrder() {
		return adminOrderDao.getAllOrder();
	}
	
	public Order getOneOrder(int id) {
		return adminOrderDao.getOneOrder(id);
	}
	
	public List getNotSendOrder(int flag) {
		return adminOrderDao.getNotSendOrder(flag);
	}

	public List getSendOrder(int flag) {
		return adminOrderDao.getSendOrder(flag);
	}

	public boolean SendOrders(int[] orderids) {
		return adminOrderDao.SendOrders(orderids);
	}
	
	public boolean SendOrder(int orderid) {
		return adminOrderDao.SendOrder(orderid);
	}
	
	public OrderPager getOrderPager(int index, int pageSize) {
		return adminOrderDao.getOrderPager(index, pageSize);
	}
	
	public OrderSendPager getOrderSendPager(int index, int pageSize) {
		return adminOrderDao.getOrderSendPager(index, pageSize);
	}
	
	public boolean deleteOrder(int[] ids) {
		return adminOrderDao.deleteOrder(ids);
	}
	
	public OrderNotSendPager getOrderNotSendPager(int index, int pageSize) {
		return adminOrderDao.getOrderNotSendPager(index, pageSize);
	}
	
	public OrderFreezePager getOrderFreezePager(int index,int pageSize) {
		return adminOrderDao.getOrderFreezePager(index, pageSize);
	}
	
	public boolean freezeOrder(int[] orderids) {
		return adminOrderDao.freezeOrder(orderids);
	}
	
	//根据当前用户的用户名查订单
	public List selectOrder(String name){
		return od.selectOrder(name);
	}
	//根据订单号查订单项
	public List selectOrderItem(int id ){
		return od.selectOrderItem(id);
	}
	
	public Boolean loggin(String name, String password){
		return ud.loggin(name, password);
	}
	
    public User SelectOneUser(String name){
		return ud.SelectOneUser(name);
	}
	 //用于修改用户的个人密码
	public Boolean updatePassword(String name, String password){
		return ud.updatePassword(name, password);
	}

	public Boolean updateUser(User user) {
		return ud.updateUser(user);
	}


	/**
	 * 获取每日到货计划分页数据
	 * @param offset 起始记录索引
	 * @param pageSize 每页记录数
	 * @param date 日期筛选
	 * @param supplier 供应商筛选
	 * @return 每日到货计划分页对象
	 */
	public DailyArrivalPager getDailyArrivalPager(int offset, int pageSize, String date, String supplier) {
		return dailyArrivalDao.getDailyArrivalPager(offset, pageSize, date, supplier);
	}



	/**
	* @param offset 起始记录索引
     * @param pageSize 每页记录数
     * @param alertType 预警类型
     * @param warehouseId 仓库ID
     * @param productName 产品名称
     * @return 库存预警分页对象
     */
	public InventoryAlertPager getInventoryAlertPager(int offset, int pageSize,
													  String alertType, String warehouseId,
													  String productName) {
		return inventoryAlertDao.getInventoryAlertPager(offset, pageSize, alertType, warehouseId, productName);
	}


	/**
	 * 获取材料订单分页数据
	 * @param offset 起始记录索引
	 * @param pageSize 每页记录数
	 * @param status 订单状态
	 * @param orderNo 订单编号
	 * @param supplier 供应商
	 * @return 材料订单分页对象
	 */
	public MaterialOrderPager getMaterialOrderPager(int offset, int pageSize,
													String status, String orderNo,
													String supplier) {
		return materialOrderDao.getMaterialOrderPager(offset, pageSize, status, orderNo, supplier);
	}



	/**
	 * 获取库存概览分页数据
	 * @param offset 起始记录索引
	 * @param pageSize 每页记录数
	 * @param warehouseId 仓库ID
	 * @param categoryId 类别ID
	 * @param productName 产品名称
	 * @return 库存概览分页对象
	 */
	public InventorySummaryPager getInventorySummaryPager(int offset, int pageSize,
														  String warehouseId, String categoryId,
														  String productName) {


		return inventorySummaryDao.getInventorySummaryPager(offset, pageSize, warehouseId, categoryId, productName);
	}


	public OrderPager getSalesOrderPager(int offset, int pageSize, String status, String orderNo, String customerName) {
		return adminOrderDao.getOrderPagerByConditions(offset, pageSize, status, orderNo, customerName);

	}






	/**
	 * 获取仓库分页数据
	 * @param offset 起始记录索引
	 * @param pageSize 每页记录数
	 * @return 仓库分页对象
	 */
	public WarehousePager getWarehousePager(int offset, int pageSize) {
		return warehouseDao.getWarehousePager(offset, pageSize);
	}

	/**
	 * 根据ID获取仓库
	 * @param warehouseId 仓库ID
	 * @return 仓库对象
	 */
	public Warehouse getWarehouseById(int warehouseId) {
		return warehouseDao.getWarehouseById(warehouseId);
	}

	/**
	 * 添加仓库
	 * @param warehouse 仓库对象
	 * @return 是否添加成功
	 */
	public boolean addWarehouse(Warehouse warehouse) {
		return warehouseDao.addWarehouse(warehouse);
	}

	/**
	 * 更新仓库
	 * @param warehouse 仓库对象
	 * @return 是否更新成功
	 */
	public boolean updateWarehouse(Warehouse warehouse) {
		return warehouseDao.updateWarehouse(warehouse);
	}




	/**
	 * 获取库存设置
	 * @return 库存设置对象
	 */
	public InventorySettings getInventorySettings() {
		return inventorySettingsDao.getInventorySettings();
	}

	/**
	 * 更新库存设置
	 * @param settings 库存设置对象
	 * @return 是否更新成功
	 */
	public boolean updateInventorySettings(InventorySettings settings) {
		return inventorySettingsDao.updateInventorySettings(settings);
	}


	/**
	 * 获取产品分页数据
	 * @param offset 起始记录索引
	 * @param pageSize 每页记录数
	 * @param categoryId 类别ID
	 * @param productName 产品名称
	 * @return 产品分页对象
	 */
	public ProductPager getProductPager(int offset, int pageSize, String categoryId, String productName) {
		return productDao.getProductPager(offset, pageSize, categoryId, productName);
	}





	/**
	 * 重置库存设置为默认值
	 * @return 重置是否成功
	 */
	public boolean resetInventorySettingsToDefault() {
		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			DbUtil dbUtil = new DbUtil();
			conn = dbUtil.getCon();

			// 检查是否存在库存设置记录
			boolean settingsExist = checkInventorySettingsExist(conn);

			if (settingsExist) {
				// 更新现有记录为默认值
				String sql = "UPDATE inventory_settings SET min_stock_alert = ?, max_stock_alert = ?, " +
						"reorder_point = ?, auto_approve_threshold = ?";
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, 10);  // 默认最小库存预警值
				pstmt.setInt(2, 100); // 默认最大库存预警值
				pstmt.setInt(3, 20);  // 默认补货点
				pstmt.setDouble(4, 500.0); // 默认自动审批阈值
			} else {
				// 插入新的默认设置记录
				String sql = "INSERT INTO inventory_settings (min_stock_alert, max_stock_alert, " +
						"reorder_point, auto_approve_threshold) VALUES (?, ?, ?, ?)";
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, 10);
				pstmt.setInt(2, 100);
				pstmt.setInt(3, 20);
				pstmt.setDouble(4, 500.0);
			}

			int rowsAffected = pstmt.executeUpdate();
			return rowsAffected > 0;

		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} catch (Exception e) {
            throw new RuntimeException(e);
        }
	}

	/**
	 * 检查库存设置记录是否存在
	 */
	private boolean checkInventorySettingsExist(Connection conn) throws SQLException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			String sql = "SELECT COUNT(*) FROM inventory_settings";
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();

			if (rs.next()) {
				return rs.getInt(1) > 0;
			}

			return false;
		} catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


	/**
	 * 删除仓库
	 * @param warehouseId 仓库ID
	 * @return 删除是否成功
	 */
	public boolean deleteWarehouse(int warehouseId) {
		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			DbUtil dbUtil = new DbUtil();
			conn = dbUtil.getCon();

			// 检查该仓库是否有关联数据
			String checkSql = "SELECT COUNT(*) FROM tb_product_inventory WHERE warehouseId = ?";
			pstmt = conn.prepareStatement(checkSql);
			pstmt.setInt(1, warehouseId);
			ResultSet rs = pstmt.executeQuery();

			if (rs.next() && rs.getInt(1) > 0) {
				// 有关联数据，不能删除
				return false;
			}

			// 关闭检查语句
			pstmt.close();

			// 执行删除操作
			String sql = "DELETE FROM tb_warehouse WHERE warehouseId = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, warehouseId);

			int rowsAffected = pstmt.executeUpdate();
			return rowsAffected > 0;

		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} finally {
			try {
				conn.close();
				conn.close();
			}catch(SQLException e){
				e.printStackTrace();
			}

		}
	}

	/**
	 * 添加仓库
	 * @param warehouseCode 仓库编码
	 * @param warehouseName 仓库名称
	 * @param location 位置
	 * @param capacity 容量
	 * @param status 状态(1:启用, 0:禁用)
	 * @return 添加是否成功
	 */
	public boolean addWarehouse(String warehouseCode, String warehouseName,
								String location, int capacity, int status) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			DbUtil dbUtil = new DbUtil();
			conn = dbUtil.getCon();

			// 检查仓库编码是否已存在
			String checkSql = "SELECT warehouseId FROM tb_warehouse WHERE warehouseCode = ?";
			pstmt = conn.prepareStatement(checkSql);
			pstmt.setString(1, warehouseCode);
			rs = pstmt.executeQuery();

			if (rs.next()) {
				// 仓库编码已存在
				return false;
			}

			// 关闭检查语句
			pstmt.close();
			rs.close();

			// 执行添加操作
			String sql = "INSERT INTO tb_warehouse (warehouseCode, warehouseName, location, capacity, status) " +
					"VALUES (?, ?, ?, ?, ?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, warehouseCode);
			pstmt.setString(2, warehouseName);
			pstmt.setString(3, location);
			pstmt.setInt(4, capacity);
			pstmt.setInt(5, status);

			int rowsAffected = pstmt.executeUpdate();
			return rowsAffected > 0;

		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} finally {
			// 关闭资源
			if (rs != null) try { rs.close(); } catch (SQLException e) {}
			if (pstmt != null) try { pstmt.close(); } catch (SQLException e) {}
			if (conn != null) try { conn.close(); } catch (SQLException e) {}
		}
	}



	public List<StockInRecord> getRecentStockIns(int limit) {
		return inventorySummaryDao.getRecentStockIns(limit);
	}

	public List<StockOutRecord> getRecentStockOuts(int limit) {
		return inventorySummaryDao.getRecentStockOuts(limit);
	}


	public InventoryAlertPager getInventoryAlerts() {
		return inventorySummaryDao.getInventoryAlerts();
	}


	public boolean createOrderForAlert(int alertId) {
		try {
			return materialOrderDao.createOrderForAlert(alertId);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 忽略预警
	 */
	public boolean ignoreAlert(int alertId) {
		try {
			return inventoryAlertDao.ignoreAlert(alertId);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}


	public Map<String, Object> getAlertDetail(int alertId) {
		return inventoryAlertDao.getAlertDetail(alertId);
	}

	public List<Map<String, Object>> getSuppliersByProductId(int productId) {
		return inventoryAlertDao.getSuppliersByProductId(productId);
	}


	/**
	 * 更新库存预警状态
	 * @param alertId 预警ID
	 * @param status 状态：0-未处理，1-已处理
	 * @param remark 备注信息
	 * @return 更新是否成功
	 */
	public boolean updateAlertStatus(int alertId, int status, String remark) {
		Connection conn = null;
		PreparedStatement stmt = null;
		boolean success = false;

		try {
			DbUtil db = new DbUtil();
			conn = db.getCon();

			// 修正SQL语句，使用正确的列名
			String sql = "UPDATE tb_inventory_alert " +
					"SET alertStatus = ?, processTime = NOW(), processNote = ? " +
					"WHERE alertId = ?";

			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, status);      // 设置alertStatus
			stmt.setString(2, remark);   // 设置processNote
			stmt.setInt(3, alertId);     // 设置alertId

			int rows = stmt.executeUpdate();
			success = rows > 0;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (stmt != null) stmt.close();
				if (conn != null) conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return success;
	}





	/**
	 * 根据产品ID获取产品名称
	 */
	public String getProductNameById(int productId) {
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String productName = null;

		try {
			DbUtil db = new DbUtil();
			conn = db.getCon();
			String sql = "SELECT subTypeName FROM tb_subtype WHERE product_id = ?";
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, productId);
			rs = stmt.executeQuery();

			if (rs.next()) {
				productName = rs.getString("subTypeName");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			}
			catch(SQLException e){
				e.printStackTrace();
			}

		}

		return productName;
	}

	/**
	 * 创建采购订单
	 */
	public boolean createMaterialOrder(
			String orderNo, int productId, String productName,
			int quantity, int supplierId, java.sql.Date expectedArrivalDate,int alertNumber) {
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		boolean success = false;

		try {
			DbUtil db = new DbUtil();
			conn = db.getCon();
			conn.setAutoCommit(false); // 开启事务

			// 1. 查询供应商信息和产品单价
			String supplierSql = "SELECT s.supplierName, s.contactPerson, s.contactPhone, ps.unitPrice " +
					"FROM tb_product_supplier ps " +
					"JOIN tb_supplier s ON ps.supplierId = s.supplierId " +
					"WHERE ps.productId = ? AND ps.supplierId = ? AND ps.status = 1";
			stmt = conn.prepareStatement(supplierSql);
			stmt.setInt(1, productId);
			stmt.setInt(2, supplierId);
			rs = stmt.executeQuery();

			if (!rs.next()) {
				throw new SQLException("供应商不可用或未关联该产品");
			}

			String supplierName = rs.getString("supplierName");
			String contactPerson = rs.getString("contactPerson");
			String contactPhone = rs.getString("contactPhone");
			BigDecimal unitPrice = rs.getBigDecimal("unitPrice");
			BigDecimal subtotal = unitPrice.multiply(new BigDecimal(quantity));








			// 2. 创建订单主记录
			String orderSql = "INSERT INTO tb_material_order " +
					"(orderNo, supplierId, supplierName, contactPerson, contactPhone, " +
					"orderTime, expectedArrivalDate, totalAmount, status, remark) " +
					"VALUES (?, ?, ?, ?, ?, NOW(), ?, ?, 'PENDING', '由库存预警创建')";
			stmt = conn.prepareStatement(orderSql, Statement.RETURN_GENERATED_KEYS);
			stmt.setString(1, orderNo);
			stmt.setInt(2, supplierId);
			stmt.setString(3, supplierName);
			stmt.setString(4, contactPerson);
			stmt.setString(5, contactPhone);
			stmt.setDate(6, expectedArrivalDate);
			stmt.setBigDecimal(7, subtotal);

			stmt.executeUpdate();

			// 获取订单ID
			rs = stmt.getGeneratedKeys();
			int orderId = -1;
			if (rs.next()) {
				orderId = rs.getInt(1);
			} else {
				throw new SQLException("获取订单ID失败");
			}

			// 3. 创建订单项记录


			String getwaregetwarehouseStr="select warehouseId from tb_inventory_alert where alertId = ?";
			stmt = conn.prepareStatement(getwaregetwarehouseStr);
			stmt.setInt(1,alertNumber);
			rs = stmt.executeQuery();
			if (!rs.next()) {
				throw new SQLException("供应商信息查询失败");
			}

			int warehouseId = rs.getInt("warehouseId");


			System.out.println(orderNo);
			System.out.println(supplierName);
			System.out.println(contactPerson);
			System.out.println(contactPhone);
			System.out.println(expectedArrivalDate);
			System.out.println(subtotal);
			System.out.println(warehouseId);





			String itemSql = "INSERT INTO tb_material_order_item " +
					"(orderId, productId, productName, unitPrice, quantity, subtotal,warehouseId) " +
					"VALUES (?, ?, ?, ?, ?, ?,?)";
			stmt = conn.prepareStatement(itemSql);
			stmt.setInt(1, orderId);
			stmt.setInt(2, productId);
			stmt.setString(3, productName);
			stmt.setBigDecimal(4, unitPrice);
			stmt.setInt(5, quantity);
			stmt.setBigDecimal(6, subtotal);
			stmt.setInt(7, warehouseId);
			stmt.executeUpdate();

			conn.commit(); // 提交事务
			success = true;
		} catch (SQLException e) {
			try {
				if (conn != null && !conn.getAutoCommit()) {
					conn.rollback(); // 回滚事务
				}
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			}
			catch (SQLException ex) {
				ex.printStackTrace();
			}

		}

		return success;
	}





}
