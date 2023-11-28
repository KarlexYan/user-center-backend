package com.karlexyan.usercenterback.model.domain.request;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserDeleteRequest implements Serializable {

    private static final long serialVersionUID = -8726061591119100102L;

    private long userId;
}
