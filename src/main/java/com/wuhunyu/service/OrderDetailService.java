package com.wuhunyu.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wuhunyu.base.BaseService;
import com.wuhunyu.mapper.OrderDetailMapper;
import com.wuhunyu.pojo.Customer;
import com.wuhunyu.pojo.OrderDetail;
import com.wuhunyu.query.OrderDetailQuery;
import com.wuhunyu.utils.AssertUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 客户订单详情业务层
 *
 * @author wuhunyu
 * @version 1.0
 * @date 2020-12-17 15:15
 */
@Service("orderDetailService")
public class OrderDetailService extends BaseService<OrderDetail, Integer> {

    @Autowired
    private OrderDetailMapper orderDetailMapper;

    /**
     * 根据订单id查询订单详情
     * @param orderDetailQuery
     * @return
     */
    public Map<String, Object> findOrderDetailByOrderId(OrderDetailQuery orderDetailQuery) {
        // 校验参数
        AssertUtil.isTrue(orderDetailQuery == null, "获取订单详情失败");
        // 校验orderId
        AssertUtil.isTrue(orderDetailQuery.getOrderId() == null, "id不能为空");
        // 执行查询操作
        List<OrderDetail> details = orderDetailMapper.findOrderDetailByOrderId(orderDetailQuery);
        AssertUtil.isTrue(details == null || details.size() == 0, "获取订单详情失败");
        // 封装数据
        Map<String,Object> result = new HashMap<>(16);
        PageHelper.startPage(orderDetailQuery.getPage(), orderDetailQuery.getLimit());
        PageInfo<OrderDetail> pageInfo = new PageInfo<>(orderDetailMapper.findOrderDetailByOrderId(orderDetailQuery));
        result.put("count",pageInfo.getTotal());
        result.put("data",pageInfo.getList());
        result.put("code", 0);
        result.put("msg", "没有查询到任何符合条件的数据");
        return result;
    }

    /**
     * 指定订单id返回总金额
     * @param id
     * @return
     */
    public Float findSum(Integer id) {
        // 校验参数
        AssertUtil.isTrue(id == null, "id不能为空");
        Float sum = orderDetailMapper.findSum(id);
        AssertUtil.isTrue(sum == null, "查询总金额失败");
        return sum;
    }

}
