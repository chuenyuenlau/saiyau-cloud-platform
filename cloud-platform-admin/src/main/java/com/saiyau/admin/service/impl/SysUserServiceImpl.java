package com.saiyau.admin.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.saiyau.admin.pojo.entity.SysUser;
import com.saiyau.admin.mapper.SysUserMapper;
import com.saiyau.admin.pojo.entity.SysUserRole;
import com.saiyau.admin.service.SysUserRoleService;
import com.saiyau.admin.service.SysUserService;
import com.saiyau.common.constant.GlobalConstants;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author liuzhongyuan
 * @since 2021-10-18
 */
@Service
@AllArgsConstructor
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {
    private final PasswordEncoder passwordEncoder;
    private final SysUserRoleService sysUserRoleService;

    /**
     * 用户分页列表
     *
     * @param page
     * @param user
     * @return
     */
    @Override
    public IPage<SysUser> list(Page<SysUser> page, SysUser user) {
        List<SysUser> list = this.baseMapper.list(page, user);
        page.setRecords(list);
        return page;
    }

    /**
     * 新增用户
     *
     * @param user
     * @return
     */
    @Override
    public boolean saveUser(SysUser user) {
        user.setPassword(passwordEncoder.encode(GlobalConstants.DEFAULT_USER_PASSWORD));
        boolean result = this.save(user);
        if (result) {
            List<Long> roleIds = user.getRoleIds();
            if (CollectionUtil.isNotEmpty(roleIds)) {
                List<SysUserRole> userRoleList = new ArrayList<>();
                roleIds.forEach(roleId -> userRoleList.add(new SysUserRole().setUserId(user.getId()).setRoleId(roleId)));
                result = sysUserRoleService.saveBatch(userRoleList);
            }
        }
        return result;
    }

    /**
     * 更新用户
     *
     * @param user
     * @return
     */
    @Override
    public boolean updateUser(SysUser user) {

        // 原来的用户角色ID集合
        List<Long> oldRoleIds = sysUserRoleService.list(new LambdaQueryWrapper<SysUserRole>()
                .eq(SysUserRole::getUserId, user.getId())).stream()
                .map(SysUserRole::getRoleId)
                .collect(Collectors.toList());

        // 新的用户角色ID集合
        List<Long> newRoleIds = user.getRoleIds();

        // 需要新增的用户角色ID集合
        List<Long> addRoleIds = newRoleIds.stream().filter(roleId -> !oldRoleIds.contains(roleId)).collect(Collectors.toList());
        if (CollectionUtil.isNotEmpty(addRoleIds)) {
            List<SysUserRole> addUserRoleList = new ArrayList<>();
            addRoleIds.forEach(roleId -> {
                addUserRoleList.add(new SysUserRole().setUserId(user.getId()).setRoleId(roleId));
            });
            sysUserRoleService.saveBatch(addUserRoleList);
        }

        // 需要删除的用户的角色ID集合
        List<Long> removeRoleIds = oldRoleIds.stream().filter(roleId -> !newRoleIds.contains(roleId)).collect(Collectors.toList());
        if (CollectionUtil.isNotEmpty(removeRoleIds)) {
            removeRoleIds.forEach(roleId -> {
                sysUserRoleService.remove(new LambdaQueryWrapper<SysUserRole>().eq(SysUserRole::getUserId, user.getId()).eq(SysUserRole::getRoleId, roleId));
            });
        }

        // 最后更新用户
        return this.updateById(user);
    }

    @Override
    public SysUser getByUsername(String username) {
        return this.baseMapper.getByUsername(username);
    }
}
