package com.mall.po;

import java.util.Date; /**
 * 原材料订单项实体类
 */
public class MaterialOrderItem {
    private int itemId;               // 订单项ID
    private int orderId;              // 订单ID
    private int productId;            // 产品ID
    private String productName;       // 产品名称
    private String specification;     // 规格
    private double unitPrice;         // 单价
    private int quantity;             // 数量
    private double subtotal;          // 小计
    private Date createTime;          // 创建时间
    private Date updateTime;          // 更新时间

    // 构造方法
    public MaterialOrderItem() {
    }

    // 完整构造方法
    public MaterialOrderItem(int itemId, int orderId, int productId,
                             String productName, String specification,
                             double unitPrice, int quantity, double subtotal,
                             Date createTime, Date updateTime) {
        this.itemId = itemId;
        this.orderId = orderId;
        this.productId = productId;
        this.productName = productName;
        this.specification = specification;
        this.unitPrice = unitPrice;
        this.quantity = quantity;
        this.subtotal = subtotal;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }

    // getter和setter方法
    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
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

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
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
        return "MaterialOrderItem{" +
                "itemId=" + itemId +
                ", orderId=" + orderId +
                ", productId=" + productId +
                ", productName='" + productName + '\'' +
                ", specification='" + specification + '\'' +
                ", unitPrice=" + unitPrice +
                ", quantity=" + quantity +
                ", subtotal=" + subtotal +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }
}
