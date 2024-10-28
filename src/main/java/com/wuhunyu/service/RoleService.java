package com.wuhunyu.service;

import com.wuhunyu.base.BaseService;
import com.wuhunyu.mapper.RoleMapper;
import com.wuhunyu.mapper.UserRoleMapper;
import com.wuhunyu.pojo.Role;
import com.wuhunyu.pojo.UserRole;
import com.wuhunyu.query.RoleQuery;
import com.wuhunyu.utils.AssertUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 角色处理业务层
 *
 * @author wuhunyu
 * @version 1.0
 * @date 2020-12-13 9:21
 */
@Service("roleService")
public class RoleService extends BaseService<Role, Integer> {

    @Autowired
    private RoleMapper roleMapper;

    /**
     * 查询所有角色表的id和role_name
     * @return
     */
    public List<Map<String, Object>> findRoles(Integer id){
        List<Map<String, Object>> roles = roleMapper.findRoles();
        AssertUtil.isTrue(roles.size() == 0, "角色表数据异常");
        // 如果该角色被禁用，则不能被选中
        for (Map<String, Object> role : roles) {
            if (0 == Integer.parseInt(role.get("disabled").toString())) {
                role.put("disabled", "true");
            } else {
                role.put("disabled", "");
            }
        }
        // 修改，需要显示用户默认的角色
        if (id != null) {
            // 查询用户的默认权限
            List<Integer> defaultRoles = roleMapper.findDefaultRoles(id);
            AssertUtil.isTrue(defaultRoles.size() == 0, "查询用户不存在");
            for (Integer defaultRoleId : defaultRoles) {
                for (Map<String, Object> role : roles) {
                    if (defaultRoleId == Integer.parseInt(role.get("id").toString())) {
                        role.put("selected", "true");
                        break;
                    }
                }
            }
        }
        return roles;
    }

    /**
     * 动态查询角色表
     * @param roleQuery
     * @return
     */
    public Map<String, Object> findRoleList(RoleQuery roleQuery) {
        // 执行查询操作
        List<Role> userRoles = roleMapper.selectByParams(roleQuery);
        AssertUtil.isTrue(userRoles.size() == 0, "查询角色不存在");
        // 封装数据结果集
        return queryByParamsForTable(roleQuery);
    }

    /**
     * 新增角色操作
     * @param role
     */
    @Transactional(propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
    public void addRole(Role role) {
        // roleName不能为空
        AssertUtil.isTrue(StringUtils.isBlank(role.getRoleName()), "角色名不能为空");
        // 创建时间默认
        role.setCreateDate(new Date());
        // 更新时间为当前时间
        role.setUpdateDate(new Date());
        // 是否有效
        role.setIsValid(1);
        // 判断新增的roleName是否有冲突
        List<Role> rolesByDatabase = roleMapper.selectByRoleName(role.getRoleName());
        AssertUtil.isTrue(rolesByDatabase.size() != 0, "角色名已被占用");
        // 执行新增操作
        AssertUtil.isTrue(roleMapper.insertSelective(role) != 1, "新增角色失败");
    }

    /**
     * 更新操作
     * @param role
     */
    @Transactional(propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
    public void updateRole(Role role) {
        // 校验参数
        // id不能为空
        AssertUtil.isTrue(role.getId()==null,"id不能为空");
        // roleName不能为空
        AssertUtil.isTrue(StringUtils.isBlank(role.getRoleName()), "角色名不能为空");
        // 创建时间默认
        role.setCreateDate(null);
        // 更新时间为当前时间
        role.setUpdateDate(new Date());
        // 是否有效
        role.setIsValid(1);
        // 判断数据库是否存在该记录
        Role roleByDataBase = roleMapper.selectByPrimaryKey(role.getId());
        AssertUtil.isTrue(roleByDataBase == null, "修改角色信息不存在");
        // 判断更新之后的roleName是否有冲突
        List<Role> rolesByDatabase = roleMapper.selectByRoleName(role.getRoleName());
        System.out.println(rolesByDatabase);
        AssertUtil.isTrue(rolesByDatabase.size() != 0 && rolesByDatabase.get(0)!=null && !role.getId().equals(rolesByDatabase.get(0).getId()), "角色名已被占用");
        // 执行更新操作
        AssertUtil.isTrue(roleMapper.updateByPrimaryKeySelective(role) != 1, "更新角色失败");
    }

    /**
     * 批量删除操作
     * @param ids
     */
    public void deleteRoles(Integer[] ids) {
        AssertUtil.isTrue(ids == null || ids.length==0, "id不能为空");
        // 执行删除操作
        AssertUtil.isTrue(roleMapper.deleteRoles(ids) < 1, "删除角色失败");
    }
}
