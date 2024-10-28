package com.wuhunyu.controller;

import com.wuhunyu.base.BaseController;
import com.wuhunyu.pojo.CustomerLoss;
import com.wuhunyu.query.CustomerLossQuery;
import com.wuhunyu.service.CustomerLossService;
import com.wuhunyu.utils.AssertUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import java.util.Map;

/**
 * 客户流失管理控制层
 *
 * @author wuhunyu
 * @version 1.0
 * @date 2020-12-17 18:22
 */
@Controller
@RequestMapping("/customerLoss")
public class CustomerLossController extends BaseController {

    @Autowired
    private CustomerLossService customerLossService;

    /**]
     * 查询所有客户流失记录
     * @param customerLossQuery
     * @return
     */
    @GetMapping("/customerLossList")
    @ResponseBody
    public Map<String, Object> findCustomerLoss(CustomerLossQuery customerLossQuery) {
        return customerLossService.findCustomerLoss(customerLossQuery);
    }

    /**
     * 跳转到客户流失首页
     * @return
     */
    @GetMapping("/customerLoss")
    public String customerLoss() {
        return "customerLoss/customerLoss";
    }

    /**
     * 跳转到客户流失信息管理页面
     * @param id
     * @param model
     * @return
     */
    @GetMapping("/customerLossData")
    public String customerLossData(@RequestParam("id") Integer id, Model model) {
        // 根据流失客户信息id查询对应的流失客户信息
        // 参数校验
        AssertUtil.isTrue(id == null, "客户流失信息id不能为空");
        CustomerLoss customerLoss = customerLossService.selectByPrimaryKey(id);
        AssertUtil.isTrue(customerLoss == null, "查询客户流失信息失败");
        model.addAttribute("customerLoss", customerLoss);
        return "customerLoss/customerLossData";
    }

}
