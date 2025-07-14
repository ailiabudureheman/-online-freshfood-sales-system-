package com.mall.po;

import java.util.Date;

/**
 * 库存预警实体类
 */
public class InventoryAlert {
    private int alertId;              // 预警ID
    private int productId;            // 产品ID
    private int warehouseId;          // 仓库ID
    private String productName;       // 产品名称
    private String warehouseName;     // 仓库名称
    private String alertType;         // 预警类型：LOW_STOCK(库存不足)、EXPIRING_SOON(即将过期)、EXPIRED(已过期)
    private String alertMessage;      // 预警消息
    private int alertStatus;          // 预警状态：0(未处理)、1(已处理)
    private Date alertTime;           // 预警时间
    private Date processTime;         // 处理时间
    private String processNote;       // 处理备注
    private int quantity;             // 当前库存数量
    private int safetyStock;          // 安全库存
    private Date expiryDate;          // 过期日期
    private Date createTime;          // 创建时间
    private Date updateTime;          // 更新时间


    private int currentStock;  // 新增仓库当前库存属性


    public int getCurrentStock() { return currentStock; }
    public void setCurrentStock(int currentStock) { this.currentStock = currentStock; }


    private String alertLevel;  // 新增预警级别属性

    // Getter和Setter方法
    public String getAlertLevel() { return alertLevel; }
    public void setAlertLevel(String alertLevel) { this.alertLevel = alertLevel; }


    // 构造方法
    public InventoryAlert() {
    }

    // 完整构造方法
    public InventoryAlert(int alertId, int productId, int warehouseId,
                          String productName, String warehouseName,
                          String alertType, String alertMessage, int alertStatus,
                          Date alertTime, Date processTime, String processNote,
                          int quantity, int safetyStock, Date expiryDate,
                          Date createTime, Date updateTime) {
        this.alertId = alertId;
        this.productId = productId;
        this.warehouseId = warehouseId;
        this.productName = productName;
        this.warehouseName = warehouseName;
        this.alertType = alertType;
        this.alertMessage = alertMessage;
        this.alertStatus = alertStatus;
        this.alertTime = alertTime;
        this.processTime = processTime;
        this.processNote = processNote;
        this.quantity = quantity;
        this.safetyStock = safetyStock;
        this.expiryDate = expiryDate;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }

    // getter和setter方法
    public int getAlertId() {
        return alertId;
    }

    public void setAlertId(int alertId) {
        this.alertId = alertId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(int warehouseId) {
        this.warehouseId = warehouseId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getWarehouseName() {
        return warehouseName;
    }

    public void setWarehouseName(String warehouseName) {
        this.warehouseName = warehouseName;
    }

    public String getAlertType() {
        return alertType;
    }

    public void setAlertType(String alertType) {
        this.alertType = alertType;
    }

    public String getAlertMessage() {
        return alertMessage;
    }

    public void setAlertMessage(String alertMessage) {
        this.alertMessage = alertMessage;
    }

    public int getAlertStatus() {
        return alertStatus;
    }

    public void setAlertStatus(int alertStatus) {
        this.alertStatus = alertStatus;
    }

    public Date getAlertTime() {
        return alertTime;
    }

    public void setAlertTime(Date alertTime) {
        this.alertTime = alertTime;
    }

    public Date getProcessTime() {
        return processTime;
    }

    public void setProcessTime(Date processTime) {
        this.processTime = processTime;
    }

    public String getProcessNote() {
        return processNote;
    }

    public void setProcessNote(String processNote) {
        this.processNote = processNote;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getSafetyStock() {
        return safetyStock;
    }

    public void setSafetyStock(int safetyStock) {
        this.safetyStock = safetyStock;
    }

    public Date getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public String toString() {
        return "InventoryAlert{" +
                "alertId=" + alertId +
                ", productId=" + productId +
                ", warehouseId=" + warehouseId +
                ", productName='" + productName + '\'' +
                ", warehouseName='" + warehouseName + '\'' +
                ", alertType='" + alertType + '\'' +
                ", alertMessage='" + alertMessage + '\'' +
                ", alertStatus=" + alertStatus +
                ", alertTime=" + alertTime +
                ", processTime=" + processTime +
                ", processNote='" + processNote + '\'' +
                ", quantity=" + quantity +
                ", safetyStock=" + safetyStock +
                ", expiryDate=" + expiryDate +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }
}