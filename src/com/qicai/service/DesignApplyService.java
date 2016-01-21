package com.qicai.service;

import java.util.List;

import com.qicai.bean.minisite.DesignApply;
import com.qicai.dto.PageDTO;

public interface DesignApplyService {
	void save(DesignApply apply) throws Exception;
	void update(DesignApply apply) throws Exception;
	DesignApply getByParam(DesignApply apply);//按条件索引
	PageDTO<List<DesignApply>> findByPage(PageDTO<DesignApply> page);
}
