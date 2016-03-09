package com.qicai.interceptor;

import java.util.Date;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.log4j.chainsaw.Main;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.qicai.annotation.LimitTag;
import com.qicai.bean.WechatUser;
import com.qicai.controller.BaseController;
import com.qicai.dto.JsonDTO;
import com.qicai.dto.admin.AdminUserDTO;
import com.qicai.dto.admin.MenuManagerDTO;
import com.qicai.service.WechatUserService;
import com.qicai.util.JSONUtil;
import com.qicai.util.OpenIdUtil;

/**
 * Ȩ������
 */
public class LimitInterceptor extends HandlerInterceptorAdapter {
	@Resource
	private OpenIdUtil openIdUtil;
	@Resource
	protected WechatUserService wechatUserService;// ΢���û�
	private Logger logger = Logger.getLogger(this.getClass());

	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		//΢�ŵ�½����
		if (handler.getClass().getAnnotation(WechatTag.class) != null) {
			String url = request.getRequestURL().toString();
			String queryString = request.getQueryString();
			WechatUser loginUser = (WechatUser) request.getSession()
					.getAttribute(BaseController.USER_SESSION);
			String code = request.getParameter("code");
			String state = request.getParameter("state");
			// û�е�¼����Ȩ
			if (loginUser == null && code == null && state == null) {
				if (queryString != null) {
					url = url + "?" + queryString + "&" + new Date().getTime();
				} else {
					url = url + "?" + new Date().getTime();
				}
				String rediUrl = openIdUtil.getCodeUrl(url);
				response.sendRedirect(rediUrl);// �ض����ȡ��Ϣ
				logger.info("�ض��򵽣�"+rediUrl);
				return false;
			} else if (loginUser == null && code != null) {// �ض�����������ӣ�������û�������ȡ
				Map<String, Object> jsonMap = openIdUtil.getObjByCode(code);
				String openId_temp = (String) jsonMap.get("openid");
				String access_token = (String) jsonMap.get("access_token");
				// �Ȳ鿴�û�ע��û�С�
				WechatUser wechatUser = wechatUserService
						.getWechatUserByOpenId(openId_temp);
				if (wechatUser == null) {
					wechatUser = openIdUtil.getUserByTokenAndUser(access_token,
							openId_temp);
					wechatUser.setOpenid(openId_temp);// ��ʼ��openId Ϊ�˷�ֹû����Ȩ
					wechatUserService.saveWechatUser(wechatUser);

					request.getSession().setAttribute(
							BaseController.USER_SESSION, wechatUser);
				} else {
					request.getSession().setAttribute(
							BaseController.USER_SESSION, wechatUser);// ����session

					if (wechatUser.getHeadimgurl() == null
							|| "".equals(wechatUser.getHeadimgurl())) {
						// �鿴��ͷ����Ϣ����û��������һ��
						wechatUser = openIdUtil.getUserByTokenAndUser(
								access_token, openId_temp);
						if (wechatUser.getHeadimgurl() != null
								&& wechatUser.getOpenid() != null) {// ��ȷ����
							wechatUserService.update(wechatUser);// ��������
						}

					}
				}

			}
			/*
			 * else {// ���ػ������� loginUser = new WechatUser();
			 * loginUser.setCreateDate(new Date());
			 * request.getSession().setAttribute(BaseController.USER_SESSION,
			 * loginUser);// ����session }
			 */

		}
		request.setAttribute("pageUri", request.getRequestURI());// ҳ��uri��
		if (handler.getClass().getAnnotation(LimitTag.class) != null) {
			LimitTag tag = handler.getClass().getAnnotation(LimitTag.class);
			boolean adminLogin = tag.adminLogin();// ���غ�̨��¼
			boolean uri = tag.uri();// ����uri
			if (adminLogin) {// ���غ�̨��¼
				AdminUserDTO adminUser = (AdminUserDTO) request.getSession()
						.getAttribute(BaseController.ADMIN_USER_SESSION);
				if (adminUser != null) {
					String godPhone = request.getSession().getServletContext()
							.getInitParameter("godPhone");
					if (uri
							&& (adminUser.getPhone() == null || !adminUser
									.getPhone().equals(godPhone))) {// �����Ϊ��������Ա��������uri
						String uriStr = request.getRequestURI();// ��ȡuri
						if (uriStr.matches(".*/.+/.+")) {// ��֤uri��ʽ
							uriStr = uriStr.substring(uriStr.lastIndexOf("/",
									uriStr.lastIndexOf("/") - 1) + 1);
							// �õ�����·��
							Map<String, MenuManagerDTO> menus = adminUser
									.getMenuList();
							if (menus != null && menus.get(uriStr) != null) {
								return true;
							}
						}
						// û��Ȩ�ޡ�
						String requestType = request
								.getHeader("X-Requested-With");
						if (requestType != null) {
							// ajax����
							JsonDTO json = new JsonDTO();
							json.setStatus(2).setMessage("��Ȩ��");//
							response.getOutputStream().write(
									JSONUtil.object2json(json)
											.getBytes("utf-8"));
						} else {
						//	String servletName = request.getSession().getServletContext().getServletContextName();
							response.sendRedirect(
									"/welcome/index.html");// ��ҳ
						}

						return false;
					}
					return true;
				} else {
					// û��Ȩ�ޡ�
					String requestType = request.getHeader("X-Requested-With");
					if (requestType != null) {
						// ajax����
						JsonDTO json = new JsonDTO();
						json.setStatus(3).setMessage("���ȵ�¼");// δ��¼
						response.getOutputStream().write(
								JSONUtil.object2json(json).getBytes("utf-8"));
					} else {
						//String servletName = request.getSession().getServletContext().getServletContextName();
						response.sendRedirect("/login.html");// ���µ�¼
					}
					return false;
				}
			}
		}
		return true;
	}
}
