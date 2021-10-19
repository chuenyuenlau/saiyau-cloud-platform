package com.saiyau.admin.controller;


import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.saiyau.admin.pojo.entity.SysDict;
import com.saiyau.admin.pojo.entity.SysDictItem;
import com.saiyau.admin.service.SysDictItemService;
import com.saiyau.admin.service.SysDictService;
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
import java.util.stream.Collectors;

/**
 * <p>
 * 字典类型表 前端控制器
 * </p>
 *
 * @author liuzhongyuan
 * @since 2021-10-18
 */
@Api(tags = "数据字典接口")
@RestController
@RequestMapping("/api/v1/dicts")
@RequiredArgsConstructor
public class SysDictController {
    private final SysDictService sysDictService;
    private final SysDictItemService sysDictItemService;

    @ApiOperation(value = "列表分页")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "页码", paramType = "query", dataType = "Integer"),
            @ApiImplicitParam(name = "limit", value = "每页数量", paramType = "query", dataType = "Integer"),
            @ApiImplicitParam(name = "name", value = "字典名称", paramType = "query", dataType = "String"),
    })
    @GetMapping("/page")
    public R list(Integer page, Integer limit, String name) {
        Page<SysDict> result = sysDictService.page(new Page<>(page, limit), new LambdaQueryWrapper<SysDict>()
                .like(StrUtil.isNotBlank(name), SysDict::getName, StrUtil.trimToNull(name))
                .orderByDesc(SysDict::getUpdateTime)
                .orderByDesc(SysDict::getCreateTime));
        return R.success(result.getRecords());
    }


    @ApiOperation(value = "字典列表")
    @GetMapping
    public R list() {
        List<SysDict> list = sysDictService.list( new LambdaQueryWrapper<SysDict>()
                .orderByDesc(SysDict::getUpdateTime)
                .orderByDesc(SysDict::getCreateTime));
        return R.success(list);
    }


    @ApiOperation(value = "字典详情")
    @ApiImplicitParam(name = "id", value = "字典id", required = true, paramType = "path", dataType = "Long")
    @GetMapping("/{id}")
    public R detail(@PathVariable Integer id) {
        SysDict dict = sysDictService.getById(id);
        return R.success(dict);
    }

    @ApiOperation(value = "新增字典")
    @ApiImplicitParam(name = "dictItem", value = "实体JSON对象", required = true, paramType = "body", dataType = "SysDictItem")
    @PostMapping
    public R add(@RequestBody SysDict dict) {
        boolean status = sysDictService.save(dict);
        return R.judge(status);
    }

    @ApiOperation(value = "修改字典")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "字典id", required = true, paramType = "path", dataType = "Long"),
            @ApiImplicitParam(name = "dictItem", value = "实体JSON对象", required = true, paramType = "body", dataType = "SysDictItem")
    })
    @PutMapping(value = "/{id}")
    public R update(
            @PathVariable Long id,
            @RequestBody SysDict dict) {

        boolean status = sysDictService.updateById(dict);
        if (status) {
            SysDict dbDict = sysDictService.getById(id);
            // 字典code更新，同步更新字典项code
            if (!StrUtil.equals(dbDict.getCode(), dict.getCode())) {
                sysDictItemService.update(new LambdaUpdateWrapper<SysDictItem>().eq(SysDictItem::getDictCode, dbDict.getCode())
                        .set(SysDictItem::getDictCode, dict.getCode()));
            }
        }
        return R.judge(status);
    }

    @ApiOperation(value = "删除字典")
    @ApiImplicitParam(name = "ids", value = "以,分割拼接字符串", required = true, paramType = "query", dataType = "String")
    @DeleteMapping("/{ids}")
    public R delete(@PathVariable String ids) {
        List<String> idList = Arrays.asList(ids.split(","));
        List<String> codeList = sysDictService.listByIds(idList).stream().map(SysDict::getCode).collect(Collectors.toList());
        if (CollectionUtil.isNotEmpty(codeList)) {
            int count = sysDictItemService.count(new LambdaQueryWrapper<SysDictItem>().in(SysDictItem::getDictCode, codeList));
            Assert.isTrue(count == 0, "删除字典失败，请先删除关联字典数据");
        }
        boolean status = sysDictService.removeByIds(idList);
        return R.judge(status);
    }

    @ApiOperation(value = "选择性更新字典")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "用户ID", required = true, paramType = "path", dataType = "Long"),
            @ApiImplicitParam(name = "dictItem", value = "实体JSON对象", required = true, paramType = "body", dataType = "SysDictItem")
    })
    @PatchMapping(value = "/{id}")
    public R patch(@PathVariable Long id, @RequestBody SysDict dict) {
        LambdaUpdateWrapper<SysDict> updateWrapper = new LambdaUpdateWrapper<SysDict>().eq(SysDict::getId, id);
        updateWrapper.set(dict.getStatus() != null, SysDict::getStatus, dict.getStatus());
        boolean update = sysDictService.update(updateWrapper);
        return R.success(update);
    }
}
