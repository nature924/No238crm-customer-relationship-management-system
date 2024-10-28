package com.wuhunyu.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wuhunyu.base.BaseService;
import com.wuhunyu.mapper.RoleMapper;
import com.wuhunyu.mapper.UserMapper;
import com.wuhunyu.mapper.UserRoleMapper;
import com.wuhunyu.pojo.User;
import com.wuhunyu.pojo.UserRole;
import com.wuhunyu.query.UserQuery;
import com.wuhunyu.utils.AssertUtil;
import com.wuhunyu.utils.Md5Util;
import com.wuhunyu.utils.PhoneUtil;
import com.wuhunyu.utils.UserIDBase64;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * UserService业务逻辑层
 *
 * @author wuhunyu
 * @version 1.0
 * @date 2020-12-08 15:42
 */
@Service("userService")
@ConfigurationProperties(prefix = "user")
@PropertySource("classpath:/password.yml")
public class UserService extends BaseService<User, Integer> {

    /**
     * 读取配置文件中的password值
     */
    @Value("${password}")
    private String password;

    private Map<String, String> map = new HashMap<>(16);

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private UserRoleMapper userRoleMapper;

    /**
     * 根据用户名查询user记录
     * @param userName
     * @return
     */
    public Map<String,String> findUserByName(String userName, String password) {
        // 验证userName非空
        AssertUtil.isTrue(StringUtils.isBlank(userName),"用户名不能为空");
        // 验证password非空
        AssertUtil.isTrue(StringUtils.isBlank(password),"密码不能为空");
        // 调用userMapper查询user记录
        User user = userMapper.findUserByName(userName);
        // 校验用户是否失效
        AssertUtil.isTrue(user.getIsValid() != 1, "该用户已被除名");
        // 验证查询结果user非空
        AssertUtil.isTrue(user == null, "用户不存在");
        if (Md5Util.encode(password).equals(user.getUserPwd())) {
            return changeUser(user);
        }
        AssertUtil.isTrue(true,"密码输入有误");
        return null;
    }

