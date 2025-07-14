package com.mall.po;

public enum StorageStatus {
    PENDING("待入库"),
    COMPLETED("已入库");

    private String displayName;

    StorageStatus(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}