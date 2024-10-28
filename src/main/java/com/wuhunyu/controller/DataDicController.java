package com.wuhunyu.controller;

import com.wuhunyu.base.BaseController;
import com.wuhunyu.base.ResultInfo;
import com.wuhunyu.pojo.DataDic;
import com.wuhunyu.query.DicQuery;
import com.wuhunyu.service.DataDicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

/**
 * 字典管理控制器
 *
 * @author wuhunyu
 * @version 1.0
 * @date 2021-02-08 18:26
 */
@Controller
@RequestMapping("/dic")
public class DataDicController extends BaseController {

    @Autowired
    private DataDicService dataDicService;

    @Autowired
    private ResultInfo resultInfo;

    @GetMapping("/toDicPage")
    public String toDicPage() {
        return "dic/dic";
    }

    @GetMapping("/selectAllDic")
    @ResponseBody
    public Map<String, Object> selectAllDic(DicQuery dicQuery) {
        return dataDicService.selectAllDic(dicQuery);
    }

    @PostMapping("/addDic")
    @ResponseBody
    public ResultInfo addDic(DataDic dataDic) {
        dataDicService.addDic(dataDic);
        resultInfo.setAll(200, "字典数据新增成功", null);
        return resultInfo;
    }

    @PostMapping("/updateDic")
    @ResponseBody
    public ResultInfo updateDic(DataDic dataDic) {
        dataDicService.updateDic(dataDic);
        resultInfo.setAll(200, "字典数据修改成功", null);
        return resultInfo;
    }

    @PostMapping("/deleteDicById")
    @ResponseBody
    public ResultInfo deleteDicById(@RequestParam("id") Integer id) {
        dataDicService.deleteDicById(id);
        resultInfo.setAll(200, "字典数据删除成功", null);
        return resultInfo;
    }

    @GetMapping("/toAddAndUpdatePage")
    public String toAddAndUpdate(@Nullable Integer id, Model model) {
        // 修改
        if (id != null) {
            model.addAttribute("dic", dataDicService.selectDataDicById(id));
        }
        // 新增
        return "dic/addAndUpdate";
    }

}
