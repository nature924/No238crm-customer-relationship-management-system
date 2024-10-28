package com.wuhunyu.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wuhunyu.base.BaseService;
import com.wuhunyu.enums.DevResult;
import com.wuhunyu.enums.StateStatus;
import com.wuhunyu.mapper.SaleChanceMapper;
import com.wuhunyu.mapper.UserMapper;
import com.wuhunyu.pojo.SaleChance;
import com.wuhunyu.query.SaleChanceQuery;
import com.wuhunyu.utils.AssertUtil;
import com.wuhunyu.utils.PhoneUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 销售机会业务层
 *
 * @author wuhunyu
 * @version 1.0
 * @date 2020-12-09 16:31
 */
@Service("saleChanceService")
public class SaleChanceService extends BaseService<SaleChance, Integer> {

    @Autowired
    private SaleChanceMapper saleChanceMapper;

    @Autowired
    private UserMapper userMapper;

    public Map<String, Object> querySaleChanceByParams(SaleChanceQuery saleChanceQuery) {
        // 为layui数据动态显示适配数据
        return queryByParamsForTable(saleChanceQuery);
    }

    /**
     * 新增销售机会记录
     *
     * @param saleChance
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void addSaleChance(SaleChance saleChance) {
        // 必输字段校验
        AssertUtil.isTrue(StringUtils.isBlank(saleChance.getCustomerName()), "客户名称不能为空");
        AssertUtil.isTrue(StringUtils.isBlank(saleChance.getLinkMan()), "联系人不能为空");
        AssertUtil.isTrue(StringUtils.isBlank(saleChance.getLinkPhone()), "联系号码不能为空");
        AssertUtil.isTrue(!PhoneUtil.isMobile(saleChance.getLinkPhone()), "联系号码格式不正确");
        // 默认字段赋值
        // 是否有效,新增时默认有效
        saleChance.setIsValid(1);
        // 创建时间默认为当前系统时间
        saleChance.setCreateDate(new Date());
        // 修改时间默认为当前系统时间
        saleChance.setUpdateDate(new Date());
        // 指派人
        if (StringUtils.isBlank(saleChance.getAssignMan())) {
            // 指派人未输入值
            // 分配状态为0
            saleChance.setState(StateStatus.UNSTATE.getType());
            // 指派时间为空
            saleChance.setAssignTime(null);
            // 开发状态为未开发
            saleChance.setDevResult(DevResult.UNDEV.getStatus());
        } else {
            // 指派人有值
            // 分配状态未1
            saleChance.setState(StateStatus.STATED.getType());
            // 指派时间为当前系统时间
            saleChance.setAssignTime(new Date());
            // 开发状态为开发中
            saleChance.setDevResult(DevResult.DEVING.getStatus());
        }
        // 执行新增操作
        Integer resultRow = saleChanceMapper.insertSelective(saleChance);
        AssertUtil.isTrue(resultRow != 1, "新增销售机会记录失败");
    }

    /**
     * 修改销售机会记录
     *
     * @param saleChance
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void updateSaleChance(SaleChance saleChance) {
        // 校验id
        AssertUtil.isTrue(saleChance.getId() == null, "id不能为空");
        // 校验id是否存在
        SaleChance saleChanceByDataBase = saleChanceMapper.selectByPrimaryKey(saleChance.getId());
        AssertUtil.isTrue(saleChanceByDataBase == null, "修改的销售机会记录不存在");
        // 必输字段校验
        AssertUtil.isTrue(StringUtils.isBlank(saleChance.getCustomerName()), "客户名称不能为空");
        AssertUtil.isTrue(StringUtils.isBlank(saleChance.getLinkMan()), "联系人不能为空");
        AssertUtil.isTrue(StringUtils.isBlank(saleChance.getLinkPhone()), "联系号码不能为空");
        AssertUtil.isTrue(!PhoneUtil.isMobile(saleChance.getLinkPhone()), "联系号码格式不正确");
        // 修改时间默认为当前系统时间
        saleChance.setUpdateDate(new Date());
        // 指派人
        // 原始数据没有设置指派人
        if (StringUtils.isBlank(saleChanceByDataBase.getAssignMan())) {
            // 修改数据有指派人信息
            if (saleChance.getAssignMan() != null) {
                // 分配状态未1
                saleChance.setState(StateStatus.STATED.getType());
                // 指派时间为当前系统时间
                saleChance.setAssignTime(new Date());
                // 开发状态为开发中
                saleChance.setDevResult(DevResult.DEVING.getStatus());
            }
        } else {
            // 原始数据有设置指派人
            if (saleChance.getAssignMan() == null || "".equals(saleChance.getAssignMan())) {
                // 修改数据没有指派人
                // 指派时间设置为null
                saleChance.setAssignTime(null);
                // 分配状态设置为未分配
                saleChance.setState(StateStatus.UNSTATE.getType());
                // 开发状态为未开发
                saleChance.setDevResult(DevResult.UNDEV.getStatus());
            } else {
                // 修改数据也有指派人
                if (!saleChance.getAssignMan().equals(saleChanceByDataBase.getAssignMan())) {
                    // 判断原始指派人和修改的指派人是否是同一个人
                    // 不一致则修改指派时间
                    saleChance.setAssignTime(new Date());
                } else {
                    // 设置指派时间为原始数据时间
                    saleChance.setAssignTime(saleChanceByDataBase.getAssignTime());
                }
            }
        }
        // 执行修改操作
        AssertUtil.isTrue(saleChanceMapper.updateByPrimaryKeySelective(saleChance) != 1, "修改销售机会记录失败");
    }

    /**
     * 获取指派人信息
     *
     * @return
     */
    public List<Map<String, Object>> getAssignMan() {
        return userMapper.getAssignMan();
    }

    /**
     * 批量删除销售机会记录
     *
     * @param ids
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void deleteSaleChance(Integer[] ids) {
        AssertUtil.isTrue(ids == null || ids.length == 0, "未选中任何需要删除的记录");
        AssertUtil.isTrue(saleChanceMapper.deleteSaleChance(ids) < 1, "删除失败");
    }

    /**
     * 修改开发状态
     *
     * @param devResult
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void updateDevResult(Integer id, String devResult) {
        AssertUtil.isTrue(id == null, "销售机会id不能为空");
        AssertUtil.isTrue(StringUtils.isBlank(devResult), "开发状态不能为空");
        AssertUtil.isTrue(saleChanceMapper.updateDevResult(id, devResult) != 1, "修改开发状态失败");
    }

}
