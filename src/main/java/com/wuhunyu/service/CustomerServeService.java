package com.wuhunyu.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wuhunyu.base.BaseService;
import com.wuhunyu.mapper.CustomerServeMapper;
import com.wuhunyu.mapper.DataDicMapper;
import com.wuhunyu.mapper.UserMapper;
import com.wuhunyu.pojo.CustomerServe;
import com.wuhunyu.pojo.DataDic;
import com.wuhunyu.query.CustomerServeQuery;
import com.wuhunyu.utils.AssertUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 客户服务服务层
 *
 * @author wuhunyu
 * @version 1.0
 * @date 2021-02-05 10:55
 */
@Service("customerServeService")
public class CustomerServeService extends BaseService<CustomerServe, Integer> {

    @Resource
    private CustomerServeMapper customerServeMapper;

    @Resource
    private DataDicMapper dataDicMapper;

    @Resource
    private UserMapper userMapper;

    public Map<String, Object> selectCustomerServes(CustomerServeQuery customerServeQuery) {
        // 校验参数
        AssertUtil.isTrue(customerServeQuery == null, "服务管理查询参数不能为空");
        // 执行查询操作
        // 修改当前页
        customerServeQuery.setPage((customerServeQuery.getPage() - 1) * customerServeQuery.getLimit());
        List<CustomerServe> customerServes = customerServeMapper.selectCustomerServes(customerServeQuery);
        AssertUtil.isTrue(customerServes.size() == 0, "查询服务管理不存在");
        // 查询全部行数
        int count = customerServeMapper.selectCustomerServesForCount(customerServeQuery);
        AssertUtil.isTrue(count < customerServes.size(), "查询服务管理失败");
        Map<String, Object> result = new HashMap<>(16);
        result.put("count", count);
        result.put("data", customerServes);
        result.put("code", 0);
        result.put("msg", "没有查询到任何符合条件的数据");
        // 封装数据结果集
        return result;
    }

