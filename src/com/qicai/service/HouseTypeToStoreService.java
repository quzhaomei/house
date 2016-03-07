package com.qicai.service;

import java.util.List;

import com.qicai.bean.bisiness.HouseTypeToStore;
import com.qicai.dto.bisiness.HouseTypeToStoreDTO;

public interface HouseTypeToStoreService {
	void save(HouseTypeToStore temp) throws Exception;//��
	void update(HouseTypeToStore temp) throws Exception;//�޸�
	void delete(HouseTypeToStore temp);//ɾ
	List<HouseTypeToStoreDTO> getListByParam(HouseTypeToStore type);//��ѯ����
	HouseTypeToStoreDTO getByParam(HouseTypeToStore temp);//��ѯ����
}
