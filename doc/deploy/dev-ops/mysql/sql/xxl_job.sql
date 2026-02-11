# ************************************************************
# Sequel Ace SQL dump
# 版本号： 20050
#
# https://sequel-ace.com/
# https://github.com/Sequel-Ace/Sequel-Ace
#
# 主机: 192.168.1.108 (MySQL 8.0.32)
# 数据库: xxl_job
# 生成时间: 2024-08-10 01:19:17 +0000

CREATE DATABASE IF NOT EXISTS `xxl_job` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE `xxl_job`;

# ************************************************************


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
SET NAMES utf8mb4;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE='NO_AUTO_VALUE_ON_ZERO', SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

CREATE database if NOT EXISTS `xxl_job` default character set utf8mb4 collate utf8mb4_unicode_ci;
use `xxl_job`;

# 转储表 xxl_job_group
# ------------------------------------------------------------

DROP TABLE IF EXISTS `xxl_job_group`;

CREATE TABLE `xxl_job_group` (
  `id` int NOT NULL AUTO_INCREMENT,
  `app_name` varchar(64) NOT NULL COMMENT '执行器AppName',
  `title` varchar(12) NOT NULL COMMENT '执行器名称',
  `address_type` tinyint NOT NULL DEFAULT '0' COMMENT '执行器地址类型：0=自动注册、1=手动录入',
  `address_list` text COMMENT '执行器地址列表，多地址逗号分隔',
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

LOCK TABLES `xxl_job_group` WRITE;
/*!40000 ALTER TABLE `xxl_job_group` DISABLE KEYS */;

INSERT INTO `xxl_job_group` (`id`, `app_name`, `title`, `address_type`, `address_list`, `update_time`)
VALUES
	(1,'big-market-job','大营销调度任务',0,NULL,'2024-08-10 09:18:32');

/*!40000 ALTER TABLE `xxl_job_group` ENABLE KEYS */;
UNLOCK TABLES;


# 转储表 xxl_job_info
# ------------------------------------------------------------

DROP TABLE IF EXISTS `xxl_job_info`;

CREATE TABLE `xxl_job_info` (
  `id` int NOT NULL AUTO_INCREMENT,
  `job_group` int NOT NULL COMMENT '执行器主键ID',
  `job_desc` varchar(255) NOT NULL,
  `add_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `author` varchar(64) DEFAULT NULL COMMENT '作者',
  `alarm_email` varchar(255) DEFAULT NULL COMMENT '报警邮件',
  `schedule_type` varchar(50) NOT NULL DEFAULT 'NONE' COMMENT '调度类型',
  `schedule_conf` varchar(128) DEFAULT NULL COMMENT '调度配置，值含义取决于调度类型',
  `misfire_strategy` varchar(50) NOT NULL DEFAULT 'DO_NOTHING' COMMENT '调度过期策略',
  `executor_route_strategy` varchar(50) DEFAULT NULL COMMENT '执行器路由策略',
  `executor_handler` varchar(255) DEFAULT NULL COMMENT '执行器任务handler',
  `executor_param` varchar(512) DEFAULT NULL COMMENT '执行器任务参数',
  `executor_block_strategy` varchar(50) DEFAULT NULL COMMENT '阻塞处理策略',
  `executor_timeout` int NOT NULL DEFAULT '0' COMMENT '任务执行超时时间，单位秒',
  `executor_fail_retry_count` int NOT NULL DEFAULT '0' COMMENT '失败重试次数',
  `glue_type` varchar(50) NOT NULL COMMENT 'GLUE类型',
  `glue_source` mediumtext COMMENT 'GLUE源代码',
  `glue_remark` varchar(128) DEFAULT NULL COMMENT 'GLUE备注',
  `glue_updatetime` datetime DEFAULT NULL COMMENT 'GLUE更新时间',
  `child_jobid` varchar(255) DEFAULT NULL COMMENT '子任务ID，多个逗号分隔',
  `trigger_status` tinyint NOT NULL DEFAULT '0' COMMENT '调度状态：0-停止，1-运行',
  `trigger_last_time` bigint NOT NULL DEFAULT '0' COMMENT '上次调度时间',
  `trigger_next_time` bigint NOT NULL DEFAULT '0' COMMENT '下次调度时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

LOCK TABLES `xxl_job_info` WRITE;
/*!40000 ALTER TABLE `xxl_job_info` DISABLE KEYS */;

INSERT INTO `xxl_job_info` (`id`, `job_group`, `job_desc`, `add_time`, `update_time`, `author`, `alarm_email`, `schedule_type`, `schedule_conf`, `misfire_strategy`, `executor_route_strategy`, `executor_handler`, `executor_param`, `executor_block_strategy`, `executor_timeout`, `executor_fail_retry_count`, `glue_type`, `glue_source`, `glue_remark`, `glue_updatetime`, `child_jobid`, `trigger_status`, `trigger_last_time`, `trigger_next_time`)
VALUES
	(1,1,'更新奖品库存任务','2018-11-03 22:21:31','2024-08-10 09:17:47','XXL','','CRON','0/5 * * * * ?','DO_NOTHING','FIRST','updateAwardStockJob','','SERIAL_EXECUTION',0,0,'BEAN','','GLUE代码初始化','2018-11-03 22:21:31','',0,0,0),
	(2,1,'发送MQ消息任务队列(DB1)','2024-08-10 09:09:27','2024-08-10 09:15:44','XXL','','CRON','0/5 * * * * ?','DO_NOTHING','FIRST','SendMessageTaskJob_DB1','','SERIAL_EXECUTION',0,0,'BEAN','','GLUE代码初始化','2024-08-10 09:09:27','',1,1723252715000,1723252720000),
	(3,1,'发送MQ消息任务队列(DB2)','2024-08-10 09:09:38','2024-08-10 09:15:40','XXL','','CRON','0/5 * * * * ?','DO_NOTHING','FIRST','SendMessageTaskJob_DB2','','SERIAL_EXECUTION',0,0,'BEAN','','GLUE代码初始化','2024-08-10 09:09:38','',1,1723252715000,1723252720000),
	(4,1,'更新活动sku库存任务','2024-08-10 09:10:09','2024-08-10 09:15:00','XXL','','CRON','0/5 * * * * ?','DO_NOTHING','FIRST','UpdateActivitySkuStockJob','','SERIAL_EXECUTION',0,0,'BEAN','','GLUE代码初始化','2024-08-10 09:10:09','',1,1723252715000,1723252720000);

/*!40000 ALTER TABLE `xxl_job_info` ENABLE KEYS */;
UNLOCK TABLES;


# 转储表 xxl_job_lock
# ------------------------------------------------------------

DROP TABLE IF EXISTS `xxl_job_lock`;

CREATE TABLE `xxl_job_lock` (
  `lock_name` varchar(50) NOT NULL COMMENT '锁名称',
  PRIMARY KEY (`lock_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

LOCK TABLES `xxl_job_lock` WRITE;
/*!40000 ALTER TABLE `xxl_job_lock` DISABLE KEYS */;

INSERT INTO `xxl_job_lock` (`lock_name`)
VALUES
	('schedule_lock');

/*!40000 ALTER TABLE `xxl_job_lock` ENABLE KEYS */;
UNLOCK TABLES;


# 转储表 xxl_job_log
# ------------------------------------------------------------

DROP TABLE IF EXISTS `xxl_job_log`;

CREATE TABLE `xxl_job_log` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `job_group` int NOT NULL COMMENT '执行器主键ID',
  `job_id` int NOT NULL COMMENT '任务，主键ID',
  `executor_address` varchar(255) DEFAULT NULL COMMENT '执行器地址，本次执行的地址',
  `executor_handler` varchar(255) DEFAULT NULL COMMENT '执行器任务handler',
  `executor_param` varchar(512) DEFAULT NULL COMMENT '执行器任务参数',
  `executor_sharding_param` varchar(20) DEFAULT NULL COMMENT '执行器任务分片参数，格式如 1/2',
  `executor_fail_retry_count` int NOT NULL DEFAULT '0' COMMENT '失败重试次数',
  `trigger_time` datetime DEFAULT NULL COMMENT '调度-时间',
  `trigger_code` int NOT NULL COMMENT '调度-结果',
  `trigger_msg` text COMMENT '调度-日志',
  `handle_time` datetime DEFAULT NULL COMMENT '执行-时间',
  `handle_code` int NOT NULL COMMENT '执行-状态',
  `handle_msg` text COMMENT '执行-日志',
  `alarm_status` tinyint NOT NULL DEFAULT '0' COMMENT '告警状态：0-默认、1-无需告警、2-告警成功、3-告警失败',
  PRIMARY KEY (`id`),
  KEY `I_trigger_time` (`trigger_time`),
  KEY `I_handle_code` (`handle_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

LOCK TABLES `xxl_job_log` WRITE;
/*!40000 ALTER TABLE `xxl_job_log` DISABLE KEYS */;

INSERT INTO `xxl_job_log` (`id`, `job_group`, `job_id`, `executor_address`, `executor_handler`, `executor_param`, `executor_sharding_param`, `executor_fail_retry_count`, `trigger_time`, `trigger_code`, `trigger_msg`, `handle_time`, `handle_code`, `handle_msg`, `alarm_status`)
VALUES
	(111,1,4,NULL,'UpdateActivitySkuStockJob','',NULL,0,'2024-08-10 09:18:30',500,'任务触发类型：Cron触发<br>调度机器：172.19.0.12<br>执行器-注册方式：自动注册<br>执行器-地址列表：null<br>路由策略：第一个<br>阻塞处理策略：单机串行<br>任务超时时间：0<br>失败重试次数：0<br><br><span style=\"color:#00c0ef;\" > >>>>>>>>>>>触发调度<<<<<<<<<<< </span><br>调度失败：执行器地址为空<br><br>',NULL,0,NULL,0),
	(112,1,2,NULL,'SendMessageTaskJob_DB1','',NULL,0,'2024-08-10 09:18:35',500,'任务触发类型：Cron触发<br>调度机器：172.19.0.12<br>执行器-注册方式：自动注册<br>执行器-地址列表：null<br>路由策略：第一个<br>阻塞处理策略：单机串行<br>任务超时时间：0<br>失败重试次数：0<br><br><span style=\"color:#00c0ef;\" > >>>>>>>>>>>触发调度<<<<<<<<<<< </span><br>调度失败：执行器地址为空<br><br>',NULL,0,NULL,0),
	(113,1,4,NULL,'UpdateActivitySkuStockJob','',NULL,0,'2024-08-10 09:18:35',500,'任务触发类型：Cron触发<br>调度机器：172.19.0.12<br>执行器-注册方式：自动注册<br>执行器-地址列表：null<br>路由策略：第一个<br>阻塞处理策略：单机串行<br>任务超时时间：0<br>失败重试次数：0<br><br><span style=\"color:#00c0ef;\" > >>>>>>>>>>>触发调度<<<<<<<<<<< </span><br>调度失败：执行器地址为空<br><br>',NULL,0,NULL,0),
	(114,1,3,NULL,'SendMessageTaskJob_DB2','',NULL,0,'2024-08-10 09:18:35',500,'任务触发类型：Cron触发<br>调度机器：172.19.0.12<br>执行器-注册方式：自动注册<br>执行器-地址列表：null<br>路由策略：第一个<br>阻塞处理策略：单机串行<br>任务超时时间：0<br>失败重试次数：0<br><br><span style=\"color:#00c0ef;\" > >>>>>>>>>>>触发调度<<<<<<<<<<< </span><br>调度失败：执行器地址为空<br><br>',NULL,0,NULL,0);

/*!40000 ALTER TABLE `xxl_job_log` ENABLE KEYS */;
UNLOCK TABLES;


# 转储表 xxl_job_log_report
# ------------------------------------------------------------

DROP TABLE IF EXISTS `xxl_job_log_report`;

CREATE TABLE `xxl_job_log_report` (
  `id` int NOT NULL AUTO_INCREMENT,
  `trigger_day` datetime DEFAULT NULL COMMENT '调度-时间',
  `running_count` int NOT NULL DEFAULT '0' COMMENT '运行中-日志数量',
  `suc_count` int NOT NULL DEFAULT '0' COMMENT '执行成功-日志数量',
  `fail_count` int NOT NULL DEFAULT '0' COMMENT '执行失败-日志数量',
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `i_trigger_day` (`trigger_day`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

LOCK TABLES `xxl_job_log_report` WRITE;
/*!40000 ALTER TABLE `xxl_job_log_report` DISABLE KEYS */;

INSERT INTO `xxl_job_log_report` (`id`, `trigger_day`, `running_count`, `suc_count`, `fail_count`, `update_time`)
VALUES
	(1,'2024-08-10 00:00:00',0,45,39,NULL),
	(2,'2024-08-09 00:00:00',0,0,0,NULL),
	(3,'2024-08-08 00:00:00',0,0,0,NULL);

/*!40000 ALTER TABLE `xxl_job_log_report` ENABLE KEYS */;
UNLOCK TABLES;


# 转储表 xxl_job_logglue
# ------------------------------------------------------------

DROP TABLE IF EXISTS `xxl_job_logglue`;

CREATE TABLE `xxl_job_logglue` (
  `id` int NOT NULL AUTO_INCREMENT,
  `job_id` int NOT NULL COMMENT '任务，主键ID',
  `glue_type` varchar(50) DEFAULT NULL COMMENT 'GLUE类型',
  `glue_source` mediumtext COMMENT 'GLUE源代码',
  `glue_remark` varchar(128) NOT NULL COMMENT 'GLUE备注',
  `add_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;



# 转储表 xxl_job_registry
# ------------------------------------------------------------

DROP TABLE IF EXISTS `xxl_job_registry`;

CREATE TABLE `xxl_job_registry` (
  `id` int NOT NULL AUTO_INCREMENT,
  `registry_group` varchar(50) NOT NULL,
  `registry_key` varchar(255) NOT NULL,
  `registry_value` varchar(255) NOT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `i_g_k_v` (`registry_group`,`registry_key`,`registry_value`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;



# 转储表 xxl_job_user
# ------------------------------------------------------------

DROP TABLE IF EXISTS `xxl_job_user`;

CREATE TABLE `xxl_job_user` (
  `id` int NOT NULL AUTO_INCREMENT,
  `username` varchar(50) NOT NULL COMMENT '账号',
  `password` varchar(50) NOT NULL COMMENT '密码',
  `role` tinyint NOT NULL COMMENT '角色：0-普通用户、1-管理员',
  `permission` varchar(255) DEFAULT NULL COMMENT '权限：执行器ID列表，多个逗号分割',
  PRIMARY KEY (`id`),
  UNIQUE KEY `i_username` (`username`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

LOCK TABLES `xxl_job_user` WRITE;
/*!40000 ALTER TABLE `xxl_job_user` DISABLE KEYS */;

INSERT INTO `xxl_job_user` (`id`, `username`, `password`, `role`, `permission`)
VALUES
	(1,'admin','e10adc3949ba59abbe56e057f20f883e',1,NULL);

/*!40000 ALTER TABLE `xxl_job_user` ENABLE KEYS */;
UNLOCK TABLES;



/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
