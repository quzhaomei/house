package com.qicai.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.qicai.bean.bisiness.Require;
import com.qicai.bean.bisiness.RequireRemark;
import com.qicai.dao.RequireDao;
import com.qicai.dto.PageDTO;
import com.qicai.dto.admin.AdminUserDTO;
import com.qicai.dto.bisiness.RequireDTO;
import com.qicai.dto.bisiness.RequireGift;
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

	@Override
	public PageDTO<List<RequireDTO>> findPublishByPage(PageDTO<Require> page) {
		List<RequireDTO> dateList = requireDao.findPublishByPage(page);

		PageDTO<List<RequireDTO>> pageDate = new PageDTO<List<RequireDTO>>();
		pageDate.setParam(dateList);
		pageDate.setPageIndex(page.getPageIndex());
		pageDate.setPageSize(page.getPageSize());
		Integer count = requireDao.getPublishCountByParam(page.getParam());
		count = count % page.getPageSize() == 0 ? count / page.getPageSize() : count / page.getPageSize() + 1;
		pageDate.setTotalPage(count);
		return pageDate;
	}

	@Override
	public List<RequireDTO> publishList(Require require) {
		return requireDao.publishList(require);
	}

	@Override
	public int getPublishCountByParam(Require require) {
		return requireDao.getPublishCountByParam(require);
	}

	@Override
	public void saveGift(RequireGift requireGift) {
		requireDao.saveGift(requireGift);
	}

	@Override
	public List<AdminUserDTO> getAllPublishUser() {
		return requireDao.getAllPublishUser();
	}

	@Override
	public void saveRemark(RequireRemark remark) {
		requireDao.saveRemark(remark);
	}

	@Override
	public void clearRemark(Integer requiredId) {
		requireDao.clearRemark(requiredId);
	}

}