    /**
     * 修改密码
     * @param userId
     * @param oldPWD
     * @param newPWD
     * @param checkPWD
     */
    @Transactional(propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
    public void updatePassword(Integer userId,String oldPWD,String newPWD,String checkPWD) {
        // 判断字段是否为空
        AssertUtil.isTrue(userId == -1, "userId为空");
        AssertUtil.isTrue(StringUtils.isBlank(oldPWD), "旧密码为空");
        AssertUtil.isTrue(StringUtils.isBlank(newPWD), "新密码为空");
        AssertUtil.isTrue(StringUtils.isBlank(checkPWD), "确认密码为空");
        // 校验新旧密码是否相同
        AssertUtil.isTrue(oldPWD.equals(newPWD), "新旧密码不能相同");
        // 校验新密码和确认密码是否相同
        AssertUtil.isTrue(!newPWD.equals(checkPWD), "两次新密码不一致");
        User user = userMapper.selectByPrimaryKey(userId);
        // 判断查询结果是否为空
        AssertUtil.isTrue(null == user, "用户不存在");
        // 旧密码和数据库中的密码不一致
        AssertUtil.isTrue(!Md5Util.encode(oldPWD).equals(user.getUserPwd()), "旧密码输入错误");
        // 修改密码
        user.setUserPwd(Md5Util.encode(newPWD));
        Integer result = userMapper.updateByPrimaryKeySelective(user);
        AssertUtil.isTrue(result < 1, "修改密码失败");
    }

    /**
     * 截取一部分user字段
     * @param user
     * @return
     */
    private Map<String, String> changeUser(User user) {
        // 对userId进行加密处理
        map.put("userId", UserIDBase64.encoderUserID(user.getId()));
        map.put("userName", user.getUserName());
        map.put("trueName", user.getTrueName());
        return map;
    }

    /**
     * 用户权限管理查询
     * @param userQuery
     * @return
     */
    public Map<String, Object> selectByParams(UserQuery userQuery) {
        Map<String,Object> result = new HashMap<>(16);
        PageHelper.startPage(userQuery.getPage(),userQuery.getLimit());
        PageInfo<User> users =new PageInfo<User>(userMapper.selectByParams(userQuery));
        // 使用list来存放所有的数据
        ArrayList<Object> userList = new ArrayList<>();
        // 格式化时间
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        for (User user : users.getList()) {
            // 创建一个新的map来存放数据
            Map<String, Object> map = new HashMap<>(16);
            map.put("id", user.getId());
            map.put("userName", user.getUserName());
            map.put("trueName", user.getTrueName());
            map.put("email", user.getEmail());
            map.put("phone", user.getPhone());
            map.put("createDate", formatter.format(user.getCreateDate()));
            map.put("updateDate", formatter.format(user.getUpdateDate()));
            // 添加角色集合
            map.put("roleName", roleMapper.findRolesByUserId(user.getId()));
            userList.add(map);
        }
        result.put("count", userList.size());
        result.put("data", userList);
        result.put("code", 0);
        result.put("msg", "没有查询到任何符合条件的数据");
        // 返回结果
        return result;
    }

    /**
     * 新增一个用户
     * @param user
     */
    @Transactional(propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
    public void addUser(User user, Integer[] ids) {
        // 参数校验
        // 用户名不能为空
        AssertUtil.isTrue(StringUtils.isBlank(user.getUserName()), "用户名不能为空");
        // 真实姓名不能为空
        AssertUtil.isTrue(StringUtils.isBlank(user.getTrueName()), "真实姓名不能为空");
        // email不能为空
        AssertUtil.isTrue(StringUtils.isBlank(user.getEmail()),"邮箱不能为空");
        // phone不能为空
        AssertUtil.isTrue(StringUtils.isBlank(user.getPhone()),"手机号码不能为空");
        // 校验手机号码格式
        AssertUtil.isTrue(!PhoneUtil.isMobile(user.getPhone()), "手机号码格式错误");
        // 角色不能为空
        AssertUtil.isTrue(ids == null && ids.length == 0, "用户角色不能为空");
        // 需要保证数据库中没有和新增用户同名的记录
        AssertUtil.isTrue(userMapper.findUsersByName(user.getUserName()).size() > 0, "此用户名已被占用");
        // 默认参数设置
        // 是否有效
        user.setIsValid(1);
        user.setCreateDate(new Date());
        user.setUpdateDate(new Date());
        // 默认密码
        user.setUserPwd(Md5Util.encode(password));
        // 执行新增操作
        AssertUtil.isTrue(userMapper.insertSelective(user) != 1, "新增用户失败");
        // 执行添加角色操作
        List<Map<String, Object>> roles = roleMapper.findRoles();
        AssertUtil.isTrue(roles.size() == 0, "角色表数据异常");
        List<UserRole> rolesByUserId = userRoleMapper.findUserRolesByUserId(user.getId());
        // userRole实体类对象
        UserRole userRole = new UserRole();
        // 创建时间
        userRole.setCreateDate(new Date());
        // 修改事件
        userRole.setUpdateDate(new Date());
        for (Integer id : ids) {
            boolean flag = false;
            // 判断角色表中是否存在该角色
            for (Map<String, Object> map : roles) {
                if (id == Integer.parseInt(map.get("id").toString()) && 1 == Integer.parseInt(map.get("disabled").toString())) {
                    flag = true;
                    break;
                }
            }
            AssertUtil.isTrue(!flag, "所选中的角色不存在");
            for (UserRole u : rolesByUserId) {
                AssertUtil.isTrue(id.equals(u.getId()), "数据异常:新增用户的角色已存在");
            }
            // 执行添加用户角色操作
            // userId
            userRole.setUserId(user.getId());
            // roleId
            userRole.setRoleId(id);
            AssertUtil.isTrue(userRoleMapper.insertSelective(userRole) != 1, "新增用户角色失败");
        }
    }

    /**
     * 修改用户信息
     *
     * @param user
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void updateUser(User user, Integer[] ids) {
        // 校验参数
        // id是否存在
        AssertUtil.isTrue(user.getId() == null, "用户id不能为空");
        // userName不能为空
        AssertUtil.isTrue(StringUtils.isBlank(user.getUserName()), "用户名不能为空");
        // trueName不能为空
        AssertUtil.isTrue(StringUtils.isBlank(user.getTrueName()), "真实姓名不能为空");
        // email不能为空
        AssertUtil.isTrue(StringUtils.isBlank(user.getEmail()), "邮箱不能为空");
        // phone不能为空
        AssertUtil.isTrue(StringUtils.isBlank(user.getPhone()), "手机号码不能为空");
        // 校验手机号码格式
        AssertUtil.isTrue(!PhoneUtil.isMobile(user.getPhone()), "手机号码格式错误");
        // 角色不能为空
        AssertUtil.isTrue(ids == null && ids.length == 0, "用户角色不能为空");
        // 默认值
        // 修改时间
        user.setUpdateDate(new Date());
        // 是否有效
        user.setIsValid(1);
        User userByDataBase = userMapper.selectByPrimaryKey(user.getId());
        List<User> users = userMapper.findUsersByName(user.getUserName());
        // 修改的用户名是否存在判断
        AssertUtil.isTrue((users.size() > 0 && !user.getId().equals(users.get(0).getId())), "此用户名已被占用");
        // 校验所修改的用户是否存在于数据库中
        AssertUtil.isTrue(!user.getId().equals(userByDataBase.getId()), "所修改的用户不存在");
        // 密码不变
        user.setUserPwd(userByDataBase.getUserPwd());
        // 创建时间不变
        user.setCreateDate(userByDataBase.getCreateDate());
        // 执行修改操作
        AssertUtil.isTrue(userMapper.updateByPrimaryKeySelective(user) != 1, "修改用户失败");
        // 执行添加角色操作
        List<Map<String, Object>> roles = roleMapper.findRoles();
        AssertUtil.isTrue(roles.size() == 0, "角色表数据异常");
        List<UserRole> rolesByUserId = userRoleMapper.findUserRolesByUserId(user.getId());
        // userRole实体类对象
        UserRole userRole = new UserRole();
        // 修改时间
        userRole.setUpdateDate(new Date());
        // userId
        userRole.setUserId(user.getId());
        Map<Integer, String> map = uniqueMap(ids, rolesByUserId);
        System.out.println("map"+map);
        Set<Map.Entry<Integer, String>> entries = map.entrySet();
        Iterator<Map.Entry<Integer, String>> iterator = entries.iterator();
        while (iterator.hasNext()) {
            Map.Entry<Integer, String> entry = iterator.next();
            // roleId
            userRole.setRoleId(entry.getKey());
            switch (entry.getValue()){
                // 更新操作
                case "update":
                    // 创建时间，不修改
                    userRole.setCreateDate(null);
                    AssertUtil.isTrue(userRoleMapper.updateUserRole(userRole) != 1, "更新用户角色失败");
                    break;
                // 新增操作
                case "insert":
                    // 创建时间
                    userRole.setCreateDate(new Date());
                    AssertUtil.isTrue(userRoleMapper.insertSelective(userRole) != 1, "新增用户角色失败");
                    break;
                // 删除操作
                case "delete":
                    AssertUtil.isTrue(userRoleMapper.deleteUserRoleByIds(new Integer[]{user.getId()}, entry.getKey()) != 1, "删除用户角色失败");
                    break;
                default:
                    AssertUtil.isTrue(true, "系统错误");
                    break;
            }
        }
    }

    /**
     * 批量删除用户
     *
     * @param ids
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void deleteUsers(Integer[] ids) {
        // 校验参数
        AssertUtil.isTrue(ids == null || ids.length==0, "用户id不能为空");
        // 执行删除操作
        AssertUtil.isTrue(userMapper.deleteUsers(ids) < 1, "删除用户失败");
        // 删除用户所具有的角色
        AssertUtil.isTrue(userRoleMapper.deleteUserRoleByIds(ids, null) < 1, "删除用户角色失败");
    }

    /**
     * 处理角色多选更新操作
     * @param ids
     * @param rolesByUserId
     * @return
     */
    public static Map<Integer, String> uniqueMap(Integer[] ids,List<UserRole> rolesByUserId) {
        HashMap<Integer, String> map = new HashMap<>(16);
        // 将ids的中值存入map
        for (Integer id : ids) {
            map.put(id, "");
        }
        List<Integer> listForInput = Arrays.asList(ids);
        List<Integer> listForDataBase = new ArrayList<>(10);
        // 将role_id中的值也存入map
        for (UserRole userRole : rolesByUserId) {
            map.put(userRole.getRoleId(), "");
            listForDataBase.add(userRole.getRoleId());
        }
        // 比较两个对象的区别
        Set<Map.Entry<Integer, String>> entries = map.entrySet();
        Iterator<Map.Entry<Integer, String>> iterator = entries.iterator();
        while (iterator.hasNext()) {
            Map.Entry<Integer, String> entry = iterator.next();
            if (listForInput.contains(entry.getKey()) && listForDataBase.contains(entry.getKey())) {
                entry.setValue("update");
            } else if (listForInput.contains(entry.getKey())) {
                entry.setValue("insert");
            } else {
                entry.setValue("delete");
            }
        }
        return map;
    }

}
