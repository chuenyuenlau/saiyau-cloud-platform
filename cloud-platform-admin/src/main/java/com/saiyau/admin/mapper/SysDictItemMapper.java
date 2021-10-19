package com.saiyau.admin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.saiyau.admin.pojo.entity.SysDictItem;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 字典数据表 Mapper 接口
 * </p>
 *
 * @author liuzhongyuan
 * @since 2021-10-18
 */
public interface SysDictItemMapper extends BaseMapper<SysDictItem> {
    List<SysDictItem> list(Page<SysDictItem> page, @Param("dictItem") SysDictItem dictItem);
}
