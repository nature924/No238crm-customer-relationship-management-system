package com.wuhunyu.mapper;

import com.wuhunyu.base.BaseMapper;
import com.wuhunyu.pojo.SaleChance;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * 销售机会接口
 * @author wuhunyu
 */
@Repository("saleChanceMapper")
@Mapper
public interface SaleChanceMapper extends BaseMapper<SaleChance,Integer> {

    /**
     * 删除销售机会记录
     * @param ids
     * @return
     */
    public Integer deleteSaleChance(Integer[] ids);

    /**
     * 修改开发状态
     * @param id
     * @param devResult
     * @return
     */
    public Integer updateDevResult(@Param("id") Integer id, @Param("devResult") String devResult);

}