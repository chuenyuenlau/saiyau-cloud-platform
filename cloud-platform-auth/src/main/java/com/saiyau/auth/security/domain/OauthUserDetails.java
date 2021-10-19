package com.saiyau.auth.security.domain;

import cn.hutool.core.collection.CollectionUtil;
import com.saiyau.admin.pojo.dto.UserDto;
import com.saiyau.common.constant.GlobalConstants;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

/**
 * @author liuzhongyuan
 * @ClassName OauthUserDetails.java
 * @Description Spring Security用户基本信息类，用户信息的返回可根据事先UserDetails接口进行扩展
 * @createTime 2021/10/19
 */
@Data
@NoArgsConstructor
public class OauthUserDetails implements UserDetails {
    /**
     * 主键id
     */
    private Long id;
    /**
     * 用户名
     */
    private String username;
    /**
     * 密码
     */
    private String password;
    /**
     * 状态：0-正常 1-禁用
     */
    private Boolean enabled;
    /**
     * 客户端id
     */
    private String clientId;
    /**
     * 租户id
     */
    private String tenantId;
    /**
     * 权限数据
     */
    private Collection<GrantedAuthority> authorities;

    public OauthUserDetails(UserDto userDto){
        this.id = userDto.getId();
        this.clientId = userDto.getClientId();
        this.username = userDto.getUsername();
        this.password = userDto.getPassword();
        this.enabled = GlobalConstants.STATUS_ACTIVE.equals(userDto.getStatus());
        if(CollectionUtil.isNotEmpty(userDto.getRoles())){
            authorities = new ArrayList<>();
            userDto.getRoles().forEach(role -> authorities.add(new SimpleGrantedAuthority(role)));
        }
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return this.enabled;
    }
}
