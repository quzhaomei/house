package com.qicai.dto.bisiness;

import java.util.Date;

import com.qicai.dto.admin.AdminUserDTO;

/**
 * �û����� DTO
 */
public class RequireDTO {
	private Integer requiredId;//����ID
	
	private Integer userId;//�û�Ψһ��ʶ
	private String username;//�û���  20
	private String userphone;//�绰����  20
	private String budget;//Ԥ��
	
	/**start-�ͻ����Ը��ĵ���Ϣ*/
	private HouseTypeDTO houseType;//����ID��
	private ZoneSetDTO zone;//������������
	private String houseDes;//��������
	private Integer isNew;//�Ƿ��·�
	private String houseInfo;//¥����Ϣ
	private Integer isReady;//�Ƿ񽻷�
	private Date readyDate;//����ʱ��
	private String houseLocation;//����λ��
	private String designTime;//ԤԼʱ��
	private String phoneTime;//�绰ԤԼʱ��
	private String customerTips;//�ͻ���ע
	
	private String designType;//װ�޷�ʽ
	private String designStyle;//װ�޷��
	private String houseStatus;//����״̬ ë�������Ϸ����¡��ֲ�װ�ޡ���װ
	/**start-�ͻ����Ը��ĵ���Ϣ*/
	/**start-�ͻ����ļ�¼*/
	private String budgetBak;//Ԥ��
	private HouseTypeDTO houseTypeBak;//����ID��
	private ZoneSetDTO zoneBak;//������������
	
	private Integer houseDesBak;//�������
	private Integer isNewBak;//�Ƿ��·�
	private String houseInfoBak;//¥����Ϣ
	private Integer isReadyBak;//�Ƿ񽻷�
	private Date readyDateBak;//����ʱ��
	private String houseLocationBak;//����λ��
	private String designTimeBak;//ԤԼʱ��
	private String phoneTimeBak;//�绰ԤԼʱ��
	private String customerTipsBak;//�ͻ���ע
	
	private String designTypeBak;//װ�޷�ʽ
	private String designStyleBak;//װ�޷��
	private String houseStatusBak;//����״̬ ë�������Ϸ����¡��ֲ�װ�ޡ���װ
	/**start-�ͻ����Ը��ĵ���Ϣ*/
	
	private Integer orderCount;//�ɵ���Ŀ
	
	
	private Date createDate;//����ʱ��
	private AdminUserDTO createUser;//������Ա
	
	private Date serviceDate;//ҵ�������Ӫʱ��
	private AdminUserDTO serviceUser;//�������Ӫ��ԱID
	private String callbackTips;//�طñ�ע
	private String serviceTips;//��Ӫ��ע
	
	/**
	 * 0-����״̬��1-�����У�2-�ͻ������ӣ�3-�ͻ��޸��ύ��4-ȷ����ϴ�������
	 * 
	 * 6-���ֵ�
	 * 
	 * 7-���ɵ�
	 * 40�رգ�41��������
	 * 8-���ɵ�
	 */
	
	private Date nextCallTime;//�´λطõ�ʱ��
	private String nextCallTimeStr;
	private Date fileTime;//�鵵ʱ��
	
	
	public String getNextCallTimeStr() {
		return nextCallTimeStr;
	}
	public void setNextCallTimeStr(String nextCallTimeStr) {
		this.nextCallTimeStr = nextCallTimeStr;
	}
	public Date getNextCallTime() {
		return nextCallTime;
	}
	public void setNextCallTime(Date nextCallTime) {
		this.nextCallTime = nextCallTime;
	}
	public Date getFileTime() {
		return fileTime;
	}
	public void setFileTime(Date fileTime) {
		this.fileTime = fileTime;
	}
	private Integer status;
	private String operatorLog;//������¼��
	public Integer getRequiredId() {
		return requiredId;
	}
	public void setRequiredId(Integer requiredId) {
		this.requiredId = requiredId;
	}
	
	
	public String getBudget() {
		return budget;
	}
	public void setBudget(String budget) {
		this.budget = budget;
	}
	public String getBudgetBak() {
		return budgetBak;
	}
	public void setBudgetBak(String budgetBak) {
		this.budgetBak = budgetBak;
	}
	public Integer getHouseDesBak() {
		return houseDesBak;
	}
	public void setHouseDesBak(Integer houseDesBak) {
		this.houseDesBak = houseDesBak;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	
	public Integer getOrderCount() {
		return orderCount;
	}
	public void setOrderCount(Integer orderCount) {
		this.orderCount = orderCount;
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
	
	public HouseTypeDTO getHouseType() {
		return houseType;
	}
	public void setHouseType(HouseTypeDTO houseType) {
		this.houseType = houseType;
	}
	public ZoneSetDTO getZone() {
		return zone;
	}
	public void setZone(ZoneSetDTO zone) {
		this.zone = zone;
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
	
	public HouseTypeDTO getHouseTypeBak() {
		return houseTypeBak;
	}
	public void setHouseTypeBak(HouseTypeDTO houseTypeBak) {
		this.houseTypeBak = houseTypeBak;
	}
	public ZoneSetDTO getZoneBak() {
		return zoneBak;
	}
	public void setZoneBak(ZoneSetDTO zoneBak) {
		this.zoneBak = zoneBak;
	}
	public Integer gethouseDesBak() {
		return houseDesBak;
	}
	public void sethouseDesBak(Integer houseDesBak) {
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
	public AdminUserDTO getCreateUser() {
		return createUser;
	}
	public void setCreateUser(AdminUserDTO createUser) {
		this.createUser = createUser;
	}
	public Date getServiceDate() {
		return serviceDate;
	}
	public void setServiceDate(Date serviceDate) {
		this.serviceDate = serviceDate;
	}
	public AdminUserDTO getServiceUser() {
		return serviceUser;
	}
	public void setServiceUser(AdminUserDTO serviceUser) {
		this.serviceUser = serviceUser;
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
	public String getDesignType() {
		return designType;
	}
	public void setDesignType(String designType) {
		this.designType = designType;
	}
	public String getDesignStyle() {
		return designStyle;
	}
	public void setDesignStyle(String designStyle) {
		this.designStyle = designStyle;
	}
	public String getHouseStatus() {
		return houseStatus;
	}
	public void setHouseStatus(String houseStatus) {
		this.houseStatus = houseStatus;
	}
	public String getDesignTypeBak() {
		return designTypeBak;
	}
	public void setDesignTypeBak(String designTypeBak) {
		this.designTypeBak = designTypeBak;
	}
	public String getDesignStyleBak() {
		return designStyleBak;
	}
	public void setDesignStyleBak(String designStyleBak) {
		this.designStyleBak = designStyleBak;
	}
	public String getHouseStatusBak() {
		return houseStatusBak;
	}
	public void setHouseStatusBak(String houseStatusBak) {
		this.houseStatusBak = houseStatusBak;
	}
	
	
}