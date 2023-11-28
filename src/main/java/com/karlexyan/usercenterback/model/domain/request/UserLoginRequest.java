package com.karlexyan.usercenterback.model.domain.request;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserLoginRequest implements Serializable {


    private static final long serialVersionUID = -5702482205045063794L;

    private String userAccount;
    private String userPassword;
}
