package com.saiyau.auth.security.service.impl;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * @author liuzhongyuan
 * @ClassName UserDetailsServiceImpl.java
 * @Description 从数据库获取认证用户信息，用于和前端传过来得用户信息进行密码判断
 * @createTime 2021/10/19
 */
public class UserDetailsServiceImpl implements UserDetailsService {
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return null;
    }
}
