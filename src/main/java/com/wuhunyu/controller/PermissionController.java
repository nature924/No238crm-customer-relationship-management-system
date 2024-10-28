package com.wuhunyu.controller;

import com.wuhunyu.base.ResultInfo;
import com.wuhunyu.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 资源管理控制器
 *
 * @author wuhunyu
 * @version 1.0
 * @date 2020-12-14 14:46
 */
@Controller
@RequestMapping("/permission")
public class PermissionController {

    @Autowired
    private PermissionService permissionService;

    @Autowired
    private ResultInfo resultInfo;

    /**
     * 查询全部资源
     * @return
     */
    @GetMapping("/permissionList")
    @ResponseBody
    public ResultInfo findAllPermissions() {
        resultInfo.setAll(200, "查询资源成功", permissionService.findAllPermissions());
        return resultInfo;
    }

    /**
     *
     * @param roleId    角色id
     * @param ids   资源id
     * @return
     */
    @PostMapping("/updatePermission")
    @ResponseBody
    public ResultInfo updatePermission(@RequestParam("roleId") Integer roleId, @Nullable Integer[] ids) {
        permissionService.updatePermission(roleId, ids);
        resultInfo.setAll(200, "授权成功", null);
        return resultInfo;
    }

}
