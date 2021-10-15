package com.saiyau.common.base;

import cn.hutool.core.date.DatePattern;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;

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
    private LocalDate createdTime;

    @ApiModelProperty(value = "修改时间")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DatePattern.NORM_DATETIME_PATTERN, timezone = "GMT+8")
    private LocalDate updatedTime;

    @ApiModelProperty(value = "创建人id")
    private String createdUser;

    @ApiModelProperty(value = "更新人id")
    private String updatedUser;

    @TableLogic(value = "0", delval = "1")
    private Boolean deleted;

}
