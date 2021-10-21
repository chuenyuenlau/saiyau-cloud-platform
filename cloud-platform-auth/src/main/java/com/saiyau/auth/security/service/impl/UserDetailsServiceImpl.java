package com.saiyau.auth.security.service.impl;

import com.saiyau.admin.api.UserFeignClient;
import com.saiyau.admin.pojo.entity.SysUser;
import com.saiyau.auth.constant.MessageConstants;
import com.saiyau.auth.security.domain.OauthUserDetails;
import com.saiyau.common.result.R;
import com.saiyau.common.result.ResultCode;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.security.auth.login.AccountExpiredException;
import javax.security.auth.login.CredentialExpiredException;

/**
 * @author liuzhongyuan
 * @ClassName UserDetailsServiceImpl.java
 * @Description 从数据库获取认证用户信息，抵用互信息进行扩展和处理
 * @createTime 2021/10/19
 */
@Service
@AllArgsConstructor
@Slf4j
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserFeignClient userFeignClient;

    @SneakyThrows
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        R<SysUser> result = userFeignClient.getUserByUsername(username);
        OauthUserDetails oauthUserDetails = null;
        if (R.isSuccess(result)) {
            SysUser sysUser = result.getData();
            oauthUserDetails = new OauthUserDetails(sysUser);
        }
        if (oauthUserDetails == null) {
            throw new UsernameNotFoundException(MessageConstants.USER_NOT_EXIST);
        }
        if (!oauthUserDetails.isEnabled()) {
            throw new DisabledException(MessageConstants.ACCOUNT_DISABLED);
        } else if (!oauthUserDetails.isAccountNonLocked()) {
            throw new LockedException(MessageConstants.ACCOUNT_LOCKED);
        } else if (!oauthUserDetails.isAccountNonExpired()) {
            throw new AccountExpiredException(MessageConstants.ACCOUNT_EXPIRED);
        } else if (!oauthUserDetails.isCredentialsNonExpired()) {
            throw new CredentialExpiredException(MessageConstants.CREDENTIALS_EXPIRED);
        }
        return null;
    }
}
