package com.qicai.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.qicai.bean.bisiness.BalanceHistory;
import com.qicai.bean.bisiness.Store;
import com.qicai.dao.BalanceHistoryDao;
import com.qicai.dao.StoreDao;
import com.qicai.dto.PageDTO;
import com.qicai.dto.bisiness.BalanceHistoryDTO;
import com.qicai.dto.bisiness.StoreDTO;
import com.qicai.service.BalanceHistoryService;

@Service
@Transactional
public class BalanceHistoryServiceImpl implements BalanceHistoryService {
	@Resource
	private BalanceHistoryDao balanceHistoryDao;
	@Resource
	private StoreDao storeDao;

	@Override
	public void save(BalanceHistory history) {
		//充值时候 进行账户更新
		balanceHistoryDao.save(history);
		
		StoreDTO store=storeDao.getByParam(new Store(history.getStoreId()));
		Integer value=0;
		if(history.getType()==0){//充值
			value=store.getBalance()+history.getValue();
		}else{//消费或赠送
			value=store.getBalance()-history.getValue();
		}
		Store updateStore =new Store(history.getStoreId());
		updateStore.setBalance(value);
		storeDao.update(updateStore);
	}

	@Override
	public PageDTO<List<BalanceHistoryDTO>> getListByPage(PageDTO<BalanceHistory> page) {
		List<BalanceHistoryDTO> dateList = balanceHistoryDao.getListByPage(page);
		PageDTO<List<BalanceHistoryDTO>> pageDate = new PageDTO<List<BalanceHistoryDTO>>();
		pageDate.setParam(dateList);
		pageDate.setPageIndex(page.getPageIndex());
		pageDate.setPageSize(page.getPageSize());
		Integer count = balanceHistoryDao.getCountByParam(page.getParam());
		count = count % page.getPageSize() == 0 ? count / page.getPageSize()
				: count / page.getPageSize() + 1;
		pageDate.setTotalPage(count);
		return pageDate;
	}
}
