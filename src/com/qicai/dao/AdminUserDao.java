package com.qicai.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.qicai.bean.admin.AdminUser;
import com.qicai.dto.PageDTO;
import com.qicai.dto.admin.AdminUserDTO;
import com.qicai.dto.bisiness.ServiceUserDTO;

/**
 *
 * @author Administrator
 *
 */
public interface AdminUserDao {
	void save(@Param("user")AdminUser user);//增
	void update(@Param("user")AdminUser user);//改
	List<AdminUserDTO> getListByParam(@Param("user")AdminUser user);//查询数组
	List<AdminUserDTO> getListByPage(@Param("page")PageDTO<AdminUser> page);//查询数组
	AdminUserDTO getByParam(@Param("user")AdminUser user);//查询单个
	int getCountByParam(@Param("user")AdminUser user);//查询数量
	int checkUserCount(@Param("user")AdminUser user);
	AdminUserDTO mengHuLogin(@Param("user")AdminUser user);//门户登陆
	
	List<AdminUserDTO> getListByParamAndRole(@Param("user")AdminUser user,
			@Param("roleId")Integer roleId);//查询数组
	
	//分页获取所有业务员
	List<ServiceUserDTO> getServiceByParam(@Param(value="page")PageDTO<AdminUser> page,@Param(value="roleId")Integer roleId);//查询数组
	int getServiceCount(@Param(value="user")AdminUser user,@Param(value="roleId")Integer roleId);
}
