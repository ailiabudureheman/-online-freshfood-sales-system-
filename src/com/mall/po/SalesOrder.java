package com.mall.po;

import java.util.Date;
import java.util.List;

/**
 * 销售订单实体类
 */
public class SalesOrder {
    private int orderId;              // 订单ID
    private String orderNo;           // 订单编号
    private String customerName;      // 客户名称
    private String customerPhone;     // 客户电话
    private String deliveryAddress;   // 配送地址
    private Date orderTime;           // 下单时间
    private Date requiredDeliveryDate;// 要求交货日期
    private double totalAmount;       // 订单金额
    private String status;            // 订单状态：待处理、处理中、已完成、已取消
    private String remark;            // 备注
    private Date createTime;          // 创建时间
    private Date updateTime;          // 更新时间
    private List<SalesOrderItem> items; // 订单项列表

    // 构造方法
    public SalesOrder() {
    }

    // 完整构造方法
    public SalesOrder(int orderId, String orderNo, String customerName,
                      String customerPhone, String deliveryAddress, Date orderTime,
                      Date requiredDeliveryDate, double totalAmount, String status,
                      String remark, Date createTime, Date updateTime,
                      List<SalesOrderItem> items) {
        this.orderId = orderId;
        this.orderNo = orderNo;
        this.customerName = customerName;
        this.customerPhone = customerPhone;
        this.deliveryAddress = deliveryAddress;
        this.orderTime = orderTime;
        this.requiredDeliveryDate = requiredDeliveryDate;
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

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerPhone() {
        return customerPhone;
    }

    public void setCustomerPhone(String customerPhone) {
        this.customerPhone = customerPhone;
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    public Date getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(Date orderTime) {
        this.orderTime = orderTime;
    }

    public Date getRequiredDeliveryDate() {
        return requiredDeliveryDate;
    }

    public void setRequiredDeliveryDate(Date requiredDeliveryDate) {
        this.requiredDeliveryDate = requiredDeliveryDate;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
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

    public List<SalesOrderItem> getItems() {
        return items;
    }

    public void setItems(List<SalesOrderItem> items) {
        this.items = items;
    }

    @Override
    public String toString() {
        return "SalesOrder{" +
                "orderId=" + orderId +
                ", orderNo='" + orderNo + '\'' +
                ", customerName='" + customerName + '\'' +
                ", customerPhone='" + customerPhone + '\'' +
                ", deliveryAddress='" + deliveryAddress + '\'' +
                ", orderTime=" + orderTime +
                ", requiredDeliveryDate=" + requiredDeliveryDate +
                ", totalAmount=" + totalAmount +
                ", status='" + status + '\'' +
                ", remark='" + remark + '\'' +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", itemsCount=" + (items != null ? items.size() : 0) +
                '}';
    }
}

