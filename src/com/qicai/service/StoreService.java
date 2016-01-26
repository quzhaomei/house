package com.qicai.service;

import java.util.List;

import com.qicai.bean.bisiness.Store;
import com.qicai.dto.PageDTO;
import com.qicai.dto.bisiness.StoreDTO;

public interface StoreService {
	void save(Store store,List<Integer> zoneIds,List<Integer> typeId);//��
	void update(Store store,List<Integer> zoneIds,List<Integer> typeId);//��
	PageDTO<List<StoreDTO>> getListByParam(PageDTO<Store> page);//��ѯ����
	StoreDTO getByParam(Store store);//��ѯ����
	int getCountByParam(Store store);//��ѯ����
	
}
