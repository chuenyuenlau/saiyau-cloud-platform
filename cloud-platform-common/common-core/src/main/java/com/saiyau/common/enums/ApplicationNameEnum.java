package com.saiyau.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author liuzhongyuan
 * @ClassName ApplicationNameEnum.java
 * @Description 微服务应用名称枚举常量
 * @createTime 2021/10/21
 */
@AllArgsConstructor
@Getter
public enum ApplicationNameEnum {
    ADMIN("cloud-platform-admin", "系统管理"),
    AUTH("cloud-platform-auth", "认证中心"),
    MONITOR("cloud-platform-monitor", "监控中心");

    /**
     * 微服务应用名
     */
    String name;
    /**
     * 微服务应用描述
     */
    String desc;

    public static ApplicationNameEnum match(String name) {
        ApplicationNameEnum[] values = ApplicationNameEnum.values();
        for (ApplicationNameEnum value : values) {
            if (value.name.equals(name)) {
                return value;
            }
        }
        return null;
    }
}
