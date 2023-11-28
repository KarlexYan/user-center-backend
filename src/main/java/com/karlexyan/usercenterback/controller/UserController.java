package com.karlexyan.usercenterback.controller;

import com.karlexyan.usercenterback.model.User;
import com.karlexyan.usercenterback.model.domain.request.UserDeleteRequest;
import com.karlexyan.usercenterback.model.domain.request.UserLoginRequest;
import com.karlexyan.usercenterback.model.domain.request.UserRegisterRequest;
import com.karlexyan.usercenterback.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

import static com.karlexyan.usercenterback.constant.UserConstant.ADMIN_USER;
import static com.karlexyan.usercenterback.constant.UserConstant.USER_LOGIN_STATUS;

@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService userService;

    @PostMapping("/register")
    public Long userRegister(@RequestBody UserRegisterRequest userRegisterRequest){
        if(userRegisterRequest == null){
            return null;
        }
        String userAccount = userRegisterRequest.getUserAccount();
        String userPassword = userRegisterRequest.getUserPassword();
        String checkPassword = userRegisterRequest.getCheckPassword();
        if(StringUtils.isAnyBlank(userAccount,userPassword,checkPassword)){
            return null;
        }
        return userService.userRegister(userAccount,userPassword,checkPassword);
    }

    @PostMapping("/login")
    public User userLogin(@RequestBody UserLoginRequest userLoginRequest, HttpServletRequest request){
        if(userLoginRequest == null){
            return null;
        }
        String userAccount = userLoginRequest.getUserAccount();
        String userPassword = userLoginRequest.getUserPassword();
        if(StringUtils.isAnyBlank(userAccount,userPassword)){
            return null;
        }
        return userService.userLogin(userAccount, userPassword,request);
    }

    @PostMapping("/outLogin")
    public Integer userOutLogin(HttpServletRequest request){
        if(request==null){
            return null;
        }
        return userService.userOutLogin(request);
    }

    @GetMapping("/currentUser")
    public User getCurrentUser(HttpServletRequest request){
        Object userObj = request.getSession().getAttribute(USER_LOGIN_STATUS);
        User currentUser = (User) userObj;
        if(currentUser==null){
            return null;
        }
        Long userId = currentUser.getUserId();
        return userService.getCurrentUser(userId);
    }

    @GetMapping("/search")
    public List<User> searchUsers(String userName, HttpServletRequest request){
        // 仅管理员可查询
        if(!this.isAdmin(request)){
            return new ArrayList<>();
        }

        return userService.searchUser(userName,request);
    }

    @PostMapping("/delete")
    public boolean deleteUser(@RequestBody UserDeleteRequest userDeleteRequest, HttpServletRequest request){
        // 仅管理员可查询
        if(!this.isAdmin(request)){
            return false;
        }

        Long userId = userDeleteRequest.getUserId();

        if(userId <= 0){
            return false;
        }
        return userService.deleteById(userId);
    }

    /**
     * 校验是否为管理员
     * @param request 请求
     * @return bool
     */
    public boolean isAdmin(HttpServletRequest request){
        Object userObj = request.getSession().getAttribute(USER_LOGIN_STATUS);
        User user = (User) userObj;
        return user != null && user.getUserRole() == ADMIN_USER;
    }
}
