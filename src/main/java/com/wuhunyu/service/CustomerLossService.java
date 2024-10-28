package com.wuhunyu.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wuhunyu.base.BaseService;
import com.wuhunyu.mapper.CustomerLossMapper;
import com.wuhunyu.pojo.CustomerLoss;
import com.wuhunyu.pojo.CustomerOrder;
import com.wuhunyu.query.CustomerLossQuery;
import com.wuhunyu.utils.AssertUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.Map;

/**
 * 客户流失管理业务层
 *
 * @author wuhunyu
 * @version 1.0
 * @date 2020-12-17 18:21
 */
@Service("customerLossService")
public class CustomerLossService extends BaseService<CustomerLoss,Integer> {

    @Autowired
    private CustomerLossMapper customerLossMapper;

    /**
     * 查询所有流失的客户信息
     * @param customerLossQuery
     * @return
     */
    public Map<String, Object> findCustomerLoss(CustomerLossQuery customerLossQuery) {
        // 参数校验
        AssertUtil.isTrue(customerLossQuery == null, "查询客户流失信息失败");
        // 封装查询结果
        Map<String,Object> result = new HashMap<>(16);
        PageHelper.startPage(customerLossQuery.getPage(), customerLossQuery.getLimit());
        PageInfo<CustomerLoss> pageInfo = new PageInfo<>(customerLossMapper.findCustomerLoss(customerLossQuery));
        result.put("count",pageInfo.getTotal());
        result.put("data",pageInfo.getList());
        result.put("code", 0);
        result.put("msg", "没有查询到任何符合条件的数据");
        return result;
    }

}
