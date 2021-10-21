package com.saiyau.auth.constant;

/**
 * @author liuzhongyuan
 * @ClassName MessageConstants.java
 * @Description 消息常量类
 * @createTime 2021/10/20
 */
public interface MessageConstants {
    String USER_NOT_EXIST = "用户不存在";
    String USERNAME_PASSWORD_ERROR = "用户名或密码错误";
    String CREDENTIALS_EXPIRED = "该账号的登录凭证已过期，请重新登录";
    String ACCOUNT_DISABLED = "该账号已被禁用，请联系管理员";
    String ACCOUNT_LOCKED = "该账号已被锁定，请联系管理员";
    String ACCOUNT_EXPIRED = "该账号已过期，请联系管理员";
    String PERMISSION_DENIED = "没有访问权限，请联系管理员";
}
