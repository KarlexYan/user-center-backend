package com.karlexyan.usercenterback.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.karlexyan.usercenterback.model.User;
import com.karlexyan.usercenterback.service.UserService;
import com.karlexyan.usercenterback.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static com.karlexyan.usercenterback.constant.UserConstant.USER_LOGIN_STATUS;

/**
 * @author KarlexYan
 * @description 针对表【user(用户表)】的数据库操作Service实现
 * @createDate 2023-11-18 19:08:42
 */
@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
        implements UserService {

    @Resource
    UserMapper userMapper;


    // 盐值 用作密码混淆
    public static final String SALT = "ky";


    @Override
    public long userRegister(String userAccount, String userPassword, String checkPassword) {
        // 1.校验
        // 非空
        if (StringUtils.isAnyBlank(userAccount, userPassword, checkPassword)) {
            return -1;
        }
        // 账号不少于4位
        if (userAccount.length() < 4) {
            return -1;
        }
        // 密码不少于6位
        if (userPassword.length() < 6 || checkPassword.length() < 6) {
            return -1;
        }

        // 账户不包含特殊字符 使用正则表达式校验
        String regex = "^\\w+$";
        Pattern pattern = Pattern.compile(regex);
        boolean flag = pattern.matcher(userAccount).matches();
        if (!flag) {
            return -1;
        }

        // 校验密码和密码相同
        if (!userPassword.equals(checkPassword)) {
            return -1;
        }

        // 账户不能重复注册
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_account", userAccount);
        long count = userMapper.selectCount(queryWrapper);
        if (count > 0) {
            return -1;
        }

        // 2.加密  (通过加盐加密)
        String encryptPassword = DigestUtils.md5DigestAsHex((SALT + userPassword).getBytes(StandardCharsets.UTF_8));

        // 3.插入数据
        User user = new User();
        user.setUserAccount(userAccount);
        user.setUserPassword(encryptPassword);
        boolean result = this.save(user);
        if (!result) {
            return -1;
        }

        return user.getUserId();
    }

    @Override
    public User userLogin(String userAccount, String userPassword, HttpServletRequest request) {
        // 1.校验
        // 非空
        if (StringUtils.isAnyBlank(userAccount, userPassword)) {
            return null;
        }
        // 账号不少于4位
        if (userAccount.length() < 4) {
            return null;
        }
        // 密码不少于6位
        if (userPassword.length() < 6) {
            return null;
        }

        // 账户不包含特殊字符 使用正则表达式校验
        String regex = "^\\w+$";
        Pattern pattern = Pattern.compile(regex);
        boolean flag = pattern.matcher(userAccount).matches();
        if (!flag) {
            return null;
        }

        // 2.加密  (通过加盐加密)
        String encryptPassword = DigestUtils.md5DigestAsHex((SALT + userPassword).getBytes(StandardCharsets.UTF_8));

        // 3.查询用户和密码是否存在
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_account", userAccount);
        queryWrapper.eq("user_password", encryptPassword);
        User user = userMapper.selectOne(queryWrapper);
        // 用户不存在
        if (user == null) {
            log.info("user login failed. userAccount cannot match userPassword");
            return null;
        }

        // 4.脱敏
        User safetyUser = getSafetyUser(user);

        // 5.记录用户登录态，存在服务器上
        request.getSession().setAttribute(USER_LOGIN_STATUS, safetyUser);

        // 6.返回脱敏后的用户信息
        return safetyUser;
    }

    @Override
    public List<User> searchUser(String username, HttpServletRequest request) {

        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        if (StringUtils.isNoneBlank(username)) {
            queryWrapper.like("user_name", username);
        }
        List<User> userList = userMapper.selectList(queryWrapper);
        // 过滤，删除敏感信息后返回
        return userList.stream().map(this::getSafetyUser).collect(Collectors.toList());
    }

    @Override
    public boolean deleteById(long id) {
        int count = userMapper.deleteById(id);
        return count > 0;
    }

    @Override
    public User getSafetyUser(User originUser) {
        if(originUser == null){
            return null;
        }
        User safetyUser = new User();
        safetyUser.setUserId(originUser.getUserId());
        safetyUser.setUserName(originUser.getUserName());
        safetyUser.setUserAccount(originUser.getUserAccount());
        safetyUser.setAvatarUrl(originUser.getAvatarUrl());
        safetyUser.setUserGender(originUser.getUserGender());
        safetyUser.setUserAge(originUser.getUserAge());
        safetyUser.setTelephone(originUser.getTelephone());
        safetyUser.setEmail(originUser.getEmail());
        safetyUser.setUserStatus(0);
        safetyUser.setCreatedTime(originUser.getCreatedTime());
        safetyUser.setUserRole(originUser.getUserRole());
        return safetyUser;
    }

    @Override
    public User getCurrentUser(long id) {
        User user = userMapper.selectById(id);
        if(user==null){
            return null;
        }
        return getSafetyUser(user);
    }

    @Override
    public int userOutLogin(HttpServletRequest request) {
        request.getSession().removeAttribute(USER_LOGIN_STATUS);
        return 1;
    }
}




