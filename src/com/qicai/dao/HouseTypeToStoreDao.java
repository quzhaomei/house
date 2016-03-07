package com.qicai.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.qicai.bean.bisiness.HouseTypeToStore;
import com.qicai.dto.bisiness.HouseTypeToStoreDTO;

public interface HouseTypeToStoreDao {
	void save(@Param(value="temp") HouseTypeToStore temp);//��
	void update(@Param(value="temp") HouseTypeToStore temp);//�޸�
	void delete(@Param(value="temp") HouseTypeToStore temp);//ɾ
	List<HouseTypeToStoreDTO> getListByParam(@Param(value="temp")HouseTypeToStore type);//��ѯ����
	HouseTypeToStoreDTO getByParam(@Param(value="temp")HouseTypeToStore temp);//��ѯ����
}
