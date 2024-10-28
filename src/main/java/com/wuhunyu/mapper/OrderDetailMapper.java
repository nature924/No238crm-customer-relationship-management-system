package com.wuhunyu.mapper;

import com.wuhunyu.base.BaseMapper;
import com.wuhunyu.pojo.OrderDetail;
import com.wuhunyu.query.OrderDetailQuery;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * 客户订单详情
 * @author wuhunyu
 */
@Repository("orderDetailMapper")
@Mapper
public interface OrderDetailMapper extends BaseMapper<OrderDetail,Integer> {

    /**
     * 根据订单id查询订单详情
     * @param orderDetailQuery
     * @return
     */
    public List<OrderDetail> findOrderDetailByOrderId(OrderDetailQuery orderDetailQuery);

    /**
     * 指定id计算总金额
     * @param id
     * @return
     */
    public Float findSum(@Param("id") Integer id);

}