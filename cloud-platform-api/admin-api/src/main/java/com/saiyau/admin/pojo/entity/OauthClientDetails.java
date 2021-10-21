package com.saiyau.admin.pojo.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.saiyau.common.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * oauth2 客户端信息表
 * </p>
 *
 * @author liuzhongyuan
 * @since 2021-10-21
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("t_oauth_client_details")
@ApiModel(value = "OauthClientDetails对象", description = "oauth2 客户端信息表")
public class OauthClientDetails extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @TableId("client_id")
    private String clientId;

    @TableField("resource_ids")
    private String resourceIds;

    @TableField("client_secret")
    private String clientSecret;

    @TableField("scope")
    private String scope;

    @TableField("authorized_grant_types")
    private String authorizedGrantTypes;

    @TableField("web_server_redirect_uri")
    private String webServerRedirectUri;

    @TableField("authorities")
    private String authorities;

    @TableField("access_token_validity")
    private Integer accessTokenValidity;

    @TableField("refresh_token_validity")
    private Integer refreshTokenValidity;

    @TableField("additional_information")
    private String additionalInformation;

    @TableField("autoapprove")
    private String autoapprove;


}
