package com.qicai.controller.minisite;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.qicai.bean.minisite.DesignApply;
import com.qicai.controller.BaseController;
import com.qicai.dto.JsonDTO;
import com.qicai.util.CookieUtil;
import com.qicai.util.JSONUtil;
import com.qicai.util.RequestUtil;
/**
 * 活动专用controller
 * 
 * @author Administrator
 */
@Controller
@RequestMapping(value = "minisite")
public class MinisiteController extends BaseController {
	private static final String SOURCE_TAG="designApply_souce";
	@RequestMapping(value = "/designApply_to.html")
	public String designApply_to(HttpServletRequest request, HttpServletResponse response, Model model) {
		String source=request.getParameter("source");
		if(source!=null&&source.matches("\\d+")){
			CookieUtil.addCookie(response, SOURCE_TAG, source, -1);
		}
		if(RequestUtil.isMobileDevice(request)){
			return "minisite/designApply_mb";
		}
		return "minisite/designApply_pc";
	}
	
	@RequestMapping(value = "/designApply.html")
	public String designApply(HttpServletRequest request, HttpServletResponse response, Model model) {
		String userphone=request.getParameter("userphone");
		String username=request.getParameter("username");
		String address=request.getParameter("address");
		DesignApply designApply=new DesignApply();
		JsonDTO json=new JsonDTO();
		if(username==null){
			json.setStatus(0).setMessage("请输入您的姓名");
		}else if(userphone==null){
			json.setStatus(0).setMessage("请输入您的电话号码");
		}
		//绑定渠道编号
		String source=CookieUtil.getCookieValueByName(request, SOURCE_TAG);
		if(source!=null&&source.matches("\\d+")){
			designApply.setSource(Integer.parseInt(source));
		}
		
		designApply.setPhone(userphone);
		DesignApply old=applyService.getByParam(designApply);
		if(old!=null){
			json.setStatus(0).setMessage("该电话号码已经报名过，请勿重复报名");
		}else{
			designApply.setCreateDate(new Date());
			designApply.setUsername(username);
			designApply.setLocation(address);
			designApply.setStatus(0);
			try {
				applyService.save(designApply);
				json.setStatus(1).setMessage("报名成功");
			} catch (Exception e) {
				e.printStackTrace();
				json.setStatus(0).setMessage("报名时，后台出现异常，请稍后操作");
			}
		}
			model.addAttribute("json", JSONUtil.object2json(json));
		return JSON;
	}
}
