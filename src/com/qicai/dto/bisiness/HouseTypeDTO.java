package com.qicai.dto.bisiness;

import java.util.Date;

import com.qicai.dto.admin.AdminUserDTO;

/**
 * 房型设置
 * @author Administrator
 *
 */
public class HouseTypeDTO {
	private Integer typeId;
	private String name;
	private Integer price;
	
	private Date createDate;
	private AdminUserDTO createUser;
	private Date updateDate;
	private AdminUserDTO updateUser;
	private Integer status;//1：初始化，能用。0-冻结中
	
	
	
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
	public Integer getTypeId() {
		return typeId;
	}
	public void setTypeId(Integer typeId) {
		this.typeId = typeId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public Integer getPrice() {
		return price;
	}
	public void setPrice(Integer price) {
		this.price = price;
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
