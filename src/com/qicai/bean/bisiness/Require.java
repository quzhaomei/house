package com.qicai.bean.bisiness;

import java.util.Date;

/**
 * �û����� 
 */
public class Require {
	private Integer requiredId;//����ID
	
	private Integer userId;//�û�Ψһ��ʶ
	private String username;//�û���  20
	private String userphone;//�绰����  20
	private Integer budget;//Ԥ��
	
	/**start-�ͻ����Ը��ĵ���Ϣ*/
	private Integer houseTypeId;//����ID��
	private Integer zoneId;//������������
	private String houseDes;//��������
	private Integer isNew;//�Ƿ��·�
	private String houseInfo;//¥����Ϣ
	private Integer isReady;//�Ƿ񽻷�
	private Date readyDate;//����ʱ��
	private String houseLocation;//����λ��
	private String designTime;//ԤԼʱ��
	private String phoneTime;//�绰ԤԼʱ��
	private String customerTips;//�ͻ���ע
	/**start-�ͻ����Ը��ĵ���Ϣ*/
	/**start-�ͻ����ļ�¼*/
	private Integer houseTypeIdBak;//����ID��
	private Integer zoneIdBak;//������������
	private String houseDesBak;//�������
	private Integer isNewBak;//�Ƿ��·�
	private String houseInfoBak;//¥����Ϣ
	private Integer isReadyBak;//�Ƿ񽻷�
	private Date readyDateBak;//����ʱ��
	private String houseLocationBak;//����λ��
	private String designTimeBak;//ԤԼʱ��
	private String phoneTimeBak;//�绰ԤԼʱ��
	private String customerTipsBak;//�ͻ���ע
	private Integer budgetBak;//Ԥ��
	/**start-�ͻ����Ը��ĵ���Ϣ*/
	
	
	private Date createDate;//����ʱ��
	private Integer createUserId;//������Ա
	
	private Date serviceDate;//������Ӫʱ��
	private Integer serviceUserId;//�������Ӫ��ԱID
	private String callbackTips;//�طñ�ע
	private String serviceTips;//��Ӫ��ע
	/**
	 * 0-����״̬��1-�����У�2-�ͻ������ӣ�3-�ͻ��޸��ύ��4-ȷ����ϴ�������
	 * 
	 * 6-���ֵ�
	 * 
	 * 7-���ɵ�
	 * 
	 * 8-���ɵ�
	 */
	private Integer status;
	private String operatorLog;//������¼��
	
	
	/**��ѯ��������*/
	private Date startDate;
	private Date endDate;
	private Date serviceStartDate;
	private Date serviceEndDate;
	
	
	public Integer getRequiredId() {
		return requiredId;
	}
	public void setRequiredId(Integer requiredId) {
		this.requiredId = requiredId;
	}
	
