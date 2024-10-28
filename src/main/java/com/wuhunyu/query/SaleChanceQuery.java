package com.wuhunyu.query;

import com.wuhunyu.base.BaseQuery;

/**
 * 销售机会查询结果处理类
 *
 * @author wuhunyu
 * @version 1.0
 * @date 2020-12-09 16:40
 */
public class SaleChanceQuery extends BaseQuery {

    /**
     * 客户名
     */
    private String customerName;

    /**
     * 创建人
     */
    private String createMan;

    /**
     * 分配状态 0:未分配；1：已分配
     */
    private Integer state;

    /**
     * 开发状态
     */
    private String devResult;

    /**
     * 指派人
     */
    private String assignMan;

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCreateMan() {
        return createMan;
    }

    public void setCreateMan(String createMan) {
        this.createMan = createMan;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getDevResult() {
        return devResult;
    }

    public void setDevResult(String devResult) {
        this.devResult = devResult;
    }

    public String getAssignMan() {
        return assignMan;
    }

    public void setAssignMan(String assignMan) {
        this.assignMan = assignMan;
    }

    @Override
    public String toString() {
        return "SaleChanceQuery{" +
                "customerName='" + customerName + '\'' +
                ", createMan='" + createMan + '\'' +
                ", state=" + state +
                ", devResult='" + devResult + '\'' +
                ", assignMan='" + assignMan + '\'' +
                '}';
    }
}
