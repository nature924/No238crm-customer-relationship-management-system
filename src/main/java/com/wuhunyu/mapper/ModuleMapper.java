package com.wuhunyu.mapper;

import com.wuhunyu.base.BaseMapper;
import com.wuhunyu.pojo.Module;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 菜单管理接口
 * @author wuhunyu
 */
@Repository("moduleMapper")
@Mapper
public interface ModuleMapper extends BaseMapper<Module,Integer> {

    /**
     * 查询全部的菜单分配记录
     * @return
     */
    public List<Module> findAllModule();

    /**
     * 根据资源id批量删除
     * @param ids
     * @return
     */
    public Integer deleteModuleByModuleId(@Param("ids") Integer[] ids);

    /**
     * 根据moduleName和grade查询moduleId
     * @param moduleName
     * @param grade
     * @return
     */
    public List<Integer> findModuleByModuleNameAndGrade(@Param("moduleName") String moduleName, @Param("grade") Integer grade);

    /**
     * 根据url和grade查询moduleId
     * @param url
     * @param grade
     * @return
     */
    public List<Integer> findModuleByUrlAndGrade(@Param("url") String url, @Param("grade") Integer grade);

    /**
     * 根据parentId查询module
     * @param parentId
     * @return
     */
    public List<Module> findModuleByParentId(@Param("parentId") Integer parentId);

    /**
     * 根据id查询module
     * @param id
     * @return
     */
    public List<Module> findModuleById(@Param("id") Integer id);

    /**
     * 根据optValue查询moduleId
     * @param optValue
     * @return
     */
    public List<Integer> findModuleByOptValue(@Param("optValue") String optValue);

    /**
     * 新增module
     * @param module
     * @return
     */
    public Integer addModule(Module module);

    /**
     * 更新module
     * @param module
     * @return
     */
    public Integer updateModule(Module module);

}