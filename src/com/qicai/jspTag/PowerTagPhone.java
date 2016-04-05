package com.qicai.jspTag;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import com.qicai.controller.BaseController;
import com.qicai.dto.admin.AdminUserDTO;
import com.qicai.dto.admin.RoleManagerDTO;

import freemarker.template.utility.StringUtil;

/**
 * 权限控制标签
 */
public class PowerTagPhone extends TagSupport {
	private static final long serialVersionUID = 1L;
	private int[] roleId;// 绑定角色
	private String phone;

	@Override
	public int doEndTag() throws JspException {
		HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
		AdminUserDTO user = (AdminUserDTO) request.getSession().getAttribute(BaseController.ADMIN_USER_SESSION);// 获取userSession
		boolean isOk = false;
		if (roleId != null) {// 验证角色
			if (user.getRoles() != null) {
				for (RoleManagerDTO temp : user.getRoles()) {
					for(Integer roleIdTemp:getRoleId())
					if (temp.getRoleId()==roleIdTemp) {
						isOk = true;
						break;
					}
				}
			}
		}
		if (isOk) {
			try {
				pageContext.getOut().print(phone);
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
				try {
					pageContext.getOut().print(format(phone));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}

		return super.doEndTag();
	}

	private String format(String phone) {
		String phoneTemp = "";
		if (phone.length() > 3 && phone.length() < 11) {
			phoneTemp = phone.substring(0, 3);
			phoneTemp = StringUtil.rightPad(phoneTemp, phone.length(), "*");
		} else if (phone.length() >= 11) {
			phoneTemp = phone.substring(0, 3);
			phoneTemp = StringUtil.rightPad(phoneTemp, phone.length() - 4, "*");
			phoneTemp += phone.substring(phone.length() - 4);
		}
		return phoneTemp;
	}

	

	public int[] getRoleId() {
		return roleId;
	}

	public void setRoleId(int[] roleId) {
		this.roleId = roleId;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

}
