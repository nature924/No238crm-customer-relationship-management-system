package com.wuhunyu.utils;

import com.wuhunyu.exceptions.ParamsException;

public class AssertUtil {

    public static void isTrue(Boolean flag,String msg){
        if(flag){
            throw new ParamsException(msg);
        }
    }

}
