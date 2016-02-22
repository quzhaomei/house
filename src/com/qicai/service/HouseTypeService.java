package com.qicai.service;

import java.util.List;

import com.qicai.bean.bisiness.HouseType;
import com.qicai.dto.bisiness.HouseTypeDTO;

public interface HouseTypeService extends BaseService<HouseType, HouseTypeDTO>{
	/**
	 * ��ȡ���з���
	 * @return �˵�list
	 */
	List<HouseTypeDTO> getAllHouseType();
	
	List<HouseTypeDTO> getListByParam(HouseType type);

}
