package com.qicai.bean.bisiness;

import java.util.Date;

/**
 * 接单区域设置，上海-虹口区， 默认两级
 * @author Administrator
 *
 */
public class ZoneSet {
	private Integer zoneId;
	private Integer parentId;//父级Id
	
	private String name;//50
	private Date createDate;
	private Integer createUserId;
	private Date updateDate;
	private Integer updateUserId;
	private Integer status;//1：初始化，能用。0-冻结中
	
	public Integer getParentId() {
		return parentId;
	}
	public void setParentId(Integer parentId) {
		this.parentId = parentId;
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
	public Integer getCreateUserId() {
		return createUserId;
	}
	public void setCreateUserId(Integer createUserId) {
		this.createUserId = createUserId;
	}
	public Date getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}
	public Integer getUpdateUserId() {
		return updateUserId;
	}
	public void setUpdateUserId(Integer updateUserId) {
		this.updateUserId = updateUserId;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	
}
