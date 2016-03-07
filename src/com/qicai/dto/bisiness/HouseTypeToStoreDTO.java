package com.qicai.dto.bisiness;

import java.util.Date;

import com.qicai.dto.admin.AdminUserDTO;

public class HouseTypeToStoreDTO {
	private Integer id;
	private Integer storeId;
	private String storeName;

	private Integer price;
	private Date updateDate;

	private HouseTypeDTO houseTypeDTO;
	private AdminUserDTO updateUser;

	public HouseTypeDTO getHouseTypeDTO() {
		return houseTypeDTO;
	}

	public Integer getStoreId() {
		return storeId;
	}

	public void setStoreId(Integer storeId) {
		this.storeId = storeId;
	}

	public void setHouseTypeDTO(HouseTypeDTO houseTypeDTO) {
		this.houseTypeDTO = houseTypeDTO;
	}

	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}

	public AdminUserDTO getUpdateUser() {
		return updateUser;
	}

	public void setUpdateUser(AdminUserDTO updateUser) {
		this.updateUser = updateUser;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public String getStoreName() {
		return storeName;
	}

	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
}
