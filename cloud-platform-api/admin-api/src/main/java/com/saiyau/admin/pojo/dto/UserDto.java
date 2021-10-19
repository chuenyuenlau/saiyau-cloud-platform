package com.saiyau.admin.pojo.dto;

import lombok.Data;

import java.util.List;

/**
 * @author liuzhongyuan
 * @ClassName UserDto.java
 * @Description TODO
 * @createTime 2021/10/19
 */
@Data
public class UserDto {
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
    private Integer status;
    /**
     * 客户端id
     */
    private String clientId;
    /**
     * 租户id
     */
    private String tenantId;
    /**
     * 角色
     */
    private List<String> roles;
}
