package com.saiyau.admin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.saiyau.admin.pojo.entity.SysUser;

import java.util.List;

/**
 * <p>
 * 用户表 Mapper 接口
 * </p>
 *
 * @author liuzhongyuan
 * @since 2021-10-18
 */
public interface SysUserMapper extends BaseMapper<SysUser> {
    List<SysUser> list(Page<SysUser> page, SysUser user);

    SysUser getByUsername(String username);
}
