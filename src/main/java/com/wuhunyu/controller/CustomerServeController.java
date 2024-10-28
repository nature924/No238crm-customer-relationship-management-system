package com.wuhunyu.controller;

import com.wuhunyu.base.BaseController;
import com.wuhunyu.base.ResultInfo;
import com.wuhunyu.pojo.CustomerServe;
import com.wuhunyu.query.CustomerServeQuery;
import com.wuhunyu.service.CustomerServeService;
import com.wuhunyu.utils.AssertUtil;
import com.wuhunyu.utils.CookieUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * 客户管理控制器
 *
 * @author wuhunyu
 * @version 1.0
 * @date 2021-02-05 11:13
 */
@Controller
@RequestMapping("/customerServe")
public class CustomerServeController extends BaseController {

    @Autowired
    private CustomerServeService customerServeService;

    @Autowired
    private ResultInfo resultInfo;

    @GetMapping("/selectCustomerServes")
    @ResponseBody
    public Map<String, Object> selectCustomerServes(CustomerServeQuery customerServeQuery, HttpServletRequest request) {
        String userName = CookieUtil.getCookieValue(request, "userName");
        return customerServeService.selectCustomerServes(customerServeQuery);
    }

    /**
     * 查询字典表数据
     * @param dicName
     * @return
     */
    @GetMapping("/selectDicByName")
    @ResponseBody
    public List<Map<String, Object>> selectDicByName(@RequestParam("dicName") String dicName) {
        return customerServeService.selectDicByName(dicName);
    }

    /**
     * 跳转到服务创建页面
     *
     * @return
     */
    @GetMapping("/toAddAndUpdatePage")
    public String toAddAndUpdatePage(@Nullable Integer id, Model model) {
        if (id == null) {
            // 前往新增页面
            return "customerServe/addAndUpdate";
        }
        // 前往修改页面
        model.addAttribute("customerServe", customerServeService.selectCustomerServeById(id));
        return "customerServe/addAndUpdate";
    }

    @PostMapping("/addCustomerServe")
    @ResponseBody
    public ResultInfo addCustomerServe(CustomerServe customerServe, HttpServletRequest request) {
        String userName = CookieUtil.getCookieValue(request, "userName");
        // 设置创建人
        customerServe.setCreatePeople(userName);
        customerServeService.addCustomerServe(customerServe);
        resultInfo.setAll(200, "服务创建成功", null);
        return resultInfo;
    }

    @PostMapping("/updateCustomerServe")
    @ResponseBody
    public ResultInfo updateCustomerServe(CustomerServe customerServe, HttpServletRequest request) {
        String userName = CookieUtil.getCookieValue(request, "userName");
        customerServeService.updateCustomerServe(customerServe, userName);
        resultInfo.setAll(200, "服务更新成功", null);
        return resultInfo;
    }

    @PostMapping("/deleteCustomerServe")
    @ResponseBody
    public ResultInfo deleteCustomerServe(@RequestParam("state") String state, @RequestParam("id") Integer id) {
        customerServeService.deleteCustomerServe(state, id);
        resultInfo.setAll(200, "服务删除成功", null);
        return resultInfo;
    }

    @PostMapping("/updateState")
    @ResponseBody
    public ResultInfo updateState(@RequestParam("id") Integer id, @RequestParam("state") String state, @RequestParam("flag") Integer flag) {
        customerServeService.updateState(id, state, flag);
        resultInfo.setAll(200, "服务审核完毕", null);
        return resultInfo;
    }

    @GetMapping("/toCustomerServeAssignerPage")
    public String toCustomerServeAssignerPage(@RequestParam("id") Integer id, Model model) {
        model.addAttribute("customerServe", customerServeService.selectCustomerServeById(id));
        return "customerServe/customerServeAssignerForEdit";
    }

    @GetMapping("/toCustomerServeBackPage")
    public String toCustomerServeBackPage(@RequestParam("id") Integer id, Model model) {
        model.addAttribute("customerServe", customerServeService.selectCustomerServeById(id));
        return "customerServe/customerServeBackForEdit";
    }

    /**
     * 客户管理模块的视图有此方法执行跳转
     *
     * @return
     */
    @GetMapping("/index/{index}")
    public String index(@PathVariable("index") Integer index) {
        // 校验参数
        AssertUtil.isTrue(index == null, "非法页面跳转");
        if (index == 1) {
            // 服务创建
            return "customerServe/customerServe";
        } else if (index == 2) {
            // 服务分配
            return "customerServe/customerServeAssigner";
        } else if (index == 3) {
            // 服务处理
            return "customerServe/customerServeHandle";
        } else if (index == 4) {
            // 服务反馈
            return "customerServe/customerServeBack";
        } else if (index == 5) {
            // 服务反馈
            return "customerServe/customerServeArchive";
        } else {
            AssertUtil.isTrue(true, "非法页面跳转");
        }
        return null;
    }

}
