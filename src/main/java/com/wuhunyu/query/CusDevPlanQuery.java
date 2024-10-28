package com.wuhunyu.query;

import com.wuhunyu.base.BaseQuery;
import com.wuhunyu.pojo.CusDevPlan;

/**
 * 客户开发计划查询条件封装
 *
 * @author wuhunyu
 * @version 1.0
 * @date 2020-12-11 15:03
 */
public class CusDevPlanQuery extends BaseQuery {

    /**
     * 销售机会id
     */
    private String saleChanceId;

    public String getSaleChanceId() {
        return saleChanceId;
    }

    public void setSaleChanceId(String saleChanceId) {
        this.saleChanceId = saleChanceId;
    }
}
