package com.qicai.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.qicai.bean.bisiness.Store;
import com.qicai.dto.PageDTO;
import com.qicai.dto.bisiness.StoreDTO;

public interface StoreDao {
	void save(@Param(value="store") Store store);//增
	void update(@Param(value="store")Store store);//改
	void delete(Store role);//删
	List<StoreDTO> getListByParam(@Param(value="page")PageDTO<Store> page);//查询数组
	StoreDTO getByParam(@Param(value="store")Store store);//查询单个
	int getCountByParam(@Param(value="store")Store store);//查询数量
	
	void addStoreOrderZones(@Param(value="storeId")Integer storeId,
			@Param(value="zoneIds")List<Integer> zoneIds);
	void clearStoreOrderZones(@Param(value="storeId")Integer storeId);
	
	void addStoreOrderTypes(@Param(value="storeId")Integer storeId,
			@Param(value="typeIds")List<Integer> zoneIds);
	void clearStoreOrderTypes(@Param(value="storeId")Integer storeId);
}
