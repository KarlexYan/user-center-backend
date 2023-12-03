package com.karlexyan.usercenterback.common;

import lombok.Data;

import java.io.Serializable;

/**
 * 通用返回类
 * @param <T>
 */
@Data
public class BaseResponse<T> implements Serializable {
    private int code;
    private String msg;
    private T data;
    private String desc;

    public BaseResponse(int code,  T data,String msg,String desc) {
        this.code = code;
        this.msg = msg;
        this.data = data;
        this.desc = desc;
    }

    public BaseResponse(int code, T data,String msg) {
        this.code = code;
        this.data = data;
        this.msg = msg;
    }

    public BaseResponse(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public BaseResponse(ErrorCode errorCode){
        this(errorCode.getCode(),null,errorCode.getMsg(),errorCode.getDesc());
    }
}
