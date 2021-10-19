package com.saiyau.admin.pojo.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.saiyau.common.base.BaseEntity;
import lombok.Data;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class MenuVo extends BaseEntity {

    private Long id;

    private Long parentId;

    private String name;

    private String icon;

    private String routeName;

    private String routePath;

    private String component;

    private Integer sort;

    private Integer status;

    private String redirect;

    @JsonInclude(value = JsonInclude.Include.NON_NULL)
    private List<MenuVo> children;

}
