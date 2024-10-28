package com.wuhunyu.base;


import org.springframework.web.bind.annotation.ModelAttribute;

import javax.servlet.http.HttpServletRequest;

/**
 * 基础控制器对象
 * @author wuhunyu
 * @date 2020年12月08日14点17分
 */
public class BaseController {

    @ModelAttribute
    public void preHandler(HttpServletRequest request){
        // 获取项目的地址
        request.setAttribute("serverName",request.getServerName());
        request.setAttribute("crm", request.getContextPath());
    }


    public ResultInfo success(){
        return new ResultInfo();
    }

    public ResultInfo success(String msg){
        ResultInfo resultInfo= new ResultInfo();
        resultInfo.setMsg(msg);
        return resultInfo;
    }

    public ResultInfo success(String msg,Object result){
        ResultInfo resultInfo= new ResultInfo();
        resultInfo.setMsg(msg);
        resultInfo.setResult(result);
        return resultInfo;
    }

}
