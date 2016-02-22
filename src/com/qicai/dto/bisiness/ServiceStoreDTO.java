package com.qicai.dto.bisiness;

import java.util.List;

public class ServiceStoreDTO {
	private Integer storeId;
	private Integer balance;
	private String storeName;
	private Integer size;
	private Integer monthCount;
	private Integer monthFreeCount;
	private List<ZoneCountDTO> zoneCount;
	private List<ZoneCountDTO> typeCount;
	
	public Integer getSize() {
		return size;
	}
	public void setSize(Integer size) {
		this.size = size;
	}
	public Integer getStoreId() {
		return storeId;
	}
	public void setStoreId(Integer storeId) {
		this.storeId = storeId;
	}
	public Integer getBalance() {
		return balance;
	}
	public void setBalance(Integer balance) {
		this.balance = balance;
	}
	public String getStoreName() {
		return storeName;
	}
	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}
	
	public Integer getMonthCount() {
		return monthCount;
	}
	public void setMonthCount(Integer monthCount) {
		this.monthCount = monthCount;
	}
	public Integer getMonthFreeCount() {
		return monthFreeCount;
	}
	public void setMonthFreeCount(Integer monthFreeCount) {
		this.monthFreeCount = monthFreeCount;
	}
	public List<ZoneCountDTO> getZoneCount() {
		return zoneCount;
	}
	public void setZoneCount(List<ZoneCountDTO> zoneCount) {
		this.zoneCount = zoneCount;
	}
	public List<ZoneCountDTO> getTypeCount() {
		return typeCount;
	}
	public void setTypeCount(List<ZoneCountDTO> typeCount) {
		this.typeCount = typeCount;
	}
	
}
