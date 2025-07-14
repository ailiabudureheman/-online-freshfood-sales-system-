package com.mall.po;

import java.util.Date;

/**
 * 供应商实体类
 */
public class Supplier {
    private int supplierId;           // 供应商ID
    private String supplierCode;      // 供应商编码
    private String supplierName;      // 供应商名称
    private String contactPerson;     // 联系人
    private String contactPhone;      // 联系电话
    private String contactEmail;      // 联系邮箱
    private String address;           // 地址
    private String businessLicense;   // 营业执照
    private String taxRegistration;   // 税务登记号
    private String bankAccount;       // 银行账户
    private String bankName;          // 开户行
    private String creditLevel;       // 信用等级
    private String status;            // 状态：正常、禁用
    private String remark;            // 备注
    private Date createTime;          // 创建时间
    private Date updateTime;          // 更新时间

    // 构造方法
    public Supplier() {
    }

    // 完整构造方法
    public Supplier(int supplierId, String supplierCode, String supplierName,
                    String contactPerson, String contactPhone, String contactEmail,
                    String address, String businessLicense, String taxRegistration,
                    String bankAccount, String bankName, String creditLevel,
                    String status, String remark, Date createTime, Date updateTime) {
        this.supplierId = supplierId;
        this.supplierCode = supplierCode;
        this.supplierName = supplierName;
        this.contactPerson = contactPerson;
        this.contactPhone = contactPhone;
        this.contactEmail = contactEmail;
        this.address = address;
        this.businessLicense = businessLicense;
        this.taxRegistration = taxRegistration;
        this.bankAccount = bankAccount;
        this.bankName = bankName;
        this.creditLevel = creditLevel;
        this.status = status;
        this.remark = remark;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }

    // getter和setter方法
    public int getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(int supplierId) {
        this.supplierId = supplierId;
    }

    public String getSupplierCode() {
        return supplierCode;
    }

    public void setSupplierCode(String supplierCode) {
        this.supplierCode = supplierCode;
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

    public String getContactEmail() {
        return contactEmail;
    }

    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getBusinessLicense() {
        return businessLicense;
    }

    public void setBusinessLicense(String businessLicense) {
        this.businessLicense = businessLicense;
    }

    public String getTaxRegistration() {
        return taxRegistration;
    }

    public void setTaxRegistration(String taxRegistration) {
        this.taxRegistration = taxRegistration;
    }

    public String getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(String bankAccount) {
        this.bankAccount = bankAccount;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getCreditLevel() {
        return creditLevel;
    }

    public void setCreditLevel(String creditLevel) {
        this.creditLevel = creditLevel;
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

    @Override
    public String toString() {
        return "Supplier{" +
                "supplierId=" + supplierId +
                ", supplierCode='" + supplierCode + '\'' +
                ", supplierName='" + supplierName + '\'' +
                ", contactPerson='" + contactPerson + '\'' +
                ", contactPhone='" + contactPhone + '\'' +
                ", contactEmail='" + contactEmail + '\'' +
                ", address='" + address + '\'' +
                ", businessLicense='" + businessLicense + '\'' +
                ", taxRegistration='" + taxRegistration + '\'' +
                ", bankAccount='" + bankAccount + '\'' +
                ", bankName='" + bankName + '\'' +
                ", creditLevel='" + creditLevel + '\'' +
                ", status='" + status + '\'' +
                ", remark='" + remark + '\'' +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }
}