package com.wuhunyu.config;

import com.wuhunyu.interceptor.NoLoginInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 拦截器配置类
 *
 * @author wuhunyu
 * @version 1.0
 * @date 2020-12-09 15:09
 */
@Configuration
public class MvcConfig implements WebMvcConfigurer {

    @Autowired
    private NoLoginInterceptor noLoginInterceptor;

    @Bean("noLoginInterceptor")
    public NoLoginInterceptor getNoLoginInterceptor() {
        return new NoLoginInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(noLoginInterceptor)
                .addPathPatterns("/**")
                // 释放静态资源
                .excludePathPatterns("/css/**","/images/**","/js/**","/lib/**")
                // 释放登录相关页面
                .excludePathPatterns("/index","/user/login");
    }
}
