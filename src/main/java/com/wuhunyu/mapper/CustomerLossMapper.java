package com.wuhunyu.mapper;

import com.wuhunyu.base.BaseMapper;
import com.wuhunyu.pojo.CustomerLoss;
import com.wuhunyu.query.CustomerLossQuery;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * 客户流失接口
 * @author wuhunyu
 */
@Repository("customerLossMapper")
@Mapper
public interface CustomerLossMapper extends BaseMapper<CustomerLoss,Integer> {

    /**
     * 查询流失的客户信息
     * @param customerLossQuery
     * @return
     */
    public List<CustomerLoss> findCustomerLoss(CustomerLossQuery customerLossQuery);

    /**
     * 修改客户流失状态为流失
     * @param CustomerLoss
     * @return
     */
    public Integer updateCustomerLossState(CustomerLoss CustomerLoss);

}