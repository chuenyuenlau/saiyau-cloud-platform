package com.saiyau.admin.controller;


import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.saiyau.admin.pojo.entity.SysDictItem;
import com.saiyau.admin.service.SysDictItemService;
import com.saiyau.common.result.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

/**
 * <p>
 * 字典数据表 前端控制器
 * </p>
 *
 * @author liuzhongyuan
 * @since 2021-10-18
 */
@Api(tags = "数据字典项接口")
@RestController
@RequestMapping("/api/v1/dictItems")
@RequiredArgsConstructor
public class SysDictItemController {
    private final SysDictItemService sysDictItemService;

    @ApiOperation(value = "列表分页")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", defaultValue = "1", value = "页码", paramType = "query", dataType = "Integer"),
            @ApiImplicitParam(name = "limit", defaultValue = "10", value = "每页数量", paramType = "query", dataType = "Integer"),
            @ApiImplicitParam(name = "name", value = "字典名称", paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "dictCode", value = "字典编码", paramType = "query", dataType = "String")
    })
    @GetMapping("/page")
    public R getPageList(
            Integer page,
            Integer limit,
            String name,
            String dictCode
    ) {
        IPage<SysDictItem> result = sysDictItemService.list(new Page<>(page, limit),
                new SysDictItem().setName(name).setDictCode(dictCode));
        return R.success(result.getRecords());
    }

    @ApiOperation(value = "字典项列表")
    @GetMapping
    public R list(String name, String dictCode) {
        List<SysDictItem> list = sysDictItemService.list(
                new LambdaQueryWrapper<SysDictItem>()
                        .like(StrUtil.isNotBlank(name), SysDictItem::getName, name)
                        .eq(StrUtil.isNotBlank(dictCode), SysDictItem::getDictCode, dictCode)
                        .select(SysDictItem::getName, SysDictItem::getValue)
                        .orderByAsc(SysDictItem::getSort)
        );
        return R.success(list);
    }

    @ApiOperation(value = "字典项详情")
    @ApiImplicitParam(name = "id", value = "字典id", required = true, paramType = "path", dataType = "Long")
    @GetMapping("/{id}")
    public R detail(@PathVariable Integer id) {
        SysDictItem dictItem = sysDictItemService.getById(id);
        return R.success(dictItem);
    }

    @ApiOperation(value = "新增字典项")
    @ApiImplicitParam(name = "dictItem", value = "实体JSON对象", required = true, paramType = "body", dataType = "SysDictItem")
    @PostMapping
    public R add(@RequestBody SysDictItem dictItem) {
        boolean status = sysDictItemService.save(dictItem);
        return R.judge(status);
    }

    @ApiOperation(value = "修改字典项")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "字典id", required = true, paramType = "path", dataType = "Long"),
            @ApiImplicitParam(name = "dictItem", value = "实体JSON对象", required = true, paramType = "body", dataType = "SysDictItem")
    })
    @PutMapping(value = "/{id}")
    public R update(
            @PathVariable Long id,
            @RequestBody SysDictItem dictItem) {
        boolean status = sysDictItemService.updateById(dictItem);
        return R.judge(status);
    }

    @ApiOperation(value = "删除字典数据")
    @ApiImplicitParam(name = "ids", value = "主键ID集合，以,分割拼接字符串", required = true, paramType = "query", dataType = "String")
    @DeleteMapping("/{ids}")
    public R delete(@PathVariable String ids) {
        boolean status = sysDictItemService.removeByIds(Arrays.asList(ids.split(",")));
        return R.judge(status);
    }


    @ApiOperation(value = "选择性更新字典数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "用户ID", required = true, paramType = "path", dataType = "Long"),
            @ApiImplicitParam(name = "dictItem", value = "实体JSON对象", required = true, paramType = "body", dataType = "SysDictItem")
    })
    @PatchMapping(value = "/{id}")
    public R patch(@PathVariable Integer id, @RequestBody SysDictItem dictItem) {
        LambdaUpdateWrapper<SysDictItem> updateWrapper = new LambdaUpdateWrapper<SysDictItem>().eq(SysDictItem::getId, id);
        updateWrapper.set(dictItem.getStatus() != null, SysDictItem::getStatus, dictItem.getStatus());
        boolean status = sysDictItemService.update(updateWrapper);
        return R.judge(status);
    }
}
