package com.wuhunyu.controller;

import com.wuhunyu.annotation.RequestCode;
import com.wuhunyu.base.BaseController;
import com.wuhunyu.base.ResultInfo;
import com.wuhunyu.pojo.Role;
import com.wuhunyu.query.RoleQuery;
import com.wuhunyu.service.RoleService;
import com.wuhunyu.utils.AssertUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

/**
 * 角色处理控制器层
 *
 * @author wuhunyu
 * @version 1.0
 * @date 2020-12-13 9:23
 */
@Controller
@RequestMapping("/role")
public class RoleController extends BaseController {

    @Autowired
    private ResultInfo resultInfo;

    @Autowired
    private RoleService roleService;

    /**
     * 查询所有角色表的id和roleName
     * @return
     */
    @GetMapping("/findRoles")
    @ResponseBody
    public List<Map<String, Object>> findRoles(@Nullable Integer id) {
        return roleService.findRoles(id);
    }

    /**
     * 动态查询t_role表
     * @param roleQuery
     * @return
     */
    @GetMapping("/roleList")
    @ResponseBody
    public Map<String, Object> selectByParams(RoleQuery roleQuery) {
        return roleService.findRoleList(roleQuery);
    }

    /**
     * 角色视图首页
     * @return
     */
    @GetMapping("/index")
    public String toRolePage() {
        return "role/role";
    }

    /**
     * 新增/修改公用页面
     * @param roleId
     * @return
     */
    @GetMapping("/toAddAndUpdatePage")
    public String toAddAndUpdatePage(@Nullable Integer roleId, Model model) {
        // 修改
        if (roleId != null) {
            Role role = roleService.selectByPrimaryKey(roleId);
            AssertUtil.isTrue(role == null, "角色不存在");
            model.addAttribute("role", role);
        }
        return "role/addAndUpdateRole";
    }

    /**
     * 新增角色
     * @param role
     * @return
     */
    @RequestCode("601001")
    @PostMapping("/addRole")
    @ResponseBody
    public ResultInfo addRole(Role role) {
        roleService.addRole(role);
        resultInfo.setAll(200, "新增角色成功", null);
        return resultInfo;
    }

    /**
     * 修改角色信息
     * @param role
     * @return
     */
    @RequestCode("601003")
    @PostMapping("/updateRole")
    @ResponseBody
    public ResultInfo updateRole(Role role) {
        roleService.updateRole(role);
        resultInfo.setAll(200, "修改角色成功", null);
        return resultInfo;
    }

    /**
     * 批量删除角色
     * @param ids
     * @return
     */
    @RequestCode("601004")
    @PostMapping("/deleteRole")
    @ResponseBody
    public ResultInfo updateRole(@RequestParam("ids") Integer[] ids) {
        roleService.deleteRoles(ids);
        resultInfo.setAll(200, "删除角色成功", null);
        return resultInfo;
    }


}
