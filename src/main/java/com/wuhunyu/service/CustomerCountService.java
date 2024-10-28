package com.wuhunyu.service;

import com.wuhunyu.mapper.CustomerCountMapper;
import com.wuhunyu.query.CustomerContributionQuery;
import com.wuhunyu.query.CustomerLossQuery;
import com.wuhunyu.utils.AssertUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 客户统计业务层
 *
 * @author wuhunyu
 * @version 1.0
 * @date 2021-02-07 22:03
 */
@Service("customerCountService")
public class CustomerCountService {

    @Autowired
    private CustomerCountMapper customerCountMapper;

    /**
     * 查询客户贡献
     *
     * @param customerContributionQuery
     * @return
     */
    public Map<String, Object> countCustomerContribution(CustomerContributionQuery customerContributionQuery) {
        System.out.println("customerContributionQuery" + customerContributionQuery);
        // 校验参数
        AssertUtil.isTrue(customerContributionQuery == null, "客户贡献分析查询参数不能为空");
        // 修改分页参数
        customerContributionQuery.setPage((customerContributionQuery.getPage() - 1) * customerContributionQuery.getLimit());
        // 执行查询操作
        List<Map<String, Object>> customerCounts = customerCountMapper.countCustomerContribution(customerContributionQuery);
        AssertUtil.isTrue(customerCounts == null || customerCounts.size() == 0, "查询客户贡献数据为空");
        // 查询统计记录总数
        int count = customerCountMapper.countCustomerContributionForNum(customerContributionQuery);
        AssertUtil.isTrue(count < 0 || count < customerCounts.size(), "查询客户贡献总记录数错误");
        // 封装数据
        Map<String, Object> result = new HashMap<>(16);
        result.put("count", 2);
        result.put("data", customerCounts);
        result.put("code", 0);
        result.put("msg", "没有查询到任何符合条件的数据");
        return result;
    }

    /**
     * 统计客户组成
     * @return
     */
    public Map<String, Object> countCustomerMake() {
        // 执行查询
        List<Map<String, Object>> maps = customerCountMapper.countCustomerMake();
        AssertUtil.isTrue(maps == null || maps.size() == 0, "查询客户组成数据为空");
        List<String> customerName = new ArrayList<>();
        List<Integer> customerValue = new ArrayList<>();
        maps.stream().forEach(map -> {
            customerName.add(map.get("level").toString());
            customerValue.add(Integer.parseInt(map.get("total").toString()));
        });
        // 封装数据
        Map<String, Object> map = new HashMap<>(16);
        map.put("customerName", customerName);
        map.put("customerValue", customerValue);
        return map;
    }

    public List<Map<String, Object>> countCustomerLoss(CustomerLossQuery customerLossQuery) {
        // 校验参数
        AssertUtil.isTrue(customerLossQuery == null, "查询客户流失分析参数不能为空");
        // 执行查询
        List<Map<String, Object>> maps = customerCountMapper.countCustomerLoss(customerLossQuery);
        // 校验结果集
        AssertUtil.isTrue(maps == null || maps.size() == 0, "查询客户流失信息为空");
        // 封装结果集
        return maps;
    }

}
