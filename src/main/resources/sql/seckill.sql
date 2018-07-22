/*
Navicat MySQL Data Transfer

Source Server         : asdad
Source Server Version : 50515
Source Host           : localhost:3306
Source Database       : seckill

Target Server Type    : MYSQL
Target Server Version : 50515
File Encoding         : 65001

Date: 2018-07-22 20:29:03
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
  `start_time` datetime NOT NULL COMMENT '秒杀开启时间',
  `end_time` datetime NOT NULL COMMENT '秒杀结束时间',
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
INSERT INTO `seckill` VALUES ('1000', '1000元秒杀iphone8', '0', '2018-05-10 15:31:53', '2018-05-10 15:31:53', '2018-05-10 15:31:53', '0');
INSERT INTO `seckill` VALUES ('1001', '500元秒杀ipad2', '100', '2018-05-10 15:31:53', '2018-05-10 15:31:53', '2018-05-10 15:31:53', '0');
INSERT INTO `seckill` VALUES ('1002', '300元秒杀小米4', '100', '2018-05-10 15:31:53', '2018-05-10 15:31:53', '2018-05-10 15:31:53', '0');
INSERT INTO `seckill` VALUES ('1003', '200元秒杀红米note', '100', '2018-05-10 15:31:53', '2018-05-10 15:31:53', '2018-05-10 15:31:53', '0');

-- ----------------------------
-- Table structure for success_killed
-- ----------------------------
DROP TABLE IF EXISTS `success_killed`;
CREATE TABLE `success_killed` (
  `seckill_id` bigint(20) NOT NULL COMMENT '秒杀商品id',
  `user_id` varchar(50) NOT NULL COMMENT '用户Id',
  `state` tinyint(4) NOT NULL COMMENT '状态标示：-1指无效，0指成功，1指已付款',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`seckill_id`,`user_id`),
  KEY `idx_create_time` (`create_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='秒杀成功明细表';

-- ----------------------------
-- Records of success_killed
-- ----------------------------
INSERT INTO `success_killed` VALUES ('1000', '01a7b4a0-14bd-4f93-9f15-93012ec62c0a', '0', '2018-07-22 20:12:53');
INSERT INTO `success_killed` VALUES ('1000', '03fa4fb1-eaec-4dbe-aef2-f657393f4fcc', '0', '2018-07-22 20:12:50');
INSERT INTO `success_killed` VALUES ('1000', '04f82a02-1404-475d-af7f-8410321e2421', '0', '2018-07-22 20:12:54');
INSERT INTO `success_killed` VALUES ('1000', '0597f3e5-9ae9-4846-a176-518695f86066', '0', '2018-07-22 20:12:52');
INSERT INTO `success_killed` VALUES ('1000', '06a3ca1c-1530-47c9-b1d0-0a63fdbee874', '0', '2018-07-22 20:12:53');
INSERT INTO `success_killed` VALUES ('1000', '0822b8b4-5ef2-4bfd-902b-79e350df1560', '0', '2018-07-22 20:12:52');
INSERT INTO `success_killed` VALUES ('1000', '0ad40366-80c1-494f-a906-71b925163706', '0', '2018-07-22 20:12:52');
INSERT INTO `success_killed` VALUES ('1000', '0af45450-ff50-4828-b891-6ca428514c1b', '0', '2018-07-22 20:12:51');
INSERT INTO `success_killed` VALUES ('1000', '0d32b1a2-89b1-4b4b-b51c-79157007525c', '0', '2018-07-22 20:12:52');
INSERT INTO `success_killed` VALUES ('1000', '109b509e-d722-4d16-807d-720a4d9e2bd7', '0', '2018-07-22 20:12:54');
INSERT INTO `success_killed` VALUES ('1000', '153984e9-a893-4377-af6a-957985983b78', '0', '2018-07-22 20:12:52');
INSERT INTO `success_killed` VALUES ('1000', '17292249-788a-4352-8181-b55126cd2bcc', '0', '2018-07-22 20:12:51');
INSERT INTO `success_killed` VALUES ('1000', '1aa45765-0995-429e-ae8a-c1149d075d58', '0', '2018-07-22 20:12:50');
INSERT INTO `success_killed` VALUES ('1000', '1ca4573a-de59-470d-818e-9b58fbc727c4', '0', '2018-07-22 20:12:53');
INSERT INTO `success_killed` VALUES ('1000', '1da252c8-f02e-4402-ab5c-25cc2483b39b', '0', '2018-07-22 20:12:53');
INSERT INTO `success_killed` VALUES ('1000', '20e67f3a-6e4f-4a04-9a04-ef8694d5158c', '0', '2018-07-22 20:12:48');
INSERT INTO `success_killed` VALUES ('1000', '211eb7ed-5481-41b5-acd8-53f2ff90495f', '0', '2018-07-22 20:12:50');
INSERT INTO `success_killed` VALUES ('1000', '236e2ea8-a5ba-4b18-879b-e41a002f8c88', '0', '2018-07-22 20:12:48');
INSERT INTO `success_killed` VALUES ('1000', '25682ae8-b092-4731-9df2-999b5a63583a', '0', '2018-07-22 20:12:52');
INSERT INTO `success_killed` VALUES ('1000', '27d7be9b-a6ed-4305-a78a-b4110aa07759', '0', '2018-07-22 20:12:51');
INSERT INTO `success_killed` VALUES ('1000', '2e1621af-5cfe-459c-955d-3a643dc9e169', '0', '2018-07-22 20:12:50');
INSERT INTO `success_killed` VALUES ('1000', '2f9e6ca6-64af-4e3f-bdaa-5c3696505a7a', '0', '2018-07-22 20:12:49');
INSERT INTO `success_killed` VALUES ('1000', '316db629-08a3-4a79-9efd-c85d056d717f', '0', '2018-07-22 20:12:53');
INSERT INTO `success_killed` VALUES ('1000', '31d35bc4-9676-4ed7-86e0-bf62e12386cf', '0', '2018-07-22 20:12:49');
INSERT INTO `success_killed` VALUES ('1000', '32fbf5c1-332c-4944-9031-9401b61f53ab', '0', '2018-07-22 20:12:50');
INSERT INTO `success_killed` VALUES ('1000', '35be180d-256d-41a7-be6f-eaa4d4207b5d', '0', '2018-07-22 20:12:49');
INSERT INTO `success_killed` VALUES ('1000', '37348191-328d-41fd-90c1-df67429d4447', '0', '2018-07-22 20:12:54');
INSERT INTO `success_killed` VALUES ('1000', '382491e0-c0d8-4a27-b45d-3fdea5ee6865', '0', '2018-07-22 20:12:49');
INSERT INTO `success_killed` VALUES ('1000', '384763bd-d11a-4c7b-81fa-0ebc83cbab70', '0', '2018-07-22 20:12:54');
INSERT INTO `success_killed` VALUES ('1000', '3ca7ce18-ae08-45d6-a6c5-52729262328c', '0', '2018-07-22 20:12:54');
INSERT INTO `success_killed` VALUES ('1000', '3cce41cf-2d9e-48de-aa05-93f1fc45080f', '0', '2018-07-22 20:12:49');
INSERT INTO `success_killed` VALUES ('1000', '4087caa9-8c57-4539-b8e3-732f06f050db', '0', '2018-07-22 20:12:53');
INSERT INTO `success_killed` VALUES ('1000', '45ac31e4-19d5-42c3-bddb-c1b272c7b762', '0', '2018-07-22 20:12:49');
INSERT INTO `success_killed` VALUES ('1000', '45ae7e6a-fbbd-4cf4-aaff-8f09ba7d6231', '0', '2018-07-22 20:12:50');
INSERT INTO `success_killed` VALUES ('1000', '53a45423-d655-48dc-acdf-2b92cb3aa57f', '0', '2018-07-22 20:12:48');
INSERT INTO `success_killed` VALUES ('1000', '54335c67-a57c-4e27-a6c2-00f0c00f8ee0', '0', '2018-07-22 20:12:53');
INSERT INTO `success_killed` VALUES ('1000', '59252a9b-530a-4150-99d1-1393c9d40fd7', '0', '2018-07-22 20:12:54');
INSERT INTO `success_killed` VALUES ('1000', '5d16aeb4-785c-4272-b1cd-d57b11cfaf91', '0', '2018-07-22 20:12:48');
INSERT INTO `success_killed` VALUES ('1000', '5dc95185-6515-4bda-a40c-ac2ef90941d1', '0', '2018-07-22 20:12:52');
INSERT INTO `success_killed` VALUES ('1000', '609b21e3-d33d-483d-a704-d54f361094ac', '0', '2018-07-22 20:12:54');
INSERT INTO `success_killed` VALUES ('1000', '652a9beb-1f73-4b03-bb09-43f3d811d889', '0', '2018-07-22 20:12:51');
INSERT INTO `success_killed` VALUES ('1000', '67a7497d-fef0-4338-9f5e-53b280d0922e', '0', '2018-07-22 20:12:50');
INSERT INTO `success_killed` VALUES ('1000', '6a849196-75dd-4afe-a03b-5a4c63010aa5', '0', '2018-07-22 20:12:54');
INSERT INTO `success_killed` VALUES ('1000', '6a9444a4-4bd6-4c23-9e32-c587b39b5ffa', '0', '2018-07-22 20:12:53');
INSERT INTO `success_killed` VALUES ('1000', '6aaaad79-f207-40a0-8697-3dcd1c6a80e0', '0', '2018-07-22 20:12:53');
INSERT INTO `success_killed` VALUES ('1000', '6c2a9d69-544d-40c7-bbc9-602260664e36', '0', '2018-07-22 20:12:53');
INSERT INTO `success_killed` VALUES ('1000', '6f4d5abe-ab71-42fa-895b-3209f9034e53', '0', '2018-07-22 20:12:51');
INSERT INTO `success_killed` VALUES ('1000', '70cb3615-d43e-4937-b1b3-a59ee2a426d3', '0', '2018-07-22 20:12:52');
INSERT INTO `success_killed` VALUES ('1000', '71ded48f-0ec8-47f5-beed-e55e6d903f28', '0', '2018-07-22 20:12:49');
INSERT INTO `success_killed` VALUES ('1000', '7268399d-bc87-4421-a6de-86210bbc7997', '0', '2018-07-22 20:12:50');
INSERT INTO `success_killed` VALUES ('1000', '75fbc2c2-1f37-4f56-a7be-526bf8170d47', '0', '2018-07-22 20:12:53');
INSERT INTO `success_killed` VALUES ('1000', '7799df95-ac97-4d2e-9a7f-cfb41ac7ae1b', '0', '2018-07-22 20:12:52');
INSERT INTO `success_killed` VALUES ('1000', '7b7bba15-7773-4467-9b76-9c899426de33', '0', '2018-07-22 20:12:50');
INSERT INTO `success_killed` VALUES ('1000', '80d469f3-8333-4616-adce-5cea9eb083c1', '0', '2018-07-22 20:12:52');
INSERT INTO `success_killed` VALUES ('1000', '86db6ce0-f713-433e-a115-fc8cfcb573f0', '0', '2018-07-22 20:12:51');
INSERT INTO `success_killed` VALUES ('1000', '8e01cccc-cd39-4f30-8998-cd151190b9a8', '0', '2018-07-22 20:12:49');
INSERT INTO `success_killed` VALUES ('1000', '907d65ac-271d-4698-b3a8-0f673929a501', '0', '2018-07-22 20:12:54');
INSERT INTO `success_killed` VALUES ('1000', '92ca72e8-1cce-4544-9e22-911a80331aab', '0', '2018-07-22 20:12:48');
INSERT INTO `success_killed` VALUES ('1000', '9a73761b-04b1-40bf-86c1-36bee2d350b8', '0', '2018-07-22 20:12:51');
INSERT INTO `success_killed` VALUES ('1000', '9b62dafe-5a41-4132-9ccb-75bf509a505f', '0', '2018-07-22 20:12:53');
INSERT INTO `success_killed` VALUES ('1000', 'a3f5e959-4a98-40b4-852f-c8724b709f58', '0', '2018-07-22 20:12:54');
INSERT INTO `success_killed` VALUES ('1000', 'a512160e-d275-4e52-a4c4-d3a19504f2af', '0', '2018-07-22 20:12:54');
INSERT INTO `success_killed` VALUES ('1000', 'a6681f45-e308-499f-a8b1-4055121c2f82', '0', '2018-07-22 20:12:53');
INSERT INTO `success_killed` VALUES ('1000', 'a8669a20-509d-40d5-b5c6-f8a336ab50b4', '0', '2018-07-22 20:12:53');
INSERT INTO `success_killed` VALUES ('1000', 'aaeadad5-2bb5-4b3b-b40a-466dbaf8ae09', '0', '2018-07-22 20:12:52');
INSERT INTO `success_killed` VALUES ('1000', 'b2b07c6d-0b06-4b06-b8d5-8d34ee764e7f', '0', '2018-07-22 20:12:55');
INSERT INTO `success_killed` VALUES ('1000', 'b67f2964-af35-437f-8e5b-d2141fdc6482', '0', '2018-07-22 20:12:51');
INSERT INTO `success_killed` VALUES ('1000', 'b930a8d4-e519-4375-8b15-501f7639ea16', '0', '2018-07-22 20:12:51');
INSERT INTO `success_killed` VALUES ('1000', 'bb008865-1e25-4464-a1d9-22518ea9ea8a', '0', '2018-07-22 20:12:50');
INSERT INTO `success_killed` VALUES ('1000', 'bcbca90d-ab61-4e6a-92e3-cc4c974b0f50', '0', '2018-07-22 20:12:50');
INSERT INTO `success_killed` VALUES ('1000', 'bcfe66bc-8293-45b3-8a03-42de82d06fa5', '0', '2018-07-22 20:12:53');
INSERT INTO `success_killed` VALUES ('1000', 'bd656046-cb62-4772-8949-f972fb5dc304', '0', '2018-07-22 20:12:54');
INSERT INTO `success_killed` VALUES ('1000', 'be1a2df4-38cb-4e08-9ca9-f86f7d6aa5f6', '0', '2018-07-22 20:12:49');
INSERT INTO `success_killed` VALUES ('1000', 'c17c196f-db53-4ea4-b6ce-a1056f952312', '0', '2018-07-22 20:12:54');
INSERT INTO `success_killed` VALUES ('1000', 'c25dac99-848a-44df-b7b3-20a48f66aa69', '0', '2018-07-22 20:12:51');
INSERT INTO `success_killed` VALUES ('1000', 'c3c1c624-26b8-4ba6-a063-3dcc2dfef13c', '0', '2018-07-22 20:12:49');
INSERT INTO `success_killed` VALUES ('1000', 'c4510e44-3401-4788-b661-c6619eadc818', '0', '2018-07-22 20:12:55');
INSERT INTO `success_killed` VALUES ('1000', 'c77940d2-52e2-467e-b969-e7f2033d0d50', '0', '2018-07-22 20:12:54');
INSERT INTO `success_killed` VALUES ('1000', 'cbc0d60b-f933-460a-b058-5dd2116d480b', '0', '2018-07-22 20:12:53');
INSERT INTO `success_killed` VALUES ('1000', 'cbd3f0d3-f228-4c7c-9af6-82f45321ecda', '0', '2018-07-22 20:12:48');
INSERT INTO `success_killed` VALUES ('1000', 'd05272d8-4382-4e77-b22d-049c38197af1', '0', '2018-07-22 20:12:52');
INSERT INTO `success_killed` VALUES ('1000', 'd1ba11f9-88bb-4866-be9f-bd165a113ab0', '0', '2018-07-22 20:12:54');
INSERT INTO `success_killed` VALUES ('1000', 'd7f1fca6-cf06-48d8-8b9f-a40ffc9ff98a', '0', '2018-07-22 20:12:51');
INSERT INTO `success_killed` VALUES ('1000', 'de3c74e5-ee35-430f-a40f-be2a0b450ee0', '0', '2018-07-22 20:12:54');
INSERT INTO `success_killed` VALUES ('1000', 'df0dd559-9206-4a61-946f-88e891ffbec9', '0', '2018-07-22 20:12:50');
INSERT INTO `success_killed` VALUES ('1000', 'dfac8102-f0a3-4a91-85e3-75b3a914d0aa', '0', '2018-07-22 20:12:49');
INSERT INTO `success_killed` VALUES ('1000', 'e3187a17-dc7f-42d6-beb3-618598c9c7ea', '0', '2018-07-22 20:12:51');
INSERT INTO `success_killed` VALUES ('1000', 'e356f56f-2579-44c0-86d9-d93ad252c5e3', '0', '2018-07-22 20:12:51');
INSERT INTO `success_killed` VALUES ('1000', 'e55b8850-9faf-4818-8956-8e3340fdc6db', '0', '2018-07-22 20:12:52');
INSERT INTO `success_killed` VALUES ('1000', 'e9402459-ec2d-47e2-9a54-a499140fb1b0', '0', '2018-07-22 20:12:49');
INSERT INTO `success_killed` VALUES ('1000', 'ecb42ce0-c73e-4fa8-ac57-faa7b7378d1f', '0', '2018-07-22 20:12:51');
INSERT INTO `success_killed` VALUES ('1000', 'ed38011d-c531-4900-9d54-73d7c9c21308', '0', '2018-07-22 20:12:50');
INSERT INTO `success_killed` VALUES ('1000', 'f2b4093c-8c9e-4993-b110-219c577152b4', '0', '2018-07-22 20:12:49');
INSERT INTO `success_killed` VALUES ('1000', 'f3c295f2-5195-4e0f-90c1-5558c0018531', '0', '2018-07-22 20:12:53');
INSERT INTO `success_killed` VALUES ('1000', 'f483a48e-7472-4332-9aea-b0ba7f1af80e', '0', '2018-07-22 20:12:50');
INSERT INTO `success_killed` VALUES ('1000', 'f4d69d8b-93c8-4e81-a373-f22c676d46f9', '0', '2018-07-22 20:12:49');
INSERT INTO `success_killed` VALUES ('1000', 'f6e4af21-2ab7-484c-b2be-7af14975491d', '0', '2018-07-22 20:12:52');
INSERT INTO `success_killed` VALUES ('1000', 'fb4d4ed1-c1a9-4f8c-bcc4-d443f6a48472', '0', '2018-07-22 20:12:51');
INSERT INTO `success_killed` VALUES ('1000', 'fc512efd-3b72-4e58-9698-14ef77808c91', '0', '2018-07-22 20:12:54');
INSERT INTO `success_killed` VALUES ('1000', 'febfe740-9449-4bcb-b6ed-7c2d82b51f77', '0', '2018-07-22 20:12:52');
