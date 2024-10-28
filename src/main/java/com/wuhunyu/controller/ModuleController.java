package com.wuhunyu.controller;

import com.wuhunyu.base.BaseController;
import com.wuhunyu.base.ResultInfo;
import com.wuhunyu.pojo.Module;
import com.wuhunyu.service.ModuleService;
import com.wuhunyu.utils.AssertUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 菜单资源管理控制器
 *
 * @author wuhunyu
 * @version 1.0
 * @date 2020-12-14 15:07
 */
@Controller
@RequestMapping("/module")
public class ModuleController extends BaseController {

    @Autowired
    private ResultInfo resultInfo;

    @Autowired
    private ModuleService moduleService;

    /**
     * 查询全部菜单资源
     * @return
     */
    @GetMapping("/moduleList")
    @ResponseBody
    public List<Map<String, Object>> findAllModules(@RequestParam("roleId") Integer roleId) {
        return moduleService.findAllModule(roleId);
    }

    @GetMapping("/modules")
    @ResponseBody
    public Map<String, Object> findModules() {
        return moduleService.findModules();
    }

    /**
     * 跳转到授权页面
     *
     * @param roleId
     * @return
     */
    @GetMapping("/toModulePage")
    public String toModulePage(@RequestParam("roleId") Integer roleId, Model model) {
        AssertUtil.isTrue(roleId == null, "roleId不能为空");
        model.addAttribute("roleId", roleId);
        return "role/grant";
    }

    /**
     * 跳转到资源管理首页
     * @return
     */
    @GetMapping("/index")
    public String toModule() {
        return "module/module";
    }

    /**
     * 批量删除资源
     * @param ids
     * @return
     */
    @PostMapping("/deleteModule")
    @ResponseBody
    public ResultInfo deleteModuleByModuleId(@RequestParam("ids") Integer[] ids) {
        moduleService.deleteModuleByModuleId(ids);
        resultInfo.setAll(200, "删除资源成功", null);
        return resultInfo;
    }

    /**
     * 新增module资源
     * @param module
     * @return
     */
    @PostMapping("/addModule")
    @ResponseBody
    public ResultInfo addModule(Module module) {
        moduleService.addModule(module);
        resultInfo.setAll(200, "添加资源成功", null);
        return resultInfo;
    }

    /**
     * 修改module资源
     * @param module
     * @return
     */
    @PostMapping("/updateModule")
    @ResponseBody
    public ResultInfo updateModule(Module module) {
        moduleService.updateModule(module);
        resultInfo.setAll(200, "修改资源成功", null);
        return resultInfo;
    }

    /**
     * 资源添加页面
     *
     * @return
     */
    @GetMapping("toAddModule")
    public String toAddModule(@RequestParam("grade") Integer grade, @RequestParam("parentId") Integer parentId, Model model) {
        // 参数校验
        AssertUtil.isTrue(!(grade == 0 || grade == 1 || grade == 2), "级别不符合规范");
        AssertUtil.isTrue(parentId == null, "父级id不能为空");
        if (grade == 0) {
            AssertUtil.isTrue(parentId != -1, "父级id错误");
        } else {
            AssertUtil.isTrue(moduleService.selectByPrimaryKey(parentId) == null, "父级id不存在");
        }
        model.addAttribute("grade", grade);
        model.addAttribute("parentId", parentId);
        return "module/addModule";
    }

    /**
     * 资源修改页面
     *
     * @return
     */
    @GetMapping("toUpdateModule")
    public String toUpdateModule(@RequestParam("id") Integer id, Model model) {
        // 校验参数
        AssertUtil.isTrue(id == null, "id不能为空");
        // 校验数据库中是否存在
        Module module = moduleService.selectByPrimaryKey(id);
        AssertUtil.isTrue(module == null, "资源不存在");
        model.addAttribute("module", module);
        return "module/updateModule";
    }

}
