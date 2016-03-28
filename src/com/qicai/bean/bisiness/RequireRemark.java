package com.qicai.bean.bisiness;

import java.util.Date;

/**
 * 需求备注 - 临时表
 * @author quzhaomei
 */
public class RequireRemark {
	private Integer remarkId;
	private Integer requiredId;
	private String remark;
	private Date nextTime;
	private Date createDate;
	private Integer status;//0-关闭，1-待跟进库
	public Integer getRemarkId() {
		return remarkId;
	}
	public void setRemarkId(Integer remarkId) {
		this.remarkId = remarkId;
	}
	public Integer getRequiredId() {
		return requiredId;
	}
	public void setRequiredId(Integer requiredId) {
		this.requiredId = requiredId;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Date getNextTime() {
		return nextTime;
	}
	public void setNextTime(Date nextTime) {
		this.nextTime = nextTime;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	
}
