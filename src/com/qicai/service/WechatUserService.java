package com.qicai.service;

import java.util.List;

import com.qicai.bean.WechatUser;
import com.qicai.dto.PageDTO;

public interface WechatUserService {
	void saveWechatUser(WechatUser wechatUser);
	void update( WechatUser wechatUser );
	WechatUser getWechatUserByOpenId( String openId);
	PageDTO<List<WechatUser>> getListByParam( PageDTO<WechatUser> page);
	int getCountByParam(PageDTO<WechatUser> page);
}
