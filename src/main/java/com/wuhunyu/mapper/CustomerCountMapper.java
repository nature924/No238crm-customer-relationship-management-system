package com.wuhunyu.mapper;

import com.wuhunyu.query.CustomerContributionQuery;
import com.wuhunyu.query.CustomerLossQuery;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Map;

/**
 * 客户信息统计接口
 *
 * @author wuhunyu
 * @version 1.0
 * @date 2021-02-07 21:17
 */
@Repository("customerCountMapper")
@Mapper
public interface CustomerCountMapper {

    /**
     * 统计客户贡献
     * @param customerContributionQuery
     * @return
     */
    public List<Map<String, Object>> countCustomerContribution(CustomerContributionQuery customerContributionQuery);

    /**
     * 查询统计客户贡献的记录数
     * @param customerContributionQuery
     * @return
     */
    public int countCustomerContributionForNum(CustomerContributionQuery customerContributionQuery);

    /**
     * 统计客户组成
     * @return
     */
    public List<Map<String, Object>> countCustomerMake();

    /**
     * 统计客户流失
     * @param customerLossQuery
     * @return
     */
    public List<Map<String, Object>> countCustomerLoss(CustomerLossQuery customerLossQuery);

}
