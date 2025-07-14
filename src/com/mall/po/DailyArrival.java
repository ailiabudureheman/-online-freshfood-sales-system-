package com.mall.po;

import java.util.Date;

public class DailyArrival {
    private int arrivalId;          // 到货计划ID
    private int orderId;            // 订单ID
    private String orderNo;         // 订单编号
    private String supplierName;    // 供应商名称
    private String productName;     // 产品名称
    private int quantity;           // 数量
    private Date arrivalDate;       // 到货日期
    private String status;          // 状态：计划中、已到货、延迟、已取消
    private String expectedTime;    // 预计时间
    private String actualTime;      // 实际时间
    private String remark;          // 备注
    private Date createTime;        // 创建时间
    private Date updateTime;        // 更新时间



    // 添加入库状态相关属性
    private String storageStatus;   // 入库状态：待入库、已入库
    private Date storageTime;       // 入库时间
    private String storageOperator; // 入库操作员

    // 添加对应的getter和setter方法
    public String getStorageStatus() {
        return storageStatus;
    }

    public void setStorageStatus(String storageStatus) {
        this.storageStatus = storageStatus;
    }

    public Date getStorageTime() {
        return storageTime;
    }

    public void setStorageTime(Date storageTime) {
        this.storageTime = storageTime;
    }

    public String getStorageOperator() {
        return storageOperator;
    }

    public void setStorageOperator(String storageOperator) {
        this.storageOperator = storageOperator;
    }












    // 构造方法
    public DailyArrival() {
    }

    // 完整构造方法
    public DailyArrival(int arrivalId, int orderId, String orderNo, String supplierName,
                        String productName, int quantity, Date arrivalDate, String status,
                        String expectedTime, String actualTime, String remark,
                        Date createTime, Date updateTime) {
        this.arrivalId = arrivalId;
        this.orderId = orderId;
        this.orderNo = orderNo;
        this.supplierName = supplierName;
        this.productName = productName;
        this.quantity = quantity;
        this.arrivalDate = arrivalDate;
        this.status = status;
        this.expectedTime = expectedTime;
        this.actualTime = actualTime;
        this.remark = remark;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }

    // getter和setter方法
    public int getArrivalId() {
        return arrivalId;
    }

    public void setArrivalId(int arrivalId) {
        this.arrivalId = arrivalId;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Date getArrivalDate() {
        return arrivalDate;
    }

    public void setArrivalDate(Date arrivalDate) {
        this.arrivalDate = arrivalDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getExpectedTime() {
        return expectedTime;
    }

    public void setExpectedTime(String expectedTime) {
        this.expectedTime = expectedTime;
    }

    public String getActualTime() {
        return actualTime;
    }

    public void setActualTime(String actualTime) {
        this.actualTime = actualTime;
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

    @Override
    public String toString() {
        return "DailyArrival{" +
                "arrivalId=" + arrivalId +
                ", orderId=" + orderId +
                ", orderNo='" + orderNo + '\'' +
                ", supplierName='" + supplierName + '\'' +
                ", productName='" + productName + '\'' +
                ", quantity=" + quantity +
                ", arrivalDate=" + arrivalDate +
                ", status='" + status + '\'' +
                ", expectedTime='" + expectedTime + '\'' +
                ", actualTime='" + actualTime + '\'' +
                ", remark='" + remark + '\'' +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }


}