	public Integer getBudgetBak() {
		return budgetBak;
	}
	public void setBudgetBak(Integer budgetBak) {
		this.budgetBak = budgetBak;
	}
	public Integer getBudget() {
		return budget;
	}
	public void setBudget(Integer budget) {
		this.budget = budget;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getUserphone() {
		return userphone;
	}
	public void setUserphone(String userphone) {
		this.userphone = userphone;
	}
	public Integer getHouseTypeId() {
		return houseTypeId;
	}
	public void setHouseTypeId(Integer houseTypeId) {
		this.houseTypeId = houseTypeId;
	}
	
	public String getHouseDes() {
		return houseDes;
	}
	public void setHouseDes(String houseDes) {
		this.houseDes = houseDes;
	}
	public Integer getIsNew() {
		return isNew;
	}
	public void setIsNew(Integer isNew) {
		this.isNew = isNew;
	}
	public String getHouseInfo() {
		return houseInfo;
	}
	public void setHouseInfo(String houseInfo) {
		this.houseInfo = houseInfo;
	}
	public Integer getZoneId() {
		return zoneId;
	}
	public void setZoneId(Integer zoneId) {
		this.zoneId = zoneId;
	}
	public Integer getIsReady() {
		return isReady;
	}
	public void setIsReady(Integer isReady) {
		this.isReady = isReady;
	}
	public Date getReadyDate() {
		return readyDate;
	}
	public void setReadyDate(Date readyDate) {
		this.readyDate = readyDate;
	}
	public String getHouseLocation() {
		return houseLocation;
	}
	public void setHouseLocation(String houseLocation) {
		this.houseLocation = houseLocation;
	}
	public String getDesignTime() {
		return designTime;
	}
	public void setDesignTime(String designTime) {
		this.designTime = designTime;
	}
	public String getPhoneTime() {
		return phoneTime;
	}
	public void setPhoneTime(String phoneTime) {
		this.phoneTime = phoneTime;
	}
	public String getCustomerTips() {
		return customerTips;
	}
	public void setCustomerTips(String customerTips) {
		this.customerTips = customerTips;
	}
	public Integer getHouseTypeIdBak() {
		return houseTypeIdBak;
	}
	public void setHouseTypeIdBak(Integer houseTypeIdBak) {
		this.houseTypeIdBak = houseTypeIdBak;
	}
	
	public String getHouseDesBak() {
		return houseDesBak;
	}
	public void setHouseDesBak(String houseDesBak) {
		this.houseDesBak = houseDesBak;
	}
	public Integer getIsNewBak() {
		return isNewBak;
	}
	public void setIsNewBak(Integer isNewBak) {
		this.isNewBak = isNewBak;
	}
	public String getHouseInfoBak() {
		return houseInfoBak;
	}
	public void setHouseInfoBak(String houseInfoBak) {
		this.houseInfoBak = houseInfoBak;
	}
	public Integer getZoneIdBak() {
		return zoneIdBak;
	}
	public void setZoneIdBak(Integer zoneIdBak) {
		this.zoneIdBak = zoneIdBak;
	}
	public Integer getIsReadyBak() {
		return isReadyBak;
	}
	public void setIsReadyBak(Integer isReadyBak) {
		this.isReadyBak = isReadyBak;
	}
	public Date getReadyDateBak() {
		return readyDateBak;
	}
	public void setReadyDateBak(Date readyDateBak) {
		this.readyDateBak = readyDateBak;
	}
	public String getHouseLocationBak() {
		return houseLocationBak;
	}
	public void setHouseLocationBak(String houseLocationBak) {
		this.houseLocationBak = houseLocationBak;
	}
	public String getDesignTimeBak() {
		return designTimeBak;
	}
	public void setDesignTimeBak(String designTimeBak) {
		this.designTimeBak = designTimeBak;
	}
	public String getPhoneTimeBak() {
		return phoneTimeBak;
	}
	public void setPhoneTimeBak(String phoneTimeBak) {
		this.phoneTimeBak = phoneTimeBak;
	}
	public String getCustomerTipsBak() {
		return customerTipsBak;
	}
	public void setCustomerTipsBak(String customerTipsBak) {
		this.customerTipsBak = customerTipsBak;
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
	public Date getServiceDate() {
		return serviceDate;
	}
	public void setServiceDate(Date serviceDate) {
		this.serviceDate = serviceDate;
	}
	public Integer getServiceUserId() {
		return serviceUserId;
	}
	public void setServiceUserId(Integer serviceUserId) {
		this.serviceUserId = serviceUserId;
	}
	public String getCallbackTips() {
		return callbackTips;
	}
	public void setCallbackTips(String callbackTips) {
		this.callbackTips = callbackTips;
	}
	public String getServiceTips() {
		return serviceTips;
	}
	public void setServiceTips(String serviceTips) {
		this.serviceTips = serviceTips;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getOperatorLog() {
		return operatorLog;
	}
	public void setOperatorLog(String operatorLog) {
		this.operatorLog = operatorLog;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
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
	public Date getServiceStartDate() {
		return serviceStartDate;
	}
	public void setServiceStartDate(Date serviceStartDate) {
		this.serviceStartDate = serviceStartDate;
	}
	public Date getServiceEndDate() {
		return serviceEndDate;
	}
	public void setServiceEndDate(Date serviceEndDate) {
		this.serviceEndDate = serviceEndDate;
	}
	public Require(Integer requiredId) {
		super();
		this.requiredId = requiredId;
	}
	public Require() {
	}
}