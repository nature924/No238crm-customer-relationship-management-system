package com.wuhunyu.mapper;

import com.wuhunyu.base.BaseMapper;
import com.wuhunyu.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository("userMapper")
@Mapper
public interface UserMapper extends BaseMapper<User, Integer> {

    /**
     * 根据用户名查询user记录
     *
     * @param userName
     * @return
     */
    public User findUserByName(@Param("userName") String userName);

    /**
     * 获取指派人信息
     * @return
     */
    public List<Map<String, Object>> getAssignMan();

    /**
     * 批量删除用户
     * @param ids
     * @return
     */
    public Integer deleteUsers(@Param("ids") Integer[] ids);

    /**
     * 根据用户名查询数据
     * @param userName
     * @return
     */
    public List<User> findUsersByName(@Param("userName") String userName);

}