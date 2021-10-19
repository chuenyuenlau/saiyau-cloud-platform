package com.saiyau.admin.pojo.dto;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
public class RolePermissionDto {
    private Long roleId;
    private List<Long> permissionIds;
    private Long menuId;
}
