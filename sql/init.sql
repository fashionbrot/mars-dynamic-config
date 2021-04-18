
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
  `del_flag` tinyint(1) DEFAULT '0' COMMENT '删除标志位 1删除 0未删除',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='系统日志';
ALTER TABLE sys_log ADD INDEX index_del_flagAndinterface_type (del_flag,interface_type);
ALTER TABLE sys_log ADD INDEX index_create_id (create_id);



INSERT INTO `sys_menu` (`id`, `menu_name`, `menu_level`, `menu_url`, `parent_menu_id`, `priority`, `permission`, `target`, `visible`, `is_refresh`, `icon`,  `create_date`,  `update_date`, `del_flag`) VALUES ('1', '用户管理', '1', '', '0', '100', '', 'menuItem', '0', '1', 'fa fa-user',  '2019-12-08 13:29:27',  '2021-02-24 13:43:17', '0');
INSERT INTO `sys_menu` (`id`, `menu_name`, `menu_level`, `menu_url`, `parent_menu_id`, `priority`, `permission`, `target`, `visible`, `is_refresh`, `icon`,  `create_date`,  `update_date`, `del_flag`) VALUES ('2', '用户列表', '2', '/system/user/index', '1', '101', 'system:user:index', 'menuItem', '0', '1', 'fa fa-user', '2019-12-08 13:29:49',  '2021-03-01 14:12:19', '0');
INSERT INTO `sys_menu` (`id`, `menu_name`, `menu_level`, `menu_url`, `parent_menu_id`, `priority`, `permission`, `target`, `visible`, `is_refresh`, `icon`,  `create_date`,  `update_date`, `del_flag`) VALUES ('3', '菜单管理', '1', '', '0', '200', '', 'menuItem', '1', '1', 'fa fa-reorder',  '2019-12-08 14:20:46', '2021-02-24 13:44:37', '0');
INSERT INTO `sys_menu` (`id`, `menu_name`, `menu_level`, `menu_url`, `parent_menu_id`, `priority`, `permission`, `target`, `visible`, `is_refresh`, `icon`,  `create_date`,  `update_date`, `del_flag`) VALUES ('4', '菜单列表', '2', '/system/menu/index', '3', '201', 'system:menu:index', 'menuItem', '1', '1', '#',  '2019-12-08 14:21:08',  '2021-03-01 14:23:46', '0');
INSERT INTO `sys_menu` (`id`, `menu_name`, `menu_level`, `menu_url`, `parent_menu_id`, `priority`, `permission`, `target`, `visible`, `is_refresh`, `icon`,  `create_date`,  `update_date`, `del_flag`) VALUES ('8', '权限管理', '1', '', '0', '400', '', 'menuItem', '0', '1', 'fa fa-user-secret',  '2019-12-08 15:56:09',  '2021-02-24 13:46:14', '0');
INSERT INTO `sys_menu` (`id`, `menu_name`, `menu_level`, `menu_url`, `parent_menu_id`, `priority`, `permission`, `target`, `visible`, `is_refresh`, `icon`,  `create_date`,  `update_date`, `del_flag`) VALUES ('9', '权限列表', '2', '/system/role/index', '8', '401', 'system:role:index', 'menuItem', '0', '1', '#',  '2019-12-08 15:56:37', '2021-03-01 14:24:06', '0');
INSERT INTO `sys_menu` (`id`, `menu_name`, `menu_level`, `menu_url`, `parent_menu_id`, `priority`, `permission`, `target`, `visible`, `is_refresh`, `icon`,  `create_date`,  `update_date`, `del_flag`) VALUES ('13', '用户列表-修改密码', '3', '', '2', '102', 'system:user:resetPwd', 'menuItem', '0', '1', '#',  '2020-09-12 21:43:55',  '2021-02-24 11:48:55', '0');
INSERT INTO `sys_menu` (`id`, `menu_name`, `menu_level`, `menu_url`, `parent_menu_id`, `priority`, `permission`, `target`, `visible`, `is_refresh`, `icon`,  `create_date`,  `update_date`, `del_flag`) VALUES ('14', '用户列表-新增用户', '3', '', '2', '103', 'system:user:add', 'menuItem', '0', '1', '#',  '2020-09-12 21:45:05',  NULL, '0');
INSERT INTO `sys_menu` (`id`, `menu_name`, `menu_level`, `menu_url`, `parent_menu_id`, `priority`, `permission`, `target`, `visible`, `is_refresh`, `icon`,  `create_date`,  `update_date`, `del_flag`) VALUES ('15', '用户列表-编辑用户', '3', '', '2', '104', 'system:user:edit', 'menuItem', '0', '1', '#',  '2020-09-12 21:45:41',  NULL, '0');
INSERT INTO `sys_menu` (`id`, `menu_name`, `menu_level`, `menu_url`, `parent_menu_id`, `priority`, `permission`, `target`, `visible`, `is_refresh`, `icon`,  `create_date`,  `update_date`, `del_flag`) VALUES ('16', '用户列表-删除用户', '3', '', '2', '105', 'system:user:del', 'menuItem', '0', '1', '#',  '2020-09-12 21:54:11',  NULL, '0');
INSERT INTO `sys_menu` (`id`, `menu_name`, `menu_level`, `menu_url`, `parent_menu_id`, `priority`, `permission`, `target`, `visible`, `is_refresh`, `icon`,  `create_date`,  `update_date`, `del_flag`) VALUES ('17', '用户列表-更新用户', '3', '', '2', '106', 'system:user:update', 'menuItem', '0', '1', '#',  '2020-09-12 21:55:55',  '2020-09-12 22:39:10', '0');
INSERT INTO `sys_menu` (`id`, `menu_name`, `menu_level`, `menu_url`, `parent_menu_id`, `priority`, `permission`, `target`, `visible`, `is_refresh`, `icon`,  `create_date`,  `update_date`, `del_flag`) VALUES ('18', '用户列表-查询全部', '3', '', '2', '107', 'system:user:page', 'menuItem', '0', '1', '#',  '2020-09-12 21:56:51', NULL, '0');
INSERT INTO `sys_menu` (`id`, `menu_name`, `menu_level`, `menu_url`, `parent_menu_id`, `priority`, `permission`, `target`, `visible`, `is_refresh`, `icon`,  `create_date`,  `update_date`, `del_flag`) VALUES ('19', '菜单列表-新增', '3', '', '4', '202', 'system:menu:add', 'menuItem', '1', '1', '#',  '2020-09-12 22:34:05', NULL, '0');
INSERT INTO `sys_menu` (`id`, `menu_name`, `menu_level`, `menu_url`, `parent_menu_id`, `priority`, `permission`, `target`, `visible`, `is_refresh`, `icon`,  `create_date`,  `update_date`, `del_flag`) VALUES ('20', '菜单列表-更新', '3', '', '4', '203', 'system:menu:update', 'menuItem', '1', '1', '#',  '2020-09-12 22:35:35',  NULL, '0');
INSERT INTO `sys_menu` (`id`, `menu_name`, `menu_level`, `menu_url`, `parent_menu_id`, `priority`, `permission`, `target`, `visible`, `is_refresh`, `icon`,  `create_date`,  `update_date`, `del_flag`) VALUES ('21', '菜单列表-删除', '3', '', '4', '204', 'system:menu:del', 'menuItem', '1', '1', '#',  '2020-09-12 22:36:17', NULL, '0');
INSERT INTO `sys_menu` (`id`, `menu_name`, `menu_level`, `menu_url`, `parent_menu_id`, `priority`, `permission`, `target`, `visible`, `is_refresh`, `icon`,  `create_date`,  `update_date`, `del_flag`) VALUES ('22', '菜单列表-查看详情', '3', '', '4', '205', 'system:menu:info', 'menuItem', '1', '1', '#',  '2020-09-12 22:37:23',  NULL, '0');
INSERT INTO `sys_menu` (`id`, `menu_name`, `menu_level`, `menu_url`, `parent_menu_id`, `priority`, `permission`, `target`, `visible`, `is_refresh`, `icon`,  `create_date`,  `update_date`, `del_flag`) VALUES ('23', '菜单列表-分页列表', '3', '', '4', '206', 'system:menu:page', 'menuItem', '1', '1', '#',  '2020-09-12 22:37:59',  NULL, '0');
INSERT INTO `sys_menu` (`id`, `menu_name`, `menu_level`, `menu_url`, `parent_menu_id`, `priority`, `permission`, `target`, `visible`, `is_refresh`, `icon`,  `create_date`,  `update_date`, `del_flag`) VALUES ('34', '权限列表-列表分页', '3', '', '9', '402', 'system:role:page', 'menuItem', '0', '1', '#',  '2020-09-12 23:32:52', NULL, '0');
INSERT INTO `sys_menu` (`id`, `menu_name`, `menu_level`, `menu_url`, `parent_menu_id`, `priority`, `permission`, `target`, `visible`, `is_refresh`, `icon`,  `create_date`,  `update_date`, `del_flag`) VALUES ('35', '权限列表-查看详情', '3', '', '9', '403', 'system:role:info', 'menuItem', '0', '1', '#',  '2020-09-12 23:33:24',  NULL, '0');
INSERT INTO `sys_menu` (`id`, `menu_name`, `menu_level`, `menu_url`, `parent_menu_id`, `priority`, `permission`, `target`, `visible`, `is_refresh`, `icon`,  `create_date`,  `update_date`, `del_flag`) VALUES ('36', '权限列表-新增', '3', '', '9', '404', 'system:role:add', 'menuItem', '0', '1', '#',  '2020-09-12 23:33:56',  NULL, '0');
INSERT INTO `sys_menu` (`id`, `menu_name`, `menu_level`, `menu_url`, `parent_menu_id`, `priority`, `permission`, `target`, `visible`, `is_refresh`, `icon`,  `create_date`,  `update_date`, `del_flag`) VALUES ('37', '权限列表-更新', '3', '', '9', '405', 'system:role:update', 'menuItem', '0', '1', '#',  '2020-09-12 23:34:24',  NULL, '0');
INSERT INTO `sys_menu` (`id`, `menu_name`, `menu_level`, `menu_url`, `parent_menu_id`, `priority`, `permission`, `target`, `visible`, `is_refresh`, `icon`,  `create_date`,  `update_date`, `del_flag`) VALUES ('38', '权限列表-删除', '3', '', '9', '406', 'system:role:del', 'menuItem', '0', '1', '#',  '2020-09-12 23:35:00',  NULL, '0');
INSERT INTO `sys_menu` (`id`, `menu_name`, `menu_level`, `menu_url`, `parent_menu_id`, `priority`, `permission`, `target`, `visible`, `is_refresh`, `icon`,  `create_date`,  `update_date`, `del_flag`) VALUES ('45', '日志管理', '1', '', '0', '500', '', 'menuItem', '0', '1', 'fa fa-pencil-square',  '2021-02-24 10:30:33',  '2021-02-24 10:32:27', '0');
INSERT INTO `sys_menu` (`id`, `menu_name`, `menu_level`, `menu_url`, `parent_menu_id`, `priority`, `permission`, `target`, `visible`, `is_refresh`, `icon`,  `create_date`,  `update_date`, `del_flag`) VALUES ('46', '日志列表', '2', '/system/log/index', '45', '501', 'system:log:index', 'menuItem', '0', '0', 'fa fa-pencil-square-o',  '2021-02-24 10:32:08',  '2021-03-01 14:38:33', '0');
INSERT INTO `sys_menu` (`id`, `menu_name`, `menu_level`, `menu_url`, `parent_menu_id`, `priority`, `permission`, `target`, `visible`, `is_refresh`, `icon`,  `create_date`,  `update_date`, `del_flag`) VALUES ('47', '日志-删除', '3', '', '46', '502', 'system:log:del', 'menuItem', '0', '1', '', '2021-02-24 15:20:12',  NULL, '0');
INSERT INTO `sys_menu` (`id`, `menu_name`, `menu_level`, `menu_url`, `parent_menu_id`, `priority`, `permission`, `target`, `visible`, `is_refresh`, `icon`,  `create_date`,  `update_date`, `del_flag`) VALUES ('48', '日志-查看详情', '3', '', '46', '503', 'system:log:index:detail', 'menuItem', '0', '1', '',  '2021-02-24 15:20:41',  '2021-03-01 14:37:59', '0');
INSERT INTO `sys_menu` (`id`, `menu_name`, `menu_level`, `menu_url`, `parent_menu_id`, `priority`, `permission`, `target`, `visible`, `is_refresh`, `icon`,  `create_date`,  `update_date`, `del_flag`) VALUES ('49', '日志-数据分页', '3', '', '46', '504', 'system:log:page', 'menuItem', '0', '1', '',  '2021-03-01 14:47:05',  NULL, '0');


