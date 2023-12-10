package com.karlexyan.usercenterback.common;


/**
 * 返回工具类
 */
public class ResultUtils {

    /**
     * 成功
     * @param data 数据
     * @return 通用返回类
     * @param <T> 数据泛型
     */
    public static <T> BaseResponse<T> success (T data){
        return new BaseResponse<>(0000, data, "ok");
    }

    /**
     * 失败
     *
     * @param errorCode 枚举类
     * @return 通用返回类
     */
    public static BaseResponse error(ErrorCode errorCode) {
        return new BaseResponse(errorCode);
    }

    public static BaseResponse error(ErrorCode errorCode, String message, String description) {
        return new BaseResponse(errorCode.getCode(), null, message, description);
    }

    public static BaseResponse error(ErrorCode errorCode, String description) {
        return new BaseResponse(errorCode.getCode(), null, errorCode.getMsg(), description);
    }

    public static BaseResponse error(Integer code, String message, String description) {
        return new BaseResponse(code, null, message, description);
    }

}
