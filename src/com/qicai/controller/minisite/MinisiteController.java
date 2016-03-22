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
import com.qicai.util.JSONUtil;
import com.qicai.util.RequestUtil;
/**
 * �ר��controller
 * 
 * @author Administrator
 */
@Controller
@RequestMapping(value = "minisite")
public class MinisiteController extends BaseController {
	@RequestMapping(value = "/designApply_to.html")
	public String designApply_to(HttpServletRequest request, HttpServletResponse response, Model model) {
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
			json.setStatus(0).setMessage("��������������");
		}else if(userphone==null){
			json.setStatus(0).setMessage("���������ĵ绰����");
		}
		designApply.setPhone(userphone);
		DesignApply old=applyService.getByParam(designApply);
		if(old!=null){
			json.setStatus(0).setMessage("�õ绰�����Ѿ��������������ظ�����");
		}else{
			designApply.setCreateDate(new Date());
			designApply.setUsername(username);
			designApply.setLocation(address);
			designApply.setStatus(0);
			try {
				applyService.save(designApply);
				json.setStatus(1).setMessage("�����ɹ�");
			} catch (Exception e) {
				e.printStackTrace();
				json.setStatus(0).setMessage("����ʱ����̨�����쳣�����Ժ����");
			}
		}
			model.addAttribute("json", JSONUtil.object2json(json));
		return JSON;
	}
}
