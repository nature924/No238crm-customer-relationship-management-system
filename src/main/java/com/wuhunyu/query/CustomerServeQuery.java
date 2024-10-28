package com.wuhunyu.query;

import com.wuhunyu.base.BaseQuery;
import java.io.Serializable;

/**
 * 客户服务查询类
 *
 * @author wuhunyu
 * @version 1.0
 * @date 2021-02-05 10:56
 */
public class CustomerServeQuery extends BaseQuery implements Serializable {

    /**
     * 客户名称
     */
    private String customer;

    /**
     * 服务类型
     */
    private Integer serveType;

    /**
     * 服务状态
     * 服务创建 fw_001
     * 服务分配 fw_002
     * 服务处理 fw_003
     * 服务反馈 fw_004
     * 服务归档 fw_005
     */
    private String state;

    /**
     * 指派人id
     */
    private String assigner;

    /**
     * 创建人
     */
    private String createPeople;

    public CustomerServeQuery() {
    }

    public CustomerServeQuery(String customer, Integer serveType, String state, String assigner, String createPeople) {
        this.customer = customer;
        this.serveType = serveType;
        this.state = state;
        this.assigner = assigner;
        this.createPeople = createPeople;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public Integer getServeType() {
        return serveType;
    }

    public void setServeType(Integer serveType) {
        this.serveType = serveType;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getAssigner() {
        return assigner;
    }

    public void setAssigner(String assigner) {
        this.assigner = assigner;
    }

    public String getCreatePeople() {
        return createPeople;
    }

    public void setCreatePeople(String createPeople) {
        this.createPeople = createPeople;
    }
}
