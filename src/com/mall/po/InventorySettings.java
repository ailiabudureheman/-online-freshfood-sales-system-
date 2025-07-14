package com.mall.po;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class InventorySettings {
	private Integer settingsId;
	private BigDecimal defaultSafetyStockFactor;
	private BigDecimal defaultReorderPointFactor;
	private Integer expiryAlertDays;
	private String alertNotificationMethod;
	private Integer warehouseInspectionCycle;
	private Integer warehouseInspectionAlertDays;
	private Integer defaultSupplierId;
	private Integer defaultLeadTime;
	private String materialOrderNotificationMethod;
	private Timestamp createTime;
	private Timestamp updateTime;

	// getter 和 setter 方法
	public Integer getSettingsId() {
		return settingsId;
	}

	public void setSettingsId(Integer settingsId) {
		this.settingsId = settingsId;
	}

	public BigDecimal getDefaultSafetyStockFactor() {
		return defaultSafetyStockFactor;
	}

	public void setDefaultSafetyStockFactor(BigDecimal defaultSafetyStockFactor) {
		this.defaultSafetyStockFactor = defaultSafetyStockFactor;
	}

	public BigDecimal getDefaultReorderPointFactor() {
		return defaultReorderPointFactor;
	}

	public void setDefaultReorderPointFactor(BigDecimal defaultReorderPointFactor) {
		this.defaultReorderPointFactor = defaultReorderPointFactor;
	}

	public Integer getExpiryAlertDays() {
		return expiryAlertDays;
	}

	public void setExpiryAlertDays(Integer expiryAlertDays) {
		this.expiryAlertDays = expiryAlertDays;
	}

	public String getAlertNotificationMethod() {
		return alertNotificationMethod;
	}

	public void setAlertNotificationMethod(String alertNotificationMethod) {
		this.alertNotificationMethod = alertNotificationMethod;
	}

	public Integer getWarehouseInspectionCycle() {
		return warehouseInspectionCycle;
	}

	public void setWarehouseInspectionCycle(Integer warehouseInspectionCycle) {
		this.warehouseInspectionCycle = warehouseInspectionCycle;
	}

	public Integer getWarehouseInspectionAlertDays() {
		return warehouseInspectionAlertDays;
	}

	public void setWarehouseInspectionAlertDays(Integer warehouseInspectionAlertDays) {
		this.warehouseInspectionAlertDays = warehouseInspectionAlertDays;
	}

	public Integer getDefaultSupplierId() {
		return defaultSupplierId;
	}

	public void setDefaultSupplierId(Integer defaultSupplierId) {
		this.defaultSupplierId = defaultSupplierId;
	}

	public Integer getDefaultLeadTime() {
		return defaultLeadTime;
	}

	public void setDefaultLeadTime(Integer defaultLeadTime) {
		this.defaultLeadTime = defaultLeadTime;
	}

	public String getMaterialOrderNotificationMethod() {
		return materialOrderNotificationMethod;
	}

	public void setMaterialOrderNotificationMethod(String materialOrderNotificationMethod) {
		this.materialOrderNotificationMethod = materialOrderNotificationMethod;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public Timestamp getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}
}