package com.saiyau.auth.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author liuzhongyuan
 * @ClassName OauthClientEnum.java
 * @Description oauth客户端枚举常量
 * @createTime 2021/10/22
 */
@AllArgsConstructor
@Getter
public enum OauthClientEnum {
    TEST("test", "测试专用客户端id"),
    ADMIN("cloud-platform-admin", "系统管理端");

    /**
     * 客户端id
     */
    String clientId;
    /**
     * 描述
     */
    String desc;

    public static OauthClientEnum getByClientId(String clientId) {
        for (OauthClientEnum client : OauthClientEnum.values()) {
            if (client.getClientId().equals(clientId)) {
                return client;
            }
        }
        return null;
    }
}
