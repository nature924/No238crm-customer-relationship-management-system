package com.wuhunyu.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

/**
 * SaleChance销售机会实体类
 *
 * @author wuhunyu
 */
public class SaleChance {
    /**
     * 自动增长
     */
    private Integer id;

    /**
     * 机会来源
     */
    private String chanceSource;

    /**
     * 非空必填
     */
    private String customerName;

    /**
     * 成功几率
     */
    private Integer cgjl;

    /**
     * 概要
     */
    private String overview;

    /**
     * 非空必填
     */
    private String linkMan;

    /**
     * 非空必填
     */
    private String linkPhone;

    /**
     * 描述
     */
    private String description;

    /**
     * 默认为登录用户
     */
    private String createMan;

    /**
     * 为空：
     *      state为未分配状态(0)
     *      assignTime为null
     *      devResult为未开发状态(0)
     * 不为空：
     *      则state为已分配状态(1)
     *      assignTime为当前系统时间
     *      devResult为开发中状态(1)
     */
    private String assignMan;

    /**
     * 指派人，存在于t_user表
     */
    private String userName;

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date assignTime;

    /**
     * 分配状态
     */
    private Integer state;

    private Integer devResult;

    /**
     * 插入操作：
     *      有效(1)
     * 更新操作：
     *      无效(0)
     */
    private Integer isValid;

    /**
     * 执行插入操作时的系统时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date createDate;

    /**
     * 当前系统时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date updateDate;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getChanceSource() {
        return chanceSource;
    }

    public void setChanceSource(String chanceSource) {
        this.chanceSource = chanceSource == null ? null : chanceSource.trim();
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName == null ? null : customerName.trim();
    }

    public Integer getCgjl() {
        return cgjl;
    }

    public void setCgjl(Integer cgjl) {
        this.cgjl = cgjl;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview == null ? null : overview.trim();
    }

    public String getLinkMan() {
        return linkMan;
    }

    public void setLinkMan(String linkMan) {
        this.linkMan = linkMan == null ? null : linkMan.trim();
    }

    public String getLinkPhone() {
        return linkPhone;
    }

    public void setLinkPhone(String linkPhone) {
        this.linkPhone = linkPhone == null ? null : linkPhone.trim();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    public String getCreateMan() {
        return createMan;
    }

    public void setCreateMan(String createMan) {
        this.createMan = createMan == null ? null : createMan.trim();
    }

    public String getAssignMan() {
        return assignMan;
    }

    public void setAssignMan(String assignMan) {
        this.assignMan = assignMan == null ? null : assignMan.trim();
    }

    public Date getAssignTime() {
        return assignTime;
    }

    public void setAssignTime(Date assignTime) {
        this.assignTime = assignTime;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Integer getDevResult() {
        return devResult;
    }

    public void setDevResult(Integer devResult) {
        this.devResult = devResult;
    }

    public Integer getIsValid() {
        return isValid;
    }

    public void setIsValid(Integer isValid) {
        this.isValid = isValid;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }
}