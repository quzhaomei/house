package com.qicai.controller.admin;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.qicai.annotation.LimitTag;
import com.qicai.bean.WechatUser;
import com.qicai.controller.BaseController;
import com.qicai.dto.PageDTO;

@Controller
@RequestMapping(value="/wechatUser")
@LimitTag(uri=true)
public class WechatUserController extends BaseController {
	@RequestMapping(value="/index")
	public String index(HttpServletRequest request,
			HttpServletResponse response, Model model){
		String pageIndex=request.getParameter("pageIndex");//页面
		String pageSize=request.getParameter("pageSize");//页数量
		
		int pageIndexc=pageIndex==null||"".equals(pageIndex.trim())?1:Integer.parseInt(pageIndex);
		int pageSizec=pageSize==null||"".equals(pageSize.trim())?10:Integer.parseInt(pageSize);
		
		PageDTO<WechatUser> pageParam=new PageDTO<WechatUser>();
		WechatUser wechatUser=new WechatUser();
		
		pageParam.setParam(wechatUser);
		pageParam.setPageIndex(pageIndexc);
		pageParam.setPageSize(pageSizec);
		
		PageDTO<List<WechatUser>> pageResult=wechatUserService.getListByParam(pageParam);
		//分页获取用户
		model.addAttribute("pageResult", pageResult);
		return "admin/wechatUserManager";
	}
	/**
	 * 查找所有用户
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/list")
	public String list(HttpServletRequest request,
			HttpServletResponse response, Model model){
	
		return null;
	}
	/**
	 * 进行用户的更新
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/saveOrUpdate")
	public String saveOrUpdate(HttpServletRequest request,
			HttpServletResponse response, Model model){
		
		return "json";
	}
}
