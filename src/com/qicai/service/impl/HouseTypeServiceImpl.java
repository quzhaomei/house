package com.qicai.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.qicai.bean.bisiness.HouseType;
import com.qicai.dao.HouseTypeDao;
import com.qicai.dto.bisiness.HouseTypeDTO;
import com.qicai.service.HouseTypeService;
@Service
@Transactional
public class HouseTypeServiceImpl implements HouseTypeService{
	@Resource
	protected HouseTypeDao houseTypeDao;

	@Override
	public void saveOrUpdate(HouseType bean) throws Exception {
		if(bean.getTypeId()!=null){
			houseTypeDao.update(bean);
		}else{
			houseTypeDao.save(bean);
		}
	}

	@Override
	public void delete(HouseType bean) {
		
	}

	@Override
	public HouseTypeDTO getById(Integer id) {
		if(id!=null){
			HouseType param=new HouseType();
			param.setTypeId(id);
			return houseTypeDao.getByParam(param);
		}
		return null;
	}

	@Override
	public List<HouseTypeDTO> getAllHouseType() {
		return houseTypeDao.getListByParam(new HouseType());
	}

	@Override
	public List<HouseTypeDTO> getListByParam(HouseType type) {
		return houseTypeDao.getListByParam(type);
	}
}