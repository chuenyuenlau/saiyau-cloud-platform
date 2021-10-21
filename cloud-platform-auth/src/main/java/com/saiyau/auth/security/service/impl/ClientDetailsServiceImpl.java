package com.saiyau.auth.security.service.impl;

import com.saiyau.admin.api.OauthClientFeignClient;
import com.saiyau.admin.pojo.entity.OauthClientDetails;
import com.saiyau.common.constant.AuthConstants;
import com.saiyau.common.enums.PasswordEncoderTypeEnum;
import com.saiyau.common.result.R;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.security.oauth2.provider.NoSuchClientException;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;
import org.springframework.stereotype.Service;

/**
 * @author liuzhongyuan
 * @ClassName ClientDetailsServiceImpl.java
 * @Description 从数据库获取Oauth2客户端信息，对客户端信息进行扩展和处理
 * @createTime 2021/10/21
 */
@Service
@AllArgsConstructor
@Slf4j
public class ClientDetailsServiceImpl implements ClientDetailsService {
    private final OauthClientFeignClient oauthClientFeignClient;

    @Override
    public ClientDetails loadClientByClientId(String clientId) throws ClientRegistrationException {
        try {
            R<OauthClientDetails> result = oauthClientFeignClient.getOauthClientById(clientId);
            if (R.isSuccess(result)) {
                OauthClientDetails client = result.getData();
                BaseClientDetails clientDetails;
                if (client == null) {
                    clientDetails = new BaseClientDetails(
                            AuthConstants.ADMIN_CLIENT_ID,
                            null,
                            AuthConstants.DEFAULT_CLIENT_SCOPE,
                            AuthConstants.DEFAULT_AUTHORIZED_GRANT_TYPES,
                            null,
                            null
                    );
                    clientDetails.setClientId(PasswordEncoderTypeEnum.NOOP.getPrefix() + AuthConstants.DEFAULT_CLIENT_SECRET);
                    clientDetails.setAccessTokenValiditySeconds(AuthConstants.DEFAULT_ACCESS_TOKEN_VALIDITY_SECONDS);
                    clientDetails.setRefreshTokenValiditySeconds(AuthConstants.DEFAULT_REFRESH_TOKEN_VALIDITY_SECONDS);
                } else {
                    clientDetails = new BaseClientDetails(
                            client.getClientId(),
                            client.getResourceIds(),
                            client.getScope(),
                            client.getAuthorizedGrantTypes(),
                            client.getAuthorities(),
                            client.getWebServerRedirectUri()
                    );
                    clientDetails.setClientSecret(PasswordEncoderTypeEnum.NOOP.getPrefix() + client.getClientSecret());
                    clientDetails.setAccessTokenValiditySeconds(client.getAccessTokenValidity());
                    clientDetails.setRefreshTokenValiditySeconds(client.getRefreshTokenValidity());
                }
                return clientDetails;
            } else {
                throw new NoSuchClientException("No client with requested id: " + clientId);
            }
        } catch (EmptyResultDataAccessException e) {
            throw new NoSuchClientException("No client with requested id: " + clientId);
        }
    }
}
