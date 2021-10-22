package com.saiyau.gateway.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author liuzhongyuan
 * @ClassName IgnoreUrlsConfig.java
 * @Description 白名单url配置类
 * @createTime 2021/10/21
 */
@Component
@ConfigurationProperties(prefix = "secure.ignore")
@Data
public class IgnoreUrlsConfig {
    /**
     * 白名单url列表
     */
    private List<String> urls;
}
