package com.saiyau.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.saiyau.admin.pojo.entity.SysRole;

import java.util.List;

/**
 * <p>
 * 角色表 服务类
 * </p>
 *
 * @author liuzhongyuan
 * @since 2021-10-18
 */
public interface SysRoleService extends IService<SysRole> {
    boolean delete(List<Long> ids);
}
