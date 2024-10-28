package com.wuhunyu.controller;

import com.wuhunyu.base.BaseController;
import com.wuhunyu.base.ResultInfo;
import com.wuhunyu.exceptions.ParamsException;
import com.wuhunyu.pojo.User;
import com.wuhunyu.query.UserQuery;
import com.wuhunyu.service.UserService;
import com.wuhunyu.utils.AssertUtil;
import com.wuhunyu.utils.LoginUserUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * User控制器类
 *
 * @author wuhunyu
 * @version 1.0
 * @date 2020-12-08 15:43
 */
@Controller
@RequestMapping("/user")
public class UserController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private ResultInfo resultInfo;

    /**
     * 登录
     * @param userName
     * @param password
     * @return
     */
    @PostMapping("/login")
    @ResponseBody
    public ResultInfo findUserByName(@Nullable String userName,@Nullable String password) {
        Map<String, String> user = userService.findUserByName(userName, password);
        // 将结果封装到ResultInfo中
        resultInfo.setAll(200, "登陆成功", user);
        logger.info("user：{}",user);
        logger.info("resultInfo:{}",resultInfo);
        return resultInfo;
    }

    @GetMapping("/toUpdateCurrentPage")
    public String toUpdateCurrentPage(HttpServletRequest request, Model model) {
        // 获取userId
        int userId = LoginUserUtil.releaseUserIdFromCookie(request);
        model.addAttribute("userModel", userService.selectByPrimaryKey(userId));
        return "user/addAndUpdateUser";
    }

    /**
     * 跳转到修改密码页面
     * @return
     */
    @GetMapping("/updatePWDPage")
    public String updatePasswordPage() {
        return "user/password";
    }

    /**
     * 修改密码操作
     * @param request
     * @param oldPWD
     * @param newPWD
     * @param checkPWD
     * @return
     */
    @PostMapping("/updatePWD")
    @ResponseBody
    public ResultInfo updatePassword(HttpServletRequest request
            ,String oldPWD,String newPWD,String checkPWD) {
        int userId = LoginUserUtil.releaseUserIdFromCookie(request);
        userService.updatePassword(userId, oldPWD, newPWD, checkPWD);
        resultInfo.setAll(200, "修改成功", null);
        logger.info("resultInfo:{}",resultInfo);
        return resultInfo;
    }

    /**
     * 登出操作
     * @param request
     * @return
     */
    @GetMapping("/logout")
    public String logout(HttpServletRequest request) {
        // 获取userId
        int userId = LoginUserUtil.releaseUserIdFromCookie(request);
        // 登录状态
        if (userId != -1) {
           // 删除session，回到登录页面
            if (request.getSession().getAttribute("user") != null) {
                // 不为空则删除session
                request.getSession().removeAttribute("user");
            }
        }
        return "redirect:/index";
    }

    // *******************************************************************************************
    //                               以下为用户权限管理相关操作
    // *******************************************************************************************

    /**
     * 查询用户列表数据
     * @param userQuery
     * @return
     */
    @GetMapping("/userList")
    @ResponseBody
    public Map<String, Object> selectByParams(UserQuery userQuery) {
        return userService.selectByParams(userQuery);
    }

    /**
     * 跳转到用户管理首页
     * @return
     */
    @GetMapping("/index")
    public String index() {
        return "user/user";
    }

    /**
     * 跳转到用户管理新增/修改页面
     * @param id
     * @return
     */
    @GetMapping("/toAddUserPage")
    public String toAddUserPage(@Nullable Integer id, Model model) {
        // id不为null表示访问的是修改页面
        if (id != null) {
            model.addAttribute("userModel",userService.selectByPrimaryKey(id));
        }
        return "user/addAndUpdateUser";
    }

    /**
     * 新增用户
     * @param user
     * @param roleIds
     * @return
     */
    @PostMapping("/addUser")
    @ResponseBody
    public ResultInfo addUser(User user, @RequestParam("roleIds") Integer[] roleIds) {
        userService.addUser(user, roleIds);
        resultInfo.setAll(200, "新增用户成功", null);
        return resultInfo;
    }

    /**
     * 修改用户信息
     * @param user
     * @param roleIds
     * @return
     */
    @PostMapping("/updateUser")
    @ResponseBody
    public ResultInfo updateUser(User user, @RequestParam("roleIds") Integer[] roleIds) {
        userService.updateUser(user, roleIds);
        resultInfo.setAll(200, "修改用户成功", null);
        return resultInfo;
    }

    /**
     * 批量删除用户
     * @param ids
     * @return
     */
    @PostMapping("/deleteUsers")
    @ResponseBody
    public ResultInfo deleteUsers(@RequestParam("ids") Integer[] ids) {
        userService.deleteUsers(ids);
        resultInfo.setAll(200, "删除用户成功", null);
        return resultInfo;
    }

}
