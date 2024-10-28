package com.wuhunyu.query;

import com.wuhunyu.base.BaseQuery;

/**
 * UserRole的查询类
 *
 * @author wuhunyu
 * @version 1.0
 * @date 2020-12-13 17:43
 */
public class RoleQuery extends BaseQuery {

    /**
     * 角色名
     */
    private String roleName;

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }
}
