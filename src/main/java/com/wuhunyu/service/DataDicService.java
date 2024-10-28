package com.wuhunyu.service;

import com.wuhunyu.mapper.DataDicMapper;
import com.wuhunyu.pojo.DataDic;
import com.wuhunyu.query.DicQuery;
import com.wuhunyu.utils.AssertUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 字段服务层
 *
 * @author wuhunyu
 * @version 1.0
 * @date 2021-02-08 17:45
 */
@Service("dataDicService")
public class DataDicService {

    @Autowired
    private DataDicMapper dataDicMapper;

    public DataDic selectDataDicById(Integer id) {
        // 校验参数
        AssertUtil.isTrue(id == null, "字典id不能为空");
        // 执行查询
        DataDic dataDic = dataDicMapper.selectByPrimaryKey(id);
        AssertUtil.isTrue(dataDic == null || dataDic.getIsValid() == 0, "查询字典数据不存在");
        return dataDic;
    }

    public Map<String, Object> selectAllDic(DicQuery dicQuery) {
        // 校验字典查询参数
        AssertUtil.isTrue(dicQuery == null, "字典查询参数不能为空");
        // 修改分页参数
        dicQuery.setPage((dicQuery.getPage() - 1) * dicQuery.getLimit());
        // 执行查询操作
        List<DataDic> dataDics = dataDicMapper.selectAllDic(dicQuery);
        AssertUtil.isTrue(dataDics == null || dataDics.size() == 0, "查询字典信息为空");
        Integer count = dataDicMapper.selectAllDicForCount(dicQuery);
        AssertUtil.isTrue(count < 0 || count < dataDics.size(), "查询字典总记录数失败");
        // 封装结果集
        Map<String, Object> map = new HashMap<>(16);
        map.put("count", count);
        map.put("data", dataDics);
        map.put("code", 0);
        map.put("msg", "没有查询到任何符合条件的数据");
        return map;
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void addDic(DataDic dataDic) {
        // 校验参数
        AssertUtil.isTrue(dataDic == null, "新增字典数据参数不能为空");
        // 字典名称
        AssertUtil.isTrue(dataDic.getDataDicName() == null || dataDic.getDataDicName().equals(""), "字典名称不能为空");
        // 字典取值
        AssertUtil.isTrue(dataDic.getDataDicValue() == null || dataDic.getDataDicValue().equals(""), "字典值不能为空");
        // 执行新增操作
        dataDic.setId(null);
        dataDic.setCreateDate(new Date());
        dataDic.setUpdateDate(new Date());
        AssertUtil.isTrue(dataDicMapper.addDic(dataDic) != 1, "新增字典数据失败");
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void updateDic(DataDic dataDic) {
        // 校验参数
        AssertUtil.isTrue(dataDic == null, "更新字典数据参数不能为空");
        // id
        AssertUtil.isTrue(dataDic.getId() == null, "字典id不能为空");
        // 字典名称
        AssertUtil.isTrue(dataDic.getDataDicName() == null || dataDic.getDataDicName().equals(""), "字典名称不能为空");
        // 字典取值
        AssertUtil.isTrue(dataDic.getDataDicValue() == null || dataDic.getDataDicValue().equals(""), "字典值不能为空");
        // 确认该记录已存在
        DataDic dataDicForDataBase = dataDicMapper.selectByPrimaryKey(dataDic.getId());
        AssertUtil.isTrue(dataDicForDataBase == null || dataDicForDataBase.getIsValid() == 0, "字典数据不存在");
        dataDic.setUpdateDate(new Date());
        // 执行修改操作
        AssertUtil.isTrue(dataDicMapper.updateDic(dataDic) != 1, "修改字典数据失败");
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void deleteDicById(Integer id) {
        // 校验参数
        AssertUtil.isTrue(id == null, "字典id不能为空");
        // 查看该记录是否存在
        DataDic dataDicForDataBase = dataDicMapper.selectByPrimaryKey(id);
        AssertUtil.isTrue(dataDicForDataBase == null || dataDicForDataBase.getIsValid() == 0, "字典数据不存在");
        // 执行操作
        AssertUtil.isTrue(dataDicMapper.deleteDicById(id) != 1, "字典数据删除失败");
    }

}
