package com.qicai.service;

import java.util.List;

import com.qicai.bean.bisiness.BalanceHistory;
import com.qicai.dto.PageDTO;
import com.qicai.dto.bisiness.BalanceHistoryDTO;

/**
 *
 * @author Administrator
 *
 */
public interface BalanceHistoryService {
	void save(BalanceHistory history);//��
	PageDTO<List<BalanceHistoryDTO>> getListByPage(PageDTO<BalanceHistory> page);//��ѯ����
}
