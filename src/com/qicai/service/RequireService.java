package com.qicai.service;

import java.util.List;

import com.qicai.bean.bisiness.Require;
import com.qicai.dto.PageDTO;
import com.qicai.dto.bisiness.RequireDTO;

public interface RequireService  {
	void save(Require require) throws Exception;
	void update(Require require) throws Exception;
	RequireDTO getByParam(Require require) ;
	PageDTO<List<RequireDTO>> findListByPage(PageDTO<Require> page);
	
	List<RequireDTO> list(Require require);//²éÑ¯Êý×é
}
