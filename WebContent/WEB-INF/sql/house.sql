-- phpMyAdmin SQL Dump
-- version 4.3.11
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: 2016-01-26 16:14:44
-- 服务器版本： 5.6.24
-- PHP Version: 5.6.8 

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `house`
--

-- --------------------------------------------------------

--
-- 表的结构 `admin_user`
--

CREATE TABLE IF NOT EXISTS `admin_user` (
  `adminUserId` int(11) NOT NULL,
  `loginname` varchar(30) CHARACTER SET utf8 NOT NULL,
  `password` varchar(50) CHARACTER SET utf8 NOT NULL,
  `nickname` varchar(30) CHARACTER SET utf8 NOT NULL,
  `phone` varchar(11) CHARACTER SET utf8 DEFAULT NULL,
  `email` varchar(30) CHARACTER SET utf8 NOT NULL,
  `type` int(2) NOT NULL,
  `orinal` int(2) NOT NULL,
  `position` varchar(50) CHARACTER SET utf8 DEFAULT NULL,
  `status` int(1) NOT NULL,
  `description` varchar(200) CHARACTER SET utf8 DEFAULT NULL,
  `createDate` datetime NOT NULL,
  `createUserId` int(11) DEFAULT NULL,
  `updateDate` datetime DEFAULT NULL,
  `updateUserId` int(11) DEFAULT NULL
) ENGINE=InnoDB AUTO_INCREMENT=115 DEFAULT CHARSET=utf8mb4;

--
-- 转存表中的数据 `admin_user`
--

INSERT INTO `admin_user` (`adminUserId`, `loginname`, `password`, `nickname`, `phone`, `email`, `type`, `orinal`, `position`, `status`, `description`, `createDate`, `createUserId`, `updateDate`, `updateUserId`) VALUES
(113, 'jiawook', '6A75D505AE56DE79D5564C3F1666B59E', '测试专用', '13333333333', '123456@qq.com', 1, 0, '', 1, 'ccs', '2015-09-01 20:58:32', NULL, '2016-01-25 15:40:08', 113),
(114, 'getmore', '25F9E794323B453885F5181F1B624D0B', '凯特猫', '18888888888', '12345@getmore.com', 1, 0, NULL, 1, '', '2016-01-19 12:06:54', 113, '2016-01-25 15:40:03', 113);

-- --------------------------------------------------------

--
-- 表的结构 `balance_history`
--

