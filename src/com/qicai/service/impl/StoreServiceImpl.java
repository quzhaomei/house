package com.qicai.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.qicai.bean.bisiness.Store;
import com.qicai.dao.StoreDao;
import com.qicai.dto.PageDTO;
import com.qicai.dto.bisiness.StoreDTO;
import com.qicai.service.StoreService;

@Service
@Transactional
public class StoreServiceImpl implements StoreService {
	@Resource
	private StoreDao storeDao;

	@Override
	public void save(Store store, List<Integer> zoneIds,List<Integer> typeIds) {
		storeDao.save(store);
		if(zoneIds!=null&&zoneIds.size()>=1){
			storeDao.addStoreOrderZones(store.getStoreId(), zoneIds);
		}
		if(typeIds!=null&&typeIds.size()>=1){
			storeDao.addStoreOrderTypes(store.getStoreId(), typeIds);
		}
	}

	@Override
	public void update(Store store, List<Integer> zoneIds,List<Integer> typeIds) {
		storeDao.update(store);
		if(zoneIds!=null){
			storeDao.clearStoreOrderZones(store.getStoreId());
			if(zoneIds.size()>0){
				storeDao.addStoreOrderZones(store.getStoreId(), zoneIds);
			}
		}
		if(typeIds!=null){
			storeDao.clearStoreOrderTypes(store.getStoreId());
			if(zoneIds.size()>0){
				storeDao.addStoreOrderTypes(store.getStoreId(), typeIds);
			}
		}
	}

	@Override
	public PageDTO<List<StoreDTO>> getListByParam(PageDTO<Store> page) {
		List<StoreDTO> dateList = storeDao.getListByParam(page);
		PageDTO<List<StoreDTO>> pageDate = new PageDTO<List<StoreDTO>>();
		pageDate.setParam(dateList);
		pageDate.setPageIndex(page.getPageIndex());
		pageDate.setPageSize(page.getPageSize());
		Integer count = storeDao.getCountByParam(page.getParam());
		count = count % page.getPageSize() == 0 ? count / page.getPageSize()
				: count / page.getPageSize() + 1;
		pageDate.setTotalPage(count);
		return pageDate;
	}

	@Override
	public StoreDTO getByParam(Store store) {
		
		return storeDao.getByParam(store);
	}

	@Override
	public int getCountByParam(Store store) {
		return 0;
	}
}
