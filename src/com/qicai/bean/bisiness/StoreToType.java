package com.qicai.bean.bisiness;
/**
 *  店铺 接单房型 中间表
 */
public class StoreToType {
	private Integer id;
	private Integer storeId;
	private Integer typeId;
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
	public Integer getTypeId() {
		return typeId;
	}
	public void setTypeId(Integer typeId) {
		this.typeId = typeId;
	}
	
}
