package com.saiyau.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * @author liuzhongyuan
 * @ClassName GatewayApplication.java
 * @Description 网关应用主启动类
 * @createTime 2021/10/21
 */
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class GatewayApplication {
    public static void main(String[] args) {
        SpringApplication.run(GatewayApplication.class, args);
    }
}
