package com.wuhunyu.service;

import com.wuhunyu.base.BaseService;
import com.wuhunyu.mapper.ModuleMapper;
import com.wuhunyu.mapper.PermissionMapper;
import com.wuhunyu.pojo.Module;
import com.wuhunyu.utils.AssertUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import java.util.*;

/**
 * 菜单管理业务层
 *
 * @author wuhunyu
 * @version 1.0
 * @date 2020-12-14 15:03
 */
@Service("moduleService")
public class ModuleService extends BaseService<Module, Integer> {

    @Autowired
    private ModuleMapper moduleMapper;

    @Autowired
    private PermissionMapper permissionMapper;

    /**
     * 查询全部的菜单资源信息
     *
     * @return
     */
    public List<Map<String, Object>> findAllModule(Integer roleId) {
        // 校验返回的结果是否为空
        List<Module> modules = moduleMapper.findAllModule();
        AssertUtil.isTrue(modules == null || modules.size() == 0, "没有菜单资源可用");
        List<Integer> moduleByRoleId = permissionMapper.findModuleByRoleId(roleId);
        // 为了适配zTree，需要对结果进行改造
        // 角色拥有部分权限
        if (moduleByRoleId != null && moduleByRoleId.size() != 0) {
            return changeResult(modules, moduleByRoleId);
        } else {
            // 角色当前没有任何权限
            return changeResult(modules);
        }
    }

    /**
     * 对查询的结果集moduls进行字段适配
     *
     * @param modules
     * @return
     */
    public static List<Map<String, Object>> changeResult(List<Module> modules) {
        ArrayList<Map<String, Object>> list = new ArrayList<>();
        for (Module module : modules) {
            Map<String, Object> map = new HashMap<String, Object>(16);
            // id -> id
            map.put("id", module.getId());
            // parentId -> pId
            map.put("pId", module.getParentId());
            // moduleName -> name
            map.put("name", module.getModuleName());
            // 设置默认展开
            map.put("open", "true");
            // 添加到list集合
            list.add(map);
        }
        AssertUtil.isTrue(list == null || list.size() == 0, "数据适配失败");
        return list;
    }

    /**
     * 对查询的结果集moduls进行字段适配
     *
     * @param modules
     * @param moduleByRoleId
     * @return
     */
    public static List<Map<String, Object>> changeResult(List<Module> modules, List<Integer> moduleByRoleId) {
        ArrayList<Map<String, Object>> list = new ArrayList<>();
        for (Module module : modules) {
            Map<String, Object> map = new HashMap<String, Object>(16);
            // id -> id
            map.put("id", module.getId());
            // parentId -> pId
            map.put("pId", module.getParentId());
            // moduleName -> name
            map.put("name", module.getModuleName());
            // 设置默认展开
            map.put("open", "true");
            // 角色拥有该权限
            if (moduleByRoleId.contains(module.getId())) {
                map.put("checked", "true");
            }
            // 添加到list集合
            list.add(map);
        }
        AssertUtil.isTrue(list == null || list.size() == 0, "数据适配失败");
        return list;
    }

    /**
     * 查询全部资源列表，并将数据封装成layui表格需要的格式
     * @return
     */
    public Map<String, Object> findModules() {
        // 校验返回的结果是否为空
        List<Module> modules = moduleMapper.findAllModule();
        AssertUtil.isTrue(modules == null || modules.size() == 0, "没有资源可用");
        // 封装数据
        HashMap<String, Object> map = new HashMap<>(16);
        map.put("count", modules.size());
        map.put("code", 200);
        map.put("msg", "资源获取成功");
        map.put("data", modules);
        return map;
    }

