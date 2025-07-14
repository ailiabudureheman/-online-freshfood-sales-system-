package com.mall.po;

public enum OrderStatus {
    PENDING("待处理"),
    PROCESSING("处理中"),
    SHIPPED("已发货"),
    COMPLETED("已完成"),
    CANCELLED("已取消");

    private String displayName;

    OrderStatus(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}