CREATE TABLE IF NOT EXISTS `balance_history` (
  `historyId` int(11) NOT NULL,
  `storeId` int(11) NOT NULL,
  `type` int(11) NOT NULL,
  `value` int(11) NOT NULL,
  `orderId` int(11) DEFAULT NULL,
  `message` varchar(100) DEFAULT NULL,
  `createUserId` int(11) NOT NULL,
  `createDate` datetime NOT NULL
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8mb4;

--
-- 转存表中的数据 `balance_history`
--

INSERT INTO `balance_history` (`historyId`, `storeId`, `type`, `value`, `orderId`, `message`, `createUserId`, `createDate`) VALUES
(3, 5, 0, 100, NULL, '第一次充值', 113, '2016-01-26 22:55:53'),
(4, 5, 0, 200, NULL, '第二次充值', 113, '2016-01-26 22:56:03'),
(5, 5, 0, 100, NULL, '', 113, '2016-01-26 22:56:11'),
(6, 5, 0, 5, NULL, '', 113, '2016-01-26 22:56:14'),
(7, 5, 0, -10, NULL, '', 113, '2016-01-26 22:56:18'),
(8, 5, 0, 544, NULL, '', 113, '2016-01-26 22:56:24'),
(9, 5, 0, 45, NULL, '', 113, '2016-01-26 22:56:34'),
(10, 5, 0, 3123, NULL, '', 113, '2016-01-26 22:56:37'),
(11, 5, 0, 312, NULL, '12', 113, '2016-01-26 22:56:49'),
(12, 5, 0, 154, NULL, '', 113, '2016-01-26 22:56:53'),
(13, 5, 0, 45, NULL, '', 113, '2016-01-26 22:56:57'),
(14, 5, 0, 6546, NULL, '', 113, '2016-01-26 22:57:16'),
(15, 5, 0, 561, NULL, '', 113, '2016-01-26 22:57:27'),
(16, 5, 0, 10101, NULL, '老板充值1W快', 113, '2016-01-26 23:13:06');

-- --------------------------------------------------------

--
-- 表的结构 `design_apply`
--

CREATE TABLE IF NOT EXISTS `design_apply` (
  `applyId` int(11) NOT NULL,
  `phone` varchar(20) NOT NULL,
  `username` varchar(100) NOT NULL,
  `location` varchar(100) DEFAULT NULL,
  `size` varchar(200) DEFAULT NULL,
  `shortUrl` varchar(200) DEFAULT NULL,
  `createDate` datetime NOT NULL,
  `description` varchar(500) DEFAULT NULL,
  `status` int(2) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='量房申请';

-- --------------------------------------------------------

--
-- 表的结构 `house_type`
--

CREATE TABLE IF NOT EXISTS `house_type` (
  `typeId` int(11) NOT NULL,
  `name` varchar(50) NOT NULL,
  `price` int(11) NOT NULL,
  `createDate` datetime NOT NULL,
  `createUserId` int(11) NOT NULL,
  `updateDate` datetime DEFAULT NULL,
  `updateUserId` int(11) DEFAULT NULL,
  `status` int(2) NOT NULL
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4;

--
-- 转存表中的数据 `house_type`
--

INSERT INTO `house_type` (`typeId`, `name`, `price`, `createDate`, `createUserId`, `updateDate`, `updateUserId`, `status`) VALUES
(5, '一室户', 60, '2016-01-24 23:43:52', 113, '2016-01-24 23:47:36', 113, 0),
(6, '一室一厅', 120, '2016-01-24 23:43:59', 113, '2016-01-24 23:47:43', 113, 1),
(7, '两室一厅', 210, '2016-01-24 23:44:07', 113, '2016-01-24 23:47:56', 113, 1),
(8, '三室两厅', 500, '2016-01-24 23:44:14', 113, NULL, NULL, 1),
(9, '小别墅', 1000, '2016-01-24 23:44:22', 113, NULL, NULL, 1);

-- --------------------------------------------------------

--
-- 表的结构 `menu_manager`
--

CREATE TABLE IF NOT EXISTS `menu_manager` (
  `menuId` int(11) NOT NULL,
  `menuName` varchar(10) NOT NULL,
  `uri` varchar(50) NOT NULL,
  `parentId` int(11) NOT NULL,
  `type` int(11) NOT NULL,
  `status` int(2) NOT NULL,
  `createDate` datetime NOT NULL,
  `updateDate` datetime DEFAULT NULL,
  `createUserId` int(11) DEFAULT NULL,
  `updateUserId` int(11) DEFAULT NULL
) ENGINE=InnoDB AUTO_INCREMENT=107 DEFAULT CHARSET=utf8;

--
-- 转存表中的数据 `menu_manager`
--

INSERT INTO `menu_manager` (`menuId`, `menuName`, `uri`, `parentId`, `type`, `status`, `createDate`, `updateDate`, `createUserId`, `updateUserId`) VALUES
(7, '用户管理', '#', -1, 0, 1, '2015-08-30 00:00:00', NULL, NULL, NULL),
(8, '后台设置', '#', -1, 0, 1, '2015-08-30 00:00:00', NULL, NULL, NULL),
(9, '菜单管理', '../menu/index.html', 8, 1, 1, '2015-08-30 00:00:00', NULL, NULL, NULL),
(10, '角色管理', '../role/index.html', 8, 1, 1, '2015-08-30 00:00:00', NULL, NULL, NULL),
(11, '权限管理', '../power/index.html', 8, 1, 1, '2015-08-30 00:00:00', NULL, NULL, NULL),
(12, '用户统计', '../user/index.html', 7, 1, 1, '2015-08-30 22:58:05', NULL, NULL, NULL),
(77, '添加用户', '../user/add.html', 12, 2, 1, '2015-10-19 22:30:19', NULL, NULL, NULL),
(78, '更新用户', '../user/update.html', 12, 2, 1, '2015-10-19 22:31:27', NULL, NULL, NULL),
(79, '切换状态', '../user/status.html', 12, 2, 1, '2015-10-19 22:31:56', NULL, NULL, NULL),
(80, '重置密码', '../user/reset.html', 12, 2, 1, '2015-10-19 22:32:15', NULL, NULL, NULL),
(81, '删除用户', '../user/delete.html', 12, 2, 1, '2015-10-19 22:32:43', NULL, NULL, NULL),
(82, '菜单明细', '../menu/list.html', 9, 2, 1, '2015-10-20 00:09:20', NULL, NULL, NULL),
(83, '增加菜单', '../menu/add.html', 9, 2, 1, '2015-10-20 00:16:45', NULL, NULL, NULL),
(84, '更新菜单', '../menu/update.html', 9, 2, 1, '2015-10-20 00:17:05', NULL, NULL, NULL),
(85, '删除菜单', '../menu/delete.html', 9, 2, 1, '2015-10-20 00:17:48', NULL, NULL, NULL),
(87, '增加角色', '../role/add.html', 10, 2, 1, '2015-10-20 00:32:21', NULL, NULL, NULL),
(88, '修改角色', '../role/update.html', 10, 2, 1, '2015-10-20 00:32:47', NULL, NULL, NULL),
(89, '切换状态', '../role/status.html', 10, 2, 1, '2015-10-20 00:33:07', NULL, NULL, NULL),
(90, '查看权限', '../power/list.html', 11, 2, 1, '2015-10-20 00:41:22', NULL, NULL, NULL),
(91, '更新权限', '../power/saveOrUpdate.html', 11, 2, 1, '2015-10-20 00:41:48', NULL, NULL, NULL),
(92, '微信用户管理', '#', -1, 0, 1, '2016-01-15 19:10:20', NULL, NULL, NULL),
(93, '微信用户列表', '../wechatUser/index.html', 92, 1, 1, '2016-01-15 19:10:49', NULL, NULL, NULL),
(94, '活动管理', '#', -1, 0, 1, '2016-01-15 20:34:39', NULL, NULL, NULL),
(95, '量房申请', '../designApply/index.html', 94, 1, 1, '2016-01-15 20:35:04', NULL, NULL, NULL),
(96, '更新量房邀请', '../designApply/saveOrUpdate.html', 95, 2, 1, '2016-01-19 12:14:02', NULL, NULL, NULL),
(97, '发送邀请短信', '../designApply/sendMsg.html', 95, 2, 1, '2016-01-19 12:14:47', NULL, NULL, NULL),
(98, '系统参数管理', '#', -1, 0, 1, '2016-01-24 22:05:44', NULL, NULL, NULL),
(99, '区域设置', '../zoneSet/index.html', 98, 1, 1, '2016-01-24 22:06:12', NULL, NULL, NULL),
(100, '增加区域', '../zoneSet/add.html', 99, 2, 1, '2016-01-24 22:06:37', NULL, NULL, NULL),
(101, '更新区域', '../zoneSet/update.html', 99, 2, 1, '2016-01-24 22:06:56', NULL, NULL, NULL),
(102, '状态切换', '../zoneSet/status.html', 99, 2, 1, '2016-01-24 22:07:16', NULL, NULL, NULL),
(103, '房型设置', '../houseType/index.html', 98, 1, 1, '2016-01-24 22:38:27', NULL, NULL, NULL),
(104, '公司管理', '#', -1, 0, 1, '2016-01-25 00:58:47', NULL, NULL, NULL),
(105, '商家信息', '../store/index.html', 104, 1, 1, '2016-01-25 00:59:12', NULL, NULL, NULL),
(106, '商户充值管理', '../balance/index.html', 104, 1, 1, '2016-01-26 19:55:32', NULL, NULL, NULL);

-- --------------------------------------------------------

--
-- 表的结构 `role_manager`
--

CREATE TABLE IF NOT EXISTS `role_manager` (
  `roleId` int(11) NOT NULL,
  `roleName` varchar(20) NOT NULL,
  `status` int(2) NOT NULL,
  `createDate` datetime NOT NULL,
  `createUserId` int(11) DEFAULT NULL,
  `updateDate` datetime DEFAULT NULL,
  `updateUserId` int(11) DEFAULT NULL
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

--
-- 转存表中的数据 `role_manager`
--

INSERT INTO `role_manager` (`roleId`, `roleName`, `status`, `createDate`, `createUserId`, `updateDate`, `updateUserId`) VALUES
(1, '超级管理员', 1, '2015-08-31 13:48:59', NULL, NULL, NULL),
(2, '管理员', 1, '2016-01-19 12:05:30', NULL, NULL, NULL),
(3, '商铺负责人', 1, '2016-01-25 15:39:46', NULL, NULL, NULL);

-- --------------------------------------------------------

--
-- 表的结构 `role_to_menus`
--

CREATE TABLE IF NOT EXISTS `role_to_menus` (
  `id` int(11) NOT NULL,
  `roleId` int(11) NOT NULL,
  `menuId` int(11) NOT NULL
) ENGINE=InnoDB AUTO_INCREMENT=27 DEFAULT CHARSET=utf8;

--
-- 转存表中的数据 `role_to_menus`
--

INSERT INTO `role_to_menus` (`id`, `roleId`, `menuId`) VALUES
(1, 1, 7),
(8, 1, 8),
(9, 1, 9),
(14, 1, 10),
(18, 1, 11),
(2, 1, 12),
(3, 1, 77),
(4, 1, 78),
(5, 1, 79),
(6, 1, 80),
(7, 1, 81),
(10, 1, 82),
(11, 1, 83),
(12, 1, 84),
(13, 1, 85),
(15, 1, 87),
(16, 1, 88),
(17, 1, 89),
(19, 1, 90),
(20, 1, 91),
(21, 2, 92),
(22, 2, 93),
(23, 2, 94),
(24, 2, 95),
(25, 2, 96),
(26, 2, 97);

-- --------------------------------------------------------

--
-- 表的结构 `store`
--

CREATE TABLE IF NOT EXISTS `store` (
  `storeId` int(11) NOT NULL,
  `balance` int(11) NOT NULL DEFAULT '0',
  `keeperId` int(11) DEFAULT NULL,
  `zoneId` int(11) NOT NULL,
  `logo` varchar(100) NOT NULL,
  `storePhone` varchar(20) NOT NULL,
  `storeName` varchar(100) NOT NULL,
  `storeAddress` varchar(100) NOT NULL,
  `callPhone` varchar(20) NOT NULL,
  `msgPhone` varchar(20) NOT NULL,
  `size` int(11) NOT NULL,
  `companyName` varchar(100) DEFAULT NULL,
  `ruleUserName` varchar(50) DEFAULT NULL,
  `ruleUserPhone` varchar(20) DEFAULT NULL,
  `createUserId` int(11) NOT NULL,
  `createDate` datetime NOT NULL,
  `updateUserId` int(11) DEFAULT NULL,
  `updateDate` datetime DEFAULT NULL,
  `status` int(11) NOT NULL
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8mb4;

--
-- 转存表中的数据 `store`
--

INSERT INTO `store` (`storeId`, `balance`, `keeperId`, `zoneId`, `logo`, `storePhone`, `storeName`, `storeAddress`, `callPhone`, `msgPhone`, `size`, `companyName`, `ruleUserName`, `ruleUserPhone`, `createUserId`, `createDate`, `updateUserId`, `updateDate`, `status`) VALUES
(5, 21826, 113, 1, '/imgStore/newImgs/img145380914966647.jpg', '13333333333', '凯特猫测试', '徐汇区明湖路', '15555555555', '15555555555', 150, '去找没凯特毛', '多得是', '15555555555', 113, '2016-01-25 00:00:00', 113, '2016-01-26 16:29:38', 1),
(6, 0, 113, 1, '/imgStore/newImgs/img145372147061916.jpg', '13333333333', '凯特毛测试2', '徐汇区明湖路', '15555555555', '15555555555', 100, '去找没凯特毛', '多得是', '15555555555', 113, '2016-01-25 19:31:29', NULL, NULL, 1),
(7, 0, 113, 12, '/imgStore/newImgs/img145372150229511.jpg', '13333333333', '凯特毛测试3', '徐汇区明湖路', '15555555555', '15555555555', 100, '去找没凯特毛', '多得是', '15555555555', 113, '2016-01-25 19:32:01', 113, '2016-01-26 14:10:07', 1),
(8, 0, 114, 1, '/imgStore/newImgs/img145372153050185.jpg', '15484515151', '凯特毛测试4', '徐汇区明湖路', '15555555555', '15555555555', 100, '去找没凯特毛', '多得是', '15555555555', 113, '2016-01-25 19:32:30', 113, '2016-01-26 14:11:07', 1),
(9, 0, 114, 1, '/imgStore/newImgs/img145372159101085.jpg', '13333333333', '凯特毛测试5', '徐汇区明湖路', '15555555555', '15555555555', 100, '去找没凯特毛', '多得是', '15555555555', 113, '2016-01-25 19:33:16', NULL, NULL, 1),
(10, 0, 114, 1, '/imgStore/newImgs/img145372160805597.jpg', '13333333333', '凯特毛测试6', '徐汇区明湖路', '15555555555', '15555555555', 100, '去找没凯特毛', '多得是', '15555555555', 113, '2016-01-25 19:33:44', 113, '2016-01-26 14:11:10', 1),
(11, 0, 113, 1, '/imgStore/newImgs/img145372163839880.jpg', '13333333333', '凯特毛测试7', '徐汇区明湖路', '15555555555', '15555555555', 100, '去找没凯特毛', '多得是', '15555555555', 113, '2016-01-25 19:34:12', 113, '2016-01-26 14:11:15', 1),
(12, 0, 113, 1, '/imgStore/newImgs/img145372166064265.jpg', '18546546515', '凯特毛测试8', '徐汇区明湖路', '15555555555', '15555555555', 100, '去找没凯特毛', '多得是', '15555555555', 113, '2016-01-25 19:34:39', NULL, NULL, 1),
(13, 0, 114, 1, '/imgStore/newImgs/img145372168827002.jpg', '13333333333', '凯特毛测试9', '徐汇区明湖路', '15555555555', '15555555555', 100, '去找没凯特毛', '多得是', '15555555555', 113, '2016-01-25 19:35:02', 113, '2016-01-26 14:11:17', 1),
(14, 0, 113, 12, '/imgStore/newImgs/img145372171389640.jpg', '13333333333', '凯特猫测试10', '徐汇区明湖路', '15555555555', '15555555555', 100, '去找没凯特毛', '多得是', '15555555555', 113, '2016-01-25 19:35:27', NULL, NULL, 1),
(15, 0, 113, 1, '/imgStore/newImgs/img145372173755071.jpg', '13333333333', '凯特毛测试11', '徐汇区明湖路', '15555555555', '15555555555', 100, '去找没凯特毛', '多得是', '15555555555', 113, '2016-01-25 19:35:51', NULL, NULL, 0),
(16, 0, 113, 1, '/imgStore/newImgs/img145379384205732.jpg', '18461515151', '凯特猫终结店', '徐汇区明湖路', '15555555555', '15555555555', 100, '去找没凯特毛', '多得是', '15555555555', 113, '2016-01-26 15:37:37', 113, '2016-01-26 15:37:43', 1);

-- --------------------------------------------------------

--
-- 表的结构 `store_to_type`
--

CREATE TABLE IF NOT EXISTS `store_to_type` (
  `id` int(11) NOT NULL,
  `storeId` int(11) NOT NULL,
  `typeId` int(11) NOT NULL
) ENGINE=InnoDB AUTO_INCREMENT=33 DEFAULT CHARSET=utf8mb4;

--
-- 转存表中的数据 `store_to_type`
--

INSERT INTO `store_to_type` (`id`, `storeId`, `typeId`) VALUES
(28, 5, 5),
(29, 5, 6),
(30, 5, 7),
(31, 5, 8),
(32, 5, 9),
(1, 16, 5),
(2, 16, 6),
(3, 16, 7),
(4, 16, 8),
(5, 16, 9);

-- --------------------------------------------------------

--
-- 表的结构 `store_to_zone`
--

CREATE TABLE IF NOT EXISTS `store_to_zone` (
  `id` int(11) NOT NULL,
  `storeId` int(11) NOT NULL,
  `zoneId` int(11) NOT NULL
) ENGINE=InnoDB AUTO_INCREMENT=65 DEFAULT CHARSET=utf8mb4;

--
-- 转存表中的数据 `store_to_zone`
--

INSERT INTO `store_to_zone` (`id`, `storeId`, `zoneId`) VALUES
(61, 5, 13),
(62, 5, 15),
(63, 5, 16),
(64, 5, 17),
(4, 6, 13),
(5, 6, 14),
(6, 6, 15),
(7, 6, 16),
(8, 7, 19),
(9, 7, 20),
(10, 8, 16),
(11, 8, 17),
(12, 9, 13),
(13, 9, 14),
(14, 9, 18),
(15, 10, 13),
(16, 10, 14),
(17, 10, 16),
(18, 10, 17),
(19, 11, 13),
(20, 11, 14),
(21, 11, 15),
(22, 11, 16),
(23, 12, 14),
(24, 12, 16),
(25, 13, 13),
(26, 13, 14),
(27, 13, 15),
(28, 14, 19),
(29, 14, 20),
(30, 15, 15),
(31, 15, 16),
(32, 15, 17),
(33, 16, 13),
(34, 16, 14),
(35, 16, 15),
(36, 16, 16),
(37, 16, 17),
(38, 16, 18);

-- --------------------------------------------------------

--
-- 表的结构 `user_to_role`
--

CREATE TABLE IF NOT EXISTS `user_to_role` (
  `id` int(11) NOT NULL,
  `userId` int(11) NOT NULL,
  `roleId` int(11) NOT NULL
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

--
-- 转存表中的数据 `user_to_role`
--

INSERT INTO `user_to_role` (`id`, `userId`, `roleId`) VALUES
(5, 113, 1),
(3, 114, 2),
(6, 113, 3),
(4, 114, 3);

-- --------------------------------------------------------

--
-- 表的结构 `wechat_user`
--

CREATE TABLE IF NOT EXISTS `wechat_user` (
  `openid` varchar(40) NOT NULL,
  `nickname` varchar(50) DEFAULT NULL,
  `sex` varchar(10) DEFAULT NULL,
  `province` varchar(10) DEFAULT NULL,
  `city` varchar(10) DEFAULT NULL,
  `country` varchar(10) DEFAULT NULL,
  `headimgurl` varchar(200) DEFAULT NULL,
  `createDate` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- 表的结构 `zone_set`
--

CREATE TABLE IF NOT EXISTS `zone_set` (
  `zoneId` int(11) NOT NULL,
  `parentId` int(11) DEFAULT NULL,
  `name` varchar(50) NOT NULL,
  `type` int(11) DEFAULT '1',
  `createDate` datetime NOT NULL,
  `createUserId` int(11) NOT NULL,
  `updateDate` datetime DEFAULT NULL,
  `updateUserId` int(11) DEFAULT NULL,
  `status` int(2) NOT NULL
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8mb4;

--
-- 转存表中的数据 `zone_set`
--

INSERT INTO `zone_set` (`zoneId`, `parentId`, `name`, `type`, `createDate`, `createUserId`, `updateDate`, `updateUserId`, `status`) VALUES
(1, NULL, '上海市', 1, '2016-01-24 22:14:16', 113, '2016-01-25 00:48:39', 113, 1),
(12, NULL, '广州市', 1, '2016-01-25 00:53:54', 113, NULL, NULL, 1),
(13, 1, '虹口区', 1, '2016-01-25 00:54:07', 113, '2016-01-25 00:55:20', 113, 1),
(14, 1, '徐汇区', 1, '2016-01-25 00:55:07', 113, '2016-01-25 00:55:16', 113, 1),
(15, 1, '闵行区', 1, '2016-01-25 00:55:44', 113, NULL, NULL, 1),
(16, 1, '浦东新区', 1, '2016-01-25 00:55:55', 113, NULL, NULL, 1),
(17, 1, '杨浦区', 1, '2016-01-25 00:56:06', 113, NULL, NULL, 1),
(18, 1, '普陀区', 1, '2016-01-25 00:56:52', 113, NULL, NULL, 1),
(19, 12, '广州区域A', 1, '2016-01-25 15:03:52', 113, '2016-01-25 15:04:04', 113, 1),
(20, 12, '广州区域B', 1, '2016-01-25 15:03:58', 113, NULL, NULL, 1),
(21, 12, '广州区域C', 1, '2016-01-25 15:04:12', 113, NULL, NULL, 1);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `admin_user`
--
ALTER TABLE `admin_user`
  ADD PRIMARY KEY (`adminUserId`), ADD UNIQUE KEY `loginname` (`loginname`), ADD UNIQUE KEY `email` (`email`);

--
-- Indexes for table `balance_history`
--
ALTER TABLE `balance_history`
  ADD PRIMARY KEY (`historyId`), ADD UNIQUE KEY `orderId` (`orderId`);

--
-- Indexes for table `design_apply`
--
ALTER TABLE `design_apply`
  ADD PRIMARY KEY (`applyId`), ADD UNIQUE KEY `phone` (`phone`);

--
-- Indexes for table `house_type`
--
ALTER TABLE `house_type`
  ADD PRIMARY KEY (`typeId`);

--
-- Indexes for table `menu_manager`
--
ALTER TABLE `menu_manager`
  ADD PRIMARY KEY (`menuId`);

--
-- Indexes for table `role_manager`
--
ALTER TABLE `role_manager`
  ADD PRIMARY KEY (`roleId`);

--
-- Indexes for table `role_to_menus`
--
ALTER TABLE `role_to_menus`
  ADD PRIMARY KEY (`id`), ADD UNIQUE KEY `roleId` (`roleId`,`menuId`);

--
-- Indexes for table `store`
--
ALTER TABLE `store`
  ADD PRIMARY KEY (`storeId`);

--
-- Indexes for table `store_to_type`
--
ALTER TABLE `store_to_type`
  ADD PRIMARY KEY (`id`), ADD KEY `storeId` (`storeId`,`typeId`);

--
-- Indexes for table `store_to_zone`
--
ALTER TABLE `store_to_zone`
  ADD PRIMARY KEY (`id`), ADD KEY `storeId` (`storeId`,`zoneId`);

--
-- Indexes for table `user_to_role`
--
ALTER TABLE `user_to_role`
  ADD PRIMARY KEY (`id`), ADD UNIQUE KEY `roleId` (`roleId`,`userId`);

--
-- Indexes for table `wechat_user`
--
ALTER TABLE `wechat_user`
  ADD PRIMARY KEY (`openid`);

--
-- Indexes for table `zone_set`
--
ALTER TABLE `zone_set`
  ADD PRIMARY KEY (`zoneId`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `admin_user`
--
ALTER TABLE `admin_user`
  MODIFY `adminUserId` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=115;
--
-- AUTO_INCREMENT for table `balance_history`
--
ALTER TABLE `balance_history`
  MODIFY `historyId` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=17;
--
-- AUTO_INCREMENT for table `design_apply`
--
ALTER TABLE `design_apply`
  MODIFY `applyId` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `house_type`
--
ALTER TABLE `house_type`
  MODIFY `typeId` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=10;
--
-- AUTO_INCREMENT for table `menu_manager`
--
ALTER TABLE `menu_manager`
  MODIFY `menuId` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=107;
--
-- AUTO_INCREMENT for table `role_manager`
--
ALTER TABLE `role_manager`
  MODIFY `roleId` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=4;
--
-- AUTO_INCREMENT for table `role_to_menus`
--
ALTER TABLE `role_to_menus`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=27;
--
-- AUTO_INCREMENT for table `store`
--
ALTER TABLE `store`
  MODIFY `storeId` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=17;
--
-- AUTO_INCREMENT for table `store_to_type`
--
ALTER TABLE `store_to_type`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=33;
--
-- AUTO_INCREMENT for table `store_to_zone`
--
ALTER TABLE `store_to_zone`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=65;
--
-- AUTO_INCREMENT for table `user_to_role`
--
ALTER TABLE `user_to_role`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=7;
--
-- AUTO_INCREMENT for table `zone_set`
--
ALTER TABLE `zone_set`
  MODIFY `zoneId` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=22;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
