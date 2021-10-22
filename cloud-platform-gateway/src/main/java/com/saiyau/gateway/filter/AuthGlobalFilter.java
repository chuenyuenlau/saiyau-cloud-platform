package com.saiyau.gateway.filter;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.nimbusds.jose.JWSObject;
import com.saiyau.common.constant.AuthConstants;
import com.saiyau.common.result.ResultCode;
import com.saiyau.gateway.config.IgnoreUrlsConfig;
import com.saiyau.gateway.util.ResponseUtils;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

/**
 * @author liuzhongyuan
 * @ClassName AuthGlobalFilter.java
 * @Description 登录用户的JWT信息转成用户信息的全局过滤器
 * @createTime 2021/10/21
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class AuthGlobalFilter implements GlobalFilter, Ordered {
    private final RedisTemplate<String, Object> redisTemplate;

    private final IgnoreUrlsConfig ignoreUrlsConfig;

    @SneakyThrows
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();

        String token = request.getHeaders().getFirst(AuthConstants.AUTHORIZATION_KEY);
        //非JWT或者JWT为空不作处理
        if (StrUtil.isBlank(token) || !token.startsWith(AuthConstants.AUTHORIZATION_PREFIX)) {
            return chain.filter(exchange);
        }
        token = token.replace(AuthConstants.AUTHORIZATION_KEY, "");
        JWSObject jwsObject = JWSObject.parse(token);
        String payload = jwsObject.getPayload().toString();
        JSONObject jsonObject = JSONUtil.parseObj(payload);
        String jti = jsonObject.getStr(AuthConstants.JWT_JTI);
        log.info("AuthGlobalFilter.filter user:{}", payload);
        Boolean isBlack = redisTemplate.hasKey(AuthConstants.TOKEN_BLACKLIST_PREFIX + jti);
        if (isBlack != null && isBlack) {
            return ResponseUtils.writeErrorInfo(response, ResultCode.TOKEN_ACCESS_FORBIDDEN);
        }
        // 存在token且不是黑名单，request写入JWT的载体信息
        request = exchange.getRequest()
                .mutate()
                .header(AuthConstants.JWT_PAYLOAD_KEY, URLEncoder.encode(payload, StandardCharsets.UTF_8.name()))
                .build();
        exchange = exchange.mutate().request(request).build();
        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
