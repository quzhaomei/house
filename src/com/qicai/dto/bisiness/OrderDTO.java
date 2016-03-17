package com.qicai.dto.bisiness;

import java.util.Date;

import com.qicai.bean.admin.AdminUser;
import com.qicai.dto.admin.AdminUserDTO;

/**
 * 订单管理
 */
public class OrderDTO {
	private Integer orderId;
	private Integer type;//1-消费，2-赠送
	private Integer status;//0 分配中，1-店铺已接收
	private Date createDate;
	private String operatorLog;
	private RequireDTO require;
	private StoreDTO store;
	private AdminUserDTO createUser;
	
	private String zonename;
	private String typename;
	private Integer price;//订单价格
	
	private Integer isSuccess;//是否量房成功  0-未成功，1-已成功
	private String remarks;//量房备注
	public String getZonename() {
		return zonename;
	}
	public void setZonename(String zonename) {
		this.zonename = zonename;
	}
	public String getTypename() {
		return typename;
	}
	public void setTypename(String typename) {
		this.typename = typename;
	}
	
	public Integer getPrice() {
		return price;
	}
	public void setPrice(Integer price) {
		this.price = price;
	}
	public Integer getOrderId() {
		return orderId;
	}
	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}
	public StoreDTO getStore() {
		return store;
	}
	public void setStore(StoreDTO store) {
		this.store = store;
	}
	public RequireDTO getRequire() {
		return require;
	}
	public void setRequire(RequireDTO require) {
		this.require = require;
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
	
	public AdminUserDTO getCreateUser() {
		return createUser;
	}
	public void setCreateUser(AdminUserDTO createUser) {
		this.createUser = createUser;
	}
	public String getOperatorLog() {
		return operatorLog;
	}
	public void setOperatorLog(String operatorLog) {
		this.operatorLog = operatorLog;
	}
	public Integer getIsSuccess() {
		return isSuccess;
	}
	public void setIsSuccess(Integer isSuccess) {
		this.isSuccess = isSuccess;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	
}
