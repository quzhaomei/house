package com.qicai.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.qicai.bean.bisiness.Order;
import com.qicai.dto.PageDTO;
import com.qicai.dto.bisiness.OrderDTO;

public interface OrderDao {
	void save(@Param(value="order") Order order);//增
	void update(@Param(value="order")Order order);//改
	List<OrderDTO> list(@Param(value="order")Order order);//查询数组
	
	OrderDTO getByParam(@Param(value="order")Order order);//查询单个
	List<OrderDTO> getListByParam(@Param(value="page")PageDTO<Order> page);//查询数组
	int getCountByParam(@Param(value="order")Order order);//查询数量
	
	List<OrderDTO> getListByKeeperId
	(@Param(value="page")PageDTO<Order> page,@Param(value="keeperId")Integer keeperId);//查询数组
	int getCountByKeeperId(@Param(value="order")Order order,@Param(value="keeperId")Integer keeperId);//查询数量
	
	OrderDTO getByKeeperId(@Param(value="order")Order order,@Param(value="keeperId")Integer keeperId);//查询单个
}
