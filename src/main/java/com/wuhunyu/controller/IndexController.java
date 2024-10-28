package com.wuhunyu.controller;

import com.wuhunyu.base.BaseController;
import com.wuhunyu.pojo.User;
import com.wuhunyu.service.PermissionService;
import com.wuhunyu.service.UserService;
import com.wuhunyu.utils.CookieUtil;
import com.wuhunyu.utils.LoginUserUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 首页控制器
 *
 * @author wuhunyu
 * @version 1.0
 * @date 2020-12-08 14:24
 */
@Controller
public class IndexController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(IndexController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private PermissionService permissionService;

    /**
     * 登录
     *
     * @return
     */
    @GetMapping("/index")
    public String index(Model model,HttpServletRequest request) {
        // 若存在cookie，则自动填充登录用户名
        String userName = CookieUtil.getCookieValue(request, "userName");
        String password = CookieUtil.getCookieValue(request, "password");
        if (!StringUtils.isBlank(userName)) {
            model.addAttribute("userName", userName);
        }
        if (!StringUtils.isBlank(password)) {
            model.addAttribute("password", password);
        }
        return "index";
    }

    /**
     * 系统欢迎页
     * @return
     */
    @GetMapping("/welcome")
    public String welcome() {
        return "welcome";
    }

    /**
     * 后端管理主页面
     * @return
     */
    @GetMapping({"/","/main"})
    public String main(HttpServletRequest request) {
        // 获取cookie中的userId
        int userId = LoginUserUtil.releaseUserIdFromCookie(request);
        User user = new User();
        if (userId == -1) {
            user.setUserName("游客");
        } else {
            // 根据userId查询userName
            user = userService.selectByPrimaryKey(userId);
            // 获取权限码
            List<String> permissions = permissionService.findAclValueByUserId(userId);
            // 存放到session中
            request.getSession().setAttribute("permissions", permissions);
        }
        request.getSession().setAttribute("user", user);
        return "main";
    }

}
