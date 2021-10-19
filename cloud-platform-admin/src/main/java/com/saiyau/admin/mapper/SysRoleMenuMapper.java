package com.saiyau.admin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.saiyau.admin.pojo.entity.SysRoleMenu;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * 角色菜单表 Mapper 接口
 * </p>
 *
 * @author liuzhongyuan
 * @since 2021-10-18
 */
public interface SysRoleMenuMapper extends BaseMapper<SysRoleMenu> {

    List<Long> listByMenuId(Integer menuId);

    List<Long> listMenuIds(Long roleId);

}
