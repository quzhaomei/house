package com.qicai.controller.wechat;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.qicai.controller.BaseController;
import com.qicai.interceptor.WechatTag;


/**
 * н╒пе
 * 
 * @author Administrator
 */
@RequestMapping(value = "/wechat")
@Controller
@WechatTag
public class WechatController extends BaseController {

	protected Logger logger = Logger.getLogger(this.getClass());

	@RequestMapping(value = "/login")
	public String index(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		
		return "wechat/index";
	}

}
