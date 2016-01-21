-- phpMyAdmin SQL Dump
-- version 4.3.11
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: 2016-01-21 06:42:18
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
(113, 'jiawook', '6A75D505AE56DE79D5564C3F1666B59E', '测试专用', '13333333333', '123456@qq.com', 1, 0, '', 1, 'ccs', '2015-09-01 20:58:32', NULL, '2016-01-15 10:16:58', 113),
(114, 'getmore', '25F9E794323B453885F5181F1B624D0B', '凯特猫', '18888888888', '12345@getmore.com', 1, 0, NULL, 1, '', '2016-01-19 12:06:54', 113, NULL, NULL);

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
) ENGINE=InnoDB AUTO_INCREMENT=98 DEFAULT CHARSET=utf8;

--
-- 转存表中的数据 `menu_manager`
--

INSERT INTO `menu_manager` (`menuId`, `menuName`, `uri`, `parentId`, `type`, `status`, `createDate`, `updateDate`, `createUserId`, `updateUserId`) VALUES
(7, '用户管理', '#', -1, 0, 1, '2015-08-30 00:00:00', NULL, NULL, NULL),
(8, '系统设置', '#', -1, 0, 1, '2015-08-30 00:00:00', NULL, NULL, NULL),
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
(97, '发送邀请短信', '../designApply/sendMsg.html', 95, 2, 1, '2016-01-19 12:14:47', NULL, NULL, NULL);

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
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

--
-- 转存表中的数据 `role_manager`
--

INSERT INTO `role_manager` (`roleId`, `roleName`, `status`, `createDate`, `createUserId`, `updateDate`, `updateUserId`) VALUES
(1, '超级管理员', 1, '2015-08-31 13:48:59', NULL, NULL, NULL),
(2, '管理员', 1, '2016-01-19 12:05:30', NULL, NULL, NULL);

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
-- 表的结构 `user_to_role`
--

CREATE TABLE IF NOT EXISTS `user_to_role` (
  `id` int(11) NOT NULL,
  `userId` int(11) NOT NULL,
  `roleId` int(11) NOT NULL
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

--
-- 转存表中的数据 `user_to_role`
--

INSERT INTO `user_to_role` (`id`, `userId`, `roleId`) VALUES
(1, 113, 1),
(2, 114, 2);

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

--
-- Indexes for dumped tables
--

--
-- Indexes for table `admin_user`
--
ALTER TABLE `admin_user`
  ADD PRIMARY KEY (`adminUserId`), ADD UNIQUE KEY `loginname` (`loginname`), ADD UNIQUE KEY `email` (`email`);

--
-- Indexes for table `design_apply`
--
ALTER TABLE `design_apply`
  ADD PRIMARY KEY (`applyId`), ADD UNIQUE KEY `phone` (`phone`);

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
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `admin_user`
--
ALTER TABLE `admin_user`
  MODIFY `adminUserId` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=115;
--
-- AUTO_INCREMENT for table `design_apply`
--
ALTER TABLE `design_apply`
  MODIFY `applyId` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `menu_manager`
--
ALTER TABLE `menu_manager`
  MODIFY `menuId` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=98;
--
-- AUTO_INCREMENT for table `role_manager`
--
ALTER TABLE `role_manager`
  MODIFY `roleId` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=3;
--
-- AUTO_INCREMENT for table `role_to_menus`
--
ALTER TABLE `role_to_menus`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=27;
--
-- AUTO_INCREMENT for table `user_to_role`
--
ALTER TABLE `user_to_role`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=3;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
