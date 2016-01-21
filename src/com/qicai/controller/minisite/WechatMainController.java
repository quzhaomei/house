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
 * �ר��controller
 * @author Administrator
 */
@Controller
@RequestMapping(value="w_m")
public class WechatMainController extends BaseController{
	//��������
		@RequestMapping(value = "/designApply.html")
		public String designApply(HttpServletRequest request,
				HttpServletResponse response, Model model) {
			String phone=request.getParameter("phone");
			if(phone!=null&&phone.matches("\\d+")){//�����û�״̬
				DesignApply apply=new DesignApply();
				apply.setPhone(phone);
				apply.setStatus(1);
				DesignApply checkDate=applyService.getByParam(apply);//��������Ƿ����
				if(checkDate!=null){//�����
					String description=checkDate.getDescription();
					description+=" # "+new SimpleDateFormat("yyyy/MM/DD HH:mm").format(new Date())+
							" �򿪶���ҳ�����";
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
					//�ж��Ƿ����ظ�����
					if(data.getStatus()!=3){//���и���
						data.setLocation(location);
						data.setStatus(3);//����
						data.setSize(size);
						data.setUsername(username);
						data.setDescription(data.getDescription()+" # "+new SimpleDateFormat("yyyy/MM/DD HH:mm").format(new Date())+
							" �����ɹ�");
						try {
							applyService.update(data);
							json.setStatus(1).setMessage("�����ɹ���");
						} catch (Exception e) {
							e.printStackTrace();
							json.setStatus(0).setMessage("����ʧ�ܣ�ϵͳ�쳣��"+e.getMessage());
						}
						
					}else{
						json.setStatus(0).setMessage("���û��Ѿ��������ˣ�");
					}
				}else{
					apply.setCreateDate(new Date());
					apply.setUsername(username);
					apply.setPhone(phone);
					apply.setSize(size);
					apply.setLocation(location);
					apply.setDescription(new SimpleDateFormat("yyyy/MM/DD HH:mm").format(new Date())+
							" ֱ�ӽ���ҳ�汨��");
					apply.setStatus(3);
					try {
						applyService.save(apply);
						json.setStatus(1).setMessage("�����ɹ���");
					} catch (Exception e) {
						e.printStackTrace();
						json.setStatus(0).setMessage(e.getMessage());
					}
				}
			}else{
				json.setStatus(0).setMessage("���ݴ���");
			}
			model.addAttribute("json", JSONUtil.object2json(json));
			return "json";
		}


}
