package com.qicai.dto.bisiness;

import java.util.Date;
import java.util.List;

import com.qicai.dto.admin.AdminUserDTO;

/**
 * �������̹���
 */
public class StoreDTO {
	private Integer storeId;
	
	private Integer balance;//���
	private List<ZoneSetDTO> orderZones;//��������
	private List<HouseTypeDTO> houseTypes;//��������
	private AdminUserDTO keeper;//������
	private ZoneSetDTO zone;//��������
	private String logo;
	
	
	private String storePhone;//���̵绰
	private String storeName;//����
	private String storeAddress;//���̵�ַ
	private String callPhone;//�Խӵ绰
	private String msgPhone;//���ŵ绰
	private Integer size;//ÿ�½ӵ���
	private Integer hasSize;//ÿ���ѽӵ���
	
	
	private String companyName;//��˾����
	private String ruleUserName;//��������
	private String ruleUserPhone;//���˵绰
	
	
	
	
	private Integer status;//1-�ӵ���0-��ͣ
	private AdminUserDTO createUser;
	private Date createDate;
	private Integer updateUser;
	private Date updateDate;
	
	private String remarks;//��ע
	public List<HouseTypeDTO> getHouseTypes() {
		return houseTypes;
	}
	public void setHouseTypes(List<HouseTypeDTO> houseTypes) {
		this.houseTypes = houseTypes;
	}
	public AdminUserDTO getKeeper() {
		return keeper;
	}
	public void setKeeper(AdminUserDTO keeper) {
		this.keeper = keeper;
	}
	public ZoneSetDTO getZone() {
		return zone;
	}
	public void setZone(ZoneSetDTO zone) {
		this.zone = zone;
	}
	public List<ZoneSetDTO> getOrderZones() {
		return orderZones;
	}
	public void setOrderZones(List<ZoneSetDTO> orderZones) {
		this.orderZones = orderZones;
	}
	public Integer getHasSize() {
		return hasSize;
	}
	public void setHasSize(Integer hasSize) {
		this.hasSize = hasSize;
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
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	
	public AdminUserDTO getCreateUser() {
		return createUser;
	}
	public void setCreateUser(AdminUserDTO createUser) {
		this.createUser = createUser;
	}
	public Integer getUpdateUser() {
		return updateUser;
	}
	public void setUpdateUser(Integer updateUser) {
		this.updateUser = updateUser;
	}
	public Date getCreateDate() {
		return createDate;
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
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	
	
}
