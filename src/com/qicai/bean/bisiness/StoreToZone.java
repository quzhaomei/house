package com.qicai.bean.bisiness;
/**
 *  店铺 接单区域 中间表
 */
public class StoreToZone {
	private Integer id;
	private Integer storeId;
	private Integer zoneId;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getStoreId() {
		return storeId;
	}
	public void setStoreId(Integer storeId) {
		this.storeId = storeId;
	}
	public Integer getZoneId() {
		return zoneId;
	}
	public void setZoneId(Integer zoneId) {
		this.zoneId = zoneId;
	}
	
}
