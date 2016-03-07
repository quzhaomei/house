package com.qicai.service;

import java.util.List;

import com.qicai.bean.bisiness.HouseTypeToStore;
import com.qicai.dto.bisiness.HouseTypeToStoreDTO;

public interface HouseTypeToStoreService {
	void save(HouseTypeToStore temp) throws Exception;//增
	void update(HouseTypeToStore temp) throws Exception;//修改
	void delete(HouseTypeToStore temp);//删
	List<HouseTypeToStoreDTO> getListByParam(HouseTypeToStore type);//查询数组
	HouseTypeToStoreDTO getByParam(HouseTypeToStore temp);//查询单个
}
