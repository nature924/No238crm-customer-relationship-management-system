package com.wuhunyu.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wuhunyu.base.BaseService;
import com.wuhunyu.mapper.CusDevPlanMapper;
import com.wuhunyu.mapper.SaleChanceMapper;
import com.wuhunyu.pojo.CusDevPlan;
import com.wuhunyu.query.CusDevPlanQuery;
import com.wuhunyu.utils.AssertUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Map;

/**
 * 客户开发计划业务层
 *
 * @author wuhunyu
 * @version 1.0
 * @date 2020-12-11 14:57
 */

@Service("cusDevPlanService")
public class CusDevPlanService extends BaseService<CusDevPlan, Integer> {

    @Autowired
    private CusDevPlanMapper cusDevPlanMapper;

    @Autowired
    private SaleChanceMapper saleChanceMapper;

    public Map<String, Object> querySaleChanceByParams(CusDevPlanQuery cusDevPlanQuery) {
        // 封装数据到map
        return queryByParamsForTable(cusDevPlanQuery);
    }

    /**
     * 新增计划项
     * @param cusDevPlan
     */
    @Transactional(propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
    public void addCusDevPlan(CusDevPlan cusDevPlan) {
        // 校验id
        AssertUtil.isTrue(cusDevPlan.getSaleChanceId() == null, "销售机会id不能为空");
        AssertUtil.isTrue(saleChanceMapper.selectByPrimaryKey(cusDevPlan.getSaleChanceId()) == null, "操作的数据不存在");
        // 校验计划向内容
        AssertUtil.isTrue(StringUtils.isBlank(cusDevPlan.getPlanItem()), "计划向内容不能为空");
        // 计划时间不能为空
        AssertUtil.isTrue(cusDevPlan.getPlanDate() == null, "计划时间不能为空");
        // 设置默认值
        cusDevPlan.setIsValid(1);
        cusDevPlan.setCreateDate(new Date());
        cusDevPlan.setUpdateDate(new Date());
        // 执行新增操作
        Integer result = cusDevPlanMapper.insertSelective(cusDevPlan);
        AssertUtil.isTrue(result != 1, "新增开发计划项失败");
    }

    /**
     * 修改计划项
     * @param cusDevPlan
     */
    @Transactional(propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
    public void updateCusDevPlan(CusDevPlan cusDevPlan) {
        // 校验id
        AssertUtil.isTrue(cusDevPlan.getId()==null,"计划项id不能为空");
        AssertUtil.isTrue(cusDevPlan.getSaleChanceId() == null, "销售机会id不能为空");
        AssertUtil.isTrue(saleChanceMapper.selectByPrimaryKey(cusDevPlan.getSaleChanceId()) == null, "操作的数据不存在");
        // 校验存在要修改的客户计划开发项
        CusDevPlan cusDevPlanByDataBase = cusDevPlanMapper.selectByPrimaryKey(cusDevPlan.getId());
        AssertUtil.isTrue(cusDevPlanByDataBase == null, "操作的数据不存在");
        // 校验计划向内容
        AssertUtil.isTrue(StringUtils.isBlank(cusDevPlan.getPlanItem()), "计划向内容不能为空");
        // 计划时间不能为空
        AssertUtil.isTrue(cusDevPlan.getPlanDate() == null, "计划时间不能为空");
        // 设置默认值
        cusDevPlan.setIsValid(1);
        cusDevPlan.setCreateDate(cusDevPlanByDataBase.getCreateDate());
        cusDevPlan.setUpdateDate(new Date());
        // 执行修改操作
        Integer result = cusDevPlanMapper.updateByPrimaryKeySelective(cusDevPlan);
        AssertUtil.isTrue(result != 1, "修改开发计划项失败");
    }

    /**
     * 批量删除客户计划项
     * @param ids
     */
    @Transactional(propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
    public void deleteCusDevPlan(Integer[] ids) {
        AssertUtil.isTrue(ids == null || ids.length==0, "计划项id不能为空");
        Integer result = cusDevPlanMapper.deleteBatch(ids);
        AssertUtil.isTrue(result < 1, "删除开发计划项失败");
    }

}
