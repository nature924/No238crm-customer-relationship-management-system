package com.wuhunyu.mapper;

import com.wuhunyu.base.BaseMapper;
import com.wuhunyu.pojo.Permission;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 资源管理接口
 * @author wuhunyu
 */
@Repository("permissionMapper")
@Mapper
public interface PermissionMapper extends BaseMapper<Permission,Integer> {

    /**
     * 查询全部的资源
     * @return
     */
    public List<Permission> findAllPermissions();

    /**
     * 根据roleId查询角色所具有的资源
     * @param roleId
     * @return
     */
    public List<Integer> findModuleByRoleId(@Param("roleId") Integer roleId);

    /**
     * 根据roleId删除t_permission
     * @param roleId
     * @return
     */
    public Integer deletePermissionByRoleId(@Param("roleId") Integer roleId);

    /**
     * 更新roleId和moduleId更新
     * @param permission
     * @return
     */
    public Integer updatePermissionByRoleIdAndModuleId(Permission permission);

    /**
     * 更新roleId和moduleId删除
     * @param permission
     * @return
     */
    public Integer deletePermissionByRoleIdAndModuleId(Permission permission);

    /**
     * 根据userId查询用户所用的权限码
     * @param userId
     * @return
     */
    public List<String> findAclValueByUserId(@Param("userId") Integer userId);

    /**
     * 根据moduleId批量删除
     * @param ids
     * @return
     */
    public Integer deletePermissionModuleId(@Param("ids") Integer[] ids);

}