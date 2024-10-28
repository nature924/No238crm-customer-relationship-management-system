package com.wuhunyu;

import com.alibaba.fastjson.JSON;
import com.wuhunyu.base.ResultInfo;
import com.wuhunyu.exceptions.AuthException;
import com.wuhunyu.exceptions.NoLoginException;
import com.wuhunyu.exceptions.ParamsException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.lang.annotation.Annotation;

/**
 * 全局异常处理类
 *
 * @author wuhunyu
 * @version 1.0
 * @date 2020-12-09 13:58
 */
@Component
public class GlobalExceptionHandler implements HandlerExceptionResolver {

    private Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @Autowired
    private ResultInfo resultInfo;

    /**
     * 全局异常处理
     * @param request
     * @param response
     * @param handler
     * @param ex
     * @return
     */
    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        // 设置默认视图
        ModelAndView view = new ModelAndView("error");
        view.addObject("code", "500");
        view.addObject("msg", "系统资源繁忙");

        // 处理未登录异常
        if (ex instanceof NoLoginException) {
            // 清除view设置
            view.clear();
            // 返回到登录页面
            view.setViewName("redirect:/index");
            logger.error("未登录异常");
            return view;
        }

        // 判断handler
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            Annotation responseBody = handlerMethod.getMethod().getDeclaredAnnotation(ResponseBody.class);
            // 返回视图
            if (responseBody == null) {
                // 自定义异常
                if (ex instanceof ParamsException) {
                    ParamsException paramsException = (ParamsException) ex;
                    view.addObject("code", paramsException.getCode());
                    view.addObject("msg", paramsException.getMsg());
                }else if (ex instanceof AuthException) {
                    AuthException authException = (AuthException) ex;
                    view.addObject("code", authException.getCode());
                    view.addObject("msg", authException.getMsg());
                }
                logger.error("返回视图异常：viewName={}",view.getViewName());
            } else {    // 返回json数据
                // 设置默认值
                resultInfo.setAll(500, ex.getMessage(), null);
                if (ex instanceof ParamsException) {
                    ParamsException paramsException = (ParamsException) ex;
                    resultInfo.setAll(paramsException.getCode(), paramsException.getMsg(), null);
                }else if (ex instanceof AuthException) {
                    AuthException authException = (AuthException) ex;
                    resultInfo.setAll(authException.getCode(), authException.getMsg(), null);
                }
                // 设置响应编码utf-8
                response.setContentType("application/json;charset=UTF-8");
                PrintWriter printer = null;
                try {
                    printer = response.getWriter();
                    String json = JSON.toJSONString(resultInfo);
                    printer.write(json);
                } catch (Exception e) {
                    logger.error(e.getMessage());
                } finally {
                    if (printer != null) {
                        printer.close();
                    }
                }
                logger.error("返回json数据异常：ResultInfo={}",resultInfo);
                return null;
            }
        }
        return view;
    }
}
