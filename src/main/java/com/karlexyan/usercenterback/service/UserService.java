package com.karlexyan.usercenterback.service;

import com.karlexyan.usercenterback.model.User;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
* @author KarlexYan
* @description 针对表【user(用户表)】的数据库操作Service
* @createDate 2023-11-18 19:08:42
*/
public interface UserService extends IService<User> {
    /**
     * 用户注册
     *
     * @param userAccount   用户账号
     * @param userPassword  用户密码
     * @param checkPassword 校验密码
     * @return 用户id
     */
    long userRegister(String userAccount, String userPassword, String checkPassword);

    /**
     * 用户登录
     *
     * @param userAccount  用户账号
     * @param userPassword 用户密码
     * @param request      请求
     * @return 脱敏后的用户对象
     */
    User userLogin(String userAccount, String userPassword, HttpServletRequest request);

    /**
     * 用户搜索
     *
     * @param username 用户名称
     * @return 用户列表
     */
    List<User> searchUser(String username, HttpServletRequest request);

    /**
     * 用户删除
     *
     * @param id 用户id
     * @return 是否删除成功
     */
    boolean deleteById(long id);

    /**
     * 用户脱敏
     * @param originUser 原始用户
     * @return 脱敏用户
     */
    User getSafetyUser(User originUser);

    /**
     * 获取用户登录信息
     * @param id 用户id
     * @return 脱敏用户
     */
    User getCurrentUser(long id);

    /**
     * 用户注销
     * @param request 请求
     * @return int
     */
    int userOutLogin(HttpServletRequest request);
}
