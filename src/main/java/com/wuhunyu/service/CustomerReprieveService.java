package com.wuhunyu.service;

import com.wuhunyu.base.BaseService;
import com.wuhunyu.mapper.CustomerLossMapper;
import com.wuhunyu.mapper.CustomerReprieveMapper;
import com.wuhunyu.pojo.CustomerLoss;
import com.wuhunyu.pojo.CustomerReprieve;
import com.wuhunyu.utils.AssertUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 客户流失详情业务层
 *
 * @author wuhunyu
 * @version 1.0
 * @date 2020-12-18 9:55
 */
@Service("customerReprieveService")
public class CustomerReprieveService extends BaseService<CustomerReprieve,Integer> {

    @Autowired
    private CustomerReprieveMapper customerReprieveMapper;

    @Autowired
    private CustomerLossMapper customerLossMapper;

    /**
     * 根据流失客户id查询流失客户详情信息
     * @param lossId
     * @return
     */
    public Map<String, Object> findCustomerReprieveByLossId(Integer lossId) {
        // 校验参数
        AssertUtil.isTrue(lossId == null, "客户流失id不能为空");
        List<CustomerReprieve> customerReprieves = customerReprieveMapper.findCustomerReprieveByLossId(lossId);
        // 校验查询结果
        AssertUtil.isTrue(customerReprieves == null, "查询客户流失信息失败");
        // 封装数据
        Map<String, Object> map = new HashMap<>(16);
        map.put("code", 0);
        map.put("msg", "查询客户流失信息失败");
        map.put("count", customerReprieves.size());
        map.put("data", customerReprieves);
        // 返回结果
        return map;
    }

    /**
     * 新增客户流失信息
     * @param customerReprieve
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void addCustomerReprieve(CustomerReprieve customerReprieve) {
        // 校验非空
        AssertUtil.isTrue(customerReprieve == null, "非法参数");
        // 参数校验
        System.out.println(customerReprieve);
        checkParam(customerReprieve);
        // 执行新增操作
        Integer result = customerReprieveMapper.insertSelective(customerReprieve);
        AssertUtil.isTrue(result != 1, "新增客户流失信息失败");
    }

    /**
     * 修改客户流失信息
     * @param customerReprieve
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void updateCustomerReprieve(CustomerReprieve customerReprieve) {
        // 校验非空
        AssertUtil.isTrue(customerReprieve == null, "非法参数");
        // 校验id非空
        AssertUtil.isTrue(customerReprieve.getId() == null, "客户流失信息详情id不能为空");
        // 参数校验
        checkParam(customerReprieve);
        // 执行更新操作
        Integer result = customerReprieveMapper.updateByPrimaryKeySelective(customerReprieve);
        AssertUtil.isTrue(result != 1, "更新客户流失信息失败");
    }

    /**
     * 批量删除客户流失信息
     * @param ids
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void deleteCustomerReprieveByIds(Integer[] ids) {
        // 校验非空
        AssertUtil.isTrue(ids == null || ids.length == 0, "客户流失信息id不能为空");
        // 执行删除操作
        Integer result = customerReprieveMapper.deleteCustomerReprieveByIds(ids);
        AssertUtil.isTrue(result != ids.length, "删除客户流失信息失败");
    }

    /**
     * 确认客户流失
     * @param customerLoss
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void updateCustomerLossState(CustomerLoss customerLoss) {
        // 校验非空
        AssertUtil.isTrue(customerLoss == null, "非法参数");
        // id
        AssertUtil.isTrue(customerLoss.getId() == null, "客户流失id不能为空");
        // 默认值
        // 最后确认流失时间
        customerLoss.setConfirmLossTime(new Date());
        // 流失状态
        customerLoss.setState(1);
        // 有效
        customerLoss.setIsValid(1);
        // 更新更新时间
        customerLoss.setUpdateDate(new Date());
        // 执行更新操作
        Integer result = customerLossMapper.updateCustomerLossState(customerLoss);
        AssertUtil.isTrue(result != 1, "确认客户流失失败");
    }

    /**
     * 校验客户流失信息添加和修改
     * @param customerReprieve
     */
    public void checkParam(CustomerReprieve customerReprieve) {
        // 校验客户流失id非空
        AssertUtil.isTrue(customerReprieve.getLossId() == null, "客户流失id不能为空");
        // 暂缓措施非空，且同一个流失客户id不能重复
        AssertUtil.isTrue(StringUtils.isBlank(customerReprieve.getMeasure()), "暂缓措施不能为空");
        CustomerReprieve customerReprieveByIdAndMeasure = customerReprieveMapper.findCustomerReprieveByIdAndMeasure(customerReprieve.getLossId(), customerReprieve.getMeasure());
        if (customerReprieve.getId() != null) {
            // 修改
            AssertUtil.isTrue(customerReprieveByIdAndMeasure != null, "未做任何修改或已存在该暂缓措施");
        } else {
            AssertUtil.isTrue(customerReprieveByIdAndMeasure != null, "暂缓措施已存在");
        }
        // 设置默认值
        // 有效状态
        customerReprieve.setIsValid(1);
        // 创建时间
        if (customerReprieve.getId() != null) {
            // 修改
            customerReprieve.setCreateDate(null);
        } else {
            // 新增
            customerReprieve.setCreateDate(new Date());
        }
        // 修改时间
        customerReprieve.setUpdateDate(new Date());
    }

}
