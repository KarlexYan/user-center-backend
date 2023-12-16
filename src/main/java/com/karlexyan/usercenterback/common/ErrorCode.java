package com.karlexyan.usercenterback.common;

/**
 * 错误码
 */
public enum ErrorCode {

    PARAMS_ERROR(40000, "请求参数错误", ""),
    NULL_ERROR(40001, "请求数据为空", ""),
    NOT_LOGIN(40100, "未登录", ""),
    NO_AUTH(40101, "无权限", ""),
    USER_ACCOUNT_EXIST(40102, "用户已存在", ""),
    USER_REGISTER_FAIL(40103, "注册失败", ""),
    USER_NOT_EXISTS(40104, "用户不存在", ""),
    SYSTEM_ERROR(50000, "系统内部异常，请联系管理员反馈", "");


    /**
     * 状态码
     */
    private final int code;
    /**
     * 状态码信息
     */
    private final String message;
    /**
     * 状态码信息详细
     */
    private final String description;

    ErrorCode(int code, String message, String description) {
        this.code = code;
        this.message = message;
        this.description = description;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public String getDescription() {
        return description;
    }
}
