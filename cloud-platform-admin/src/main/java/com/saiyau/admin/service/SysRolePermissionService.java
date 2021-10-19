package com.saiyau.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.saiyau.admin.pojo.dto.RolePermissionDTO;
import com.saiyau.admin.pojo.entity.SysRolePermission;

import java.util.List;

/**
 * <p>
 * 角色权限表 服务类
 * </p>
 *
 * @author liuzhongyuan
 * @since 2021-10-18
 */
public interface SysRolePermissionService extends IService<SysRolePermission> {
    List<Long> listPermissionId(Long moduleId, Long roleId);
    List<Long> listPermissionId(Long roleId);
    boolean update(RolePermissionDTO rolePermission);
}
