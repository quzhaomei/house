package com.qicai.controller.admin.minisite;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.qicai.annotation.LimitTag;
import com.qicai.bean.minisite.DesignApply;
import com.qicai.controller.BaseController;
import com.qicai.dto.JsonDTO;
import com.qicai.dto.PageDTO;
import com.qicai.util.HttpSendResult;
import com.qicai.util.HttpSender;
import com.qicai.util.JSONUtil;
import com.qicai.util.ShortUrlUtil;

@Controller
@RequestMapping(value = "designApply")
@LimitTag(uri = true)
public class DesignApplyController extends BaseController {
	// ������ת�˵�ҳ��,������û��б�
	@RequestMapping(value = "/index")
	public String index(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		String pageIndex = request.getParameter("pageIndex");// ҳ��
		String pageSize = request.getParameter("pageSize");// ҳ����
		if (pageIndex == null) {
			pageIndex = "1";
		}
		if (pageSize == null) {
			pageSize = "10";
		}
		if (pageIndex.matches("\\d+") && pageSize.matches("\\d+")) {
			Integer pageIndexInt = Integer.parseInt(pageIndex);
			Integer pageSizeInt = Integer.parseInt(pageSize);
			PageDTO<DesignApply> page = new PageDTO<DesignApply>();
			if (pageIndexInt != 0 && pageSizeInt != 0) {
				page.setPageIndex(pageIndexInt);
				page.setPageSize(pageSizeInt);
				DesignApply selectParam = new DesignApply();
				page.setParam(selectParam);

				PageDTO<List<DesignApply>> pageDate = applyService
						.findByPage(page);
				model.addAttribute("pageResult", pageDate);
				return "admin/designApply";
			}
		}
		return "admin/designApply";
	}

	@RequestMapping(value = "/sendMsg")
	public String sendMsg(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		//���Ͷ���
		String applyId=request.getParameter("applyId");
		String content=request.getParameter("content");
		
		if(applyId!=null&&applyId.matches("\\d+")){
			DesignApply apply = new DesignApply();
			apply.setApplyId(Integer.parseInt(applyId));
			apply=applyService.getByParam(apply);
			if(apply!=null){
				String toPhone=apply.getPhone();//��ȡ�绰����
				try {
					//ȥ��content�еı�ǩ
					content=content.replaceAll("<[/]*url>", "");
					//Ϊnull�����Ͳ�������
					HttpSendResult result=HttpSender.sendMsg(toPhone, content);
					if(result.getIsSuccess()){//�ɹ��������״̬
						apply.setStatus(1);
						apply.setDescription(apply.getDescription()+
								" # "+dateFormat.format(new Date()) + " ���Ͷ���");
					}
					try {
						applyService.update(apply);
					} catch (Exception e) {
					}
					model.addAttribute("json", JSONUtil.object2json(result));
				} catch (Exception e) {
					model.addAttribute("json", 
							JSONUtil.object2json(new HttpSendResult(false, "ϵͳ�����쳣")));
					e.printStackTrace();
				}
			}
		} 
		return "json";
	}

	// ����
	@RequestMapping(value = "/saveOrUpdate")
	public String saveOrUpdate(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		String name = request.getParameter("name");
		String phone = request.getParameter("phone");
		JsonDTO json = new JsonDTO();
		if (phone != null && phone.matches("\\d+") && name != null) {
			DesignApply apply = new DesignApply();
			apply.setPhone(phone);
			DesignApply checkDate = applyService.getByParam(apply);// ��������Ƿ��ظ�
			if (checkDate != null) {
				json.setStatus(0).setMessage("���û��Ѿ�ע���");
			} else {
				// ��ʼ��
				apply.setUsername(name);
				apply.setCreateDate(new Date());
				apply.setStatus(0);
				apply.setDescription(dateFormat.format(new Date()) + " ����̨����");
				String host = request.getRequestURL().toString();
				host = host.substring(0, host.lastIndexOf("/"));
				host = host.substring(0, host.lastIndexOf("/"));
				host += "/w_m/designApply.html?phone=" + phone;
				String shortUrl = ShortUrlUtil.getShotUrl(host);
				apply.setShortUrl(shortUrl);// ռλ
				try {
					applyService.save(apply);
					json.setStatus(1).setMessage("��ӳɹ���");
				} catch (Exception e) {
					json.setStatus(0).setMessage("�½�ʧ�ܣ�" + e.getMessage());
					e.printStackTrace();
				}
			}
		} else {
			json.setStatus(0).setMessage("�����쳣��");
		}
		model.addAttribute("json", JSONUtil.object2json(json));
		return "json";
	}

	private SimpleDateFormat dateFormat = new SimpleDateFormat(
			"yyyy/MM/DD HH:mm");
}
