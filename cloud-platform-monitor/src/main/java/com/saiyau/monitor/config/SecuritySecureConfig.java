package com.saiyau.monitor.config;

import de.codecentric.boot.admin.server.config.AdminServerProperties;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

/**
 * @author liuzhongyuan
 * @ClassName SecuritySecureConfig.java
 * @Description TODO
 * @createTime 2021/10/15
 */
public class SecuritySecureConfig extends WebSecurityConfigurerAdapter {
    private final String ADMIN_CONTEXT_PATH;

    public SecuritySecureConfig(AdminServerProperties adminServerProperties) {
        this.ADMIN_CONTEXT_PATH = adminServerProperties.getContextPath();
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        SavedRequestAwareAuthenticationSuccessHandler successHandler = new SavedRequestAwareAuthenticationSuccessHandler();
        successHandler.setTargetUrlParameter("redirectTo");
        successHandler.setDefaultTargetUrl(ADMIN_CONTEXT_PATH + "/");

        httpSecurity.authorizeRequests()
        //1.配置所有静态资源和登录页可以公开访问
                .antMatchers(ADMIN_CONTEXT_PATH + "/assets/**").permitAll()
                .antMatchers(ADMIN_CONTEXT_PATH + "/login").permitAll()
                .anyRequest().authenticated()
                .and()
                //2.配置登录和登出路径
                .formLogin().loginPage(ADMIN_CONTEXT_PATH + "/login").successHandler(successHandler).and()
                .logout().logoutUrl(ADMIN_CONTEXT_PATH + "/logout").and()
                //3.开启http basic支持，admin-client注册时需要使用
                .httpBasic().and()
                .csrf()
                //4.开启基于cookie的csrf保护
                .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                //5.忽略这些路径的csrf保护以便admin-client注册
                .ignoringAntMatchers(
                        ADMIN_CONTEXT_PATH + "/instances",
                        ADMIN_CONTEXT_PATH + "/actuator/**"
                );
    }
}
