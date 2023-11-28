package com.karlexyan.usercenterback.model.domain.request;

import lombok.Data;

import java.io.Serializable;

@Data
public class  UserSearchRequest implements Serializable {


    private static final long serialVersionUID = 681262377271975979L;
    private String userName;
}
