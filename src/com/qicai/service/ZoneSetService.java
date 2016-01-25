package com.qicai.service;

import java.util.List;

import com.qicai.bean.bisiness.ZoneSet;
import com.qicai.dto.bisiness.ZoneSetDTO;

public interface ZoneSetService extends BaseService<ZoneSet, ZoneSetDTO>{
	/**
	 * ��ȡ��������
	 * @return �˵�list
	 */
	List<ZoneSetDTO> getZoneSetByParam(ZoneSet zone);

}
