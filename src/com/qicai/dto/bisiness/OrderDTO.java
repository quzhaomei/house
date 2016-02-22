package com.qicai.dto.bisiness;

import java.util.Date;

import com.qicai.bean.admin.AdminUser;
import com.qicai.dto.admin.AdminUserDTO;

/**
 * ��������
 */
public class OrderDTO {
	private Integer orderId;
	private Integer type;//1-���ѣ�2-����
	private Integer status;//0 �����У�1-�����ѽ���
	private Date createDate;
	private String operatorLog;
	private RequireDTO require;
	private StoreDTO store;
	private AdminUserDTO createUser;
	
	private String zonename;
	private String typename;
	private Integer value;
	
	
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
	public Integer getValue() {
		return value;
	}
	public void setValue(Integer value) {
		this.value = value;
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
	
}
