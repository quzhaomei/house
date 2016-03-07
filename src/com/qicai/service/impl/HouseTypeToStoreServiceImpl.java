package com.qicai.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.qicai.bean.bisiness.HouseTypeToStore;
import com.qicai.dao.HouseTypeToStoreDao;
import com.qicai.dto.bisiness.HouseTypeToStoreDTO;
import com.qicai.service.HouseTypeToStoreService;

@Service
@Transactional
public class HouseTypeToStoreServiceImpl implements HouseTypeToStoreService {
	@Resource
	private HouseTypeToStoreDao tempDao;

	@Override
	public void save(HouseTypeToStore temp) {
		tempDao.save(temp);
	}

	@Override
	public void update(HouseTypeToStore temp) {
		tempDao.update(temp);
	}

	@Override
	public void delete(HouseTypeToStore temp) {
		tempDao.delete(temp);
	}

	@Override
	public List<HouseTypeToStoreDTO> getListByParam(HouseTypeToStore type) {
		return tempDao.getListByParam(type);
	}

	@Override
	public HouseTypeToStoreDTO getByParam(HouseTypeToStore temp) {
		return tempDao.getByParam(temp);
	}

}
