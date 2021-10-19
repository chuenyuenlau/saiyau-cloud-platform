package com.saiyau.admin.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.saiyau.admin.pojo.entity.SysPermission;
import com.saiyau.admin.mapper.SysPermissionMapper;
import com.saiyau.admin.service.SysPermissionService;
import com.saiyau.common.constant.GlobalConstants;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * 权限表 服务实现类
 * </p>
 *
 * @author liuzhongyuan
 * @since 2021-10-18
 */
@Service
@AllArgsConstructor
public class SysPermissionServiceImpl extends ServiceImpl<SysPermissionMapper, SysPermission> implements SysPermissionService {

    private final RedisTemplate<String, Object> redisTemplate;

    @Override
    public List<SysPermission> listPermRoles() {
        return this.baseMapper.listPermRoles();
    }

    @Override
    public IPage<SysPermission> list(Page<SysPermission> page, SysPermission permission) {
        List<SysPermission> list = this.baseMapper.list(page, permission);
        page.setRecords(list);
        return page;
    }

    @Override
    public boolean refreshPermRolesRules() {
        redisTemplate.delete(Arrays.asList(GlobalConstants.URL_PERM_ROLES_KEY, GlobalConstants.BTN_PERM_ROLES_KEY));
        List<SysPermission> permissions = this.listPermRoles();
        if (CollectionUtil.isNotEmpty(permissions)) {
            // 初始化URL【权限->角色(集合)】规则
            List<SysPermission> urlPermList = permissions.stream()
                    .filter(item -> StrUtil.isNotBlank(item.getUrlPerm()))
                    .collect(Collectors.toList());
            if (CollectionUtil.isNotEmpty(urlPermList)) {
                Map<String, List<String>> urlPermRoles = new HashMap<>();
                urlPermList.forEach(item -> {
                    String perm = item.getUrlPerm();
                    List<String> roles = item.getRoles();
                    urlPermRoles.put(perm, roles);
                });
                redisTemplate.opsForHash().putAll(GlobalConstants.URL_PERM_ROLES_KEY, urlPermRoles);
                redisTemplate.convertAndSend("cleanRoleLocalCache", "true");
            }
            // 初始化URL【按钮->角色(集合)】规则
            List<SysPermission> btnPermList = permissions.stream()
                    .filter(item -> StrUtil.isNotBlank(item.getBtnPerm()))
                    .collect(Collectors.toList());
            if (CollectionUtil.isNotEmpty(btnPermList)) {
                Map<String, List<String>> btnPermRoles = CollectionUtil.newHashMap();
                btnPermList.forEach(item -> {
                    String perm = item.getBtnPerm();
                    List<String> roles = item.getRoles();
                    btnPermRoles.put(perm, roles);
                });
                redisTemplate.opsForHash().putAll(GlobalConstants.BTN_PERM_ROLES_KEY, btnPermRoles);
            }
        }
        return true;
    }


    @Override
    public List<String> listBtnPermByRoles(List<String> roles) {
        List<String> perms = this.baseMapper.listBtnPermByRoles(roles);
        return perms;
    }
}
