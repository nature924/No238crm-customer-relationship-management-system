package com.wuhunyu.controller;

import com.wuhunyu.base.BaseController;
import com.wuhunyu.base.ResultInfo;
import com.wuhunyu.enums.StateStatus;
import com.wuhunyu.pojo.CusDevPlan;
import com.wuhunyu.pojo.SaleChance;
import com.wuhunyu.query.CusDevPlanQuery;
import com.wuhunyu.query.SaleChanceQuery;
import com.wuhunyu.service.CusDevPlanService;
import com.wuhunyu.service.SaleChanceService;
import com.wuhunyu.utils.AssertUtil;
import com.wuhunyu.utils.LoginUserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * 客户开发计划控制器
 *
 * @author wuhunyu
 * @version 1.0
 * @date 2020-12-10 21:07
 */
@RequestMapping("/cusDevPlan")
@Controller
public class CusDevPlanController extends BaseController {

    @Autowired
    private SaleChanceService saleChanceService;

    @Autowired
    private CusDevPlanService cusDevPlanService;

    @Autowired
    private ResultInfo resultInfo;

    /**
     * 跳转到客户开发计划首页
     * @return
     */
    @GetMapping("/index")
    public String index() {
        return "cusDevPlan/cusDevPlan";
    }

    /**
     * 多条件查询客户开发计划数据
     *
     * @param saleChanceQuery
     * @return
     */
    @GetMapping("/cusDevPlanList")
    @ResponseBody
    public Map<String, Object> querySaleChanceList(SaleChanceQuery saleChanceQuery, HttpServletRequest request) {
        // 查询客户开发计划数据
        int userId = LoginUserUtil.releaseUserIdFromCookie(request);
        saleChanceQuery.setState(StateStatus.STATED.getType());
        saleChanceQuery.setAssignMan(String.valueOf(userId));
        return saleChanceService.querySaleChanceByParams(saleChanceQuery);
    }

    /**
     * 开启'计划项数据开发'，'计划项数据维护'窗口
     * @param id
     * @param model
     * @return
     */
    @GetMapping("/toCusDevPlan")
    public String toCusDevPlan(@RequestParam("id") Integer id, Model model) {
        AssertUtil.isTrue(id == null, "id不能为空");
        SaleChance saleChance = saleChanceService.selectByPrimaryKey(id);
        AssertUtil.isTrue(saleChance == null, "所操作的记录不存在");
        model.addAttribute("saleChance",saleChance);
        return "cusDevPlan/cusDevPlanData";
    }

    /**
     * 查询客户开发计划数据
     * @param cusDevPlanQuery
     * @return
     */
    @GetMapping("/cusDevPlan")
    @ResponseBody
    public Map<String, Object> queryCusDevPlanList(CusDevPlanQuery cusDevPlanQuery) {
        Map<String, Object> map = cusDevPlanService.querySaleChanceByParams(cusDevPlanQuery);
        return map;
    }

    /**
     * 新增开发计划项
     * @param cusDevPlan
     * @return
     */
    @PostMapping("/addCusDevPlan")
    @ResponseBody
    public ResultInfo addCusDevPlan(CusDevPlan cusDevPlan) {
        cusDevPlanService.addCusDevPlan(cusDevPlan);
        resultInfo.setAll(200, "新增开发计划项成功", null);
        return resultInfo;
    }

    /**
     * 修改开发计划项
     * @param cusDevPlan
     * @return
     */
    @PostMapping("/updateCusDevPlan")
    @ResponseBody
    public ResultInfo updateCusDevPlan(CusDevPlan cusDevPlan) {
        cusDevPlanService.updateCusDevPlan(cusDevPlan);
        resultInfo.setAll(200, "修改开发计划项成功", null);
        return resultInfo;
    }

    @PostMapping("/deleteCusDevPlan")
    @ResponseBody
    public ResultInfo deleteCusDevPlan(Integer[] ids) {
        cusDevPlanService.deleteCusDevPlan(ids);
        resultInfo.setAll(200, "删除开发计划项成功", null);
        return resultInfo;
    }

    @PostMapping("/updateDevResult")
    @ResponseBody
    public ResultInfo updateDevResult(@RequestParam("id") Integer id,@RequestParam("devResult") String devResult) {
        saleChanceService.updateDevResult(id, devResult);
        resultInfo.setAll(200, "修改开发状态成功", null);
        return resultInfo;
    }

    /**
     * 跳转到新增或修改页面
     * @param id
     * @return
     */
    @GetMapping("/toAddAndUpdatePage")
    public String toAddAndUpdatePage(@RequestParam("saleChanceId") Integer saleChanceId,@Nullable Integer id , Model model) {
        // 封装销售机会id
        model.addAttribute("saleChanceId", saleChanceId);
        // 如果id不为空，则表示需要跳转到计划向修改页面
        if (id != null) {
            CusDevPlan cusDevPlan = cusDevPlanService.selectByPrimaryKey(id);
            AssertUtil.isTrue(cusDevPlan == null, "查询的计划项数据不存在");
            model.addAttribute("cusDevPlan", cusDevPlan);
        }
        return "cusDevPlan/addAndUpdate";
    }

}
