package com.wuhunyu.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wuhunyu.base.BaseService;
import com.wuhunyu.mapper.CustomerOrderMapper;
import com.wuhunyu.pojo.Customer;
import com.wuhunyu.pojo.CustomerOrder;
import com.wuhunyu.query.CustomerOrderQuery;
import com.wuhunyu.utils.AssertUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.Map;

/**
 * 客户订单业务层
 *
 * @author wuhunyu
 * @version 1.0
 * @date 2020-12-17 11:52
 */
@Service("customerOrderService")
public class CustomerOrderService extends BaseService<CustomerOrder, Integer> {

    @Autowired
    private CustomerOrderMapper customerOrderMapper;

    /**
     * 查询客户信息
     * @param customerOrderQuery
     * @return
     */
    public Map<String, Object> findCustomerOrderByCustomerId(CustomerOrderQuery customerOrderQuery) {
        // 校验参数
        AssertUtil.isTrue(customerOrderQuery == null, "查询客户信息失败");
        AssertUtil.isTrue(customerOrderQuery.getCusId() == null, "客户id不能为空");
        // 封装查询结果
        Map<String,Object> result = new HashMap<>(16);
        PageHelper.startPage(customerOrderQuery.getPage(), customerOrderQuery.getLimit());
        PageInfo<CustomerOrder> pageInfo = new PageInfo<>(customerOrderMapper.findCustomerOrderByCustomerId(customerOrderQuery));
        result.put("count",pageInfo.getTotal());
        result.put("data",pageInfo.getList());
        result.put("code", 0);
        result.put("msg", "没有查询到任何符合条件的数据");
        return result;
    }

    /**
     * 根据id查询订单信息
     * @param id
     * @return
     */
    public CustomerOrder findCustomerOrderById(Integer id) {
        // 校验id
        AssertUtil.isTrue(id == null, "id不能为空");
        // 执行查询操作
        CustomerOrder customerOrder = customerOrderMapper.selectByPrimaryKey(id);
        // 校验查询结果
        AssertUtil.isTrue(customerOrder == null, "查询订单信息失败");
        return customerOrder;
    }

}
