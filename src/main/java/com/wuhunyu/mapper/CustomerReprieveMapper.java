package com.wuhunyu.mapper;

import com.wuhunyu.base.BaseMapper;
import com.wuhunyu.pojo.CustomerReprieve;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * 客户流失详情接口
 * @author wuhunyu
 */
@Repository("customerReprieveMapper")
@Mapper
public interface CustomerReprieveMapper extends BaseMapper<CustomerReprieve,Integer> {

    /**
     * 根据流失id查询流失信息详情
     * @param lossId
     * @return
     */
    public List<CustomerReprieve> findCustomerReprieveByLossId(@Param("lossId") Integer lossId);

    /**
     * 根据流失客户id和暂缓措施查询流失信息
     * @param lossId
     * @param measure
     * @return
     */
    public CustomerReprieve findCustomerReprieveByIdAndMeasure(@Param("lossId") Integer lossId,@Param("measure") String measure);

    /**
     * 根据流失信息id批量删除
     * @param ids
     * @return
     */
    public Integer deleteCustomerReprieveByIds(@Param("ids") Integer[] ids);

}