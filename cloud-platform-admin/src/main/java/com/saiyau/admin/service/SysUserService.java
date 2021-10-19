package com.saiyau.admin.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.saiyau.admin.pojo.entity.SysUser;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author liuzhongyuan
 * @since 2021-10-18
 */
public interface SysUserService extends IService<SysUser> {
    /**
     * 用户分页列表
     * @param page
     * @param user
     * @return
     */
    IPage<SysUser> list(Page<SysUser> page, SysUser user);

    /**
     * 新增用户
     *
     * @param user
     * @return
     */
    boolean saveUser(SysUser user);

    /**
     * 修改用户
     *
     * @param user
     * @return
     */
    boolean updateUser(SysUser user);

    /**
     * 根据用户名获取认证用户信息，携带角色和密码
     *
     * @param username
     * @return
     */
    SysUser getByUsername(String username);
}
