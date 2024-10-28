package com.wuhunyu.query;

import com.alibaba.druid.proxy.jdbc.PreparedStatementProxyImpl;
import com.wuhunyu.base.BaseQuery;

import java.io.PrintWriter;

/**
 * 客户信息查询类
 *
 * @author wuhunyu
 * @version 1.0
 * @date 2020-12-16 20:20
 */
public class CustomerQuery extends BaseQuery {

    /**
     * 客户名称
     */
    private String customerName;

    /**
     * 客户编号
     */
    private String customerNo;

    /**
     * 客户级别
     */
    private String level;

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerNo() {
        return customerNo;
    }

    public void setCustomerNo(String customerNo) {
        this.customerNo = customerNo;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }
}
