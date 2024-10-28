package com.wuhunyu.service;

import com.wuhunyu.base.BaseService;
import com.wuhunyu.mapper.ModuleMapper;
import com.wuhunyu.mapper.PermissionMapper;
import com.wuhunyu.mapper.UserMapper;
import com.wuhunyu.pojo.Module;
import com.wuhunyu.pojo.Permission;
import com.wuhunyu.utils.AssertUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import java.util.*;

/**
 * 资源管理业务层
 *
 * @author wuhunyu
 * @version 1.0
 * @date 2020-12-14 14:43
 */
@Service("permissionService")
public class PermissionService extends BaseService<Permission, Integer> {

    @Autowired
    private PermissionMapper permissionMapper;

    @Autowired
    private ModuleMapper moduleMapper;

    @Autowired
    private UserMapper userMapper;

    /**
     * 查询所有资源
     * @return
     */
    public List<Permission> findAllPermissions() {
        List<Permission> permissions = permissionMapper.findAllPermissions();
        // 校验结果是否为空
        AssertUtil.isTrue(permissions==null || permissions.size() == 0, "没有资源可用");
        return permissions;
    }

    /**
     * 授权操作
     * @param roleId
     * @param ids
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void updatePermission(Integer roleId, Integer[] ids) {
        // 校验参数
        AssertUtil.isTrue(roleId == null, "角色id不能为空");
        // ids为空则表示该角色没有任何权限，直接执行删除操作
        if (ids == null || ids.length == 0) {
            AssertUtil.isTrue(permissionMapper.deletePermissionByRoleId(roleId) == -1, "授权失败");
        } else {
            // 根据roleId查询全部的资源记录id
            List<Integer> moduleByRoleId = permissionMapper.findModuleByRoleId(roleId);
            List<Integer> modules = Arrays.asList(ids);
            // 使用一个set对list去并集
            Set<Integer> set = new HashSet<>(16);
            // 将moduleByRoleId的所有值存在set中
            for (Integer module : moduleByRoleId) {
                set.add(module);
            }
            // 将modules的所有值存在set中
            for (Integer module : modules) {
                set.add(module);
            }
            // 准备一个Permission对象
            Permission permission = new Permission();
            // 设置角色id
            permission.setRoleId(roleId);
            // 设置更新时间
            permission.setUpdateDate(new Date());
            // 遍历set
            Iterator<Integer> iterator = set.iterator();
            while (iterator.hasNext()) {
                Integer next = iterator.next();
                // 设置资源id
                permission.setModuleId(next);
                // 原始数据中和新增数据中都有该资源，进行跟新操作
                if (moduleByRoleId.contains(next) && modules.contains(next)) {
                    // 保持创建时间不变
                    permission.setCreateDate(null);
                    // 权限，保持不变
                    permission.setAclValue(null);
                    // 执行更新操作
                    AssertUtil.isTrue(permissionMapper.updatePermissionByRoleIdAndModuleId(permission) != 1, "授权失败");
                } else if (moduleByRoleId.contains(next)) {
                    // 数据库中存在而新增的数据中不存在，执行删除操作
                    AssertUtil.isTrue(permissionMapper.deletePermissionByRoleIdAndModuleId(permission) != 1, "授权失败");
                } else {
                    // 数据库中没有，而新增数据中有，执行新增操作
                    // 权限，暂定为10，目前id最大为5614
                    permission.setAclValue(getAclValue(next));
                    // 设置创建时间
                    permission.setCreateDate(new Date());
                    // 执行新增操作
                    AssertUtil.isTrue(permissionMapper.insertSelective(permission) != 1, "授权失败");
                }
            }
        }
    }

    /**
     * 根据moduleId查询权限码
     * @param moduleId
     * @return
     */
    public String getAclValue(Integer moduleId) {
        // 查询全部module记录
        List<Module> modules = moduleMapper.findAllModule();
        for (Module module : modules) {
            if (moduleId.equals(module.getId())) {
                return module.getOptValue();
            }
        }
        AssertUtil.isTrue(true, "授权失败");
        return null;
    }

    /**
     * 查询用户所具有的权限
     * @param userId
     * @return
     */
    public List<String> findAclValueByUserId(Integer userId) {
        // 校验参数
        // 校验参数不能为空
        AssertUtil.isTrue(userId == null, "用户id不能为空");
        // 校验该用户id确实可用
        AssertUtil.isTrue(userMapper.selectByPrimaryKey(userId) == null, "用户不存在");
        // 执行参训操作
        return permissionMapper.findAclValueByUserId(userId);
    }

}
