package com.mall.po;

import java.util.Date;

public class StockInRecord {
    private int inventoryId;  // 对应tb_product_inventory的主键
    private int productId;
    private String productName;
    private int warehouseId;
    private String warehouseName;
    private int quantity;
    private Date stockInTime;
    private String operator;  // 操作人，可从库存记录或日志表获取
    private String batchNumber;

    // Getter方法
    public int getInventoryId() {
        return inventoryId;
    }

    public int getProductId() {
        return productId;
    }

    public String getProductName() {
        return productName;
    }

    public int getWarehouseId() {
        return warehouseId;
    }

    public String getWarehouseName() {
        return warehouseName;
    }

    public int getQuantity() {
        return quantity;
    }

    public Date getStockInTime() {
        return stockInTime;
    }

    public String getOperator() {
        return operator;
    }

    public String getBatchNumber() {
        return batchNumber;
    }

    // Setter方法
    public void setInventoryId(int inventoryId) {
        this.inventoryId = inventoryId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public void setWarehouseId(int warehouseId) {
        this.warehouseId = warehouseId;
    }

    public void setWarehouseName(String warehouseName) {
        this.warehouseName = warehouseName;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setStockInTime(Date stockInTime) {
        this.stockInTime = stockInTime;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public void setBatchNumber(String batchNumber) {
        this.batchNumber = batchNumber;
    }
}
