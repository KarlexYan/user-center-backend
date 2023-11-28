package com.karlexyan.usercenterback.service;

import com.karlexyan.usercenterback.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

/*
* 用户服务测试
* @author karlexyan
* */
@SpringBootTest
class UserServiceTest {

    @Resource
    private UserService userService;

    @Test
    void testAddUser(){
        User user = new User();
        user.setUserName("KarlexYan2");
        user.setUserPassword("123456");
        user.setUserAge(23);
        boolean result = userService.save(user);
        System.out.println(user.getUserName());
        Assertions.assertTrue(result);
    }

    @Test
    void userRegister() {
        String account = "Karlex112";
        String password = "123456";
        String checkPassword = "123456";
        long result = userService.userRegister(account, password, checkPassword);
        Assertions.assertTrue(result > 0);
    }
}