/*
 Navicat Premium Data Transfer
 Date: 23/04/2024 12:32:43
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for goods
-- ----------------------------
DROP TABLE IF EXISTS `goods`;
CREATE TABLE `goods`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '物品名',
  `price` decimal(10, 2) NULL DEFAULT NULL COMMENT '价格',
  `category` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '分类',
  `storage_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '仓库ID',
  `quantity` int(11) NULL DEFAULT NULL COMMENT '数量',
  `notes` varchar(100) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '备注',
  `unit_volume` decimal(10, 2) NULL DEFAULT NULL COMMENT '单位容量',
  `total_volume` decimal(10, 2) GENERATED ALWAYS AS ((`quantity` * `unit_volume`)) STORED COMMENT '总容量' NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 45 CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of goods
-- ----------------------------
INSERT INTO `goods` VALUES (28, '羽绒服', 499.90, '服装类', '3', 1200, '', 368.30, DEFAULT);
INSERT INTO `goods` VALUES (29, '麒麟西瓜', 8.88, '水果类', '1', 980, '每斤西瓜8.88', 13.70, DEFAULT);
INSERT INTO `goods` VALUES (30, '麒麟西瓜', 8.88, '水果类', '2', 1500, '2号商店仓库的西瓜', 13.70, DEFAULT);
INSERT INTO `goods` VALUES (31, '维生素C片', 5.00, '医药类', '4', 400, 'OTC医药维生素', 30.00, DEFAULT);
INSERT INTO `goods` VALUES (32, '维生素B片', 10.40, '医药类', '4', 250, 'OTC医药类', 30.00, DEFAULT);
INSERT INTO `goods` VALUES (33, 'MI NoteBook', 3999.90, '电子类', '1', 10, '笔记本电脑，注意防潮存储', 536.80, DEFAULT);
INSERT INTO `goods` VALUES (34, '大白菜', 3.10, '蔬菜类', '4', 1000, '白菜价', 2.00, DEFAULT);
INSERT INTO `goods` VALUES (35, '猪肉', 29.90, '肉食类', '4', 5000, '数量的计量单位为斤', 28.60, DEFAULT);
INSERT INTO `goods` VALUES (36, 'RedMi手机', 799.00, '电子类', '1', 100, '机皇', 131.40, DEFAULT);
INSERT INTO `goods` VALUES (38, '容量占用砖头', 0.00, 'virtual-capicity', '5', 14, '', 100000.00, DEFAULT);
INSERT INTO `goods` VALUES (39, '荔枝', 18.86, '水果类', '3', 1000, '', 80.00, DEFAULT);

-- ----------------------------
-- Table structure for log
-- ----------------------------
DROP TABLE IF EXISTS `log`;
CREATE TABLE `log`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `storage` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '归属仓库',
  `goods` int(11) NOT NULL COMMENT '货品id',
  `userId` int(11) NULL DEFAULT NULL COMMENT '操作员',
  `adminId` int(11) NULL DEFAULT 2 COMMENT '审批员',
  `count` int(11) NULL DEFAULT NULL COMMENT '数量',
  `createtime` datetime NULL DEFAULT NULL COMMENT '操作时间',
  `remark` varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  `type` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '操作类型',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 160 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of log
-- ----------------------------
INSERT INTO `log` VALUES (129, '5', 38, 1, 2, 6, '2023-03-01 15:57:54', '入库测试仓库容量', '1');
INSERT INTO `log` VALUES (130, '4', 32, 14, 2, 200, '2024-03-15 16:00:13', '员工入库测试', '1');
INSERT INTO `log` VALUES (131, '2', 30, 14, 2, 300, '2024-04-17 16:00:26', '员工出库测试', '2');
INSERT INTO `log` VALUES (132, '3', 28, 1, 2, 9999, '2024-04-18 13:46:22', 'test', '1');
INSERT INTO `log` VALUES (133, '3', 28, 1, 2, 9999, '2024-04-18 13:46:48', '3', '1');
INSERT INTO `log` VALUES (134, '3', 28, 1, 2, 20000, '2024-04-18 14:02:54', '正常出库', '2');
INSERT INTO `log` VALUES (135, '3', 28, 1, 2, 300, '2024-04-18 14:03:48', '入库测试', '1');
INSERT INTO `log` VALUES (136, '3', 28, 1, 2, 333, '2024-04-18 21:15:32', '在线部署测试', '1');
INSERT INTO `log` VALUES (137, '3', 28, 1, 2, 20, '2024-04-19 21:38:07', 'update', '1');
INSERT INTO `log` VALUES (138, '1', 29, 1, 2, 20, '2024-04-19 21:38:17', 'update-admin', '2');
INSERT INTO `log` VALUES (139, '2', 30, 1, 2, 200, '2024-04-19 21:38:31', 'update-admin', '2');
INSERT INTO `log` VALUES (140, '4', 31, 14, 2, 300, '2024-04-19 21:39:02', 'update-user', '1');
INSERT INTO `log` VALUES (146, '3', 28, 1, 2, 66, '2024-04-22 19:21:23', '都', '1');
INSERT INTO `log` VALUES (147, '3', 28, 1, 2, 99, '2024-04-22 20:30:48', '9', '1');
INSERT INTO `log` VALUES (148, '3', 28, 1, 2, 99, '2024-04-22 20:31:14', 'df', '2');
INSERT INTO `log` VALUES (149, '3', 28, 1, 2, 99, '2024-04-22 21:54:42', '3', '1');
INSERT INTO `log` VALUES (150, '3', 28, 1, 2, 99, '2024-04-22 21:55:02', 'r', '2');
INSERT INTO `log` VALUES (151, '3', 28, 1, 2, 66, '2024-04-22 22:19:14', 't', '1');
INSERT INTO `log` VALUES (152, '3', 28, 1, 2, 32, '2024-04-22 22:19:38', 'd', '2');
INSERT INTO `log` VALUES (153, '3', 28, 1, 2, 33, '2024-04-22 23:06:23', '5', '1');
INSERT INTO `log` VALUES (154, '3', 28, 1, 2, 44444, '2024-04-22 23:06:48', 'r', '1');
INSERT INTO `log` VALUES (155, '3', 28, 1, 2, 44466, '2024-04-22 23:09:08', 'f', '2');
INSERT INTO `log` VALUES (156, '3', 28, 1, 2, 1, '2024-04-22 23:09:20', 'd', '2');
INSERT INTO `log` VALUES (157, '3', 28, 1, 2, 10, '2024-04-22 23:15:27', 's', '1');
INSERT INTO `log` VALUES (158, '3', 28, 1, 2, 100, '2024-04-22 23:15:57', '2', '1');
INSERT INTO `log` VALUES (159, '3', 28, 1, 2, 20, '2024-04-22 23:16:22', 't', '2');

-- ----------------------------
-- Table structure for menu
-- ----------------------------
DROP TABLE IF EXISTS `menu`;
CREATE TABLE `menu`  (
  `id` int(11) NOT NULL,
  `menuCode` varchar(8) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '菜单编码',
  `menuName` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '菜单名',
  `menuClick` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '触发函数',
  `menuRight` varchar(8) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '显示权限',
  `menuComponent` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '跳转页面',
  `menuIcon` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '菜单图标',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of menu
-- ----------------------------
INSERT INTO `menu` VALUES (1, '001', '员工管理', 'User', '0,1', 'user/UserManage.vue', 'el-icon-position');
INSERT INTO `menu` VALUES (2, '002', '货物管理', 'GoodsControl', '0,1,2', 'goods/GoodsControl.vue', 'el-icon-shopping-bag-2');
INSERT INTO `menu` VALUES (3, '003', '商店仓库', 'Storage', '0,1', 'storage/StorageManage.vue', 'el-icon-s-grid');
INSERT INTO `menu` VALUES (4, '004', '货物信息', 'Goods', '0,1', 'goods/GoodsManage.vue', 'el-icon-menu');
INSERT INTO `menu` VALUES (5, '005', '日志', 'Log', '0,1,2', 'log/LogManage.vue', 'el-icon-tickets');
INSERT INTO `menu` VALUES (6, '006', '容量计算器', 'Calculate', '0,1,2', 'calculate/Calculate.vue', 'el-icon-share');

-- ----------------------------
-- Table structure for storage
-- ----------------------------
DROP TABLE IF EXISTS `storage`;
CREATE TABLE `storage`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '仓库名',
  `remark` varchar(100) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '备注',
  `initial_capacity` int(11) NULL DEFAULT NULL COMMENT '初始容量',
  `calculated_capacity` int(11) NULL DEFAULT NULL COMMENT '统计容量',
  `remaining_capacity` int(11) NULL DEFAULT NULL COMMENT '剩余容量',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of storage
-- ----------------------------
INSERT INTO `storage` VALUES (1, '武汉中心商店', '最大容量仓库', 3000000, 31934, 2968066);
INSERT INTO `storage` VALUES (2, '西湖商店', '县级部署', 1500000, 20550, 1479450);
INSERT INTO `storage` VALUES (3, '东湖商店', '东北供货', 1500000, 521960, 978040);
INSERT INTO `storage` VALUES (4, '加盟商店(广州)', '吞吐量最大仓库', 2700000, 164500, 2535500);
INSERT INTO `storage` VALUES (5, 'BoxShop', '直营仓库', 2000000, 1400000, 600000);

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `no` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '账号',
  `name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '名字',
  `password` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '密码',
  `age` int(11) NULL DEFAULT NULL,
  `sex` int(11) NULL DEFAULT NULL COMMENT '性别',
  `phone` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '电话',
  `role_id` int(11) NULL DEFAULT NULL COMMENT '⻆⾊ 0超级管理员，1管理员，2普通账号',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 21 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (1, 'admin', '超级管理员', 'abc333', NULL, 1, 'Admin@Email', 0);
INSERT INTO `user` VALUES (2, 'moss', 'MOSS', '666', NULL, 0, 'MOSS@Emai.com', 1);
INSERT INTO `user` VALUES (3, '3116', '小红', '123', 18, 0, 'red@outlook.com', 2);
INSERT INTO `user` VALUES (5, '3115', 'Bomm', '123', 18, 0, 'BOMM@qq.com', 2);
INSERT INTO `user` VALUES (6, '3116', '于敏', '224135', 21, 0, '1335678243', 2);
INSERT INTO `user` VALUES (7, '3117', '开心超人', '123', 13, 1, 'happyman@126.com', 2);
INSERT INTO `user` VALUES (8, '3118', '甜心超人', '123', 11, 0, NULL, 2);
INSERT INTO `user` VALUES (9, '3119', '大大怪', '123', 23, 1, 'Guaiguai1314', 2);
INSERT INTO `user` VALUES (10, '31110', '小小怪', '1234', 13, 1, 'guai@qq.com', 2);
INSERT INTO `user` VALUES (12, '31112', '小心超人', '123', 10, 1, 'xiaoxin@Email.com', 2);
INSERT INTO `user` VALUES (13, '3112', '小红', '123', 19, 1, 'red', 2);
INSERT INTO `user` VALUES (14, '3113', 'Alice', '123', 33, 0, 'ttt', 2);
INSERT INTO `user` VALUES (18, '31142', '粗心超人', '123', 12, 1, '', 2);
INSERT INTO `user` VALUES (19, '31143', '宅博士', '123', 18, 1, NULL, 1);

-- ----------------------------
-- Triggers structure for table goods
-- ----------------------------
DROP TRIGGER IF EXISTS `after_goods_insert`;
delimiter ;;
CREATE TRIGGER `after_goods_insert` AFTER INSERT ON `goods` FOR EACH ROW BEGIN
  DECLARE new_calculated_capacity INT;

  -- 计算新的统计容量
  SELECT SUM(total_volume) INTO new_calculated_capacity
  FROM goods
  WHERE storage_id = NEW.storage_id;

  -- 更新storage表
  UPDATE storage
  SET calculated_capacity = new_calculated_capacity,
      remaining_capacity = GREATEST(initial_capacity - new_calculated_capacity, 0)
  WHERE id = NEW.storage_id;
END
;;
delimiter ;

-- ----------------------------
-- Triggers structure for table goods
-- ----------------------------
DROP TRIGGER IF EXISTS `after_goods_update`;
delimiter ;;
CREATE TRIGGER `after_goods_update` AFTER UPDATE ON `goods` FOR EACH ROW BEGIN
  DECLARE new_calculated_capacity INT;

  -- 如果仓库ID没有变化，只需要更新变化的容量
  IF OLD.storage_id = NEW.storage_id THEN
    SELECT SUM(total_volume) INTO new_calculated_capacity
    FROM goods
    WHERE storage_id = NEW.storage_id;

    -- 更新storage表
    UPDATE storage
    SET calculated_capacity = new_calculated_capacity,
        remaining_capacity = GREATEST(initial_capacity - new_calculated_capacity, 0)
    WHERE id = NEW.storage_id;
  ELSE
    -- 如果仓库ID变化了，需要更新两个仓库的容量
    -- 更新旧仓库的容量
    SELECT SUM(total_volume) INTO new_calculated_capacity
    FROM goods
    WHERE storage_id = OLD.storage_id;

    UPDATE storage
    SET calculated_capacity = new_calculated_capacity,
        remaining_capacity = GREATEST(initial_capacity - new_calculated_capacity, 0)
    WHERE id = OLD.storage_id;

    -- 更新新仓库的容量
    SELECT SUM(total_volume) INTO new_calculated_capacity
    FROM goods
    WHERE storage_id = NEW.storage_id;

    UPDATE storage
    SET calculated_capacity = new_calculated_capacity,
        remaining_capacity = GREATEST(initial_capacity - new_calculated_capacity, 0)
    WHERE id = NEW.storage_id;
  END IF;
END
;;
delimiter ;

-- ----------------------------
-- Triggers structure for table goods
-- ----------------------------
DROP TRIGGER IF EXISTS `after_goods_delete`;
delimiter ;;
CREATE TRIGGER `after_goods_delete` AFTER DELETE ON `goods` FOR EACH ROW BEGIN
  DECLARE new_calculated_capacity INT;

  -- 计算新的统计容量
  SELECT SUM(total_volume) INTO new_calculated_capacity
  FROM goods
  WHERE storage_id = OLD.storage_id;

  -- 更新storage表
  UPDATE storage
  SET calculated_capacity = new_calculated_capacity,
      remaining_capacity = GREATEST(initial_capacity - new_calculated_capacity, 0)
  WHERE id = OLD.storage_id;
END
;;
delimiter ;

SET FOREIGN_KEY_CHECKS = 1;
