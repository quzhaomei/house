package com.qicai.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.qicai.bean.bisiness.ZoneSet;
import com.qicai.dto.bisiness.ZoneSetDTO;

public interface ZoneSetDao {
	void save(@Param(value="zone") ZoneSet zone);//增
	void update(@Param(value="zone")ZoneSet zone);//改
	void delete(ZoneSet role);//删
	List<ZoneSetDTO> getListByParam(@Param(value="zone")ZoneSet zone);//查询数组
	ZoneSetDTO getByParam(@Param(value="zone")ZoneSet zone);//查询单个
	int getCountByParam(ZoneSet zone);//查询数量

}
