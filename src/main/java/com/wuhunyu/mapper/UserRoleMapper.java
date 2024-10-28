package com.wuhunyu.mapper;

import com.wuhunyu.base.BaseMapper;
import com.wuhunyu.pojo.UserRole;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author wuhunyu
 */
@Repository("userRoleMapper")
@Mapper
public interface UserRoleMapper extends BaseMapper<UserRole,Integer> {

    /**
     * 根据userId查询用户角色表
     * @param userId
     * @return
     */
    public List<UserRole> findUserRolesByUserId(@Param("userId") Integer userId);

    /**
     * 批量删除t_user_role数据
     * @param ids
     * @param roleId
     * @return
     */
    public Integer deleteUserRoleByIds(@Param("ids") Integer[] ids, @Param("roleId") Integer roleId);

    /**
     * 动态更新t_user_role数据
     * @param userRole
     * @return
     */
    public Integer updateUserRole(UserRole userRole);

}