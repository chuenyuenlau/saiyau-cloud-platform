package com.saiyau.auth.security.config;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.http.HttpStatus;
import cn.hutool.json.JSONUtil;
import com.saiyau.auth.security.domain.OauthUserDetails;
import com.saiyau.auth.security.service.impl.UserDetailsServiceImpl;
import com.saiyau.common.result.R;
import com.saiyau.common.result.ResultCode;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;
import org.springframework.security.web.AuthenticationEntryPoint;

import java.security.KeyPair;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author liuzhongyuan
 * @ClassName AuthorizationServerConfig.java
 * @Description 认证授权配置
 * @createTime 2021/10/19
 */
@Configuration
@EnableAuthorizationServer
@AllArgsConstructor
@Slf4j
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {
    private AuthenticationManager authenticationManager;
    private UserDetailsServiceImpl userDetailsService;

    /**
     * OAuth2客户端【数据库加载】
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        super.configure(clients);
    }

    /**
     * 配置授权（authorization）以及令牌（token）的访问端点和令牌服务(token services)
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
        List<TokenEnhancer> tokenEnhancers = new ArrayList<>();
        tokenEnhancers.add(tokenEnhancer());
        tokenEnhancers.add(jwtAccessTokenConverter());
        tokenEnhancerChain.setTokenEnhancers(tokenEnhancers);
        endpoints
                .authenticationManager(authenticationManager)
                .accessTokenConverter(jwtAccessTokenConverter())
                .tokenEnhancer(tokenEnhancerChain)
                .userDetailsService(userDetailsService)
                // refresh token有两种使用方式：重复使用(true)、非重复使用(false)，默认为true
                //      1 重复使用：access token过期刷新时， refresh token过期时间未改变，仍以初次生成的时间为准
                //      2 非重复使用：access token过期刷新时， refresh token过期时间延续，在refresh token有效期内刷新便永不失效达到无需再次登录的目的
                .reuseRefreshTokens(true);
    }

    /**
     * 使用非对称加密算法对token签名
     */
    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter() {
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        converter.setKeyPair(keyPair());
        return converter;
    }

    /**
     * 从classpath下的密钥库中获取密钥对(公钥+私钥)
     */
    @Bean
    public KeyPair keyPair() {
        KeyStoreKeyFactory factory = new KeyStoreKeyFactory(new ClassPathResource("jwt.jks"), "123456".toCharArray());
        KeyPair keyPair = factory.getKeyPair("jwt", "123456".toCharArray());
        return keyPair;
    }

    /**
     * JWT 内容增强
     * @return
     */
    @Bean
    public TokenEnhancer tokenEnhancer() {
        return (accessToken, authentication) -> {
            Map<String, Object> additionalInfo = CollectionUtil.newHashMap();
            OauthUserDetails oauthUserDetails = (OauthUserDetails) authentication.getUserAuthentication().getPrincipal();
            additionalInfo.put("userId", oauthUserDetails.getId());
            additionalInfo.put("username", oauthUserDetails.getUsername());
            ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(additionalInfo);
            return accessToken;
        };
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        // 用户不存在异常抛出
        provider.setHideUserNotFoundExceptions(false);
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    /**
     * 密码编码器
     * 委托方式，根据密码的前缀选择对应的encoder，例如：{bcypt}前缀 -> 标识BCYPT算法加密；{noop} -> 标识不使用任何加密，即明文的方式
     * 密码判断 DaoAuthenticationProvider#additionalAuthenticationChecks
     *
     * @return
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    /**
     * 自定义认证异常响应数据
     */
    @Bean
    public AuthenticationEntryPoint authenticationEntryPoint() {
        return (request, response, e) -> {
            response.setStatus(HttpStatus.HTTP_OK);
            response.setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
            response.setHeader("Access-Control-Allow-Origin", "*");
            response.setHeader("Cache-Control", "no-cache");
            R result = R.failed(ResultCode.CLIENT_AUTHENTICATION_FAILED);
            response.getWriter().print(JSONUtil.toJsonStr(result));
            response.getWriter().flush();
        };
    }
}
