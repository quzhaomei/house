package com.qicai.service;

import java.util.List;

import com.qicai.bean.bisiness.ZoneSet;
import com.qicai.dto.bisiness.ZoneSetDTO;

public interface ZoneSetService extends BaseService<ZoneSet, ZoneSetDTO>{
	/**
	 * 获取所有区域
	 * @return 菜单list
	 */
	List<ZoneSetDTO> getZoneSetByParam(ZoneSet zone);

}
