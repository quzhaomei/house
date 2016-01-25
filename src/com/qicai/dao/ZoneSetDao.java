package com.qicai.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.qicai.bean.bisiness.ZoneSet;
import com.qicai.dto.bisiness.ZoneSetDTO;

public interface ZoneSetDao {
	void save(@Param(value="zone") ZoneSet zone);//��
	void update(@Param(value="zone")ZoneSet zone);//��
	void delete(ZoneSet role);//ɾ
	List<ZoneSetDTO> getListByParam(@Param(value="zone")ZoneSet zone);//��ѯ����
	ZoneSetDTO getByParam(@Param(value="zone")ZoneSet zone);//��ѯ����
	int getCountByParam(ZoneSet zone);//��ѯ����

}
