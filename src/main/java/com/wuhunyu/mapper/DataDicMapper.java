package com.wuhunyu.mapper;

import com.wuhunyu.base.BaseMapper;
import com.wuhunyu.pojo.DataDic;
import com.wuhunyu.query.DicQuery;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Map;

/**
 * 字典数据接口
 *
 * @author wuhun
 */
@Mapper
@Repository("dataDicMapper")
public interface DataDicMapper extends BaseMapper<DataDic, Integer> {

    /**
     * 按服务名查询字典数据
     * @param dicName
     * @return
     */
    public List<Map<String, Object>> selectDicByName(@Param("dicName") String dicName);

    /**
     * 查询全部字典数据
     * @param dicQuery
     * @return
     */
    public List<DataDic> selectAllDic(DicQuery dicQuery);

    /**
     * 查询字典数据总记录数
     * @param dicQuery
     * @return
     */
    public Integer selectAllDicForCount(DicQuery dicQuery);

    /**
     * 新增字典数据
     * @param dataDic
     * @return
     */
    public int addDic(DataDic dataDic);

    /**
     * 更新字典
     * @param dataDic
     * @return
     */
    public int updateDic(DataDic dataDic);

    /**
     * 根据id删除字典数据
     * @param id
     * @return
     */
    public int deleteDicById(@Param("id") Integer id);

}