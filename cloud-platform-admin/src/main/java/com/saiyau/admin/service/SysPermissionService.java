package com.saiyau.admin.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.saiyau.admin.pojo.entity.SysPermission;

import java.util.List;

/**
 * <p>
 * 权限表 服务类
 * </p>
 *
 * @author liuzhongyuan
 * @since 2021-10-18
 */
public interface SysPermissionService extends IService<SysPermission> {
    List<SysPermission> listPermRoles();

    IPage<SysPermission> list(Page<SysPermission> page, SysPermission permission);

    List<String> listBtnPermByRoles(List<String> roles);

    /**
     * 刷新Redis缓存中角色菜单的权限规则，角色和菜单信息变更调用
     *
     * @return
     */
    boolean refreshPermRolesRules();
}
