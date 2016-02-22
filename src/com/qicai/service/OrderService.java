package com.qicai.service;

import java.util.List;

import com.qicai.bean.bisiness.Order;
import com.qicai.dto.PageDTO;
import com.qicai.dto.bisiness.OrderDTO;

public interface OrderService {
	void save(Order order);//增
	void update(Order order);//改
	List<OrderDTO> list(Order order);//查询数组
	PageDTO<List<OrderDTO>> getListByParam(PageDTO<Order> page);//查询数组
	OrderDTO getByParam(Order order);//查询单个
	int getCountByParam(Order order);//查询数量
	
	PageDTO<List<OrderDTO>> getListByKeeperId(PageDTO<Order> page,Integer keeperId);//查询数组
	
	OrderDTO getByKeeperId(Order order,Integer keeperId);//查询单个
	
}
