package com.qicai.bean.bisiness;

import java.util.Date;

/**
 * �˻��䶯��ʷ��¼
 * @author Administrator
 *
 */
public class BalanceHistory {
	private String historyId;
	private Integer storeId;//�̻�ID
	private Integer type;//0-��ֵ��1-����
	private Integer value;//�˻�ֵ
	private Integer orderId;//����ID��
	private String message;//���ѱ�ע
	private Integer createUserId;//������
	private Date createDate;//����ʱ��
	public String getHistoryId() {
		return historyId;
	}
	public void setHistoryId(String historyId) {
		this.historyId = historyId;
	}
	public Integer getStoreId() {
		return storeId;
	}
	public void setStoreId(Integer storeId) {
		this.storeId = storeId;
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
	
	public Integer getCreateUserId() {
		return createUserId;
	}
	public void setCreateUserId(Integer createUserId) {
		this.createUserId = createUserId;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	
}
