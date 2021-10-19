package com.saiyau.admin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.saiyau.admin.pojo.entity.SysRolePermission;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 角色权限表 Mapper 接口
 * </p>
 *
 * @author liuzhongyuan
 * @since 2021-10-18
 */
public interface SysRolePermissionMapper extends BaseMapper<SysRolePermission> {
    List<Long> listPermissionId(@Param("menuId") Long menuId, @Param("roleId") Long roleId);
}
