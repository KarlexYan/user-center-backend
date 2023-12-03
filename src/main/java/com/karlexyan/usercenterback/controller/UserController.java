package com.karlexyan.usercenterback.controller;

import com.karlexyan.usercenterback.common.BaseResponse;
import com.karlexyan.usercenterback.common.ErrorCode;
import com.karlexyan.usercenterback.common.ResultUtils;
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
    public BaseResponse<Long> userRegister(@RequestBody UserRegisterRequest userRegisterRequest){
        if(userRegisterRequest == null){
            return ResultUtils.error(ErrorCode.NULL_ERROR);
        }
        String userAccount = userRegisterRequest.getUserAccount();
        String userPassword = userRegisterRequest.getUserPassword();
        String checkPassword = userRegisterRequest.getCheckPassword();
        if(StringUtils.isAnyBlank(userAccount,userPassword,checkPassword)){
            return ResultUtils.error(ErrorCode.NULL_ERROR);
        }

        long result = userService.userRegister(userAccount, userPassword, checkPassword);
        return ResultUtils.success(result);
    }

    @PostMapping("/login")
    public BaseResponse<User> userLogin(@RequestBody UserLoginRequest userLoginRequest, HttpServletRequest request){
        if(userLoginRequest == null){
            return ResultUtils.error(ErrorCode.NULL_ERROR);
        }
        String userAccount = userLoginRequest.getUserAccount();
        String userPassword = userLoginRequest.getUserPassword();
        if(StringUtils.isAnyBlank(userAccount,userPassword)){
            return ResultUtils.error(ErrorCode.NULL_ERROR);
        }

        User user = userService.userLogin(userAccount, userPassword, request);
        return ResultUtils.success(user);
    }

    @PostMapping("/outLogin")
    public BaseResponse<Integer> userOutLogin(HttpServletRequest request){
        if(request==null){
            return ResultUtils.error(ErrorCode.NULL_ERROR);
        }
        int result = userService.userOutLogin(request);
        return ResultUtils.success(result);
    }

    @GetMapping("/currentUser")
    public BaseResponse<User> getCurrentUser(HttpServletRequest request){
        Object userObj = request.getSession().getAttribute(USER_LOGIN_STATUS);
        User currentUser = (User) userObj;
        if(currentUser==null){
            return ResultUtils.error(ErrorCode.NULL_ERROR);
        }
        Long userId = currentUser.getUserId();
        User user = userService.getCurrentUser(userId);
        return ResultUtils.success(user);
    }

    @GetMapping("/search")
    public BaseResponse<List<User>> searchUsers(String userName, HttpServletRequest request){
        // 仅管理员可查询
        if(!this.isAdmin(request)){
            return ResultUtils.error(ErrorCode.NO_AUTH);
        }

        List<User> users = userService.searchUser(userName, request);

        return ResultUtils.success(users);
    }

    @PostMapping("/delete")
    public BaseResponse<Boolean> deleteUser(@RequestBody UserDeleteRequest userDeleteRequest, HttpServletRequest request){
        // 仅管理员可查询
        if(!this.isAdmin(request)){
            return ResultUtils.error(ErrorCode.NO_AUTH);
        }

        Long userId = userDeleteRequest.getUserId();

        if(userId <= 0){
            return ResultUtils.error(ErrorCode.PARAMS_ERROR);
        }
        boolean result = userService.deleteById(userId);

        return ResultUtils.success(result);
    }

    /**
     * 校验是否为管理员
     * @param request 请求
     * @return bool
     */
    public boolean isAdmin(HttpServletRequest request){
        Object userObj = request.getSession().getAttribute(USER_LOGIN_STATUS);
        User user = (User) userObj;
        return user != null && user.getUserRole() .equals(ADMIN_USER) ;
    }
}
