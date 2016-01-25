package com.qicai.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.qicai.bean.bisiness.ZoneSet;
import com.qicai.dao.ZoneSetDao;
import com.qicai.dto.bisiness.ZoneSetDTO;
import com.qicai.service.ZoneSetService;
@Service
@Transactional
public class ZoneSetServiceImpl implements ZoneSetService{
	@Resource
	protected ZoneSetDao zoneSetDao;

	@Override
	public void saveOrUpdate(ZoneSet bean) throws Exception {
		if(bean.getZoneId()!=null){
			zoneSetDao.update(bean);
		}else{
			zoneSetDao.save(bean);
		}
	}

	@Override
	public void delete(ZoneSet bean) {
		
	}

	@Override
	public ZoneSetDTO getById(Integer id) {
		if(id!=null){
			ZoneSet param=new ZoneSet();
			param.setZoneId(id);
			return zoneSetDao.getByParam(param);
		}
		return null;
	}

	@Override
	public List<ZoneSetDTO> getZoneSetByParam(ZoneSet zone) {
		return zoneSetDao.getListByParam(zone);
	}

}