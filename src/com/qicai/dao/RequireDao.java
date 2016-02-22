package com.qicai.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.qicai.bean.bisiness.Require;
import com.qicai.dto.PageDTO;
import com.qicai.dto.bisiness.RequireDTO;
/**
 * 用户预约需求
 */
public interface RequireDao {
	void save(@Param(value="require") Require require);//增
	void update(@Param(value="require")Require require);//改
	void delete(Require require);//删
	List<RequireDTO> getListByParam(@Param(value="page")PageDTO<Require> page);//查询数组
	RequireDTO getByParam(@Param(value="require")Require require);//查询单个
	int getCountByParam(@Param(value="require")Require require);//查询数量
	
	List<RequireDTO> list(@Param(value="require")Require require);//查询数组
}
