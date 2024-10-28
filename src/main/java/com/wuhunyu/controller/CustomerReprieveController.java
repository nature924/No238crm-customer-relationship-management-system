package com.wuhunyu.controller;

import com.wuhunyu.base.BaseController;
import com.wuhunyu.base.ResultInfo;
import com.wuhunyu.pojo.CustomerLoss;
import com.wuhunyu.pojo.CustomerReprieve;
import com.wuhunyu.service.CustomerReprieveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

/**
 * 客户信息流失详情
 *
 * @author wuhunyu
 * @version 1.0
 * @date 2020-12-18 10:10
 */
@Controller
@RequestMapping("/customerReprieve")
public class CustomerReprieveController extends BaseController {

    @Autowired
    private CustomerReprieveService customerReprieveService;

    @Autowired
    private ResultInfo resultInfo;

    /**
     * 根据流失客户id查询流失客户详情信息
     * @param lossId
     * @return
     */
    @GetMapping("/customerReprieveList")
    @ResponseBody
    public Map<String, Object> findCustomerReprieveByLossId(@RequestParam("lossId") Integer lossId) {
        return customerReprieveService.findCustomerReprieveByLossId(lossId);
    }

    /**
     * 添加客户流失信息
     * @param customerReprieve
     * @return
     */
    @PostMapping("addCustomerReprieve")
    @ResponseBody
    public ResultInfo addCustomerReprieve(CustomerReprieve customerReprieve) {
        customerReprieveService.addCustomerReprieve(customerReprieve);
        resultInfo.setAll(200, "添加客户流失信息成功", null);
        return resultInfo;
    }

    /**
     * 更新客户流失信息
     * @param customerReprieve
     * @return
     */
    @PostMapping("updateCustomerReprieve")
    @ResponseBody
    public ResultInfo updateCustomerReprieve(CustomerReprieve customerReprieve) {
        customerReprieveService.updateCustomerReprieve(customerReprieve);
        resultInfo.setAll(200, "更新客户流失信息成功", null);
        return resultInfo;
    }

    /**
     * 删除客户流失信息
     * @param ids
     * @return
     */
    @PostMapping("deleteCustomerReprieve")
    @ResponseBody
    public ResultInfo deleteCustomerReprieve(@RequestParam("ids") Integer[] ids) {
        customerReprieveService.deleteCustomerReprieveByIds(ids);
        resultInfo.setAll(200, "删除客户流失信息成功", null);
        return resultInfo;
    }

    /**
     * 确认客户流失
     * @param customerLoss
     * @return
     */
    @PostMapping("updateCustomerLossState")
    @ResponseBody
    public ResultInfo updateCustomerLossState(CustomerLoss customerLoss) {
        customerReprieveService.updateCustomerLossState(customerLoss);
        resultInfo.setAll(200, "确认客户流失成功", null);
        return resultInfo;
    }

}
