package com.saiyau.admin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.saiyau.admin.pojo.entity.SysMenu;

import java.util.List;

/**
 * <p>
 * 菜单表 Mapper 接口
 * </p>
 *
 * @author liuzhongyuan
 * @since 2021-10-18
 */
public interface SysMenuMapper extends BaseMapper<SysMenu> {
    List<SysMenu> listRoute();
}
