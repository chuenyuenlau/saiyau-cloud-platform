package com.saiyau.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.saiyau.admin.pojo.entity.SysRoleMenu;

import java.util.List;

/**
 * <p>
 * 角色菜单表 服务类
 * </p>
 *
 * @author liuzhongyuan
 * @since 2021-10-18
 */
public interface SysRoleMenuService extends IService<SysRoleMenu> {
    List<Long> listMenuIds(Long roleId);

    /**
     * 修改角色菜单
     * @param roleId
     * @param menuIds
     * @return
     */
    boolean update(Long roleId, List<Long> menuIds);

}
