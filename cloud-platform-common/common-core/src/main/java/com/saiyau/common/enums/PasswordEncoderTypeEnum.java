package com.saiyau.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author liuzhongyuan
 * @ClassName PasswordEncoderTypeEnum.java
 * @Description 密码编码类型枚举
 * @createTime 2021/10/21
 */
@AllArgsConstructor
@Getter
public enum PasswordEncoderTypeEnum {
    BCRYPT("{bcrypt}", "BCRYPT加密"),
    NOOP("{noop}", "无加密明文");

    /**
     * 前缀
     */
    String prefix;

    /**
     * 描述
     */
    String desc;
}
