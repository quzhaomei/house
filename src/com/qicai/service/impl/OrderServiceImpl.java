package com.qicai.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.qicai.bean.bisiness.Order;
import com.qicai.dao.OrderDao;
import com.qicai.dto.PageDTO;
import com.qicai.dto.bisiness.OrderDTO;
import com.qicai.service.OrderService;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {
	@Resource
	private OrderDao orderDao;

	@Override
	public void save(Order order) {
		orderDao.save(order);
	}

	@Override
	public void update(Order order) {
		orderDao.update(order);
	}

	@Override
	public PageDTO<List<OrderDTO>> getListByParam(PageDTO<Order> page) {
		List<OrderDTO> dateList = orderDao.getListByParam(page);
		PageDTO<List<OrderDTO>> pageDate = new PageDTO<List<OrderDTO>>();
		pageDate.setParam(dateList);
		pageDate.setPageIndex(page.getPageIndex());
		pageDate.setPageSize(page.getPageSize());
		Integer count = orderDao.getCountByParam(page.getParam());
		count = count % page.getPageSize() == 0 ? count / page.getPageSize()
				: count / page.getPageSize() + 1;
		pageDate.setTotalPage(count);
		return pageDate;
	}

	@Override
	public OrderDTO getByParam(Order order) {
		return orderDao.getByParam(order);
	}

	@Override
	public int getCountByParam(Order order) {
		return orderDao.getCountByParam(order);
	}

	@Override
	public PageDTO<List<OrderDTO>> getListByKeeperId(PageDTO<Order> page, Integer keeperId) {
		List<OrderDTO> dateList = orderDao.getListByKeeperId(page,keeperId);
		PageDTO<List<OrderDTO>> pageDate = new PageDTO<List<OrderDTO>>();
		pageDate.setParam(dateList);
		pageDate.setPageIndex(page.getPageIndex());
		pageDate.setPageSize(page.getPageSize());
		Integer count = orderDao.getCountByKeeperId(page.getParam(),keeperId);
		count = count % page.getPageSize() == 0 ? count / page.getPageSize()
				: count / page.getPageSize() + 1;
		pageDate.setTotalPage(count);
		return pageDate;
	}

	@Override
	public OrderDTO getByKeeperId(Order order, Integer keeperId) {
		return orderDao.getByKeeperId(order, keeperId);
	}

	@Override
	public List<OrderDTO> list(Order order) {
		return orderDao.list(order);
	}
}
