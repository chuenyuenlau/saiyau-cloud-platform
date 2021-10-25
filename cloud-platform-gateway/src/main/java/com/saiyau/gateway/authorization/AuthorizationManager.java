package com.saiyau.gateway.authorization;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.StrUtil;
import com.saiyau.common.constant.AuthConstants;
import com.saiyau.common.constant.GlobalConstants;
import com.saiyau.gateway.config.IgnoreUrlsConfig;
import lombok.AllArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.ReactiveAuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.server.authorization.AuthorizationContext;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author liuzhongyuan
 * @ClassName AuthorizationManager.java
 * @Description 鉴权管理器，用于判断是否有权限访问资源
 * @createTime 2021/10/21
 */
@Component
@AllArgsConstructor
public class AuthorizationManager implements ReactiveAuthorizationManager<AuthorizationContext> {
    private final RedisTemplate<String, Object> redisTemplate;
    private final IgnoreUrlsConfig ignoreUrlsConfig;


    @Override
    public Mono<AuthorizationDecision> check(Mono<Authentication> mono, AuthorizationContext authorizationContext) {
        ServerHttpRequest request = authorizationContext.getExchange().getRequest();
        URI uri = request.getURI();
        AntPathMatcher pathMatcher = new AntPathMatcher();
        String method = request.getMethodValue();
        String path = uri.getPath();
        //RESTful接口权限设计，例如：GET:/api/v1/users/addUser
        String restfulPath = method + ":" + path;
        //白名单路径直接放行
        List<String> ignoreUrls = ignoreUrlsConfig.getUrls();
        for (String ignoreUrl : ignoreUrls) {
            if (pathMatcher.match(ignoreUrl, uri.getPath())) {
                return Mono.just(new AuthorizationDecision(true));
            }
        }
        //对应跨域的预检请求直接放行
        if (HttpMethod.OPTIONS.equals(request.getMethod())) {
            return Mono.just(new AuthorizationDecision(true));
        }
        //请求头获取token字符
        String token = request.getHeaders().getFirst(AuthConstants.AUTHORIZATION_KEY);
        if (StrUtil.isBlank(token) || !token.startsWith(AuthConstants.AUTHORIZATION_PREFIX)) {
            return Mono.just(new AuthorizationDecision(true));
        }
        /* 鉴权校验 */
        //缓存获取规则数据 [URL权限-角色集合]，例如：urlPermRolesRules = [{'key':'GET:/user/**','value':['ADMIN','GUEST']}, ...]
        Map<Object, Object> urlPermRolesRules = redisTemplate.opsForHash().entries(GlobalConstants.URL_PERM_ROLES_KEY);
        //根据请求路径判断有访问权限的角色列表
        List<String> authorizedRoles = new ArrayList<>();
        boolean isRequireCheck = false;
        for (Map.Entry<Object, Object> permRoles : urlPermRolesRules.entrySet()) {
            String perm = (String) permRoles.getKey();
            if (pathMatcher.match(perm, restfulPath)) {
                authorizedRoles.addAll(Convert.toList(String.class, permRoles.getValue()));
                if (!isRequireCheck) {
                    isRequireCheck = true;
                }
            }
        }
        if (!isRequireCheck) {
            return Mono.just(new AuthorizationDecision(true));
        }
        Set<String> authorizedRolesSet = authorizedRoles.stream()
                .map(i -> i = AuthConstants.AUTHORITY_PREFIX + i)
                .collect(Collectors.toSet());
        //认证通过且角色匹配的用户可访问当前路径
        return mono.filter(Authentication::isAuthenticated)
                .flatMapIterable(Authentication::getAuthorities)
                .map(GrantedAuthority::getAuthority)
                .any(authority -> {
                    //用户的角色
                    String roleCode = authority.substring(AuthConstants.AUTHORITY_PREFIX.length());
                    if (GlobalConstants.ROOT_ROLE_CODE.equals(roleCode)) {
                        //如果是超级管理员则放行
                        return true;
                    }
                    return CollectionUtil.isNotEmpty(authorizedRoles) && authorizedRolesSet.contains(roleCode);
                })
                .map(AuthorizationDecision::new)
                .defaultIfEmpty(new AuthorizationDecision(false));
    }
}
