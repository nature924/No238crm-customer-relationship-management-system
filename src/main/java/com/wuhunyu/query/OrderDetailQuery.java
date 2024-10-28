package com.wuhunyu.query;

import com.wuhunyu.base.BaseQuery;

/**
 * 客户订单详情查询类
 *
 * @author wuhunyu
 * @version 1.0
 * @date 2020-12-17 15:18
 */
public class OrderDetailQuery extends BaseQuery {

    private Integer orderId;

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }
}
