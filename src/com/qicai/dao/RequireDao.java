package com.qicai.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.qicai.bean.bisiness.Require;
import com.qicai.dto.PageDTO;
import com.qicai.dto.bisiness.RequireDTO;
import com.qicai.dto.bisiness.RequireGift;
/**
 * �û�ԤԼ����
 */
public interface RequireDao {
	void save(@Param(value="require") Require require);//��
	
	void saveGift(@Param(value="gift") RequireGift requireGift);//��
	
	void update(@Param(value="require")Require require);//��
	void delete(Require require);//ɾ
	List<RequireDTO> getListByParam(@Param(value="page")PageDTO<Require> page);//��ѯ����
	RequireDTO getByParam(@Param(value="require")Require require);//��ѯ����
	int getCountByParam(@Param(value="require")Require require);//��ѯ����
	
	List<RequireDTO> findPublishByPage(@Param(value="page")PageDTO<Require> page);
	int getPublishCountByParam(@Param(value="require")Require require);//��ѯ����
	
	List<RequireDTO> list(@Param(value="require")Require require);//��ѯ����
	
	List<RequireDTO> publishList(@Param(value="require")Require require);//��ѯ����
}
