package com.saiyau.admin.controller;


import com.saiyau.admin.pojo.entity.SysDept;
import com.saiyau.admin.pojo.vo.DeptVO;
import com.saiyau.admin.pojo.vo.TreeSelectVO;
import com.saiyau.admin.service.SysDeptService;
import com.saiyau.common.result.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 部门表 前端控制器
 * </p>
 *
 * @author liuzhongyuan
 * @since 2021-10-18
 */
@Api(tags = "部门接口")
@RestController
@RequestMapping("/api/v1/depts")
@AllArgsConstructor
public class SysDeptController {
    private final SysDeptService deptService;

    @ApiOperation(value = "部门表格（Table）层级列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "name", value = "部门名称", paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "status", value = "部门状态", paramType = "query", dataType = "Long"),
    })
    @GetMapping("/table")
    public R getTableList(Integer status, String name) {
        List<DeptVO> deptTableList = deptService.listTable(status, name);
        return R.success(deptTableList);
    }

    @ApiOperation(value = "部门下拉（TreeSelect）层级列表")
    @GetMapping("/select")
    public R getSelectList() {
        List<TreeSelectVO> deptSelectList = deptService.listTreeSelect();
        return R.success(deptSelectList);
    }

    @ApiOperation(value = "部门详情")
    @ApiImplicitParam(name = "id", value = "部门id", required = true, paramType = "path", dataType = "Long")
    @GetMapping("/{id}")
    public R detail(@PathVariable Long id) {
        SysDept sysDept = deptService.getById(id);
        return R.success(sysDept);
    }

    @ApiOperation(value = "新增部门")
    @PostMapping
    public R add(@RequestBody SysDept dept) {
        Long id = deptService.saveDept(dept);
        return R.success(id);
    }

    @ApiOperation(value = "修改部门")
    @PutMapping(value = "/{id}")
    public R update(@PathVariable Long id, @RequestBody SysDept dept) {
        dept.setId(id);
        Long deptId = deptService.saveDept(dept);
        return R.success(deptId);
    }

    @ApiOperation(value = "删除部门")
    @ApiImplicitParam(name = "ids", value = "部门ID，多个以英文逗号,分割拼接", required = true, paramType = "query", dataType = "String")
    @DeleteMapping("/{ids}")
    public R delete(@PathVariable("ids") String ids) {
        boolean status = deptService.deleteByIds(ids);
        return R.judge(status);
    }

}
