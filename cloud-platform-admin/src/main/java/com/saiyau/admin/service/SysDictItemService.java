package com.saiyau.admin.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.saiyau.admin.pojo.entity.SysDictItem;

/**
 * <p>
 * 字典数据表 服务类
 * </p>
 *
 * @author liuzhongyuan
 * @since 2021-10-18
 */
public interface SysDictItemService extends IService<SysDictItem> {

    public IPage<SysDictItem> list(Page<SysDictItem> page, SysDictItem dict);

}
