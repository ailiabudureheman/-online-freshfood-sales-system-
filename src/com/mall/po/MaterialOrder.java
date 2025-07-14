package com.mall.po;

import java.util.Date;
import java.util.List;

/**
 * 原材料订单实体类
 */
public class MaterialOrder {
    private int orderId;              // 订单ID
    private String orderNo;           // 订单编号
    private int supplierId;           // 供应商ID
    private String supplierName;      // 供应商名称
    private String contactPerson;     // 联系人
    private String contactPhone;      // 联系电话
    private Date orderTime;           // 下单时间
    private Date expectedArrivalDate; // 预计到货日期
    private double totalAmount;       // 订单金额
//    private String status;            // 订单状态：待处理、已确认、已发货、已送达、已取消
    private String remark;            // 备注
    private Date createTime;          // 创建时间
    private Date updateTime;          // 更新时间
    private List<MaterialOrderItem> items; // 订单项列表





    private StorageStatus storageStatus; // 入库状态
    private Date storageTime; // 入库时间
    private String storageOperator; // 入库操作员

    // Getters and Setters


    public StorageStatus getStorageStatus() {
        return storageStatus;
    }

    public void setStorageStatus(StorageStatus storageStatus) {
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




    private int materialCount;

    // 构造函数、getter和setter方法

    public int getMaterialCount() {
        return materialCount;
    }

    public void setMaterialCount(int materialCount) {
        this.materialCount = materialCount;
    }


    private OrderStatus status; // 使用枚举类型

    // getter和setter
    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }


    // 构造方法
    public MaterialOrder() {
    }

    // 完整构造方法
    public MaterialOrder(int orderId, String orderNo, int supplierId,
                         String supplierName, String contactPerson,
                         String contactPhone, Date orderTime,
                         Date expectedArrivalDate, double totalAmount,
                         OrderStatus status, String remark, Date createTime,
                         Date updateTime, List<MaterialOrderItem> items) {
        this.orderId = orderId;
        this.orderNo = orderNo;
        this.supplierId = supplierId;
        this.supplierName = supplierName;
        this.contactPerson = contactPerson;
        this.contactPhone = contactPhone;
        this.orderTime = orderTime;
        this.expectedArrivalDate = expectedArrivalDate;
        this.totalAmount = totalAmount;
        this.status = status;
        this.remark = remark;
        this.createTime = createTime;
        this.updateTime = updateTime;
        this.items = items;
    }

    // getter和setter方法
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

    public String getContactPerson() {
        return contactPerson;
    }

    public void setContactPerson(String contactPerson) {
        this.contactPerson = contactPerson;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }

    public Date getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(Date orderTime) {
        this.orderTime = orderTime;
    }

    public Date getExpectedArrivalDate() {
        return expectedArrivalDate;
    }

    public void setExpectedArrivalDate(Date expectedArrivalDate) {
        this.expectedArrivalDate = expectedArrivalDate;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

//    public String getStatus() {
//        return status;
//    }
//
//    public void setStatus(String status) {
//        this.status = status;
//    }

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

    public List<MaterialOrderItem> getItems() {
        return items;
    }

    public void setItems(List<MaterialOrderItem> items) {
        this.items = items;
    }

    @Override
    public String toString() {
        return "MaterialOrder{" +
                "orderId=" + orderId +
                ", orderNo='" + orderNo + '\'' +
                ", supplierId=" + supplierId +
                ", supplierName='" + supplierName + '\'' +
                ", contactPerson='" + contactPerson + '\'' +
                ", contactPhone='" + contactPhone + '\'' +
                ", orderTime=" + orderTime +
                ", expectedArrivalDate=" + expectedArrivalDate +
                ", totalAmount=" + totalAmount +
                ", status='" + status + '\'' +
                ", remark='" + remark + '\'' +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", itemsCount=" + (items != null ? items.size() : 0) +
                '}';
    }
}

