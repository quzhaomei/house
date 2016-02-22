package com.qicai.service;

import java.util.List;

import com.qicai.bean.admin.AdminUser;
import com.qicai.dto.PageDTO;
import com.qicai.dto.admin.AdminUserDTO;
import com.qicai.dto.bisiness.ServiceUserDTO;

public interface AdminUserService extends BaseService<AdminUser, AdminUserDTO> {
	void saveUserAndRole(AdminUser user,List<Integer> roleIds) throws Exception;
	void updateUserAndRole(AdminUser user,List<Integer> roleIds) throws Exception;
	
	PageDTO<List<AdminUserDTO>> findPageDateByPageParam(PageDTO<AdminUser> page);
	int checkUserCount(AdminUser user);
	
	AdminUserDTO getUserByParam(AdminUser adminUser);
	
	AdminUserDTO mengHuLogin(AdminUser adminUser);
	
	List<AdminUserDTO> getListByParamAndRole(AdminUser user,Integer roleId);//��ѯ����
	
	PageDTO<List<ServiceUserDTO>> getServiceByParam(PageDTO<AdminUser> page,Integer roleId);
}
