package com.qicai.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.qicai.bean.bisiness.HouseType;
import com.qicai.dto.bisiness.HouseTypeDTO;

public interface HouseTypeDao {
	void save(@Param(value="type") HouseType type);//��
	void update(@Param(value="type")HouseType type);//��
	void delete(HouseType type);//ɾ
	List<HouseTypeDTO> getListByParam(@Param(value="type")HouseType type);//��ѯ����
	HouseTypeDTO getByParam(@Param(value="type")HouseType type);//��ѯ����
	int getCountByParam(HouseType type);//��ѯ����

}
