package com.saiyau.admin.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.saiyau.admin.pojo.entity.SysDictItem;
import com.saiyau.admin.mapper.SysDictItemMapper;
import com.saiyau.admin.service.SysDictItemService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 字典数据表 服务实现类
 * </p>
 *
 * @author liuzhongyuan
 * @since 2021-10-18
 */
@Service
public class SysDictItemServiceImpl extends ServiceImpl<SysDictItemMapper, SysDictItem> implements SysDictItemService {
    @Override
    public IPage<SysDictItem> list(Page<SysDictItem> page, SysDictItem dict) {
        List<SysDictItem> list = this.baseMapper.list(page,dict);
        page.setRecords(list);
        return page;
    }
}
