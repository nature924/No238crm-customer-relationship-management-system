package com.wuhunyu.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wuhunyu.base.BaseService;
import com.wuhunyu.mapper.CustomerLossMapper;
import com.wuhunyu.mapper.CustomerMapper;
import com.wuhunyu.mapper.CustomerOrderMapper;
import com.wuhunyu.pojo.Customer;
import com.wuhunyu.pojo.CustomerLoss;
import com.wuhunyu.query.CustomerQuery;
import com.wuhunyu.utils.AssertUtil;
import com.wuhunyu.utils.PhoneUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * 客户信息管理业务层
 *
 * @author wuhunyu
 * @version 1.0
 * @date 2020-12-16 20:18
 */
@Service("customerService")
public class CustomerService extends BaseService<Customer, Integer> {

    @Autowired
    private CustomerMapper customerMapper;

    @Autowired
    private CustomerLossMapper customerLossMapper;

    @Autowired
    private CustomerOrderMapper customerOrderMapper;

    /**
     * 动态查询客户信息
     * @param customerQuery
     * @return
     */
    public Map<String, Object> findCustomers(CustomerQuery customerQuery) {
        // 执行查询操作
        List<Customer> customers = customerMapper.findCustomers(customerQuery);
        AssertUtil.isTrue(customers.size() == 0, "查询的客户信息不存在");
        Map<String,Object> result = new HashMap<>(16);
        PageHelper.startPage(customerQuery.getPage(), customerQuery.getLimit());
        PageInfo<Customer> pageInfo = new PageInfo<Customer>(customerMapper.findCustomers(customerQuery));
        result.put("count",pageInfo.getTotal());
        result.put("data",pageInfo.getList());
        result.put("code", 0);
        result.put("msg", "没有查询到任何符合条件的数据");
        // 封装数据结果集
        return result;
    }

    /**
     * 添加客户信息
     * @param customer
     */
    @Transactional(propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
    public void addCustomer(Customer customer) {
        // 参数校验
        // customer对象非空
        AssertUtil.isTrue(customer == null, "提交参数不合法");
        checkParams(customer);
        // 执行新增操作
        AssertUtil.isTrue(customerMapper.insertSelective(customer) != 1, "新增客户信息失败");
    }

    /**
     * 修改客户信息
     * @param customer
     */
    @Transactional(propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
    public void updateCustomer(Customer customer) {
        // 参数校验
        // customer对象非空
        AssertUtil.isTrue(customer == null, "提交参数不合法");
        // 校验id
        AssertUtil.isTrue(customer.getId() == null, "客户id不能为空");
        // 校验id是否存在
        AssertUtil.isTrue(customerMapper.selectByPrimaryKey(customer.getId()) == null, "客户不存在");
        checkParams(customer);
        // 执行更新操作
        AssertUtil.isTrue(customerMapper.updateByPrimaryKeySelective(customer) != 1, "更新客户信息失败");
    }

    /**
     * 新增和修改客户信息参数校验
     * @param customer
     */
    public void checkParams(Customer customer) {

        // 法人不为空
        AssertUtil.isTrue(StringUtils.isBlank(customer.getFr()), "法人不能为空");
        // 客户名称不能为空，且不能重复
        AssertUtil.isTrue(StringUtils.isBlank(customer.getName()), "客户名称不能为空");
        if (customer.getId() != null) {
            // 修改
            Customer customerByName = customerMapper.findCustomerByName(customer.getName());
            AssertUtil.isTrue(customerByName != null && !customerByName.getId().equals(customer.getId()), "客户名称已被占用");
        } else {
            // 新增
            AssertUtil.isTrue(customerMapper.findCustomerByName(customer.getName()) != null, "客户名称已被占用");
        }
        // 手机号码校验
        AssertUtil.isTrue(!PhoneUtil.isMobile(customer.getPhone()), "手机号码格式错误");
        // 设置默认值
        // 客户编号生成
        customer.setKhno("KH" + System.currentTimeMillis());
        // 有效值
        customer.setIsValid(1);
        // 状态值
        customer.setState(0);
        // 创建时间
        if (customer.getId() != null) {
            customer.setCreateDate(null);
        } else {
            customer.setCreateDate(new Date());
        }
        // 更新时间
        customer.setUpdateDate(new Date());
    }

    /**
     * 批量删除客户信息
     * @param ids
     */
    @Transactional(propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
    public void deleteCustomerByIds(Integer[] ids) {
        // 参数校验
        AssertUtil.isTrue(ids == null || ids.length == 0, "id不能为空");
        // 执行删除操作
        AssertUtil.isTrue(customerMapper.deleteCustomers(ids) != ids.length, "删除客户信息失败");
    }

    /**
     * 查询所有客户级别
     * @return
     */
    public List<String> findLevels() {
        List<String> levels = customerMapper.findLevels();
        AssertUtil.isTrue(levels == null || levels.size() == 0, "获取客户级别失败");
        return levels;
    }

    @Transactional(propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
    public void updateCustomerList() {
        // 查询流失客户
        List<Customer> loss = customerMapper.findAllLoss();
        // 定义ids数组，方便批量更新客户信息状态
        ArrayList<Integer> ids = new ArrayList<>();
        // 将流失客户批量插入到客户流失表中
        if (loss != null && loss.size() > 0) {
            ArrayList<CustomerLoss> list = new ArrayList<>();
            // 适配数据插入到客户流失表中
            loss.forEach(customer -> {
                // 流失客户对象
                CustomerLoss customerLoss = new CustomerLoss();
                // 客户编号
                customerLoss.setCusNo(customer.getKhno());
                // 创建事件，默认未当前时间
                customerLoss.setCreateDate(new Date());
                // 更新时间
                customerLoss.setUpdateDate(new Date());
                // 客户经理
                customerLoss.setCusManager(customer.getCusManager());
                // 客户名称
                customerLoss.setCusName(customer.getName());
                // 是否有效
                customerLoss.setIsValid(1);
                // 客户流失状态   1-确认流失  0-暂缓流失
                customerLoss.setState(0);
                // 最后下单时间
                Date lastOrderTime = customerOrderMapper.findLastOrderTimeByCustomerId(customer.getId());
                if (lastOrderTime != null) {
                    // 有最后下单时间
                    customerLoss.setLastOrderTime(lastOrderTime);
                } else {
                    customerLoss.setLastOrderTime(null);
                }
                // 将流失对象添加到集合中
                list.add(customerLoss);
                ids.add(customer.getId());
            });
            // 执行新增操作
            AssertUtil.isTrue(customerLossMapper.insertBatch(list) != loss.size(), "添加流失客户信息失败");
            // 修改客户信息的状态
            AssertUtil.isTrue(customerMapper.updateCustomerState(ids) != loss.size(), "更新客户信息失败");
        }
    }

}
