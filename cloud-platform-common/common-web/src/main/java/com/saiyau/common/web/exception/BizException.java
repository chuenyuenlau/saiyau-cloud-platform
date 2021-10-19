package com.saiyau.common.web.exception;

import com.saiyau.common.result.IResultCode;
import lombok.Getter;

/**
 * @author liuzhongyuan
 * @ClassName BizException.java
 * @Description 自定义执行异常类
 * @createTime 2021/10/18
 */
@Getter
public class BizException extends RuntimeException {

    public IResultCode resultCode;

    public BizException(IResultCode errorCode) {
        super(errorCode.getMessage());
        this.resultCode = errorCode;
    }

    public BizException(String message){
        super(message);
    }

    public BizException(String message, Throwable cause){
        super(message, cause);
    }

    public BizException(Throwable cause){
        super(cause);
    }
}
