package com.qicai.bean.bisiness;

import java.util.Date;

/**
 * 用户需求 
 */
public class Require {
	private Integer requiredId;//需求ID
	
	private Integer userId;//用户唯一标识
	private String username;//用户名  20
	private String userphone;//电话号码  20
	
	/**start-客户可以更改的信息*/
	private String budget;//预算
	private Integer houseTypeId;//房型ID；
	private Integer zoneId;//房子所属区域
	private String houseDes;//房子描述
	private Integer isNew;//是否新房
	private String houseInfo;//楼盘信息
	private Integer isReady;//是否交房
	private Date readyDate;//交房时间
	private String houseLocation;//房屋位置
	private String designTime;//预约时间
	private String phoneTime;//电话预约时间
	private String customerTips;//客户备注
	//新增
	private String designType;//装修方式
	private String designStyle;//装修风格
	private String houseStatus;//房屋状态 毛坯房、老房翻新、局部装修、工装
	/**start-客户可以更改的信息*/
	/**start-客户更改记录*/
	private Integer houseTypeIdBak;//房型ID；
	private Integer zoneIdBak;//房子所属区域
	private String houseDesBak;//房子面积
	private Integer isNewBak;//是否新房
	private String houseInfoBak;//楼盘信息
	private Integer isReadyBak;//是否交房
	private Date readyDateBak;//交房时间
	private String houseLocationBak;//房屋位置
	private String designTimeBak;//预约时间
	private String phoneTimeBak;//电话预约时间
	private String customerTipsBak;//客户备注
	private String budgetBak;//预算
	
	private String designTypeBak;//装修方式
	private String designStyleBak;//装修风格
	private String houseStatusBak;//房屋状态 毛坯房、老房翻新、局部装修、工装
	
	/**start-客户可以更改的信息*/
	
	private Integer price;//自定义价格
	
	private Date createDate;//生产时间
	private Integer createUserId;//生成人员
	
	private Date serviceDate;//分配运营时间
	private Integer serviceUserId;//分配的运营人员ID
	private String callbackTips;//回访备注
	private String serviceTips;//运营备注
	/**
	 * 0-发起状态，1-短信中，2-客户打开连接，3-客户修改提交，4-确认完毕待发布，
	 * 
	 * 6-待分单
	 * 
	 * 7-待派单
	 * 
	 * 8-已派单
	 * 
	 * 
	 * 40关闭，41待跟进库
	 */
	
	private Integer status;
	private String operatorLog;//操作记录，
	
	/**新字段*/
	private Date nextCallTime;//下次回访的时间
	private Date fileTime;//归档时间
	
	
	
	/**查询辅助字段*/
	private Integer acceptNum;//已接单数目
	
	private Integer specialStatus;//特殊状态
	
	
	public Integer getAcceptNum() {
		return acceptNum;
	}
	public void setAcceptNum(Integer acceptNum) {
		this.acceptNum = acceptNum;
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
	/**查询辅助条件*/
	private Date startDate;
	private Date endDate;
	private Date serviceStartDate;
	private Date serviceEndDate;
	
	private Date startFileTime;
	private Date endFileTime;
	
	private Date startNextCallTime;
	private Date endNextCallTime;
	
	private Date specialStartDate;
	private Date specialEndDate;
	
	
	public Date getStartFileTime() {
		return startFileTime;
	}
	public void setStartFileTime(Date startFileTime) {
		this.startFileTime = startFileTime;
	}
	public Date getEndFileTime() {
		return endFileTime;
	}
	public void setEndFileTime(Date endFileTime) {
		this.endFileTime = endFileTime;
	}
	public Integer getRequiredId() {
		return requiredId;
	}
	public void setRequiredId(Integer requiredId) {
		this.requiredId = requiredId;
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
	public Date getStartNextCallTime() {
		return startNextCallTime;
	}
	public void setStartNextCallTime(Date startNextCallTime) {
		this.startNextCallTime = startNextCallTime;
	}
	public Date getEndNextCallTime() {
		return endNextCallTime;
	}
	
	public Integer getSpecialStatus() {
		return specialStatus;
	}
	public void setSpecialStatus(Integer specialStatus) {
		this.specialStatus = specialStatus;
	}
	
	public Date getSpecialStartDate() {
		return specialStartDate;
	}
	public void setSpecialStartDate(Date specialStartDate) {
		this.specialStartDate = specialStartDate;
	}
	public Date getSpecialEndDate() {
		return specialEndDate;
	}
	public void setSpecialEndDate(Date specialEndDate) {
		this.specialEndDate = specialEndDate;
	}
	public void setEndNextCallTime(Date endNextCallTime) {
		this.endNextCallTime = endNextCallTime;
	}
	public Require() {
	}
	public Integer getPrice() {
		return price;
	}
	public void setPrice(Integer price) {
		this.price = price;
	}
	
}