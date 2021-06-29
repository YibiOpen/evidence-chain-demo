/*----用户----*/
INSERT INTO `evi_user`(`id`, `uname`, `pwd`, `sign_user_id`, `public_key`, `private_key`, `create_time`, `update_time`) VALUES (1, 'eviAdmin', 'e0bebd22819993425814866b62701e2919ea26f1370499c1037b53b9d49c2c8a', '', '', '', '2021-06-24 11:34:54', '2021-06-29 10:21:49');
/*----产品----*/
INSERT INTO `evi_product`(`id`, `product_name`, `product_code`, `open_status`, `create_time`, `update_time`) VALUES (1, '亦笔产品', 'I-001', 1, '2021-06-29 10:47:28', '2021-06-29 10:47:28');
/*----节点----*/
INSERT INTO `evi_step`(`id`, `product_id`, `step_name`, `step_code`, `open_status`, `create_time`, `update_time`) VALUES (1, 1, '注册', 'X-001', 1, '2021-06-29 10:50:01', '2021-06-29 10:50:01');
INSERT INTO `evi_step`(`id`, `product_id`, `step_name`, `step_code`, `open_status`, `create_time`, `update_time`) VALUES (2, 1, '授信', 'X-002', 1, '2021-06-29 10:50:13', '2021-06-29 10:50:53');
INSERT INTO `evi_step`(`id`, `product_id`, `step_name`, `step_code`, `open_status`, `create_time`, `update_time`) VALUES (3, 1, '放款', 'X-003', 1, '2021-06-29 10:50:15', '2021-06-29 10:50:53');
INSERT INTO `evi_step`(`id`, `product_id`, `step_name`, `step_code`, `open_status`, `create_time`, `update_time`) VALUES (4, 1, '还款', 'X-004', 1, '2021-06-29 10:50:18', '2021-06-29 10:50:55');
/*----要素----*/
INSERT INTO `evi_field`(`id`, `product_id`, `step_id`, `en_name`, `ch_name`, `create_time`, `update_time`) VALUES (1, 1, 1, 'userName', '用户姓名', '2021-06-29 10:51:07', '2021-06-29 10:51:41');
INSERT INTO `evi_field`(`id`, `product_id`, `step_id`, `en_name`, `ch_name`, `create_time`, `update_time`) VALUES (2, 1, 1, 'idNo', '身份证号码', '2021-06-29 10:51:43', '2021-06-29 10:53:38');
INSERT INTO `evi_field`(`id`, `product_id`, `step_id`, `en_name`, `ch_name`, `create_time`, `update_time`) VALUES (3, 1, 1, 'mobileNo', '手机号', '2021-06-29 10:51:43', '2021-06-29 10:53:41');
INSERT INTO `evi_field`(`id`, `product_id`, `step_id`, `en_name`, `ch_name`, `create_time`, `update_time`) VALUES (4, 1, 1, 'email', '电子邮箱', '2021-06-29 10:51:44', '2021-06-29 10:53:44');
INSERT INTO `evi_field`(`id`, `product_id`, `step_id`, `en_name`, `ch_name`, `create_time`, `update_time`) VALUES (5, 1, 1, 'gender', '性别', '2021-06-29 10:51:44', '2021-06-29 10:53:46');
INSERT INTO `evi_field`(`id`, `product_id`, `step_id`, `en_name`, `ch_name`, `create_time`, `update_time`) VALUES (6, 1, 1, 'nation', '民族', '2021-06-29 10:52:22', '2021-06-29 10:53:49');
INSERT INTO `evi_field`(`id`, `product_id`, `step_id`, `en_name`, `ch_name`, `create_time`, `update_time`) VALUES (7, 1, 1, 'openTime', '开户时间', '2021-06-29 10:52:30', '2021-06-29 10:53:52');
INSERT INTO `evi_field`(`id`, `product_id`, `step_id`, `en_name`, `ch_name`, `create_time`, `update_time`) VALUES (8, 1, 1, 'authTime', '实名认证时间', '2021-06-29 10:52:30', '2021-06-29 10:53:54');
INSERT INTO `evi_field`(`id`, `product_id`, `step_id`, `en_name`, `ch_name`, `create_time`, `update_time`) VALUES (9, 1, 1, 'fingerprint', '设备指纹', '2021-06-29 10:52:31', '2021-06-29 10:53:56');
INSERT INTO `evi_field`(`id`, `product_id`, `step_id`, `en_name`, `ch_name`, `create_time`, `update_time`) VALUES (10, 1, 2, 'userName', '用户姓名', '2021-06-29 10:55:58', '2021-06-29 10:57:45');
INSERT INTO `evi_field`(`id`, `product_id`, `step_id`, `en_name`, `ch_name`, `create_time`, `update_time`) VALUES (11, 1, 2, 'idNo', '身份证号码', '2021-06-29 10:55:58', '2021-06-29 10:57:47');
INSERT INTO `evi_field`(`id`, `product_id`, `step_id`, `en_name`, `ch_name`, `create_time`, `update_time`) VALUES (12, 1, 2, 'mobileNo', '手机号', '2021-06-29 10:55:59', '2021-06-29 10:57:50');
INSERT INTO `evi_field`(`id`, `product_id`, `step_id`, `en_name`, `ch_name`, `create_time`, `update_time`) VALUES (13, 1, 2, 'applAcNo', '银行卡号', '2021-06-29 10:55:59', '2021-06-29 10:58:32');
INSERT INTO `evi_field`(`id`, `product_id`, `step_id`, `en_name`, `ch_name`, `create_time`, `update_time`) VALUES (14, 1, 2, 'applAcNam', '银行户名', '2021-06-29 10:56:00', '2021-06-29 10:58:44');
INSERT INTO `evi_field`(`id`, `product_id`, `step_id`, `en_name`, `ch_name`, `create_time`, `update_time`) VALUES (15, 1, 2, 'apprvAmt', '授信额度', '2021-06-29 10:56:00', '2021-06-29 10:58:51');
INSERT INTO `evi_field`(`id`, `product_id`, `step_id`, `en_name`, `ch_name`, `create_time`, `update_time`) VALUES (16, 1, 2, 'amountNo', '额度合同编号', '2021-06-29 10:56:00', '2021-06-29 10:58:58');
INSERT INTO `evi_field`(`id`, `product_id`, `step_id`, `en_name`, `ch_name`, `create_time`, `update_time`) VALUES (17, 1, 2, 'signDt', '合同签订时间', '2021-06-29 10:57:09', '2021-06-29 10:59:05');
INSERT INTO `evi_field`(`id`, `product_id`, `step_id`, `en_name`, `ch_name`, `create_time`, `update_time`) VALUES (18, 1, 2, 'fingerprint', '设备指纹', '2021-06-29 10:56:39', '2021-06-29 10:59:01');