package com.saiyau.admin.service.impl;

import cn.hutool.core.lang.Assert;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.saiyau.admin.pojo.entity.SysRole;
import com.saiyau.admin.mapper.SysRoleMapper;
import com.saiyau.admin.pojo.entity.SysRoleMenu;
import com.saiyau.admin.pojo.entity.SysRolePermission;
import com.saiyau.admin.pojo.entity.SysUserRole;
import com.saiyau.admin.service.SysRoleMenuService;
import com.saiyau.admin.service.SysRolePermissionService;
import com.saiyau.admin.service.SysRoleService;
import com.saiyau.admin.service.SysUserRoleService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * <p>
 * 角色表 服务实现类
 * </p>
 *
 * @author liuzhongyuan
 * @since 2021-10-18
 */
@Service
@AllArgsConstructor
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements SysRoleService {
   private final SysUserRoleService sysUserRoleService;
   private final SysRoleMenuService sysRoleMenuService;
   private final SysRolePermissionService sysRolePermissionService;

    @Override
    public boolean delete(List<Long> ids) {
        Optional.ofNullable(ids).orElse(new ArrayList<>()).forEach(id -> {
            int count = sysUserRoleService.count(new LambdaQueryWrapper<SysUserRole>().eq(SysUserRole::getRoleId, id));
            Assert.isTrue(count <= 0, "该角色已分配用户，无法删除");
            sysRoleMenuService.remove(new LambdaQueryWrapper<SysRoleMenu>().eq(SysRoleMenu::getRoleId, id));
            sysRolePermissionService.remove(new LambdaQueryWrapper<SysRolePermission>().eq(SysRolePermission::getRoleId, id));
        });
        return this.removeByIds(ids);
    }
}
