package com.wuhunyu.query;

import com.wuhunyu.base.BaseQuery;
import java.io.Serializable;

/**
 * 字段表查询类
 *
 * @author wuhunyu
 * @version 1.0
 * @date 2021-02-08 17:33
 */
public class DicQuery extends BaseQuery implements Serializable {

    private String dicName;

    public DicQuery() {
    }

    public DicQuery(String dicName) {
        this.dicName = dicName;
    }

    public String getDicName() {
        return dicName;
    }

    public void setDicName(String dicName) {
        this.dicName = dicName;
    }
}
