package com.qicai.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.qicai.bean.WechatUser;
import com.qicai.dto.PageDTO;

public interface WechatUserDao {
	void saveWechatUser(@Param(value="wechatUser")WechatUser wechatUser);
	void updateWechatUser(@Param(value="wechatUser")WechatUser wechatUser);
	WechatUser getWechatUserByOpenId(@Param(value="openId") String openId);
	List<WechatUser> getListByParam(@Param(value="page") PageDTO<WechatUser> page);
	int getCountByParam(@Param(value="page") PageDTO<WechatUser> page);
}
