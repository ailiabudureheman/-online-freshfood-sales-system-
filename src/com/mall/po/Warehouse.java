package com.mall.po;

import java.util.Date;

/**
 * 仓库信息实体类
 */
public class Warehouse {
    private int warehouseId;          // 仓库ID
    private String warehouseCode;     // 仓库编码
    private String warehouseName;     // 仓库名称
    private String location;          // 位置
    private int capacity;             // 容量
    private int currentStock;         // 当前库存
    private Date lastInspectionDate;  // 上次检查日期
    private Date nextInspectionDate;  // 下次检查日期
    private int status;               // 状态：1-正常，0-关闭
    private Date createTime;          // 创建时间
    private Date updateTime;          // 更新时间

    // 构造方法
    public Warehouse() {
    }

    // getter和setter方法
    public int getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(int warehouseId) {
        this.warehouseId = warehouseId;
    }

    public String getWarehouseCode() {
        return warehouseCode;
    }

    public void setWarehouseCode(String warehouseCode) {
        this.warehouseCode = warehouseCode;
    }

    public String getWarehouseName() {
        return warehouseName;
    }

    public void setWarehouseName(String warehouseName) {
        this.warehouseName = warehouseName;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public int getCurrentStock() {
        return currentStock;
    }

    public void setCurrentStock(int currentStock) {
        this.currentStock = currentStock;
    }

    public Date getLastInspectionDate() {
        return lastInspectionDate;
    }

    public void setLastInspectionDate(Date lastInspectionDate) {
        this.lastInspectionDate = lastInspectionDate;
    }

    public Date getNextInspectionDate() {
        return nextInspectionDate;
    }

    public void setNextInspectionDate(Date nextInspectionDate) {
        this.nextInspectionDate = nextInspectionDate;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
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
        return "Warehouse{" +
                "warehouseId=" + warehouseId +
                ", warehouseCode='" + warehouseCode + '\'' +
                ", warehouseName='" + warehouseName + '\'' +
                ", location='" + location + '\'' +
                ", capacity=" + capacity +
                ", currentStock=" + currentStock +
                ", lastInspectionDate=" + lastInspectionDate +
                ", nextInspectionDate=" + nextInspectionDate +
                ", status=" + status +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }
}

