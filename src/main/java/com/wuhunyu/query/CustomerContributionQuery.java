package com.wuhunyu.query;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.wuhunyu.base.BaseQuery;
import java.io.Serializable;
import java.util.Date;

/**
 * 客户贡献查询类
 *
 * @author wuhunyu
 * @version 1.0
 * @date 2021-02-07 21:20
 */
public class CustomerContributionQuery extends BaseQuery implements Serializable {

    /**
     * 客户名
     */
    private String customer;

    /**
     * 最小金额，方便起见不使用BigDecimal
     */
    private Integer minMoney;

    /**
     * 最大金额
     */
    private Integer maxMoney;

    /**
     * 最小下单时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date minDate;

    /**
     * 最大下单时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date maxDate;

    public CustomerContributionQuery() {
    }

    public CustomerContributionQuery(String customer, Integer minMoney, Integer maxMoney, Date minDate, Date maxDate) {
        this.customer = customer;
        this.minMoney = minMoney;
        this.maxMoney = maxMoney;
        this.minDate = minDate;
        this.maxDate = maxDate;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public Integer getMinMoney() {
        return minMoney;
    }

    public void setMinMoney(Integer minMoney) {
        this.minMoney = minMoney;
    }

    public Integer getMaxMoney() {
        return maxMoney;
    }

    public void setMaxMoney(Integer maxMoney) {
        this.maxMoney = maxMoney;
    }

    public Date getMinDate() {
        return minDate;
    }

    public void setMinDate(Date minDate) {
        this.minDate = minDate;
    }

    public Date getMaxDate() {
        return maxDate;
    }

    public void setMaxDate(Date maxDate) {
        this.maxDate = maxDate;
    }
}
