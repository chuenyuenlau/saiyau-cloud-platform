package com.saiyau.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.saiyau.admin.pojo.entity.SysMenu;
import com.saiyau.admin.pojo.vo.MenuVO;
import com.saiyau.admin.pojo.vo.RouteVO;
import com.saiyau.admin.pojo.vo.SelectVO;
import com.saiyau.admin.pojo.vo.TreeSelectVO;

import java.util.List;

/**
 * <p>
 * 菜单表 服务类
 * </p>
 *
 * @author liuzhongyuan
 * @since 2021-10-18
 */
public interface SysMenuService extends IService<SysMenu> {
    /**
     * 菜单表格（Table）层级列表
     *
     * @param name 菜单名称
     * @return
     */
    List<MenuVO> listTable(String name);


    /**
     * 菜单下拉（Select）层级列表
     *
     * @return
     */
    List<SelectVO> listSelect();


    /**
     * 菜单路由（Route）层级列表
     *
     * @return
     */
    List<RouteVO> listRoute();

    /**
     * 菜单下拉(TreeSelect)层级列表
     *
     * @return
     */
    List<TreeSelectVO> listTreeSelect();

}
