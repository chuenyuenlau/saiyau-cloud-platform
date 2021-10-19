package com.saiyau.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.saiyau.admin.pojo.entity.SysDept;
import com.saiyau.admin.pojo.vo.DeptVO;
import com.saiyau.admin.pojo.vo.TreeSelectVO;

import java.util.List;

/**
 * <p>
 * 部门表 服务类
 * </p>
 *
 * @author liuzhongyuan
 * @since 2021-10-18
 */
public interface SysDeptService extends IService<SysDept> {
    /**
     * 部门表格（Table）层级列表
     *
     * @param status 部门状态： 1-开启 0-禁用
     * @param name
     * @return
     */
    List<DeptVO> listTable(Integer status, String name);

    /**
     * 部门树形下拉（TreeSelect）层级列表
     *
     * @return
     */
    List<TreeSelectVO> listTreeSelect();

    /**
     * 新增/修改部门
     *
     * @param dept
     * @return
     */
    Long saveDept(SysDept dept);

    /**
     * 删除部门
     *
     * @param ids 部门ID，多个以英文逗号,拼接字符串
     * @return
     */
    boolean deleteByIds(String ids);

}
