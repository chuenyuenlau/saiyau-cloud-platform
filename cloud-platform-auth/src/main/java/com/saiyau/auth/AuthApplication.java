package com.saiyau.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author liuzhongyuan
 * @ClassName AuthApplication.java
 * @Description 认证中心
 * @createTime 2021/10/19
 */
@EnableDiscoveryClient
@EnableFeignClients(basePackages = {"com.saiyau.admin.api"})
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class AuthApplication {
    public static void main(String[] args) {
        SpringApplication.run(AuthApplication.class, args);
    }
}
