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
 * 权限拦截
 */
public class LimitInterceptor extends HandlerInterceptorAdapter {
	@Resource
	private OpenIdUtil openIdUtil;
	@Resource
	protected WechatUserService wechatUserService;// 微信用户
	private Logger logger = Logger.getLogger(this.getClass());

	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		//微信登陆拦截
		if (handler.getClass().getAnnotation(WechatTag.class) != null) {
			String url = request.getRequestURL().toString();
			String queryString = request.getQueryString();
			WechatUser loginUser = (WechatUser) request.getSession()
					.getAttribute(BaseController.USER_SESSION);
			String code = request.getParameter("code");
			String state = request.getParameter("state");
			// 没有登录就授权
			if (loginUser == null && code == null && state == null) {
				if (queryString != null) {
					url = url + "?" + queryString + "&" + new Date().getTime();
				} else {
					url = url + "?" + new Date().getTime();
				}
				String rediUrl = openIdUtil.getCodeUrl(url);
				response.sendRedirect(rediUrl);// 重定向获取信息
				logger.info("重定向到："+rediUrl);
				return false;
			} else if (loginUser == null && code != null) {// 重定向回来的链接，则进行用户数据拉取
				Map<String, Object> jsonMap = openIdUtil.getObjByCode(code);
				String openId_temp = (String) jsonMap.get("openid");
				String access_token = (String) jsonMap.get("access_token");
				// 先查看用户注册没有。
				WechatUser wechatUser = wechatUserService
						.getWechatUserByOpenId(openId_temp);
				if (wechatUser == null) {
					wechatUser = openIdUtil.getUserByTokenAndUser(access_token,
							openId_temp);
					wechatUser.setOpenid(openId_temp);// 初始化openId 为了防止没有授权
					wechatUserService.saveWechatUser(wechatUser);

					request.getSession().setAttribute(
							BaseController.USER_SESSION, wechatUser);
				} else {
					request.getSession().setAttribute(
							BaseController.USER_SESSION, wechatUser);// 保存session

					if (wechatUser.getHeadimgurl() == null
							|| "".equals(wechatUser.getHeadimgurl())) {
						// 查看有头像信息不，没有再请求一次
						wechatUser = openIdUtil.getUserByTokenAndUser(
								access_token, openId_temp);
						if (wechatUser.getHeadimgurl() != null
								&& wechatUser.getOpenid() != null) {// 正确返回
							wechatUserService.update(wechatUser);// 更新数据
						}

					}
				}

			}
			/*
			 * else {// 本地环境测试 loginUser = new WechatUser();
			 * loginUser.setCreateDate(new Date());
			 * request.getSession().setAttribute(BaseController.USER_SESSION,
			 * loginUser);// 保存session }
			 */

		}
		request.setAttribute("pageUri", request.getRequestURI());// 页面uri绑定
		if (handler.getClass().getAnnotation(LimitTag.class) != null) {
			LimitTag tag = handler.getClass().getAnnotation(LimitTag.class);
			boolean adminLogin = tag.adminLogin();// 拦截后台登录
			boolean uri = tag.uri();// 拦截uri
			if (adminLogin) {// 拦截后台登录
				AdminUserDTO adminUser = (AdminUserDTO) request.getSession()
						.getAttribute(BaseController.ADMIN_USER_SESSION);
				if (adminUser != null) {
					String godPhone = request.getSession().getServletContext()
							.getInitParameter("godPhone");
					if (uri
							&& (adminUser.getPhone() == null || !adminUser
									.getPhone().equals(godPhone))) {// 如果不为超级管理员，则拦截uri
						String uriStr = request.getRequestURI();// 获取uri
						if (uriStr.matches(".*/.+/.+")) {// 验证uri格式
							uriStr = uriStr.substring(uriStr.lastIndexOf("/",
									uriStr.lastIndexOf("/") - 1) + 1);
							// 得到绝对路径
							Map<String, MenuManagerDTO> menus = adminUser
									.getMenuList();
							if (menus != null && menus.get(uriStr) != null) {
								return true;
							}
						}
						// 没有权限。
						String requestType = request
								.getHeader("X-Requested-With");
						if (requestType != null) {
							// ajax请求
							JsonDTO json = new JsonDTO();
							json.setStatus(2).setMessage("无权限");//
							response.getOutputStream().write(
									JSONUtil.object2json(json)
											.getBytes("utf-8"));
						} else {
						//	String servletName = request.getSession().getServletContext().getServletContextName();
							response.sendRedirect(
									"/welcome/index.html");// 首页
						}

						return false;
					}
					return true;
				} else {
					// 没有权限。
					String requestType = request.getHeader("X-Requested-With");
					if (requestType != null) {
						// ajax请求
						JsonDTO json = new JsonDTO();
						json.setStatus(3).setMessage("请先登录");// 未登录
						response.getOutputStream().write(
								JSONUtil.object2json(json).getBytes("utf-8"));
					} else {
						//String servletName = request.getSession().getServletContext().getServletContextName();
						response.sendRedirect("/login.html");// 重新登录
					}
					return false;
				}
			}
		}
		return true;
	}
}
