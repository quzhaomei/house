package com.qicai.bean.bisiness;

import java.util.Date;

/**
 * �������̹���
 */
public class Store {
	private Integer storeId;
	
	private Integer balance;//���
	private Integer zoneId;//��������
	private Integer keeperId;//������
	private String logo;
	
	
	private String storePhone;//���̵绰
	private String storeName;//����
	private String storeAddress;//���̵�ַ
	private String callPhone;//�Խӵ绰
	private String msgPhone;//���ŵ绰
	private Integer size;//ÿ�½ӵ���
	
	
	private String companyName;//��˾����
	private String ruleUserName;//��������
	private String ruleUserPhone;//���˵绰
	
	
	private String remarks;//��ע
	private String httpUrl;//������ַ
	
	private Integer status;//1-�ӵ���0-��ͣ
	private Integer createUserId;
	private Date createDate;
	private Integer updateUserId;
	private Date updateDate;
	
	
	
	/**��ѯ�����ֶ�**/
	private String keeperName;
	private Date startDate;
	private Date endDate;
	private Integer serviceTypeId;
	private Integer serviceZoneId;
	
	
	public Integer getServiceTypeId() {
		return serviceTypeId;
	}
	public void setServiceTypeId(Integer serviceTypeId) {
		this.serviceTypeId = serviceTypeId;
	}
	public Integer getServiceZoneId() {
		return serviceZoneId;
	}
	public void setServiceZoneId(Integer serviceZoneId) {
		this.serviceZoneId = serviceZoneId;
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
	public String getKeeperName() {
		return keeperName;
	}
	public void setKeeperName(String keeperName) {
		this.keeperName = keeperName;
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
	public Integer getZoneId() {
		return zoneId;
	}
	public void setZoneId(Integer zoneId) {
		this.zoneId = zoneId;
	}
	public Integer getKeeperId() {
		return keeperId;
	}
	public void setKeeperId(Integer keeperId) {
		this.keeperId = keeperId;
	}
	public String getLogo() {
		return logo;
	}
	public void setLogo(String logo) {
		this.logo = logo;
	}
	
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getStorePhone() {
		return storePhone;
	}
	public void setStorePhone(String storePhone) {
		this.storePhone = storePhone;
	}
	public String getStoreName() {
		return storeName;
	}
	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}
	public String getRuleUserName() {
		return ruleUserName;
	}
	public void setRuleUserName(String ruleUserName) {
		this.ruleUserName = ruleUserName;
	}
	public String getRuleUserPhone() {
		return ruleUserPhone;
	}
	public void setRuleUserPhone(String ruleUserPhone) {
		this.ruleUserPhone = ruleUserPhone;
	}
	public String getCallPhone() {
		return callPhone;
	}
	public void setCallPhone(String callPhone) {
		this.callPhone = callPhone;
	}
	public String getMsgPhone() {
		return msgPhone;
	}
	public void setMsgPhone(String msgPhone) {
		this.msgPhone = msgPhone;
	}
	public String getStoreAddress() {
		return storeAddress;
	}
	public void setStoreAddress(String storeAddress) {
		this.storeAddress = storeAddress;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
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
	public Integer getUpdateUserId() {
		return updateUserId;
	}
	public void setUpdateUserId(Integer updateUserId) {
		this.updateUserId = updateUserId;
	}
	public Date getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}
	public Integer getSize() {
		return size;
	}
	public void setSize(Integer size) {
		this.size = size;
	}
	public Store(Integer storeId) {
		super();
		this.storeId = storeId;
	}
	public Store() {
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getHttpUrl() {
		return httpUrl;
	}
	public void setHttpUrl(String httpUrl) {
		this.httpUrl = httpUrl;
	}
	
}
