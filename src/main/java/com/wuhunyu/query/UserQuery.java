package com.wuhunyu.query;

import com.wuhunyu.base.BaseQuery;

/**
 * user查询类
 *
 * @author wuhunyu
 * @version 1.0
 * @date 2020-12-12 10:28
 */
public class UserQuery extends BaseQuery {

    /**
     * 用户名
     */
    private String userName;

    /**
     * 电子邮箱
     */
    private String email;

    /**
     * 手机号
     */
    private String phone;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
