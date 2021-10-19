package com.saiyau.admin.pojo.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author haoxr
 * @date 2020-11-06
 */
@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class RouteVo {

    private String path;

    private String component;

    private String redirect;

    private boolean alwaysShow;

    private String name;

    private Boolean hidden;

    private Meta meta;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Meta {
        private String title;
        private String icon;
        private List<String> roles;
    }
    private List<RouteVo> children;
}
