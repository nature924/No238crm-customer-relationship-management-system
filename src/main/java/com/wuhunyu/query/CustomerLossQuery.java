package com.wuhunyu.query;

import com.wuhunyu.base.BaseQuery;

/**
 * 客户流失查询类
 *
 * @author wuhunyu
 * @version 1.0
 * @date 2020-12-17 22:33
 */
public class CustomerLossQuery extends BaseQuery {

    private String customerNo;

    private String customerName;

    private Integer state;

    public String getCustomerNo() {
        return customerNo;
    }

    public void setCustomerNo(String customerNo) {
        this.customerNo = customerNo;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }
}
