package com.wuhunyu.mapper;

import com.wuhunyu.base.BaseMapper;
import com.wuhunyu.pojo.CustomerServe;
import com.wuhunyu.query.CustomerServeQuery;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * 客户服务管理
 * @author wuhunyu
 */
@Repository("customerServeMapper")
@Mapper
public interface CustomerServeMapper extends BaseMapper<CustomerServe, Integer> {

    /**
     * 动态查询客户管理信息
     * @param customerServeQuery
     * @return
     */
    public List<CustomerServe> selectCustomerServes(CustomerServeQuery customerServeQuery);

    /**
     * 动态查询客户管理信息的总记录数
     * @param customerServeQuery
     * @return
     */
    public int selectCustomerServesForCount(CustomerServeQuery customerServeQuery);

    /**
     * 删除服务
     * @param id
     * @return
     */
    public int deleteCustomerServe(@Param("id") Integer id);



}