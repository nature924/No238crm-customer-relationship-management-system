package com.wuhunyu.mapper;

import com.wuhunyu.base.BaseMapper;
import com.wuhunyu.pojo.Role;
import com.wuhunyu.query.RoleQuery;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Map;

@Repository("roleMapper")
@Mapper
public interface RoleMapper extends BaseMapper<Role, Integer> {

    /**
     * 查询所有role_id和role_name
     * @return
     */
    public List<Map<String, Object>> findRoles();

    /**
     * 根据用户id查询用户默认的角色
     * @param id
     * @return
     */
    public List<Integer> findDefaultRoles(@Param("id") Integer id);

    /**
     * 动态查询t_role表
     * @param roleQuery
     * @return
     */
    public List<Role> selectByParams(RoleQuery roleQuery);

    /**
     * 根据角色名查询
     * @param roleName
     * @return
     */
    public List<Role> selectByRoleName(@Param("roleName") String roleName);

    /**
     * 批量删除角色数据
     * @param ids
     * @return
     */
    public Integer deleteRoles(@Param("ids") Integer[] ids);

    /**
     * 根据userId查询用户所拥有的全部角色
     * @param userId
     * @return
     */
    public List<String> findRolesByUserId(@Param("userId") Integer userId);

}