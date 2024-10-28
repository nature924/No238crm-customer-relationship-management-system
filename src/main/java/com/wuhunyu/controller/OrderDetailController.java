package com.wuhunyu.controller;

import com.wuhunyu.base.BaseController;
import com.wuhunyu.pojo.CustomerOrder;
import com.wuhunyu.pojo.OrderDetail;
import com.wuhunyu.query.CustomerOrderQuery;
import com.wuhunyu.query.OrderDetailQuery;
import com.wuhunyu.service.CustomerOrderService;
import com.wuhunyu.service.OrderDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 订单详情控制层
 *
 * @author wuhunyu
 * @version 1.0
 * @date 2020-12-17 15:30
 */
@Controller
@RequestMapping("/orderDetail")
public class OrderDetailController extends BaseController {

    @Autowired
    private OrderDetailService orderDetailService;

    @Autowired
    private CustomerOrderService customerOrderService;

    /**
     * 根据订单id查询订单详情
     *
     * @param orderDetailQuery
     * @return
     */
    @GetMapping("/orderDetailList")
    @ResponseBody
    public Map<String, Object> findOrderDetailByOrderId(OrderDetailQuery orderDetailQuery) {
        return orderDetailService.findOrderDetailByOrderId(orderDetailQuery);
    }

    /**
     * 跳转到订单详情页
     * @param cusId
     * @param model
     * @return
     */
    @GetMapping("/toOrderDetailPage")
    public String toOrderDetailPage(@RequestParam("id") Integer id, Model model) {
        // 查询订单信息
        model.addAttribute("customer", customerOrderService.findCustomerOrderById(id));
        model.addAttribute("sum", orderDetailService.findSum(id));
        return "customerOrder/orderDetail";
    }

}
