package com.karlexyan.usercenterback.common;

/**
 * 错误码
 */
public enum ErrorCode {

    PARAMS_ERROR(40000,"请求参数错误",""),
    NULL_ERROR(40001,"请求数据为空",""),
    NOT_LOGIN(40100,"未登录",""),
    NO_AUTH(40101,"无权限","");


    /**
     * 状态码
     */
    private final int code;
    /**
     * 状态码信息
     */
    private final String msg;
    /**
     * 状态码信息详细
     */
    private final String desc;

    ErrorCode(int code, String msg, String desc) {
        this.code = code;
        this.msg = msg;
        this.desc = desc;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public String getDesc() {
        return desc;
    }
}
