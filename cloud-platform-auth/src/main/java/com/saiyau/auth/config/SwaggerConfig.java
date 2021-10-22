package com.saiyau.auth.config;

import com.saiyau.swagger.config.BaseSwaggerConfig;
import com.saiyau.swagger.config.SwaggerProperties;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.swagger2.annotations.EnableSwagger2WebMvc;

/**
 * @author liuzhongyuan
 * @ClassName SwaggerConfig.java
 * @Description TODO
 * @createTime 2021/10/22
 */
@Configuration
@EnableSwagger2WebMvc
public class SwaggerConfig extends BaseSwaggerConfig {
    @Override
    public SwaggerProperties swaggerProperties() {
        return SwaggerProperties.builder()
                .title("认证中心")
                .groupName("认证中心")
                .apiBasePackage("com.saiyau.auth.controller")
                .description("<div style='font-size:14px;color:red'>认证中心接口</div>")
                .version("1.0.0")
                .enableSecurity(true)
                .build();
    }
}
