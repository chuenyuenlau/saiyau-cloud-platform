package com.saiyau.auth.security.domain;

import cn.hutool.core.collection.CollectionUtil;
import com.saiyau.admin.pojo.dto.UserDto;
import com.saiyau.admin.pojo.entity.SysUser;
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

    public OauthUserDetails(SysUser sysUser){
        this.id = sysUser.getId();
        this.username = sysUser.getUsername();
        this.tenantId = sysUser.getTenantId();
        this.password = sysUser.getPassword();
        this.enabled = GlobalConstants.STATUS_ACTIVE.equals(sysUser.getStatus());
        if(CollectionUtil.isNotEmpty(sysUser.getRoles())){
            authorities = new ArrayList<>();
            sysUser.getRoles().forEach(role -> authorities.add(new SimpleGrantedAuthority(role)));
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
