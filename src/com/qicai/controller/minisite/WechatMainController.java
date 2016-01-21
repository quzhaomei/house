package com.qicai.controller.minisite;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.qicai.bean.minisite.DesignApply;
import com.qicai.controller.BaseController;
import com.qicai.dto.JsonDTO;
import com.qicai.util.JSONUtil;

/**
 * 活动专用controller
 * @author Administrator
 */
@Controller
@RequestMapping(value="w_m")
public class WechatMainController extends BaseController{
	//量房申请
		@RequestMapping(value = "/designApply.html")
		public String designApply(HttpServletRequest request,
				HttpServletResponse response, Model model) {
			String phone=request.getParameter("phone");
			if(phone!=null&&phone.matches("\\d+")){//更新用户状态
				DesignApply apply=new DesignApply();
				apply.setPhone(phone);
				apply.setStatus(1);
				DesignApply checkDate=applyService.getByParam(apply);//检查数据是否存在
				if(checkDate!=null){//则更新
					String description=checkDate.getDescription();
					description+=" # "+new SimpleDateFormat("yyyy/MM/DD HH:mm").format(new Date())+
							" 打开短信页面浏览";
					checkDate.setDescription(description);
					checkDate.setStatus(2);
					try {
						applyService.update(checkDate);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				
			}
			return "mobile/designApply";
		}
		@RequestMapping(value = "/saveDesignApply")
		public String saveDesignApply(HttpServletRequest request,
				HttpServletResponse response, Model model) {
			String username=request.getParameter("username");
			String phone=request.getParameter("phone");
			String location=request.getParameter("location");
			String size=request.getParameter("size");
			JsonDTO json=new JsonDTO();
			if(username!=null&&username.length()<=20
					&&phone!=null&&phone.matches("\\d+")
					&&location!=null&&location.length()<=100
					&&size!=null&&size.length()<=100){
				DesignApply apply=new DesignApply();
				apply.setPhone(phone);
				DesignApply data=applyService.getByParam(apply);
				if(data!=null){
					//判断是否是重复报名
					if(data.getStatus()!=3){//进行更新
						data.setLocation(location);
						data.setStatus(3);//报名
						data.setSize(size);
						data.setUsername(username);
						data.setDescription(data.getDescription()+" # "+new SimpleDateFormat("yyyy/MM/DD HH:mm").format(new Date())+
							" 报名成功");
						try {
							applyService.update(data);
							json.setStatus(1).setMessage("报名成功！");
						} catch (Exception e) {
							e.printStackTrace();
							json.setStatus(0).setMessage("报名失败，系统异常："+e.getMessage());
						}
						
					}else{
						json.setStatus(0).setMessage("该用户已经报名过了！");
					}
				}else{
					apply.setCreateDate(new Date());
					apply.setUsername(username);
					apply.setPhone(phone);
					apply.setSize(size);
					apply.setLocation(location);
					apply.setDescription(new SimpleDateFormat("yyyy/MM/DD HH:mm").format(new Date())+
							" 直接进入页面报名");
					apply.setStatus(3);
					try {
						applyService.save(apply);
						json.setStatus(1).setMessage("报名成功！");
					} catch (Exception e) {
						e.printStackTrace();
						json.setStatus(0).setMessage(e.getMessage());
					}
				}
			}else{
				json.setStatus(0).setMessage("数据错误！");
			}
			model.addAttribute("json", JSONUtil.object2json(json));
			return "json";
		}


}
