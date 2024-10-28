package com.wuhunyu.base;

import org.springframework.stereotype.Component;

@Component("resultInfo")
public class ResultInfo {
    private Integer code=200;
    private String msg="success";

    private Object result;

    public ResultInfo() {
    }

    public ResultInfo(Integer code, String msg, Object result) {
        this.code = code;
        this.msg = msg;
        this.result = result;
    }

    public void setAll(Integer code, String msg, Object result){
        this.code = code;
        this.msg = msg;
        this.result = result;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "ResultInfo{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", result=" + result +
                '}';
    }
}
