package com.wuhunyu.interceptor;

import com.wuhunyu.exceptions.NoLoginException;
import com.wuhunyu.mapper.UserMapper;
import com.wuhunyu.utils.LoginUserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 登录拦截器
 *
 * @author wuhunyu
 * @version 1.0
 * @date 2020-12-09 14:48
 */
public class NoLoginInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private UserMapper userMapper;

    /**
     * 对cookie中没有userId或session没有user的请求进行拦截，并抛出NoLoginException异常
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 获取session
        Object user = request.getSession().getAttribute("user");
        // 获取cookie
        int userId = LoginUserUtil.releaseUserIdFromCookie(request);
        // session为空
        if (user != null) {
            return true;
        }else if (userId != -1 && userMapper.selectByPrimaryKey(userId) != null) {
            return true;
        }else {
            throw new NoLoginException();
        }
    }
}
