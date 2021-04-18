
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