    /**
     * 根据moduleId批量删除
     * @param ids
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void deleteModuleByModuleId(Integer[] ids) {
        // 校验参数
        AssertUtil.isTrue(ids == null || ids.length == 0, "id不能为空");
        // 查询资源id是否存在
        Module module = moduleMapper.selectByPrimaryKey(ids[0]);
        AssertUtil.isTrue(module == null, "所删除的资源不存在");
        // 查询该资源下是否存在子级资源,三级菜单不需要判断
        if (module.getGrade() != 2) {
            List<Module> modules = moduleMapper.findModuleById(ids[0]);
            System.out.println(ids[0] + "/" + modules);
            AssertUtil.isTrue(modules != null && modules.size() != 0, "无法删除非空的资源");
        }
        // 执行删除操作
        AssertUtil.isTrue(moduleMapper.deleteModuleByModuleId(ids) < 1, "删除资源失败");
        // 删除资源于角色的关系,有一些资源是刚分配但是没有赋予角色的
        AssertUtil.isTrue(permissionMapper.deletePermissionModuleId(ids) == -1, "删除资源失败");
    }

    /**
     * 增加资源
     * @param module
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void addModule(Module module) {
        // 参数校验
        checkParam(module);
        // 执行新增操作
        AssertUtil.isTrue(moduleMapper.addModule(module) != 1, "新增资源失败");
    }

    /**
     * 修改资源
     * @param module
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void updateModule(Module module) {
        // 参数校验
        // id不为空
        AssertUtil.isTrue(module.getId() == null, "资源id不能为空");
        checkParam(module);
        // 执行更新操作
        AssertUtil.isTrue(moduleMapper.updateModule(module) != 1, "修改资源失败");
    }

    /**
     * 对module的增加和修改进行校验
     * @param module
     */
    public void checkParam(Module module) {
        // module对象不能为空
        AssertUtil.isTrue(module == null, "module对象不能为空");
        // 层级只能是0-目录，1-菜单，2-按钮
        AssertUtil.isTrue(module.getGrade() == null || !(module.getGrade() == 0 || module.getGrade() == 1 || module.getGrade() == 2), "层级无效");
        // 资源名称在同一层级下不能相同
        // 修改和新增的判断有区别
        // moduleId不为空时为修改，否则为新增
        AssertUtil.isTrue(StringUtils.isBlank(module.getModuleName()), "资源名称不能为空");
        List<Integer> ids = moduleMapper.findModuleByModuleNameAndGrade(module.getModuleName(), module.getGrade());
        if (module.getId() != null) {
            // 修改
            AssertUtil.isTrue(!(ids == null || ids.size() == 0) && !ids.get(0).equals(module.getId()), "资源名称已被占用");
        } else {
            // 新增
            AssertUtil.isTrue(ids != null && ids.size() != 0, "资源名称已被占用");
        }
        // url在菜单级别时不能为空且不能重复，在其他级别为空
        if (module.getGrade() == 1) {
            AssertUtil.isTrue(StringUtils.isBlank(module.getUrl()), "url不能为空");
            List<Integer> urls = moduleMapper.findModuleByUrlAndGrade(module.getUrl(), module.getGrade());
            if (module.getId() != null) {
                // 修改
                AssertUtil.isTrue(!(urls == null || urls.size() == 0) && !urls.get(0).equals(module.getId()), "url路径已被占用");
            } else {
                // 新增
                AssertUtil.isTrue(urls != null && urls.size() != 0, "url路径已被占用");
            }
        } else {
            // url取空值
            module.setUrl(null);
        }
        // 菜单parentId   grade=0时为空，grade为1或2时parentId不能为空且必须存在
        if (module.getGrade() == 0) {
            // 设置parentId为空
            module.setParentId(-1);
        } else {
            // 不能为空且必须存在数据库中，且二级的父级时一级，三级的父级时二级
            List<Module> parents = moduleMapper.findModuleByParentId(module.getParentId());
            AssertUtil.isTrue(parents == null || parents.size() == 0, "父级菜单不能为空");
            if (module.getGrade() == 1) {
                AssertUtil.isTrue(parents.get(0).getGrade() != 0, "级别逻辑错误");
            } else if (module.getGrade() == 2) {
                AssertUtil.isTrue(parents.get(0).getGrade() != 1, "级别逻辑错误");
            }
        }
        // 权限码，非空且不能重复
        AssertUtil.isTrue(StringUtils.isBlank(module.getOptValue()), "权限码不能为空");
        // 权限码不能重复
        List<Integer> optValues = moduleMapper.findModuleByOptValue(module.getOptValue());
        if (module.getId() != null) {
            // 修改
            AssertUtil.isTrue(!(optValues == null || optValues.size() == 0) && !optValues.get(0).equals(module.getId()), "权限码已被占用");
        } else {
            // 新增
            AssertUtil.isTrue(optValues != null && optValues.size() != 0, "权限码已被占用");
        }
        // 是否有效
        module.setIsValid((byte) 1);
        // 创建时间
        if (module.getId() != null) {
            // 修改保持不变
            module.setCreateDate(null);
        } else {
            // 新增
            module.setCreateDate(new Date());
        }
        // 修改时间
        module.setUpdateDate(new Date());
    }

}
