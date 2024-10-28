package com.wuhunyu.controller;

import com.wuhunyu.base.BaseController;
import com.wuhunyu.base.ResultInfo;
import com.wuhunyu.pojo.Customer;
import com.wuhunyu.query.CustomerQuery;
import com.wuhunyu.service.CustomerService;
import com.wuhunyu.utils.AssertUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

/**
 * 客户信息管理控制层
 *
 * @author wuhunyu
 * @version 1.0
 * @date 2020-12-16 20:19
 */
@Controller
@RequestMapping("/customer")
public class CustomerController extends BaseController {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private ResultInfo resultInfo;

    /**
     * 跳转到客户信息管理首页
     * @return
     */
    @GetMapping("/customer")
    public String customer() {
        return "customer/customer";
    }

    /**
     * 跳转到新增或修改页面
     * @param id
     * @param model
     * @return
     */
    @GetMapping("/toAddAndUpdatePage")
    public String toAddAndUpdatePage(@Nullable Integer id, Model model) {
        if (id != null) {
            // 修改
            // 获取客户信息
            Customer customer = customerService.selectByPrimaryKey(id);
            AssertUtil.isTrue(customer == null, "获取客户信息失败");
            model.addAttribute("customer", customer);
        }
        return "customer/addAndUpdate";
    }

    /**
     * 获取客户信息列表
     * @param customerQuery
     * @return
     */
    @GetMapping("/customerList")
    @ResponseBody
    public Map<String, Object> findCustomers(@Nullable CustomerQuery customerQuery) {
        return customerService.findCustomers(customerQuery);
    }

    /**
     * 新增客户信息
     * @param customer
     * @return
     */
    @PostMapping("/addCustomer")
    @ResponseBody
    public ResultInfo addCustomer(Customer customer) {
        customerService.addCustomer(customer);
        resultInfo.setAll(200, "添加客户信息成功", null);
        return resultInfo;
    }

    /**
     * 修改客户信息
     * @param customer
     * @return
     */
    @PostMapping("/updateCustomer")
    @ResponseBody
    public ResultInfo updateCustomer(Customer customer) {
        customerService.updateCustomer(customer);
        resultInfo.setAll(200, "更新客户信息成功", null);
        return resultInfo;
    }

    /**
     * 批量删除客户信息
     * @param ids
     * @return
     */
    @PostMapping("/deleteCustomer")
    @ResponseBody
    public ResultInfo deleteCustomer(@RequestParam("ids") Integer[] ids) {
        customerService.deleteCustomerByIds(ids);
        resultInfo.setAll(200, "删除客户信息成功", null);
        return resultInfo;
    }

    /**
     * 查询所有客户级别
     * @return
     */
    @GetMapping("/getLevels")
    @ResponseBody
    public List<String> findLevels() {
        return customerService.findLevels();
    }

    /**
     * 跳转到客户订单详情页面
     * @param customerId
     * @return
     */
    @GetMapping("/toCustomerOrderPage")
    public String toCustomerOrderPage(@RequestParam("customerId") Integer customerId,Model model) {
        // 校验参数
        AssertUtil.isTrue(customerId == null, "客户id不能为空");
        // 封装客户信息
        Customer customer = customerService.selectByPrimaryKey(customerId);
        AssertUtil.isTrue(customer == null, "查询客户信息失败");
        model.addAttribute("customer", customer);
        return "customerOrder/customerOrder";
    }

}
