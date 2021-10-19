package com.saiyau.admin.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.saiyau.admin.pojo.entity.SysPermission;
import com.saiyau.admin.service.SysPermissionService;
import com.saiyau.common.result.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

/**
 * <p>
 * 权限表 前端控制器
 * </p>
 *
 * @author liuzhongyuan
 * @since 2021-10-18
 */
@Api(tags = "权限接口")
@RestController
@RequestMapping("/api/v1/permissions")
@RequiredArgsConstructor
public class SysPermissionController {
    private final SysPermissionService sysPermissionService;

    @ApiOperation(value = "列表分页")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", defaultValue = "1", value = "页码", paramType = "query", dataType = "Integer"),
            @ApiImplicitParam(name = "limit", defaultValue = "10", value = "每页数量", paramType = "query", dataType = "Integer"),
            @ApiImplicitParam(name = "name", value = "权限名称", paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "menuId", value = "菜单ID", paramType = "query", dataType = "Long")
    })
    @GetMapping("/page")
    public R pageList(Integer page, Integer limit, String name, Long menuId) {
        IPage<SysPermission> result = sysPermissionService.list(
                new Page<>(page, limit),
                new SysPermission()
                        .setMenuId(menuId)
                        .setName(name)
        );
        return R.success(result.getRecords());
    }


    @ApiOperation(value = "权限列表")
    @ApiImplicitParam(name = "menuId", value = "菜单ID", paramType = "query", dataType = "Long")
    @GetMapping
    public R list( Long menuId) {
        List<SysPermission> list = sysPermissionService.list(new LambdaQueryWrapper<SysPermission>()
                .eq(SysPermission::getMenuId, menuId));
        return R.success(list);
    }

    @ApiOperation(value = "权限详情")
    @ApiImplicitParam(name = "id", value = "权限ID", required = true, paramType = "path", dataType = "Long")
    @GetMapping("/{id}")
    public R detail(@PathVariable Long id) {
        SysPermission permission = sysPermissionService.getById(id);
        return R.success(permission);
    }

    @ApiOperation(value = "新增权限")
    @ApiImplicitParam(name = "permission", value = "实体JSON对象", required = true, paramType = "body", dataType = "SysPermission")
    @PostMapping
    public R add(@RequestBody SysPermission permission) {
        boolean result = sysPermissionService.save(permission);
        if (result) {
            sysPermissionService.refreshPermRolesRules();
        }
        return R.judge(result);
    }

    @ApiOperation(value = "修改权限")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "权限id", required = true, paramType = "path", dataType = "Long"),
            @ApiImplicitParam(name = "permission", value = "实体JSON对象", required = true, paramType = "body", dataType = "SysPermission")
    })
    @PutMapping(value = "/{id}")
    public R update(
            @PathVariable Long id,
            @RequestBody SysPermission permission) {
        boolean result = sysPermissionService.updateById(permission);
        if (result) {
            sysPermissionService.refreshPermRolesRules();
        }
        return R.judge(result);
    }

    @ApiOperation(value = "删除权限")
    @ApiImplicitParam(name = "ids", value = "id集合", required = true, paramType = "query", dataType = "Long")
    @DeleteMapping("/{ids}")
    public R delete(@PathVariable String ids) {
        boolean status = sysPermissionService.removeByIds(Arrays.asList(ids.split(",")));
        return R.judge(status);
    }
}
