package com.wuhunyu.controller;

import com.wuhunyu.base.BaseController;
import com.wuhunyu.query.CustomerOrderQuery;
import com.wuhunyu.service.CustomerOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import java.util.Map;

/**
 * 客户订单控制层
 *
 * @author wuhunyu
 * @version 1.0
 * @date 2020-12-17 11:58
 */
@Controller
@RequestMapping("/customerOrder")
public class CustomerOrderController extends BaseController {

    @Autowired
    private CustomerOrderService customerOrderService;

    /**
     * 查询客户的账单信息
     * @param customerOrderQuery
     * @return
     */
    @GetMapping("/customerOrderList")
    @ResponseBody
    public Map<String, Object> findCustomerOrders(CustomerOrderQuery customerOrderQuery) {
        return customerOrderService.findCustomerOrderByCustomerId(customerOrderQuery);
    }

}
