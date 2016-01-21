package com.qicai.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.qicai.bean.WechatUser;
import com.qicai.dao.WechatUserDao;
import com.qicai.dto.PageDTO;
import com.qicai.service.WechatUserService;
@Service
public class WechatUserServiceImpl implements WechatUserService {
	@Resource
	private WechatUserDao wechatUserDao;
	@Override
	public void saveWechatUser(WechatUser wechatUser) {
		wechatUser.setCreateDate(new Date());
		wechatUserDao.saveWechatUser(wechatUser);
	}

	@Override
	public WechatUser getWechatUserByOpenId(String openId) {
		if(openId==null)
			return null;
		return wechatUserDao.getWechatUserByOpenId(openId);
	}

	@Override
	public PageDTO<List<WechatUser>> getListByParam(PageDTO<WechatUser> page) {
		PageDTO<List<WechatUser>> result=new PageDTO<List<WechatUser>>();
		result.setPageIndex(page.getPageIndex());
		result.setPageSize(page.getPageSize());
		int count=wechatUserDao.getCountByParam(page);
		int totalPage=1;
		totalPage=count%page.getPageSize()==0?count/page.getPageSize():count/page.getPageSize()+1;
		result.setTotalPage(totalPage);
		List<WechatUser> dateList=wechatUserDao.getListByParam(page);
		result.setParam(dateList);
		return result;
	}

	@Override
	public int getCountByParam(PageDTO<WechatUser> page) {
		return wechatUserDao.getCountByParam(page);
	}

	@Override
	public void update(WechatUser wechatUser) {
		wechatUserDao.updateWechatUser(wechatUser);
	}

}
