package com.qicai.dto.bisiness;

import java.util.Date;

import com.qicai.dto.admin.AdminUserDTO;

/**
 * 接单房型设置
 * @author Administrator
 *
 */
public class ZoneSetDTO {
	private Integer zoneId;
	private ZoneSetDTO parent;//父级
	private String name;
	private Date createDate;
	private AdminUserDTO createUser;
	private Date updateDate;
	private AdminUserDTO updateUser;
	private Integer status;
	
	
	public ZoneSetDTO getParent() {
		return parent;
	}
	public void setParent(ZoneSetDTO parent) {
		this.parent = parent;
	}
	public AdminUserDTO getCreateUser() {
		return createUser;
	}
	public void setCreateUser(AdminUserDTO createUser) {
		this.createUser = createUser;
	}
	public AdminUserDTO getUpdateUser() {
		return updateUser;
	}
	public void setUpdateUser(AdminUserDTO updateUser) {
		this.updateUser = updateUser;
	}
	public Integer getZoneId() {
		return zoneId;
	}
	public void setZoneId(Integer zoneId) {
		this.zoneId = zoneId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public Date getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	
}
