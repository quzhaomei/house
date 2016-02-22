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
	void save(@Param("user")AdminUser user);//��
	void update(@Param("user")AdminUser user);//��
	List<AdminUserDTO> getListByParam(@Param("user")AdminUser user);//��ѯ����
	List<AdminUserDTO> getListByPage(@Param("page")PageDTO<AdminUser> page);//��ѯ����
	AdminUserDTO getByParam(@Param("user")AdminUser user);//��ѯ����
	int getCountByParam(@Param("user")AdminUser user);//��ѯ����
	int checkUserCount(@Param("user")AdminUser user);
	AdminUserDTO mengHuLogin(@Param("user")AdminUser user);//�Ż���½
	
	List<AdminUserDTO> getListByParamAndRole(@Param("user")AdminUser user,
			@Param("roleId")Integer roleId);//��ѯ����
	
	//��ҳ��ȡ����ҵ��Ա
	List<ServiceUserDTO> getServiceByParam(@Param(value="page")PageDTO<AdminUser> page,@Param(value="roleId")Integer roleId);//��ѯ����
	int getServiceCount(@Param(value="user")AdminUser user,@Param(value="roleId")Integer roleId);
}