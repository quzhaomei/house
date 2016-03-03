package com.qicai.bean.bisiness;

import java.util.Date;

/**
 * 订单管理
 */
public class Order {
	private Integer orderId;
	private Integer storeId;
	private Integer requiredId;
	private Integer type;//1-消费，2-赠送
	private Date createDate;
	private Integer createUserId;
	private String operatorLog;
	
	private Integer status;//0 分配中，1-店铺已接收
	
	
	/**查询辅助条件*/
	private Date startDate;
	private Date endDate;
	
	private String username; //客服名称
	private String storeName;//店铺名称
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getStoreName() {
		return storeName;
	}
	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public Integer getOrderId() {
		return orderId;
	}
	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}
	public Integer getStoreId() {
		return storeId;
	}
	public void setStoreId(Integer storeId) {
		this.storeId = storeId;
	}
	
	public Integer getRequiredId() {
		return requiredId;
	}
	public void setRequiredId(Integer requiredId) {
		this.requiredId = requiredId;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
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
	public String getOperatorLog() {
		return operatorLog;
	}
	public void setOperatorLog(String operatorLog) {
		this.operatorLog = operatorLog;
	}
	
}
