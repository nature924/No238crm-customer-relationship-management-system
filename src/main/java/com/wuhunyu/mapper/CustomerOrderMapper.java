package com.wuhunyu.mapper;

import com.wuhunyu.base.BaseMapper;
import com.wuhunyu.pojo.CustomerOrder;
import com.wuhunyu.query.CustomerOrderQuery;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * 客户订单接口
 * @author wuhunyu
 */
@Repository("customerOrderMapper")
@Mapper
public interface CustomerOrderMapper extends BaseMapper<CustomerOrder,Integer> {

    /**
     * 根据客户id查询客户订单
     * @param customerOrderQuery
     * @return
     */
    public List<CustomerOrder> findCustomerOrderByCustomerId(CustomerOrderQuery customerOrderQuery);

    /**
     * 根据客户id查询客户的最后订单时间
     * @param customerId
     * @return
     */
    public Date findLastOrderTimeByCustomerId(@Param("customerId") Integer customerId);

}