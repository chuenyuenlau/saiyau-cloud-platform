package com.saiyau.admin.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.saiyau.common.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 权限表
 * </p>
 *
 * @author liuzhongyuan
 * @since 2021-10-18
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("t_sys_permission")
@ApiModel(value = "SysPermission对象", description = "权限表")
public class SysPermission extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键id")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "租户id")
    @TableField("tenant_id")
    private String tenantId;

    @ApiModelProperty(value = "权限名称")
    @TableField("name")
    private String name;

    @ApiModelProperty(value = "菜单id")
    @TableField("menu_id")
    private Long menuId;

    @ApiModelProperty(value = "菜单权限标识")
    @TableField("url_perm")
    private String urlPerm;

    @ApiModelProperty(value = "按钮权限标识")
    @TableField("btn_perm")
    private String btnPerm;

    /**
     * 有权限的角色编号集合
     */
    @TableField(exist = false)
    private List<String> roles;


}
