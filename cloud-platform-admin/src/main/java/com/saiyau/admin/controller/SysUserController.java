package com.saiyau.admin.controller;


import cn.hutool.core.bean.BeanUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.saiyau.admin.pojo.entity.SysUser;
import com.saiyau.admin.pojo.entity.SysUserRole;
import com.saiyau.admin.pojo.vo.UserVo;
import com.saiyau.admin.service.SysPermissionService;
import com.saiyau.admin.service.SysUserRoleService;
import com.saiyau.admin.service.SysUserService;
import com.saiyau.common.result.R;
import com.saiyau.common.web.util.JwtUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author liuzhongyuan
 * @since 2021-10-18
 */
@Api(tags = "用户接口")
@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@Slf4j
public class SysUserController {
    private final SysUserService sysUserService;
    private final SysUserRoleService sysUserRoleService;
    private final PasswordEncoder passwordEncoder;
    private final SysPermissionService sysPermissionService;

    @ApiOperation(value = "列表分页")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "页码", paramType = "query", dataType = "Long"),
            @ApiImplicitParam(name = "limit", value = "每页数量", paramType = "query", dataType = "Long"),
            @ApiImplicitParam(name = "nickname", value = "用户昵称", paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "mobile", value = "手机号码", paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "status", value = "状态", paramType = "query", dataType = "Long"),
            @ApiImplicitParam(name = "deptId", value = "部门ID", paramType = "query", dataType = "Long"),
    })
    @GetMapping
    public R list(Integer page, Integer limit, String nickname, String mobile, Integer status, Long deptId) {

        SysUser user = new SysUser();
        user.setNickname(nickname);
        user.setMobile(mobile);
        user.setStatus(status);
        user.setDeptId(deptId);

        IPage<SysUser> result = sysUserService.list(new Page<>(page, limit), user);
        return R.success(result.getRecords());
    }

    @ApiOperation(value = "用户详情")
    @ApiImplicitParam(name = "id", value = "用户ID", required = true, paramType = "path", dataType = "Long")
    @GetMapping("/{id}")
    public R detail(@PathVariable Long id) {
        SysUser user = sysUserService.getById(id);
        if (user != null) {
            List<Long> roleIds = sysUserRoleService.list(new LambdaQueryWrapper<SysUserRole>()
                    .eq(SysUserRole::getUserId, user.getId())
                    .select(SysUserRole::getRoleId)
            ).stream().map(SysUserRole::getRoleId).collect(Collectors.toList());
            user.setRoleIds(roleIds);
        }
        return R.success(user);
    }

    @ApiOperation(value = "新增用户")
    @PostMapping
    public R add(@RequestBody SysUser user) {
        boolean result = sysUserService.saveUser(user);
        return R.judge(result);
    }

    @ApiOperation(value = "修改用户")
    @ApiImplicitParam(name = "id", value = "用户ID", required = true, paramType = "path", dataType = "Long")
    @PutMapping(value = "/{id}")
    public R update(
            @PathVariable Long id,
            @RequestBody SysUser user) {
        boolean result = sysUserService.updateUser(user);
        return R.judge(result);
    }

    @ApiOperation(value = "删除用户")
    @ApiImplicitParam(name = "ids", value = "id集合", required = true, paramType = "query", dataType = "String")
    @DeleteMapping("/{ids}")
    public R delete(@PathVariable String ids) {
        boolean status = sysUserService.removeByIds(new ArrayList<>(Arrays.asList(ids.split(","))));
        return R.judge(status);
    }

    @ApiOperation(value = "选择性更新用户")
    @ApiImplicitParam(name = "id", value = "用户ID", required = true, paramType = "path", dataType = "Long")
    @PatchMapping(value = "/{id}")
    public R patch(@PathVariable Long id, @RequestBody SysUser user) {
        LambdaUpdateWrapper<SysUser> updateWrapper = new LambdaUpdateWrapper<SysUser>().eq(SysUser::getId, id);
        updateWrapper.set(user.getStatus() != null, SysUser::getStatus, user.getStatus());
        updateWrapper.set(user.getPassword() != null, SysUser::getPassword, passwordEncoder.encode(user.getPassword()));
        boolean status = sysUserService.update(updateWrapper);
        return R.judge(status);
    }


    /**
     * 提供用于用户登录认证需要的用户信息
     *
     * @param username
     * @return
     */
    @ApiOperation(value = "根据用户名获取用户信息")
    @ApiImplicitParam(name = "username", value = "用户名", required = true, paramType = "path", dataType = "String")
    @GetMapping("/username/{username}")
    public R<SysUser> getUserByUsername(@PathVariable String username) {
        SysUser user = sysUserService.getByUsername(username);
        return R.success(user);
    }


    @ApiOperation(value = "获取当前登陆的用户信息")
    @GetMapping("/me")
    public R<UserVo> getCurrentUser() {
        UserVo userVO = new UserVo();

        // 用户基本信息
        Long userId = JwtUtils.getUserId();
        SysUser user = sysUserService.getById(userId);
        BeanUtil.copyProperties(user, userVO);

        // 用户角色信息
        List<String> roles = JwtUtils.getRoles();
        userVO.setRoles(roles);

        // 用户按钮权限信息
        List<String> perms = sysPermissionService.listBtnPermByRoles(roles);
        userVO.setPerms(perms);

        log.info("获取当前登陆的用户信息:{}", JSONUtil.toJsonStr(userVO));

        return R.success(userVO);
    }
}
