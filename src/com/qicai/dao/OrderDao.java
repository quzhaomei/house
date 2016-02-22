package com.qicai.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.qicai.bean.bisiness.Order;
import com.qicai.dto.PageDTO;
import com.qicai.dto.bisiness.OrderDTO;

public interface OrderDao {
	void save(@Param(value="order") Order order);//��
	void update(@Param(value="order")Order order);//��
	List<OrderDTO> list(@Param(value="order")Order order);//��ѯ����
	
	OrderDTO getByParam(@Param(value="order")Order order);//��ѯ����
	List<OrderDTO> getListByParam(@Param(value="page")PageDTO<Order> page);//��ѯ����
	int getCountByParam(@Param(value="order")Order order);//��ѯ����
	
	List<OrderDTO> getListByKeeperId
	(@Param(value="page")PageDTO<Order> page,@Param(value="keeperId")Integer keeperId);//��ѯ����
	int getCountByKeeperId(@Param(value="order")Order order,@Param(value="keeperId")Integer keeperId);//��ѯ����
	
	OrderDTO getByKeeperId(@Param(value="order")Order order,@Param(value="keeperId")Integer keeperId);//��ѯ����
}