    /**
     * 新增服务管理
     * @param customerServe
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void addCustomerServe(CustomerServe customerServe) {
        // 校验参数
        AssertUtil.isTrue(customerServe == null, "服务创建参数不能为空");
        // id置为空
        customerServe.setId(null);
        // 服务类型
        AssertUtil.isTrue(customerServe.getServeType() == null || "".equals(customerServe.getServeType()), "服务类型不能为空");
        // 客户名称
        AssertUtil.isTrue(customerServe.getCustomer() == null || "".equals(customerServe.getCustomer()), "客户名称不能为空");
        // 服务概要
        AssertUtil.isTrue(customerServe.getServiceRequest() == null || "".equals(customerServe.getServiceRequest()), "服务概要不能为空");
        // 服务内容
        AssertUtil.isTrue(customerServe.getOverview() == null || "".equals(customerServe.getOverview()), "服务内容不能为空");
        // 创建人
        AssertUtil.isTrue(customerServe.getCreatePeople() == null || "".equals(customerServe.getCreatePeople()), "创建人不能为空");
        // 校验服务类型是否存在
        AssertUtil.isTrue(dataDicMapper.selectByPrimaryKey(Integer.parseInt(customerServe.getServeType())) == null, "服务类型不存在");
        // 创建时间和修改时间
        customerServe.setCreateDate(new Date());
        customerServe.setUpdateDate(new Date());
        // 是否有效
        customerServe.setIsValid(1);
        // 服务创建
        customerServe.setState("fw_001");
        // 执行插入
        AssertUtil.isTrue(customerServeMapper.insertSelective(customerServe) != 1, "服务创建失败");
    }

    /**
     * 服务更新
     *
     * @param customerServe
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void updateCustomerServe(CustomerServe customerServe, String createPeople) {
        // 校验参数
        AssertUtil.isTrue(customerServe == null, "服务更新参数不能为空");
        AssertUtil.isTrue(customerServe.getId() == null, "服务id不能为空");
        AssertUtil.isTrue(customerServe.getState() == null, "服务状态不能为空");
        // 校验服务是否存在
        CustomerServe customerServeFromDataBase = customerServeMapper.selectByPrimaryKey(customerServe.getId());
        AssertUtil.isTrue(customerServeFromDataBase == null, "服务不存在");
        if ("fw_001".equals(customerServe.getState())) {
            // 服务创建
            // 服务类型
            AssertUtil.isTrue(customerServe.getServeType() == null || "".equals(customerServe.getServeType()), "服务类型不能为空");
            // 客户名称
            AssertUtil.isTrue(customerServe.getCustomer() == null || "".equals(customerServe.getCustomer()), "客户名称不能为空");
            // 服务概要
            AssertUtil.isTrue(customerServe.getServiceRequest() == null || "".equals(customerServe.getServiceRequest()), "服务概要不能为空");
            // 服务内容
            AssertUtil.isTrue(customerServe.getOverview() == null || "".equals(customerServe.getOverview()), "服务内容不能为空");
            // 创建人
            AssertUtil.isTrue(createPeople == null || "".equals(createPeople), "创建人不能为空");
            // 校验服务类型是否存在
            AssertUtil.isTrue(dataDicMapper.selectByPrimaryKey(Integer.parseInt(customerServe.getServeType())) == null, "服务类型不存在");
            // 构建新对象
            customerServeFromDataBase.setServeType(customerServe.getServeType());
            customerServeFromDataBase.setCustomer(customerServe.getCustomer());
            customerServeFromDataBase.setServiceRequest(customerServe.getServiceRequest());
            customerServeFromDataBase.setOverview(customerServe.getOverview());
            customerServeFromDataBase.setCreatePeople(createPeople);
        } else if ("fw_002".equals(customerServe.getState())) {
            // 服务分配 fw_002
            AssertUtil.isTrue(customerServe.getAssigner() == null || customerServe.getAssigner().equals(""), "指派人不能为空");
            // 查看指派人是否存在
            AssertUtil.isTrue(userMapper.selectByPrimaryKey(Integer.parseInt(customerServe.getAssigner())) == null, "指派人不存在");
            // 构造新对象
            customerServeFromDataBase.setAssigner(customerServe.getAssigner());
            customerServeFromDataBase.setAssignTime(new Date());
        } else if ("fw_003".equals(customerServe.getState())) {
            // 服务处理 fw_003
            AssertUtil.isTrue(customerServe.getServiceProcePeople() == null || customerServe.getServiceProcePeople().equals(""), "服务处理人不能为空");
            AssertUtil.isTrue(customerServe.getServiceProce() == null || customerServe.getServiceProce().equals(""), "服务处理内容不能为空");
            // 查看服务处理人是否存在
            AssertUtil.isTrue(userMapper.findUserByName(customerServe.getServiceProcePeople()) == null, "服务处理人不存在");
            // 构造新对象
            customerServeFromDataBase.setServiceProcePeople(customerServe.getServiceProcePeople());
            customerServeFromDataBase.setServiceProce(customerServe.getServiceProce());
            customerServeFromDataBase.setServiceProceTime(new Date());
        } else if ("fw_004".equals(customerServe.getState())) {
            // 服务反馈 fw_004
            // 校验服务结果
            AssertUtil.isTrue(customerServe.getServiceProceResult() == null || customerServe.getServiceProceResult().equals(""), "服务反馈结果不能为空");
            AssertUtil.isTrue(customerServe.getMyd() == null || customerServe.getMyd().equals(""), "服务满意度不能为空");
            customerServeFromDataBase.setServiceProceResult(customerServe.getServiceProceResult());
            customerServeFromDataBase.setMyd(customerServe.getMyd());
        } else {
            AssertUtil.isTrue(true, "服务状态异常");
        }
        customerServeFromDataBase.setUpdateDate(new Date());
        // 执行更新操作
        AssertUtil.isTrue(customerServeMapper.updateByPrimaryKeySelective(customerServeFromDataBase) != 1, "更新服务失败");
    }

    /**
     * 删除单个服务
     * @param state
     * @param id
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void deleteCustomerServe(String state, Integer id) {
        // 参数校验
        AssertUtil.isTrue(state == null || state.equals(""), "服务状态不能为空");
        AssertUtil.isTrue(id == null, "服务id不能为空");
        // 查看服务是否存在
        CustomerServe customerServe = customerServeMapper.selectByPrimaryKey(id);
        // 判断服状态是否一致
        AssertUtil.isTrue(customerServe == null || !customerServe.getState().equals(state), "服务不存在或服务状态不一致");
        // 执行删除操作
        AssertUtil.isTrue(customerServeMapper.deleteCustomerServe(id) != 1, "服务删除失败");
    }

    /**
     * 查询服务类型字典
     *
     * @param dicName
     * @return
     */
    public List<Map<String, Object>> selectDicByName(String dicName) {
        // 校验参数
        AssertUtil.isTrue(dicName == null || dicName.equals(""), "字段参数不能为空");
        // 执行查询
        List<Map<String, Object>> maps = dataDicMapper.selectDicByName(dicName);
        // 校验结果集
        AssertUtil.isTrue(maps == null || maps.size() == 0, "查询结果为空");
        // 返回结果集
        return maps;
    }

    /**
     * 根据服务id查询服务
     * @param id
     * @return
     */
    public CustomerServe selectCustomerServeById(Integer id) {
        // 校验参数
        AssertUtil.isTrue(id == null, "服务id不能为空");
        // 执行查询
        CustomerServe customerServe = customerServeMapper.selectByPrimaryKey(id);
        AssertUtil.isTrue(customerServe == null, "查询服务为空");
        return customerServe;
    }

    /**
     * 修改服务状态
     *
     * @param state
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void updateState(Integer id, String state, Integer flag) {
        // 校验参数
        AssertUtil.isTrue(id == null, "服务id不能为空");
        AssertUtil.isTrue(state == null || state.equals(""), "服务状态不能为空");
        AssertUtil.isTrue(!(flag != null || flag == 0 || flag == 1), "服务标志位不能为空");
        // 查询是否存在服务
        CustomerServe customerServe = customerServeMapper.selectByPrimaryKey(id);
        AssertUtil.isTrue(customerServe == null, "服务不存在");
        AssertUtil.isTrue(!state.equals(customerServe.getState()), "服务状态异常");
        // 执行修改
        String newState = "fw_001";
        if (flag == 0) {
            newState = state.substring(0, 5) + (Integer.parseInt(state.substring(5)) + 1);
        } else if (flag == 1) {
            newState = state.substring(0, 5) + (Integer.parseInt(state.substring(5)) - 1);
        }
        // 修改状态
        customerServe.setState(newState);
        customerServe.setUpdateDate(new Date());
        // 执行操作
        AssertUtil.isTrue(customerServeMapper.updateByPrimaryKeySelective(customerServe) != 1, "服务审核不通过");
    }



}
