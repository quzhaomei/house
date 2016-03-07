package com.qicai.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.qicai.bean.bisiness.HouseTypeToStore;
import com.qicai.dto.bisiness.HouseTypeToStoreDTO;

public interface HouseTypeToStoreDao {
	void save(@Param(value="temp") HouseTypeToStore temp);//增
	void update(@Param(value="temp") HouseTypeToStore temp);//修改
	void delete(@Param(value="temp") HouseTypeToStore temp);//删
	List<HouseTypeToStoreDTO> getListByParam(@Param(value="temp")HouseTypeToStore type);//查询数组
	HouseTypeToStoreDTO getByParam(@Param(value="temp")HouseTypeToStore temp);//查询单个
}
