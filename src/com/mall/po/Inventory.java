package com.mall.po;

import java.util.Date;

public class Inventory {
    private int inventoryId;          // 库存ID
    private int productId;            // 产品ID
    private int warehouseId;          // 仓库ID
    private String productName;       // 产品名称
    private String specification;     // 规格
    private String warehouseName;     // 仓库名称
    private String categoryName;      // 类别名称
    private int quantity;             // 库存数量
    private int safetyStock;          // 安全库存
    private int reorderPoint;         // 再订货点
    private Date expiryDate;          // 过期日期
    private String status;            // 库存状态：正常、不足、缺货
    private Date lastUpdateDate;      // 最后更新日期
    private Date createTime;          // 创建时间
    private Date updateTime;          // 更新时间

    // 构造方法
    public Inventory() {
    }

    // 完整构造方法
    public Inventory(int inventoryId, int productId, int warehouseId,
                     String productName, String specification, String warehouseName,
                     String categoryName, int quantity, int safetyStock,
                     int reorderPoint, Date expiryDate, String status,
                     Date lastUpdateDate, Date createTime, Date updateTime) {
        this.inventoryId = inventoryId;
        this.productId = productId;
        this.warehouseId = warehouseId;
        this.productName = productName;
        this.specification = specification;
        this.warehouseName = warehouseName;
        this.categoryName = categoryName;
        this.quantity = quantity;
        this.safetyStock = safetyStock;
        this.reorderPoint = reorderPoint;
        this.expiryDate = expiryDate;
        this.status = status;
        this.lastUpdateDate = lastUpdateDate;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }

    // getter和setter方法
    public int getInventoryId() {
        return inventoryId;
    }

    public void setInventoryId(int inventoryId) {
        this.inventoryId = inventoryId;
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

    public String getSpecification() {
        return specification;
    }

    public void setSpecification(String specification) {
        this.specification = specification;
    }

    public String getWarehouseName() {
        return warehouseName;
    }

    public void setWarehouseName(String warehouseName) {
        this.warehouseName = warehouseName;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
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

    public int getReorderPoint() {
        return reorderPoint;
    }

    public void setReorderPoint(int reorderPoint) {
        this.reorderPoint = reorderPoint;
    }

    public Date getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getLastUpdateDate() {
        return lastUpdateDate;
    }

    public void setLastUpdateDate(Date lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
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
        return "Inventory{" +
                "inventoryId=" + inventoryId +
                ", productId=" + productId +
                ", warehouseId=" + warehouseId +
                ", productName='" + productName + '\'' +
                ", specification='" + specification + '\'' +
                ", warehouseName='" + warehouseName + '\'' +
                ", categoryName='" + categoryName + '\'' +
                ", quantity=" + quantity +
                ", safetyStock=" + safetyStock +
                ", reorderPoint=" + reorderPoint +
                ", expiryDate=" + expiryDate +
                ", status='" + status + '\'' +
                ", lastUpdateDate=" + lastUpdateDate +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }
}