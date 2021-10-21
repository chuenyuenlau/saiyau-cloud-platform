package com.saiyau.admin.controller;


import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.saiyau.admin.pojo.entity.OauthClientDetails;
import com.saiyau.admin.service.OauthClientDetailsService;
import com.saiyau.common.result.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

/**
 * <p>
 * oauth2 客户端信息表 前端控制器
 * </p>
 *
 * @author liuzhongyuan
 * @since 2021-10-21
 */
@Api(tags = "oauth客户端接口")
@RestController
@RequestMapping("/api/v1/oauth-clients")
@RequiredArgsConstructor
@Slf4j
public class OauthClientDetailsController {
    OauthClientDetailsService oauthClientDetailsService;
    
    @ApiOperation(value = "列表分页")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "页码", paramType = "query", dataType = "Long"),
            @ApiImplicitParam(name = "limit", value = "每页数量", paramType = "query", dataType = "Long"),
            @ApiImplicitParam(name = "clientId", value = "客户端ID", paramType = "query", dataType = "String")
    })
    @GetMapping
    public R list(Integer page,
                  Integer limit,
                  String clientId) {
        IPage<OauthClientDetails> result = oauthClientDetailsService.page(
                new Page<>(page, limit),
                new LambdaQueryWrapper<OauthClientDetails>()
                        .like(StrUtil.isNotBlank(clientId), OauthClientDetails::getClientId, clientId));
        return R.success(result.getRecords());
    }

    @ApiOperation(value = "客户端详情")
    @ApiImplicitParam(name = "clientId", value = "客户端id", required = true, paramType = "path", dataType = "String")
    @GetMapping("/{clientId}")
    public R detail(@PathVariable String clientId) {
        OauthClientDetails client = oauthClientDetailsService.getById(clientId);
        return R.success(client);
    }

    @ApiOperation(value = "新增客户端")
    @ApiImplicitParam(name = "client", value = "实体JSON对象", required = true, paramType = "body", dataType = "OauthClientDetails")
    @PostMapping
    public R add(@RequestBody OauthClientDetails client) {
        boolean status = oauthClientDetailsService.save(client);
        return R.judge(status);
    }

    @ApiOperation(value = "修改客户端")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "clientId", value = "客户端id", required = true, paramType = "path", dataType = "String"),
            @ApiImplicitParam(name = "client", value = "实体JSON对象", required = true, paramType = "body", dataType = "OauthClientDetails")
    })
    @PutMapping(value = "/{clientId}")
    public R update(
            @PathVariable String clientId,
            @RequestBody OauthClientDetails client) {
        boolean status = oauthClientDetailsService.updateById(client);
        return R.judge(status);
    }

    @ApiOperation(value = "删除客户端")
    @ApiImplicitParam(name = "ids", value = "id集合,以,拼接字符串", required = true, paramType = "query", dataType = "String")
    @DeleteMapping("/{ids}")
    public R delete(@PathVariable("ids") String ids) {
        boolean status = oauthClientDetailsService.removeByIds(Arrays.asList(ids.split(",")));
        return R.judge(status);
    }
}
