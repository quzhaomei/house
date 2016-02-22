package com.qicai.service;

import java.util.List;

import com.qicai.bean.bisiness.Order;
import com.qicai.dto.PageDTO;
import com.qicai.dto.bisiness.OrderDTO;

public interface OrderService {
	void save(Order order);//��
	void update(Order order);//��
	List<OrderDTO> list(Order order);//��ѯ����
	PageDTO<List<OrderDTO>> getListByParam(PageDTO<Order> page);//��ѯ����
	OrderDTO getByParam(Order order);//��ѯ����
	int getCountByParam(Order order);//��ѯ����
	
	PageDTO<List<OrderDTO>> getListByKeeperId(PageDTO<Order> page,Integer keeperId);//��ѯ����
	
	OrderDTO getByKeeperId(Order order,Integer keeperId);//��ѯ����
	
}
