package com.saiyau.common.constant;

/**
 * @author liuzhongyuan
 * @ClassName AuthConstants.java
 * @Description 认证鉴权相关常量
 * @createTime 2021/10/18
 */
public interface AuthConstants {

    /**
     * 认证请求头key
     */
    String AUTHORIZATION_KEY = "Authorization";

    /**
     * JWT令牌前缀
     */
    String AUTHORIZATION_PREFIX = "bearer ";

    /**
     * Basic认证前缀
     */
    String BASIC_PREFIX = "Basic ";

    /**
     * JWT载体key
     */
    String JWT_PAYLOAD_KEY = "payload";

    /**
     * JWT ID 唯一标识
     */
    String JWT_JTI = "jti";

    /**
     * JWT 过期标识
     */
    String JWT_EXP = "exp";

    /**
     * 黑名单token前缀
     */
    String TOKEN_BLACKLIST_PREFIX = "auth:token:blacklist:";

    String USER_ID_KEY = "userId";

    String USER_NAME_KEY = "username";

    String CLIENT_ID_KEY = "client_id";

    /**
     * JWT存储权限前缀
     */
    String AUTHORITY_PREFIX = "ROLE_";

    /**
     * 后台管理client_id
     */
    String ADMIN_CLIENT_ID = "admin-app";
    /**
     * 默认范围
     */
    String DEFAULT_CLIENT_SCOPE = "all";
    /**
     * JWT默认模式
     */
    String DEFAULT_AUTHORIZED_GRANT_TYPES = "password,authorization_code,refresh_token";
    /**
     * 默认客户端密钥
     */
    String DEFAULT_CLIENT_SECRET = "123456";
    /**
     * JWT token默认有效时间
     */
    Integer DEFAULT_ACCESS_TOKEN_VALIDITY_SECONDS = 3600;
    /**
     * JWT refresh_token默认有效时间
     */
    Integer DEFAULT_REFRESH_TOKEN_VALIDITY_SECONDS = 7200;

    /**
     * JWT存储权限属性
     */
    String JWT_AUTHORITIES_KEY = "authorities";

    String GRANT_TYPE_KEY = "grant_type";

    String REFRESH_TOKEN = "refresh_token";

    String APP_API_PATTERN = "/*/app-api/**";


}
