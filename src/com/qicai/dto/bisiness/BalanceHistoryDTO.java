package com.qicai.dto.bisiness;

import java.util.Date;

import com.qicai.dto.admin.AdminUserDTO;

/**
 * �˻��䶯��ʷ��¼
 * @author Administrator
 *
 */
public class BalanceHistoryDTO {
	private String historyId;
	private StoreDTO store;//�̻�
	private Integer type;//0-��ֵ��1-����
	private Integer value;//�˻�ֵ
	private Integer orderId;//����ID��
	private String message;//���ѱ�ע
	private AdminUserDTO createUser;//������
	private Date createDate;//����ʱ��
	public String getHistoryId() {
		return historyId;
	}
	public void setHistoryId(String historyId) {
		this.historyId = historyId;
	}
	
	public StoreDTO getStore() {
		return store;
	}
	public void setStore(StoreDTO store) {
		this.store = store;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public Integer getValue() {
		return value;
	}
	public void setValue(Integer value) {
		this.value = value;
	}
	
	public AdminUserDTO getCreateUser() {
		return createUser;
	}
	public void setCreateUser(AdminUserDTO createUser) {
		this.createUser = createUser;
	}
	public Integer getOrderId() {
		return orderId;
	}
	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	
}
