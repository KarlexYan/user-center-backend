package com.karlexyan.usercenterback.model.domain.request;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserRegisterRequest implements Serializable {
    private static final long serialVersionUID = -6942803334049088256L;

    private String userAccount;
    private String userPassword;
    private String checkPassword;
}
