package com.saiyau.common.base;

import cn.hutool.core.date.DatePattern;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * @author liuzhongyuan
 * @ClassName BaseEntity.java
 * @Description 基础实体类
 * @createTime 2021/10/14
 */
@Data
public class BaseEntity implements Serializable {

    @ApiModelProperty(value = "创建时间")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DatePattern.NORM_DATETIME_PATTERN, timezone = "GMT+8")
    @TableField("create_time")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "修改时间")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DatePattern.NORM_DATETIME_PATTERN, timezone = "GMT+8")
    @TableField("update_time")
    private LocalDateTime updateTime;

    @ApiModelProperty(value = "创建人id")
    @TableField("create_user")
    private Long createUser;

    @ApiModelProperty(value = "修改人id")
    @TableField("update_user")
    private Long updateUser;

    @ApiModelProperty(value = "软删除标志：0-未删除 1-已删除")
    @TableField("is_deleted")
    @TableLogic(value = "0", delval = "1")
    private Boolean isDeleted;
}
