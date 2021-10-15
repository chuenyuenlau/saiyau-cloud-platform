# 用户表
DROP TABLE IF EXISTS `t_sys_user`;
CREATE TABLE `t_sys_user` (
    `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键id',
    `tenant_id` varchar(200) DEFAULT NULL COMMENT '租户id',
    `username` varchar(64) DEFAULT NULL COMMENT '用户名',
    `password` varchar(200) DEFAULT NULL COMMENT '密码',
    `name` varchar(20) DEFAULT NULL COMMENT '姓名',
    `nickname` varchar(20) DEFAULT NULL COMMENT '昵称',
    `sex` int(1) DEFAULT NULL COMMENT '性别： 0-女 1-男',
    `mobile` varchar(20) DEFAULT NULL COMMENT '手机号',
    `birthday` varchar(20) DEFAULT NULL COMMENT '生日',
    `email` varchar(100) DEFAULT NULL COMMENT '有限',
    `dept_id` bigint(20) DEFAULT NULL COMMENT '部门id',
    `avatar` varchar(255) DEFAULT NULL COMMENT '用户头像',
    `remark` varchar(400) DEFAULT NULL COMMENT '备注',
    `status` int(1) DEFAULT 0 COMMENT '状态：0-正常 1-禁用',
    `is_deleted` tinyint(1) DEFAULT 0 COMMENT '软删除标志：0-未删除 1-已删除',
    `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
    `create_user` bigint(20) DEFAULT NULL COMMENT '创建人id',
    `update_user` bigint(20) DEFAULT NULL COMMENT '修改人id',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT = '用户表';

# 角色表
DROP TABLE IF EXISTS `t_sys_role`;
CREATE TABLE `t_sys_role` (
    `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键id',
    `tenant_id` varchar(200) DEFAULT NULL COMMENT '租户id',
    `name` varchar(64) DEFAULT NULL COMMENT '名称',
    `code` varchar(64) DEFAULT NULL COMMENT '角色编码',
    `sort` int(11) DEFAULT NULL COMMENT '排序',
    `remark` varchar(400) DEFAULT NULL COMMENT '备注',
    `status` int(1) DEFAULT 0 COMMENT '状态：0-正常 1-禁用',
    `is_deleted` tinyint(1) DEFAULT 0 COMMENT '软删除标志：0-未删除 1-已删除',
    `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
    `create_user` bigint(20) DEFAULT NULL COMMENT '创建人id',
    `update_user` bigint(20) DEFAULT NULL COMMENT '修改人id',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT = '角色表';

# 菜单表
DROP TABLE IF EXISTS `t_sys_menu`;
CREATE TABLE `t_sys_menu` (
    `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键id',
    `tenant_id` varchar(200) DEFAULT NULL COMMENT '租户id',
    `parent_id` bigint(20) DEFAULT NULL COMMENT '父菜单id',
    `name` varchar(64) DEFAULT NULL COMMENT '菜单名称',
    `path` varchar(256) DEFAULT NULL COMMENT '路由路径',
    `component` varchar(256) DEFAULT NULL COMMENT '组件路径',
    `redirect` varchar(256) DEFAULT NULL COMMENT '跳转路径',
    `icon` varchar(64) DEFAULT NULL COMMENT '菜单图标',
    `sort` int(11) DEFAULT NULL COMMENT '排序',
    `status` int(1) DEFAULT 0 COMMENT '状态：0-正常 1-禁用',
    `is_deleted` tinyint(1) DEFAULT 0 COMMENT '软删除标志：0-未删除 1-已删除',
    `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
    `create_user` bigint(20) DEFAULT NULL COMMENT '创建人id',
    `update_user` bigint(20) DEFAULT NULL COMMENT '修改人id',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT = '菜单表';

# 部门表
DROP TABLE IF EXISTS `t_sys_dept`;
CREATE TABLE `t_sys_dept` (
    `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键id',
    `tenant_id` varchar(200) DEFAULT NULL COMMENT '租户id',
    `parent_id` bigint(20) DEFAULT NULL COMMENT '父节点id',
    `name` varchar(64) DEFAULT NULL COMMENT '部门名称',
    `tree_path` varchar(256) DEFAULT NULL COMMENT '父节点id路径',
    `sort` int(11) DEFAULT NULL COMMENT '排序',
    `status` int(1) DEFAULT 0 COMMENT '状态：0-正常 1-禁用',
    `is_deleted` tinyint(1) DEFAULT 0 COMMENT '软删除标志：0-未删除 1-已删除',
    `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
    `create_user` bigint(20) DEFAULT NULL COMMENT '创建人id',
    `update_user` bigint(20) DEFAULT NULL COMMENT '修改人id',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT = '部门表';

# 权限表
DROP TABLE IF EXISTS `t_sys_permission`;
CREATE TABLE `t_sys_permission` (
    `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键id',
    `tenant_id` varchar(200) DEFAULT NULL COMMENT '租户id',
    `name` varchar(64) DEFAULT NULL COMMENT '权限名称',
    `menu_id` bigint(20) DEFAULT NULL COMMENT '菜单id',
    `url_perm` varchar(256) DEFAULT NULL COMMENT '菜单权限标识',
    `btn_perm` varchar(256) DEFAULT NULL COMMENT '按钮权限标识',
    `is_deleted` tinyint(1) DEFAULT 0 COMMENT '软删除标志：0-未删除 1-已删除',
    `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
    `create_user` bigint(20) DEFAULT NULL COMMENT '创建人id',
    `update_user` bigint(20) DEFAULT NULL COMMENT '修改人id',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT = '权限表';

# 用户角色表
DROP TABLE IF EXISTS `t_sys_user_role`;
CREATE TABLE `t_sys_user_role` (
    `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键id',
    `tenant_id` varchar(200) DEFAULT NULL COMMENT '租户id',
    `role_id` bigint(64) DEFAULT NULL COMMENT '角色id',
    `user_id` bigint(20) DEFAULT NULL COMMENT '用户id',
    `url_perm` varchar(256) DEFAULT NULL COMMENT '菜单权限标识',
    `btn_perm` varchar(256) DEFAULT NULL COMMENT '按钮权限标识',
    `is_deleted` tinyint(1) DEFAULT 0 COMMENT '软删除标志：0-未删除 1-已删除',
    `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
    `create_user` bigint(20) DEFAULT NULL COMMENT '创建人id',
    `update_user` bigint(20) DEFAULT NULL COMMENT '修改人id',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT = '用户角色表';

# 角色菜单表
DROP TABLE IF EXISTS `t_sys_role_menu`;
CREATE TABLE `t_sys_role_menu` (
    `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键id',
    `tenant_id` varchar(200) DEFAULT NULL COMMENT '租户id',
    `role_id` bigint(64) DEFAULT NULL COMMENT '角色id',
    `menu_id` bigint(20) DEFAULT NULL COMMENT '菜单id',
    `is_deleted` tinyint(1) DEFAULT 0 COMMENT '软删除标志：0-未删除 1-已删除',
    `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
    `create_user` bigint(20) DEFAULT NULL COMMENT '创建人id',
    `update_user` bigint(20) DEFAULT NULL COMMENT '修改人id',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT = '角色菜单表';

# 角色权限表
DROP TABLE IF EXISTS `t_sys_role_permission`;
CREATE TABLE `t_sys_role_permission` (
    `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键id',
    `tenant_id` varchar(200) DEFAULT NULL COMMENT '租户id',
    `role_id` bigint(64) DEFAULT NULL COMMENT '角色id',
    `permission_id` bigint(20) DEFAULT NULL COMMENT '权限id',
    `is_deleted` tinyint(1) DEFAULT 0 COMMENT '软删除标志：0-未删除 1-已删除',
    `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
    `create_user` bigint(20) DEFAULT NULL COMMENT '创建人id',
    `update_user` bigint(20) DEFAULT NULL COMMENT '修改人id',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT = '角色权限表';

# 字典类型表
DROP TABLE IF EXISTS `t_sys_dict`;
CREATE TABLE `t_sys_dict` (
    `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键id',
    `tenant_id` varchar(200) DEFAULT NULL COMMENT '租户id',
    `name` varchar(100) DEFAULT NULL COMMENT '字典类型名称',
    `code` varchar(50) DEFAULT NULL COMMENT '字典类型编码',
    `remark` varchar(400) DEFAULT NULL COMMENT '备注',
    `status` int(1) DEFAULT 0 NULL COMMENT '状态：0-正常 1-禁用',
    `is_deleted` tinyint(1) DEFAULT 0 COMMENT '软删除标志：0-未删除 1-已删除',
    `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
    `create_user` bigint(20) DEFAULT NULL COMMENT '创建人id',
    `update_user` bigint(20) DEFAULT NULL COMMENT '修改人id',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT = '字典类型表';

# 字典数据表
DROP TABLE IF EXISTS `t_sys_dict_item`;
CREATE TABLE `t_sys_dict_item` (
    `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键id',
    `tenant_id` varchar(200) DEFAULT NULL COMMENT '租户id',
    `name` varchar(100) DEFAULT NULL COMMENT '字典项名称',
    `value` varchar(100) DEFAULT NULL COMMENT '字典项值',
    `dict_code` varchar(50) DEFAULT NULL COMMENT '字典类型编码',
    `defaulted` tinyint(1) DEFAULT 0 COMMENT '是否默认：0-否 1-是',
    `sort` int(11) DEFAULT NULL COMMENT '排序',
    `remark` varchar(400) DEFAULT NULL COMMENT '备注',
    `status` int(1) DEFAULT 0 NULL COMMENT '状态：0-正常 1-禁用',
    `is_deleted` tinyint(1) DEFAULT 0 COMMENT '软删除标志：0-未删除 1-已删除',
    `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
    `create_user` bigint(20) DEFAULT NULL COMMENT '创建人id',
    `update_user` bigint(20) DEFAULT NULL COMMENT '修改人id',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT = '字典数据表';