package com.saiyau.admin.controller;


import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.saiyau.admin.pojo.entity.SysMenu;
import com.saiyau.admin.pojo.vo.MenuVo;
import com.saiyau.admin.pojo.vo.RouteVo;
import com.saiyau.admin.pojo.vo.SelectVo;
import com.saiyau.admin.pojo.vo.TreeSelectVo;
import com.saiyau.admin.service.SysMenuService;
import com.saiyau.admin.service.SysPermissionService;
import com.saiyau.common.result.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

/**
 * <p>
 * 菜单表 前端控制器
 * </p>
 *
 * @author liuzhongyuan
 * @since 2021-10-18
 */
@Api(tags = "菜单接口")
@RestController
@RequestMapping("/api/v1/menus")
@RequiredArgsConstructor
public class SysMenuController {
    private final SysMenuService sysMenuService;
    private final SysPermissionService sysPermissionService;

    @ApiOperation(value = "菜单表格（Table）层级列表")
    @ApiImplicitParam(name = "name", value = "菜单名称", paramType = "query", dataType = "String")
    @GetMapping("/table")
    public R getTableList(String name) {
        List<MenuVo> menuList = sysMenuService.listTable(name);
        return R.success(menuList);
    }


    @ApiOperation(value = "菜单下拉（Select）层级列表")
    @GetMapping("/select")
    public R getSelectList() {
        List<SelectVo> menuList = sysMenuService.listSelect();
        return R.success(menuList);
    }

    @ApiOperation(value = "菜单下拉（TreeSelect）层级列表")
    @GetMapping("/tree-select")
    public R getTreeSelectList() {
        List<TreeSelectVo> menuList = sysMenuService.listTreeSelect();
        return R.success(menuList);
    }

    @ApiOperation(value = "菜单路由（Route）层级列表")
    @GetMapping("/route")
    public R getRouteList() {
        List<RouteVo> routeList = sysMenuService.listRoute();
        return R.success(routeList);
    }


    @ApiOperation(value = "菜单详情")
    @ApiImplicitParam(name = "id", value = "菜单id", required = true, paramType = "path", dataType = "Long")
    @GetMapping("/{id}")
    public R detail(@PathVariable Integer id) {
        SysMenu menu = sysMenuService.getById(id);
        return R.success(menu);
    }

    @ApiOperation(value = "新增菜单")
    @PostMapping
    @CacheEvict(cacheNames = "system",key = "'routeList'")
    public R add(@RequestBody SysMenu menu) {
        boolean result = sysMenuService.save(menu);
        if(result){
            sysPermissionService.refreshPermRolesRules();
        }
        return R.judge(result);
    }

    @ApiOperation(value = "修改菜单")
    @PutMapping(value = "/{id}")
    @CacheEvict(cacheNames = "system",key = "'routeList'")
    public R update(
            @PathVariable Long id,
            @RequestBody SysMenu menu) {
        boolean result = sysMenuService.updateById(menu);
        if(result){
            sysPermissionService.refreshPermRolesRules();
        }
        return R.judge(result);
    }

    @ApiOperation(value = "删除菜单")
    @ApiImplicitParam(name = "ids", value = "id集合字符串，以,分割", required = true, paramType = "query", dataType = "String")
    @DeleteMapping("/{ids}")
    @CacheEvict(cacheNames = "system",key = "'routeList'")
    public R delete(@PathVariable("ids") String ids) {
        boolean result = sysMenuService.removeByIds(Arrays.asList(ids.split(",")));
        if(result){
            sysPermissionService.refreshPermRolesRules();
        }
        return R.judge(result);
    }

    @ApiOperation(value = "选择性修改菜单")
    @PatchMapping(value = "/{id}")
    @CacheEvict(cacheNames = "system",key = "'routeList'")
    public R patch(@PathVariable Integer id, @RequestBody SysMenu menu) {
        LambdaUpdateWrapper<SysMenu> updateWrapper = new LambdaUpdateWrapper<SysMenu>().eq(SysMenu::getId, id);
        updateWrapper.set(menu.getStatus() != null, SysMenu::getStatus, menu.getStatus());
        boolean result = sysMenuService.update(updateWrapper);
        if(result){
            sysPermissionService.refreshPermRolesRules();
        }
        return R.judge(result);
    }
}
