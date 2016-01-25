package com.qicai.service;

import java.util.List;

import com.qicai.bean.bisiness.Store;
import com.qicai.dto.PageDTO;
import com.qicai.dto.bisiness.StoreDTO;

public interface StoreService {
	void save(Store store,List<Integer> zoneIds);//增
	void update(Store store,List<Integer> zoneIds);//改
	PageDTO<List<StoreDTO>> getListByParam(PageDTO<Store> page);//查询数组
	StoreDTO getByParam(Store store);//查询单个
	int getCountByParam(Store store);//查询数量
	
}
