package com.qicai.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.qicai.bean.bisiness.Require;
import com.qicai.dao.RequireDao;
import com.qicai.dto.PageDTO;
import com.qicai.dto.bisiness.RequireDTO;
import com.qicai.service.RequireService;

@Service
@Transactional
public class RequireServiceImpl implements RequireService {
	@Resource
	private RequireDao requireDao;

	@Override
	public void save(Require require) throws Exception {
		requireDao.save(require);
	}

	@Override
	public void update(Require require) throws Exception {
		requireDao.update(require);
	}

	@Override
	public PageDTO<List<RequireDTO>> findListByPage(PageDTO<Require> page) {
		List<RequireDTO> dateList = requireDao.getListByParam(page);

		PageDTO<List<RequireDTO>> pageDate = new PageDTO<List<RequireDTO>>();
		pageDate.setParam(dateList);
		pageDate.setPageIndex(page.getPageIndex());
		pageDate.setPageSize(page.getPageSize());
		Integer count = requireDao.getCountByParam(page.getParam());
		count = count % page.getPageSize() == 0 ? count / page.getPageSize() : count / page.getPageSize() + 1;
		pageDate.setTotalPage(count);
		return pageDate;
	}

	@Override
	public RequireDTO getByParam(Require require) {
		RequireDTO requireDTO = requireDao.getByParam(require);
		return requireDTO;
	}

	@Override
	public List<RequireDTO> list(Require require) {
		return requireDao.list(require);
	}

}
