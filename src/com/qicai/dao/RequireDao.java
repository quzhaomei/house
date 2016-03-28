package com.qicai.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.qicai.bean.bisiness.Require;
import com.qicai.bean.bisiness.RequireRemark;
import com.qicai.dto.PageDTO;
import com.qicai.dto.admin.AdminUserDTO;
import com.qicai.dto.bisiness.RequireDTO;
import com.qicai.dto.bisiness.RequireGift;
/**
 * 用户预约需求
 */
public interface RequireDao {
	void save(@Param(value="require") Require require);//增
	
	void saveRemark(@Param(value="remark") RequireRemark remark);//增
	void clearRemark(@Param(value="requiredId") Integer requiredId);//增
	
	void saveGift(@Param(value="gift") RequireGift requireGift);//增
	
	void update(@Param(value="require")Require require);//改
	void delete(Require require);//删
	List<RequireDTO> getListByParam(@Param(value="page")PageDTO<Require> page);//查询数组
	RequireDTO getByParam(@Param(value="require")Require require);//查询单个
	int getCountByParam(@Param(value="require")Require require);//查询数量
	
	List<RequireDTO> findPublishByPage(@Param(value="page")PageDTO<Require> page);
	int getPublishCountByParam(@Param(value="require")Require require);//查询数量
	
	List<RequireDTO> list(@Param(value="require")Require require);//查询数组
	
	List<RequireDTO> publishList(@Param(value="require")Require require);//查询数组
	
	List<AdminUserDTO> getAllPublishUser();
}
