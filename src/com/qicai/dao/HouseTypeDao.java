package com.qicai.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.qicai.bean.bisiness.HouseType;
import com.qicai.dto.bisiness.HouseTypeDTO;

public interface HouseTypeDao {
	void save(@Param(value="type") HouseType type);//增
	void update(@Param(value="type")HouseType type);//改
	void delete(HouseType type);//删
	List<HouseTypeDTO> getListByParam(@Param(value="type")HouseType type);//查询数组
	HouseTypeDTO getByParam(@Param(value="type")HouseType type);//查询单个
	int getCountByParam(HouseType type);//查询数量

}
