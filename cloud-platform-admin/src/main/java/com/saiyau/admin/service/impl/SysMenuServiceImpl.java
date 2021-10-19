package com.saiyau.admin.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.saiyau.admin.mapper.SysMenuMapper;
import com.saiyau.admin.pojo.entity.SysMenu;
import com.saiyau.admin.pojo.vo.MenuVO;
import com.saiyau.admin.pojo.vo.RouteVO;
import com.saiyau.admin.pojo.vo.SelectVO;
import com.saiyau.admin.pojo.vo.TreeSelectVO;
import com.saiyau.admin.service.SysMenuService;
import com.saiyau.common.constant.GlobalConstants;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * <p>
 * 菜单表 服务实现类
 * </p>
 *
 * @author liuzhongyuan
 * @since 2021-10-18
 */
@Service
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu> implements SysMenuService {
    /**
     * 菜单表格（Table）层级列表
     *
     * @param name 菜单名称
     * @return
     */
    @Override
    public List<MenuVO> listTable(String name) {
        List<SysMenu> menuList = this.list(
                new LambdaQueryWrapper<SysMenu>()
                        .like(StrUtil.isNotBlank(name), SysMenu::getName, name)
                        .orderByAsc(SysMenu::getSort)
        );
        List<MenuVO> tableList = recursionTableList(GlobalConstants.ROOT_MENU_ID, menuList);
        return tableList;
    }


    /**
     * 递归生成菜单表格层级列表
     *
     * @param parentId 父级ID
     * @param menuList 菜单列表
     * @return
     */
    private static List<MenuVO> recursionTableList(Long parentId, List<SysMenu> menuList) {
        List<MenuVO> menuTableList = new ArrayList<>();
        Optional.ofNullable(menuList).orElse(new ArrayList<>())
                .stream()
                .filter(menu -> menu.getParentId().equals(parentId))
                .forEach(menu -> {
                    MenuVO menuVO = new MenuVO();
                    BeanUtil.copyProperties(menu, menuVO);
                    List<MenuVO> children = recursionTableList(menu.getId(), menuList);

                    if (CollectionUtil.isNotEmpty(children)) {
                        menuVO.setChildren(children);
                    }
                    menuTableList.add(menuVO);
                });
        return menuTableList;
    }


    /**
     * 菜单下拉（Select）层级列表
     *
     * @return
     */
    @Override
    public List<SelectVO> listSelect() {
        List<SysMenu> menuList = this.list(new LambdaQueryWrapper<SysMenu>().orderByAsc(SysMenu::getSort));
        List<SelectVO> menuSelectList = recursionSelectList(GlobalConstants.ROOT_MENU_ID, menuList);
        return menuSelectList;
    }


    /**
     * 递归生成菜单下拉层级列表
     *
     * @param parentId 父级ID
     * @param menuList 菜单列表
     * @return
     */
    private static List<SelectVO> recursionSelectList(Long parentId, List<SysMenu> menuList) {
        List<SelectVO> menuSelectList = new ArrayList<>();
        Optional.ofNullable(menuList).orElse(new ArrayList<>())
                .stream()
                .filter(menu -> menu.getParentId().equals(parentId))
                .forEach(menu -> {
                    SelectVO selectVO = new SelectVO(menu.getId(), menu.getName());
                    List<SelectVO> children = recursionSelectList(menu.getId(), menuList);
                    if (CollectionUtil.isNotEmpty(children)) {
                        selectVO.setChildren(children);
                    }
                    menuSelectList.add(selectVO);
                });
        return menuSelectList;
    }


    /**
     * 菜单路由（Route）层级列表
     * <p>
     * 读多写少，缓存至Redis
     *
     * @return
     * @Cacheable cacheNames:缓存名称，不同缓存的数据是彼此隔离； key: 缓存Key。
     */
    @Override
    @Cacheable(cacheNames = "system", key = "'routeList'")
    public List<RouteVO> listRoute() {
        List<SysMenu> menuList = this.baseMapper.listRoute();
        List<RouteVO> list = recursionRoute(GlobalConstants.ROOT_MENU_ID, menuList);
        return list;
    }


    /**
     * 递归生成菜单路由层级列表
     *
     * @param parentId 父级ID
     * @param menuList 菜单列表
     * @return
     */
    private List<RouteVO> recursionRoute(Long parentId, List<SysMenu> menuList) {
        List<RouteVO> list = new ArrayList<>();
        Optional.ofNullable(menuList).ifPresent(menus -> menus.stream().filter(menu -> menu.getParentId().equals(parentId))
                .forEach(menu -> {
                    RouteVO routeVO = new RouteVO();
                    routeVO.setName(menu.getId() + ""); // 根据name路由跳转 this.$router.push({path:xxx})
                    routeVO.setPath(menu.getPath()); // 根据path路由跳转 this.$router.push({name:xxx})
                    routeVO.setRedirect(menu.getRedirect());
                    routeVO.setComponent(menu.getComponent());
                    routeVO.setRedirect(menu.getRedirect());
                    RouteVO.Meta meta = new RouteVO.Meta(menu.getName(), menu.getIcon(), menu.getRoles());
                    routeVO.setMeta(meta);
                    // 菜单显示隐藏
                    routeVO.setHidden(!GlobalConstants.STATUS_ACTIVE.equals(menu.getStatus()));
                    List<RouteVO> children = recursionRoute(menu.getId(), menuList);
                    routeVO.setChildren(children);
                    if (CollectionUtil.isNotEmpty(children)) {
                        routeVO.setAlwaysShow(Boolean.TRUE); // 显示子节点
                    }
                    list.add(routeVO);
                }));
        return list;

    }

    /**
     * 菜单下拉（TreeSelect）层级列表
     *
     * @return
     */
    @Override
    public List<TreeSelectVO> listTreeSelect() {
        List<SysMenu> menuList = this.list(new LambdaQueryWrapper<SysMenu>().orderByAsc(SysMenu::getSort));
        List<TreeSelectVO> menuSelectList = recursionTreeSelectList(GlobalConstants.ROOT_MENU_ID, menuList);
        return menuSelectList;
    }


    /**
     * 递归生成菜单下拉(TreeSelect)层级列表
     *
     * @param parentId 父级ID
     * @param menuList 菜单列表
     * @return
     */
    private static List<TreeSelectVO> recursionTreeSelectList(Long parentId, List<SysMenu> menuList) {
        List<TreeSelectVO> menuSelectList = new ArrayList<>();
        Optional.ofNullable(menuList).orElse(new ArrayList<>())
                .stream()
                .filter(menu -> menu.getParentId().equals(parentId))
                .forEach(menu -> {
                    TreeSelectVO treeSelectVO = new TreeSelectVO(menu.getId(), menu.getName());
                    List<TreeSelectVO> children = recursionTreeSelectList(menu.getId(), menuList);
                    if (CollectionUtil.isNotEmpty(children)) {
                        treeSelectVO.setChildren(children);
                    }
                    menuSelectList.add(treeSelectVO);
                });
        return menuSelectList;
    }
}
