package com.qicai.service;

import java.util.List;

import com.qicai.bean.bisiness.Require;
import com.qicai.bean.bisiness.RequireRemark;
import com.qicai.dto.PageDTO;
import com.qicai.dto.admin.AdminUserDTO;
import com.qicai.dto.bisiness.RequireDTO;
import com.qicai.dto.bisiness.RequireGift;

public interface RequireService  {
	void save(Require require) throws Exception;
	
	void saveRemark( RequireRemark remark);//��
	void clearRemark(Integer requiredId);//��
	
	void saveGift(RequireGift requireGift);//��
	
	void update(Require require) throws Exception;
	RequireDTO getByParam(Require require) ;
	PageDTO<List<RequireDTO>> findListByPage(PageDTO<Require> page);
	
	PageDTO<List<RequireDTO>> findPublishByPage(PageDTO<Require> page);
	
	List<RequireDTO> list(Require require);//��ѯ����
	List<RequireDTO> publishList(Require require);//��ѯ����
	
	int getPublishCountByParam(Require require);//��ѯ����
	
	List<AdminUserDTO> getAllPublishUser();
}
