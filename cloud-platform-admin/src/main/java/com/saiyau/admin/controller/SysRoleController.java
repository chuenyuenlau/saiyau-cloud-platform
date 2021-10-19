package com.saiyau.admin.controller;


import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.saiyau.admin.pojo.dto.RolePermissionDto;
import com.saiyau.admin.pojo.entity.SysRole;
import com.saiyau.admin.service.SysPermissionService;
import com.saiyau.admin.service.SysRoleMenuService;
import com.saiyau.admin.service.SysRolePermissionService;
import com.saiyau.admin.service.SysRoleService;
import com.saiyau.common.constant.GlobalConstants;
import com.saiyau.common.result.R;
import com.saiyau.common.web.util.JwtUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 角色表 前端控制器
 * </p>
 *
 * @author liuzhongyuan
 * @since 2021-10-18
 */
@Api(tags = "角色接口")
@RestController
@RequestMapping("/api/v1/roles")
@RequiredArgsConstructor
public class SysRoleController {
    private final SysRoleService sysRoleService;
    private final SysRoleMenuService sysRoleMenuService;
    private final SysRolePermissionService sysRolePermissionService;
    private final SysPermissionService sysPermissionService;


    @ApiOperation(value = "列表分页")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "页码", paramType = "query", dataType = "Long"),
            @ApiImplicitParam(name = "limit", value = "每页数量", paramType = "query", dataType = "Long"),
            @ApiImplicitParam(name = "name", value = "角色名称", paramType = "query", dataType = "String"),
    })
    @GetMapping("/page")
    public R pageList(Integer page, Integer limit, String name) {
        List<String> roles = JwtUtils.getRoles();
        boolean isRoot = roles.contains(GlobalConstants.ROOT_ROLE_CODE);  // 判断是否是超级管理员
        LambdaQueryWrapper<SysRole> queryWrapper = new LambdaQueryWrapper<SysRole>()
                .like(StrUtil.isNotBlank(name), SysRole::getName, name)
                .ne(!isRoot, SysRole::getCode, GlobalConstants.ROOT_ROLE_CODE)
                .orderByAsc(SysRole::getSort)
                .orderByDesc(SysRole::getUpdateTime)
                .orderByDesc(SysRole::getCreateTime);
        Page<SysRole> result = sysRoleService.page(new Page<>(page, limit), queryWrapper);
        return R.success(result.getRecords());
    }


    @ApiOperation(value = "角色列表")
    @GetMapping
    public R list() {
        List<String> roles = JwtUtils.getRoles();
        boolean isRoot = roles.contains(GlobalConstants.ROOT_ROLE_CODE);  // 判断是否是超级管理员
        List list = sysRoleService.list(new LambdaQueryWrapper<SysRole>()
                .eq(SysRole::getStatus, GlobalConstants.STATUS_ACTIVE)
                .ne(!isRoot, SysRole::getCode, GlobalConstants.ROOT_ROLE_CODE)
                .orderByAsc(SysRole::getSort)
        );
        return R.success(list);
    }


    @ApiOperation(value = "新增角色")
    @ApiImplicitParam(name = "role", value = "实体JSON对象", required = true, paramType = "body", dataType = "SysRole")
    @PostMapping
    public R add(@RequestBody SysRole role) {
        int count = sysRoleService.count(new LambdaQueryWrapper<SysRole>()
                .eq(SysRole::getCode, role.getCode())
                .or()
                .eq(SysRole::getName, role.getName())
        );
        Assert.isTrue(count == 0, "角色名称或角色编码重复，请检查！");
        boolean result = sysRoleService.save(role);
        if (result) {
            sysPermissionService.refreshPermRolesRules();
        }
        return R.judge(result);
    }

    @ApiOperation(value = "修改角色")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "角色id", required = true, paramType = "path", dataType = "Long"),
            @ApiImplicitParam(name = "role", value = "实体JSON对象", required = true, paramType = "body", dataType = "SysRole")
    })
    @PutMapping(value = "/{id}")
    public R update(
            @PathVariable Long id,
            @RequestBody SysRole role) {
        int count = sysRoleService.count(new LambdaQueryWrapper<SysRole>()
                .eq(SysRole::getCode, role.getCode())
                .or()
                .eq(SysRole::getName, role.getName())
                .ne(SysRole::getId, id)
        );
        Assert.isTrue(count == 0, "角色名称或角色编码重复，请检查！");
        boolean result = sysRoleService.updateById(role);
        if (result) {
            sysPermissionService.refreshPermRolesRules();
        }
        return R.judge(result);
    }

    @ApiOperation(value = "删除角色")
    @ApiImplicitParam(name = "ids", value = "以,分割拼接字符串", required = true, dataType = "String")
    @DeleteMapping("/{ids}")
    public R delete(@PathVariable String ids) {
        boolean result = sysRoleService.delete(Arrays.asList(ids.split(",")).stream()
                .map(id -> Long.parseLong(id)).collect(Collectors.toList()));
        if (result) {
            sysPermissionService.refreshPermRolesRules();
        }
        return R.judge(result);
    }

    @ApiOperation(value = "选择性修改角色")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "用户ID", required = true, paramType = "path", dataType = "Long"),
            @ApiImplicitParam(name = "role", value = "实体JSON对象", required = true, paramType = "body", dataType = "SysRole")
    })
    @PatchMapping(value = "/{id}")
    public R patch(@PathVariable Long id, @RequestBody SysRole role) {
        LambdaUpdateWrapper<SysRole> updateWrapper = new LambdaUpdateWrapper<SysRole>()
                .eq(SysRole::getId, id)
                .set(role.getStatus() != null, SysRole::getStatus, role.getStatus());
        boolean result = sysRoleService.update(updateWrapper);
        if (result) {
            sysPermissionService.refreshPermRolesRules();
        }
        return R.judge(result);
    }

    @ApiOperation(value = "获取角色拥有的菜单ID集合")
    @ApiImplicitParam(name = "id", value = "角色id", required = true, paramType = "path", dataType = "Long")
    @GetMapping("/{id}/menus")
    public R listRoleMenu(@PathVariable("id") Long roleId) {
        List<Long> menuIds = sysRoleMenuService.listMenuIds(roleId);
        return R.success(menuIds);
    }

    @ApiOperation(value = "获取角色拥有的权限ID集合")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "角色id", required = true, paramType = "path", dataType = "Integer"),
            @ApiImplicitParam(name = "menuId", value = "菜单ID", paramType = "query", dataType = "Integer"),
    })
    @GetMapping("/{id}/permissions")
    public R listRolePermission(@PathVariable("id") Long roleId, Long menuId) {
        List<Long> permissionIds = sysRolePermissionService.listPermissionId(menuId, roleId);
        return R.success(permissionIds);
    }

    @ApiOperation(value = "修改角色菜单")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "角色id", required = true, paramType = "path", dataType = "Long"),
            @ApiImplicitParam(name = "role", value = "实体JSON对象", required = true, paramType = "body", dataType = "SysRole")
    })
    @PutMapping(value = "/{id}/menus")
    public R updateRoleMenu(
            @PathVariable("id") Long roleId,
            @RequestBody SysRole role) {

        List<Long> menuIds = role.getMenuIds();
        boolean result = sysRoleMenuService.update(roleId, menuIds);
        if (result) {
            sysPermissionService.refreshPermRolesRules();
        }
        return R.judge(result);
    }

    @ApiOperation(value = "修改角色权限")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "角色id", required = true, paramType = "path", dataType = "Long"),
            @ApiImplicitParam(name = "rolePermission", value = "实体JSON对象", required = true, paramType = "body", dataType = "RolePermissionDTO")
    })
    @PutMapping(value = "/{id}/permissions")
    public R updateRolePermission(
            @PathVariable("id") Long roleId,
            @RequestBody RolePermissionDto rolePermission) {
        rolePermission.setRoleId(roleId);
        boolean result = sysRolePermissionService.update(rolePermission);
        if (result) {
            sysPermissionService.refreshPermRolesRules();
        }
        return R.judge(result);
    }
}
