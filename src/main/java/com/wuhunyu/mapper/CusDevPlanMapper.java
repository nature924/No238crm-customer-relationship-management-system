package com.wuhunyu.mapper;

import com.wuhunyu.base.BaseMapper;
import com.wuhunyu.pojo.CusDevPlan;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * 客户开发计划接口
 * @author wuhunyu
 */
@Repository("cusDevPlanMapper")
@Mapper
public interface CusDevPlanMapper extends BaseMapper<CusDevPlan, Integer> {

}