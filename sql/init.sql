
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `id` bigint(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `user_name` varchar(16) NOT NULL COMMENT '用户名',
  `account` varchar(16) NOT NULL COMMENT '账号',
  `password` varchar(32) NOT NULL COMMENT '加密密码',
  `salt` varchar(32) NOT NULL COMMENT '密码加盐参数',
  `status` tinyint(1) DEFAULT '1' COMMENT '用户状态 0关闭 1开启',
  `role_id` bigint(10) DEFAULT NULL COMMENT '角色ID',
  `super_admin` tinyint(1) DEFAULT '0' COMMENT '是否是超级管理员 0普通 1超级 ',
  `create_date` datetime NOT NULL COMMENT '创建时间',
  `update_date` datetime DEFAULT NULL COMMENT '最近更新时间',
  `del_flag` tinyint(1) DEFAULT '0' COMMENT '删除标志位 1删除 0未删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COMMENT='系统用户表';
ALTER TABLE sys_user ADD INDEX index_del_flag (del_flag);


DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role` (
  `id` bigint(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `role_name` varchar(32) NOT NULL COMMENT '角色描述',
  `status` tinyint(1) DEFAULT '1' COMMENT '用户状态 0关闭 1开启',
  `create_date` datetime NOT NULL COMMENT '创建时间',
  `update_date` datetime DEFAULT NULL COMMENT '最近更新时间',
  `del_flag` tinyint(1) DEFAULT '0' COMMENT '删除标志位 1删除 0未删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色表';
ALTER TABLE sys_role ADD INDEX index_del_flag (del_flag);


DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu` (
  `id` bigint(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增id',
  `menu_name` varchar(16) NOT NULL COMMENT '菜单名称',
  `menu_level` tinyint(1) unsigned NOT NULL COMMENT '菜单级别',
  `menu_url` varchar(128) DEFAULT NULL COMMENT '菜单url',
  `parent_menu_id` bigint(11) unsigned DEFAULT '0' COMMENT '父菜单id',
  `priority` int(5) unsigned NOT NULL COMMENT '显示优先级',
  `permission` varchar(64) DEFAULT '' COMMENT '权限',
  `target` varchar(10) default 'menuItem' comment '打开方式（menuItem页签 menuBlank新窗口）',
  `visible` tinyint(1) default 0 comment '菜单状态（0显示 1隐藏）',
  `is_refresh` tinyint(1) default 1 comment '是否刷新（0刷新 1不刷新）',
  `icon` varchar(32) default '#' comment '菜单图标',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  `update_date` datetime DEFAULT NULL COMMENT '最近更新时间',
  `del_flag` tinyint(1) DEFAULT '0' COMMENT '删除标志位 1删除 0未删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COMMENT='菜单表';
ALTER TABLE sys_menu ADD INDEX index_del_flag (del_flag);


DROP TABLE IF EXISTS `sys_menu_role_relation`;
CREATE TABLE `sys_menu_role_relation` (
  `id` bigint(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增id',
  `menu_id` bigint(11) NOT NULL COMMENT '用户ID',
  `role_id` bigint(11) NOT NULL COMMENT '角色ID',
  `create_date` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='菜单-角色关系表';



CREATE TABLE `sys_log` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '自增id',
  `request_desc` varchar(32) DEFAULT NULL COMMENT '请求描述',
  `request_url` varchar(64) DEFAULT NULL COMMENT '请求url',
  `method_type` tinyint(1) DEFAULT NULL COMMENT '请求方法 1post 2get ',
  `request_ip` varchar(32) DEFAULT NULL COMMENT '请求ip',
  `request_param` varchar(1200) DEFAULT NULL COMMENT '请求参数',
  `target_method` varchar(32) DEFAULT NULL COMMENT '接口方法',
  `log_type` tinyint(1) NOT NULL COMMENT '1 请求日志 2:异常日志',
  `interface_type` tinyint(1) NOT NULL COMMENT '接口类型 1:后台日志 ',
  `create_id` bigint(11) DEFAULT NULL COMMENT '创建者id',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  `exception` varchar(1200) DEFAULT NULL COMMENT '异常日志',
  `request_id` varchar(32) DEFAULT NULL COMMENT '日志id',
  `del_flag` tinyint(1) DEFAULT '0' COMMENT '删除标志位 1删除 0未删除',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='系统日志';
ALTER TABLE sys_log ADD INDEX index_del_flagAndinterface_type (del_flag,interface_type);
ALTER TABLE sys_log ADD INDEX index_create_id (create_id);


INSERT INTO `sys_user` (`id`, `user_name`, `account`, `password`, `salt`, `status`, `role_id`, `super_admin`, `create_date`, `update_date`, `del_flag`) VALUES ('1', '超级管理员', 'mars', '83cb9029ff90e67632b07687b65dab10', 'e31917406be54ff799c53360569a9b87', '1', NULL, '1', '2021-04-17 23:55:46', '2021-04-18 08:54:36', '0');


INSERT INTO `sys_menu` (`id`, `menu_name`, `menu_level`, `menu_url`, `parent_menu_id`, `priority`, `permission`, `target`, `visible`, `is_refresh`, `icon`, `create_date`, `update_date`, `del_flag`) VALUES ('1', '用户管理', '1', '', '0', '100', '', 'menuItem', '0', '1', 'fa fa-user', '2019-12-08 13:29:27', '2021-02-24 13:43:17', '0');
INSERT INTO `sys_menu` (`id`, `menu_name`, `menu_level`, `menu_url`, `parent_menu_id`, `priority`, `permission`, `target`, `visible`, `is_refresh`, `icon`, `create_date`, `update_date`, `del_flag`) VALUES ('2', '用户列表', '2', '/sys/user/index', '1', '101', 'sys:user:index', 'menuItem', '0', '1', 'fa fa-user', '2019-12-08 13:29:49', '2021-04-26 08:36:52', '0');
INSERT INTO `sys_menu` (`id`, `menu_name`, `menu_level`, `menu_url`, `parent_menu_id`, `priority`, `permission`, `target`, `visible`, `is_refresh`, `icon`, `create_date`, `update_date`, `del_flag`) VALUES ('3', '菜单管理', '1', '', '0', '200', '', 'menuItem', '1', '1', 'fa fa-reorder', '2019-12-08 14:20:46', '2021-02-24 13:44:37', '0');
INSERT INTO `sys_menu` (`id`, `menu_name`, `menu_level`, `menu_url`, `parent_menu_id`, `priority`, `permission`, `target`, `visible`, `is_refresh`, `icon`, `create_date`, `update_date`, `del_flag`) VALUES ('4', '菜单列表', '2', '/sys/menu/index', '3', '201', 'sys:menu:index', 'menuItem', '1', '1', '#', '2019-12-08 14:21:08', '2021-03-01 14:23:46', '0');
INSERT INTO `sys_menu` (`id`, `menu_name`, `menu_level`, `menu_url`, `parent_menu_id`, `priority`, `permission`, `target`, `visible`, `is_refresh`, `icon`, `create_date`, `update_date`, `del_flag`) VALUES ('8', '权限管理', '1', '', '0', '400', '', 'menuItem', '0', '1', 'fa fa-user-secret', '2019-12-08 15:56:09', '2021-02-24 13:46:14', '0');
INSERT INTO `sys_menu` (`id`, `menu_name`, `menu_level`, `menu_url`, `parent_menu_id`, `priority`, `permission`, `target`, `visible`, `is_refresh`, `icon`, `create_date`, `update_date`, `del_flag`) VALUES ('9', '权限列表', '2', '/sys/role/index', '8', '401', 'sys:role:index', 'menuItem', '0', '1', '#', '2019-12-08 15:56:37', '2021-04-26 09:13:51', '0');
INSERT INTO `sys_menu` (`id`, `menu_name`, `menu_level`, `menu_url`, `parent_menu_id`, `priority`, `permission`, `target`, `visible`, `is_refresh`, `icon`, `create_date`, `update_date`, `del_flag`) VALUES ('13', '用户列表-修改密码', '3', '', '2', '102', 'sys:user:resetPwd', 'menuItem', '0', '1', '#', '2020-09-12 21:43:55', '2021-02-24 11:48:55', '0');
INSERT INTO `sys_menu` (`id`, `menu_name`, `menu_level`, `menu_url`, `parent_menu_id`, `priority`, `permission`, `target`, `visible`, `is_refresh`, `icon`, `create_date`, `update_date`, `del_flag`) VALUES ('14', '用户列表-新增用户', '3', '', '2', '103', 'sys:user:insert', 'menuItem', '0', '1', '#', '2020-09-12 21:45:05', NULL, '0');
INSERT INTO `sys_menu` (`id`, `menu_name`, `menu_level`, `menu_url`, `parent_menu_id`, `priority`, `permission`, `target`, `visible`, `is_refresh`, `icon`, `create_date`, `update_date`, `del_flag`) VALUES ('15', '用户列表-查看详情', '3', '', '2', '104', 'sys:user:selectById', 'menuItem', '0', '1', '#', '2020-09-12 21:45:41', '2021-04-18 13:39:47', '0');
INSERT INTO `sys_menu` (`id`, `menu_name`, `menu_level`, `menu_url`, `parent_menu_id`, `priority`, `permission`, `target`, `visible`, `is_refresh`, `icon`, `create_date`, `update_date`, `del_flag`) VALUES ('16', '用户列表-删除用户', '3', '', '2', '105', 'sys:user:deleteById', 'menuItem', '0', '1', '#', '2020-09-12 21:54:11', NULL, '0');
INSERT INTO `sys_menu` (`id`, `menu_name`, `menu_level`, `menu_url`, `parent_menu_id`, `priority`, `permission`, `target`, `visible`, `is_refresh`, `icon`, `create_date`, `update_date`, `del_flag`) VALUES ('17', '用户列表-更新用户', '3', '', '2', '106', 'sys:user:updateById', 'menuItem', '0', '1', '#', '2020-09-12 21:55:55', '2020-09-12 22:39:10', '0');
INSERT INTO `sys_menu` (`id`, `menu_name`, `menu_level`, `menu_url`, `parent_menu_id`, `priority`, `permission`, `target`, `visible`, `is_refresh`, `icon`, `create_date`, `update_date`, `del_flag`) VALUES ('18', '用户列表-查询全部', '3', '', '2', '107', 'sys:user:page', 'menuItem', '0', '1', '#', '2020-09-12 21:56:51', NULL, '0');
INSERT INTO `sys_menu` (`id`, `menu_name`, `menu_level`, `menu_url`, `parent_menu_id`, `priority`, `permission`, `target`, `visible`, `is_refresh`, `icon`, `create_date`, `update_date`, `del_flag`) VALUES ('19', '菜单列表-新增', '3', '', '4', '202', 'sys:menu:insert', 'menuItem', '1', '1', '#', '2020-09-12 22:34:05', NULL, '0');
INSERT INTO `sys_menu` (`id`, `menu_name`, `menu_level`, `menu_url`, `parent_menu_id`, `priority`, `permission`, `target`, `visible`, `is_refresh`, `icon`, `create_date`, `update_date`, `del_flag`) VALUES ('20', '菜单列表-更新', '3', '', '4', '203', 'sys:menu:updateById', 'menuItem', '1', '1', '#', '2020-09-12 22:35:35', NULL, '0');
INSERT INTO `sys_menu` (`id`, `menu_name`, `menu_level`, `menu_url`, `parent_menu_id`, `priority`, `permission`, `target`, `visible`, `is_refresh`, `icon`, `create_date`, `update_date`, `del_flag`) VALUES ('21', '菜单列表-删除', '3', '', '4', '204', 'sys:menu:deleteById', 'menuItem', '1', '1', '#', '2020-09-12 22:36:17', NULL, '0');
INSERT INTO `sys_menu` (`id`, `menu_name`, `menu_level`, `menu_url`, `parent_menu_id`, `priority`, `permission`, `target`, `visible`, `is_refresh`, `icon`, `create_date`, `update_date`, `del_flag`) VALUES ('22', '菜单列表-查看详情', '3', '', '4', '205', 'sys:menu:selectById', 'menuItem', '1', '1', '#', '2020-09-12 22:37:23', NULL, '0');
INSERT INTO `sys_menu` (`id`, `menu_name`, `menu_level`, `menu_url`, `parent_menu_id`, `priority`, `permission`, `target`, `visible`, `is_refresh`, `icon`, `create_date`, `update_date`, `del_flag`) VALUES ('23', '菜单列表-分页列表', '3', '', '4', '206', 'sys:menu:page', 'menuItem', '1', '1', '#', '2020-09-12 22:37:59', NULL, '0');
INSERT INTO `sys_menu` (`id`, `menu_name`, `menu_level`, `menu_url`, `parent_menu_id`, `priority`, `permission`, `target`, `visible`, `is_refresh`, `icon`, `create_date`, `update_date`, `del_flag`) VALUES ('34', '权限列表-列表分页', '3', '', '9', '402', 'sys:role:page', 'menuItem', '0', '1', '#', '2020-09-12 23:32:52', NULL, '0');
INSERT INTO `sys_menu` (`id`, `menu_name`, `menu_level`, `menu_url`, `parent_menu_id`, `priority`, `permission`, `target`, `visible`, `is_refresh`, `icon`, `create_date`, `update_date`, `del_flag`) VALUES ('35', '权限列表-查看详情', '3', '', '9', '403', 'sys:role:selectById', 'menuItem', '0', '1', '#', '2020-09-12 23:33:24', NULL, '0');
INSERT INTO `sys_menu` (`id`, `menu_name`, `menu_level`, `menu_url`, `parent_menu_id`, `priority`, `permission`, `target`, `visible`, `is_refresh`, `icon`, `create_date`, `update_date`, `del_flag`) VALUES ('36', '权限列表-新增', '3', '', '9', '404', 'sys:role:insert', 'menuItem', '0', '1', '#', '2020-09-12 23:33:56', NULL, '0');
INSERT INTO `sys_menu` (`id`, `menu_name`, `menu_level`, `menu_url`, `parent_menu_id`, `priority`, `permission`, `target`, `visible`, `is_refresh`, `icon`, `create_date`, `update_date`, `del_flag`) VALUES ('37', '权限列表-更新', '3', '', '9', '405', 'sys:role:updateById', 'menuItem', '0', '1', '#', '2020-09-12 23:34:24', NULL, '0');
INSERT INTO `sys_menu` (`id`, `menu_name`, `menu_level`, `menu_url`, `parent_menu_id`, `priority`, `permission`, `target`, `visible`, `is_refresh`, `icon`, `create_date`, `update_date`, `del_flag`) VALUES ('38', '权限列表-删除', '3', '', '9', '406', 'sys:role:deleteById', 'menuItem', '0', '1', '#', '2020-09-12 23:35:00', NULL, '0');
INSERT INTO `sys_menu` (`id`, `menu_name`, `menu_level`, `menu_url`, `parent_menu_id`, `priority`, `permission`, `target`, `visible`, `is_refresh`, `icon`, `create_date`, `update_date`, `del_flag`) VALUES ('45', '日志管理', '1', '', '0', '500', '', 'menuItem', '0', '1', 'fa fa-pencil-square', '2021-02-24 10:30:33', '2021-02-24 10:32:27', '0');
INSERT INTO `sys_menu` (`id`, `menu_name`, `menu_level`, `menu_url`, `parent_menu_id`, `priority`, `permission`, `target`, `visible`, `is_refresh`, `icon`, `create_date`, `update_date`, `del_flag`) VALUES ('46', '日志列表', '2', '/sys/log/index', '45', '501', 'sys:log:index', 'menuItem', '0', '0', 'fa fa-pencil-square-o', '2021-02-24 10:32:08', '2021-04-26 09:14:45', '0');
INSERT INTO `sys_menu` (`id`, `menu_name`, `menu_level`, `menu_url`, `parent_menu_id`, `priority`, `permission`, `target`, `visible`, `is_refresh`, `icon`, `create_date`, `update_date`, `del_flag`) VALUES ('47', '日志-删除', '3', '', '46', '502', 'sys:log:deleteById', 'menuItem', '0', '1', '', '2021-02-24 15:20:12', NULL, '0');
INSERT INTO `sys_menu` (`id`, `menu_name`, `menu_level`, `menu_url`, `parent_menu_id`, `priority`, `permission`, `target`, `visible`, `is_refresh`, `icon`, `create_date`, `update_date`, `del_flag`) VALUES ('48', '日志-查看详情', '3', '', '46', '503', 'sys:log:index:detail', 'menuItem', '0', '1', '', '2021-02-24 15:20:41', '2021-03-01 14:37:59', '0');
INSERT INTO `sys_menu` (`id`, `menu_name`, `menu_level`, `menu_url`, `parent_menu_id`, `priority`, `permission`, `target`, `visible`, `is_refresh`, `icon`, `create_date`, `update_date`, `del_flag`) VALUES ('49', '日志-数据分页', '3', '', '46', '504', 'sys:log:page', 'menuItem', '0', '1', '', '2021-03-01 14:47:05', NULL, '0');
INSERT INTO `sys_menu` (`id`, `menu_name`, `menu_level`, `menu_url`, `parent_menu_id`, `priority`, `permission`, `target`, `visible`, `is_refresh`, `icon`, `create_date`, `update_date`, `del_flag`) VALUES ('50', '应用环境管理', '1', '', '0', '600', ' ', 'menuItem', '0', '1', 'fa fa-gear', '2021-04-18 15:37:42', NULL, '0');
INSERT INTO `sys_menu` (`id`, `menu_name`, `menu_level`, `menu_url`, `parent_menu_id`, `priority`, `permission`, `target`, `visible`, `is_refresh`, `icon`, `create_date`, `update_date`, `del_flag`) VALUES ('51', '应用列表', '2', '/m/app/index', '50', '601', 'm:app:index', 'menuItem', '0', '0', 'fa fa-gears', '2021-04-18 15:38:24', '2021-04-26 07:54:01', '0');
INSERT INTO `sys_menu` (`id`, `menu_name`, `menu_level`, `menu_url`, `parent_menu_id`, `priority`, `permission`, `target`, `visible`, `is_refresh`, `icon`, `create_date`, `update_date`, `del_flag`) VALUES ('52', '环境列表', '2', '/m/env/index', '50', '650', ' m:env:index', 'menuItem', '0', '0', 'fa fa-object-group', '2021-04-18 15:40:32', '2021-04-26 07:56:16', '0');
INSERT INTO `sys_menu` (`id`, `menu_name`, `menu_level`, `menu_url`, `parent_menu_id`, `priority`, `permission`, `target`, `visible`, `is_refresh`, `icon`, `create_date`, `update_date`, `del_flag`) VALUES ('53', '模板属性', '1', '', '0', '700', '', 'menuItem', '0', '1', 'fa fa-object-group', '2021-04-19 09:38:11', NULL, '0');
INSERT INTO `sys_menu` (`id`, `menu_name`, `menu_level`, `menu_url`, `parent_menu_id`, `priority`, `permission`, `target`, `visible`, `is_refresh`, `icon`, `create_date`, `update_date`, `del_flag`) VALUES ('54', '模板列表', '2', '/m/template/index', '53', '701', 'm:template:index', 'menuItem', '0', '1', 'fa fa-object-ungroup', '2021-04-19 09:39:09', '2021-04-26 08:57:19', '0');
INSERT INTO `sys_menu` (`id`, `menu_name`, `menu_level`, `menu_url`, `parent_menu_id`, `priority`, `permission`, `target`, `visible`, `is_refresh`, `icon`, `create_date`, `update_date`, `del_flag`) VALUES ('55', '属性列表', '2', '/m/property/index', '53', '719', 'm:property:index', 'menuItem', '0', '1', 'fa fa-sitemap', '2021-04-19 09:41:30', '2021-04-26 08:57:59', '0');
INSERT INTO `sys_menu` (`id`, `menu_name`, `menu_level`, `menu_url`, `parent_menu_id`, `priority`, `permission`, `target`, `visible`, `is_refresh`, `icon`, `create_date`, `update_date`, `del_flag`) VALUES ('56', '环境变量', '2', 'm/env/variable/index', '53', '750', 'm:env:variable:index', 'menuItem', '0', '1', 'fa fa-trademark', '2021-04-20 02:24:49', '2021-04-26 08:58:35', '0');
INSERT INTO `sys_menu` (`id`, `menu_name`, `menu_level`, `menu_url`, `parent_menu_id`, `priority`, `permission`, `target`, `visible`, `is_refresh`, `icon`, `create_date`, `update_date`, `del_flag`) VALUES ('57', '动态数据配置', '1', '', '0', '800', ' ', 'menuItem', '0', '1', 'fa fa-cubes', '2021-04-22 12:23:07', NULL, '0');
INSERT INTO `sys_menu` (`id`, `menu_name`, `menu_level`, `menu_url`, `parent_menu_id`, `priority`, `permission`, `target`, `visible`, `is_refresh`, `icon`, `create_date`, `update_date`, `del_flag`) VALUES ('58', '动态配置列表', '2', '/m/dynamic/data/index', '57', '801', 'm:dynamic:data:index', 'menuItem', '0', '1', 'fa fa-cube', '2021-04-22 12:23:58', NULL, '0');
INSERT INTO `sys_menu` (`id`, `menu_name`, `menu_level`, `menu_url`, `parent_menu_id`, `priority`, `permission`, `target`, `visible`, `is_refresh`, `icon`, `create_date`, `update_date`, `del_flag`) VALUES ('59', '动态配置记录列表', '2', '/m/dynamic/data/log/index', '57', '802', 'm:dynamic:data:log:index', 'menuItem', '0', '1', 'fa fa-random', '2021-04-25 06:45:30', '2021-04-26 01:59:46', '0');
INSERT INTO `sys_menu` (`id`, `menu_name`, `menu_level`, `menu_url`, `parent_menu_id`, `priority`, `permission`, `target`, `visible`, `is_refresh`, `icon`, `create_date`, `update_date`, `del_flag`) VALUES ('60', '添加应用', '3', '', '51', '602', 'm:app:add', 'menuItem', '0', '1', '', '2021-04-26 07:55:33', '2021-04-26 07:56:49', '0');
INSERT INTO `sys_menu` (`id`, `menu_name`, `menu_level`, `menu_url`, `parent_menu_id`, `priority`, `permission`, `target`, `visible`, `is_refresh`, `icon`, `create_date`, `update_date`, `del_flag`) VALUES ('61', '编辑应用', '3', '', '51', '603', 'm:app:edit', 'menuItem', '0', '1', '', '2021-04-26 07:57:24', '2021-04-26 07:57:47', '0');
INSERT INTO `sys_menu` (`id`, `menu_name`, `menu_level`, `menu_url`, `parent_menu_id`, `priority`, `permission`, `target`, `visible`, `is_refresh`, `icon`, `create_date`, `update_date`, `del_flag`) VALUES ('62', '保存应用', '3', '', '51', '604', 'm:app:insert', 'menuItem', '0', '1', '', '2021-04-26 07:59:06', '2021-04-26 07:59:18', '0');
INSERT INTO `sys_menu` (`id`, `menu_name`, `menu_level`, `menu_url`, `parent_menu_id`, `priority`, `permission`, `target`, `visible`, `is_refresh`, `icon`, `create_date`, `update_date`, `del_flag`) VALUES ('63', '更新应用', '3', '', '51', '605', 'm:app:updateById', 'menuItem', '0', '1', '', '2021-04-26 07:59:50', '2021-04-26 08:00:05', '0');
INSERT INTO `sys_menu` (`id`, `menu_name`, `menu_level`, `menu_url`, `parent_menu_id`, `priority`, `permission`, `target`, `visible`, `is_refresh`, `icon`, `create_date`, `update_date`, `del_flag`) VALUES ('64', '删除应用', '3', '', '51', '606', 'm:app:deleteById', 'menuItem', '0', '1', '', '2021-04-26 08:00:38', '2021-04-26 08:00:58', '0');
INSERT INTO `sys_menu` (`id`, `menu_name`, `menu_level`, `menu_url`, `parent_menu_id`, `priority`, `permission`, `target`, `visible`, `is_refresh`, `icon`, `create_date`, `update_date`, `del_flag`) VALUES ('65', '添加环境', '3', '', '52', '652', 'm:env:add', 'menuItem', '0', '1', '', '2021-04-26 07:55:33', '2021-04-26 07:56:49', '0');
INSERT INTO `sys_menu` (`id`, `menu_name`, `menu_level`, `menu_url`, `parent_menu_id`, `priority`, `permission`, `target`, `visible`, `is_refresh`, `icon`, `create_date`, `update_date`, `del_flag`) VALUES ('66', '编辑环境', '3', '', '52', '653', 'm:env:edit', 'menuItem', '0', '1', '', '2021-04-26 07:57:24', '2021-04-26 07:57:47', '0');
INSERT INTO `sys_menu` (`id`, `menu_name`, `menu_level`, `menu_url`, `parent_menu_id`, `priority`, `permission`, `target`, `visible`, `is_refresh`, `icon`, `create_date`, `update_date`, `del_flag`) VALUES ('67', '保存环境', '3', '', '52', '654', 'm:env:insert', 'menuItem', '0', '1', '', '2021-04-26 07:59:06', '2021-04-26 07:59:18', '0');
INSERT INTO `sys_menu` (`id`, `menu_name`, `menu_level`, `menu_url`, `parent_menu_id`, `priority`, `permission`, `target`, `visible`, `is_refresh`, `icon`, `create_date`, `update_date`, `del_flag`) VALUES ('68', '更新环境', '3', '', '52', '655', 'm:env:updateById', 'menuItem', '0', '1', '', '2021-04-26 07:59:50', '2021-04-26 08:00:05', '0');
INSERT INTO `sys_menu` (`id`, `menu_name`, `menu_level`, `menu_url`, `parent_menu_id`, `priority`, `permission`, `target`, `visible`, `is_refresh`, `icon`, `create_date`, `update_date`, `del_flag`) VALUES ('69', '删除环境', '3', '', '52', '656', 'm:env:deleteById', 'menuItem', '0', '1', '', '2021-04-26 08:00:38', '2021-04-26 08:00:58', '0');
INSERT INTO `sys_menu` (`id`, `menu_name`, `menu_level`, `menu_url`, `parent_menu_id`, `priority`, `permission`, `target`, `visible`, `is_refresh`, `icon`, `create_date`, `update_date`, `del_flag`) VALUES ('70', '添加模板', '3', '', '54', '702', 'm:template:add', 'menuItem', '0', '1', '', '2021-04-26 07:55:33', '2021-04-26 07:56:49', '0');
INSERT INTO `sys_menu` (`id`, `menu_name`, `menu_level`, `menu_url`, `parent_menu_id`, `priority`, `permission`, `target`, `visible`, `is_refresh`, `icon`, `create_date`, `update_date`, `del_flag`) VALUES ('71', '编辑模板', '3', '', '54', '703', 'm:template:edit', 'menuItem', '0', '1', '', '2021-04-26 07:57:24', '2021-04-26 07:57:47', '0');
INSERT INTO `sys_menu` (`id`, `menu_name`, `menu_level`, `menu_url`, `parent_menu_id`, `priority`, `permission`, `target`, `visible`, `is_refresh`, `icon`, `create_date`, `update_date`, `del_flag`) VALUES ('72', '新增模板', '3', '', '54', '704', 'm:template:insert', 'menuItem', '0', '1', '', '2021-04-26 07:59:06', '2021-04-26 08:49:36', '0');
INSERT INTO `sys_menu` (`id`, `menu_name`, `menu_level`, `menu_url`, `parent_menu_id`, `priority`, `permission`, `target`, `visible`, `is_refresh`, `icon`, `create_date`, `update_date`, `del_flag`) VALUES ('73', '更新模板', '3', '', '54', '705', 'm:template:updateById', 'menuItem', '0', '1', '', '2021-04-26 07:59:50', '2021-04-26 08:00:05', '0');
INSERT INTO `sys_menu` (`id`, `menu_name`, `menu_level`, `menu_url`, `parent_menu_id`, `priority`, `permission`, `target`, `visible`, `is_refresh`, `icon`, `create_date`, `update_date`, `del_flag`) VALUES ('74', '删除模板', '3', '', '54', '706', 'm:template:deleteById', 'menuItem', '0', '1', '', '2021-04-26 08:00:38', '2021-04-26 08:00:58', '0');
INSERT INTO `sys_menu` (`id`, `menu_name`, `menu_level`, `menu_url`, `parent_menu_id`, `priority`, `permission`, `target`, `visible`, `is_refresh`, `icon`, `create_date`, `update_date`, `del_flag`) VALUES ('75', '添加属性', '3', '', '55', '720', 'm:property:add', 'menuItem', '0', '1', '', '2021-04-26 07:55:33', '2021-04-26 07:56:49', '0');
INSERT INTO `sys_menu` (`id`, `menu_name`, `menu_level`, `menu_url`, `parent_menu_id`, `priority`, `permission`, `target`, `visible`, `is_refresh`, `icon`, `create_date`, `update_date`, `del_flag`) VALUES ('76', '编辑属性', '3', '', '55', '721', 'm:property:edit', 'menuItem', '0', '1', '', '2021-04-26 07:57:24', '2021-04-26 07:57:47', '0');
INSERT INTO `sys_menu` (`id`, `menu_name`, `menu_level`, `menu_url`, `parent_menu_id`, `priority`, `permission`, `target`, `visible`, `is_refresh`, `icon`, `create_date`, `update_date`, `del_flag`) VALUES ('77', '新增属性', '3', '', '55', '722', 'm:property:insert', 'menuItem', '0', '1', '', '2021-04-26 07:59:06', '2021-04-26 07:59:18', '0');
INSERT INTO `sys_menu` (`id`, `menu_name`, `menu_level`, `menu_url`, `parent_menu_id`, `priority`, `permission`, `target`, `visible`, `is_refresh`, `icon`, `create_date`, `update_date`, `del_flag`) VALUES ('78', '更新属性', '3', '', '55', '723', 'm:property:updateById', 'menuItem', '0', '1', '', '2021-04-26 07:59:50', '2021-04-26 08:00:05', '0');
INSERT INTO `sys_menu` (`id`, `menu_name`, `menu_level`, `menu_url`, `parent_menu_id`, `priority`, `permission`, `target`, `visible`, `is_refresh`, `icon`, `create_date`, `update_date`, `del_flag`) VALUES ('79', '删除属性', '3', '', '55', '724', 'm:property:deleteById', 'menuItem', '0', '1', '', '2021-04-26 08:00:38', '2021-04-26 08:00:58', '0');
INSERT INTO `sys_menu` (`id`, `menu_name`, `menu_level`, `menu_url`, `parent_menu_id`, `priority`, `permission`, `target`, `visible`, `is_refresh`, `icon`, `create_date`, `update_date`, `del_flag`) VALUES ('80', '复制属性', '3', '', '55', '725', 'm:property:copyProperty', 'menuItem', '0', '1', '', '2021-04-26 08:00:38', '2021-04-26 08:00:58', '0');
INSERT INTO `sys_menu` (`id`, `menu_name`, `menu_level`, `menu_url`, `parent_menu_id`, `priority`, `permission`, `target`, `visible`, `is_refresh`, `icon`, `create_date`, `update_date`, `del_flag`) VALUES ('81', '添加变量', '3', '', '56', '751', 'm:env:variable:add', 'menuItem', '0', '1', '', '2021-04-26 07:55:33', '2021-04-26 07:56:49', '0');
INSERT INTO `sys_menu` (`id`, `menu_name`, `menu_level`, `menu_url`, `parent_menu_id`, `priority`, `permission`, `target`, `visible`, `is_refresh`, `icon`, `create_date`, `update_date`, `del_flag`) VALUES ('82', '编辑变量', '3', '', '56', '752', 'm:env:variable:edit', 'menuItem', '0', '1', '', '2021-04-26 07:57:24', '2021-04-26 07:57:47', '0');
INSERT INTO `sys_menu` (`id`, `menu_name`, `menu_level`, `menu_url`, `parent_menu_id`, `priority`, `permission`, `target`, `visible`, `is_refresh`, `icon`, `create_date`, `update_date`, `del_flag`) VALUES ('83', '新增变量', '3', '', '56', '753', 'm:env:variable:insert', 'menuItem', '0', '1', '', '2021-04-26 07:59:06', '2021-04-26 07:59:18', '0');
INSERT INTO `sys_menu` (`id`, `menu_name`, `menu_level`, `menu_url`, `parent_menu_id`, `priority`, `permission`, `target`, `visible`, `is_refresh`, `icon`, `create_date`, `update_date`, `del_flag`) VALUES ('84', '更新变量', '3', '', '56', '754', 'm:env:variable:updateById', 'menuItem', '0', '1', '', '2021-04-26 07:59:50', '2021-04-26 08:00:05', '0');
INSERT INTO `sys_menu` (`id`, `menu_name`, `menu_level`, `menu_url`, `parent_menu_id`, `priority`, `permission`, `target`, `visible`, `is_refresh`, `icon`, `create_date`, `update_date`, `del_flag`) VALUES ('85', '删除变量', '3', '', '56', '755', 'm:env:variable:deleteById', 'menuItem', '0', '1', '', '2021-04-26 08:00:38', '2021-04-26 08:00:58', '0');
INSERT INTO `sys_menu` (`id`, `menu_name`, `menu_level`, `menu_url`, `parent_menu_id`, `priority`, `permission`, `target`, `visible`, `is_refresh`, `icon`, `create_date`, `update_date`, `del_flag`) VALUES ('86', '添加数据', '3', '', '58', '802', 'm:dynamic:data:add', 'menuItem', '0', '1', '', '2021-04-26 07:55:33', '2021-04-26 07:56:49', '0');
INSERT INTO `sys_menu` (`id`, `menu_name`, `menu_level`, `menu_url`, `parent_menu_id`, `priority`, `permission`, `target`, `visible`, `is_refresh`, `icon`, `create_date`, `update_date`, `del_flag`) VALUES ('87', '编辑数据', '3', '', '58', '803', 'm:dynamic:data:edit', 'menuItem', '0', '1', '', '2021-04-26 07:57:24', '2021-04-26 07:57:47', '0');
INSERT INTO `sys_menu` (`id`, `menu_name`, `menu_level`, `menu_url`, `parent_menu_id`, `priority`, `permission`, `target`, `visible`, `is_refresh`, `icon`, `create_date`, `update_date`, `del_flag`) VALUES ('88', '新增数据', '3', '', '58', '804', 'm:dynamic:data:insert', 'menuItem', '0', '1', '', '2021-04-26 07:59:06', '2021-04-26 07:59:18', '0');
INSERT INTO `sys_menu` (`id`, `menu_name`, `menu_level`, `menu_url`, `parent_menu_id`, `priority`, `permission`, `target`, `visible`, `is_refresh`, `icon`, `create_date`, `update_date`, `del_flag`) VALUES ('89', '更新数据', '3', '', '58', '805', 'm:dynamic:data:updateById', 'menuItem', '0', '1', '', '2021-04-26 07:59:50', '2021-04-26 08:00:05', '0');
INSERT INTO `sys_menu` (`id`, `menu_name`, `menu_level`, `menu_url`, `parent_menu_id`, `priority`, `permission`, `target`, `visible`, `is_refresh`, `icon`, `create_date`, `update_date`, `del_flag`) VALUES ('90', '删除数据', '3', '', '58', '806', 'm:dynamic:data:deleteById', 'menuItem', '0', '1', '', '2021-04-26 08:00:38', '2021-04-26 08:00:58', '0');
INSERT INTO `sys_menu` (`id`, `menu_name`, `menu_level`, `menu_url`, `parent_menu_id`, `priority`, `permission`, `target`, `visible`, `is_refresh`, `icon`, `create_date`, `update_date`, `del_flag`) VALUES ('91', '发布配置', '3', '', '58', '807', 'm:dynamic:data:release:release', 'menuItem', '0', '1', '', '2021-04-26 08:00:38', '2021-04-26 08:00:58', '0');
INSERT INTO `sys_menu` (`id`, `menu_name`, `menu_level`, `menu_url`, `parent_menu_id`, `priority`, `permission`, `target`, `visible`, `is_refresh`, `icon`, `create_date`, `update_date`, `del_flag`) VALUES ('92', '查看详情', '3', '', '59', '811', 'm:dynamic:data:log:edit', 'menuItem', '0', '1', '', '2021-04-26 07:55:33', '2021-04-26 07:56:49', '0');
INSERT INTO `sys_menu` (`id`, `menu_name`, `menu_level`, `menu_url`, `parent_menu_id`, `priority`, `permission`, `target`, `visible`, `is_refresh`, `icon`, `create_date`, `update_date`, `del_flag`) VALUES ('93', '删除记录', '3', '', '59', '812', 'm:dynamic:data:log:deleteById', 'menuItem', '0', '1', '', '2021-04-26 07:57:24', '2021-04-26 07:57:47', '0');
INSERT INTO `sys_menu` (`id`, `menu_name`, `menu_level`, `menu_url`, `parent_menu_id`, `priority`, `permission`, `target`, `visible`, `is_refresh`, `icon`, `create_date`, `update_date`, `del_flag`) VALUES ('94', '数据回滚', '3', '', '59', '813', 'm:dynamic:data:log:rollback', 'menuItem', '0', '1', '', '2021-04-26 07:57:24', '2021-04-26 07:57:47', '0');
INSERT INTO `sys_menu` (`id`, `menu_name`, `menu_level`, `menu_url`, `parent_menu_id`, `priority`, `permission`, `target`, `visible`, `is_refresh`, `icon`, `create_date`, `update_date`, `del_flag`) VALUES ('95', '系统配置管理', '1', '', '0', '900', '', 'menuItem', '0', '1', 'fa fa-database', '2021-07-15 13:55:18', NULL, '0');
INSERT INTO `sys_menu` (`id`, `menu_name`, `menu_level`, `menu_url`, `parent_menu_id`, `priority`, `permission`, `target`, `visible`, `is_refresh`, `icon`, `create_date`, `update_date`, `del_flag`) VALUES ('96', '系统配置列表', '2', '/m/system/config/index', '95', '901', 'm:system:config:index', 'menuItem', '0', '1', 'fa fa-database', '2021-07-15 13:56:49', NULL, '0');



DROP TABLE IF EXISTS `m_app`;
CREATE TABLE `m_app` (
  `id` bigint(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增id',
  `app_code` varchar(32) NOT NULL COMMENT '应用',
  `app_desc` varchar(32) NOT NULL COMMENT '应用说明',
  `create_id` bigint(11) NOT NULL COMMENT '创建者id',
  `create_date` datetime NOT NULL COMMENT '创建时间',
  `del_flag` tinyint(1) DEFAULT '0' COMMENT '删除标志位 1删除 0未删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='应用表';


DROP TABLE IF EXISTS `m_env`;
CREATE TABLE `m_env` (
  `id` bigint(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增id',
  `env_code` varchar(32) NOT NULL COMMENT '环境',
  `env_desc` varchar(32) NOT NULL COMMENT '环境描述',
  `create_id` bigint(11) NOT NULL COMMENT '创建者id',
  `create_date` datetime NOT NULL COMMENT '创建时间',
  `del_flag` tinyint(1) DEFAULT '0' COMMENT '删除标志位 1删除 0未删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COMMENT='环境表';


DROP TABLE IF EXISTS `m_env_variable`;
CREATE TABLE `m_env_variable` (
  `id` bigint(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增id',
  `variable_name` varchar(32) NOT NULL COMMENT '变量名称',
  `variable_desc` varchar(32) DEFAULT NULL COMMENT '变量说明',
  `variable_key` varchar(32) NOT NULL COMMENT '变量key',
  `create_id` bigint(11) NOT NULL COMMENT '创建者id',
  `create_date` datetime NOT NULL COMMENT '创建时间',
  `del_flag` tinyint(1) DEFAULT '0' COMMENT '删除标志位 1删除 0未删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COMMENT='常量表';

CREATE TABLE `m_env_variable_relation` (
  `id` bigint(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增id',
  `env_code` varchar(32) NOT NULL COMMENT '环境code',
  `variable_value` varchar(255) NOT NULL COMMENT '常量值',
  `variable_key` varchar(32) NOT NULL COMMENT '常量key',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COMMENT='常量和环境关系表';


DROP TABLE IF EXISTS `m_template`;
CREATE TABLE `m_template` (
  `id` bigint(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增id',
  `app_code` varchar(32) NOT NULL COMMENT '应用',
  `template_key` varchar(32) NOT NULL COMMENT '模板key',
  `template_name` varchar(32) NOT NULL COMMENT '模板名称',
  `template_desc` varchar(32) DEFAULT NULL COMMENT '模板描述',
  `create_id` bigint(11) NOT NULL COMMENT '创建者id',
  `create_date` datetime NOT NULL COMMENT '创建时间',
  `del_flag` tinyint(1) DEFAULT '0' COMMENT '删除标志位 1删除 0未删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='模板表';


DROP TABLE IF EXISTS `m_property`;
CREATE TABLE `m_property` (
  `id` bigint(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增id',
  `property_name` varchar(32) NOT NULL COMMENT '属性名称',
  `property_key` varchar(32) NOT NULL COMMENT '属性key',
  `property_type` varchar(32) NOT NULL COMMENT '属性类型',
  `column_length` tinyint(4) NOT NULL COMMENT '属性长度',
  `label_type` varchar(64) NOT NULL COMMENT 'html标签类型',
  `label_value` varchar(255) default '' COMMENT 'html 标签默认值',
  `label_required` tinyint(1)  DEFAULT '0' COMMENT '是否必填 0非必填 1必填 ',
  `default_value` varchar(64) default '' COMMENT '默认值',
  `app_code` varchar(32) NOT NULL COMMENT '应用名称',
  `variable_key` varchar(32) DEFAULT NULL COMMENT '常量key',
  `template_key` varchar(32) DEFAULT NULL COMMENT '模板key ，公共属性为空，指定模板属性不为空',
  `attribute_type` tinyint(2)  DEFAULT '0' COMMENT '属性类别 0 公共属性 1 模板属性',
  `priority` tinyint(5) NOT NULL DEFAULT '0' COMMENT '显示优先级',
  `show_table` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否在table页展示 1展示 0 不展示',
  `create_id` bigint(11) NOT NULL COMMENT '创建者id',
  `create_date` datetime NOT NULL COMMENT '创建时间',
  `del_flag` tinyint(1) DEFAULT '0' COMMENT '删除标志位 1删除 0未删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8mb4 COMMENT='属性表';




DROP TABLE IF EXISTS `m_dynamic_data`;
CREATE TABLE `m_dynamic_data` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增id',
  `env_code` varchar(32) NOT NULL COMMENT '环境code',
  `app_code` varchar(32) NOT NULL COMMENT '应用名',
  `template_key` varchar(32) NOT NULL COMMENT '模板key',
  `status` tinyint(1)  DEFAULT '1' COMMENT '状态 1开启 0关闭',
  `data_desc` varchar(32) DEFAULT '' COMMENT '描述',
  `priority` tinyint(4) unsigned NOT NULL DEFAULT '0' COMMENT '优先级',
  `release_type` tinyint(1) DEFAULT '0' COMMENT '发布状态 1已发布 0修改 2已删除 3新增',
  `create_id` bigint(11) NOT NULL COMMENT '创建者id',
  `create_date` datetime NOT NULL COMMENT '创建时间',
  `update_id` bigint(11) DEFAULT NULL COMMENT '最近更新者id',
  `update_date` datetime DEFAULT NULL COMMENT '最近更新时间',
  `del_flag` tinyint(1) DEFAULT '0' COMMENT '删除标志位 1删除 0未删除',
  PRIMARY KEY (`id`),
  KEY `index_eat` (`env_code`,`app_code`,`template_key`) USING BTREE
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COMMENT='动态配置表';


DROP TABLE IF EXISTS `m_dynamic_data_value`;
CREATE TABLE `m_dynamic_data_value` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增id',
  `data_id` bigint(20) unsigned NOT NULL COMMENT '动态配置表id',
  `json` text DEFAULT NULL COMMENT '实例json',
  `temp_json` text DEFAULT NULL COMMENT 'temp json',
  `del_flag` tinyint(1) DEFAULT '0' COMMENT '删除标志位 1删除 0未删除',
  PRIMARY KEY (`id`),
  KEY `index_eat` (`data_id`) USING BTREE
) ENGINE=InnoDB  DEFAULT CHARSET=utf8mb4 COMMENT='动态配置数据表';


DROP TABLE IF EXISTS `m_dynamic_data_log`;
CREATE TABLE `m_dynamic_data_log` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增id',
  `env_code` varchar(32) NOT NULL COMMENT '环境code',
  `app_code` varchar(32) NOT NULL COMMENT '应用名',
  `template_key` varchar(32) NOT NULL COMMENT '模板key',
  `description` varchar(32) DEFAULT NULL COMMENT '描述',
  `operation_type` tinyint(1) DEFAULT '0' COMMENT '操作类型 1编辑 2删除 ',
  `data_value_id` bigint(20) unsigned NOT NULL  COMMENT '动态配置数据 id',
  `json` text DEFAULT NULL COMMENT '实例json',
  `temp_json` text DEFAULT NULL COMMENT 'temp json',
  `create_id` bigint(11) NOT NULL COMMENT '创建者id',
  `create_date` datetime NOT NULL COMMENT '创建时间',
  `del_flag` tinyint(1) DEFAULT '0' COMMENT '删除标志位 1删除 0未删除',
  PRIMARY KEY (`id`),
  KEY `index_eat` (`env_code`,`app_code`,`template_key`) USING BTREE
) ENGINE=InnoDB  DEFAULT CHARSET=utf8mb4 COMMENT='配置数据记录表';



DROP TABLE IF EXISTS `m_dynamic_data_release`;
CREATE TABLE `m_dynamic_data_release` (
  `id` bigint(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增id',
  `env_code` varchar(32) NOT NULL COMMENT '环境code',
  `app_code` varchar(32) NOT NULL COMMENT '应用名',
  `template_keys` varchar(255) DEFAULT NULL COMMENT '模板keys',
  `update_date` datetime DEFAULT NULL COMMENT '最近更新时间',
  `del_flag` tinyint(1) DEFAULT '0' COMMENT '删除标志位 1删除 0未删除',
  PRIMARY KEY (`id`),
  KEY `index_envCode_appName` (`env_code`,`app_code`) USING BTREE
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COMMENT='配置数据发布表';






DROP TABLE IF EXISTS `m_system_config`;
CREATE TABLE `m_system_config` (
  `id` bigint(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增id',
  `app_code` varchar(32) NOT NULL COMMENT '应用名称',
  `env_code` varchar(32) NOT NULL COMMENT '环境code',
  `modifier` varchar(32) DEFAULT NULL COMMENT '修改人',
  `file_name` varchar(32) NOT NULL COMMENT '文件名称',
  `file_desc` varchar(255) DEFAULT NULL COMMENT '文件描述',
  `file_type` varchar(16) NOT NULL DEFAULT 'PROPERTIES' COMMENT '文件类型 TEXT YAML  PROPERTIES',
  `json` text DEFAULT NULL COMMENT '配置文件内容',
  `temp_json` text DEFAULT NULL COMMENT '临时数据',
  `status` tinyint(1) NOT NULL DEFAULT '0' COMMENT '状态 1新增 2更新 3删除 4已发布',
  `create_date` datetime NOT NULL COMMENT '创建时间',
  `update_date` datetime DEFAULT NULL COMMENT '最近更新时间',
  `del_flag` tinyint(1) DEFAULT '0' COMMENT '删除标志位 1删除 0未删除',
  PRIMARY KEY (`id`),
  KEY `idx_envcode_appcode` (`env_code`,`app_code`) USING BTREE
) ENGINE=InnoDB  DEFAULT CHARSET=utf8mb4 COMMENT='应用系统配置表';
ALTER TABLE m_system_config ADD INDEX index_del_flag (del_flag);


DROP TABLE IF EXISTS `m_system_config_history`;
CREATE TABLE `m_system_config_history` (
  `id` bigint(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增id',
  `app_code` varchar(32) NOT NULL COMMENT '应用名称',
  `env_code` varchar(32) NOT NULL COMMENT '环境code',
  `modifier` varchar(32) DEFAULT NULL COMMENT '修改人',
  `file_name` varchar(32) NOT NULL COMMENT '文件名称',
  `file_desc` varchar(255) DEFAULT NULL COMMENT '文件描述',
  `file_type` varchar(16) NOT NULL DEFAULT 'PROPERTIES' COMMENT '文件类型 TEXT YAML  PROPERTIES',
  `json` text DEFAULT NULL COMMENT '配置文件内容',
  `temp_json` text DEFAULT NULL COMMENT '临时数据',
  `status` tinyint(1) NOT NULL DEFAULT '0' COMMENT '状态 1新增 2更新 3删除 4已发布',
  `create_date` datetime NOT NULL COMMENT '创建时间',
  `update_date` datetime DEFAULT NULL COMMENT '最近更新时间',
  `del_flag` tinyint(1) DEFAULT '0' COMMENT '删除标志位 1删除 0未删除',
  PRIMARY KEY (`id`),
  KEY `idx_envcode_appcode` (`env_code`,`app_code`) USING BTREE
) ENGINE=InnoDB  DEFAULT CHARSET=utf8mb4 COMMENT='应用系统配置历史表';
ALTER TABLE m_system_config_history ADD INDEX index_del_flag (del_flag);



DROP TABLE IF EXISTS `m_system_config_role_relation`;
CREATE TABLE `m_system_config_role_relation` (
  `id` bigint(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增id',
  `role_id` bigint(11) NOT NULL COMMENT '角色ID',
  `system_config_id` bigint(20) NOT NULL COMMENT '动态配置ID',
  `permission` tinyint(4) NOT NULL COMMENT '权限 1111 代表 增删改查 都有权限',
  `create_date` datetime NOT NULL COMMENT '创建时间',
  `update_id` bigint(11) DEFAULT NULL COMMENT '最近更新者id',
  `update_date` datetime DEFAULT NULL COMMENT '最近更新时间',
  `del_flag` tinyint(1) DEFAULT '0' COMMENT '删除标志位 1删除 0未删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COMMENT='应用系统配置-角色关系表';
ALTER TABLE m_system_config_role_relation ADD INDEX index_del_flag (del_flag);


CREATE TABLE `m_system_release` (
  `id` bigint(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增id',
  `env_code` varchar(32) NOT NULL COMMENT '环境code',
  `app_code` varchar(32) NOT NULL COMMENT '应用code',
  `files` varchar(255) DEFAULT NULL COMMENT '模板keys',
  `update_date` datetime DEFAULT NULL COMMENT '最近更新时间',
  `release_flag` tinyint(1) DEFAULT '0' COMMENT '删除标志位 1删除 0未删除',
  PRIMARY KEY (`id`),
  KEY `index_envCode_appName` (`env_code`,`app_code`) USING BTREE
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COMMENT='系统配置发布表';



-- 测试数据
INSERT INTO `m_app` ( `app_code`, `app_desc`, `create_id`, `create_date`, `del_flag`) VALUES ( 'app', 'app', '1', '2021-04-25 02:03:50', '0');
INSERT INTO `m_env` ( `env_code`, `env_desc`, `create_id`, `create_date`, `del_flag`) VALUES ( 'beta', 'beta', '1', '2021-04-25 02:04:22', '0');
INSERT INTO `m_env` ( `env_code`, `env_desc`, `create_id`, `create_date`, `del_flag`) VALUES ( 'betaB', 'betaB', '1', '2021-04-25 02:04:32', '0');
INSERT INTO `m_template` ( `app_code`, `template_key`, `template_name`, `template_desc`, `create_id`, `create_date`, `del_flag`) VALUES ('app', 'banner', 'banner', '', '1', '2021-04-25 02:13:51', '0');
INSERT INTO `m_property` ( `property_name`, `property_key`, `property_type`, `column_length`, `label_type`, `label_value`, `label_required`, `default_value`, `app_code`, `variable_key`, `template_key`, `attribute_type`, `priority`, `show_table`, `create_id`, `create_date`, `del_flag`) VALUES ( '标题', 'title', 'varchar', '10', 'input', '', '0', '测试标题', 'app', '', 'banner', '1', '1', '1', '1', '2021-04-25 02:16:10', '0');
INSERT INTO `m_property` ( `property_name`, `property_key`, `property_type`, `column_length`, `label_type`, `label_value`, `label_required`, `default_value`, `app_code`, `variable_key`, `template_key`, `attribute_type`, `priority`, `show_table`, `create_id`, `create_date`, `del_flag`) VALUES ( '跳转地址', 'jumpUrl', 'varchar', '32', 'input', '', '1', '', 'app', 'appDomain', 'banner', '1', '1', '1', '1', '2021-04-25 02:16:50', '0');
INSERT INTO `m_property` ( `property_name`, `property_key`, `property_type`, `column_length`, `label_type`, `label_value`, `label_required`, `default_value`, `app_code`, `variable_key`, `template_key`, `attribute_type`, `priority`, `show_table`, `create_id`, `create_date`, `del_flag`) VALUES ( '标题', 'title', 'varchar', '22', 'input', '', '0', '', 'hzzy', '', 'banner', '1', '1', '1', '1', '2021-04-25 03:45:44', '0');
INSERT INTO `m_env_variable` ( `variable_name`, `variable_desc`, `variable_key`, `create_id`, `create_date`, `del_flag`) VALUES ( 'app域名', '', 'appDomain', '1', '2021-04-25 02:17:23', '0');
INSERT INTO `m_env_variable_relation` ( `env_code`, `variable_value`, `variable_key`) VALUES ( 'beta', 'http://1', 'appDomain');
INSERT INTO `m_env_variable_relation` ( `env_code`, `variable_value`, `variable_key`) VALUES ( 'betaB', 'http://2', 'appDomain');
