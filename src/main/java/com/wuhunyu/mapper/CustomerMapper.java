package com.wuhunyu.mapper;

import com.wuhunyu.base.BaseMapper;
import com.wuhunyu.pojo.Customer;
import com.wuhunyu.query.CustomerQuery;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * 客户信息接口
 * @author wuhunyu
 */
@Repository("customerMapper")
@Mapper
public interface CustomerMapper extends BaseMapper<Customer,Integer> {

    /**
     * 动态查询客户信息
     * @param customerQuery
     * @return
     */
    public List<Customer> findCustomers(CustomerQuery customerQuery);

    /**
     * 根据客户名查询
     * @param customerName
     * @return
     */
    public Customer findCustomerByName(@Param("customerName") String customerName);

    /**
     * 批量删除
     * @param ids
     * @return
     */
    public Integer deleteCustomers(@Param("ids") Integer[] ids);

    /**
     * 查询所有有效的客户级别
     * @return
     */
    public List<String> findLevels();

    /**
     * 查询全部流失的客户记录
     * @return
     */
    public List<Customer> findAllLoss();

    /**
     * 根据客户id修改客户状态
     * @param ids
     * @return
     */
    public Integer updateCustomerState(List<Integer> ids);

}