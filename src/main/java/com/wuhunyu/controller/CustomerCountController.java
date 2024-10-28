package com.wuhunyu.controller;

import com.wuhunyu.base.BaseController;
import com.wuhunyu.base.ResultInfo;
import com.wuhunyu.query.CustomerContributionQuery;
import com.wuhunyu.query.CustomerLossQuery;
import com.wuhunyu.service.CustomerCountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import java.util.Map;

/**
 * 客户贡献统计控制器
 *
 * @author wuhunyu
 * @version 1.0
 * @date 2021-02-07 22:15
 */
@Controller
@RequestMapping("/customerCount")
public class CustomerCountController extends BaseController {

    @Autowired
    private ResultInfo resultInfo;

    @Autowired
    private CustomerCountService customerCountService;

    @GetMapping("/toCustomerContributionPage")
    public String toCustomerContributionPage() {
        return "customerCount/customerCount";
    }

    @GetMapping("/countCustomerContribution")
    @ResponseBody
    public Map<String, Object> countCustomerContribution(CustomerContributionQuery customerContributionQuery) {
        return customerCountService.countCustomerContribution(customerContributionQuery);
    }

    @GetMapping("/toCountCustomerMakePage")
    public String toCountCustomerMakePage() {
        return "customerCount/countCustomerMake";
    }

    @GetMapping("/countCustomerMake")
    @ResponseBody
    public ResultInfo countCustomerMake() {
        resultInfo.setAll(200, "查询客户组成信息成功", customerCountService.countCustomerMake());
        return resultInfo;
    }

    @GetMapping("/toCountCustomerLossPage")
    public String toCountCustomerLossPage() {
        return "customerCount/customerCountLoss";
    }

    @GetMapping("/countCustomerLoss")
    @ResponseBody
    public ResultInfo countCustomerLoss(CustomerLossQuery customerLossQuery) {
        resultInfo.setAll(200, "查询客户流失信息成功", customerCountService.countCustomerLoss(customerLossQuery));
        return resultInfo;
    }

}
