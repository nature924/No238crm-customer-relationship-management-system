package com.wuhunyu.query;

import com.wuhunyu.base.BaseQuery;

/**
 * 客户订单查询类
 *
 * @author wuhunyu
 * @version 1.0
 * @date 2020-12-17 14:19
 */
public class CustomerOrderQuery extends BaseQuery {

    private Integer cusId;

    public Integer getCusId() {
        return cusId;
    }

    public void setCusId(Integer cusId) {
        this.cusId = cusId;
    }
}
