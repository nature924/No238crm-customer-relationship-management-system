package com.wuhunyu.aspect;

import com.wuhunyu.annotation.RequestCode;
import com.wuhunyu.exceptions.AuthException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * 权限拦截处理器
 *
 * @author wuhunyu
 * @version 1.0
 * @date 2020-12-15 17:35
 */
@Component
@Aspect
public class PermissionHandler {

    @Autowired
    HttpSession session;

    /**
     * 权限控制
     */
    @Around("@annotation(com.wuhunyu.annotation.RequestCode)")
    public void permissionHandler(ProceedingJoinPoint joinPoint) throws Throwable {
        List<String> permissions = (List<String>) session.getAttribute("permissions");
        System.out.println("permissions:"+permissions);
        if (permissions == null || permissions.size() == 0) {
            // 抛出没有权限的异常
            throw new AuthException();
        } else {
            // 获取注解上的授权码
            MethodSignature signature = (MethodSignature) joinPoint.getSignature();
            RequestCode requestCode = signature.getMethod().getDeclaredAnnotation(RequestCode.class);
            // 如果不存在该授权码，则不执行该方法，并抛出异常
            if (!permissions.contains(requestCode.value())) {
                throw new AuthException();
            }
        }
        joinPoint.proceed();
    }

}
