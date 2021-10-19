package com.saiyau.admin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.saiyau.admin.pojo.entity.SysPermission;

import java.util.List;

/**
 * <p>
 * 权限表 Mapper 接口
 * </p>
 *
 * @author liuzhongyuan
 * @since 2021-10-18
 */
public interface SysPermissionMapper extends BaseMapper<SysPermission> {

    List<SysPermission> listPermRoles();

    List<SysPermission> list(Page<SysPermission> page, SysPermission permission);

    List<String> listBtnPermByRoles(List<String> roles);
}
