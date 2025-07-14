package com.mall.po;

import java.util.Date;

/**
 * 产品实体类
 */
public class Product {
    private int productId;            // 产品ID
    private int categoryId;           // 类别ID
    private String categoryName;      // 类别名称
    private String productName;       // 产品名称
    private String specification;     // 规格
    private String barcode;           // 条形码
    private double purchasePrice;     // 采购价
    private double sellingPrice;      // 销售价
    private int safetyStock;          // 安全库存
    private int reorderPoint;         // 再订货点
    private int currentStock;         // 当前库存
    private String status;            // 状态：正常、下架、缺货
    private String remark;            // 备注
    private Date createTime;          // 创建时间
    private Date updateTime;          // 更新时间
    private int supplierId;           // 供应商ID
    private String supplierName;      // 供应商名称

    // 构造方法
    public Product() {
    }

    // 完整构造方法
    public Product(int productId, int categoryId, String categoryName,
                   String productName, String specification, String barcode,
                   double purchasePrice, double sellingPrice, int safetyStock,
                   int reorderPoint, int currentStock, String status,
                   String remark, Date createTime, Date updateTime,
                   int supplierId, String supplierName) {
        this.productId = productId;
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.productName = productName;
        this.specification = specification;
        this.barcode = barcode;
        this.purchasePrice = purchasePrice;
        this.sellingPrice = sellingPrice;
        this.safetyStock = safetyStock;
        this.reorderPoint = reorderPoint;
        this.currentStock = currentStock;
        this.status = status;
        this.remark = remark;
        this.createTime = createTime;
        this.updateTime = updateTime;
        this.supplierId = supplierId;
        this.supplierName = supplierName;
    }

    // getter和setter方法
    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
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

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public double getPurchasePrice() {
        return purchasePrice;
    }

    public void setPurchasePrice(double purchasePrice) {
        this.purchasePrice = purchasePrice;
    }

    public double getSellingPrice() {
        return sellingPrice;
    }

    public void setSellingPrice(double sellingPrice) {
        this.sellingPrice = sellingPrice;
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

    public int getCurrentStock() {
        return currentStock;
    }

    public void setCurrentStock(int currentStock) {
        this.currentStock = currentStock;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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

    public int getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(int supplierId) {
        this.supplierId = supplierId;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    @Override
    public String toString() {
        return "Product{" +
                "productId=" + productId +
                ", categoryId=" + categoryId +
                ", categoryName='" + categoryName + '\'' +
                ", productName='" + productName + '\'' +
                ", specification='" + specification + '\'' +
                ", barcode='" + barcode + '\'' +
                ", purchasePrice=" + purchasePrice +
                ", sellingPrice=" + sellingPrice +
                ", safetyStock=" + safetyStock +
                ", reorderPoint=" + reorderPoint +
                ", currentStock=" + currentStock +
                ", status='" + status + '\'' +
                ", remark='" + remark + '\'' +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", supplierId=" + supplierId +
                ", supplierName='" + supplierName + '\'' +
                '}';
    }
}