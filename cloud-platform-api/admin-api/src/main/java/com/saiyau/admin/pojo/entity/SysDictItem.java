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

/**
 * <p>
 * 字典数据表
 * </p>
 *
 * @author liuzhongyuan
 * @since 2021-10-18
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("t_sys_dict_item")
@ApiModel(value = "SysDictItem对象", description = "字典数据表")
public class SysDictItem extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键id")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "租户id")
    @TableField("tenant_id")
    private String tenantId;

    @ApiModelProperty(value = "字典项名称")
    @TableField("name")
    private String name;

    @ApiModelProperty(value = "字典项值")
    @TableField("value")
    private String value;

    @ApiModelProperty(value = "字典类型编码")
    @TableField("dict_code")
    private String dictCode;

    @ApiModelProperty(value = "是否默认：0-否 1-是")
    @TableField("defaulted")
    private Integer defaulted;

    @ApiModelProperty(value = "排序")
    @TableField("sort")
    private Integer sort;

    @ApiModelProperty(value = "备注")
    @TableField("remark")
    private String remark;

    @ApiModelProperty(value = "状态：0-正常 1-禁用")
    @TableField("status")
    private Integer status;


}
