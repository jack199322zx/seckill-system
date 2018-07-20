/*
Navicat MySQL Data Transfer

Source Server         : test
Source Server Version : 50717
Source Host           : localhost:3306
Source Database       : seckill

Target Server Type    : MYSQL
Target Server Version : 50717
File Encoding         : 65001

Date: 2018-07-20 17:15:45
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for seckill
-- ----------------------------
DROP TABLE IF EXISTS `seckill`;
CREATE TABLE `seckill` (
  `seckill_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '商品库存id',
  `name` varchar(120) NOT NULL COMMENT '商品名称',
  `number` int(11) NOT NULL COMMENT '库存数量',
  `start_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '秒杀开启时间',
  `end_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '秒杀结束时间',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `version` int(11) NOT NULL COMMENT '版本号',
  PRIMARY KEY (`seckill_id`),
  KEY `idx_start_time` (`start_time`),
  KEY `idx_end_time` (`end_time`),
  KEY `idx_create_time` (`create_time`)
) ENGINE=InnoDB AUTO_INCREMENT=1004 DEFAULT CHARSET=utf8 COMMENT='秒杀库存表';

-- ----------------------------
-- Records of seckill
-- ----------------------------
INSERT INTO `seckill` VALUES ('1000', '1000元秒杀iphone8', '88', '2018-05-10 15:31:53', '2018-05-10 15:31:53', '2018-05-10 15:31:53', '0');
INSERT INTO `seckill` VALUES ('1001', '500元秒杀ipad2', '100', '2018-05-10 15:31:53', '2018-05-10 15:31:53', '2018-05-10 15:31:53', '0');
INSERT INTO `seckill` VALUES ('1002', '300元秒杀小米4', '100', '2018-05-10 15:31:53', '2018-05-10 15:31:53', '2018-05-10 15:31:53', '0');
INSERT INTO `seckill` VALUES ('1003', '200元秒杀红米note', '100', '2018-05-10 15:31:53', '2018-05-10 15:31:53', '2018-05-10 15:31:53', '0');

-- ----------------------------
-- Table structure for success_killed
-- ----------------------------
DROP TABLE IF EXISTS `success_killed`;
CREATE TABLE `success_killed` (
  `seckill_id` bigint(20) NOT NULL COMMENT '秒杀商品id',
  `user_id` bigint(20) NOT NULL COMMENT '用户Id',
  `state` tinyint(4) NOT NULL COMMENT '状态标示：-1指无效，0指成功，1指已付款',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`seckill_id`,`user_id`),
  KEY `idx_create_time` (`create_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='秒杀成功明细表';

-- ----------------------------
-- Records of success_killed
-- ----------------------------
INSERT INTO `success_killed` VALUES ('1000', '0', '0', '2018-07-06 15:58:16');
INSERT INTO `success_killed` VALUES ('1000', '1', '0', '2018-07-06 15:58:16');
INSERT INTO `success_killed` VALUES ('1000', '2', '0', '2018-07-06 15:58:16');
INSERT INTO `success_killed` VALUES ('1000', '3', '0', '2018-07-06 15:58:16');
INSERT INTO `success_killed` VALUES ('1000', '4', '0', '2018-07-06 15:58:16');
INSERT INTO `success_killed` VALUES ('1000', '5', '0', '2018-07-06 15:58:17');
INSERT INTO `success_killed` VALUES ('1000', '6', '0', '2018-07-06 15:58:17');
INSERT INTO `success_killed` VALUES ('1000', '41', '0', '2018-07-06 15:58:17');
INSERT INTO `success_killed` VALUES ('1000', '280', '0', '2018-07-06 15:58:17');
INSERT INTO `success_killed` VALUES ('1000', '504', '0', '2018-07-06 15:58:17');
INSERT INTO `success_killed` VALUES ('1000', '767', '0', '2018-07-06 15:58:17');
INSERT INTO `success_killed` VALUES ('1000', '965', '0', '2018-07-06 15:58:17');
