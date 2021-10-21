package com.saiyau.common.result;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author liuzhongyuan
 * @ClassName R.java
 * @Description 通用返回对象
 * @createTime 2021/10/18
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class R<T> {
    private String code;
    private String message;
    private T data;

    public static <T> R<T> judge(boolean result) {
        if (result) {
            return success();
        } else {
            return failed();
        }
    }

    public static <T> R<T> success() {
        return new R<>(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMessage(), null);
    }

    public static <T> R<T> success(T data) {
        return new R<>(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMessage(), data);
    }

    public static <T> R<T> failed() {
        return new R<>(ResultCode.SYSTEM_EXECUTION_ERROR.getCode(), ResultCode.SYSTEM_EXECUTION_ERROR.getMessage(), null);
    }

    public static <T> R<T> failed(String message) {
        return new R<>(ResultCode.SYSTEM_EXECUTION_ERROR.getCode(), message, null);
    }

    public static <T> R<T> failed(IResultCode errorCode) {
        return new R<>(errorCode.getCode(), errorCode.getMessage(), null);
    }

    public static <T> R<T> failed(IResultCode resultCode, String msg) {
        return new R<>(resultCode.getCode(), msg, null);
    }

    public static boolean isSuccess(R<?> result) {
        return result != null && ResultCode.SUCCESS.getCode().equals(result.getCode());
    }
}
