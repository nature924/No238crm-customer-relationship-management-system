package com.wuhunyu.annotation;

import java.lang.annotation.*;

/**
 * 授权码管理
 *
 * @author wuhunyu
 * @version 1.0
 * @date 2020-12-15 17:26
 */

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RequestCode {

    /**
     * 授权码
     * @return
     */
    String value() default "0";

}
