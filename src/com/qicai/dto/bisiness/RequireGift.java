package com.qicai.dto.bisiness;

import java.util.Date;

/**
 * 量房成功礼物
 * @author quzhaomei
 */
public class RequireGift {
	private Integer giftId;
	private Integer requiredId;
	private String address;//地址
	private Integer status;//0-配送中
	private Date createDate;
	public Integer getGiftId() {
		return giftId;
	}
	public void setGiftId(Integer giftId) {
		this.giftId = giftId;
	}
	public Integer getRequiredId() {
		return requiredId;
	}
	public void setRequiredId(Integer requiredId) {
		this.requiredId = requiredId;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
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
	
}
