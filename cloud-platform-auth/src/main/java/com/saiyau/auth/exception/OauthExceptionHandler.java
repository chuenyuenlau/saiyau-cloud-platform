package com.saiyau.auth.exception;

import com.saiyau.common.result.R;
import com.saiyau.common.result.ResultCode;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.common.exceptions.InvalidGrantException;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * @author liuzhongyuan
 * @ClassName OauthExceptionHandler.java
 * @Description Oauth2认证异常处理
 * @createTime 2021/10/20
 */
public class OauthExceptionHandler {
    /**
     * 用户不存在
     *
     * @param e
     * @return
     */
    @ExceptionHandler(UsernameNotFoundException.class)
    public R handleUsernameNotFoundException(UsernameNotFoundException e) {
        return R.failed(ResultCode.USER_NOT_EXIST);
    }

    /**
     * 用户名和密码异常
     *
     * @param e
     * @return
     */
    @ExceptionHandler(InvalidGrantException.class)
    public R handleInvalidGrantException(InvalidGrantException e) {
        return R.failed(ResultCode.USERNAME_OR_PASSWORD_ERROR);
    }


    /**
     * 账户异常(禁用、锁定、过期)
     *
     * @param e
     * @return
     */
    @ExceptionHandler({InternalAuthenticationServiceException.class})
    public R handleInternalAuthenticationServiceException(InternalAuthenticationServiceException e) {
        return R.failed(e.getMessage());
    }
}
