package com.wuhunyu.controller;

import com.wuhunyu.base.BaseController;
import com.wuhunyu.base.ResultInfo;
import com.wuhunyu.enums.StateStatus;
import com.wuhunyu.pojo.SaleChance;
import com.wuhunyu.query.SaleChanceQuery;
import com.wuhunyu.service.SaleChanceService;
import com.wuhunyu.utils.AssertUtil;
import com.wuhunyu.utils.CookieUtil;
import com.wuhunyu.utils.LoginUserUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * 销售机会控制层
 *
 * @author wuhunyu
 * @version 1.0
 * @date 2020-12-09 16:32
 */
@Controller
@RequestMapping("/saleChance")
public class SaleChanceController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(SaleChanceController.class);

    @Autowired
    private SaleChanceService saleChanceService;

    @Autowired
    private ResultInfo resultInfo;

    /**
     * 多条件查询销售机会数据
     *
     * @param saleChanceQuery
     * @return
     */
    @GetMapping("/saleChanceList")
    @ResponseBody
    public Map<String, Object> querySaleChanceList(SaleChanceQuery saleChanceQuery) {
        // 查询客户开发计划数据
        System.out.println(saleChanceQuery);
        return saleChanceService.querySaleChanceByParams(saleChanceQuery);
    }

    /**
     * 跳转至销售机会首页
     * @return
     */
    @GetMapping("/index")
    public String SaleChance() {
        return "saleChance/saleChance";
    }

    /**
     * 跳转到销售机会新增，更新页面
     * @param id  为null表示跳转到新增页面，不为null表示跳转到更新页面
     * @return
     */
    @GetMapping("/toSaleChance")
    public String toSaleChance(@Nullable Integer id, Model model) {
        if (id != null) {
            // 访问修改页面
            SaleChance saleChance = saleChanceService.selectByPrimaryKey(id);
            model.addAttribute("saleChance", saleChance);
        }
        return "saleChance/addAndUpdate";
    }

    @PostMapping("/addSaleChance")
    @ResponseBody
    public ResultInfo doAddSaleChance(HttpServletRequest request, SaleChance saleChance) {
        String userName = CookieUtil.getCookieValue(request, "userName");
        // 设置创建人
        saleChance.setCreateMan(userName);
        saleChanceService.addSaleChance(saleChance);
        resultInfo.setAll(200,"新增销售机会记录成功",null);
        return resultInfo;
    }

    @PostMapping("/updateSaleChance")
    @ResponseBody
    public ResultInfo doUpdateSaleChance(SaleChance saleChance) {
        // 设置创建人
        saleChanceService.updateSaleChance(saleChance);
        resultInfo.setAll(200,"修改销售机会记录成功",null);
        return resultInfo;
    }

    /**
     * 获取指派人信息，id和userName
     * @return
     */
    @PostMapping("/getAssignMan")
    @ResponseBody
    public ResultInfo getAssignMan() {
        List<Map<String, Object>> assignMans = saleChanceService.getAssignMan();
        logger.info("assignMans={}",assignMans);
        AssertUtil.isTrue(assignMans == null, "获取指派人信息失败");
        AssertUtil.isTrue(assignMans.size() == 0, "没有可获取的指派人信息");
        resultInfo.setAll(200, "获取指派人成功", assignMans);
        return resultInfo;
    }

    /**
     * 批量删除销售机会记录
     * @return
     */
    @PostMapping("/deleteSaleChance")
    @ResponseBody
    public ResultInfo deleteSaleChance(@RequestParam("ids") Integer[] ids) {
        System.out.println("ids="+ids);
        saleChanceService.deleteSaleChance(ids);
        resultInfo.setAll(200, "删除成功", null);
        return resultInfo;
    }

}
