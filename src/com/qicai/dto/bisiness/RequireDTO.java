package com.qicai.dto.bisiness;

import java.util.Date;

import com.qicai.dto.admin.AdminUserDTO;

/**
 * 用户需求 DTO
 */
public class RequireDTO {
	private Integer requiredId;//需求ID
	
	private Integer userId;//用户唯一标识
	private String username;//用户名  20
	private String userphone;//电话号码  20
	private String budget;//预算
	
	/**start-客户可以更改的信息*/
	private HouseTypeDTO houseType;//房型ID；
	private ZoneSetDTO zone;//房子所属区域
	private String houseDes;//房子描述
	private Integer isNew;//是否新房
	private String houseInfo;//楼盘信息
	private Integer isReady;//是否交房
	private Date readyDate;//交房时间
	private String houseLocation;//房屋位置
	private String designTime;//预约时间
	private String phoneTime;//电话预约时间
	private String customerTips;//客户备注
	
	private String designType;//装修方式
	private String designStyle;//装修风格
	private String houseStatus;//房屋状态 毛坯房、老房翻新、局部装修、工装
	/**start-客户可以更改的信息*/
	/**start-客户更改记录*/
	private String budgetBak;//预算
	private HouseTypeDTO houseTypeBak;//房型ID；
	private ZoneSetDTO zoneBak;//房子所属区域
	
	private Integer houseDesBak;//房子面积
	private Integer isNewBak;//是否新房
	private String houseInfoBak;//楼盘信息
	private Integer isReadyBak;//是否交房
	private Date readyDateBak;//交房时间
	private String houseLocationBak;//房屋位置
	private String designTimeBak;//预约时间
	private String phoneTimeBak;//电话预约时间
	private String customerTipsBak;//客户备注
	
	private String designTypeBak;//装修方式
	private String designStyleBak;//装修风格
	private String houseStatusBak;//房屋状态 毛坯房、老房翻新、局部装修、工装
	/**start-客户可以更改的信息*/
	
	private Integer orderCount;//派单数目
	
	
	private Date createDate;//生产时间
	private AdminUserDTO createUser;//生成人员
	
	private Date serviceDate;//业务分配运营时间
	private AdminUserDTO serviceUser;//分配的运营人员ID
	private String callbackTips;//回访备注
	private String serviceTips;//运营备注
	
	/**
	 * 0-发起状态，1-短信中，2-客户打开连接，3-客户修改提交，4-确认完毕待发布，
	 * 
	 * 6-待分单
	 * 
	 * 7-待派单
	 * 40关闭，41待跟进库
	 * 8-已派单
	 */
	
	private Date nextCallTime;//下次回访的时间
	private String nextCallTimeStr;
	private Date fileTime;//归档时间
	
	
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
	private String operatorLog;//操作记录，
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