/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 80020
 Source Host           : localhost:3306
 Source Schema         : platform

 Target Server Type    : MySQL
 Target Server Version : 80020
 File Encoding         : 65001

 Date: 14/10/2020 11:33:10
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for QRTZ_BLOB_TRIGGERS
-- ----------------------------
DROP TABLE IF EXISTS `QRTZ_BLOB_TRIGGERS`;
CREATE TABLE `QRTZ_BLOB_TRIGGERS` (
  `SCHED_NAME` varchar(120) COLLATE utf8_bin NOT NULL,
  `TRIGGER_NAME` varchar(200) COLLATE utf8_bin NOT NULL,
  `TRIGGER_GROUP` varchar(200) COLLATE utf8_bin NOT NULL,
  `BLOB_DATA` blob,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  KEY `SCHED_NAME` (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  CONSTRAINT `QRTZ_BLOB_TRIGGERS_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `QRTZ_TRIGGERS` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Records of QRTZ_BLOB_TRIGGERS
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for QRTZ_CALENDARS
-- ----------------------------
DROP TABLE IF EXISTS `QRTZ_CALENDARS`;
CREATE TABLE `QRTZ_CALENDARS` (
  `SCHED_NAME` varchar(120) COLLATE utf8_bin NOT NULL,
  `CALENDAR_NAME` varchar(200) COLLATE utf8_bin NOT NULL,
  `CALENDAR` blob NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`CALENDAR_NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Records of QRTZ_CALENDARS
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for QRTZ_CRON_TRIGGERS
-- ----------------------------
DROP TABLE IF EXISTS `QRTZ_CRON_TRIGGERS`;
CREATE TABLE `QRTZ_CRON_TRIGGERS` (
  `SCHED_NAME` varchar(120) COLLATE utf8_bin NOT NULL,
  `TRIGGER_NAME` varchar(200) COLLATE utf8_bin NOT NULL,
  `TRIGGER_GROUP` varchar(200) COLLATE utf8_bin NOT NULL,
  `CRON_EXPRESSION` varchar(120) COLLATE utf8_bin NOT NULL,
  `TIME_ZONE_ID` varchar(80) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  CONSTRAINT `QRTZ_CRON_TRIGGERS_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `QRTZ_TRIGGERS` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='Quartz定时任务时间触发器';

-- ----------------------------
-- Records of QRTZ_CRON_TRIGGERS
-- ----------------------------
BEGIN;
INSERT INTO `QRTZ_CRON_TRIGGERS` VALUES ('DefaultQuartzScheduler', 'job', 'test2', '0 1 * * * ?', 'Asia/Shanghai');
INSERT INTO `QRTZ_CRON_TRIGGERS` VALUES ('DefaultQuartzScheduler', 'job2', 'test', '10 1 * * * ?', 'Asia/Shanghai');
INSERT INTO `QRTZ_CRON_TRIGGERS` VALUES ('DefaultQuartzScheduler', 'job3', 'test2', '15 2 * * * ?', 'Asia/Shanghai');
INSERT INTO `QRTZ_CRON_TRIGGERS` VALUES ('DefaultQuartzScheduler', 'myJob', '123', '0 0 1 * * ?', 'Asia/Shanghai');
COMMIT;

-- ----------------------------
-- Table structure for QRTZ_FIRED_TRIGGERS
-- ----------------------------
DROP TABLE IF EXISTS `QRTZ_FIRED_TRIGGERS`;
CREATE TABLE `QRTZ_FIRED_TRIGGERS` (
  `SCHED_NAME` varchar(120) COLLATE utf8_bin NOT NULL,
  `ENTRY_ID` varchar(95) COLLATE utf8_bin NOT NULL,
  `TRIGGER_NAME` varchar(200) COLLATE utf8_bin NOT NULL,
  `TRIGGER_GROUP` varchar(200) COLLATE utf8_bin NOT NULL,
  `INSTANCE_NAME` varchar(200) COLLATE utf8_bin NOT NULL,
  `FIRED_TIME` bigint NOT NULL,
  `SCHED_TIME` bigint NOT NULL,
  `PRIORITY` int NOT NULL,
  `STATE` varchar(16) COLLATE utf8_bin NOT NULL,
  `JOB_NAME` varchar(200) COLLATE utf8_bin DEFAULT NULL,
  `JOB_GROUP` varchar(200) COLLATE utf8_bin DEFAULT NULL,
  `IS_NONCONCURRENT` varchar(1) COLLATE utf8_bin DEFAULT NULL,
  `REQUESTS_RECOVERY` varchar(1) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`SCHED_NAME`,`ENTRY_ID`),
  KEY `IDX_QRTZ_FT_TRIG_INST_NAME` (`SCHED_NAME`,`INSTANCE_NAME`),
  KEY `IDX_QRTZ_FT_INST_JOB_REQ_RCVRY` (`SCHED_NAME`,`INSTANCE_NAME`,`REQUESTS_RECOVERY`),
  KEY `IDX_QRTZ_FT_J_G` (`SCHED_NAME`,`JOB_NAME`,`JOB_GROUP`),
  KEY `IDX_QRTZ_FT_JG` (`SCHED_NAME`,`JOB_GROUP`),
  KEY `IDX_QRTZ_FT_T_G` (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  KEY `IDX_QRTZ_FT_TG` (`SCHED_NAME`,`TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Records of QRTZ_FIRED_TRIGGERS
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for QRTZ_JOB_DETAILS
-- ----------------------------
DROP TABLE IF EXISTS `QRTZ_JOB_DETAILS`;
CREATE TABLE `QRTZ_JOB_DETAILS` (
  `SCHED_NAME` varchar(120) COLLATE utf8_bin NOT NULL,
  `JOB_NAME` varchar(200) COLLATE utf8_bin NOT NULL,
  `JOB_GROUP` varchar(200) COLLATE utf8_bin NOT NULL,
  `DESCRIPTION` varchar(250) COLLATE utf8_bin DEFAULT NULL,
  `JOB_CLASS_NAME` varchar(250) COLLATE utf8_bin NOT NULL,
  `IS_DURABLE` varchar(1) COLLATE utf8_bin NOT NULL,
  `IS_NONCONCURRENT` varchar(1) COLLATE utf8_bin NOT NULL,
  `IS_UPDATE_DATA` varchar(1) COLLATE utf8_bin NOT NULL,
  `REQUESTS_RECOVERY` varchar(1) COLLATE utf8_bin NOT NULL,
  `JOB_DATA` blob,
  PRIMARY KEY (`SCHED_NAME`,`JOB_NAME`,`JOB_GROUP`),
  KEY `IDX_QRTZ_J_REQ_RECOVERY` (`SCHED_NAME`,`REQUESTS_RECOVERY`),
  KEY `IDX_QRTZ_J_GRP` (`SCHED_NAME`,`JOB_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='Quartz定时任务详情表';

-- ----------------------------
-- Records of QRTZ_JOB_DETAILS
-- ----------------------------
BEGIN;
INSERT INTO `QRTZ_JOB_DETAILS` VALUES ('DefaultQuartzScheduler', 'job', 'test2', '测试一下', 'com.guihe.platform.task.job.MyJob', '0', '0', '0', '0', 0xACED0005737200156F72672E71756172747A2E4A6F62446174614D61709FB083E8BFA9B0CB020000787200266F72672E71756172747A2E7574696C732E537472696E674B65794469727479466C61674D61708208E8C3FBC55D280200015A0013616C6C6F77735472616E7369656E74446174617872001D6F72672E71756172747A2E7574696C732E4469727479466C61674D617013E62EAD28760ACE0200025A000564697274794C00036D617074000F4C6A6176612F7574696C2F4D61703B787001737200116A6176612E7574696C2E486173684D61700507DAC1C31660D103000246000A6C6F6164466163746F724900097468726573686F6C6478703F4000000000000C770800000010000000017400046E616D657400036A6F627800);
INSERT INTO `QRTZ_JOB_DETAILS` VALUES ('DefaultQuartzScheduler', 'job2', 'test', NULL, 'com.guihe.platform.task.job.MyJob', '0', '0', '0', '0', 0xACED0005737200156F72672E71756172747A2E4A6F62446174614D61709FB083E8BFA9B0CB020000787200266F72672E71756172747A2E7574696C732E537472696E674B65794469727479466C61674D61708208E8C3FBC55D280200015A0013616C6C6F77735472616E7369656E74446174617872001D6F72672E71756172747A2E7574696C732E4469727479466C61674D617013E62EAD28760ACE0200025A000564697274794C00036D617074000F4C6A6176612F7574696C2F4D61703B787001737200116A6176612E7574696C2E486173684D61700507DAC1C31660D103000246000A6C6F6164466163746F724900097468726573686F6C6478703F4000000000000C770800000010000000017400046E616D65737200116A6176612E6C616E672E496E746567657212E2A0A4F781873802000149000576616C7565787200106A6176612E6C616E672E4E756D62657286AC951D0B94E08B0200007870000000027800);
INSERT INTO `QRTZ_JOB_DETAILS` VALUES ('DefaultQuartzScheduler', 'job3', 'test2', NULL, 'com.guihe.platform.task.job.MyJob', '0', '0', '0', '0', 0xACED0005737200156F72672E71756172747A2E4A6F62446174614D61709FB083E8BFA9B0CB020000787200266F72672E71756172747A2E7574696C732E537472696E674B65794469727479466C61674D61708208E8C3FBC55D280200015A0013616C6C6F77735472616E7369656E74446174617872001D6F72672E71756172747A2E7574696C732E4469727479466C61674D617013E62EAD28760ACE0200025A000564697274794C00036D617074000F4C6A6176612F7574696C2F4D61703B787001737200116A6176612E7574696C2E486173684D61700507DAC1C31660D103000246000A6C6F6164466163746F724900097468726573686F6C6478703F4000000000000C770800000010000000017400046E616D65737200116A6176612E6C616E672E496E746567657212E2A0A4F781873802000149000576616C7565787200106A6176612E6C616E672E4E756D62657286AC951D0B94E08B0200007870000000037800);
INSERT INTO `QRTZ_JOB_DETAILS` VALUES ('DefaultQuartzScheduler', 'myJob', '123', '测试', 'com.guihe.platform.task.job.MyJob', '0', '0', '0', '0', 0xACED0005737200156F72672E71756172747A2E4A6F62446174614D61709FB083E8BFA9B0CB020000787200266F72672E71756172747A2E7574696C732E537472696E674B65794469727479466C61674D61708208E8C3FBC55D280200015A0013616C6C6F77735472616E7369656E74446174617872001D6F72672E71756172747A2E7574696C732E4469727479466C61674D617013E62EAD28760ACE0200025A000564697274794C00036D617074000F4C6A6176612F7574696C2F4D61703B787001737200116A6176612E7574696C2E486173684D61700507DAC1C31660D103000246000A6C6F6164466163746F724900097468726573686F6C6478703F4000000000000C770800000010000000017400046E616D65740006E6B58BE8AF957800);
COMMIT;

-- ----------------------------
-- Table structure for QRTZ_LOCKS
-- ----------------------------
DROP TABLE IF EXISTS `QRTZ_LOCKS`;
CREATE TABLE `QRTZ_LOCKS` (
  `SCHED_NAME` varchar(120) COLLATE utf8_bin NOT NULL,
  `LOCK_NAME` varchar(40) COLLATE utf8_bin NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`LOCK_NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Records of QRTZ_LOCKS
-- ----------------------------
BEGIN;
INSERT INTO `QRTZ_LOCKS` VALUES ('DefaultQuartzScheduler', 'TRIGGER_ACCESS');
COMMIT;

-- ----------------------------
-- Table structure for QRTZ_PAUSED_TRIGGER_GRPS
-- ----------------------------
DROP TABLE IF EXISTS `QRTZ_PAUSED_TRIGGER_GRPS`;
CREATE TABLE `QRTZ_PAUSED_TRIGGER_GRPS` (
  `SCHED_NAME` varchar(120) COLLATE utf8_bin NOT NULL,
  `TRIGGER_GROUP` varchar(200) COLLATE utf8_bin NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Records of QRTZ_PAUSED_TRIGGER_GRPS
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for QRTZ_SCHEDULER_STATE
-- ----------------------------
DROP TABLE IF EXISTS `QRTZ_SCHEDULER_STATE`;
CREATE TABLE `QRTZ_SCHEDULER_STATE` (
  `SCHED_NAME` varchar(120) COLLATE utf8_bin NOT NULL,
  `INSTANCE_NAME` varchar(200) COLLATE utf8_bin NOT NULL,
  `LAST_CHECKIN_TIME` bigint NOT NULL,
  `CHECKIN_INTERVAL` bigint NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`INSTANCE_NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Records of QRTZ_SCHEDULER_STATE
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for QRTZ_SIMPLE_TRIGGERS
-- ----------------------------
DROP TABLE IF EXISTS `QRTZ_SIMPLE_TRIGGERS`;
CREATE TABLE `QRTZ_SIMPLE_TRIGGERS` (
  `SCHED_NAME` varchar(120) COLLATE utf8_bin NOT NULL,
  `TRIGGER_NAME` varchar(200) COLLATE utf8_bin NOT NULL,
  `TRIGGER_GROUP` varchar(200) COLLATE utf8_bin NOT NULL,
  `REPEAT_COUNT` bigint NOT NULL,
  `REPEAT_INTERVAL` bigint NOT NULL,
  `TIMES_TRIGGERED` bigint NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  CONSTRAINT `QRTZ_SIMPLE_TRIGGERS_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `QRTZ_TRIGGERS` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Records of QRTZ_SIMPLE_TRIGGERS
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for QRTZ_SIMPROP_TRIGGERS
-- ----------------------------
DROP TABLE IF EXISTS `QRTZ_SIMPROP_TRIGGERS`;
CREATE TABLE `QRTZ_SIMPROP_TRIGGERS` (
  `SCHED_NAME` varchar(120) COLLATE utf8_bin NOT NULL,
  `TRIGGER_NAME` varchar(200) COLLATE utf8_bin NOT NULL,
  `TRIGGER_GROUP` varchar(200) COLLATE utf8_bin NOT NULL,
  `STR_PROP_1` varchar(512) COLLATE utf8_bin DEFAULT NULL,
  `STR_PROP_2` varchar(512) COLLATE utf8_bin DEFAULT NULL,
  `STR_PROP_3` varchar(512) COLLATE utf8_bin DEFAULT NULL,
  `INT_PROP_1` int DEFAULT NULL,
  `INT_PROP_2` int DEFAULT NULL,
  `LONG_PROP_1` bigint DEFAULT NULL,
  `LONG_PROP_2` bigint DEFAULT NULL,
  `DEC_PROP_1` decimal(13,4) DEFAULT NULL,
  `DEC_PROP_2` decimal(13,4) DEFAULT NULL,
  `BOOL_PROP_1` varchar(1) COLLATE utf8_bin DEFAULT NULL,
  `BOOL_PROP_2` varchar(1) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  CONSTRAINT `QRTZ_SIMPROP_TRIGGERS_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `QRTZ_TRIGGERS` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Records of QRTZ_SIMPROP_TRIGGERS
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for QRTZ_TRIGGERS
-- ----------------------------
DROP TABLE IF EXISTS `QRTZ_TRIGGERS`;
CREATE TABLE `QRTZ_TRIGGERS` (
  `SCHED_NAME` varchar(120) COLLATE utf8_bin NOT NULL,
  `TRIGGER_NAME` varchar(200) COLLATE utf8_bin NOT NULL,
  `TRIGGER_GROUP` varchar(200) COLLATE utf8_bin NOT NULL,
  `JOB_NAME` varchar(200) COLLATE utf8_bin NOT NULL,
  `JOB_GROUP` varchar(200) COLLATE utf8_bin NOT NULL,
  `DESCRIPTION` varchar(250) COLLATE utf8_bin DEFAULT NULL,
  `NEXT_FIRE_TIME` bigint DEFAULT NULL,
  `PREV_FIRE_TIME` bigint DEFAULT NULL,
  `PRIORITY` int DEFAULT NULL,
  `TRIGGER_STATE` varchar(16) COLLATE utf8_bin NOT NULL,
  `TRIGGER_TYPE` varchar(8) COLLATE utf8_bin NOT NULL,
  `START_TIME` bigint NOT NULL,
  `END_TIME` bigint DEFAULT NULL,
  `CALENDAR_NAME` varchar(200) COLLATE utf8_bin DEFAULT NULL,
  `MISFIRE_INSTR` smallint DEFAULT NULL,
  `JOB_DATA` blob,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  KEY `IDX_QRTZ_T_J` (`SCHED_NAME`,`JOB_NAME`,`JOB_GROUP`),
  KEY `IDX_QRTZ_T_JG` (`SCHED_NAME`,`JOB_GROUP`),
  KEY `IDX_QRTZ_T_C` (`SCHED_NAME`,`CALENDAR_NAME`),
  KEY `IDX_QRTZ_T_G` (`SCHED_NAME`,`TRIGGER_GROUP`),
  KEY `IDX_QRTZ_T_STATE` (`SCHED_NAME`,`TRIGGER_STATE`),
  KEY `IDX_QRTZ_T_N_STATE` (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`,`TRIGGER_STATE`),
  KEY `IDX_QRTZ_T_N_G_STATE` (`SCHED_NAME`,`TRIGGER_GROUP`,`TRIGGER_STATE`),
  KEY `IDX_QRTZ_T_NEXT_FIRE_TIME` (`SCHED_NAME`,`NEXT_FIRE_TIME`),
  KEY `IDX_QRTZ_T_NFT_ST` (`SCHED_NAME`,`TRIGGER_STATE`,`NEXT_FIRE_TIME`),
  KEY `IDX_QRTZ_T_NFT_MISFIRE` (`SCHED_NAME`,`MISFIRE_INSTR`,`NEXT_FIRE_TIME`),
  KEY `IDX_QRTZ_T_NFT_ST_MISFIRE` (`SCHED_NAME`,`MISFIRE_INSTR`,`NEXT_FIRE_TIME`,`TRIGGER_STATE`),
  KEY `IDX_QRTZ_T_NFT_ST_MISFIRE_GRP` (`SCHED_NAME`,`MISFIRE_INSTR`,`NEXT_FIRE_TIME`,`TRIGGER_GROUP`,`TRIGGER_STATE`),
  CONSTRAINT `QRTZ_TRIGGERS_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `JOB_NAME`, `JOB_GROUP`) REFERENCES `QRTZ_JOB_DETAILS` (`SCHED_NAME`, `JOB_NAME`, `JOB_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='Quartz定时任务执行表';

-- ----------------------------
-- Records of QRTZ_TRIGGERS
-- ----------------------------
BEGIN;
INSERT INTO `QRTZ_TRIGGERS` VALUES ('DefaultQuartzScheduler', 'job', 'test2', 'job', 'test2', NULL, 1602648060000, 1602644460000, 5, 'WAITING', 'CRON', 1601281834000, 0, NULL, 0, '');
INSERT INTO `QRTZ_TRIGGERS` VALUES ('DefaultQuartzScheduler', 'job2', 'test', 'job2', 'test', NULL, 1602648070000, 1602644470000, 5, 'WAITING', 'CRON', 1601101467000, 0, NULL, 0, '');
INSERT INTO `QRTZ_TRIGGERS` VALUES ('DefaultQuartzScheduler', 'job3', 'test2', 'job3', 'test2', NULL, 1602648135000, 1602644535000, 5, 'WAITING', 'CRON', 1601101467000, 0, NULL, 0, '');
INSERT INTO `QRTZ_TRIGGERS` VALUES ('DefaultQuartzScheduler', 'myJob', '123', 'myJob', '123', NULL, 1602694800000, 1602610519319, 5, 'WAITING', 'CRON', 1601279458000, 0, NULL, 0, '');
COMMIT;

-- ----------------------------
-- Table structure for qrtz_logs
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_logs`;
CREATE TABLE `qrtz_logs` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
  `class_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '定时器类名',
  `method_name` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '定时器方法名',
  `job_name` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '定时器名称',
  `job_group` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '定时器分组',
  `description` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '定时器说明',
  `play_time` double(32,2) DEFAULT NULL COMMENT '总耗时',
  `job_data` text COLLATE utf8_bin COMMENT '定时器执行时所需参数',
  `status` int DEFAULT '1' COMMENT '1成功2失败',
  `err_msg` text COLLATE utf8_bin COMMENT '报错信息',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=146 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='定时任务执行日志 ';

-- ----------------------------
-- Records of qrtz_logs
-- ----------------------------
BEGIN;
INSERT INTO `qrtz_logs` VALUES (1, 'com.guihe.platform.task.job.MyJob', 'executeInternal', 'job', 'test2', '测试一下', 0.00, '{\"name\":\"job\"}', 1, NULL, '2020-09-28 17:00:39');
INSERT INTO `qrtz_logs` VALUES (2, 'com.guihe.platform.task.job.MyJob', 'executeInternal', 'myJob', '123', '测试', 0.00, '{\"name\":\"测试\"}', 1, NULL, '2020-09-28 17:00:42');
INSERT INTO `qrtz_logs` VALUES (3, 'com.guihe.platform.task.job.MyJob', 'executeInternal', 'job3', 'test2', NULL, 0.00, '{\"name\":3}', 1, NULL, '2020-09-28 17:00:43');
INSERT INTO `qrtz_logs` VALUES (4, 'com.guihe.platform.task.job.MyJob', 'executeInternal', 'job2', 'test', NULL, 0.00, '{\"name\":2}', 1, NULL, '2020-09-28 17:00:44');
INSERT INTO `qrtz_logs` VALUES (5, 'com.guihe.platform.task.job.MyJob', 'executeInternal', 'myJob', '123', '测试', 0.00, '{\"name\":\"测试\"}', 1, NULL, '2020-09-28 17:01:00');
INSERT INTO `qrtz_logs` VALUES (6, 'com.guihe.platform.task.job.MyJob', 'executeInternal', 'job2', 'test', NULL, 0.00, '{\"name\":2}', 1, NULL, '2020-09-28 17:01:10');
INSERT INTO `qrtz_logs` VALUES (7, 'com.guihe.platform.task.job.MyJob', 'executeInternal', 'job3', 'test2', NULL, 0.00, '{\"name\":3}', 1, NULL, '2020-09-28 17:01:15');
INSERT INTO `qrtz_logs` VALUES (8, 'com.guihe.platform.task.job.MyJob', 'executeInternal', 'job', 'test2', '测试一下', 0.00, '{\"name\":\"job\"}', 1, NULL, '2020-09-28 17:01:20');
INSERT INTO `qrtz_logs` VALUES (9, 'com.guihe.platform.task.job.MyJob', 'executeInternal', 'myJob', '123', '测试', 0.00, '{\"name\":\"测试\"}', 1, NULL, '2020-09-28 17:02:00');
INSERT INTO `qrtz_logs` VALUES (10, 'com.guihe.platform.task.job.MyJob', 'executeInternal', 'job2', 'test', NULL, 0.00, '{\"name\":2}', 1, NULL, '2020-09-28 17:02:10');
INSERT INTO `qrtz_logs` VALUES (11, 'com.guihe.platform.task.job.MyJob', 'executeInternal', 'job3', 'test2', NULL, 0.00, '{\"name\":3}', 1, NULL, '2020-09-28 17:02:15');
INSERT INTO `qrtz_logs` VALUES (12, 'com.guihe.platform.task.job.MyJob', 'executeInternal', 'job', 'test2', '测试一下', 0.00, '{\"name\":\"job\"}', 1, NULL, '2020-09-28 17:02:20');
INSERT INTO `qrtz_logs` VALUES (13, 'com.guihe.platform.task.job.MyJob', 'executeInternal', 'job2', 'test', NULL, 0.00, '{\"name\":2}', 1, NULL, '2020-09-28 17:02:49');
INSERT INTO `qrtz_logs` VALUES (14, 'com.guihe.platform.task.job.MyJob', 'executeInternal', 'job3', 'test2', NULL, 0.00, '{\"name\":3}', 1, NULL, '2020-09-28 17:02:49');
INSERT INTO `qrtz_logs` VALUES (15, 'com.guihe.platform.task.job.MyJob', 'executeInternal', 'job', 'test2', '测试一下', 0.00, '{\"name\":\"job\"}', 1, NULL, '2020-09-28 17:02:49');
INSERT INTO `qrtz_logs` VALUES (16, 'com.guihe.platform.task.job.MyJob', 'executeInternal', 'job', 'test2', '测试一下', 0.00, '{\"name\":\"job\"}', 1, NULL, '2020-09-28 21:31:55');
INSERT INTO `qrtz_logs` VALUES (17, 'com.guihe.platform.task.job.MyJob', 'executeInternal', 'job2', 'test', NULL, 0.00, '{\"name\":2}', 1, NULL, '2020-09-29 03:32:45');
INSERT INTO `qrtz_logs` VALUES (18, 'com.guihe.platform.task.job.MyJob', 'executeInternal', 'job3', 'test2', NULL, 0.00, '{\"name\":3}', 1, NULL, '2020-09-29 03:32:45');
INSERT INTO `qrtz_logs` VALUES (19, 'com.guihe.platform.task.job.MyJob', 'executeInternal', 'job', 'test2', '测试一下', 0.00, '{\"name\":\"job\"}', 1, NULL, '2020-09-29 03:32:45');
INSERT INTO `qrtz_logs` VALUES (20, 'com.guihe.platform.task.job.MyJob', 'executeInternal', 'myJob', '123', '测试', 0.00, '{\"name\":\"测试\"}', 1, NULL, '2020-09-29 03:32:45');
INSERT INTO `qrtz_logs` VALUES (21, 'com.guihe.platform.task.job.MyJob', 'executeInternal', 'job', 'test2', '测试一下', 0.00, '{\"name\":\"job\"}', 1, NULL, '2020-09-29 09:37:48');
INSERT INTO `qrtz_logs` VALUES (22, 'com.guihe.platform.task.job.MyJob', 'executeInternal', 'job2', 'test', NULL, 0.00, '{\"name\":2}', 1, NULL, '2020-09-29 09:37:48');
INSERT INTO `qrtz_logs` VALUES (23, 'com.guihe.platform.task.job.MyJob', 'executeInternal', 'job3', 'test2', NULL, 0.00, '{\"name\":3}', 1, NULL, '2020-09-29 09:37:48');
INSERT INTO `qrtz_logs` VALUES (24, 'com.guihe.platform.task.job.MyJob', 'executeInternal', 'job', 'test2', '测试一下', 0.00, '{\"name\":\"job\"}', 1, NULL, '2020-09-29 10:01:00');
INSERT INTO `qrtz_logs` VALUES (25, 'com.guihe.platform.task.job.MyJob', 'executeInternal', 'job2', 'test', NULL, 0.00, '{\"name\":2}', 1, NULL, '2020-09-29 10:01:10');
INSERT INTO `qrtz_logs` VALUES (26, 'com.guihe.platform.task.job.MyJob', 'executeInternal', 'job3', 'test2', NULL, 0.00, '{\"name\":3}', 1, NULL, '2020-09-29 10:02:15');
INSERT INTO `qrtz_logs` VALUES (27, 'com.guihe.platform.task.job.MyJob', 'executeInternal', 'job', 'test2', '测试一下', 0.00, '{\"name\":\"job\"}', 1, NULL, '2020-09-29 10:41:45');
INSERT INTO `qrtz_logs` VALUES (28, 'com.guihe.platform.task.job.MyJob', 'executeInternal', 'job', 'test2', '测试一下', 0.00, '{\"name\":\"job\"}', 1, NULL, '2020-09-29 11:01:00');
INSERT INTO `qrtz_logs` VALUES (29, 'com.guihe.platform.task.job.MyJob', 'executeInternal', 'job2', 'test', NULL, 0.00, '{\"name\":2}', 1, NULL, '2020-09-29 11:01:10');
INSERT INTO `qrtz_logs` VALUES (30, 'com.guihe.platform.task.job.MyJob', 'executeInternal', 'job3', 'test2', NULL, 0.00, '{\"name\":3}', 1, NULL, '2020-09-29 11:02:15');
INSERT INTO `qrtz_logs` VALUES (31, 'com.guihe.platform.task.job.MyJob', 'executeInternal', 'job', 'test2', '测试一下', 0.00, '{\"name\":\"job\"}', 1, NULL, '2020-09-29 12:01:00');
INSERT INTO `qrtz_logs` VALUES (32, 'com.guihe.platform.task.job.MyJob', 'executeInternal', 'job2', 'test', NULL, 0.00, '{\"name\":2}', 1, NULL, '2020-09-29 12:01:10');
INSERT INTO `qrtz_logs` VALUES (33, 'com.guihe.platform.task.job.MyJob', 'executeInternal', 'job3', 'test2', NULL, 0.00, '{\"name\":3}', 1, NULL, '2020-09-29 12:02:15');
INSERT INTO `qrtz_logs` VALUES (34, 'com.guihe.platform.task.job.MyJob', 'executeInternal', 'job', 'test2', '测试一下', 0.00, '{\"name\":\"job\"}', 1, NULL, '2020-09-29 13:01:00');
INSERT INTO `qrtz_logs` VALUES (35, 'com.guihe.platform.task.job.MyJob', 'executeInternal', 'job2', 'test', NULL, 0.00, '{\"name\":2}', 1, NULL, '2020-09-29 13:01:10');
INSERT INTO `qrtz_logs` VALUES (36, 'com.guihe.platform.task.job.MyJob', 'executeInternal', 'job3', 'test2', NULL, 0.00, '{\"name\":3}', 1, NULL, '2020-09-29 13:02:15');
INSERT INTO `qrtz_logs` VALUES (37, 'com.guihe.platform.task.job.MyJob', 'executeInternal', 'job', 'test2', '测试一下', 0.00, '{\"name\":\"job\"}', 1, NULL, '2020-09-29 14:01:00');
INSERT INTO `qrtz_logs` VALUES (38, 'com.guihe.platform.task.job.MyJob', 'executeInternal', 'job2', 'test', NULL, 0.00, '{\"name\":2}', 1, NULL, '2020-09-29 14:01:10');
INSERT INTO `qrtz_logs` VALUES (39, 'com.guihe.platform.task.job.MyJob', 'executeInternal', 'job3', 'test2', NULL, 0.00, '{\"name\":3}', 1, NULL, '2020-09-29 14:02:15');
INSERT INTO `qrtz_logs` VALUES (40, 'com.guihe.platform.task.job.MyJob', 'executeInternal', 'job', 'test2', '测试一下', 0.00, '{\"name\":\"job\"}', 1, NULL, '2020-09-29 15:01:00');
INSERT INTO `qrtz_logs` VALUES (41, 'com.guihe.platform.task.job.MyJob', 'executeInternal', 'job2', 'test', NULL, 0.00, '{\"name\":2}', 1, NULL, '2020-09-29 15:01:10');
INSERT INTO `qrtz_logs` VALUES (42, 'com.guihe.platform.task.job.MyJob', 'executeInternal', 'job3', 'test2', NULL, 0.00, '{\"name\":3}', 1, NULL, '2020-09-29 15:02:15');
INSERT INTO `qrtz_logs` VALUES (43, 'com.guihe.platform.task.job.MyJob', 'executeInternal', 'job', 'test2', '测试一下', 0.00, '{\"name\":\"job\"}', 1, NULL, '2020-09-29 16:01:00');
INSERT INTO `qrtz_logs` VALUES (44, 'com.guihe.platform.task.job.MyJob', 'executeInternal', 'job2', 'test', NULL, 0.00, '{\"name\":2}', 1, NULL, '2020-09-29 16:01:10');
INSERT INTO `qrtz_logs` VALUES (45, 'com.guihe.platform.task.job.MyJob', 'executeInternal', 'job3', 'test2', NULL, 0.00, '{\"name\":3}', 1, NULL, '2020-09-29 16:02:15');
INSERT INTO `qrtz_logs` VALUES (46, 'com.guihe.platform.task.job.MyJob', 'executeInternal', 'job', 'test2', '测试一下', 0.00, '{\"name\":\"job\"}', 1, NULL, '2020-09-29 17:01:00');
INSERT INTO `qrtz_logs` VALUES (47, 'com.guihe.platform.task.job.MyJob', 'executeInternal', 'job2', 'test', NULL, 0.00, '{\"name\":2}', 1, NULL, '2020-09-29 17:01:10');
INSERT INTO `qrtz_logs` VALUES (48, 'com.guihe.platform.task.job.MyJob', 'executeInternal', 'job3', 'test2', NULL, 0.00, '{\"name\":3}', 1, NULL, '2020-09-29 17:02:15');
INSERT INTO `qrtz_logs` VALUES (49, 'com.guihe.platform.task.job.MyJob', 'executeInternal', 'job3', 'test2', NULL, 0.00, '{\"name\":3}', 1, NULL, '2020-10-11 10:04:31');
INSERT INTO `qrtz_logs` VALUES (50, 'com.guihe.platform.task.job.MyJob', 'executeInternal', 'job2', 'test', NULL, 0.00, '{\"name\":2}', 1, NULL, '2020-10-11 10:04:31');
INSERT INTO `qrtz_logs` VALUES (51, 'com.guihe.platform.task.job.MyJob', 'executeInternal', 'job', 'test2', '测试一下', 0.08, '{\"name\":\"job\"}', 1, NULL, '2020-10-11 10:04:31');
INSERT INTO `qrtz_logs` VALUES (52, 'com.guihe.platform.task.job.MyJob', 'executeInternal', 'myJob', '123', '测试', 0.00, '{\"name\":\"测试\"}', 1, NULL, '2020-10-11 10:04:32');
INSERT INTO `qrtz_logs` VALUES (53, 'com.guihe.platform.task.job.MyJob', 'executeInternal', 'job', 'test2', '测试一下', 0.00, '{\"name\":\"job\"}', 1, NULL, '2020-10-11 11:01:00');
INSERT INTO `qrtz_logs` VALUES (54, 'com.guihe.platform.task.job.MyJob', 'executeInternal', 'job2', 'test', NULL, 0.00, '{\"name\":2}', 1, NULL, '2020-10-11 11:01:10');
INSERT INTO `qrtz_logs` VALUES (55, 'com.guihe.platform.task.job.MyJob', 'executeInternal', 'job3', 'test2', NULL, 0.00, '{\"name\":3}', 1, NULL, '2020-10-11 11:02:15');
INSERT INTO `qrtz_logs` VALUES (56, 'com.guihe.platform.task.job.MyJob', 'executeInternal', 'job', 'test2', '测试一下', 0.00, '{\"name\":\"job\"}', 1, NULL, '2020-10-11 12:01:00');
INSERT INTO `qrtz_logs` VALUES (57, 'com.guihe.platform.task.job.MyJob', 'executeInternal', 'job2', 'test', NULL, 0.00, '{\"name\":2}', 1, NULL, '2020-10-11 12:01:10');
INSERT INTO `qrtz_logs` VALUES (58, 'com.guihe.platform.task.job.MyJob', 'executeInternal', 'job3', 'test2', NULL, 0.00, '{\"name\":3}', 1, NULL, '2020-10-11 12:02:15');
INSERT INTO `qrtz_logs` VALUES (59, 'com.guihe.platform.task.job.MyJob', 'executeInternal', 'job', 'test2', '测试一下', 0.01, '{\"name\":\"job\"}', 1, NULL, '2020-10-11 13:01:20');
INSERT INTO `qrtz_logs` VALUES (60, 'com.guihe.platform.task.job.MyJob', 'executeInternal', 'job2', 'test', NULL, 0.01, '{\"name\":2}', 1, NULL, '2020-10-11 13:01:24');
INSERT INTO `qrtz_logs` VALUES (61, 'com.guihe.platform.task.job.MyJob', 'executeInternal', 'job3', 'test2', NULL, 0.01, '{\"name\":3}', 1, NULL, '2020-10-11 13:34:38');
INSERT INTO `qrtz_logs` VALUES (62, 'com.guihe.platform.task.job.MyJob', 'executeInternal', 'job', 'test2', '测试一下', 0.00, '{\"name\":\"job\"}', 1, NULL, '2020-10-11 14:01:00');
INSERT INTO `qrtz_logs` VALUES (63, 'com.guihe.platform.task.job.MyJob', 'executeInternal', 'job2', 'test', NULL, 0.00, '{\"name\":2}', 1, NULL, '2020-10-11 14:01:10');
INSERT INTO `qrtz_logs` VALUES (64, 'com.guihe.platform.task.job.MyJob', 'executeInternal', 'job3', 'test2', NULL, 0.00, '{\"name\":3}', 1, NULL, '2020-10-11 14:02:15');
INSERT INTO `qrtz_logs` VALUES (65, 'com.guihe.platform.task.job.MyJob', 'executeInternal', 'job', 'test2', '测试一下', 0.01, '{\"name\":\"job\"}', 1, NULL, '2020-10-11 15:33:55');
INSERT INTO `qrtz_logs` VALUES (66, 'com.guihe.platform.task.job.MyJob', 'executeInternal', 'job2', 'test', NULL, 0.01, '{\"name\":2}', 1, NULL, '2020-10-11 15:33:58');
INSERT INTO `qrtz_logs` VALUES (67, 'com.guihe.platform.task.job.MyJob', 'executeInternal', 'job3', 'test2', NULL, 0.01, '{\"name\":3}', 1, NULL, '2020-10-11 15:34:02');
INSERT INTO `qrtz_logs` VALUES (68, 'com.guihe.platform.task.job.MyJob', 'executeInternal', 'job', 'test2', '测试一下', 0.00, '{\"name\":\"job\"}', 1, NULL, '2020-10-11 16:01:00');
INSERT INTO `qrtz_logs` VALUES (69, 'com.guihe.platform.task.job.MyJob', 'executeInternal', 'job2', 'test', NULL, 0.00, '{\"name\":2}', 1, NULL, '2020-10-11 16:01:10');
INSERT INTO `qrtz_logs` VALUES (70, 'com.guihe.platform.task.job.MyJob', 'executeInternal', 'job3', 'test2', NULL, 0.00, '{\"name\":3}', 1, NULL, '2020-10-11 16:02:15');
INSERT INTO `qrtz_logs` VALUES (71, 'com.guihe.platform.task.job.MyJob', 'executeInternal', 'job', 'test2', '测试一下', 0.00, '{\"name\":\"job\"}', 1, NULL, '2020-10-11 17:01:00');
INSERT INTO `qrtz_logs` VALUES (72, 'com.guihe.platform.task.job.MyJob', 'executeInternal', 'job2', 'test', NULL, 0.00, '{\"name\":2}', 1, NULL, '2020-10-11 17:01:10');
INSERT INTO `qrtz_logs` VALUES (73, 'com.guihe.platform.task.job.MyJob', 'executeInternal', 'job3', 'test2', NULL, 0.00, '{\"name\":3}', 1, NULL, '2020-10-11 17:02:15');
INSERT INTO `qrtz_logs` VALUES (74, 'com.guihe.platform.task.job.MyJob', 'executeInternal', 'job', 'test2', '测试一下', 0.00, '{\"name\":\"job\"}', 1, NULL, '2020-10-11 23:48:46');
INSERT INTO `qrtz_logs` VALUES (75, 'com.guihe.platform.task.job.MyJob', 'executeInternal', 'job2', 'test', NULL, 0.00, '{\"name\":2}', 1, NULL, '2020-10-12 05:49:02');
INSERT INTO `qrtz_logs` VALUES (76, 'com.guihe.platform.task.job.MyJob', 'executeInternal', 'job3', 'test2', NULL, 0.00, '{\"name\":3}', 1, NULL, '2020-10-12 05:49:02');
INSERT INTO `qrtz_logs` VALUES (77, 'com.guihe.platform.task.job.MyJob', 'executeInternal', 'job', 'test2', '测试一下', 0.00, '{\"name\":\"job\"}', 1, NULL, '2020-10-12 05:49:02');
INSERT INTO `qrtz_logs` VALUES (78, 'com.guihe.platform.task.job.MyJob', 'executeInternal', 'myJob', '123', '测试', 0.00, '{\"name\":\"测试\"}', 1, NULL, '2020-10-12 05:49:02');
INSERT INTO `qrtz_logs` VALUES (79, 'com.guihe.platform.task.job.MyJob', 'executeInternal', 'job', 'test2', '测试一下', 0.00, '{\"name\":\"job\"}', 1, NULL, '2020-10-12 09:54:42');
INSERT INTO `qrtz_logs` VALUES (80, 'com.guihe.platform.task.job.MyJob', 'executeInternal', 'job2', 'test', NULL, 0.00, '{\"name\":2}', 1, NULL, '2020-10-12 09:54:43');
INSERT INTO `qrtz_logs` VALUES (81, 'com.guihe.platform.task.job.MyJob', 'executeInternal', 'job3', 'test2', NULL, 0.00, '{\"name\":3}', 1, NULL, '2020-10-12 09:54:43');
INSERT INTO `qrtz_logs` VALUES (82, 'com.guihe.platform.task.job.MyJob', 'executeInternal', 'job', 'test2', '测试一下', 0.00, '{\"name\":\"job\"}', 1, NULL, '2020-10-12 18:03:04');
INSERT INTO `qrtz_logs` VALUES (83, 'com.guihe.platform.task.job.MyJob', 'executeInternal', 'job2', 'test', NULL, 0.00, '{\"name\":2}', 1, NULL, '2020-10-12 18:03:04');
INSERT INTO `qrtz_logs` VALUES (84, 'com.guihe.platform.task.job.MyJob', 'executeInternal', 'job3', 'test2', NULL, 0.00, '{\"name\":3}', 1, NULL, '2020-10-12 18:03:04');
INSERT INTO `qrtz_logs` VALUES (85, 'com.guihe.platform.task.job.MyJob', 'executeInternal', 'job', 'test2', '测试一下', 0.00, '{\"name\":\"job\"}', 1, NULL, '2020-10-12 22:26:47');
INSERT INTO `qrtz_logs` VALUES (86, 'com.guihe.platform.task.job.MyJob', 'executeInternal', 'job2', 'test', NULL, 0.00, '{\"name\":2}', 1, NULL, '2020-10-12 22:26:47');
INSERT INTO `qrtz_logs` VALUES (87, 'com.guihe.platform.task.job.MyJob', 'executeInternal', 'job3', 'test2', NULL, 0.00, '{\"name\":3}', 1, NULL, '2020-10-12 22:26:48');
INSERT INTO `qrtz_logs` VALUES (88, 'com.guihe.platform.task.job.MyJob', 'executeInternal', 'job', 'test2', '测试一下', 0.00, '{\"name\":\"job\"}', 1, NULL, '2020-10-13 06:41:54');
INSERT INTO `qrtz_logs` VALUES (89, 'com.guihe.platform.task.job.MyJob', 'executeInternal', 'job2', 'test', NULL, 0.00, '{\"name\":2}', 1, NULL, '2020-10-13 06:41:54');
INSERT INTO `qrtz_logs` VALUES (90, 'com.guihe.platform.task.job.MyJob', 'executeInternal', 'job3', 'test2', NULL, 0.00, '{\"name\":3}', 1, NULL, '2020-10-13 06:41:54');
INSERT INTO `qrtz_logs` VALUES (91, 'com.guihe.platform.task.job.MyJob', 'executeInternal', 'myJob', '123', '测试', 0.00, '{\"name\":\"测试\"}', 1, NULL, '2020-10-13 06:41:54');
INSERT INTO `qrtz_logs` VALUES (92, 'com.guihe.platform.task.job.MyJob', 'executeInternal', 'job', 'test2', '测试一下', 0.00, '{\"name\":\"job\"}', 1, NULL, '2020-10-13 08:49:25');
INSERT INTO `qrtz_logs` VALUES (93, 'com.guihe.platform.task.job.MyJob', 'executeInternal', 'job2', 'test', NULL, 0.00, '{\"name\":2}', 1, NULL, '2020-10-13 08:49:26');
INSERT INTO `qrtz_logs` VALUES (94, 'com.guihe.platform.task.job.MyJob', 'executeInternal', 'job3', 'test2', NULL, 0.00, '{\"name\":3}', 1, NULL, '2020-10-13 08:49:26');
INSERT INTO `qrtz_logs` VALUES (95, 'com.guihe.platform.task.job.MyJob', 'executeInternal', 'job', 'test2', '测试一下', 0.00, '{\"name\":\"job\"}', 1, NULL, '2020-10-13 09:01:00');
INSERT INTO `qrtz_logs` VALUES (96, 'com.guihe.platform.task.job.MyJob', 'executeInternal', 'job2', 'test', NULL, 0.00, '{\"name\":2}', 1, NULL, '2020-10-13 09:01:10');
INSERT INTO `qrtz_logs` VALUES (97, 'com.guihe.platform.task.job.MyJob', 'executeInternal', 'job3', 'test2', NULL, 0.00, '{\"name\":3}', 1, NULL, '2020-10-13 09:02:15');
INSERT INTO `qrtz_logs` VALUES (98, 'com.guihe.platform.task.job.MyJob', 'executeInternal', 'job', 'test2', '测试一下', 0.00, '{\"name\":\"job\"}', 1, NULL, '2020-10-13 10:01:00');
INSERT INTO `qrtz_logs` VALUES (99, 'com.guihe.platform.task.job.MyJob', 'executeInternal', 'job2', 'test', NULL, 0.00, '{\"name\":2}', 1, NULL, '2020-10-13 10:01:10');
INSERT INTO `qrtz_logs` VALUES (100, 'com.guihe.platform.task.job.MyJob', 'executeInternal', 'job3', 'test2', NULL, 0.00, '{\"name\":3}', 1, NULL, '2020-10-13 10:02:15');
INSERT INTO `qrtz_logs` VALUES (101, 'com.guihe.platform.task.job.MyJob', 'executeInternal', 'job', 'test2', '测试一下', 0.00, '{\"name\":\"job\"}', 1, NULL, '2020-10-13 11:01:00');
INSERT INTO `qrtz_logs` VALUES (102, 'com.guihe.platform.task.job.MyJob', 'executeInternal', 'job2', 'test', NULL, 0.00, '{\"name\":2}', 1, NULL, '2020-10-13 11:01:10');
INSERT INTO `qrtz_logs` VALUES (103, 'com.guihe.platform.task.job.MyJob', 'executeInternal', 'job3', 'test2', NULL, 0.00, '{\"name\":3}', 1, NULL, '2020-10-13 11:02:15');
INSERT INTO `qrtz_logs` VALUES (104, 'com.guihe.platform.task.job.MyJob', 'executeInternal', 'job', 'test2', '测试一下', 0.00, '{\"name\":\"job\"}', 1, NULL, '2020-10-13 11:22:05');
INSERT INTO `qrtz_logs` VALUES (105, 'com.guihe.platform.task.job.MyJob', 'executeInternal', 'job', 'test2', '测试一下', 0.00, '{\"name\":\"job\"}', 1, NULL, '2020-10-13 11:22:25');
INSERT INTO `qrtz_logs` VALUES (106, 'com.guihe.platform.task.job.MyJob', 'executeInternal', 'job', 'test2', '测试一下', 0.00, '{\"name\":\"job\"}', 1, NULL, '2020-10-13 12:01:00');
INSERT INTO `qrtz_logs` VALUES (107, 'com.guihe.platform.task.job.MyJob', 'executeInternal', 'job2', 'test', NULL, 0.00, '{\"name\":2}', 1, NULL, '2020-10-13 12:01:10');
INSERT INTO `qrtz_logs` VALUES (108, 'com.guihe.platform.task.job.MyJob', 'executeInternal', 'job3', 'test2', NULL, 0.00, '{\"name\":3}', 1, NULL, '2020-10-13 12:02:15');
INSERT INTO `qrtz_logs` VALUES (109, 'com.guihe.platform.task.job.MyJob', 'executeInternal', 'job', 'test2', '测试一下', 0.00, '{\"name\":\"job\"}', 1, NULL, '2020-10-13 13:01:00');
INSERT INTO `qrtz_logs` VALUES (110, 'com.guihe.platform.task.job.MyJob', 'executeInternal', 'job2', 'test', NULL, 0.00, '{\"name\":2}', 1, NULL, '2020-10-13 13:01:10');
INSERT INTO `qrtz_logs` VALUES (111, 'com.guihe.platform.task.job.MyJob', 'executeInternal', 'job3', 'test2', NULL, 0.00, '{\"name\":3}', 1, NULL, '2020-10-13 13:02:15');
INSERT INTO `qrtz_logs` VALUES (112, 'com.guihe.platform.task.job.MyJob', 'executeInternal', 'job', 'test2', '测试一下', 0.00, '{\"name\":\"job\"}', 1, NULL, '2020-10-13 14:01:00');
INSERT INTO `qrtz_logs` VALUES (113, 'com.guihe.platform.task.job.MyJob', 'executeInternal', 'job2', 'test', NULL, 0.00, '{\"name\":2}', 1, NULL, '2020-10-13 14:01:10');
INSERT INTO `qrtz_logs` VALUES (114, 'com.guihe.platform.task.job.MyJob', 'executeInternal', 'job3', 'test2', NULL, 0.00, '{\"name\":3}', 1, NULL, '2020-10-13 14:02:15');
INSERT INTO `qrtz_logs` VALUES (115, 'com.guihe.platform.task.job.MyJob', 'executeInternal', 'job', 'test2', '测试一下', 0.00, '{\"name\":\"job\"}', 1, NULL, '2020-10-13 15:01:00');
INSERT INTO `qrtz_logs` VALUES (116, 'com.guihe.platform.task.job.MyJob', 'executeInternal', 'job2', 'test', NULL, 0.00, '{\"name\":2}', 1, NULL, '2020-10-13 15:01:10');
INSERT INTO `qrtz_logs` VALUES (117, 'com.guihe.platform.task.job.MyJob', 'executeInternal', 'job3', 'test2', NULL, 0.00, '{\"name\":3}', 1, NULL, '2020-10-13 15:02:15');
INSERT INTO `qrtz_logs` VALUES (118, 'com.guihe.platform.task.job.MyJob', 'executeInternal', 'job', 'test2', '测试一下', 0.00, '{\"name\":\"job\"}', 1, NULL, '2020-10-13 16:01:00');
INSERT INTO `qrtz_logs` VALUES (119, 'com.guihe.platform.task.job.MyJob', 'executeInternal', 'job2', 'test', NULL, 0.00, '{\"name\":2}', 1, NULL, '2020-10-13 16:01:10');
INSERT INTO `qrtz_logs` VALUES (120, 'com.guihe.platform.task.job.MyJob', 'executeInternal', 'job3', 'test2', NULL, 0.00, '{\"name\":3}', 1, NULL, '2020-10-13 16:02:15');
INSERT INTO `qrtz_logs` VALUES (121, 'com.guihe.platform.task.job.MyJob', 'executeInternal', 'job', 'test2', '测试一下', 0.00, '{\"name\":\"job\"}', 1, NULL, '2020-10-13 17:01:00');
INSERT INTO `qrtz_logs` VALUES (122, 'com.guihe.platform.task.job.MyJob', 'executeInternal', 'job2', 'test', NULL, 0.00, '{\"name\":2}', 1, NULL, '2020-10-13 17:01:10');
INSERT INTO `qrtz_logs` VALUES (123, 'com.guihe.platform.task.job.MyJob', 'executeInternal', 'job3', 'test2', NULL, 0.00, '{\"name\":3}', 1, NULL, '2020-10-13 17:02:15');
INSERT INTO `qrtz_logs` VALUES (124, 'com.guihe.platform.task.job.MyJob', 'executeInternal', 'job', 'test2', '测试一下', 0.00, '{\"name\":\"job\"}', 1, NULL, '2020-10-13 19:34:26');
INSERT INTO `qrtz_logs` VALUES (125, 'com.guihe.platform.task.job.MyJob', 'executeInternal', 'job2', 'test', NULL, 0.00, '{\"name\":2}', 1, NULL, '2020-10-13 19:34:26');
INSERT INTO `qrtz_logs` VALUES (126, 'com.guihe.platform.task.job.MyJob', 'executeInternal', 'job3', 'test2', NULL, 0.00, '{\"name\":3}', 1, NULL, '2020-10-13 19:34:26');
INSERT INTO `qrtz_logs` VALUES (127, 'com.guihe.platform.task.job.MyJob', 'executeInternal', 'job', 'test2', '测试一下', 0.00, '{\"name\":\"job\"}', 1, NULL, '2020-10-14 01:35:19');
INSERT INTO `qrtz_logs` VALUES (128, 'com.guihe.platform.task.job.MyJob', 'executeInternal', 'job2', 'test', NULL, 0.00, '{\"name\":2}', 1, NULL, '2020-10-14 01:35:20');
INSERT INTO `qrtz_logs` VALUES (129, 'com.guihe.platform.task.job.MyJob', 'executeInternal', 'job3', 'test2', NULL, 0.00, '{\"name\":3}', 1, NULL, '2020-10-14 01:35:20');
INSERT INTO `qrtz_logs` VALUES (130, 'com.guihe.platform.task.job.MyJob', 'executeInternal', 'myJob', '123', '测试', 0.00, '{\"name\":\"测试\"}', 1, NULL, '2020-10-14 01:35:20');
INSERT INTO `qrtz_logs` VALUES (131, 'com.guihe.platform.task.job.MyJob', 'executeInternal', 'job', 'test2', '测试一下', 0.00, '{\"name\":\"job\"}', 1, NULL, '2020-10-14 05:35:49');
INSERT INTO `qrtz_logs` VALUES (132, 'com.guihe.platform.task.job.MyJob', 'executeInternal', 'job2', 'test', NULL, 0.00, '{\"name\":2}', 1, NULL, '2020-10-14 05:35:50');
INSERT INTO `qrtz_logs` VALUES (133, 'com.guihe.platform.task.job.MyJob', 'executeInternal', 'job3', 'test2', NULL, 0.00, '{\"name\":3}', 1, NULL, '2020-10-14 05:35:50');
INSERT INTO `qrtz_logs` VALUES (134, 'com.guihe.platform.task.job.MyJob', 'executeInternal', 'job', 'test2', '测试一下', 0.00, '{\"name\":\"job\"}', 1, NULL, '2020-10-14 08:20:43');
INSERT INTO `qrtz_logs` VALUES (135, 'com.guihe.platform.task.job.MyJob', 'executeInternal', 'job2', 'test', NULL, 0.00, '{\"name\":2}', 1, NULL, '2020-10-14 08:20:43');
INSERT INTO `qrtz_logs` VALUES (136, 'com.guihe.platform.task.job.MyJob', 'executeInternal', 'job3', 'test2', NULL, 0.00, '{\"name\":3}', 1, NULL, '2020-10-14 08:20:43');
INSERT INTO `qrtz_logs` VALUES (137, 'com.guihe.platform.task.job.MyJob', 'executeInternal', 'job', 'test2', '测试一下', 0.00, '{\"name\":\"job\"}', 1, NULL, '2020-10-14 09:01:00');
INSERT INTO `qrtz_logs` VALUES (138, 'com.guihe.platform.task.job.MyJob', 'executeInternal', 'job2', 'test', NULL, 0.00, '{\"name\":2}', 1, NULL, '2020-10-14 09:01:10');
INSERT INTO `qrtz_logs` VALUES (139, 'com.guihe.platform.task.job.MyJob', 'executeInternal', 'job3', 'test2', NULL, 0.00, '{\"name\":3}', 1, NULL, '2020-10-14 09:02:15');
INSERT INTO `qrtz_logs` VALUES (140, 'com.guihe.platform.task.job.MyJob', 'executeInternal', 'job', 'test2', '测试一下', 0.00, '{\"name\":\"job\"}', 1, NULL, '2020-10-14 10:01:00');
INSERT INTO `qrtz_logs` VALUES (141, 'com.guihe.platform.task.job.MyJob', 'executeInternal', 'job2', 'test', NULL, 0.00, '{\"name\":2}', 1, NULL, '2020-10-14 10:01:10');
INSERT INTO `qrtz_logs` VALUES (142, 'com.guihe.platform.task.job.MyJob', 'executeInternal', 'job3', 'test2', NULL, 0.00, '{\"name\":3}', 1, NULL, '2020-10-14 10:02:15');
INSERT INTO `qrtz_logs` VALUES (143, 'com.guihe.platform.task.job.MyJob', 'executeInternal', 'job', 'test2', '测试一下', 0.00, '{\"name\":\"job\"}', 1, NULL, '2020-10-14 11:01:00');
INSERT INTO `qrtz_logs` VALUES (144, 'com.guihe.platform.task.job.MyJob', 'executeInternal', 'job2', 'test', NULL, 0.00, '{\"name\":2}', 1, NULL, '2020-10-14 11:01:10');
INSERT INTO `qrtz_logs` VALUES (145, 'com.guihe.platform.task.job.MyJob', 'executeInternal', 'job3', 'test2', NULL, 0.00, '{\"name\":3}', 1, NULL, '2020-10-14 11:02:15');
COMMIT;

-- ----------------------------
-- Table structure for sys_department
-- ----------------------------
DROP TABLE IF EXISTS `sys_department`;
CREATE TABLE `sys_department` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT 'id',
  `parent_id` int DEFAULT NULL COMMENT '父id',
  `name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '部门名称',
  `type` int DEFAULT NULL COMMENT '是否为根0否1是',
  `status` int DEFAULT NULL COMMENT '状态1正常2禁用3删除',
  `create_by` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_bin DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_bin DEFAULT NULL COMMENT '更新人',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_bin COMMENT='部门 部门表';

-- ----------------------------
-- Records of sys_department
-- ----------------------------
BEGIN;
INSERT INTO `sys_department` VALUES (1, NULL, '123', 1, 3, 'admin', '2020-09-22 10:49:05', 'admin', '2020-09-22 15:03:17');
INSERT INTO `sys_department` VALUES (2, 1, '2', 0, 1, 'admin', '2020-09-22 13:26:51', NULL, NULL);
INSERT INTO `sys_department` VALUES (3, 2, '3', 0, 1, 'admin', '2020-09-22 13:28:52', NULL, NULL);
INSERT INTO `sys_department` VALUES (4, 1, '1', 0, 1, 'admin', '2020-09-22 13:33:55', NULL, NULL);
INSERT INTO `sys_department` VALUES (5, NULL, '测试部门', 1, 1, 'admin', '2020-09-22 13:34:12', '超级管理员', '2020-10-13 11:20:13');
INSERT INTO `sys_department` VALUES (6, 5, '子部门1', 0, 1, 'admin', '2020-09-22 15:30:56', 'admin', '2020-09-22 15:31:10');
INSERT INTO `sys_department` VALUES (7, 5, '子部门2', 0, 1, 'admin', '2020-09-22 15:31:02', NULL, NULL);
INSERT INTO `sys_department` VALUES (8, NULL, '测试部门3', 1, 1, 'admin', '2020-09-22 15:31:28', 'admin1', '2020-09-23 11:24:20');
INSERT INTO `sys_department` VALUES (9, 6, '子子部门', 0, 2, 'admin', '2020-09-22 15:33:23', NULL, NULL);
COMMIT;

-- ----------------------------
-- Table structure for sys_department_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_department_user`;
CREATE TABLE `sys_department_user` (
  `department_id` int NOT NULL,
  `user_id` int NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_bin COMMENT='部门系统用户中间表';

-- ----------------------------
-- Records of sys_department_user
-- ----------------------------
BEGIN;
INSERT INTO `sys_department_user` VALUES (5, 1);
COMMIT;

-- ----------------------------
-- Table structure for sys_dict
-- ----------------------------
DROP TABLE IF EXISTS `sys_dict`;
CREATE TABLE `sys_dict` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(40) COLLATE utf8_bin DEFAULT NULL COMMENT '名称',
  `dict_key` varchar(30) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '字典key',
  `value` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '字典value',
  `pid` int DEFAULT NULL COMMENT '父字典id',
  `content` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT '字典备注',
  `status` int DEFAULT '1' COMMENT '1启用 2禁用 3删除',
  `create_time` datetime DEFAULT NULL,
  `create_by` varchar(30) COLLATE utf8_bin DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `update_by` varchar(30) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='系统字典表';

-- ----------------------------
-- Records of sys_dict
-- ----------------------------
BEGIN;
INSERT INTO `sys_dict` VALUES (1, '测试', 'test', '1', NULL, NULL, 1, '2020-10-13 10:15:59', '超级管理员', '2020-10-13 10:20:09', '超级管理员');
INSERT INTO `sys_dict` VALUES (2, '1', '1', '1', NULL, NULL, 3, '2020-10-13 10:18:35', '超级管理员', '2020-10-13 10:19:58', '超级管理员');
INSERT INTO `sys_dict` VALUES (3, '测试1', 'test01', '1', NULL, '测试', 1, '2020-10-13 10:29:01', '超级管理员', '2020-10-13 10:49:22', '超级管理员');
INSERT INTO `sys_dict` VALUES (7, '测试子字典', '1', '1', 3, NULL, 2, NULL, NULL, '2020-10-13 10:56:52', '超级管理员');
COMMIT;

-- ----------------------------
-- Table structure for sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu` (
  `id` int NOT NULL AUTO_INCREMENT,
  `parent_id` int DEFAULT NULL,
  `name` varchar(90) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `url` varchar(150) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `perms` varchar(150) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `type` int DEFAULT NULL,
  `icon` varchar(150) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `order_num` int DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `create_by` varchar(60) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `update_by` varchar(60) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=171 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='系统菜单表';

-- ----------------------------
-- Records of sys_menu
-- ----------------------------
BEGIN;
INSERT INTO `sys_menu` VALUES (126, 0, '系统管理', '#', '#', 1, 'asdsad', 10, '2019-05-23 18:25:44', NULL, NULL, NULL);
INSERT INTO `sys_menu` VALUES (127, 126, '菜单管理', '/api/sys/menu/list', 'api:sys:menu:list', 1, NULL, 1, '2019-05-23 18:27:19', NULL, '2019-05-23 18:31:07', NULL);
INSERT INTO `sys_menu` VALUES (128, 127, '添加菜单', '/api/sys/menu/add', 'api:sys:menu:add', 2, NULL, 1, '2019-05-23 18:28:26', NULL, NULL, NULL);
INSERT INTO `sys_menu` VALUES (129, 127, '编辑菜单', '/api/sys/menu/edit', 'api:sys:menu:edit', 2, NULL, 2, '2019-05-23 18:29:04', NULL, NULL, NULL);
INSERT INTO `sys_menu` VALUES (130, 126, '删除菜单', '/api/sys/menu/remove', 'api:sys:menu:remove', 2, 'asd', 3, '2019-05-23 18:29:39', NULL, NULL, NULL);
INSERT INTO `sys_menu` VALUES (131, 126, '权限管理', '/api/sys/role/list', 'api:sys:role:list', 1, NULL, 2, '2019-05-23 18:30:33', NULL, NULL, NULL);
INSERT INTO `sys_menu` VALUES (132, 131, '分配权限', '/api/sys/role/commitTree', 'api:sys:role:commitTree', 2, NULL, 1, '2019-05-23 18:32:10', NULL, NULL, NULL);
INSERT INTO `sys_menu` VALUES (136, 126, '用户管理', '/api/sys/user/list', 'api:sys:user:list', 1, NULL, 3, '2019-05-23 18:34:42', NULL, NULL, NULL);
INSERT INTO `sys_menu` VALUES (138, 2, '提交用户信息', '/api/sys/user/commit', 'api:sys:user:commit', 2, 'asdasd', 2, '2019-05-23 18:35:57', NULL, NULL, NULL);
INSERT INTO `sys_menu` VALUES (139, 123, '启用用户', '/api/sys/user/enable', 'api:sys:user:enable', 2, '23', 3, '2019-05-23 18:37:03', NULL, NULL, NULL);
INSERT INTO `sys_menu` VALUES (140, 140, '禁用用户', '/api/sys/user/disable', 'api:sys:user:disable', 1, 'asdsad', 4, '2019-05-23 18:37:32', NULL, NULL, NULL);
INSERT INTO `sys_menu` VALUES (141, 23, '重置密码', '/api/sys/user/reset', 'api:sys:user:reset', 2, 'asd', 5, '2019-05-23 18:38:08', NULL, NULL, NULL);
INSERT INTO `sys_menu` VALUES (144, 126, '部门管理', '/api/sys/department', '#', 1, NULL, 3, NULL, NULL, NULL, NULL);
INSERT INTO `sys_menu` VALUES (145, 144, '部门列表', '/api/sys/department/list', 'api:sys:department:list', 2, NULL, 1, NULL, NULL, NULL, NULL);
INSERT INTO `sys_menu` VALUES (146, 144, '获取详情', '/api/sys/department/list', 'api:sys:department:detail', 2, NULL, 2, NULL, NULL, NULL, NULL);
INSERT INTO `sys_menu` VALUES (147, 144, '提交数据', '/api/sys/department/commit', 'api:sys:department:commit', 2, NULL, 3, NULL, NULL, NULL, NULL);
INSERT INTO `sys_menu` VALUES (148, 126, '字典管理', '/api/sys/dict', '#', 1, NULL, 1, NULL, NULL, NULL, NULL);
INSERT INTO `sys_menu` VALUES (149, 148, '查询列表', '/api/sys/dict/list', 'api:sys:dict:list', 2, NULL, 1, NULL, NULL, NULL, NULL);
INSERT INTO `sys_menu` VALUES (150, 148, '提交数据', '/api/sys/dict/commit', 'api:sys:dict:commit', 2, NULL, 2, NULL, NULL, NULL, NULL);
INSERT INTO `sys_menu` VALUES (151, 148, '获取详情', '/api/sys/dict/detail', 'api:sys:dict:detail', 2, NULL, 3, NULL, NULL, NULL, NULL);
INSERT INTO `sys_menu` VALUES (152, 148, '更改状态', '/api/sys/dict/changeStatus', 'api:sys:dict:changeStatus', 2, NULL, 4, NULL, NULL, NULL, NULL);
INSERT INTO `sys_menu` VALUES (153, 148, '删除字典', '/api/sys/dict/delete', 'api:sys:dict:delete', 2, NULL, 5, NULL, NULL, NULL, NULL);
INSERT INTO `sys_menu` VALUES (154, 144, '分配用户', '/api/sys/department/commitUser', 'api:sys:department:commitUser', 2, NULL, 4, NULL, NULL, NULL, NULL);
INSERT INTO `sys_menu` VALUES (155, 144, '更改状态', '/api/sys/department/changeStatus', 'api:sys:department:changeStatus', 2, NULL, 5, NULL, NULL, NULL, NULL);
INSERT INTO `sys_menu` VALUES (156, 144, '删除部门', '/api/sys/department/delete', 'api:sys:department:delete', 2, NULL, 6, NULL, NULL, NULL, NULL);
INSERT INTO `sys_menu` VALUES (157, 0, '服务监控', '#', '#', 1, NULL, 1, NULL, NULL, NULL, NULL);
INSERT INTO `sys_menu` VALUES (158, 157, 'Quartz日志', '/api/quartz/logs', '#', 1, NULL, 10, NULL, NULL, NULL, NULL);
INSERT INTO `sys_menu` VALUES (159, 158, '查询列表', 'api:quartz:logs:list', 'api:quartz:log:list', 2, NULL, 1, NULL, NULL, NULL, NULL);
INSERT INTO `sys_menu` VALUES (160, 157, 'Quartz管理', '/api/quartz', '#', 1, NULL, 9, NULL, NULL, NULL, NULL);
INSERT INTO `sys_menu` VALUES (161, 160, '查询列表', '/api/quartz/list', 'api:quartz:list', 2, NULL, 1, NULL, NULL, NULL, NULL);
INSERT INTO `sys_menu` VALUES (162, 160, '删除任务', '/api/quartz/deleteJob', 'api:quartz:deleteJob', 2, NULL, 2, NULL, NULL, NULL, NULL);
INSERT INTO `sys_menu` VALUES (163, 160, '暂停任务', '/api/quartz/pauseJob', 'api:quartz:pauseJob', 2, NULL, 3, NULL, NULL, NULL, NULL);
INSERT INTO `sys_menu` VALUES (164, 160, '恢复任务', 'api:quartz:resumeJob', 'api:quartz:resumeJob', 2, NULL, 4, NULL, NULL, NULL, NULL);
INSERT INTO `sys_menu` VALUES (165, 160, '执行任务', '/api/quartz/runJob', 'api:quartz:runJob', 2, NULL, 5, NULL, NULL, NULL, NULL);
INSERT INTO `sys_menu` VALUES (166, 160, '新增任务', '/api/quartz/addJob', 'api:quartz:addJob', 2, NULL, 6, NULL, NULL, NULL, NULL);
INSERT INTO `sys_menu` VALUES (167, 160, '修改执行时间', '/api/quartz/editJobTime', 'api:quartz:editJobTime', 2, NULL, 7, NULL, NULL, NULL, NULL);
INSERT INTO `sys_menu` VALUES (168, 157, 'Nacos监控', '#', '#', 1, NULL, 1, NULL, NULL, NULL, NULL);
INSERT INTO `sys_menu` VALUES (169, 157, 'Sentinel控制台', '#', '#', 1, NULL, 2, NULL, NULL, NULL, NULL);
INSERT INTO `sys_menu` VALUES (170, 157, 'API文档', '#', '#', 1, NULL, 3, NULL, NULL, NULL, NULL);
COMMIT;

-- ----------------------------
-- Table structure for sys_menu_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu_role`;
CREATE TABLE `sys_menu_role` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `menu_id` int DEFAULT NULL,
  `role_id` int DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=473 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='菜单/角色中间表';

-- ----------------------------
-- Records of sys_menu_role
-- ----------------------------
BEGIN;
INSERT INTO `sys_menu_role` VALUES (74, 139, 3);
INSERT INTO `sys_menu_role` VALUES (438, 126, 1);
INSERT INTO `sys_menu_role` VALUES (439, 127, 1);
INSERT INTO `sys_menu_role` VALUES (440, 128, 1);
INSERT INTO `sys_menu_role` VALUES (441, 129, 1);
INSERT INTO `sys_menu_role` VALUES (442, 130, 1);
INSERT INTO `sys_menu_role` VALUES (443, 131, 1);
INSERT INTO `sys_menu_role` VALUES (444, 132, 1);
INSERT INTO `sys_menu_role` VALUES (445, 136, 1);
INSERT INTO `sys_menu_role` VALUES (446, 144, 1);
INSERT INTO `sys_menu_role` VALUES (447, 145, 1);
INSERT INTO `sys_menu_role` VALUES (448, 146, 1);
INSERT INTO `sys_menu_role` VALUES (449, 147, 1);
INSERT INTO `sys_menu_role` VALUES (450, 154, 1);
INSERT INTO `sys_menu_role` VALUES (451, 155, 1);
INSERT INTO `sys_menu_role` VALUES (452, 156, 1);
INSERT INTO `sys_menu_role` VALUES (453, 148, 1);
INSERT INTO `sys_menu_role` VALUES (454, 149, 1);
INSERT INTO `sys_menu_role` VALUES (455, 150, 1);
INSERT INTO `sys_menu_role` VALUES (456, 151, 1);
INSERT INTO `sys_menu_role` VALUES (457, 152, 1);
INSERT INTO `sys_menu_role` VALUES (458, 153, 1);
INSERT INTO `sys_menu_role` VALUES (459, 157, 1);
INSERT INTO `sys_menu_role` VALUES (460, 158, 1);
INSERT INTO `sys_menu_role` VALUES (461, 159, 1);
INSERT INTO `sys_menu_role` VALUES (462, 160, 1);
INSERT INTO `sys_menu_role` VALUES (463, 161, 1);
INSERT INTO `sys_menu_role` VALUES (464, 162, 1);
INSERT INTO `sys_menu_role` VALUES (465, 163, 1);
INSERT INTO `sys_menu_role` VALUES (466, 164, 1);
INSERT INTO `sys_menu_role` VALUES (467, 165, 1);
INSERT INTO `sys_menu_role` VALUES (468, 166, 1);
INSERT INTO `sys_menu_role` VALUES (469, 167, 1);
INSERT INTO `sys_menu_role` VALUES (470, 168, 1);
INSERT INTO `sys_menu_role` VALUES (471, 169, 1);
INSERT INTO `sys_menu_role` VALUES (472, 170, 1);
COMMIT;

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(90) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `code` varchar(10) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '角色代码',
  `status` int DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `create_by` varchar(60) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `update_by` varchar(60) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='系统角色表';

-- ----------------------------
-- Records of sys_role
-- ----------------------------
BEGIN;
INSERT INTO `sys_role` VALUES (1, '超级管理员', NULL, 1, '2019-08-01 16:35:40', 'admin', '2020-09-16 12:54:41', NULL);
COMMIT;

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `id` int NOT NULL AUTO_INCREMENT,
  `img` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '头像',
  `nickname` varchar(60) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `username` varchar(60) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `password` varchar(450) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `mobile` varchar(60) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `salt` varchar(90) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `secret` varchar(40) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '密钥',
  `status` int unsigned DEFAULT '1',
  `create_time` datetime DEFAULT NULL,
  `create_by` varchar(60) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `update_by` varchar(60) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=39 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='系统用户表';

-- ----------------------------
-- Records of sys_user
-- ----------------------------
BEGIN;
INSERT INTO `sys_user` VALUES (1, '../../images/headerimg.jpeg', '超级管理员', 'admin', 'de24e06e8fc310c7f4fe2266264495694052d1f120f0ed77cb09f6c597f15395', '13111111111', 'pQAbg6UQ99ia5MWwsfIm', 'f3cnospnjnif34ce3bc36nig2ka7z5jk', 1, '2020-09-16 12:55:10', 'admin', '2020-09-23 11:34:52', NULL);
COMMIT;

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `user_id` int DEFAULT NULL,
  `role_id` int DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='系统用户/角色中间表';

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------
BEGIN;
INSERT INTO `sys_user_role` VALUES (1, 1, 1);
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
