package com.qicai.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.qicai.bean.bisiness.BalanceHistory;
import com.qicai.dto.PageDTO;
import com.qicai.dto.bisiness.BalanceHistoryDTO;

/**
 *
 * @author Administrator
 *
 */
public interface BalanceHistoryDao {
	void save(@Param("history")BalanceHistory history);//增
	List<BalanceHistoryDTO> getListByPage(@Param("page")PageDTO<BalanceHistory> page);//查询数组
	int getCountByParam(@Param("history")BalanceHistory history);//查询数量
}
